/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.loader.api.FabricLoader
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package net.anubis.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.anubis.client.config.BundledDefaults;
import net.anubis.client.event.impl.ModuleToggleEvent;
import net.anubis.client.input.ModuleKeybinds;
import net.anubis.client.module.ClientModule;
import net.anubis.client.module.ModuleRegistry;
import net.anubis.client.module.ModuleRevision;
import net.anubis.client.module.setting.Setting;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(value=EnvType.CLIENT)
public final class ModuleConfigStore {
    private static final Logger LOGGER = LoggerFactory.getLogger((String)"Anubis");
    private static final ModuleConfigStore INSTANCE = new ModuleConfigStore();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final int CONFIG_VERSION = 3;
    private static final long AUTOSAVE_DELAY_NANOS = 2000000000L;
    private static final Map<String, String> RENAMED_MODULES = Map.of("activity-bypass", "player-bypass", "Activity Bypass", "player-bypass");
    private final Map<String, JsonObject> orphanModules = new LinkedHashMap<String, JsonObject>();
    private final Map<String, Boolean> pendingEnabled = new LinkedHashMap<String, Boolean>();
    private boolean loaded;
    private boolean readOnly;
    private final Map<String, JsonObject> retainedEntries = new LinkedHashMap<String, JsonObject>();
    private String activeProfile;
    private long savedRevision = -1L;
    private long observedRevision = -1L;
    private long observedAt;

    private ModuleConfigStore() {
    }

    public static ModuleConfigStore getInstance() {
        return INSTANCE;
    }

    public synchronized void load() {
        if (this.loaded) {
            return;
        }
        this.loaded = true;
        this.orphanModules.clear();
        this.pendingEnabled.clear();
        BundledDefaults.adopt(ModuleRegistry.all());
        this.readConfig();
        for (ClientModule module : ModuleRegistry.all()) {
            if (!BundledDefaults.enabledByDefault(module) || this.retainedEntries.containsKey(module.id())) continue;
            this.pendingEnabled.put(module.id(), true);
        }
    }

    private void readConfig() {
        Path path = this.configPath();
        if (!Files.isRegularFile(path, new LinkOption[0])) {
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(path);){
            Path backup;
            int version;
            JsonObject root = JsonParser.parseReader((Reader)reader).getAsJsonObject();
            this.applyConfigRoot(root);
            if (root.has("activeProfile") && root.get("activeProfile").isJsonPrimitive()) {
                this.activeProfile = root.get("activeProfile").getAsString();
            }
            int n = version = root.has("version") ? root.get("version").getAsInt() : 1;
            if (!this.readOnly && version < 3 && !Files.exists(backup = path.resolveSibling(String.valueOf(path.getFileName()) + ".v" + version + ".bak"), new LinkOption[0])) {
                Files.copy(path, backup, new CopyOption[0]);
            }
        }
        catch (IOException | RuntimeException e) {
            LOGGER.warn("[Anubis] Failed to load module config; preserving the file", (Throwable)e);
            this.orphanModules.clear();
            this.pendingEnabled.clear();
            this.readOnly = true;
        }
    }

    public synchronized boolean enablesOnLoad(ClientModule module) {
        return Boolean.TRUE.equals(this.pendingEnabled.get(module.id()));
    }

    public synchronized void applyPendingEnabled() {
        if (this.pendingEnabled.isEmpty()) {
            return;
        }
        ModuleToggleEvent.silently(() -> {
            for (Map.Entry<String, Boolean> entry : this.pendingEnabled.entrySet()) {
                ClientModule module;
                if (!Boolean.TRUE.equals(entry.getValue()) || (module = ModuleRegistry.resolve(entry.getKey())) == null || !module.persistEnabled()) continue;
                module.setEnabled(true);
            }
        });
        this.pendingEnabled.clear();
    }

    public synchronized void save() {
        this.ensureLoaded();
        if (this.readOnly) {
            return;
        }
        long revision = ModuleRevision.current();
        JsonObject root = this.buildConfigRoot();
        if (this.activeProfile != null) {
            root.addProperty("activeProfile", this.activeProfile);
        }
        Path path = this.configPath();
        try {
            Files.createDirectories(path.getParent(), new FileAttribute[0]);
            Path tmp = path.resolveSibling(path.getFileName().toString() + ".tmp");
            try (BufferedWriter writer = Files.newBufferedWriter(tmp, new OpenOption[0]);){
                GSON.toJson((JsonElement)root, (Appendable)writer);
            }
            try {
                Files.move(tmp, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            }
            catch (IOException atomicFail) {
                Files.move(tmp, path, StandardCopyOption.REPLACE_EXISTING);
            }
            this.savedRevision = revision;
        }
        catch (IOException e) {
            LOGGER.warn("[Anubis] Failed to save module config", (Throwable)e);
        }
    }

    public synchronized void tickAutosave() {
        long now = System.nanoTime();
        if (!this.autosaveDue(ModuleRevision.current(), now)) {
            return;
        }
        this.save();
        this.observedAt = now;
    }

    private boolean autosaveDue(long revision, long now) {
        if (!this.loaded || this.readOnly) {
            return false;
        }
        if (this.savedRevision < 0L) {
            this.savedRevision = this.observedRevision = revision;
            return false;
        }
        if (revision == this.savedRevision) {
            return false;
        }
        if (revision != this.observedRevision) {
            this.observedRevision = revision;
            this.observedAt = now;
            return false;
        }
        return now - this.observedAt >= 2000000000L;
    }

    public synchronized boolean hasUnsavedChanges() {
        return !this.readOnly && this.savedRevision >= 0L && ModuleRevision.current() != this.savedRevision;
    }

    public synchronized boolean isReadOnly() {
        return this.readOnly;
    }

    public synchronized String activeProfile() {
        return this.activeProfile;
    }

    synchronized void setActiveProfile(String name) {
        this.activeProfile = name;
    }

    public synchronized JsonObject snapshot() {
        this.ensureLoaded();
        JsonObject root = this.buildConfigRoot();
        JsonObject modules = root.getAsJsonObject("modules");
        for (ClientModule module : ModuleRegistry.all()) {
            if (module.inConfigs()) {
                modules.add(module.id(), (JsonElement)ModuleConfigStore.withoutPrivate(module, modules.getAsJsonObject(module.id())));
                continue;
            }
            modules.remove(module.id());
        }
        return root;
    }

    public synchronized boolean applyProfile(JsonObject root) {
        this.ensureLoaded();
        int version = ModuleConfigStore.profileVersion(root);
        if (version < 0) {
            return false;
        }
        List<ClientModule> modules = ModuleRegistry.all();
        Map<ClientModule, JsonObject> matched = ModuleConfigStore.matchEntries(root, version, modules);
        HashMap enable = new HashMap();
        ModuleToggleEvent.silently(() -> {
            for (ClientModule module : modules) {
                if (!module.inConfigs()) continue;
                JsonObject entry = (JsonObject)matched.get(module);
                for (Setting setting : module.settings()) {
                    if (setting.isPrivate()) continue;
                    setting.resetToDefault();
                }
                module.setKeybind(-1);
                module.setHoldMode(false);
                if (entry != null) {
                    entry = ModuleConfigStore.withoutPrivate(module, entry);
                    ModuleConfigStore.applyModuleEntry(module, entry, enable);
                    this.retainedEntries.put(module.id(), entry);
                } else {
                    this.retainedEntries.remove(module.id());
                }
                if (!module.persistEnabled()) continue;
                module.setEnabled(Boolean.TRUE.equals(enable.get(module.id())));
            }
        });
        this.pendingEnabled.clear();
        return true;
    }

    public synchronized JsonObject shareable(JsonObject root) {
        int version = ModuleConfigStore.profileVersion(root);
        if (version < 0) {
            return null;
        }
        List<ClientModule> modules = ModuleRegistry.all();
        Map<ClientModule, JsonObject> matched = ModuleConfigStore.matchEntries(root, version, modules);
        JsonObject shared = new JsonObject();
        for (ClientModule module : modules) {
            JsonObject entry = module.inConfigs() ? matched.get(module) : null;
            JsonObject kept = entry == null ? null : ModuleConfigStore.shareableEntry(module, entry);
            if (kept == null) continue;
            shared.add(module.id(), (JsonElement)kept);
        }
        JsonObject out = new JsonObject();
        out.addProperty("version", (Number)3);
        out.add("modules", (JsonElement)shared);
        return out;
    }

    private static int profileVersion(JsonObject root) {
        if (root == null || !root.has("modules") || !root.get("modules").isJsonObject()) {
            return -1;
        }
        try {
            int version = root.has("version") ? root.get("version").getAsInt() : 0;
            return version > 3 ? -1 : version;
        }
        catch (RuntimeException e) {
            return -1;
        }
    }

    private static Map<ClientModule, JsonObject> matchEntries(JsonObject root, int version, List<ClientModule> modules) {
        HashMap<String, ClientModule> byKey = new HashMap<String, ClientModule>();
        for (ClientModule module : modules) {
            byKey.putIfAbsent(module.name(), module);
        }
        for (ClientModule module : modules) {
            byKey.put(module.id(), module);
        }
        HashMap<ClientModule, JsonObject> matched = new HashMap<ClientModule, JsonObject>();
        for (Map.Entry entry : ModuleConfigStore.renameModules(root.getAsJsonObject("modules"), version).entrySet()) {
            if (!((JsonElement)entry.getValue()).isJsonObject()) continue;
            JsonObject moduleEntry = ((JsonElement)entry.getValue()).getAsJsonObject();
            ClientModule module = (ClientModule)byKey.get(entry.getKey());
            if (module == null && moduleEntry.has("name") && moduleEntry.get("name").isJsonPrimitive()) {
                module = (ClientModule)byKey.get(moduleEntry.get("name").getAsString());
            }
            if (module == null) continue;
            matched.putIfAbsent(module, moduleEntry);
        }
        return matched;
    }

    private static JsonObject shareableEntry(ClientModule module, JsonObject entry) {
        int key;
        JsonObject out = new JsonObject();
        if (module.persistEnabled() && ModuleConfigStore.flag(entry, "enabled")) {
            out.addProperty("enabled", Boolean.valueOf(true));
        }
        if ((key = ModuleConfigStore.number(entry, "keybind", -1)) != -1 && ModuleKeybinds.isBindable(key)) {
            out.addProperty("keybind", (Number)key);
        }
        if (ModuleConfigStore.flag(entry, "holdMode")) {
            out.addProperty("holdMode", Boolean.valueOf(true));
        }
        JsonObject values = entry.has("settings") && entry.get("settings").isJsonObject() ? entry.getAsJsonObject("settings") : null;
        JsonObject settings = new JsonObject();
        if (values != null) {
            for (Setting setting : module.settings()) {
                JsonElement value;
                if (setting.isPrivate() || (value = values.has(setting.id()) ? values.get(setting.id()) : values.get(setting.name())) == null || value.isJsonNull() || setting.isDefault(value)) continue;
                settings.add(setting.id(), setting.compact(value.deepCopy()));
            }
        }
        if (settings.size() > 0) {
            out.add("settings", (JsonElement)settings);
        }
        if (out.size() == 0) {
            return null;
        }
        int saved = ModuleConfigStore.number(entry, "settingsVersion", 0);
        if (saved > 0) {
            out.addProperty("settingsVersion", (Number)saved);
        }
        return out;
    }

    private static JsonObject withoutPrivate(ClientModule module, JsonObject entry) {
        JsonObject copy = entry.deepCopy();
        if (copy.has("settings") && copy.get("settings").isJsonObject()) {
            JsonObject settings = copy.getAsJsonObject("settings");
            for (Setting setting : module.settings()) {
                if (!setting.isPrivate()) continue;
                settings.remove(setting.id());
                settings.remove(setting.name());
            }
        }
        return copy;
    }

    private static boolean flag(JsonObject entry, String key) {
        JsonElement value = entry.get(key);
        if (value == null || !value.isJsonPrimitive()) {
            return false;
        }
        try {
            return value.getAsBoolean();
        }
        catch (RuntimeException e) {
            return false;
        }
    }

    private static int number(JsonObject entry, String key, int fallback) {
        JsonElement value = entry.get(key);
        if (value == null || !value.isJsonPrimitive()) {
            return fallback;
        }
        try {
            return value.getAsInt();
        }
        catch (RuntimeException e) {
            return fallback;
        }
    }

    public synchronized void adopt(ClientModule module) {
        if (!this.loaded) {
            return;
        }
        JsonObject entry = this.orphanModules.remove(module.id());
        if (entry == null) {
            return;
        }
        this.retainedEntries.put(module.id(), entry.deepCopy());
        ModuleConfigStore.applyModuleEntry(module, entry, this.pendingEnabled);
    }

    public synchronized void release(ClientModule module) {
        if (!this.loaded) {
            return;
        }
        JsonObject entry = this.serializeModule(module);
        if (Boolean.TRUE.equals(this.pendingEnabled.remove(module.id()))) {
            entry.addProperty("enabled", Boolean.valueOf(true));
        }
        this.orphanModules.put(module.id(), entry);
        this.retainedEntries.remove(module.id());
    }

    public static String renamedModuleId(String key) {
        for (Map.Entry<String, String> rename : RENAMED_MODULES.entrySet()) {
            if (!rename.getKey().equalsIgnoreCase(key)) continue;
            return rename.getValue();
        }
        return null;
    }

    private void applyConfigRoot(JsonObject root) {
        int version;
        if (root == null) {
            return;
        }
        int n = version = root.has("version") ? root.get("version").getAsInt() : 0;
        if (version > 3) {
            this.readOnly = true;
            LOGGER.warn("[Anubis] Module config version {} is newer than supported {}; skipping load", (Object)version, (Object)3);
            return;
        }
        if (!root.has("modules") || !root.get("modules").isJsonObject()) {
            return;
        }
        JsonObject modules = ModuleConfigStore.renameModules(root.getAsJsonObject("modules"), version);
        for (Map.Entry entry : modules.entrySet()) {
            String name = (String)entry.getKey();
            if (!((JsonElement)entry.getValue()).isJsonObject()) continue;
            JsonObject moduleEntry = ((JsonElement)entry.getValue()).getAsJsonObject();
            ClientModule module = ModuleRegistry.resolve(name);
            if (module == null && version >= 2 && moduleEntry.has("name") && moduleEntry.get("name").isJsonPrimitive()) {
                module = ModuleRegistry.resolve(moduleEntry.get("name").getAsString());
            }
            if (module == null) {
                this.orphanModules.put(name, moduleEntry.deepCopy());
                continue;
            }
            this.retainedEntries.put(module.id(), moduleEntry.deepCopy());
            ModuleConfigStore.applyModuleEntry(module, moduleEntry, this.pendingEnabled);
        }
    }

    private JsonObject buildConfigRoot() {
        JsonObject root = new JsonObject();
        root.addProperty("version", (Number)3);
        JsonObject modulesObj = new JsonObject();
        for (ClientModule clientModule : ModuleRegistry.all()) {
            modulesObj.add(clientModule.id(), (JsonElement)this.serializeModule(clientModule));
        }
        for (Map.Entry entry : this.orphanModules.entrySet()) {
            if (modulesObj.has((String)entry.getKey())) continue;
            modulesObj.add((String)entry.getKey(), (JsonElement)((JsonObject)entry.getValue()).deepCopy());
        }
        root.add("modules", (JsonElement)modulesObj);
        return root;
    }

    private void ensureLoaded() {
        if (!this.loaded) {
            this.load();
        }
    }

    private Path configPath() {
        return FabricLoader.getInstance().getConfigDir().resolve("anubis").resolve("modules.json");
    }

    private static void applyModuleEntry(ClientModule module, JsonObject entry, Map<String, Boolean> pendingEnabled) {
        boolean enabled;
        if (entry.has("keybind") && entry.get("keybind").isJsonPrimitive()) {
            try {
                int key = entry.get("keybind").getAsInt();
                if (key == -1 || ModuleKeybinds.isBindable(key)) {
                    module.setKeybind(key);
                }
            }
            catch (RuntimeException key) {
                // empty catch block
            }
        }
        if (entry.has("holdMode") && entry.get("holdMode").isJsonPrimitive()) {
            try {
                module.setHoldMode(entry.get("holdMode").getAsBoolean());
            }
            catch (RuntimeException key) {
                // empty catch block
            }
        }
        if (entry.has("settings") && entry.get("settings").isJsonObject()) {
            JsonObject settings = entry.getAsJsonObject("settings");
            for (Setting setting : module.settings()) {
                JsonElement value;
                if (setting.isSessionOnly() || (value = settings.has(setting.id()) ? settings.get(setting.id()) : settings.get(setting.name())) == null) continue;
                setting.deserialize(value);
            }
        }
        ModuleConfigStore.migrateSettings(module, entry);
        if (!entry.has("enabled") || !entry.get("enabled").isJsonPrimitive()) {
            return;
        }
        try {
            enabled = entry.get("enabled").getAsBoolean();
        }
        catch (RuntimeException ignored) {
            return;
        }
        if (!module.persistEnabled()) {
            return;
        }
        String name = module.id();
        if (enabled) {
            pendingEnabled.put(name, true);
        } else {
            module.setEnabled(false);
        }
    }

    private static void migrateSettings(ClientModule module, JsonObject entry) {
        int current = module.settingsVersion();
        if (current <= 0) {
            return;
        }
        int saved = 0;
        if (entry.has("settingsVersion") && entry.get("settingsVersion").isJsonPrimitive()) {
            try {
                saved = entry.get("settingsVersion").getAsInt();
            }
            catch (RuntimeException runtimeException) {
                // empty catch block
            }
        }
        if (saved >= current) {
            return;
        }
        try {
            module.migrateSettings(saved);
        }
        catch (RuntimeException e) {
            LOGGER.warn("[Anubis] Failed to migrate settings of '{}' from version {}", new Object[]{module.id(), saved, e});
        }
    }

    private static JsonObject renameModules(JsonObject modules, int version) {
        if (version >= 3) {
            return modules;
        }
        JsonObject renamed = new JsonObject();
        for (Map.Entry entry : modules.entrySet()) {
            String id = RENAMED_MODULES.get(entry.getKey());
            if (id == null) {
                renamed.add((String)entry.getKey(), (JsonElement)entry.getValue());
                continue;
            }
            if (modules.has(id) || renamed.has(id)) continue;
            renamed.add(id, (JsonElement)entry.getValue());
        }
        return renamed;
    }

    private JsonObject serializeModule(ClientModule module) {
        JsonObject previous = this.retainedEntries.get(module.id());
        JsonObject entry = previous == null ? new JsonObject() : previous.deepCopy();
        entry.addProperty("name", module.name());
        entry.addProperty("enabled", Boolean.valueOf(module.isEnabled()));
        entry.addProperty("keybind", (Number)module.keybind());
        entry.addProperty("holdMode", Boolean.valueOf(module.isHoldMode()));
        if (module.settingsVersion() > 0) {
            entry.addProperty("settingsVersion", (Number)module.settingsVersion());
        }
        JsonObject settings = entry.has("settings") && entry.get("settings").isJsonObject() ? entry.getAsJsonObject("settings").deepCopy() : new JsonObject();
        for (Setting setting : module.settings()) {
            settings.remove(setting.name());
            if (setting.isSessionOnly()) {
                settings.remove(setting.id());
                continue;
            }
            settings.add(setting.id(), setting.serialize());
        }
        entry.add("settings", (JsonElement)settings);
        return entry;
    }
}

