/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.net.InetAddresses
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonPrimitive
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.loader.api.FabricLoader
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package net.anubis.client.proxy;

import com.google.common.net.InetAddresses;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
import net.anubis.client.proxy.Proxy;
import net.anubis.client.proxy.ProxyType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(value=EnvType.CLIENT)
public final class Proxies {
    private static final Logger LOGGER = LoggerFactory.getLogger((String)"Anubis");
    private static final Proxies INSTANCE = new Proxies();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final int CONFIG_VERSION = 1;
    private static final Pattern IPV4 = Pattern.compile("\\d{1,3}(?:\\.\\d{1,3}){3}");
    private static final Pattern HOST_NAME = Pattern.compile("[A-Za-z0-9_-]+(?:\\.[A-Za-z0-9_-]+)*");
    private final List<Proxy> proxies = new CopyOnWriteArrayList<Proxy>();
    private final List<Proxy> view = Collections.unmodifiableList(this.proxies);
    private volatile boolean loaded;
    private boolean readOnly;

    private Proxies() {
    }

    public static Proxies get() {
        return INSTANCE;
    }

    public List<Proxy> list() {
        this.ensureLoaded();
        return this.view;
    }

    public Proxy enabled() {
        this.ensureLoaded();
        for (Proxy proxy : this.proxies) {
            if (!proxy.enabled) continue;
            return proxy;
        }
        return null;
    }

    public synchronized void setEnabled(Proxy proxy, boolean on) {
        this.ensureLoaded();
        if (!this.proxies.contains(proxy)) {
            return;
        }
        if (on) {
            if (!proxy.isValid()) {
                return;
            }
            for (Proxy other : this.proxies) {
                other.enabled = other == proxy;
            }
        } else {
            proxy.enabled = false;
        }
        this.save();
    }

    public synchronized void remove(Proxy proxy) {
        this.ensureLoaded();
        if (this.proxies.remove(proxy)) {
            this.save();
        }
    }

    public synchronized void removeAll(Collection<Proxy> gone) {
        this.ensureLoaded();
        if (this.proxies.removeAll(Set.copyOf(gone))) {
            this.save();
        }
    }

    public synchronized void checked() {
        this.ensureLoaded();
        this.save();
    }

    public synchronized int importText(String text) {
        this.ensureLoaded();
        if (text == null || text.isBlank()) {
            return 0;
        }
        HashSet<String> known = new HashSet<String>();
        for (Proxy proxy : this.proxies) {
            known.add(Proxies.key(proxy));
        }
        ArrayList<Proxy> found = new ArrayList<Proxy>();
        for (String line : text.split("\\R")) {
            Proxy proxy = Proxies.parseLine(line);
            if (proxy == null || !known.add(Proxies.key(proxy))) continue;
            found.add(proxy);
        }
        if (found.isEmpty()) {
            return 0;
        }
        this.proxies.addAll(found);
        this.save();
        return found.size();
    }

    synchronized Proxy enabledCopy() {
        Proxy proxy = this.enabled();
        return proxy == null ? null : proxy.copy();
    }

    private void keepOneEnabled() {
        Proxy keep = null;
        for (Proxy proxy : this.proxies) {
            if (keep != null || !proxy.enabled || !proxy.isValid()) continue;
            keep = proxy;
        }
        for (Proxy proxy : this.proxies) {
            boolean bl = proxy == keep;
            proxy.enabled = bl;
        }
    }

    private static String key(Proxy proxy) {
        return proxy.displayAddress().toLowerCase(Locale.ROOT);
    }

    static Proxy parseLine(String line) {
        String host;
        int colon;
        boolean uriLogin;
        String rest;
        String string = rest = line == null ? "" : line.strip();
        if (rest.isEmpty() || rest.startsWith("#")) {
            return null;
        }
        ProxyType type = ProxyType.SOCKS5;
        int scheme = rest.indexOf("://");
        if (scheme >= 0) {
            type = ProxyType.parse(rest.substring(0, scheme));
            if (type == null) {
                return null;
            }
            if ((rest = rest.substring(scheme + 3)).endsWith("/")) {
                rest = rest.substring(0, rest.length() - 1);
            }
        }
        String username = "";
        String password = "";
        int at = rest.lastIndexOf(64);
        boolean bl = uriLogin = at >= 0;
        if (uriLogin) {
            String login = rest.substring(0, at);
            rest = rest.substring(at + 1);
            colon = login.indexOf(58);
            username = colon < 0 ? login : login.substring(0, colon);
            String string2 = password = colon < 0 ? "" : login.substring(colon + 1);
        }
        if (rest.startsWith("[")) {
            int close = rest.indexOf(93);
            if (close < 0 || close + 1 >= rest.length() || rest.charAt(close + 1) != ':') {
                return null;
            }
            host = rest.substring(1, close);
            rest = rest.substring(close + 2);
        } else {
            colon = rest.indexOf(58);
            if (colon < 0) {
                return null;
            }
            host = rest.substring(0, colon);
            rest = rest.substring(colon + 1);
        }
        String[] parts = rest.split(":", 3);
        int port = Proxies.parsePort(parts[0]);
        host = Proxies.normalizeHost(host);
        if (port < 0 || host == null) {
            return null;
        }
        if (parts.length > 1) {
            if (uriLogin || parts[1].isEmpty()) {
                return null;
            }
            username = parts[1];
            password = parts.length > 2 ? parts[2] : "";
        }
        Proxy proxy = new Proxy();
        proxy.type = type;
        proxy.address = host;
        proxy.port = port;
        proxy.username = username;
        proxy.password = password;
        proxy.name = proxy.displayAddress();
        return proxy;
    }

    private static int parsePort(String text) {
        if (text.isEmpty() || text.length() > 5) {
            return -1;
        }
        for (int i = 0; i < text.length(); ++i) {
            if (text.charAt(i) >= '0' && text.charAt(i) <= '9') continue;
            return -1;
        }
        int port = Integer.parseInt(text);
        return port >= 1 && port <= 65535 ? port : -1;
    }

    private static String normalizeHost(String host) {
        if (IPV4.matcher(host).matches()) {
            StringBuilder plain = new StringBuilder();
            for (String octet : host.split("\\.")) {
                int value = Integer.parseInt(octet);
                if (value > 255) {
                    return null;
                }
                if (!plain.isEmpty()) {
                    plain.append('.');
                }
                plain.append(value);
            }
            return plain.toString();
        }
        if (host.indexOf(58) >= 0) {
            return InetAddresses.isInetAddress((String)host) ? host : null;
        }
        return HOST_NAME.matcher(host).matches() ? host : null;
    }

    private void ensureLoaded() {
        if (!this.loaded) {
            this.load();
        }
    }

    private synchronized void load() {
        if (this.loaded) {
            return;
        }
        Path path = Proxies.configPath();
        ArrayList<Proxy> saved = new ArrayList<Proxy>();
        if (Files.isRegularFile(path, new LinkOption[0])) {
            try (BufferedReader reader = Files.newBufferedReader(path);){
                JsonObject root = JsonParser.parseReader((Reader)reader).getAsJsonObject();
                int version = Proxies.integer(root, "version");
                if (version > 1) {
                    this.readOnly = true;
                    LOGGER.warn("[Anubis] Proxy list version {} is newer than supported {}; skipping load", (Object)version, (Object)1);
                } else {
                    JsonElement jsonElement = root.get("proxies");
                    if (jsonElement instanceof JsonArray) {
                        JsonArray list = (JsonArray)jsonElement;
                        for (JsonElement element : list) {
                            if (!(element instanceof JsonObject)) continue;
                            JsonObject entry = (JsonObject)element;
                            saved.add(Proxies.readEntry(entry));
                        }
                    }
                }
            }
            catch (IOException | RuntimeException e) {
                LOGGER.warn("[Anubis] Failed to load the proxy list; starting empty", (Throwable)e);
                saved.clear();
                this.setAside(path);
            }
        }
        this.proxies.addAll(saved);
        this.keepOneEnabled();
        this.loaded = true;
    }

    private void setAside(Path path) {
        try {
            Files.move(path, path.resolveSibling(String.valueOf(path.getFileName()) + ".corrupt"), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            LOGGER.warn("[Anubis] Couldn't move the broken proxy list aside; not saving proxies this session", (Throwable)e);
            this.readOnly = true;
        }
    }

    private static Proxy readEntry(JsonObject json) {
        JsonPrimitive ping;
        JsonPrimitive flag;
        Proxy proxy = new Proxy();
        proxy.name = Proxies.string(json, "name");
        ProxyType type = ProxyType.parse(Proxies.string(json, "type"));
        proxy.type = type == null ? ProxyType.SOCKS5 : type;
        proxy.address = Proxies.string(json, "address");
        proxy.port = Proxies.integer(json, "port");
        proxy.username = Proxies.string(json, "username");
        proxy.password = Proxies.string(json, "password");
        JsonElement jsonElement = json.get("enabled");
        proxy.enabled = jsonElement instanceof JsonPrimitive && (flag = (JsonPrimitive)jsonElement).isBoolean() && flag.getAsBoolean();
        jsonElement = json.get("ping");
        if (jsonElement instanceof JsonPrimitive && (ping = (JsonPrimitive)jsonElement).isNumber()) {
            proxy.ping = Math.max(-1, ping.getAsInt());
        }
        return proxy;
    }

    private static String string(JsonObject json, String key) {
        String string;
        JsonElement jsonElement = json.get(key);
        if (jsonElement instanceof JsonPrimitive) {
            JsonPrimitive value = (JsonPrimitive)jsonElement;
            string = value.getAsString();
        } else {
            string = "";
        }
        return string;
    }

    private static int integer(JsonObject json, String key) {
        JsonPrimitive value;
        JsonElement jsonElement = json.get(key);
        return jsonElement instanceof JsonPrimitive && (value = (JsonPrimitive)jsonElement).isNumber() ? value.getAsInt() : 0;
    }

    private void save() {
        if (this.readOnly) {
            return;
        }
        JsonObject root = new JsonObject();
        root.addProperty("version", (Number)1);
        JsonArray list = new JsonArray();
        for (Proxy proxy : this.proxies) {
            JsonObject entry = new JsonObject();
            entry.addProperty("name", proxy.name == null ? "" : proxy.name);
            entry.addProperty("type", (proxy.type == null ? ProxyType.SOCKS5 : proxy.type).name());
            entry.addProperty("address", proxy.address == null ? "" : proxy.address.trim());
            entry.addProperty("port", (Number)proxy.port);
            if (proxy.username != null && !proxy.username.isEmpty()) {
                entry.addProperty("username", proxy.username);
            }
            if (proxy.password != null && !proxy.password.isEmpty()) {
                entry.addProperty("password", proxy.password);
            }
            entry.addProperty("enabled", Boolean.valueOf(proxy.enabled));
            if (proxy.ping != -2) {
                entry.addProperty("ping", (Number)proxy.ping);
            }
            list.add((JsonElement)entry);
        }
        root.add("proxies", (JsonElement)list);
        Path path = Proxies.configPath();
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
        }
        catch (IOException e) {
            LOGGER.warn("[Anubis] Failed to save the proxy list", (Throwable)e);
        }
    }

    private static Path configPath() {
        return FabricLoader.getInstance().getConfigDir().resolve("anubis").resolve("proxies.json");
    }
}

