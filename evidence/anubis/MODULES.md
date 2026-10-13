# Module inventory

Extracted from constructor string constants and direct class-file methods. Defaults combine the shipped modules.json with constant enabledByDefault() results; a saved user config can override them.

| Module ID | Name | Description | Fresh default | Class |
|---|---|---|---|---|
| hotkeys | Hotkeys | Lists the modules you have bound to a key, and their keys. | True | net.anubis.client.module.impl.client.HotkeysModule |
| hud | HUD | Module list, coordinates and status overlays. | True | net.anubis.client.module.impl.client.HudModule |
| performance | Performance | Reduces background work and image memory while keeping effects and features. Disable to compare FPS. | None | net.anubis.client.module.impl.client.PerformanceModule |
| radar | Radar | A round radar of the players around you. | True | net.anubis.client.module.impl.client.RadarModule |
| region-map | Region Map | DonutSMP region map with directional indicator. | True | net.anubis.client.module.impl.client.RegionMapModule |
| spotify-hud | Spotify HUD | Shows what Spotify is playing, with synced lyrics and optional media keys. | True | net.anubis.client.module.impl.client.SpotifyHudModule |
| aim-assist | Aim Assist | Eases your crosshair onto the nearest enemy player while you move the mouse. | False | net.anubis.client.module.impl.combat.AimAssistModule |
| anchor-aura | Anchor Aura | Places, charges and detonates respawn anchors on nearby enemies. | False | net.anubis.client.module.impl.combat.AnchorAuraModule |
| auto-city | Auto City | Mines a surround block of the best enemy in a hole, then turns off so Crystal Aura can blow through it. | False | net.anubis.client.module.impl.combat.AutoCityModule |
| autoinvtotem | Auto Inventory Totem | Refills the offhand totem after a pop through the inventory or a hotbar swap; Double Hand also keeps one in the main hand while in danger. | False | net.anubis.client.module.impl.combat.AutoInvTotemModule |
| autototem | Auto Totem | Fills an empty offhand with a totem without opening the inventory; Double Hand also keeps one in the main hand while in danger. | False | net.anubis.client.module.impl.combat.AutoTotemModule |
| crystal-aura | Crystal Aura | Places and breaks end crystals for the most damage. | False | net.anubis.client.module.impl.combat.CrystalAuraModule |
| crystal-macro | Crystal Macro | Breaks and places crystals while you hold right-click. | False | net.anubis.client.module.impl.combat.CrystalMacroModule |
| crystal-optimizer | Crystal Optimizer | Removes crystals client-side as soon as your hit breaks them. | False | net.anubis.client.module.impl.combat.CrystalOptimizerModule |
| fastxp | FastXP | Throws experience bottles faster while use is held. | False | net.anubis.client.module.impl.combat.FastXpModule |
| friend-guard | Friend Guard | Stops you from hitting players on your friends list. | True | net.anubis.client.module.impl.combat.FriendGuardModule |
| hovertotem | Hover Totem | Swaps a hovered totem from any open container into the offhand or selected hotbar slot. | False | net.anubis.client.module.impl.combat.HoverTotemModule |
| jump-reset | Jump Reset | Jumps the tick knockback lands to take less of it. | False | net.anubis.client.module.impl.combat.JumpResetModule |
| key-throw | Key Throw | Throws a pearl or a wind charge with a key and swaps back. | False | net.anubis.client.module.impl.combat.KeyThrowModule |
| kill-aura | Kill Aura | Attacks nearby targets without moving your camera. | False | net.anubis.client.module.impl.combat.KillAuraModule |
| mace | Mace | Swaps to the strongest mace for smash hits, with an optional smash assist. | False | net.anubis.client.module.impl.combat.MaceModule |
| shield-breaker | Shield Breaker | Swaps to an axe for any hit into a raised shield. Auto also makes the hit. | False | net.anubis.client.module.impl.combat.ShieldBreakerModule |
| anchor-macro | Anchor Macro | Places, charges and blows up respawn anchors on right-click. | False | net.anubis.client.module.impl.combat.SingleAnchorMacroModule |
| spear-swap | Spear Swap | Swaps to your Lunge spear and lunges. Reach Poke stabs with a spear past sword reach. | False | net.anubis.client.module.impl.combat.SpearLungeModule |
| triggerbot | Triggerbot | Hits the entity under the crosshair once the swing is charged, with a reaction delay. | False | net.anubis.client.module.impl.combat.TriggerbotModule |
| w-tap-recode | W Tap | Resets your sprint right after a sprint hit so the next hit knocks back too. | False | net.anubis.client.module.impl.combat.WTapRecodeModule |
| ah-sell | AH Sell | Lists up to 9 items from your inventory on the Auction House at your price or the cheapest price. | False | net.anubis.client.module.impl.donutmacros.AhSellModule |
| ah-sniper | AH Sniper | Refreshes the AH and instantly buys your item below your price, with optional auto selling. | False | net.anubis.client.module.impl.donutmacros.AhSniperModule |
| auto-sell | Auto Sell | Keeps the /sell menu open and keeps selling everything that is not on your Never Sell list. | False | net.anubis.client.module.impl.donutmacros.AutoSellModule |
| auto-tpahere | Tpahere Kill Bot | Sends tpahere requests to online players one by one, pauses when someone arrives and fights them. Servers can detect this. | False | net.anubis.client.module.impl.donutmacros.AutoTpaHereModule |
| order-ah-seller | Order AH Trader | Sells an item on the Auction House, undercuts other sellers and restocks through orders. | False | net.anubis.client.module.impl.donutmacros.OrderAhSellerModule |
| order-dropper | Order Dropper | Opens one of your orders, collects it and drops the chosen number of pages, then turns off. | False | net.anubis.client.module.impl.donutmacros.OrderDropperModule |
| panic-pay | Panic Pay | Sells your whole inventory and armor with /sell, then pays all your money to one player. | False | net.anubis.client.module.impl.donutmacros.PanicPayModule |
| tp-macro | Tp Macro | Sends /tpa or /tpahere to one player on a timer. | False | net.anubis.client.module.impl.donutmacros.TpMacroModule |
| air-stuck | Air Stuck | Freezes you in place, even mid-air. | False | net.anubis.client.module.impl.misc.AirStuckModule |
| amethyst-bypass | Amethyst Bypass | Shows amethyst geodes hidden by anti-xray. | False | net.anubis.client.module.impl.misc.AmethystBypassModule |
| anti-trap | Anti Trap | Allows you to escape from armor stands and chest minecarts. | False | net.anubis.client.module.impl.misc.AntiTrapModule |
| anti-vanish | Anti Vanish | Detects vanished players. | False | net.anubis.client.module.impl.misc.AntiVanishModule |
| auto-clicker | Auto Clicker | Clicks the left and/or right mouse button at a randomised, human CPS. | False | net.anubis.client.module.impl.misc.AutoClickerModule |
| auto-eat | Auto Eat | Eats hotbar or offhand food once hunger or health drops too low. | False | net.anubis.client.module.impl.misc.AutoEatModule |
| auto-reconnect | Auto Reconnect | Reconnects to the last server after an unexpected disconnect. | False | net.anubis.client.module.impl.misc.AutoReconnectModule |
| auto-relog | Auto Relog | Relogs once when you go down to deepslate level. | False | net.anubis.client.module.impl.misc.AutoRelogModule |
| auto-replenish | Auto Replenish | Refills a hotbar or offhand slot from the main inventory once it runs low. | False | net.anubis.client.module.impl.misc.AutoReplenishModule |
| block-notifier | Block Notifier | Shows a toast with the block and its coordinates when one of your chosen blocks shows up. | False | net.anubis.client.module.impl.misc.BlockNotifierModule |
| chat-macro | Chat Macro | Sends chat messages or commands with a key press. | False | net.anubis.client.module.impl.misc.ChatMacroModule |
| coord-snapper | Coord Snapper | Copies the coordinates you look at or stand on with one key. | False | net.anubis.client.module.impl.misc.CoordSnapperModule |
| discord-presence | Discord Presence | Shows Anubis Client and the server you are on in your Discord status. | True | net.anubis.client.module.impl.misc.DiscordPresenceModule |
| donut-nofall | Donut NoFall | Take no fall damage on DonutSMP. | False | net.anubis.client.module.impl.misc.DonutNoFallModule |
| fake-stats | Fake Stats | Shows your own name and stats on the sidebar and tab list. Blank stats get scrambled. | True | net.anubis.client.module.impl.misc.FakeStats |
| fast-use | Fast Use | Places blocks and uses items faster while use is held. | False | net.anubis.client.module.impl.misc.FastUseModule |
| freecam | Freecam | Detached camera. | False | net.anubis.client.module.impl.misc.FreecamModule |
| home-meta | Home Meta | Re-sets a home, RTPs away, then teleports back home. | False | net.anubis.client.module.impl.misc.HomeMetaModule |
| legit-auto-tool | Legit Auto Tool | Auto tool that only switches hotbar slots like a player. | False | net.anubis.client.module.impl.misc.LegitAutoToolModule |
| name-protect | Name Protect | Shows aliases instead of player names on your screen. | False | net.anubis.client.module.impl.misc.NameProtectModule |
| netherite-finder | Netherite Finder | Boxes Nether chunk sections hiding ancient debris. | False | net.anubis.client.module.impl.misc.NetheriteFinderModule |
| no-inv-leak | No Inv Leak | Hides your items on screen for screen shares. Stream Only keeps them visible to you alone. | False | net.anubis.client.module.impl.misc.NoInvLeakModule |
| player-bypass-2 | Player Bypass 2 | Finds hidden players from chunks that keep ticking outside your simulation distance. | False | net.anubis.client.module.impl.misc.PlayerBypass2Module |
| player-bypass | Player Bypass | Flags chunks with signs of underground player bases from light data. | False | net.anubis.client.module.impl.misc.PlayerBypassModule |
| player-detection | Player Detection | Detects when players are in the world. | False | net.anubis.client.module.impl.misc.PlayerDetectionModule |
| quantum | Quantum & Render | Finds player activity in resent chunks and marks the area around it. | False | net.anubis.client.module.impl.misc.QuantumModule |
| render-distance-toggle | Render Distance Toggle | Reloads chunks when you go below a Y level. | False | net.anubis.client.module.impl.misc.RenderDistanceToggleModule |
| riptide | Riptide | Uses a riptide trident while you hold right-click. | False | net.anubis.client.module.impl.misc.RiptideModule |
| skin-change | Skin Change | Wear any player's skin client-side. | False | net.anubis.client.module.impl.misc.SkinChangeModule |
| slow-swing | Slow Swing | Control hand swing animation speed. | False | net.anubis.client.module.impl.misc.SlowSwingModule |
| spam | Spam | Spams specified messages in chat. | False | net.anubis.client.module.impl.misc.SpamModule |
| spawner-protect | Spawner Protect | Protects spawners on block updates; player arrivals only send webhook notifications. | False | net.anubis.client.module.impl.misc.SpawnerProtectModule |
| speed-mine | Donut Speed Mine | Finishes breaking blocks before the full break time. | False | net.anubis.client.module.impl.misc.SpeedMineModule |
| staff-list | Staff List | Lists online DonutSMP staff. | False | net.anubis.client.module.impl.misc.StaffListModule |
| trident-util | Trident Util | Use tridents out of water and charge them faster. | False | net.anubis.client.module.impl.misc.TridentUtilModule |
| weather-notifier | Weather Notifier | Shows an advancement-style toast when it starts raining. | True | net.anubis.client.module.impl.misc.WeatherNotifierModule |
| auto-jump | Auto Jump | Automatically jumps. | False | net.anubis.client.module.impl.movement.AutoJumpModule |
| auto-walk | Auto Walk | Automatically walks forward. | False | net.anubis.client.module.impl.movement.AutoWalkModule |
| boat-fly | Boat Fly | Transforms your boat (or any ridden vehicle) into a plane. | False | net.anubis.client.module.impl.movement.BoatFlyModule |
| boat-noclip | Boat Noclip | Fly your boat through blocks and entities. | False | net.anubis.client.module.impl.movement.BoatNoclipModule |
| click-tp | Click TP | Teleports you to the block you click on. | False | net.anubis.client.module.impl.movement.ClickTpModule |
| elytra-fly | Elytra Fly | Gives you more control over your elytra. | False | net.anubis.client.module.impl.movement.ElytraFlyModule |
| elytra-swap | Elytra Swap | Swaps between your chestplate and elytra with one key. | False | net.anubis.client.module.impl.movement.ElytraSwapModule |
| entity-control | Entity Control | Lets you control rideable entities without a saddle. | False | net.anubis.client.module.impl.movement.EntityControlModule |
| entity-speed | Entity Speed | Makes you go faster horizontally when riding entities. | False | net.anubis.client.module.impl.movement.EntitySpeedModule |
| fly | Fly | Lets you fly. | False | net.anubis.client.module.impl.movement.FlyModule |
| jesus | Jesus | Walk on liquids and powder snow like Jesus. | False | net.anubis.client.module.impl.movement.JesusModule |
| no-jump-delay | No Jump Delay | Removes the delay between jumps. | False | net.anubis.client.module.impl.movement.NoJumpDelayModule |
| sneak | Sneak | Sneaks for you. | False | net.anubis.client.module.impl.movement.SneakModule |
| speed | Speed | Modifies your movement speed when moving on the ground. | False | net.anubis.client.module.impl.movement.SpeedModule |
| spider | Spider | Allows you to climb walls like a spider. | False | net.anubis.client.module.impl.movement.SpiderModule |
| sprint | Sprint | Keeps you sprinting while you move forward, like vanilla Toggle Sprint. | False | net.anubis.client.module.impl.movement.SprintModule |
| trident-boost | Trident Boost | Boosts you when using riptide with a trident. | False | net.anubis.client.module.impl.movement.TridentBoostModule |
| atmosphere | Atmosphere | A custom sky with stars and an aurora. | True | net.anubis.client.module.impl.render.AtmosphereModule |
| block-entity-debug | Block Entity Debug | Renders every leaked block entity as ESP (always-on Netty capture). | False | net.anubis.client.module.impl.render.BlockEntityDebugModule |
| block-esp | Block ESP | Highlights chosen blocks; touching blocks merge into one shape. | False | net.anubis.client.module.impl.render.BlockEspModule |
| block-overlay | Block Overlay | A smooth, animated outline and fill on the block you look at. | True | net.anubis.client.module.impl.render.BlockOverlayModule |
| chunk-scanner | Chunk Scanner | Marks suspicious chunks with signs of player activity. | False | net.anubis.client.module.impl.render.ChunkScannerModule |
| color-grading | Color Grading | Adds color, contrast and a vignette to the world. | True | net.anubis.client.module.impl.render.ColorGradingModule |
| custom-crosshair | Custom Crosshair | Draws a configurable crosshair in place of the vanilla one. | True | net.anubis.client.module.impl.render.CustomCrosshairModule |
| custom-fov | Custom Fov | Changes your field of view (FOV). | True | net.anubis.client.module.impl.render.CustomFovModule |
| custom-glint | Custom Glint | Recolours the enchantment glint and changes its speed and pattern size. | True | net.anubis.client.module.impl.render.CustomGlintModule |
| esp | ESP | Highlights players, hostile and passive mobs with outlines, boxes and tracers. | False | net.anubis.client.module.impl.render.EspModule |
| floating-relics | Floating Relics | Egyptian symbols float and spin around you. | True | net.anubis.client.module.impl.render.FloatingRelicsModule |
| freelook | Freelook | Turns the camera and the player separately. | False | net.anubis.client.module.impl.render.FreelookModule |
| fullbright | Fullbright | Brighten dark areas by overriding the client lightmap gamma. | True | net.anubis.client.module.impl.render.FullbrightModule |
| hand-aura | Hand Aura | Adds a glowing outline to your hand and held item. | True | net.anubis.client.module.impl.render.HandAuraModule |
| 3d-hand | 3D Hand | Shows your own arm holding the item in first person: swords, tools, items and blocks. | False | net.anubis.client.module.impl.render.HandModelModule |
| hand-pose | Hand Pose | Moves, rotates and resizes your first-person hands. | True | net.anubis.client.module.impl.render.HandsModule |
| hit-particles | Hit Particles | Spawns particles where your hits land. | True | net.anubis.client.module.impl.render.HitParticlesModule |
| hole-esp | Hole ESP | Highlights holes, tunnels, and staircases. | False | net.anubis.client.module.impl.render.HoleEspModule |
| item-esp | Item ESP | Highlights selected dropped items with model outlines and tracers. | False | net.anubis.client.module.impl.render.ItemEspModule |
| jump-circles | Jump Circles | Draws a fading Anubis ring where you jump. | True | net.anubis.client.module.impl.render.JumpCirclesModule |
| light-debug | Light Debug | Highlights artificial light underground. | False | net.anubis.client.module.impl.render.LightEspModule |
| low-health-warning | Low Health Warning | Flashes the screen edges when your health is low. | False | net.anubis.client.module.impl.render.LowHealthWarningModule |
| motion-blur | Motion Blur | Adds motion blur when you turn. | True | net.anubis.client.module.impl.render.MotionBlurModule |
| nametags | Nametags | Player nametags with held items, armour, health, ping and distance. | True | net.anubis.client.module.impl.render.NametagsModule |
| no-render | No Render | Hide selected overlays, screen effects and entity renders. | False | net.anubis.client.module.impl.render.NoRenderModule |
| pearl-trajectory | Pearl Trajectory | Predicts where a held pearl lands and previews the flight of thrown pearls. | False | net.anubis.client.module.impl.render.PearlTrajectoryModule |
| prime-chunk-finder | Prime Chunk Finder | Flags chunks with light and redstone changes below Y 0. | False | net.anubis.client.module.impl.render.PrimeChunkFinderModule |
| particles | Particles | Egyptian particles for totem pops, projectiles and footsteps. | True | net.anubis.client.module.impl.render.RelicParticlesModule |
| glow-esp | Glow ESP | Outlines entities through walls with a glow. | True | net.anubis.client.module.impl.render.ShaderEspModule |
| spawner-nametags | Spawner Nametags | Shows each spawner's mob and activation range over the block. | False | net.anubis.client.module.impl.render.SpawnerNametagsModule |
| spawner-notifier | Spawner Notifier | Scans chunks for mob spawners and highlights them. | False | net.anubis.client.module.impl.render.SpawnerNotifierModule |
| storage-esp | Storage ESP | Highlights storage blocks and spawners with per-type colours. | False | net.anubis.client.module.impl.render.StorageEspModule |
| sus-chunk-finder | Sus Chunk Finder | Finds chunks with signs of player activity from fully grown amethyst. | False | net.anubis.client.module.impl.render.SusChunkFinderModule |
| swing-animation | Swing Animation | Changes your first-person swing animation. | True | net.anubis.client.module.impl.render.SwingAnimationModule |
| target-esp | Target ESP | Draws an Egyptian mark on your combat target. | True | net.anubis.client.module.impl.render.TargetEspModule |
| time-changer | Time Changer | Makes you able to set a custom time. | False | net.anubis.client.module.impl.render.TimeChangerModule |
| totem-spirit | Totem Spirit | A glowing ghost rises when a player pops a totem. | True | net.anubis.client.module.impl.render.TotemSpiritModule |
| tracers | Tracers | Displays tracer lines to specified entities. | False | net.anubis.client.module.impl.render.TracersModule |
| trajectories | Trajectories | Shows where your projectiles will land. | False | net.anubis.client.module.impl.render.TrajectoriesModule |
| weapon-inspect | CS Knife | Knife skins for your sword and CS-style inspects with a key of your choice (F by default); hold the key to keep the knife spinning. | True | net.anubis.client.module.impl.render.WeaponInspectModule |
| xray | Xray | Hide or fade everything but a block whitelist to see ores through terrain. | False | net.anubis.client.module.impl.render.XrayModule |
| zoom | Zoom | Hold the key to zoom in. | False | net.anubis.client.module.impl.render.ZoomModule |
| character-3d | 3D Character | Replaces your player model with a 3D character. | True | net.anubis.client.module.impl.render.character.CharacterModule |
