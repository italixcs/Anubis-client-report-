/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.class_156
 *  net.minecraft.class_310
 *  net.minecraft.class_4844
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package net.anubis.client.account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import net.anubis.client.account.Account;
import net.anubis.client.account.AccountException;
import net.anubis.client.account.AccountType;
import net.anubis.client.account.AuthHttp;
import net.anubis.client.account.Credentials;
import net.anubis.client.account.GameSession;
import net.anubis.client.account.MicrosoftLogin;
import net.anubis.client.account.SessionLogin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_156;
import net.minecraft.class_310;
import net.minecraft.class_4844;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(value=EnvType.CLIENT)
public final class Accounts {
    private static final Logger LOGGER = LoggerFactory.getLogger((String)"Anubis");
    private static final Accounts INSTANCE = new Accounts();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final int CONFIG_VERSION = 1;
    private static final Pattern VALID_NAME = Pattern.compile("[A-Za-z0-9_]{1,16}");
    private final ExecutorService worker = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "Anubis Accounts");
        thread.setDaemon(true);
        return thread;
    });
    private final GameSession session = new GameSession();
    private final List<Account> accounts = new ArrayList<Account>();
    private volatile List<Account> snapshot = List.of();
    private volatile String activeId;
    private volatile boolean busy;
    private volatile String status = "";
    private volatile Thread microsoftSignIn;
    private volatile boolean loaded;
    private boolean readOnly;

    private Accounts() {
    }

    public static Accounts get() {
        return INSTANCE;
    }

    public List<Account> list() {
        this.ensureLoaded();
        return this.snapshot;
    }

    public Account active() {
        String sessionUuid = class_310.method_1551().method_1548().method_44717().toString();
        List<Account> all = this.list();
        String lastSwitched = this.activeId;
        for (Account account : all) {
            if (!account.id().equals(lastSwitched) || !account.uuid().equals(sessionUuid)) continue;
            return account;
        }
        for (Account account : all) {
            if (!account.uuid().equals(sessionUuid)) continue;
            return account;
        }
        return null;
    }

    public CompletableFuture<Account> addCracked(String username) {
        String name;
        String string = name = username == null ? "" : username.trim();
        if (!VALID_NAME.matcher(name).matches()) {
            return this.failed("A cracked name is 1-16 letters, digits or underscores");
        }
        return this.signIn(AccountType.CRACKED, null, "Logging in as " + name + "...", () -> Accounts.offline(name));
    }

    public CompletableFuture<Account> addSession(String token) {
        String accessToken;
        String string = accessToken = token == null ? "" : token.trim();
        if (accessToken.isEmpty()) {
            return this.failed("Paste an access token first");
        }
        return this.signIn(AccountType.SESSION, null, "Checking the session token...", () -> SessionLogin.signIn(accessToken));
    }

    public CompletableFuture<Account> addMicrosoft() {
        return this.signIn(AccountType.MICROSOFT, null, "Opening Microsoft sign-in...", this::microsoftBrowserSignIn);
    }

    public void cancelMicrosoft() {
        Thread signIn = this.microsoftSignIn;
        if (signIn != null) {
            signIn.interrupt();
        }
    }

    public CompletableFuture<Void> login(Account account) {
        Account saved = this.find(account);
        if (saved == null) {
            return this.failed("That account is no longer saved");
        }
        String who = saved.name().isEmpty() ? saved.type().label() + " account" : saved.name();
        return this.signIn(saved.type(), saved.id(), "Logging in as " + who + "...", () -> switch (saved.type()) {
            default -> throw new MatchException(null, null);
            case AccountType.CRACKED -> Accounts.offline(saved.name());
            case AccountType.SESSION -> SessionLogin.signIn(saved.secret());
            case AccountType.MICROSOFT -> MicrosoftLogin.minecraft(MicrosoftLogin.refresh(saved.secret()));
        }).thenAccept(signedIn -> {});
    }

    public synchronized void remove(Account account) {
        int index = this.indexOf(account);
        if (index < 0) {
            return;
        }
        this.accounts.remove(index);
        this.publish();
        this.save();
    }

    public boolean busy() {
        return this.busy;
    }

    public String status() {
        return this.status;
    }

    private static Credentials offline(String name) {
        return new Credentials(name, class_4844.method_43344((String)name), "", "");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Credentials microsoftBrowserSignIn() throws InterruptedException {
        this.microsoftSignIn = Thread.currentThread();
        try {
            MicrosoftLogin.DeviceCode code = MicrosoftLogin.requestDeviceCode();
            this.status = "Waiting for Microsoft sign-in in your browser... (code " + code.userCode() + " at " + code.verificationUri().replaceFirst("^https?://", "") + ")";
            class_156.method_668().method_670(code.directUri());
            MicrosoftLogin.MsaToken token = MicrosoftLogin.awaitApproval(code);
            this.status = "Signing in to Xbox Live...";
            Credentials credentials = MicrosoftLogin.minecraft(token);
            return credentials;
        }
        finally {
            this.microsoftSignIn = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private CompletableFuture<Account> signIn(AccountType type, String savedId, String progress, SignIn blocking) {
        Accounts accounts = this;
        synchronized (accounts) {
            if (this.busy) {
                return CompletableFuture.failedFuture(new AccountException("Another account action is still running"));
            }
            if (Accounts.inGame()) {
                return this.failed("Leave the world before switching accounts");
            }
            this.busy = true;
        }
        this.ensureLoaded();
        this.status = progress;
        CompletableFuture<Account> result = new CompletableFuture<Account>();
        this.worker.execute(() -> {
            Thread.interrupted();
            try {
                Credentials credentials = blocking.run();
                GameSession.Prepared prepared = this.session.prepare(type, credentials);
                class_310.method_1551().execute(() -> this.finish(type, savedId, credentials, prepared, result));
            }
            catch (InterruptedException e) {
                this.settleLater(result, null, new AccountException(type == AccountType.MICROSOFT ? "Microsoft sign-in cancelled" : "Login cancelled"));
            }
            catch (AccountException e) {
                LOGGER.warn("[Anubis] {} login failed: {}", (Object)type.label(), (Object)e.getMessage());
                this.settleLater(result, null, e);
            }
            catch (RuntimeException e) {
                LOGGER.warn("[Anubis] {} login failed", (Object)type.label(), (Object)e);
                this.settleLater(result, null, new AccountException("Login failed: " + e.getMessage(), e));
            }
        });
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void finish(AccountType type, String savedId, Credentials credentials, GameSession.Prepared prepared, CompletableFuture<Account> result) {
        Account account;
        Accounts accounts = this;
        synchronized (accounts) {
            account = this.remember(type, savedId, credentials);
        }
        if (Accounts.inGame()) {
            this.settle(result, null, new AccountException("Joined a world before the switch finished - leave it and log in again"));
            return;
        }
        try {
            this.session.apply(prepared);
        }
        catch (RuntimeException e) {
            LOGGER.error("[Anubis] Switching the session to {} failed", (Object)account, (Object)e);
            this.settle(result, null, new AccountException("Couldn't switch the game to " + credentials.name() + ": " + e.getMessage(), e));
            return;
        }
        this.activeId = account.id();
        LOGGER.info("[Anubis] Switched session to {}", (Object)account);
        this.status = "Logged in as " + credentials.name();
        this.settle(result, account, null);
    }

    private void settleLater(CompletableFuture<Account> result, Account account, AccountException error) {
        class_310.method_1551().execute(() -> this.settle(result, account, error));
    }

    private void settle(CompletableFuture<Account> result, Account account, AccountException error) {
        if (error != null) {
            this.status = error.getMessage();
        }
        this.busy = false;
        if (error != null) {
            result.completeExceptionally(error);
        } else {
            result.complete(account);
        }
    }

    private Account remember(AccountType type, String savedId, Credentials credentials) {
        Account account;
        String uuid = credentials.uuid().toString();
        int index = -1;
        for (int i = 0; i < this.accounts.size(); ++i) {
            boolean same;
            Account existing = this.accounts.get(i);
            boolean bl = savedId != null ? existing.id().equals(savedId) : (existing.type() == type && (type == AccountType.CRACKED ? existing.name().equalsIgnoreCase(credentials.name()) : existing.uuid().equals(uuid)) ? true : (same = false));
            if (!same) continue;
            index = i;
            break;
        }
        if (index < 0 && savedId != null) {
            return new Account(savedId, type, credentials.name(), uuid, credentials.secret());
        }
        Account account2 = account = index >= 0 ? this.accounts.get(index).withLogin(credentials.name(), uuid, credentials.secret()) : new Account(UUID.randomUUID().toString(), type, credentials.name(), uuid, credentials.secret());
        if (index >= 0) {
            this.accounts.set(index, account);
        } else {
            this.accounts.add(account);
        }
        this.publish();
        this.save();
        return account;
    }

    private static boolean inGame() {
        class_310 mc = class_310.method_1551();
        return mc.field_1687 != null || mc.method_1562() != null;
    }

    private <T> CompletableFuture<T> failed(String message) {
        this.status = message;
        return CompletableFuture.failedFuture(new AccountException(message));
    }

    private synchronized Account find(Account account) {
        int index = this.indexOf(account);
        return index < 0 ? null : this.accounts.get(index);
    }

    private int indexOf(Account account) {
        this.ensureLoaded();
        if (account == null) {
            return -1;
        }
        for (int i = 0; i < this.accounts.size(); ++i) {
            if (!this.accounts.get(i).id().equals(account.id())) continue;
            return i;
        }
        return -1;
    }

    private void publish() {
        this.snapshot = List.copyOf(this.accounts);
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
        this.loaded = true;
        Path path = Accounts.configPath();
        if (!Files.isRegularFile(path, new LinkOption[0])) {
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(path);){
            int version;
            JsonObject root = JsonParser.parseReader((Reader)reader).getAsJsonObject();
            int n = version = root.has("version") ? root.get("version").getAsInt() : 0;
            if (version > 1) {
                this.readOnly = true;
                LOGGER.warn("[Anubis] Account list version {} is newer than supported {}; skipping load", (Object)version, (Object)1);
                return;
            }
            if (!root.has("accounts") || !root.get("accounts").isJsonArray()) {
                return;
            }
            for (JsonElement element : root.getAsJsonArray("accounts")) {
                Account account;
                if (!element.isJsonObject() || (account = Accounts.read(element.getAsJsonObject())) == null) continue;
                this.accounts.add(account);
            }
        }
        catch (IOException | RuntimeException e) {
            LOGGER.warn("[Anubis] Failed to load the account list; preserving the file", (Throwable)e);
            this.accounts.clear();
            this.readOnly = true;
        }
        this.publish();
    }

    private static Account read(JsonObject json) {
        String typeName = AuthHttp.text(json, "type");
        AccountType type = null;
        for (AccountType candidate : AccountType.values()) {
            if (!candidate.name().equalsIgnoreCase(typeName)) continue;
            type = candidate;
        }
        if (type == null) {
            return null;
        }
        String id = AuthHttp.text(json, "id");
        String name = AuthHttp.text(json, "name");
        String uuid = AuthHttp.text(json, "uuid");
        String token = AuthHttp.text(json, "token");
        if (name == null) {
            name = "";
        }
        if (type == AccountType.CRACKED ? !VALID_NAME.matcher(name).matches() : token == null || token.isEmpty()) {
            return null;
        }
        if (type == AccountType.CRACKED) {
            uuid = class_4844.method_43344((String)name).toString();
        }
        return new Account(id == null || id.isEmpty() ? UUID.randomUUID().toString() : id, type, name, uuid == null ? "" : uuid, token == null ? "" : token);
    }

    private void save() {
        if (this.readOnly) {
            return;
        }
        JsonObject root = new JsonObject();
        root.addProperty("version", (Number)1);
        JsonArray list = new JsonArray();
        for (Account account : this.accounts) {
            JsonObject entry = new JsonObject();
            entry.addProperty("id", account.id());
            entry.addProperty("type", account.type().name());
            entry.addProperty("name", account.name());
            entry.addProperty("uuid", account.uuid());
            if (!account.secret().isEmpty()) {
                entry.addProperty("token", account.secret());
            }
            list.add((JsonElement)entry);
        }
        root.add("accounts", (JsonElement)list);
        Path path = Accounts.configPath();
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
            LOGGER.warn("[Anubis] Failed to save the account list", (Throwable)e);
        }
    }

    private static Path configPath() {
        return FabricLoader.getInstance().getConfigDir().resolve("anubis").resolve("accounts.json");
    }

    @Environment(value=EnvType.CLIENT)
    private static interface SignIn {
        public Credentials run() throws InterruptedException;
    }
}

