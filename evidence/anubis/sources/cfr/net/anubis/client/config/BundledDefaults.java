/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package net.anubis.client.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.anubis.client.input.ModuleKeybinds;
import net.anubis.client.module.ClientModule;
import net.anubis.client.module.setting.Setting;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(value=EnvType.CLIENT)
public final class BundledDefaults {
    private static final Logger LOGGER = LoggerFactory.getLogger((String)"Anubis");
    private static final String ROOT = "/assets/anubis/defaults/";
    private static JsonObject modules;
    private static JsonObject clickGui;
    private static List<JsonObject> presets;

    private BundledDefaults() {
    }

    public static synchronized void adopt(List<ClientModule> registered) {
        for (ClientModule module : registered) {
            JsonObject entry = BundledDefaults.entry(module);
            if (entry == null || !module.inConfigs()) continue;
            try {
                int key;
                JsonObject settings = BundledDefaults.object(entry, "settings");
                if (settings != null) {
                    for (Setting setting : module.settings()) {
                        JsonElement value = settings.get(setting.id());
                        if (value == null || setting.isPrivate()) continue;
                        setting.adoptDefault(value);
                    }
                }
                if (entry.has("keybind") && ModuleKeybinds.isBindable(key = entry.get("keybind").getAsInt())) {
                    module.setKeybind(key);
                }
                if (!entry.has("holdMode")) continue;
                module.setHoldMode(entry.get("holdMode").getAsBoolean());
            }
            catch (RuntimeException e) {
                LOGGER.warn("[Anubis] Failed to apply bundled defaults of '{}'", (Object)module.id(), (Object)e);
            }
        }
    }

    public static synchronized boolean enabledByDefault(ClientModule module) {
        JsonElement enabled;
        JsonObject entry = module.inConfigs() ? BundledDefaults.entry(module) : null;
        JsonElement jsonElement = enabled = entry == null ? null : entry.get("enabled");
        if (enabled == null || !enabled.isJsonPrimitive()) {
            return module.enabledByDefault();
        }
        try {
            return enabled.getAsBoolean();
        }
        catch (RuntimeException e) {
            return module.enabledByDefault();
        }
    }

    public static synchronized float[] panelSpot(String panel, float screenW, float screenH) {
        JsonObject layout = BundledDefaults.clickGui();
        JsonObject panels = BundledDefaults.object(layout, "panels");
        JsonElement screen = layout.get("screen");
        if (panels == null || screen == null || !screen.isJsonArray() || !panels.has(panel)) {
            return null;
        }
        try {
            JsonArray reference = screen.getAsJsonArray();
            JsonArray spot = panels.getAsJsonArray(panel);
            return new float[]{spot.get(0).getAsFloat() * screenW / reference.get(0).getAsFloat(), spot.get(1).getAsFloat() * screenH / reference.get(1).getAsFloat()};
        }
        catch (RuntimeException e) {
            return null;
        }
    }

    public static synchronized Set<String> collapsedPanels() {
        LinkedHashSet<String> collapsed = new LinkedHashSet<String>();
        JsonElement list = BundledDefaults.clickGui().get("collapsed");
        if (list == null || !list.isJsonArray()) {
            return collapsed;
        }
        for (JsonElement element : list.getAsJsonArray()) {
            if (!element.isJsonPrimitive()) continue;
            collapsed.add(element.getAsString());
        }
        return collapsed;
    }

    public static synchronized List<JsonObject> presets() {
        if (presets == null) {
            ArrayList<JsonObject> list = new ArrayList<JsonObject>();
            JsonElement array = BundledDefaults.read("presets.json").get("presets");
            if (array != null && array.isJsonArray()) {
                for (JsonElement element : array.getAsJsonArray()) {
                    if (!element.isJsonObject()) continue;
                    list.add(element.getAsJsonObject());
                }
            }
            presets = List.copyOf(list);
        }
        ArrayList<JsonObject> copies = new ArrayList<JsonObject>(presets.size());
        for (JsonObject preset : presets) {
            copies.add(preset.deepCopy());
        }
        return copies;
    }

    private static JsonObject entry(ClientModule module) {
        JsonObject all = BundledDefaults.object(BundledDefaults.modules(), "modules");
        return all == null ? null : BundledDefaults.object(all, module.id());
    }

    private static JsonObject modules() {
        if (modules == null) {
            modules = BundledDefaults.read("modules.json");
        }
        return modules;
    }

    private static JsonObject clickGui() {
        if (clickGui == null) {
            clickGui = BundledDefaults.read("clickgui.json");
        }
        return clickGui;
    }

    private static JsonObject object(JsonObject parent, String key) {
        JsonElement value = parent.get(key);
        return value != null && value.isJsonObject() ? value.getAsJsonObject() : null;
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private static JsonObject read(String name) {
        try (InputStream in = BundledDefaults.class.getResourceAsStream(ROOT + name);){
            JsonObject jsonObject;
            if (in == null) {
                JsonObject jsonObject2 = new JsonObject();
                return jsonObject2;
            }
            try (InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);){
                jsonObject = JsonParser.parseReader((Reader)reader).getAsJsonObject();
            }
            return jsonObject;
        }
        catch (Exception e) {
            LOGGER.warn("[Anubis] Failed to read bundled defaults {}", (Object)name, (Object)e);
            return new JsonObject();
        }
    }
}

