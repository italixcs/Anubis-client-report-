/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1268
 *  net.minecraft.class_1297
 *  net.minecraft.class_1542
 *  net.minecraft.class_1657
 *  net.minecraft.class_1703
 *  net.minecraft.class_1707
 *  net.minecraft.class_1713
 *  net.minecraft.class_1799
 *  net.minecraft.class_2246
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2382
 *  net.minecraft.class_243
 *  net.minecraft.class_2480
 *  net.minecraft.class_2561
 *  net.minecraft.class_2680
 *  net.minecraft.class_310
 *  net.minecraft.class_3532
 *  net.minecraft.class_3965
 *  net.minecraft.class_419
 *  net.minecraft.class_746
 */
package net.anubis.client.module.impl.misc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import net.anubis.client.chat.ModuleChat;
import net.anubis.client.event.impl.BlockUpdateEvent;
import net.anubis.client.event.impl.TickEvent;
import net.anubis.client.module.ClientModule;
import net.anubis.client.module.ModuleCategory;
import net.anubis.client.module.impl.misc.spawnerprotect.SpawnerAlerts;
import net.anubis.client.module.setting.BooleanSetting;
import net.anubis.client.module.setting.NumberSetting;
import net.anubis.client.module.setting.StringSetting;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_1657;
import net.minecraft.class_1703;
import net.minecraft.class_1707;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_2480;
import net.minecraft.class_2561;
import net.minecraft.class_2680;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3965;
import net.minecraft.class_419;
import net.minecraft.class_746;

@Environment(value=EnvType.CLIENT)
public final class SpawnerProtectModule
extends ClientModule {
    private static final int SHOP_END_CATEGORY_SLOT = 11;
    private static final int SHOP_ENDER_CHEST_SLOT = 9;
    private static final int SHOP_CONFIRM_SLOT = 25;
    private final NumberSetting targetStackCount = this.addSetting("stack-to-deposit", new NumberSetting("Stack To Deposit", 35.0f, 1.0f, 35.0f).step(1.0f));
    private final NumberSetting scanRange = this.addSetting("trigger-range", new NumberSetting("Trigger Range", 64.0f, 16.0f, 128.0f).step(0.1f));
    private final NumberSetting rotationSpeed = this.addSetting("rotation-speed", new NumberSetting("Rotation Speed", 15.0f, 1.0f, 30.0f).step(0.1f));
    private final BooleanSetting detectBlockUpdates = this.addSetting("detect-block-updates", new BooleanSetting("Detect Block Updates", true));
    private final StringSetting whitelist = this.addSetting("whitelist", new StringSetting("Whitelist", ""));
    private final NumberSetting breakRange = this.addSetting("break-range", new NumberSetting("Break Range", 5.5f, 1.0f, 8.0f).step(0.1f));
    private final NumberSetting blockUpdateThreshold = this.addSetting("block-update-threshold", new NumberSetting("Block Update Threshold", 5.0f, 1.0f, 20.0f).step(1.0f));
    private final NumberSetting blockUpdateWindow = this.addSetting("block-update-window", new NumberSetting("Block Update Window", 60.0f, 1.0f, 600.0f).step(1.0f));
    private final StringSetting webhookUrl = this.addSetting("webhook-url", new StringSetting("Webhook URL", ""));
    private State currentState = State.WAITING_FOR_STRANGER;
    private class_2338 targetBlock;
    private class_2338 targetChest;
    private int chestTick;
    private int breakCooldown;
    private int lagWaitTicks;
    private boolean strangerDetected;
    private final Set<UUID> nearbyPlayers = new HashSet<UUID>();
    private int blockBreakCounter;
    private long lastBreakTimestamp;
    private int shopSequence;
    private int shopTick;
    private int miningTicks;

    public SpawnerProtectModule() {
        super("spawner-protect", "Spawner Protect", "Protects spawners on block updates; player arrivals only send webhook notifications.", ModuleCategory.DONUT);
        this.whitelist.privateValue();
        this.webhookUrl.privateValue();
    }

    @Override
    public boolean persistEnabled() {
        return false;
    }

    @Override
    public int settingsVersion() {
        return 1;
    }

    @Override
    public void migrateSettings(int savedVersion) {
        if (savedVersion < 1 && this.targetStackCount.get() == 3.0f) {
            this.targetStackCount.resetToDefault();
        }
    }

    @Override
    protected void onEnable() {
        this.on(TickEvent.TYPE, this::onTick);
        this.on(BlockUpdateEvent.TYPE, this::onBlockUpdate);
        this.resetModule();
    }

    @Override
    protected void onDisable() {
        this.stopMovement();
        this.updateSneak(false);
    }

    private void resetModule() {
        this.currentState = State.WAITING_FOR_STRANGER;
        this.strangerDetected = false;
        this.nearbyPlayers.clear();
        this.lagWaitTicks = 0;
        this.blockBreakCounter = 0;
        this.lastBreakTimestamp = 0L;
        this.stopMovement();
        this.updateSneak(false);
        this.shopSequence = 0;
        this.shopTick = 0;
        this.resetState();
    }

    private void resetState() {
        this.targetChest = null;
        this.targetBlock = null;
        this.chestTick = 0;
        this.breakCooldown = 0;
        this.miningTicks = 0;
    }

    private void onBlockUpdate(BlockUpdateEvent event) {
        class_310 mc = class_310.method_1551();
        if (this.strangerDetected || mc.field_1724 == null || mc.field_1687 == null || this.isNearSpawn() || !this.detectBlockUpdates.get()) {
            return;
        }
        if (event.oldState.method_26215() || event.oldState.method_26204() instanceof class_2480) {
            return;
        }
        this.checkBlockUpdate(event.pos, event.newState);
    }

    private void checkBlockUpdate(class_2338 pos, class_2680 newState) {
        double dz;
        class_310 mc = class_310.method_1551();
        if (this.strangerDetected || mc.field_1687 == null) {
            return;
        }
        for (class_1657 player : mc.field_1687.method_18456()) {
            if (!(player.method_33571().method_1022(class_243.method_24953((class_2382)pos)) < 8.0) || !this.isWhitelisted(player.method_5477().getString())) continue;
            return;
        }
        if (pos.method_10264() <= -64 || !newState.method_26215()) {
            return;
        }
        if (mc.field_1724.method_33571().method_1022(class_243.method_24953((class_2382)pos)) < 6.0) {
            return;
        }
        double dx = mc.field_1724.method_23317() - (double)pos.method_10263();
        double horizontalDist = Math.sqrt(dx * dx + (dz = mc.field_1724.method_23321() - (double)pos.method_10260()) * dz);
        if (horizontalDist > (double)this.scanRange.get()) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now - this.lastBreakTimestamp > (long)this.blockUpdateWindow.getInt() * 1000L) {
            this.blockBreakCounter = 0;
        }
        ++this.blockBreakCounter;
        this.lastBreakTimestamp = now;
        if (this.blockBreakCounter < this.blockUpdateThreshold.getInt()) {
            return;
        }
        this.strangerDetected = true;
        this.currentState = State.WORKING;
        ModuleChat.send((ClientModule)this, "\ud83d\udea8 UZAK TESP\u0130T: Yak\u0131nda bir blok k\u0131r\u0131ld\u0131! (Yatay Mesafe: " + (int)horizontalDist + "m, Y: " + pos.method_10264() + ")");
        this.sendWebhook("\ud83d\udea8 Protection activated", "The block-break threshold was reached. Spawner collection has started.", 15548997, SpawnerAlerts.field("\ud83d\udce1 Trigger", "Block breaking", true), SpawnerAlerts.field("\ud83e\uddf1 Block counter", this.blockBreakCounter + " / " + this.blockUpdateThreshold.getInt(), true), SpawnerAlerts.field("\ud83d\udccf Horizontal distance", (int)horizontalDist + " blocks", true), SpawnerAlerts.field("\ud83d\udccd Block location", SpawnerProtectModule.coordinates(pos), false));
    }

    private void onTick(TickEvent event) {
        class_310 mc = class_310.method_1551();
        if (mc.field_1724 == null || mc.field_1687 == null || mc.field_1761 == null || mc.field_1755 instanceof class_419) {
            this.resetModule();
            return;
        }
        if (this.isNearSpawn()) {
            if (this.strangerDetected) {
                this.resetModule();
            }
            return;
        }
        this.notifyNearbyPlayers();
        if (this.currentState == State.WAITING_FOR_STRANGER) {
            return;
        }
        if (this.currentState != State.OPENING_CHEST && this.currentState != State.DEPOSITING_ITEMS && this.currentState != State.BUYING_ECHEST) {
            this.updateSneak(true);
        }
        this.handleRotation();
        switch (this.currentState.ordinal()) {
            case 1: {
                this.handleWorking();
                break;
            }
            case 2: {
                this.handleGoingToChest();
                break;
            }
            case 3: {
                this.handleOpeningChest();
                break;
            }
            case 4: {
                this.handleDepositing();
                break;
            }
            case 5: {
                this.handleFinalExit();
                break;
            }
            case 6: {
                this.handleBuyingEChest();
                break;
            }
            case 7: {
                this.handlePlacingEChest();
                break;
            }
        }
    }

    private void notifyNearbyPlayers() {
        class_310 mc = class_310.method_1551();
        HashSet<UUID> present = new HashSet<UUID>();
        for (class_1657 player : mc.field_1687.method_18456()) {
            if (player == mc.field_1724 || player.method_7325() || this.isWhitelisted(player.method_5477().getString()) || mc.field_1724.method_5739((class_1297)player) > this.scanRange.get()) continue;
            present.add(player.method_5667());
            if (this.nearbyPlayers.contains(player.method_5667())) continue;
            this.sendWebhook("\ud83d\udc64 A player entered your range", "A new player is nearby. This event only sends a notification.", 5793266, SpawnerAlerts.field("Player", "`" + player.method_5477().getString() + "`", true), SpawnerAlerts.field("\ud83d\udccf Distance", String.format(Locale.ROOT, "%.1f blocks", Float.valueOf(mc.field_1724.method_5739((class_1297)player))), true), SpawnerAlerts.field("\ud83d\udee1\ufe0f Protection", this.strangerDetected ? "In progress" : "Standby", true), SpawnerAlerts.field("\ud83d\udccd Player location", SpawnerProtectModule.coordinates(player.method_24515()), false));
        }
        this.nearbyPlayers.clear();
        this.nearbyPlayers.addAll(present);
    }

    private void handleRotation() {
        class_310 mc = class_310.method_1551();
        class_243 targetPos = null;
        if (this.currentState == State.WORKING) {
            class_1542 dropped = this.findDroppedSpawner();
            if (dropped != null) {
                targetPos = dropped.method_73189();
            } else {
                this.retargetSpawner(mc);
                if (this.targetBlock != null) {
                    targetPos = class_243.method_24953((class_2382)this.targetBlock);
                }
            }
        } else if ((this.currentState == State.GOING_TO_CHEST || this.currentState == State.OPENING_CHEST) && this.targetChest != null) {
            targetPos = class_243.method_24953((class_2382)this.targetChest);
        }
        if (targetPos != null) {
            this.smoothLook(mc.field_1724, targetPos);
        }
    }

    private void retargetSpawner(class_310 mc) {
        if (this.targetBlock == null || mc.field_1687.method_8320(this.targetBlock).method_26204() != class_2246.field_10260 || mc.field_1724.method_24515().method_10262((class_2382)this.targetBlock) > 256.0) {
            this.targetBlock = this.findRandomBlock(class_2246.field_10260, 16);
            this.miningTicks = 0;
        }
    }

    private void smoothLook(class_746 player, class_243 target) {
        class_243 eyes = player.method_33571();
        double dx = target.field_1352 - eyes.field_1352;
        double dy = target.field_1351 - eyes.field_1351;
        double dz = target.field_1350 - eyes.field_1350;
        double dist = Math.sqrt(dx * dx + dz * dz);
        float targetYaw = (float)Math.toDegrees(Math.atan2(-dx, dz));
        float targetPitch = (float)(-Math.toDegrees(Math.atan2(dy, dist)));
        float step = this.rotationSpeed.get();
        player.method_36456(player.method_36454() + class_3532.method_15363((float)class_3532.method_15393((float)(targetYaw - player.method_36454())), (float)(-step), (float)step));
        player.method_36457(player.method_36455() + class_3532.method_15363((float)class_3532.method_15393((float)(targetPitch - player.method_36455())), (float)(-step), (float)step));
    }

    private void handleWorking() {
        class_310 mc = class_310.method_1551();
        class_746 player = mc.field_1724;
        if (player.field_7512 != player.field_7498) {
            player.method_7346();
        }
        if (this.findDroppedSpawner() != null) {
            this.lagWaitTicks = 0;
            this.stopBreaking();
            mc.field_1690.field_1894.method_23481(true);
            return;
        }
        int spawners = this.getSpawnerCount();
        if (spawners >= this.targetStackCount.getInt() * 64 || spawners > 0 && player.method_31548().method_7376() == -1) {
            this.goToChest();
            return;
        }
        this.retargetSpawner(mc);
        if (this.targetBlock == null) {
            this.stopMovement();
            if (this.lagWaitTicks < 40) {
                ++this.lagWaitTicks;
            } else if (this.getSpawnerCount() > 0) {
                this.goToChest();
            } else {
                this.currentState = State.FINAL_EXIT;
                this.lagWaitTicks = 0;
            }
            return;
        }
        this.lagWaitTicks = 0;
        if (player.method_33571().method_1022(class_243.method_24953((class_2382)this.targetBlock)) > (double)this.breakRange.get()) {
            this.stopBreaking();
            mc.field_1690.field_1894.method_23481(true);
            return;
        }
        mc.field_1690.field_1894.method_23481(false);
        ++this.miningTicks;
        if (this.miningTicks > 60) {
            mc.field_1761.method_2896(player, class_1268.field_5808, new class_3965(class_243.method_24953((class_2382)this.targetBlock), class_2350.field_11036, this.targetBlock, false));
            player.method_6104(class_1268.field_5808);
            this.miningTicks = 0;
        }
        if (this.breakCooldown <= 0) {
            mc.field_1761.method_2902(this.targetBlock, class_2350.field_11036);
            player.method_6104(class_1268.field_5808);
            mc.field_1690.field_1886.method_23481(true);
            this.breakCooldown = 6;
        } else {
            --this.breakCooldown;
        }
    }

    private void goToChest() {
        this.stopMovement();
        this.targetChest = this.findNearestBlock(class_2246.field_10443, 4);
        if (this.targetChest != null) {
            this.currentState = State.GOING_TO_CHEST;
        } else if (this.getEnderChestCount() > 0) {
            this.currentState = State.PLACING_ECHEST;
            this.shopTick = 0;
        } else {
            this.currentState = State.BUYING_ECHEST;
            this.shopSequence = 0;
            this.shopTick = 0;
        }
    }

    private void handleGoingToChest() {
        class_310 mc = class_310.method_1551();
        if (this.targetChest == null) {
            this.targetChest = this.findNearestBlock(class_2246.field_10443, 4);
        }
        if (this.targetChest == null) {
            this.currentState = State.WORKING;
            return;
        }
        mc.field_1690.field_1894.method_23481(true);
        if (mc.field_1724.method_24515().method_19771((class_2382)this.targetChest, 4.0)) {
            this.stopMovement();
            this.currentState = State.OPENING_CHEST;
        }
    }

    private void handleOpeningChest() {
        class_310 mc = class_310.method_1551();
        this.updateSneak(false);
        if (this.chestTick > 200) {
            ModuleChat.send((ClientModule)this, "Ender chest a\u00e7\u0131lamad\u0131; e\u015fyalar envanterde tutuluyor.");
            this.currentState = State.FINAL_EXIT;
            this.lagWaitTicks = 0;
            return;
        }
        if (this.chestTick % 12 == 0 && this.targetChest != null) {
            mc.field_1761.method_2896(mc.field_1724, class_1268.field_5808, new class_3965(class_243.method_24953((class_2382)this.targetChest), class_2350.field_11036, this.targetChest, false));
        }
        ++this.chestTick;
        if (mc.field_1724.field_7512 instanceof class_1707) {
            this.chestTick = 0;
            this.currentState = State.DEPOSITING_ITEMS;
            this.lagWaitTicks = 0;
        }
    }

    private void handleBuyingEChest() {
        class_310 mc = class_310.method_1551();
        this.stopMovement();
        this.updateSneak(false);
        ++this.shopTick;
        if (this.shopTick > 200) {
            ModuleChat.send((ClientModule)this, "Market zaman a\u015f\u0131m\u0131; e\u015fyalar envanterde tutuluyor.");
            this.currentState = State.FINAL_EXIT;
            this.lagWaitTicks = 0;
            return;
        }
        if (this.shopSequence > 0 && mc.field_1755 == null && this.shopTick > 60) {
            ModuleChat.send((ClientModule)this, "Market ekran\u0131 kapand\u0131, ba\u015ftan deneniyor...");
            this.shopSequence = 0;
            this.shopTick = 0;
            return;
        }
        if (this.shopTick < 30) {
            return;
        }
        switch (this.shopSequence) {
            case 0: {
                ModuleChat.send((ClientModule)this, "Market a\u00e7\u0131l\u0131yor: /shop");
                mc.method_1562().method_45730("shop");
                this.shopSequence = 1;
                this.shopTick = 0;
                break;
            }
            case 1: {
                this.shopStep(mc, "SHOP", "Market: End kategorisi se\u00e7iliyor...", 11);
                break;
            }
            case 2: {
                this.shopStep(mc, "END", "Market: Ender Chest se\u00e7iliyor...", 9);
                break;
            }
            case 3: {
                this.shopStep(mc, "ENDER CHEST", "Market: Sat\u0131n alma onaylan\u0131yor...", 25);
                break;
            }
            case 4: {
                if (this.getEnderChestCount() > 0) {
                    ModuleChat.send((ClientModule)this, "Ender Chest sat\u0131n al\u0131nd\u0131.");
                    mc.field_1724.method_7346();
                    this.currentState = State.PLACING_ECHEST;
                    this.shopTick = 0;
                    break;
                }
                if (this.shopTick <= 100) break;
                ModuleChat.send((ClientModule)this, "Sat\u0131n alma ba\u015far\u0131s\u0131z oldu (Zaman a\u015f\u0131m\u0131).");
                mc.field_1724.method_7346();
                this.shopSequence = 0;
                this.shopTick = 0;
                break;
            }
        }
    }

    private void shopStep(class_310 mc, String title, String message, int slot) {
        if (mc.field_1755 == null || !mc.field_1755.method_25440().getString().toUpperCase(Locale.ROOT).contains(title)) {
            return;
        }
        ModuleChat.send((ClientModule)this, message);
        mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, slot, 0, class_1713.field_7790, (class_1657)mc.field_1724);
        ++this.shopSequence;
        this.shopTick = 0;
    }

    private void handlePlacingEChest() {
        int i;
        class_310 mc = class_310.method_1551();
        class_746 player = mc.field_1724;
        ++this.shopTick;
        if (this.shopTick < 10) {
            return;
        }
        int slot = -1;
        for (i = 0; i < 9; ++i) {
            if (player.method_31548().method_5438(i).method_7909() != class_2246.field_10443.method_8389()) continue;
            slot = i;
            break;
        }
        if (slot == -1) {
            for (i = 9; i < 36; ++i) {
                if (player.method_31548().method_5438(i).method_7909() != class_2246.field_10443.method_8389()) continue;
                player.method_7346();
                mc.field_1761.method_2906(player.field_7498.field_7763, i, 0, class_1713.field_7791, (class_1657)player);
                this.shopTick = 0;
                return;
            }
            this.currentState = State.BUYING_ECHEST;
            this.shopSequence = 0;
            return;
        }
        player.method_31548().method_61496(slot);
        class_2338 feet = player.method_24515();
        class_2338 placePos = null;
        for (class_2350 direction : class_2350.values()) {
            class_2338 candidate;
            if (direction == class_2350.field_11036 || direction == class_2350.field_11033 || !mc.field_1687.method_8320(candidate = feet.method_10093(direction)).method_45474() || mc.field_1687.method_8320(candidate.method_10074()).method_26215()) continue;
            placePos = candidate;
            break;
        }
        if (placePos == null) {
            ModuleChat.send((ClientModule)this, "E-Chest koyacak yer bulunamad\u0131!");
            this.currentState = State.FINAL_EXIT;
            this.lagWaitTicks = 0;
            return;
        }
        class_2338 support = placePos.method_10074();
        mc.field_1761.method_2896(player, class_1268.field_5808, new class_3965(class_243.method_24953((class_2382)support).method_1031(0.0, 0.5, 0.0), class_2350.field_11036, support, false));
        player.method_6104(class_1268.field_5808);
        this.targetChest = placePos;
        this.currentState = State.GOING_TO_CHEST;
    }

    private int getEnderChestCount() {
        return SpawnerProtectModule.countInInventory(class_2246.field_10443);
    }

    private void handleDepositing() {
        class_310 mc = class_310.method_1551();
        if (this.lagWaitTicks < 15) {
            ++this.lagWaitTicks;
            return;
        }
        class_1703 class_17032 = mc.field_1724.field_7512;
        if (!(class_17032 instanceof class_1707)) {
            this.currentState = State.OPENING_CHEST;
            this.chestTick = 0;
            return;
        }
        class_1707 chest = (class_1707)class_17032;
        int chestSize = chest.field_7761.size() - 36;
        boolean hasSpace = false;
        for (int i = 0; i < chestSize; ++i) {
            class_1799 stack = chest.method_7611(i).method_7677();
            if (!stack.method_7960() && (stack.method_7909() != class_2246.field_10260.method_8389() || stack.method_7947() >= stack.method_7914())) continue;
            hasSpace = true;
            break;
        }
        boolean depositedSomething = false;
        for (int i = 0; i < 36; ++i) {
            int slotId = chestSize + i;
            if (chest.method_7611(slotId).method_7677().method_7909() != class_2246.field_10260.method_8389()) continue;
            if (!hasSpace) {
                this.currentState = State.FINAL_EXIT;
                this.lagWaitTicks = 0;
                return;
            }
            mc.field_1761.method_2906(chest.field_7763, slotId, 0, class_1713.field_7794, (class_1657)mc.field_1724);
            depositedSomething = true;
            break;
        }
        if (!depositedSomething) {
            mc.field_1724.method_7346();
            if (this.findNearestBlock(class_2246.field_10260, 16) != null) {
                this.currentState = State.WORKING;
            } else {
                this.currentState = State.FINAL_EXIT;
                this.lagWaitTicks = 0;
            }
        }
    }

    private void handleFinalExit() {
        class_310 mc = class_310.method_1551();
        if (this.lagWaitTicks == 0) {
            int remaining = this.getSpawnerCount();
            boolean leftOver = remaining > 0;
            this.sendWebhook(leftOver ? "\u26a0\ufe0f Spawners remain in inventory" : "\u2705 Task completed", leftOver ? "Some spawners are still in your inventory. Disconnecting in two seconds." : "The collection sequence has ended. Disconnecting in two seconds.", leftOver ? 16705372 : 5763719, SpawnerAlerts.field("\ud83c\udf92 Remaining in inventory", remaining + " spawners", true), SpawnerAlerts.field("\ud83d\udeaa Next step", "Disconnect from server", true));
            ModuleChat.send((ClientModule)this, "G\u00f6rev bitti, e\u015fyalar\u0131n sunucuya kaydedilmesi i\u00e7in 2 saniye beklenip \u00e7\u0131k\u0131lacak...");
        }
        this.stopMovement();
        this.updateSneak(false);
        ++this.lagWaitTicks;
        if (this.lagWaitTicks > 40) {
            if (mc.method_1562() != null) {
                mc.method_1562().method_48296().method_10747((class_2561)class_2561.method_43470((String)"[SpawnerProtect] G\u00f6rev tamamland\u0131, g\u00fcvenli \u00e7\u0131k\u0131\u015f yap\u0131ld\u0131."));
            }
            this.resetModule();
            this.requestDisable();
        }
    }

    @SafeVarargs
    private void sendWebhook(String title, String description, int color, Map<String, Object> ... eventFields) {
        class_310 mc = class_310.method_1551();
        if (this.webhookUrl.get().isBlank() || mc.field_1724 == null || mc.field_1687 == null) {
            return;
        }
        ArrayList<Map<String, Object>> fields = new ArrayList<Map<String, Object>>(Arrays.asList(eventFields));
        fields.add(SpawnerAlerts.field("\ud83d\udc64 Account", "`" + mc.field_1724.method_5477().getString() + "`", true));
        fields.add(SpawnerAlerts.field("\ud83c\udf0d Dimension", "`" + String.valueOf(mc.field_1687.method_27983().method_29177()) + "`", true));
        fields.add(SpawnerAlerts.field("\ud83d\udccd Your location", SpawnerProtectModule.coordinates(mc.field_1724.method_24515()), false));
        SpawnerAlerts.send(this.webhookUrl.get(), SpawnerAlerts.payload(title, description, color, fields, Instant.now()));
    }

    private static String coordinates(class_2338 pos) {
        return "`X: " + pos.method_10263() + "  Y: " + pos.method_10264() + "  Z: " + pos.method_10260() + "`";
    }

    private void updateSneak(boolean sneak) {
        class_310 mc = class_310.method_1551();
        if (mc.field_1724 != null) {
            mc.field_1724.method_5660(sneak);
        }
        mc.field_1690.field_1832.method_23481(sneak);
    }

    private boolean isNearSpawn() {
        class_746 player = class_310.method_1551().field_1724;
        return player != null && Math.abs(player.method_23317()) < 100.0 && Math.abs(player.method_23321()) < 100.0;
    }

    private boolean isWhitelisted(String name) {
        return Arrays.stream(this.whitelist.get().split("[,;\\s]+")).anyMatch(entry -> entry.equalsIgnoreCase(name));
    }

    private class_2338 findNearestBlock(class_2248 block, int radius) {
        class_310 mc = class_310.method_1551();
        class_2338 origin = mc.field_1724.method_24515();
        class_2338 nearest = null;
        double minDist = Double.MAX_VALUE;
        for (int x = -radius; x <= radius; ++x) {
            for (int y = -radius; y <= radius; ++y) {
                for (int z = -radius; z <= radius; ++z) {
                    double dist;
                    class_2338 pos = origin.method_10069(x, y, z);
                    if (mc.field_1687.method_8320(pos).method_26204() != block || !((dist = origin.method_10262((class_2382)pos)) < minDist)) continue;
                    minDist = dist;
                    nearest = pos;
                }
            }
        }
        return nearest;
    }

    private class_2338 findRandomBlock(class_2248 block, int radius) {
        class_310 mc = class_310.method_1551();
        class_2338 origin = mc.field_1724.method_24515();
        ArrayList<class_2338> found = new ArrayList<class_2338>();
        double maxDistSq = radius * radius;
        for (int x = -radius; x <= radius; ++x) {
            for (int y = -radius; y <= radius; ++y) {
                for (int z = -radius; z <= radius; ++z) {
                    class_2338 pos = origin.method_10069(x, y, z);
                    if (!(origin.method_10262((class_2382)pos) <= maxDistSq) || mc.field_1687.method_8320(pos).method_26204() != block) continue;
                    found.add(pos);
                }
            }
        }
        return found.isEmpty() ? null : (class_2338)found.get(new Random().nextInt(found.size()));
    }

    private int getSpawnerCount() {
        return SpawnerProtectModule.countInInventory(class_2246.field_10260);
    }

    private static int countInInventory(class_2248 block) {
        class_746 player = class_310.method_1551().field_1724;
        int count = 0;
        for (int i = 0; i < 36; ++i) {
            class_1799 stack = player.method_31548().method_5438(i);
            if (stack.method_7909() != block.method_8389()) continue;
            count += stack.method_7947();
        }
        return count;
    }

    private class_1542 findDroppedSpawner() {
        class_310 mc = class_310.method_1551();
        class_1542 nearest = null;
        float nearestDist = Float.MAX_VALUE;
        for (class_1297 entity : mc.field_1687.method_18112()) {
            float dist;
            class_1542 item;
            if (!(entity instanceof class_1542) || (item = (class_1542)entity).method_6983().method_7909() != class_2246.field_10260.method_8389() || (dist = mc.field_1724.method_5739((class_1297)item)) >= 16.0f || item.method_6977() || !(dist < nearestDist)) continue;
            nearestDist = dist;
            nearest = item;
        }
        return nearest;
    }

    private void stopBreaking() {
        class_310 mc = class_310.method_1551();
        mc.field_1690.field_1886.method_23481(false);
        if (mc.field_1761 != null) {
            mc.field_1761.method_2925();
        }
        this.breakCooldown = 0;
    }

    private void stopMovement() {
        this.stopBreaking();
        class_310.method_1551().field_1690.field_1894.method_23481(false);
    }

    @Environment(value=EnvType.CLIENT)
    private static enum State {
        WAITING_FOR_STRANGER,
        WORKING,
        GOING_TO_CHEST,
        OPENING_CHEST,
        DEPOSITING_ITEMS,
        FINAL_EXIT,
        BUYING_ECHEST,
        PLACING_ECHEST;

    }
}

