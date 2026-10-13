package net.anubis.client.account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import net.anubis.client.account.GameSession;
import net.anubis.client.account.MicrosoftLogin;
import net.anubis.client.addon.AddonManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_156;
import net.minecraft.class_310;
import net.minecraft.class_4844;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
/* loaded from: classes-only.jar:net/anubis/client/account/Accounts.class */
public final class Accounts {
    private static final int CONFIG_VERSION = 1;
    private volatile String activeId;
    private volatile boolean busy;
    private volatile Thread microsoftSignIn;
    private volatile boolean loaded;
    private boolean readOnly;
    private static final Logger LOGGER = LoggerFactory.getLogger("Anubis");
    private static final Accounts INSTANCE = new Accounts();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Pattern VALID_NAME = Pattern.compile("[A-Za-z0-9_]{1,16}");
    private final ExecutorService worker = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "Anubis Accounts");
        thread.setDaemon(true);
        return thread;
    });
    private final GameSession session = new GameSession();
    private final List<Account> accounts = new ArrayList();
    private volatile List<Account> snapshot = List.of();
    private volatile String status = "";

    @Environment(EnvType.CLIENT)
    /* loaded from: classes-only.jar:net/anubis/client/account/Accounts$SignIn.class */
    private interface SignIn {
        Credentials run() throws InterruptedException;
    }

    private Accounts() {
    }

    public static Accounts get() {
        return INSTANCE;
    }

    public List<Account> list() throws IOException {
        ensureLoaded();
        return this.snapshot;
    }

    public Account active() throws IOException {
        String sessionUuid = class_310.method_1551().method_1548().method_44717().toString();
        List<Account> all = list();
        String lastSwitched = this.activeId;
        for (Account account : all) {
            if (account.id().equals(lastSwitched) && account.uuid().equals(sessionUuid)) {
                return account;
            }
        }
        for (Account account2 : all) {
            if (account2.uuid().equals(sessionUuid)) {
                return account2;
            }
        }
        return null;
    }

    public CompletableFuture<Account> addCracked(String username) {
        String name = username == null ? "" : username.trim();
        if (!VALID_NAME.matcher(name).matches()) {
            return failed("A cracked name is 1-16 letters, digits or underscores");
        }
        return signIn(AccountType.CRACKED, null, "Logging in as " + name + "...", () -> {
            return offline(name);
        });
    }

    public CompletableFuture<Account> addSession(String token) {
        String accessToken = token == null ? "" : token.trim();
        return accessToken.isEmpty() ? failed("Paste an access token first") : signIn(AccountType.SESSION, null, "Checking the session token...", () -> {
            return SessionLogin.signIn(accessToken);
        });
    }

    public CompletableFuture<Account> addMicrosoft() {
        return signIn(AccountType.MICROSOFT, null, "Opening Microsoft sign-in...", this::microsoftBrowserSignIn);
    }

    public void cancelMicrosoft() {
        Thread signIn = this.microsoftSignIn;
        if (signIn != null) {
            signIn.interrupt();
        }
    }

    public CompletableFuture<Void> login(Account account) throws IOException {
        Account saved = find(account);
        if (saved == null) {
            return failed("That account is no longer saved");
        }
        String who = saved.name().isEmpty() ? saved.type().label() + " account" : saved.name();
        return signIn(saved.type(), saved.id(), "Logging in as " + who + "...", () -> {
            switch (saved.type()) {
                case CRACKED:
                    return offline(saved.name());
                case SESSION:
                    return SessionLogin.signIn(saved.secret());
                case MICROSOFT:
                    return MicrosoftLogin.minecraft(MicrosoftLogin.refresh(saved.secret()));
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }).thenAccept(signedIn -> {
        });
    }

    public synchronized void remove(Account account) throws IOException {
        int index = indexOf(account);
        if (index < 0) {
            return;
        }
        this.accounts.remove(index);
        publish();
        save();
    }

    public boolean busy() {
        return this.busy;
    }

    public String status() {
        return this.status;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Credentials offline(String name) {
        return new Credentials(name, class_4844.method_43344(name), "", "");
    }

    private Credentials microsoftBrowserSignIn() throws InterruptedException {
        this.microsoftSignIn = Thread.currentThread();
        try {
            MicrosoftLogin.DeviceCode code = MicrosoftLogin.requestDeviceCode();
            this.status = "Waiting for Microsoft sign-in in your browser... (code " + code.userCode() + " at " + code.verificationUri().replaceFirst("^https?://", "") + ")";
            class_156.method_668().method_670(code.directUri());
            MicrosoftLogin.MsaToken token = MicrosoftLogin.awaitApproval(code);
            this.status = "Signing in to Xbox Live...";
            Credentials credentialsMinecraft = MicrosoftLogin.minecraft(token);
            this.microsoftSignIn = null;
            return credentialsMinecraft;
        } catch (Throwable th) {
            this.microsoftSignIn = null;
            throw th;
        }
    }

    private CompletableFuture<Account> signIn(AccountType type, String savedId, String progress, SignIn blocking) throws IOException {
        synchronized (this) {
            if (this.busy) {
                return CompletableFuture.failedFuture(new AccountException("Another account action is still running"));
            }
            if (inGame()) {
                return failed("Leave the world before switching accounts");
            }
            this.busy = true;
            ensureLoaded();
            this.status = progress;
            CompletableFuture<Account> result = new CompletableFuture<>();
            this.worker.execute(() -> {
                Thread.interrupted();
                try {
                    Credentials credentials = blocking.run();
                    GameSession.Prepared prepared = this.session.prepare(type, credentials);
                    class_310.method_1551().execute(() -> {
                        finish(type, savedId, credentials, prepared, result);
                    });
                } catch (InterruptedException e) {
                    settleLater(result, null, new AccountException(type == AccountType.MICROSOFT ? "Microsoft sign-in cancelled" : "Login cancelled"));
                } catch (AccountException e2) {
                    LOGGER.warn("[Anubis] {} login failed: {}", type.label(), e2.getMessage());
                    settleLater(result, null, e2);
                } catch (RuntimeException e3) {
                    LOGGER.warn("[Anubis] {} login failed", type.label(), e3);
                    settleLater(result, null, new AccountException("Login failed: " + e3.getMessage(), e3));
                }
            });
            return result;
        }
    }

    private void finish(AccountType type, String savedId, Credentials credentials, GameSession.Prepared prepared, CompletableFuture<Account> result) {
        Account account;
        synchronized (this) {
            account = remember(type, savedId, credentials);
        }
        if (inGame()) {
            settle(result, null, new AccountException("Joined a world before the switch finished - leave it and log in again"));
            return;
        }
        try {
            this.session.apply(prepared);
            this.activeId = account.id();
            LOGGER.info("[Anubis] Switched session to {}", account);
            this.status = "Logged in as " + credentials.name();
            settle(result, account, null);
        } catch (RuntimeException e) {
            LOGGER.error("[Anubis] Switching the session to {} failed", account, e);
            settle(result, null, new AccountException("Couldn't switch the game to " + credentials.name() + ": " + e.getMessage(), e));
        }
    }

    private void settleLater(CompletableFuture<Account> result, Account account, AccountException error) {
        class_310.method_1551().execute(() -> {
            settle(result, account, error);
        });
    }

    private void settle(CompletableFuture<Account> result, Account account, AccountException error) {
        if (error != null) {
            this.status = error.getMessage();
        }
        this.busy = false;
        if (error == null) {
            result.complete(account);
        } else {
            result.completeExceptionally(error);
        }
    }

    private Account remember(AccountType type, String savedId, Credentials credentials) throws IOException {
        Account account;
        boolean zEquals;
        String uuid = credentials.uuid().toString();
        int index = -1;
        int i = 0;
        while (true) {
            if (i >= this.accounts.size()) {
                break;
            }
            Account existing = this.accounts.get(i);
            if (savedId != null) {
                zEquals = existing.id().equals(savedId);
            } else {
                zEquals = existing.type() == type && (type != AccountType.CRACKED ? existing.uuid().equals(uuid) : existing.name().equalsIgnoreCase(credentials.name()));
            }
            boolean same = zEquals;
            if (!same) {
                i++;
            } else {
                index = i;
                break;
            }
        }
        if (index < 0 && savedId != null) {
            return new Account(savedId, type, credentials.name(), uuid, credentials.secret());
        }
        if (index >= 0) {
            account = this.accounts.get(index).withLogin(credentials.name(), uuid, credentials.secret());
        } else {
            account = new Account(UUID.randomUUID().toString(), type, credentials.name(), uuid, credentials.secret());
        }
        Account account2 = account;
        if (index >= 0) {
            this.accounts.set(index, account2);
        } else {
            this.accounts.add(account2);
        }
        publish();
        save();
        return account2;
    }

    private static boolean inGame() {
        class_310 mc = class_310.method_1551();
        return (mc.field_1687 == null && mc.method_1562() == null) ? false : true;
    }

    private <T> CompletableFuture<T> failed(String message) {
        this.status = message;
        return CompletableFuture.failedFuture(new AccountException(message));
    }

    private synchronized Account find(Account account) throws IOException {
        int index = indexOf(account);
        if (index < 0) {
            return null;
        }
        return this.accounts.get(index);
    }

    private int indexOf(Account account) throws IOException {
        ensureLoaded();
        if (account == null) {
            return -1;
        }
        for (int i = 0; i < this.accounts.size(); i++) {
            if (this.accounts.get(i).id().equals(account.id())) {
                return i;
            }
        }
        return -1;
    }

    private void publish() {
        this.snapshot = List.copyOf(this.accounts);
    }

    private void ensureLoaded() throws IOException {
        if (!this.loaded) {
            load();
        }
    }

    private synchronized void load() throws IOException {
        Reader reader;
        JsonObject root;
        int version;
        Account account;
        if (this.loaded) {
            return;
        }
        this.loaded = true;
        Path path = configPath();
        if (Files.isRegularFile(path, new LinkOption[0])) {
            try {
                reader = Files.newBufferedReader(path);
                try {
                    root = JsonParser.parseReader(reader).getAsJsonObject();
                    version = root.has("version") ? root.get("version").getAsInt() : 0;
                } catch (Throwable th) {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (IOException | RuntimeException e) {
                LOGGER.warn("[Anubis] Failed to load the account list; preserving the file", e);
                this.accounts.clear();
                this.readOnly = true;
            }
            if (version > 1) {
                this.readOnly = true;
                LOGGER.warn("[Anubis] Account list version {} is newer than supported {}; skipping load", Integer.valueOf(version), 1);
                if (reader != null) {
                    reader.close();
                    return;
                }
                return;
            }
            if (!root.has("accounts") || !root.get("accounts").isJsonArray()) {
                if (reader != null) {
                    reader.close();
                    return;
                }
                return;
            }
            Iterator it = root.getAsJsonArray("accounts").iterator();
            while (it.hasNext()) {
                JsonElement element = (JsonElement) it.next();
                if (element.isJsonObject() && (account = read(element.getAsJsonObject())) != null) {
                    this.accounts.add(account);
                }
            }
            if (reader != null) {
                reader.close();
            }
            publish();
        }
    }

    private static Account read(JsonObject json) {
        String typeName = AuthHttp.text(json, "type");
        AccountType type = null;
        for (AccountType candidate : AccountType.values()) {
            if (candidate.name().equalsIgnoreCase(typeName)) {
                type = candidate;
            }
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
        if (type == AccountType.CRACKED) {
            if (!VALID_NAME.matcher(name).matches()) {
                return null;
            }
        } else if (token == null || token.isEmpty()) {
            return null;
        }
        if (type == AccountType.CRACKED) {
            uuid = class_4844.method_43344(name).toString();
        }
        return new Account((id == null || id.isEmpty()) ? UUID.randomUUID().toString() : id, type, name, uuid == null ? "" : uuid, token == null ? "" : token);
    }

    private void save() throws IOException {
        if (this.readOnly) {
            return;
        }
        JsonObject root = new JsonObject();
        root.addProperty("version", 1);
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
            list.add(entry);
        }
        root.add("accounts", list);
        Path path = configPath();
        try {
            Files.createDirectories(path.getParent(), new FileAttribute[0]);
            Path tmp = path.resolveSibling(path.getFileName().toString() + ".tmp");
            Writer writer = Files.newBufferedWriter(tmp, new OpenOption[0]);
            try {
                GSON.toJson(root, writer);
                if (writer != null) {
                    writer.close();
                }
                try {
                    Files.move(tmp, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
                } catch (IOException e) {
                    Files.move(tmp, path, StandardCopyOption.REPLACE_EXISTING);
                }
            } finally {
            }
        } catch (IOException e2) {
            LOGGER.warn("[Anubis] Failed to save the account list", e2);
        }
    }

    private static Path configPath() {
        return FabricLoader.getInstance().getConfigDir().resolve(AddonManager.ENTRYPOINT).resolve("accounts.json");
    }
}
