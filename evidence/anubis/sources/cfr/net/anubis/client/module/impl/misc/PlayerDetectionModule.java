/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_2535
 *  net.minecraft.class_2561
 *  net.minecraft.class_310
 *  net.minecraft.class_370
 *  net.minecraft.class_370$class_9037
 *  net.minecraft.class_374
 *  net.minecraft.class_634
 *  net.minecraft.class_638
 *  net.minecraft.class_642
 *  net.minecraft.class_742
 *  net.minecraft.class_746
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package net.anubis.client.module.impl.misc;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
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
import net.minecraft.class_374;
import net.minecraft.class_634;
import net.minecraft.class_638;
import net.minecraft.class_642;
import net.minecraft.class_742;
import net.minecraft.class_746;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(value=EnvType.CLIENT)
public final class PlayerDetectionModule
extends ClientModule {
    private static final Logger LOGGER = LoggerFactory.getLogger((String)"Anubis");
    private static final Set<String> PERMANENT_WHITELIST = Set.of("freecamera");
    private static final long DISCONNECT_DELAY_MS = 500L;
    private static final String WEBHOOK_TITLE = "\ud83d\udea8 Player Detection Alert";
    private static final int WEBHOOK_COLOR = 15158332;
    private static final class_370.class_9037 TOAST_ID = new class_370.class_9037();
    private static final ExecutorService WEBHOOK_EXECUTOR = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "Anubis-PlayerDetection-Webhook");
        thread.setDaemon(true);
        return thread;
    });
    private final StringSetting modulesToToggle = this.addSetting("modules-to-toggle", new StringSetting("Modules To Toggle", ""));
    private final BooleanSetting enableDisconnect = this.addSetting("disconnect", new BooleanSetting("Disconnect", true));
    private final ModeSetting notificationMode = this.addSetting("notification-mode", new ModeSetting("Notification Mode", "Both", "Chat", "Toast", "Both"));
    private final BooleanSetting toggleOnPlayer = this.addSetting("toggle-on-detect", new BooleanSetting("Toggle On Detect", true));
    private final StringSetting userWhitelist = this.addSetting("user-whitelist", new StringSetting("User Whitelist", ""));
    private final BooleanSetting enableWebhook = this.addSetting("webhook", new BooleanSetting("Webhook", false));
    private final StringSetting webhookUrl = this.addSetting("webhook-url", new StringSetting("Webhook URL", ""));
    private final BooleanSetting selfPing = this.addSetting("self-ping", new BooleanSetting("Self Ping", false));
    private final StringSetting discordId = this.addSetting("discord-id", new StringSetting("Discord ID", ""));
    private final BooleanSetting enablePanicPay = this.addSetting("enable-panic-pay", new BooleanSetting("Enable Panic Pay", false));
    private final StringSetting panicPayTarget = this.addSetting("target-player", new StringSetting("Target Player", ""));
    private final StringSetting panicPayAmount = this.addSetting("amount", new StringSetting("Amount", ""));
    private final Set<String> detectedPlayers = new HashSet<String>();
    private class_638 lastLevel;
    private String whitelistRaw;
    private Set<String> whitelist = Set.of();

    public PlayerDetectionModule() {
        super("player-detection", "Player Detection", "Detects when players are in the world.", ModuleCategory.DONUT);
        this.usesNotifications();
        this.webhookUrl.visibleWhen(this.enableWebhook::get);
        this.selfPing.visibleWhen(this.enableWebhook::get);
        this.discordId.visibleWhen(() -> this.enableWebhook.get() && this.selfPing.get());
        this.panicPayTarget.visibleWhen(this.enablePanicPay::get);
        this.panicPayAmount.visibleWhen(this.enablePanicPay::get);
        this.webhookUrl.privateValue();
        this.discordId.privateValue();
        this.enablePanicPay.privateValue();
        this.panicPayTarget.privateValue();
        this.panicPayAmount.privateValue();
        this.userWhitelist.privateValue();
        this.userWhitelist.getImString().inputData.isResizable = true;
        this.modulesToToggle.getImString().inputData.isResizable = true;
    }

    @Override
    protected void onEnable() {
        this.detectedPlayers.clear();
        this.lastLevel = null;
        this.on(TickEvent.TYPE, Stage.POST, this::onTick);
    }

    @Override
    protected void onDisable() {
        this.detectedPlayers.clear();
        this.lastLevel = null;
    }

    private void onTick(TickEvent event) {
        class_310 mc = class_310.method_1551();
        class_746 self = mc.field_1724;
        class_638 level = mc.field_1687;
        if (self == null || level == null) {
            this.detectedPlayers.clear();
            this.lastLevel = null;
            return;
        }
        if (level != this.lastLevel) {
            this.detectedPlayers.clear();
            this.lastLevel = level;
        }
        String selfName = self.method_7334().name();
        UUID selfId = self.method_5667();
        Set<String> ignored = this.whitelist();
        LinkedHashSet<String> current = null;
        for (class_742 player : level.method_18456()) {
            String key;
            String name;
            if (player == self || selfId.equals(player.method_5667()) || (name = player.method_7334().name()) == null || name.equals(selfName) || PERMANENT_WHITELIST.contains(key = name.toLowerCase(Locale.ROOT)) || ignored.contains(key)) continue;
            if (current == null) {
                current = new LinkedHashSet<String>();
            }
            current.add(name);
        }
        if (current == null) {
            this.detectedPlayers.clear();
        } else if (!current.equals(this.detectedPlayers)) {
            this.detectedPlayers.clear();
            this.detectedPlayers.addAll((Collection<String>)current);
            this.handlePlayerDetection((Set<String>)current);
        }
    }

    private void handlePlayerDetection(Set<String> players) {
        class_310 mc = class_310.method_1551();
        switch (this.notificationMode.get()) {
            case "Chat": {
                this.announceDetection(players);
                break;
            }
            case "Toast": {
                this.showToast(mc);
                break;
            }
            default: {
                this.announceDetection(players);
                this.showToast(mc);
            }
        }
        for (ClientModule module : this.resolveModulesToToggle()) {
            module.toggle();
        }
        if (this.enablePanicPay.get()) {
            this.panicPay(mc);
        }
        if (this.enableWebhook.get()) {
            this.sendWebhookNotification(mc, players);
        }
        if (this.toggleOnPlayer.get()) {
            this.toggle();
        }
        if (this.enableDisconnect.get()) {
            this.scheduleDisconnect(mc, players);
        }
    }

    private void announceDetection(Set<String> players) {
        ModuleChat.send((ClientModule)this, PlayerDetectionModule.names(ModuleChat.body().danger(players.size() == 1 ? "Player detected" : "Players detected").text(": "), players));
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
        class_370.method_1990((class_374)mc.method_1566(), (class_370.class_9037)TOAST_ID, (class_2561)class_2561.method_43470((String)this.name()), (class_2561)class_2561.method_43470((String)"Player Detected!"));
    }

    private List<ClientModule> resolveModulesToToggle() {
        String raw = this.modulesToToggle.get();
        if (raw.isBlank()) {
            return List.of();
        }
        LinkedHashSet<ClientModule> resolved = new LinkedHashSet<ClientModule>();
        block0: for (String entry : raw.split(",")) {
            String key = entry.trim();
            if (key.isEmpty()) continue;
            String renamed = ModuleConfigStore.renamedModuleId(key);
            if (renamed != null) {
                key = renamed;
            }
            for (ClientModule module : ModuleRegistry.all()) {
                if (!module.id().equalsIgnoreCase(key) && !module.name().equalsIgnoreCase(key)) continue;
                if (module == this) continue block0;
                resolved.add(module);
                continue block0;
            }
        }
        return new ArrayList<ClientModule>(resolved);
    }

    private Set<String> whitelist() {
        String raw = this.userWhitelist.get();
        if (!raw.equals(this.whitelistRaw)) {
            HashSet<String> parsed = new HashSet<String>();
            for (String entry : raw.split(",")) {
                String name = entry.trim();
                if (name.isEmpty()) continue;
                parsed.add(name.toLowerCase(Locale.ROOT));
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
            ModuleChat.send((ClientModule)this, ModuleChat.body().text("Panic pay target is ").warn("not set"));
            return;
        }
        if (amount.isEmpty()) {
            ModuleChat.send((ClientModule)this, ModuleChat.body().text("Panic pay amount is ").warn("not set"));
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
        ModuleChat.send((ClientModule)this, ModuleChat.body().text("Panic pay ").good("sent").text(": ").value(amount).text(" to ").name(target));
    }

    private void sendWebhookNotification(class_310 mc, Set<String> players) {
        String url = this.webhookUrl.get().trim();
        if (url.isEmpty()) {
            ModuleChat.send((ClientModule)this, ModuleChat.body().text("Webhook URL is ").warn("not set"));
            return;
        }
        if (!PlayerDetectionModule.isValidWebhookUrl(url)) {
            ModuleChat.send((ClientModule)this, ModuleChat.body().text("Webhook URL is ").danger("invalid"));
            return;
        }
        class_642 server = mc.method_1558();
        String serverName = server != null && server.field_3752 != null ? server.field_3752 : "Unknown Server";
        String id = this.discordId.get().trim();
        String ping = this.selfPing.get() && !id.isEmpty() ? "<@" + id + ">" : "";
        String target = this.panicPayTarget.get().trim();
        String amount = this.panicPayAmount.get().trim();
        long unixSeconds = System.currentTimeMillis() / 1000L;
        StringBuilder description = new StringBuilder("Player(s) detected on server!\n\n").append("**Players:** ").append(PlayerDetectionModule.escapeMarkdown(String.join((CharSequence)", ", players))).append('\n').append("**Server:** ").append(PlayerDetectionModule.escapeMarkdown(serverName)).append('\n').append("**Time:** <t:").append(unixSeconds).append(":R>");
        if (this.enablePanicPay.get() && !target.isEmpty() && !amount.isEmpty()) {
            description.append("\n**Panic Pay:** Activated - Target: ").append(PlayerDetectionModule.escapeMarkdown(target)).append(", Amount: ").append(PlayerDetectionModule.escapeMarkdown(amount));
        }
        WebhookPayloadBuilder payload = new WebhookPayloadBuilder().content(ping).addEmbed(new WebhookEmbed(WEBHOOK_TITLE, description.toString(), 15158332, null, null, "Sent by Anubis", null));
        WebhookConfig config = new WebhookConfig(url);
        WEBHOOK_EXECUTOR.execute(() -> {
            boolean sent;
            try {
                sent = WebhookHolder.SERVICE.send(config, payload);
            }
            catch (LinkageError | RuntimeException failure) {
                LOGGER.warn("[Anubis] Player Detection webhook failed ({})", (Object)failure.getClass().getSimpleName());
                sent = false;
            }
            ModuleChat.send((ClientModule)this, sent ? ModuleChat.body().text("Webhook notification ").good("sent") : ModuleChat.body().text("Webhook notification ").danger("failed").text(" (see the log for details)"));
        });
    }

    private static boolean isValidWebhookUrl(String url) {
        try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            return uri.getHost() != null && ("https".equalsIgnoreCase(scheme) || "http".equalsIgnoreCase(scheme));
        }
        catch (URISyntaxException e) {
            return false;
        }
    }

    private static String escapeMarkdown(String text) {
        StringBuilder out = new StringBuilder(text.length() + 8);
        for (int i = 0; i < text.length(); ++i) {
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
        CompletableFuture.delayedExecutor(500L, TimeUnit.MILLISECONDS, (Executor)mc).execute(() -> this.disconnectFromServer(mc, session, players));
    }

    private void disconnectFromServer(class_310 mc, class_2535 session, Set<String> players) {
        class_634 listener = mc.method_1562();
        if (mc.field_1687 == null || listener == null || listener.method_48296() != session) {
            return;
        }
        session.method_10747((class_2561)class_2561.method_43470((String)("Player(s) detected: " + String.join((CharSequence)", ", players))));
        ModuleChat.send((ClientModule)this, PlayerDetectionModule.names(ModuleChat.body().danger("Disconnected").text(" from the server (").text(players.size() == 1 ? "player" : "players").text(" detected: "), players).text(")"));
    }

    @Environment(value=EnvType.CLIENT)
    private static final class WebhookHolder {
        static final WebhookService SERVICE = new WebhookService();

        private WebhookHolder() {
        }
    }
}

