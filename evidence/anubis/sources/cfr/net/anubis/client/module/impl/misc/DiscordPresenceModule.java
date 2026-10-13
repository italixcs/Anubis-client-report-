/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_2535
 *  net.minecraft.class_310
 *  net.minecraft.class_634
 *  net.minecraft.class_642
 *  net.minecraft.class_8732
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package net.anubis.client.module.impl.misc;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import net.anubis.client.discord.DiscordIpc;
import net.anubis.client.event.impl.TickEvent;
import net.anubis.client.module.ClientModule;
import net.anubis.client.module.ModuleCategory;
import net.anubis.client.module.setting.BooleanSetting;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2535;
import net.minecraft.class_310;
import net.minecraft.class_634;
import net.minecraft.class_642;
import net.minecraft.class_8732;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(value=EnvType.CLIENT)
public final class DiscordPresenceModule
extends ClientModule {
    private static final Logger LOGGER = LoggerFactory.getLogger((String)"Anubis");
    private static final String APPLICATION_ID = "1553391775840866314";
    private static final String LOGO_ASSET = "logo";
    private static final String LOGO_TEXT = "Anubis Client";
    private static final String BUTTON_LABEL = "Discord";
    private static final String IN_MENU = "In Main Menu";
    private static final String SINGLEPLAYER = "Singleplayer";
    private static final String MULTIPLAYER = "Multiplayer";
    private static final String LAN_WORLD = "LAN World";
    private static final String REALMS = "Realms";
    private static final long GAME_STARTED = ProcessHandle.current().info().startInstant().orElseGet(Instant::now).getEpochSecond();
    private static final long RETRY_MIN_MS = 5000L;
    private static final long RETRY_MAX_MS = 30000L;
    private static final long IDLE_POLL_MS = 1000L;
    private static final long MIN_UPDATE_GAP_MS = 4000L;
    private static final long KEEP_ALIVE_MS = 60000L;
    private final BooleanSetting showServerIp = this.addSetting("show-server-ip", new BooleanSetting("Show Server IP", true));
    private final BooleanSetting discordButton = this.addSetting("discord-button", new BooleanSetting("Discord Button", true));
    private volatile Card card;
    private Link link;
    private class_2535 session;
    private long joinedAt;
    private boolean loggedIdle;

    public DiscordPresenceModule() {
        super("discord-presence", "Discord Presence", "Shows Anubis Client and the server you are on in your Discord status.", ModuleCategory.CLIENT);
    }

    @Override
    public boolean enabledByDefault() {
        return true;
    }

    @Override
    protected void onEnable() {
        if (APPLICATION_ID.isBlank()) {
            if (!this.loggedIdle) {
                this.loggedIdle = true;
                LOGGER.info("[Anubis] Discord Presence: no Discord application id set (DiscordPresenceModule.APPLICATION_ID); idle");
            }
            return;
        }
        this.session = null;
        this.card = new Card(IN_MENU, GAME_STARTED, this.discordButton.get());
        this.link = new Link(this.link);
        this.refresh();
        this.on(TickEvent.TYPE, event -> this.refresh());
        this.link.start();
    }

    @Override
    protected void onDisable() {
        if (this.link != null) {
            this.link.stop();
        }
        this.session = null;
    }

    private void refresh() {
        long startedAt;
        String details;
        class_310 mc = class_310.method_1551();
        class_634 listener = mc.method_1562();
        if (listener != null) {
            if (listener.method_48296() != this.session) {
                this.session = listener.method_48296();
                this.joinedAt = System.currentTimeMillis() / 1000L;
            }
            details = this.place(mc, listener.method_45734());
            startedAt = this.joinedAt;
        } else {
            if (this.session != null && this.session.method_10758() && this.session.method_10744() instanceof class_8732) {
                return;
            }
            this.session = null;
            details = IN_MENU;
            startedAt = GAME_STARTED;
        }
        boolean button = this.discordButton.get();
        Card shown = this.card;
        if (startedAt != shown.startedAt() || button != shown.discordButton() || !details.equals(shown.details())) {
            this.card = new Card(details, startedAt, button);
            this.link.wake();
        }
    }

    private String place(class_310 mc, class_642 server) {
        if (mc.method_1542()) {
            return SINGLEPLAYER;
        }
        if (server == null) {
            return MULTIPLAYER;
        }
        if (server.method_52811()) {
            return REALMS;
        }
        if (server.method_2994()) {
            return LAN_WORLD;
        }
        return this.showServerIp.get() ? server.field_3761 : MULTIPLAYER;
    }

    private static JsonObject activity(Card card) {
        JsonObject activity = new JsonObject();
        activity.addProperty("details", card.details());
        JsonObject timestamps = new JsonObject();
        timestamps.addProperty("start", (Number)card.startedAt());
        activity.add("timestamps", (JsonElement)timestamps);
        JsonObject assets = new JsonObject();
        assets.addProperty("large_image", LOGO_ASSET);
        assets.addProperty("large_text", LOGO_TEXT);
        activity.add("assets", (JsonElement)assets);
        if (card.discordButton()) {
            JsonObject button = new JsonObject();
            button.addProperty("label", BUTTON_LABEL);
            button.addProperty("url", "https://discord.gg/anubis");
            JsonArray buttons = new JsonArray();
            buttons.add((JsonElement)button);
            activity.add("buttons", (JsonElement)buttons);
        }
        return activity;
    }

    @Environment(value=EnvType.CLIENT)
    private record Card(String details, long startedAt, boolean discordButton) {
    }

    @Environment(value=EnvType.CLIENT)
    private final class Link
    implements Runnable {
        private final Thread thread = new Thread((Runnable)this, "Anubis Discord Presence");
        private volatile boolean running = true;
        private Thread previous;
        private DiscordIpc ipc;
        private Card sent;
        private long sentAt;
        private long retryMs = 5000L;
        private String lastProblem;

        Link(Link previous) {
            this.previous = previous != null ? previous.thread : null;
            this.thread.setDaemon(true);
        }

        void start() {
            this.thread.start();
        }

        void wake() {
            LockSupport.unpark(this.thread);
        }

        void stop() {
            this.running = false;
            LockSupport.unpark(this.thread);
        }

        @Override
        public void run() {
            this.awaitPrevious();
            while (this.running) {
                block5: {
                    if (this.ipc == null && !this.connect()) {
                        this.pause(this.retryMs);
                        this.retryMs = Math.min(this.retryMs * 2L, 30000L);
                        continue;
                    }
                    try {
                        this.ipc.poll();
                        Card next = DiscordPresenceModule.this.card;
                        long since = System.currentTimeMillis() - this.sentAt;
                        if (this.sent != null && since < (next.equals(this.sent) ? 60000L : 4000L)) break block5;
                        this.send(next);
                    }
                    catch (IOException lost) {
                        this.report("lost Discord", lost);
                        this.hangUp();
                        continue;
                    }
                }
                this.pause(1000L);
            }
            if (this.ipc != null) {
                this.clear();
            }
        }

        private void awaitPrevious() {
            if (this.previous == null) {
                return;
            }
            try {
                this.previous.join();
            }
            catch (InterruptedException interrupted) {
                this.running = false;
            }
            this.previous = null;
        }

        private boolean connect() {
            try {
                this.ipc = DiscordIpc.connect(DiscordPresenceModule.APPLICATION_ID);
            }
            catch (IOException failure) {
                this.report("could not connect", failure);
                return false;
            }
            if (this.ipc == null) {
                return false;
            }
            LOGGER.info("[Anubis] Discord Presence: connected to Discord");
            this.retryMs = 5000L;
            this.lastProblem = null;
            this.sent = null;
            return true;
        }

        private void send(Card next) throws IOException {
            try {
                this.ipc.setActivity(DiscordPresenceModule.activity(next));
            }
            catch (DiscordIpc.RefusedException refused) {
                this.report("Discord refused the card", refused);
            }
            this.sent = next;
            this.sentAt = System.currentTimeMillis();
        }

        private void clear() {
            try {
                this.ipc.setActivity(null);
            }
            catch (IOException | DiscordIpc.RefusedException exception) {
                // empty catch block
            }
            this.hangUp();
        }

        private void hangUp() {
            this.ipc.close();
            this.ipc = null;
            this.sent = null;
        }

        private void pause(long millis) {
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(millis));
        }

        private void report(String what, Exception problem) {
            String line = what + " (" + String.valueOf(problem) + ")";
            if (line.equals(this.lastProblem)) {
                return;
            }
            this.lastProblem = line;
            LOGGER.warn("[Anubis] Discord Presence: {}", (Object)line);
        }
    }
}

