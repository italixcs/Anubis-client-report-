package net.anubis.client.module.impl.misc;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import net.anubis.client.discord.DiscordIpc;
import net.anubis.client.event.impl.TickEvent;
import net.anubis.client.gui.mainmenu.CommunityPopup;
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

@Environment(EnvType.CLIENT)
/* loaded from: classes-only.jar:net/anubis/client/module/impl/misc/DiscordPresenceModule.class */
public final class DiscordPresenceModule extends ClientModule {
    private static final String APPLICATION_ID = "1553391775840866314";
    private static final String LOGO_ASSET = "logo";
    private static final String LOGO_TEXT = "Anubis Client";
    private static final String BUTTON_LABEL = "Discord";
    private static final String IN_MENU = "In Main Menu";
    private static final String SINGLEPLAYER = "Singleplayer";
    private static final String MULTIPLAYER = "Multiplayer";
    private static final String LAN_WORLD = "LAN World";
    private static final String REALMS = "Realms";
    private static final long RETRY_MIN_MS = 5000;
    private static final long RETRY_MAX_MS = 30000;
    private static final long IDLE_POLL_MS = 1000;
    private static final long MIN_UPDATE_GAP_MS = 4000;
    private static final long KEEP_ALIVE_MS = 60000;
    private final BooleanSetting showServerIp;
    private final BooleanSetting discordButton;
    private volatile Card card;
    private Link link;
    private class_2535 session;
    private long joinedAt;
    private boolean loggedIdle;
    private static final Logger LOGGER = LoggerFactory.getLogger("Anubis");
    private static final long GAME_STARTED = ((Instant) ProcessHandle.current().info().startInstant().orElseGet(Instant::now)).getEpochSecond();

    public DiscordPresenceModule() {
        super("discord-presence", "Discord Presence", "Shows Anubis Client and the server you are on in your Discord status.", ModuleCategory.CLIENT);
        this.showServerIp = (BooleanSetting) addSetting("show-server-ip", new BooleanSetting("Show Server IP", true));
        this.discordButton = (BooleanSetting) addSetting("discord-button", new BooleanSetting("Discord Button", true));
    }

    @Override // net.anubis.client.module.ClientModule
    public boolean enabledByDefault() {
        return true;
    }

    @Override // net.anubis.client.module.ClientModule
    protected void onEnable() {
        if (APPLICATION_ID.isBlank()) {
            if (!this.loggedIdle) {
                this.loggedIdle = true;
                LOGGER.info("[Anubis] Discord Presence: no Discord application id set (DiscordPresenceModule.APPLICATION_ID); idle");
                return;
            }
            return;
        }
        this.session = null;
        this.card = new Card(IN_MENU, GAME_STARTED, this.discordButton.get());
        this.link = new Link(this.link);
        refresh();
        on(TickEvent.TYPE, event -> {
            refresh();
        });
        this.link.start();
    }

    @Override // net.anubis.client.module.ClientModule
    protected void onDisable() {
        if (this.link != null) {
            this.link.stop();
        }
        this.session = null;
    }

    private void refresh() {
        String details;
        long startedAt;
        class_310 mc = class_310.method_1551();
        class_634 listener = mc.method_1562();
        if (listener != null) {
            if (listener.method_48296() != this.session) {
                this.session = listener.method_48296();
                this.joinedAt = System.currentTimeMillis() / IDLE_POLL_MS;
            }
            details = place(mc, listener.method_45734());
            startedAt = this.joinedAt;
        } else {
            if (this.session != null && this.session.method_10758() && (this.session.method_10744() instanceof class_8732)) {
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
        return mc.method_1542() ? SINGLEPLAYER : server == null ? MULTIPLAYER : server.method_52811() ? REALMS : server.method_2994() ? LAN_WORLD : this.showServerIp.get() ? server.field_3761 : MULTIPLAYER;
    }

    private static JsonObject activity(Card card) {
        JsonObject activity = new JsonObject();
        activity.addProperty("details", card.details());
        JsonObject timestamps = new JsonObject();
        timestamps.addProperty("start", Long.valueOf(card.startedAt()));
        activity.add("timestamps", timestamps);
        JsonObject assets = new JsonObject();
        assets.addProperty("large_image", LOGO_ASSET);
        assets.addProperty("large_text", "Anubis Client");
        activity.add("assets", assets);
        if (card.discordButton()) {
            JsonObject button = new JsonObject();
            button.addProperty("label", BUTTON_LABEL);
            button.addProperty("url", CommunityPopup.INVITE_URL);
            JsonArray buttons = new JsonArray();
            buttons.add(button);
            activity.add("buttons", buttons);
        }
        return activity;
    }

    @Environment(EnvType.CLIENT)
    /* loaded from: classes-only.jar:net/anubis/client/module/impl/misc/DiscordPresenceModule$Card.class */
    private static final class Card extends Record {
        private final String details;
        private final long startedAt;
        private final boolean discordButton;

        private Card(String details, long startedAt, boolean discordButton) {
            this.details = details;
            this.startedAt = startedAt;
            this.discordButton = discordButton;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Card.class), Card.class, "details;startedAt;discordButton", "FIELD:Lnet/anubis/client/module/impl/misc/DiscordPresenceModule$Card;->details:Ljava/lang/String;", "FIELD:Lnet/anubis/client/module/impl/misc/DiscordPresenceModule$Card;->startedAt:J", "FIELD:Lnet/anubis/client/module/impl/misc/DiscordPresenceModule$Card;->discordButton:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Card.class), Card.class, "details;startedAt;discordButton", "FIELD:Lnet/anubis/client/module/impl/misc/DiscordPresenceModule$Card;->details:Ljava/lang/String;", "FIELD:Lnet/anubis/client/module/impl/misc/DiscordPresenceModule$Card;->startedAt:J", "FIELD:Lnet/anubis/client/module/impl/misc/DiscordPresenceModule$Card;->discordButton:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Card.class, Object.class), Card.class, "details;startedAt;discordButton", "FIELD:Lnet/anubis/client/module/impl/misc/DiscordPresenceModule$Card;->details:Ljava/lang/String;", "FIELD:Lnet/anubis/client/module/impl/misc/DiscordPresenceModule$Card;->startedAt:J", "FIELD:Lnet/anubis/client/module/impl/misc/DiscordPresenceModule$Card;->discordButton:Z").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public String details() {
            return this.details;
        }

        public long startedAt() {
            return this.startedAt;
        }

        public boolean discordButton() {
            return this.discordButton;
        }
    }

    @Environment(EnvType.CLIENT)
    /* loaded from: classes-only.jar:net/anubis/client/module/impl/misc/DiscordPresenceModule$Link.class */
    private final class Link implements Runnable {
        private Thread previous;
        private DiscordIpc ipc;
        private Card sent;
        private long sentAt;
        private String lastProblem;
        private final Thread thread = new Thread(this, "Anubis Discord Presence");
        private volatile boolean running = true;
        private long retryMs = DiscordPresenceModule.RETRY_MIN_MS;

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

        /* JADX WARN: Removed duplicated region for block: B:18:0x006e A[Catch: IOException -> 0x0076, TryCatch #0 {IOException -> 0x0076, blocks: (B:10:0x0036, B:12:0x0055, B:18:0x006e), top: B:27:0x0036 }] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void run() throws java.lang.InterruptedException {
            /*
                r6 = this;
                r0 = r6
                r0.awaitPrevious()
            L4:
                r0 = r6
                boolean r0 = r0.running
                if (r0 == 0) goto L8f
                r0 = r6
                net.anubis.client.discord.DiscordIpc r0 = r0.ipc
                if (r0 != 0) goto L36
                r0 = r6
                boolean r0 = r0.connect()
                if (r0 != 0) goto L36
                r0 = r6
                r1 = r6
                long r1 = r1.retryMs
                r0.pause(r1)
                r0 = r6
                r1 = r6
                long r1 = r1.retryMs
                r2 = 2
                long r1 = r1 * r2
                r2 = 30000(0x7530, double:1.4822E-319)
                long r1 = java.lang.Math.min(r1, r2)
                r0.retryMs = r1
                goto L4
            L36:
                r0 = r6
                net.anubis.client.discord.DiscordIpc r0 = r0.ipc     // Catch: java.io.IOException -> L76
                r0.poll()     // Catch: java.io.IOException -> L76
                r0 = r6
                net.anubis.client.module.impl.misc.DiscordPresenceModule r0 = net.anubis.client.module.impl.misc.DiscordPresenceModule.this     // Catch: java.io.IOException -> L76
                net.anubis.client.module.impl.misc.DiscordPresenceModule$Card r0 = r0.card     // Catch: java.io.IOException -> L76
                r7 = r0
                long r0 = java.lang.System.currentTimeMillis()     // Catch: java.io.IOException -> L76
                r1 = r6
                long r1 = r1.sentAt     // Catch: java.io.IOException -> L76
                long r0 = r0 - r1
                r8 = r0
                r0 = r6
                net.anubis.client.module.impl.misc.DiscordPresenceModule$Card r0 = r0.sent     // Catch: java.io.IOException -> L76
                if (r0 == 0) goto L6e
                r0 = r8
                r1 = r7
                r2 = r6
                net.anubis.client.module.impl.misc.DiscordPresenceModule$Card r2 = r2.sent     // Catch: java.io.IOException -> L76
                boolean r1 = r1.equals(r2)     // Catch: java.io.IOException -> L76
                if (r1 == 0) goto L67
                r1 = 60000(0xea60, double:2.9644E-319)
                goto L6a
            L67:
                r1 = 4000(0xfa0, double:1.9763E-320)
            L6a:
                int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
                if (r0 < 0) goto L73
            L6e:
                r0 = r6
                r1 = r7
                r0.send(r1)     // Catch: java.io.IOException -> L76
            L73:
                goto L85
            L76:
                r7 = move-exception
                r0 = r6
                java.lang.String r1 = "lost Discord"
                r2 = r7
                r0.report(r1, r2)
                r0 = r6
                r0.hangUp()
                goto L4
            L85:
                r0 = r6
                r1 = 1000(0x3e8, double:4.94E-321)
                r0.pause(r1)
                goto L4
            L8f:
                r0 = r6
                net.anubis.client.discord.DiscordIpc r0 = r0.ipc
                if (r0 == 0) goto L9a
                r0 = r6
                r0.clear()
            L9a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: net.anubis.client.module.impl.misc.DiscordPresenceModule.Link.run():void");
        }

        private void awaitPrevious() throws InterruptedException {
            if (this.previous == null) {
                return;
            }
            try {
                this.previous.join();
            } catch (InterruptedException e) {
                this.running = false;
            }
            this.previous = null;
        }

        private boolean connect() {
            try {
                this.ipc = DiscordIpc.connect(DiscordPresenceModule.APPLICATION_ID);
                if (this.ipc == null) {
                    return false;
                }
                DiscordPresenceModule.LOGGER.info("[Anubis] Discord Presence: connected to Discord");
                this.retryMs = DiscordPresenceModule.RETRY_MIN_MS;
                this.lastProblem = null;
                this.sent = null;
                return true;
            } catch (IOException failure) {
                report("could not connect", failure);
                return false;
            }
        }

        private void send(Card next) throws IOException {
            try {
                this.ipc.setActivity(DiscordPresenceModule.activity(next));
            } catch (DiscordIpc.RefusedException refused) {
                report("Discord refused the card", refused);
            }
            this.sent = next;
            this.sentAt = System.currentTimeMillis();
        }

        private void clear() {
            try {
                this.ipc.setActivity(null);
            } catch (IOException | DiscordIpc.RefusedException e) {
            }
            hangUp();
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
            DiscordPresenceModule.LOGGER.warn("[Anubis] Discord Presence: {}", line);
        }
    }
}
