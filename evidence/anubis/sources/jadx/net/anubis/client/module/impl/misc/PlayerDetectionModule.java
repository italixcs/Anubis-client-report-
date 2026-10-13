package net.anubis.client.module.impl.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import net.anubis.client.chat.ModuleChat;
import net.anubis.client.config.ModuleConfigStore;
import net.anubis.client.event.Stage;
import net.anubis.client.event.impl.TickEvent;
import net.anubis.client.module.ClientModule;
import net.anubis.client.module.ModuleCategory;
import net.anubis.client.module.ModuleRegistry;
import net.anubis.client.module.setting.BooleanSetting;
import net.anubis.client.module.setting.ModeSetting;
import net.anubis.client.module.setting.StringSetting;
import net.anubis.client.webhook.WebhookConfig;
import net.anubis.client.webhook.WebhookEmbed;
import net.anubis.client.webhook.WebhookPayloadBuilder;
import net.anubis.client.webhook.WebhookService;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2535;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_370;
import net.minecraft.class_634;
import net.minecraft.class_638;
import net.minecraft.class_642;
import net.minecraft.class_742;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
/* loaded from: classes-only.jar:net/anubis/client/module/impl/misc/PlayerDetectionModule.class */
public final class PlayerDetectionModule extends ClientModule {
    private static final long DISCONNECT_DELAY_MS = 500;
    private static final String WEBHOOK_TITLE = "�� Player Detection Alert";
    private static final int WEBHOOK_COLOR = 15158332;
    private final StringSetting modulesToToggle;
    private final BooleanSetting enableDisconnect;
    private final ModeSetting notificationMode;
    private final BooleanSetting toggleOnPlayer;
    private final StringSetting userWhitelist;
    private final BooleanSetting enableWebhook;
    private final StringSetting webhookUrl;
    private final BooleanSetting selfPing;
    private final StringSetting discordId;
    private final BooleanSetting enablePanicPay;
    private final StringSetting panicPayTarget;
    private final StringSetting panicPayAmount;
    private final Set<String> detectedPlayers;
    private class_638 lastLevel;
    private String whitelistRaw;
    private Set<String> whitelist;
    private static final Logger LOGGER = LoggerFactory.getLogger("Anubis");
    private static final Set<String> PERMANENT_WHITELIST = Set.of("freecamera");
    private static final class_370.class_9037 TOAST_ID = new class_370.class_9037();
    private static final ExecutorService WEBHOOK_EXECUTOR = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "Anubis-PlayerDetection-Webhook");
        thread.setDaemon(true);
        return thread;
    });

    @Environment(EnvType.CLIENT)
    /* loaded from: classes-only.jar:net/anubis/client/module/impl/misc/PlayerDetectionModule$WebhookHolder.class */
    private static final class WebhookHolder {
        static final WebhookService SERVICE = new WebhookService();

        private WebhookHolder() {
        }
    }

    public PlayerDetectionModule() {
        super("player-detection", "Player Detection", "Detects when players are in the world.", ModuleCategory.DONUT);
        this.modulesToToggle = (StringSetting) addSetting("modules-to-toggle", new StringSetting("Modules To Toggle", ""));
        this.enableDisconnect = (BooleanSetting) addSetting("disconnect", new BooleanSetting("Disconnect", true));
        this.notificationMode = (ModeSetting) addSetting("notification-mode", new ModeSetting("Notification Mode", "Both", "Chat", "Toast", "Both"));
        this.toggleOnPlayer = (BooleanSetting) addSetting("toggle-on-detect", new BooleanSetting("Toggle On Detect", true));
        this.userWhitelist = (StringSetting) addSetting("user-whitelist", new StringSetting("User Whitelist", ""));
        this.enableWebhook = (BooleanSetting) addSetting("webhook", new BooleanSetting("Webhook", false));
        this.webhookUrl = (StringSetting) addSetting("webhook-url", new StringSetting("Webhook URL", ""));
        this.selfPing = (BooleanSetting) addSetting("self-ping", new BooleanSetting("Self Ping", false));
        this.discordId = (StringSetting) addSetting("discord-id", new StringSetting("Discord ID", ""));
        this.enablePanicPay = (BooleanSetting) addSetting("enable-panic-pay", new BooleanSetting("Enable Panic Pay", false));
        this.panicPayTarget = (StringSetting) addSetting("target-player", new StringSetting("Target Player", ""));
        this.panicPayAmount = (StringSetting) addSetting("amount", new StringSetting("Amount", ""));
        this.detectedPlayers = new HashSet();
        this.whitelist = Set.of();
        usesNotifications();
        StringSetting stringSetting = this.webhookUrl;
        BooleanSetting booleanSetting = this.enableWebhook;
        Objects.requireNonNull(booleanSetting);
        stringSetting.visibleWhen(booleanSetting::get);
        BooleanSetting booleanSetting2 = this.selfPing;
        BooleanSetting booleanSetting3 = this.enableWebhook;
        Objects.requireNonNull(booleanSetting3);
        booleanSetting2.visibleWhen(booleanSetting3::get);
        this.discordId.visibleWhen(() -> {
            return this.enableWebhook.get() && this.selfPing.get();
        });
        StringSetting stringSetting2 = this.panicPayTarget;
        BooleanSetting booleanSetting4 = this.enablePanicPay;
        Objects.requireNonNull(booleanSetting4);
        stringSetting2.visibleWhen(booleanSetting4::get);
        StringSetting stringSetting3 = this.panicPayAmount;
        BooleanSetting booleanSetting5 = this.enablePanicPay;
        Objects.requireNonNull(booleanSetting5);
        stringSetting3.visibleWhen(booleanSetting5::get);
        this.webhookUrl.privateValue();
        this.discordId.privateValue();
        this.enablePanicPay.privateValue();
        this.panicPayTarget.privateValue();
        this.panicPayAmount.privateValue();
        this.userWhitelist.privateValue();
        this.userWhitelist.getImString().inputData.isResizable = true;
        this.modulesToToggle.getImString().inputData.isResizable = true;
    }

    @Override // net.anubis.client.module.ClientModule
    protected void onEnable() {
        this.detectedPlayers.clear();
        this.lastLevel = null;
        on(TickEvent.TYPE, Stage.POST, this::onTick);
    }

    @Override // net.anubis.client.module.ClientModule
    protected void onDisable() {
        this.detectedPlayers.clear();
        this.lastLevel = null;
    }

    private void onTick(TickEvent event) {
        String name;
        class_310 mc = class_310.method_1551();
        class_742 class_742Var = mc.field_1724;
        class_638 level = mc.field_1687;
        if (class_742Var == null || level == null) {
            this.detectedPlayers.clear();
            this.lastLevel = null;
            return;
        }
        if (level != this.lastLevel) {
            this.detectedPlayers.clear();
            this.lastLevel = level;
        }
        String selfName = class_742Var.method_7334().name();
        UUID selfId = class_742Var.method_5667();
        Set<String> ignored = whitelist();
        Set<String> current = null;
        for (class_742 player : level.method_18456()) {
            if (player != class_742Var && !selfId.equals(player.method_5667()) && (name = player.method_7334().name()) != null && !name.equals(selfName)) {
                String key = name.toLowerCase(Locale.ROOT);
                if (!PERMANENT_WHITELIST.contains(key) && !ignored.contains(key)) {
                    if (current == null) {
                        current = new LinkedHashSet<>();
                    }
                    current.add(name);
                }
            }
        }
        if (current == null) {
            this.detectedPlayers.clear();
        } else if (!current.equals(this.detectedPlayers)) {
            this.detectedPlayers.clear();
            this.detectedPlayers.addAll(current);
            handlePlayerDetection(current);
        }
    }

    private void handlePlayerDetection(Set<String> players) {
        class_310 mc;
        mc = class_310.method_1551();
        switch (this.notificationMode.get()) {
            case "Chat":
                announceDetection(players);
                break;
            case "Toast":
                showToast(mc);
                break;
            default:
                announceDetection(players);
                showToast(mc);
                break;
        }
        for (ClientModule module : resolveModulesToToggle()) {
            module.toggle();
        }
        if (this.enablePanicPay.get()) {
            panicPay(mc);
        }
        if (this.enableWebhook.get()) {
            sendWebhookNotification(mc, players);
        }
        if (this.toggleOnPlayer.get()) {
            toggle();
        }
        if (this.enableDisconnect.get()) {
            scheduleDisconnect(mc, players);
        }
    }

    private void announceDetection(Set<String> players) {
        ModuleChat.send(this, names(ModuleChat.body().danger(players.size() == 1 ? "Player detected" : "Players detected").text(": "), players));
    }

    private static ModuleChat.Body names(ModuleChat.Body body, Collection<String> players) {
        boolean first = true;
        for (String player : players) {
            if (!first) {
                body.text(", ");
            }
            body.name(player);
            first = false;
        }
        return body;
    }

    private void showToast(class_310 mc) {
        class_370.method_1990(mc.method_1566(), TOAST_ID, class_2561.method_43470(name()), class_2561.method_43470("Player Detected!"));
    }

    private List<ClientModule> resolveModulesToToggle() {
        String raw = this.modulesToToggle.get();
        if (raw.isBlank()) {
            return List.of();
        }
        Set<ClientModule> resolved = new LinkedHashSet<>();
        for (String entry : raw.split(",")) {
            String key = entry.trim();
            if (!key.isEmpty()) {
                String renamed = ModuleConfigStore.renamedModuleId(key);
                if (renamed != null) {
                    key = renamed;
                }
                for (ClientModule module : ModuleRegistry.all()) {
                    if (module.id().equalsIgnoreCase(key) || module.name().equalsIgnoreCase(key)) {
                        if (module != this) {
                            resolved.add(module);
                        }
                    }
                }
            }
        }
        return new ArrayList(resolved);
    }

    private Set<String> whitelist() {
        String raw = this.userWhitelist.get();
        if (!raw.equals(this.whitelistRaw)) {
            Set<String> parsed = new HashSet<>();
            for (String entry : raw.split(",")) {
                String name = entry.trim();
                if (!name.isEmpty()) {
                    parsed.add(name.toLowerCase(Locale.ROOT));
                }
            }
            this.whitelist = parsed;
            this.whitelistRaw = raw;
        }
        return this.whitelist;
    }

    private void panicPay(class_310 mc) {
        String target = this.panicPayTarget.get().trim();
        String amount = this.panicPayAmount.get().trim();
        if (target.isEmpty()) {
            ModuleChat.send(this, ModuleChat.body().text("Panic pay target is ").warn("not set"));
            return;
        }
        if (amount.isEmpty()) {
            ModuleChat.send(this, ModuleChat.body().text("Panic pay amount is ").warn("not set"));
            return;
        }
        class_634 connection = mc.method_1562();
        if (connection == null) {
            return;
        }
        String command = String.format("/pay %s %s", target, amount);
        if (mc.field_1705 != null) {
            mc.field_1705.method_1743().method_1803(command);
        }
        connection.method_45730(command.substring(1));
        ModuleChat.send(this, ModuleChat.body().text("Panic pay ").good("sent").text(": ").value(amount).text(" to ").name(target));
    }

    private void sendWebhookNotification(class_310 mc, Set<String> players) {
        String url = this.webhookUrl.get().trim();
        if (url.isEmpty()) {
            ModuleChat.send(this, ModuleChat.body().text("Webhook URL is ").warn("not set"));
            return;
        }
        if (!isValidWebhookUrl(url)) {
            ModuleChat.send(this, ModuleChat.body().text("Webhook URL is ").danger("invalid"));
            return;
        }
        class_642 server = mc.method_1558();
        String serverName = (server == null || server.field_3752 == null) ? "Unknown Server" : server.field_3752;
        String id = this.discordId.get().trim();
        String ping = (!this.selfPing.get() || id.isEmpty()) ? "" : "<@" + id + ">";
        String target = this.panicPayTarget.get().trim();
        String amount = this.panicPayAmount.get().trim();
        long unixSeconds = System.currentTimeMillis() / 1000;
        StringBuilder description = new StringBuilder("Player(s) detected on server!\n\n").append("**Players:** ").append(escapeMarkdown(String.join(", ", players))).append('\n').append("**Server:** ").append(escapeMarkdown(serverName)).append('\n').append("**Time:** <t:").append(unixSeconds).append(":R>");
        if (this.enablePanicPay.get() && !target.isEmpty() && !amount.isEmpty()) {
            description.append("\n**Panic Pay:** Activated - Target: ").append(escapeMarkdown(target)).append(", Amount: ").append(escapeMarkdown(amount));
        }
        WebhookPayloadBuilder payload = new WebhookPayloadBuilder().content(ping).addEmbed(new WebhookEmbed(WEBHOOK_TITLE, description.toString(), Integer.valueOf(WEBHOOK_COLOR), null, null, "Sent by Anubis", null));
        WebhookConfig config = new WebhookConfig(url);
        WEBHOOK_EXECUTOR.execute(() -> {
            boolean sent;
            try {
                sent = WebhookHolder.SERVICE.send(config, payload);
            } catch (LinkageError | RuntimeException failure) {
                LOGGER.warn("[Anubis] Player Detection webhook failed ({})", failure.getClass().getSimpleName());
                sent = false;
            }
            ModuleChat.send(this, sent ? ModuleChat.body().text("Webhook notification ").good("sent") : ModuleChat.body().text("Webhook notification ").danger("failed").text(" (see the log for details)"));
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0026, code lost:
    
        if ("http".equalsIgnoreCase(r0) != false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static boolean isValidWebhookUrl(java.lang.String r4) {
        /*
            java.net.URI r0 = new java.net.URI     // Catch: java.net.URISyntaxException -> L2f
            r1 = r0
            r2 = r4
            r1.<init>(r2)     // Catch: java.net.URISyntaxException -> L2f
            r5 = r0
            r0 = r5
            java.lang.String r0 = r0.getScheme()     // Catch: java.net.URISyntaxException -> L2f
            r6 = r0
            r0 = r5
            java.lang.String r0 = r0.getHost()     // Catch: java.net.URISyntaxException -> L2f
            if (r0 == 0) goto L2d
            java.lang.String r0 = "https"
            r1 = r6
            boolean r0 = r0.equalsIgnoreCase(r1)     // Catch: java.net.URISyntaxException -> L2f
            if (r0 != 0) goto L29
            java.lang.String r0 = "http"
            r1 = r6
            boolean r0 = r0.equalsIgnoreCase(r1)     // Catch: java.net.URISyntaxException -> L2f
            if (r0 == 0) goto L2d
        L29:
            r0 = 1
            goto L2e
        L2d:
            r0 = 0
        L2e:
            return r0
        L2f:
            r5 = move-exception
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.anubis.client.module.impl.misc.PlayerDetectionModule.isValidWebhookUrl(java.lang.String):boolean");
    }

    private static String escapeMarkdown(String text) {
        StringBuilder out = new StringBuilder(text.length() + 8);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\\' || c == '*' || c == '_' || c == '~' || c == '`' || c == '|') {
                out.append('\\');
            }
            out.append(c);
        }
        return out.toString();
    }

    private void scheduleDisconnect(class_310 mc, Set<String> players) {
        class_634 listener = mc.method_1562();
        if (listener == null) {
            return;
        }
        class_2535 session = listener.method_48296();
        CompletableFuture.delayedExecutor(DISCONNECT_DELAY_MS, TimeUnit.MILLISECONDS, mc).execute(() -> {
            disconnectFromServer(mc, session, players);
        });
    }

    private void disconnectFromServer(class_310 mc, class_2535 session, Set<String> players) {
        class_634 listener = mc.method_1562();
        if (mc.field_1687 == null || listener == null || listener.method_48296() != session) {
            return;
        }
        session.method_10747(class_2561.method_43470("Player(s) detected: " + String.join(", ", players)));
        ModuleChat.send(this, names(ModuleChat.body().danger("Disconnected").text(" from the server (").text(players.size() == 1 ? "player" : "players").text(" detected: "), players).text(")"));
    }
}
