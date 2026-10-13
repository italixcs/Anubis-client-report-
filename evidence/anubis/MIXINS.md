# Complete Mixin annotation inventory

Generated directly from class-file annotations. Target method/field names are the shipped intermediary mappings. Delegate names describe Anubis code reached by each hook.

## com.example.client.mixin.AccessorAbstractContainerScreen

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_465;"}]}]`.

- `anubis$getHoveredSlot()Lnet/minecraft/class_1735;`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_2787"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorAccountsMinecraft

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_310;"}]}]`.

- `anubis$setUser(Lnet/minecraft/class_320;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_1726"}}]`. Anubis delegates: ``.
- `anubis$setUserApiService(Lcom/mojang/authlib/minecraft/UserApiService;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_26902"}}]`. Anubis delegates: ``.
- `anubis$setUserPropertiesFuture(Ljava/util/concurrent/CompletableFuture;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_47680"}}]`. Anubis delegates: ``.
- `anubis$setProfileFuture(Ljava/util/concurrent/CompletableFuture;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_45899"}}]`. Anubis delegates: ``.
- `anubis$setPlayerSocialManager(Lnet/minecraft/class_5520;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_26842"}}]`. Anubis delegates: ``.
- `anubis$setProfileKeyPairManager(Lnet/minecraft/class_7853;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_39068"}}]`. Anubis delegates: ``.
- `anubis$setReportingContext(Lnet/minecraft/class_7574;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_39492"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorAtmosphereClientLevel

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_638;"}]}]`.

- `anubis$atmosphereSkyFlash()I`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_21526"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorBlockOverlayMultiPlayerGameMode

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_636;"}]}]`.

- `anubis$blockOverlayDestroyProgress()F`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_3715"}}]`. Anubis delegates: ``.
- `anubis$blockOverlayDestroyPos()Lnet/minecraft/class_2338;`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_3714"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorBoatFlyServerboundMoveVehiclePacket

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_2833;"}]}]`.

- `anubis$setPosition(Lnet/minecraft/class_243;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "comp_3350"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorCombatMinecraft

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_310;"}]}]`.

- `anubis$rightClickDelay()I`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_1752"}}]`. Anubis delegates: ``.
- `anubis$setRightClickDelay(I)V`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_1752"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorElytraFlyBlockBehaviour

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_4970;"}]}]`.

- `anubis$elytraFlyHasCollision()Z`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_23159"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorElytraFlyMultiPlayerGameMode

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_636;"}]}]`.

- `anubis$elytraFlySyncSelected()V`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Invoker;", "values": {"value": "method_2911"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorGameRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_757;"}]}]`.

- `anubis$getFov(Lnet/minecraft/class_4184;FZ)F`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Invoker;", "values": {"value": "method_3196"}}]`. Anubis delegates: ``.
- `anubis$getFogRenderer()Lnet/minecraft/class_758;`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_60793"}}]`. Anubis delegates: ``.
- `anubis$shouldRenderBlockOutline()Z`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Invoker;", "values": {"value": "method_3202"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorHandItemLayer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_10444$class_10446;"}]}]`.

- `anubis$handTransform()Lnet/minecraft/class_804;`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_56967"}}]`. Anubis delegates: ``.
- `anubis$handExtents()Ljava/util/function/Supplier;`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_56968"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorHandItemRenderState

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_10444;"}]}]`.

- `anubis$handLayers()[Lnet/minecraft/class_10444$class_10446;`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_55340"}}]`. Anubis delegates: ``.
- `anubis$handLayerCount()I`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_55339"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorHandPlayerModel

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_591;"}]}]`.

- `anubis$handSlim()Z`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_3480"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorHudCooldownInstance

Configuration: `anubis.mixins.json`. Targets: `[{"targets": ["net/minecraft/class_1796$class_1797"]}]`.

- `anubis$hudStartTime()I`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "comp_3083"}}]`. Anubis delegates: ``.
- `anubis$hudEndTime()I`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "comp_3084"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorHudItemCooldowns

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1796;"}]}]`.

- `anubis$hudCooldowns()Ljava/util/Map;`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_8024"}}]`. Anubis delegates: ``.
- `anubis$hudTickCount()I`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_8025"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorHudToastInstance

Configuration: `anubis.mixins.json`. Targets: `[{"targets": ["net/minecraft/class_374$class_375"]}]`.

- `anubis$hudToast()Lnet/minecraft/class_368;`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_2241"}}]`. Anubis delegates: ``.
- `anubis$hudFirstSlot()I`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_52787"}}]`. Anubis delegates: ``.
- `anubis$hudVisibility()Lnet/minecraft/class_368$class_369;`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_2244"}}]`. Anubis delegates: ``.
- `anubis$hudVisiblePortion()F`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_52789"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorHudToastManager

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_374;"}]}]`.

- `anubis$hudVisibleToasts()Ljava/util/List;`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_2239"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorJesusServerboundMovePlayerPacket

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_2828;"}]}]`.

- `anubis$jesusSetOnGround(Z)V`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_29179"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorLevelLoadingScreen

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_3928;"}]}]`.

- `anubis$getProgress()F`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_61632"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorLoadingOverlay

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_425;"}]}]`.

- `anubis$getProgress()F`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_17770"}}]`. Anubis delegates: ``.
- `anubis$getFadeOutStart()J`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_17771"}}]`. Anubis delegates: ``.
- `anubis$getFadeInStart()J`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_18220"}}]`. Anubis delegates: ``.
- `anubis$isFadeIn()Z`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_18219"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorLocalPlayer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_746;"}]}]`.

- `anubis$setPositionReminder(I)V`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_3923"}}]`. Anubis delegates: ``.
- `anubis$wasSprinting()Z`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_3919"}}]`. Anubis delegates: ``.
- `anubis$lastSentInput()Lnet/minecraft/class_10185;`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_54156"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorMenusJoinMultiplayerScreen

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_500;"}]}]`.

- `anubis$refreshServerList()V`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Invoker;", "values": {"value": "method_2534"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorMovementClientInput

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_744;"}]}]`.

- `anubis$setMoveVector(Lnet/minecraft/class_241;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_55868"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorNetheriteClientLevel

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_638;"}]}]`.

- `anubis$blockStatePredictionHandler()Lnet/minecraft/class_7202;`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_37951"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorOrderDialogScreen

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_11473;"}]}]`.

- `anubis$dialog()Lnet/minecraft/class_11419;`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_60761"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorServerboundMovePlayerPacket

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_2828;"}]}]`.

- `anubis$setY(D)V`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_12886"}}]`. Anubis delegates: ``.
- `anubis$setOnGround(Z)V`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_29179"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorSkinChangeEntityRenderDispatcher

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_898;"}]}]`.

- `anubis$skinChangePlayerRenderers()Ljava/util/Map;`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_4687"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorSpawnerNametagsBaseSpawner

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1917;"}]}]`.

- `anubis$spawnerNametagsNextSpawnData()Lnet/minecraft/class_1952;`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_9155"}}]`. Anubis delegates: ``.
- `anubis$spawnerNametagsRequiredPlayerRange()I`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_9158"}}]`. Anubis delegates: ``.

## com.example.client.mixin.AccessorTriggerbotKeyMapping

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_304;"}]}]`.

- `anubis$triggerbotClickCount()I`: `[{"type": "Lorg/spongepowered/asm/mixin/gen/Accessor;", "values": {"value": "field_1661"}}]`. Anubis delegates: ``.

## com.example.client.mixin.MixinAirStuckLocalPlayer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_746;"}]}]`.

- `anubis$airStuck(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_6007"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: ``.

## com.example.client.mixin.MixinAtmosphereCloudRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_9955;"}]}]`.

- `anubis$cloudColour(I)I`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyVariable;", "values": {"method": ["method_62168"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}, "argsOnly": 1, "ordinal": 0}}]`. Anubis delegates: `net/anubis/client/module/impl/render/AtmosphereModule.clouds, net/anubis/client/module/impl/render/AtmosphereModule.get`.

## com.example.client.mixin.MixinAtmosphereFogRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_758;"}]}]`.

- `anubis$fogColour(Lnet/minecraft/class_4184;FLnet/minecraft/class_638;IFLorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_62185"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/AtmosphereModule.fog, net/anubis/client/module/impl/render/AtmosphereModule.get`.

## com.example.client.mixin.MixinAtmosphereRainFog

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_11398;"}]}]`.

- `anubis$keepClearInRain(Lnet/minecraft/class_4184;Lnet/minecraft/class_638;Lnet/minecraft/class_9779;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_76304"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/AtmosphereModule.get, net/anubis/client/module/impl/render/AtmosphereModule.keepsSkyInRain`.

## com.example.client.mixin.MixinAtmosphereSkyRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_9975;"}]}]`.

- `anubis$readSky(Lnet/minecraft/class_638;FLnet/minecraft/class_4184;Lnet/minecraft/class_12076;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_74926"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/AtmosphereModule.extract, net/anubis/client/module/impl/render/AtmosphereModule.get`.
- `anubis$dome(ILorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_62302"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/AtmosphereModule.get, net/anubis/client/module/impl/render/AtmosphereModule.skyDisc`.
- `anubis$afterDome(ILorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_62302"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/AtmosphereModule.afterSkyDisc, net/anubis/client/module/impl/render/AtmosphereModule.get`.
- `anubis$noSunriseFan(Lnet/minecraft/class_4587;FILorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_62306"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/AtmosphereModule.get, net/anubis/client/module/impl/render/AtmosphereModule.replacesSunrise`.
- `anubis$stars(FLnet/minecraft/class_4587;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_62310"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/AtmosphereModule.get, net/anubis/client/module/impl/render/AtmosphereModule.stars`.
- `anubis$beforeMoon(Lnet/minecraft/class_4587;FFFLnet/minecraft/class_12131;FFLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_62307"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_9975;method_62304(Lnet/minecraft/class_12131;FLnet/minecraft/class_4587;)V"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/AtmosphereModule.beforeMoon, net/anubis/client/module/impl/render/AtmosphereModule.get`.
- `anubis$afterMoon(Lnet/minecraft/class_4587;FFFLnet/minecraft/class_12131;FFLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_62307"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_9975;method_62304(Lnet/minecraft/class_12131;FLnet/minecraft/class_4587;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/AtmosphereModule.afterMoon, net/anubis/client/module/impl/render/AtmosphereModule.get`.
- `anubis$afterCelestials(Lnet/minecraft/class_4587;FFFLnet/minecraft/class_12131;FFLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_62307"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/AtmosphereModule.afterCelestials, net/anubis/client/module/impl/render/AtmosphereModule.get`.
- `anubis$endSky(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_62312"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/AtmosphereModule.endSky, net/anubis/client/module/impl/render/AtmosphereModule.get`.

## com.example.client.mixin.MixinAutoCityMinecraft

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_310;"}]}]`.

- `anubis$autoCityContinueAttack(ZLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_1590"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/combat/AutoCityModule.mining`.

## com.example.client.mixin.MixinBlock

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_2248;"}]}]`.

- `anubis$xrayForceFace(Lnet/minecraft/class_2680;Lnet/minecraft/class_2680;Lnet/minecraft/class_2350;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_9607"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/XrayModule$Snapshot.isWhitelisted`.

## com.example.client.mixin.MixinBlockEntityRenderDispatcher

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_824;"}]}]`.

- `anubis$xraySkipHiddenBlockEntity(Lnet/minecraft/class_2586;FLnet/minecraft/class_11683$class_11792;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_74348"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/XrayModule$Snapshot.alphaFor`.

## com.example.client.mixin.MixinBlockOverlayLevelRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_761;"}]}]`.

- `anubis$hideVanillaOutline(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_62210"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/ModuleRegistry.get, net/anubis/client/module/impl/render/BlockOverlayModule.isEnabled`.

## com.example.client.mixin.MixinBlockStateBase

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_4970$class_4971;"}]}]`.

- `anubis$xrayNoAmbientOcclusion(Lnet/minecraft/class_1922;Lnet/minecraft/class_2338;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_26210"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: ``.

## com.example.client.mixin.MixinBoatFlyClientPacketListener

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_634;"}]}]`.

- `anubis$boatFlyVehicleSetback(Lnet/minecraft/class_2692;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11134"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2833;method_65307(Lnet/minecraft/class_1297;)Lnet/minecraft/class_2833;"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/BoatFlyModule.onVehicleSetback, net/anubis/client/module/impl/movement/BoatNoclipModule.onVehicleSetback`.

## com.example.client.mixin.MixinBorderlessWindow

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1041;"}]}]`.

- `anubis$setMode(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_4479"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/gui/BorderlessFullscreen.wanted`.

## com.example.client.mixin.MixinBoatNoclipAbstractBoat

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_10255;"}]}]`.

- `anubis$boatNoclipCollide(Z)Z`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;", "values": {"method": ["method_30949"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: ``.

## com.example.client.mixin.MixinCamera

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_4184;"}]}]`.

- `anubis$freelookRotation(Lnet/minecraft/class_4184;FFLcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_19321"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_4184;method_19325(FF)V"}}]}}]`. Anubis delegates: `net/anubis/client/module/ModuleRegistry.get, net/anubis/client/module/impl/render/FreelookModule.cameraPitch, net/anubis/client/module/impl/render/FreelookModule.cameraYaw, net/anubis/client/module/impl/render/FreelookModule.isEnabled`.
- `anubis$freelookThroughBlocks(Lnet/minecraft/class_4184;FLcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)F`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_19321"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_4184;method_19318(F)F"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/FreelookModule.throughBlocks`.
- `anubis$freecamCamera(Lnet/minecraft/class_1937;Lnet/minecraft/class_1297;ZZFLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_19321"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/FreecamModule.getPitch, net/anubis/client/module/impl/misc/FreecamModule.getX, net/anubis/client/module/impl/misc/FreecamModule.getY, net/anubis/client/module/impl/misc/FreecamModule.getYaw, net/anubis/client/module/impl/misc/FreecamModule.getZ`.

## com.example.client.mixin.MixinCharacterAvatarRenderState

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_10055;"}]}]`.


## com.example.client.mixin.MixinCharacterAvatarRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1007;"}]}]`.

- `anubis$addCharacterLayer(Lnet/minecraft/class_5617$class_5618;ZLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["<init>"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/character/CharacterLayer.<init>`.
- `anubis$extractCharacter(Lnet/minecraft/class_11890;Lnet/minecraft/class_10055;FLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_62604"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/character/CharacterModule.extract`.
- `anubis$characterFirstPersonHand(Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;ILnet/minecraft/class_2960;Lnet/minecraft/class_630;ZLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_23205"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/character/CharacterLayer.firstPersonHand`.

## com.example.client.mixin.MixinCharacterLighting

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_308;"}]}]`.

- `anubis$trackCharacterLighting(Lnet/minecraft/class_308$class_11274;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_71034"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/character/CharacterLighting.setup`.

## com.example.client.mixin.MixinCharacterLivingEntityRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_922;"}]}]`.

- `anubis$hideModelUnderCharacter(Lnet/minecraft/class_10042;ZZZLorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_24302"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/character/CharacterModule.hidesModel`.
- `anubis$hideLayersUnderCharacter(Lnet/minecraft/class_3887;Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;ILnet/minecraft/class_10017;FFLcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_4054"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_3887;method_4199(Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;ILnet/minecraft/class_10017;FF)V"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/character/CharacterModule.hidesLayer`.

## com.example.client.mixin.MixinCharacterPlayerModel

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_591;"}]}]`.

- `anubis$characterHand(Lnet/minecraft/class_10055;Lnet/minecraft/class_1306;Lnet/minecraft/class_4587;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_72859"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/character/CharacterLayer.translateToHand`.

## com.example.client.mixin.MixinClientConnection

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_2535;"}]}]`.

- `anubis$onPacketReceive(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/class_2596;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_10770"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/event/impl/PacketReceiveEvent.dispatchAsync, net/anubis/client/event/impl/PacketReceiveNettyEvent.dispatchSync`.
- `anubis$onPacketSend(Lnet/minecraft/class_2596;Lio/netty/channel/ChannelFutureListener;ZLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_52906"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/event/impl/PacketSendEvent.dispatchSync, net/anubis/client/util/pvp/ServerRotation.noteSent`.

## com.example.client.mixin.MixinClientLevel

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_638;"}]}]`.

- `anubis$onEntityAdd(Lnet/minecraft/class_1297;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_53875"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}]}}]`. Anubis delegates: `net/anubis/client/event/impl/WorldEntityAddEvent.dispatch`.
- `anubis$onBlockUpdated(Lnet/minecraft/class_2338;Lnet/minecraft/class_2680;Lnet/minecraft/class_2680;ILorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_8413"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}]}}]`. Anubis delegates: `net/anubis/client/event/impl/BlockUpdateEvent.dispatch`.

## com.example.client.mixin.MixinClientPacketListener

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_634;"}]}]`.

- `anubis$onSendChat(Ljava/lang/String;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_45729"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/event/impl/ChatSendEvent.dispatch`.
- `anubis$afterLogin(Lnet/minecraft/class_2678;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11120"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/event/impl/PacketAppliedEvent.dispatch`.
- `anubis$afterChunkData(Lnet/minecraft/class_2672;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11128"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/event/impl/PacketAppliedEvent.dispatch`.
- `anubis$afterBlockUpdate(Lnet/minecraft/class_2626;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11136"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/event/impl/PacketAppliedEvent.dispatch`.
- `anubis$afterSectionUpdate(Lnet/minecraft/class_2637;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11100"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/event/impl/PacketAppliedEvent.dispatch`.
- `anubis$afterLightUpdate(Lnet/minecraft/class_2676;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11143"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/event/impl/PacketAppliedEvent.dispatch`.
- `anubis$afterEntityMotion(Lnet/minecraft/class_2743;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11132"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/event/impl/PacketAppliedEvent.dispatch`.

## com.example.client.mixin.MixinPlayerBypass2ClientPacketListener

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_634;"}]}]`.

- `anubis$pb2Login(Lnet/minecraft/class_2678;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11120"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/util/playerbypass2/SimulationSignals.onLogin`.
- `anubis$pb2Respawn(Lnet/minecraft/class_2724;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11117"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/util/playerbypass2/SimulationSignals.onRespawn`.
- `anubis$pb2Teleport(Lnet/minecraft/class_2708;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11157"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/util/playerbypass2/SimulationSignals.onTeleport`.
- `anubis$pb2Simulation(Lnet/minecraft/class_6682;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_39025"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/util/playerbypass2/SimulationSignals.onSimulationDistance`.
- `anubis$pb2View(Lnet/minecraft/class_4273;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_20203"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/util/playerbypass2/SimulationSignals.onViewDistance`.
- `anubis$pb2Chunk(Lnet/minecraft/class_2672;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11128"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/util/playerbypass2/SimulationSignals.onChunk`.
- `anubis$pb2Move(Lnet/minecraft/class_2684;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11155"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/util/playerbypass2/SimulationSignals.onMove`.
- `anubis$pb2PositionSync(Lnet/minecraft/class_10264;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_64553"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/util/playerbypass2/SimulationSignals.onPositionSync`.
- `anubis$pb2Head(Lnet/minecraft/class_2726;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11139"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/util/playerbypass2/SimulationSignals.onHeadRotation`.
- `anubis$pb2EntityData(Lnet/minecraft/class_2739;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11093"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/util/playerbypass2/SimulationSignals.onEntityData`.
- `anubis$pb2EntityEvent(Lnet/minecraft/class_2663;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11148"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/util/playerbypass2/SimulationSignals.onEntityEvent`.
- `anubis$pb2AddEntity(Lnet/minecraft/class_2604;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11112"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/util/playerbypass2/SimulationSignals.onAddEntity`.
- `anubis$pb2RemoveEntities(Lnet/minecraft/class_2716;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11095"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/util/playerbypass2/SimulationSignals.onRemoveEntities`.
- `anubis$pb2Block(Lnet/minecraft/class_2626;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11136"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/util/playerbypass2/SimulationSignals.onBlockUpdate`.
- `anubis$pb2Section(Lnet/minecraft/class_2637;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11100"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/util/playerbypass2/SimulationSignals.onSectionUpdate`.
- `anubis$pb2TakeItem(Lnet/minecraft/class_2775;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11150"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/util/playerbypass2/SimulationSignals.onTakeItem`.
- `anubis$pb2Damage(Lnet/minecraft/class_8143;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_49034"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/util/playerbypass2/SimulationSignals.onDamage`.
- `anubis$pb2Equipment(Lnet/minecraft/class_2744;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11151"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/util/playerbypass2/SimulationSignals.onEquipment`.

## com.example.client.mixin.MixinTranslationGuardAnvilScreen

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_471;"}]}]`.

- `anubis$guardSlot(Lnet/minecraft/class_1703;ILnet/minecraft/class_1799;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapmethod/WrapMethod;", "values": {"method": ["method_7635"]}}]`. Anubis delegates: `net/anubis/client/util/TranslationGuard.run`.
- `anubis$guardName(Ljava/lang/String;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapmethod/WrapMethod;", "values": {"method": ["method_2403"]}}]`. Anubis delegates: `net/anubis/client/util/TranslationGuard.run`.

## com.example.client.mixin.MixinTranslationGuardClientLanguage

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1078;"}]}]`.

- `anubis$beginLoad(Lnet/minecraft/class_3300;Ljava/util/List;ZLorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_4675"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "require": 0}}]`. Anubis delegates: `net/anubis/client/util/TranslationGuard.beginLoad`.
- `anubis$tapLoad(Ljava/io/InputStream;Ljava/util/function/BiConsumer;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;Lnet/minecraft/class_3298;)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_4676"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2477;method_29425(Ljava/io/InputStream;Ljava/util/function/BiConsumer;)V"}}], "require": 0}}]`. Anubis delegates: `net/anubis/client/util/TranslationGuard.tap`.
- `anubis$endLoad(Lnet/minecraft/class_3300;Ljava/util/List;ZLorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_4675"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}], "require": 0}}]`. Anubis delegates: `net/anubis/client/util/TranslationGuard.endLoad`.

## com.example.client.mixin.MixinTranslationGuardKeybind

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_2572;"}]}]`.

- `anubis$guardKeybind(Lnet/minecraft/class_2561;)Lnet/minecraft/class_2561;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;", "values": {"method": ["method_27691"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/util/TranslationGuard.active, net/anubis/client/util/TranslationGuard.keybind`.

## com.example.client.mixin.MixinTranslationGuardLanguage

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_2477;"}]}]`.

- `anubis$guardLanguage(Lnet/minecraft/class_2477;)Lnet/minecraft/class_2477;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;", "values": {"method": ["method_10517"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/util/TranslationGuard.active, net/anubis/client/util/TranslationGuard.language`.

## com.example.client.mixin.MixinTranslationGuardSignEditScreen

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_7743;"}]}]`.

- `anubis$guardMessages(Lnet/minecraft/class_2625;ZZLnet/minecraft/class_2561;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["<init>(Lnet/minecraft/class_2625;ZZLnet/minecraft/class_2561;)V"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/util/TranslationGuard.plain`.

## com.example.client.mixin.MixinColorGradingGameRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_757;"}]}]`.

- `anubis$colorGrading(Lnet/minecraft/class_9779;ZLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_3192"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_758;method_71108()V"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/ColorGradingModule.afterWorld`.

## com.example.client.mixin.MixinCustomCrosshairGui

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_329;"}]}]`.

- `anubis$hideVanillaCrosshair(Lnet/minecraft/class_332;Lnet/minecraft/class_9779;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_1736"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: ``.

## com.example.client.mixin.MixinCustomFovGameRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_757;"}]}]`.

- `anubis$customFov(FZ)F`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;", "values": {"method": ["method_3196"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/CustomFovModule.fov, net/anubis/client/module/impl/render/CustomFovModule.hudFovApplied, net/anubis/client/module/impl/render/ZoomModule.fov`.

## com.example.client.mixin.MixinCustomGlintTextureTransform

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_12250;"}]}]`.

- `anubis$glintPatternScale(F)F`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyVariable;", "values": {"method": ["method_76031"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}, "argsOnly": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/CustomGlintModule.uvScaleMultiplier`.
- `anubis$glintClock(J)J`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_76031"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_156;method_658()J"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/CustomGlintModule.glintMillis`.

## com.example.client.mixin.MixinDuskBackdropScreen

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_437;"}]}]`.

- `anubis$duskBackdrop(Lnet/minecraft/class_332;FLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_57728"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/gui/AnubisTitleScreen.enabled, net/anubis/client/gui/mainmenu/DuskBackdrop.draw`.

## com.example.client.mixin.MixinElytraFlyEntity

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1297;"}]}]`.

- `anubis$elytraFlyGetPose(Lnet/minecraft/class_4050;)Lnet/minecraft/class_4050;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;", "values": {"method": ["method_18376"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/ElytraFlyModule.canPacketEfly`.

## com.example.client.mixin.MixinElytraFlyLivingEntity

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1309;"}]}]`.

- `anubis$elytraFlyIsFallFlying(Z)Z`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;", "values": {"method": ["method_6128"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/ElytraFlyModule.modifyFallFlying`.

## com.example.client.mixin.MixinEntity

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1297;"}]}]`.

- `anubis$isCurrentlyGlowing(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_5851"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/EspModule.shouldGlow, net/anubis/client/module/impl/render/ItemEspModule.shouldGlow`.
- `anubis$getTeamColor(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_22861"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/EspModule.glowColor, net/anubis/client/module/impl/render/EspModule.shouldGlow, net/anubis/client/module/impl/render/ItemEspModule.glowColor, net/anubis/client/module/impl/render/ItemEspModule.shouldGlow`.
- `anubis$freecamOwnBody(Lnet/minecraft/class_1657;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_5756"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/FreecamModule.drawsSelf`.

## com.example.client.mixin.MixinEntityControlAbstractBoat

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_10255;"}]}]`.

- `anubis$entityControlInputLeft(Z)Z`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_64482"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "FIELD", "target": "Lnet/minecraft/class_10255;field_54444:Z", "opcode": 180}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/entitycontrol/EntityControlVehicleHook.locksBoatTurning`.
- `anubis$entityControlInputRight(Z)Z`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_64482"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "FIELD", "target": "Lnet/minecraft/class_10255;field_54428:Z", "opcode": 180}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/entitycontrol/EntityControlVehicleHook.locksBoatTurning`.

## com.example.client.mixin.MixinEntityControlEntity

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1297;"}]}]`.

- `anubis$entityControlVehicleMove(Lnet/minecraft/class_243;Lnet/minecraft/class_1313;)Lnet/minecraft/class_243;`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyVariable;", "values": {"method": ["method_5784"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}, "argsOnly": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/entitycontrol/EntityControlVehicleHook.onEntityMove`.

## com.example.client.mixin.MixinEntityControlLocalPlayer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_746;"}]}]`.

- `anubis$entityControlMaxJump(F)F`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;", "values": {"method": ["method_3151"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/EntityControlModule.maxJump`.
- `anubis$boatFlyCancelJump(Lnet/minecraft/class_1316;)Lnet/minecraft/class_1316;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;", "values": {"method": ["method_45773"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/BoatFlyModule.cancelsMountJump`.

## com.example.client.mixin.MixinEntityControlMob

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1308;"}]}]`.

- `anubis$entityControlSpoofSaddle(Z)Z`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;", "values": {"method": ["method_66672"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/EntityControlModule.spoofSaddle`.

## com.example.client.mixin.MixinFallingBlockRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_901;"}]}]`.

- `anubis$cancelFallingBlock(Lnet/minecraft/class_1540;Lnet/minecraft/class_4604;DDDLorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_62436"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/NoRenderModule.hideFallingBlocks`.

## com.example.client.mixin.MixinFlyEntity

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1297;"}]}]`.

- `anubis$flyIsInWater(Z)Z`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;", "values": {"method": ["method_5799"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: ``.
- `anubis$flyIsInLava(Z)Z`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;", "values": {"method": ["method_5771"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: ``.
- `anubis$flyIsUnderWater(Z)Z`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_5790"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_1297;method_5869()Z"}}]}}]`. Anubis delegates: ``.

## com.example.client.mixin.MixinFlyLocalPlayer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_746;"}]}]`.

- `anubis$flyNoSneak(Z)Z`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;", "values": {"method": ["method_5715"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/FlyModule.noSneak`.
- `anubis$flyFreecamVertical(F)F`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_6007"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_1656;method_7252()F"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/FlyModule.suppressFlightVerticalInput`.

## com.example.client.mixin.MixinFreecamLevelRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_761;"}]}]`.

- `anubis$freecamEntitiesReady(Lnet/minecraft/class_2338;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_40050"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: ``.
- `anubis$freelookCullInsideBlocks(Lnet/minecraft/class_761;Lnet/minecraft/class_4184;Lnet/minecraft/class_4604;ZLcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_22710"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_761;method_74752(Lnet/minecraft/class_4184;Lnet/minecraft/class_4604;Z)V"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/FreelookModule.throughBlocks`.

## com.example.client.mixin.MixinFriendGuardMultiPlayerGameMode

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_636;"}]}]`.

- `anubis$friendGuard(Lnet/minecraft/class_1657;Lnet/minecraft/class_1297;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_2918"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1, "order": 900}}]`. Anubis delegates: `net/anubis/client/module/impl/combat/FriendGuardModule.intercepts`.
- `anubis$friendGuardStab(Lnet/minecraft/class_12125;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_75407"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1, "order": 900}}]`. Anubis delegates: `net/anubis/client/module/impl/combat/FriendGuardModule.interceptsStab`.

## com.example.client.mixin.MixinGameRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1041;"}]}]`.

- `anubis$beforeSwap(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_15998"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}]}}]`. Anubis delegates: `net/anubis/client/imgui/ImGuiManager.getInstance, net/anubis/client/imgui/ImGuiManager.renderIfActive, net/anubis/client/module/impl/misc/noinvleak/StreamOverlay.endFrame`.

## com.example.client.mixin.MixinGameRendererEffects

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_757;"}]}]`.

- `anubis$freecamHands(Z)Z`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_3172"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_5498;method_31034()Z"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/FreecamModule.keepHands`.
- `anubis$cancelTotemAnimation(Lnet/minecraft/class_1799;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_3189"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/NoRenderModule.hideTotemAnimation`.
- `anubis$cancelNauseaWobble(FFLcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)F`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_3188"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Ljava/lang/Math;max(FF)F"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/NoRenderModule.hideNauseaPortal`.

## com.example.client.mixin.MixinGlDevice

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_10874;"}]}]`.

- `anubis$blockDsaForAmd()Z`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Redirect;", "values": {"method": ["method_68813"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "FIELD", "target": "Lnet/minecraft/class_10865;field_58039:Z", "opcode": 178}}}}]`. Anubis delegates: ``.

## com.example.client.mixin.MixinGlintHeadSpecialModelRenderers

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_10517;"}]}]`.

- `anubis$registerGlintHead(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_65712"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: ``.

## com.example.client.mixin.MixinGui

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_329;"}]}]`.

- `anubis$freecamCrosshair(Z)Z`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_1736"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_5498;method_31034()Z"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/FreecamModule.showBody`.
- `anubis$onRender2D(Lnet/minecraft/class_332;Lnet/minecraft/class_9779;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_1753"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/event/impl/Render2DEvent.dispatch`.
- `anubis$cancelVanillaEffects(Lnet/minecraft/class_332;Lnet/minecraft/class_9779;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_1765"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: ``.
- `anubis$cancelTextureOverlay(Lnet/minecraft/class_332;Lnet/minecraft/class_2960;FLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_31977"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/NoRenderModule.hidePumpkinPowderSnow`.
- `anubis$cancelPortalOverlay(Lnet/minecraft/class_332;FLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_1746"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/NoRenderModule.hideNauseaPortal`.
- `anubis$cancelConfusionOverlay(Lnet/minecraft/class_332;FLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_61980"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/NoRenderModule.hideNauseaPortal`.

## com.example.client.mixin.MixinHandAuraItemInHandRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_759;"}]}]`.

- `anubis$handAura(Lnet/minecraft/class_11684;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_22976"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_11684;method_73002()V"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/HandAuraModule.beginCapture, net/anubis/client/module/impl/render/HandAuraModule.endCapture`.

## com.example.client.mixin.MixinHandsItemInHandRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_759;"}]}]`.

- `anubis$handsPose(Lnet/minecraft/class_742;FFLnet/minecraft/class_1268;FLnet/minecraft/class_1799;FLnet/minecraft/class_4587;Lnet/minecraft/class_11659;ILorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_3228"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_4587;method_22903()V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/HandsModule.shape`.

## com.example.client.mixin.MixinJesusBlockCollisions

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_5329;"}]}]`.

- `anubis$jesusCollisionShape(Lnet/minecraft/class_3726;Lnet/minecraft/class_2680;Lnet/minecraft/class_1941;Lnet/minecraft/class_2338;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)Lnet/minecraft/class_265;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["computeNext"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_3726;method_62878(Lnet/minecraft/class_2680;Lnet/minecraft/class_1941;Lnet/minecraft/class_2338;)Lnet/minecraft/class_265;"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/JesusModule.collisionShape`.

## com.example.client.mixin.MixinJesusEntity

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1297;"}]}]`.

- `anubis$jesusAboveBubbleColumn(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_5700"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}]}}]`. Anubis delegates: ``.
- `anubis$jesusInsideBubbleColumn(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_5764"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}]}}]`. Anubis delegates: ``.

## com.example.client.mixin.MixinJesusLivingEntity

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1309;"}]}]`.

- `anubis$jesusCanStandOnFluid(ZLnet/minecraft/class_3610;)Z`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;", "values": {"method": ["method_26319"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/JesusModule.canWalkOnFluid`.

## com.example.client.mixin.MixinJesusPowderSnowBlock

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_5635;"}]}]`.

- `anubis$jesusWalkOnPowderSnow(ZLnet/minecraft/class_1297;)Z`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;", "values": {"method": ["method_32355"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/JesusModule.canWalkOnPowderSnow`.

## com.example.client.mixin.MixinJumpCirclesLevelRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_761;"}]}]`.

- `anubis$jumpCircles(Lnet/minecraft/class_4587;Lnet/minecraft/class_4597$class_4598;Lnet/minecraft/class_11658;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_62206"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/JumpCirclesModule.renderLevel`.

## com.example.client.mixin.MixinJumpCirclesLivingEntity

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1309;"}]}]`.

- `anubis$jumpCircle(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_6043"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_1309;method_18800(DDD)V"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/JumpCirclesModule.onJump`.

## com.example.client.mixin.MixinKeyboardHandler

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_309;"}]}]`.

- `anubis$openGuiFirst(JILnet/minecraft/class_11908;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapmethod/WrapMethod;", "values": {"method": ["method_1466"]}}]`. Anubis delegates: `net/anubis/client/AnubisClientMod.dropGuiClicks, net/anubis/client/AnubisClientMod.isGuiKey, net/anubis/client/AnubisClientMod.toggleGuiFromKey`.
- `anubis$onKeyPress(JILnet/minecraft/class_11908;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_1466"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/event/impl/KeyInputEvent.dispatch`.

## com.example.client.mixin.MixinLevelRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_761;"}]}]`.

- `anubis$onRenderLevel(Lnet/minecraft/class_4604;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;)Lnet/minecraft/class_4604;`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyArg;", "values": {"method": ["method_22710"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_863;method_23099(Lnet/minecraft/class_4604;DDDF)V"}}, "index": 0}}]`. Anubis delegates: `net/anubis/client/event/impl/Render3DEvent.dispatch, net/anubis/client/util/ShaderOverlay.capture, net/anubis/client/util/TracerEngine.beginFrame`.
- `anubis$noFluidFogOnTop(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lnet/minecraft/class_4184;)Lcom/mojang/blaze3d/buffers/GpuBufferSlice;`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyArg;", "values": {"method": ["method_22710"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_761;method_62199(Lnet/minecraft/class_9909;Lnet/minecraft/class_12075;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lorg/joml/Matrix4f;)V"}}, "index": 2}}]`. Anubis delegates: ``.
- `anubis$overlayAfterShaderPack(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lnet/minecraft/class_4184;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_22710"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/util/ShaderOverlay.pending, net/anubis/client/util/ShaderOverlay.render`.
- `anubis$worldFogForHand(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lnet/minecraft/class_4184;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_22710"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: ``.
- `anubis$keepSkyDuringBlindnessDarkness(Lnet/minecraft/class_4184;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_43788"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/NoRenderModule.hideBlindnessDarkness`.

## com.example.client.mixin.MixinLightTexture

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_765;"}]}]`.

- `anubis$xrayFullbright(FLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_3313"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "FIELD", "target": "Lnet/minecraft/class_765;field_4135:Z", "opcode": 181, "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}], "cancellable": 1}}]`. Anubis delegates: ``.
- `anubis$hideDarknessPulse(F)F`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_3313"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_746;method_66279(Lnet/minecraft/class_6880;F)F"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/NoRenderModule.hideBlindnessDarkness`.
- `anubis$fullbrightGamma(F)F`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyArg;", "values": {"method": ["method_3313"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lcom/mojang/blaze3d/buffers/Std140Builder;putFloat(F)Lcom/mojang/blaze3d/buffers/Std140Builder;", "ordinal": 6}}, "index": 0}}]`. Anubis delegates: `net/anubis/client/module/ModuleRegistry.get, net/anubis/client/module/impl/render/FullbrightModule.gamma, net/anubis/client/module/impl/render/FullbrightModule.isEnabled`.

## com.example.client.mixin.MixinLiquidBlockRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_775;"}]}]`.

- `anubis$xrayFluidAlpha(F)F`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyArg;", "values": {"method": ["method_23072"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_4588;method_22915(FFFF)Lnet/minecraft/class_4588;"}}, "index": 3}}]`. Anubis delegates: ``.

## com.example.client.mixin.MixinLivingEntity

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1309;"}]}]`.

- `anubis$modifySwingDuration(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_6028"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/ModuleRegistry.get, net/anubis/client/module/impl/misc/SlowSwingModule.getModifiedSwingDuration, net/anubis/client/module/impl/misc/SlowSwingModule.isEnabled`.

## com.example.client.mixin.MixinLoadingOverlay

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_425;"}]}]`.

- `anubis$backgroundFill(I)I`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyArg;", "values": {"method": ["method_25394"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_332;method_25294(IIIII)V"}}, "index": 4}}]`. Anubis delegates: `net/anubis/client/gui/LoadingScreens.backgroundColor`.
- `anubis$backgroundClear(I)I`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyArg;", "values": {"method": ["method_25394"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lcom/mojang/blaze3d/systems/CommandEncoder;clearColorTexture(Lcom/mojang/blaze3d/textures/GpuTexture;I)V"}}, "index": 1}}]`. Anubis delegates: `net/anubis/client/gui/LoadingScreens.backgroundColor`.

## com.example.client.mixin.MixinLocalPlayer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_746;"}]}]`.

- `anubis$onMove(Lnet/minecraft/class_1313;Lnet/minecraft/class_243;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapmethod/WrapMethod;", "values": {"method": ["method_5784"]}}]`. Anubis delegates: `net/anubis/client/event/PooledCancellableEventType.hasListeners, net/anubis/client/event/impl/PlayerMoveEvent.move`.
- `anubis$walkServerRotation(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_5773"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}]}}]`. Anubis delegates: `net/anubis/client/util/pvp/ServerRotation.applyForMovement`.
- `anubis$finishServerRotation(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_5773"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/util/pvp/ServerRotation.finishTick`.
- `anubis$freecamAfterInput(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_6007"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_744;method_3129()V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/FreecamModule.drive`.
- `anubis$freecamBeforeSend(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_3136"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/FreecamModule.drive`.
- `anubis$swapOutServerRotation(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_3136"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/util/pvp/ServerRotation.afterSend`.

## com.example.client.mixin.MixinMaceMultiPlayerGameMode

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_636;"}]}]`.

- `anubis$weaponBeforeAttack(Lnet/minecraft/class_1657;Lnet/minecraft/class_1297;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_2918"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_636;method_2911()V"}}]}}]`. Anubis delegates: `net/anubis/client/module/ModuleRegistry.get, net/anubis/client/module/impl/combat/MaceModule.beforeAttack, net/anubis/client/module/impl/combat/MaceModule.isEnabled, net/anubis/client/module/impl/combat/ShieldBreakerModule.beforeAttack, net/anubis/client/module/impl/combat/ShieldBreakerModule.isEnabled, net/anubis/client/module/impl/combat/melee/MeleeAttacks.actedThisTick`.

## com.example.client.mixin.MixinMenusJoinMultiplayerScreen

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_500;"}]}]`.

- `anubis$addOverlay(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_25426"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/gui/AnubisTitleScreen.enabled, net/anubis/client/gui/mainmenu/MultiplayerOverlay.<init>, net/anubis/client/gui/mainmenu/MultiplayerOverlay.show`.
- `anubis$showOverlay(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_48640"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/gui/mainmenu/MultiplayerOverlay.show`.
- `anubis$hideOverlay(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_25432"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/gui/mainmenu/MultiplayerOverlay.hide`.
- `anubis$overlayKeys(Lnet/minecraft/class_11908;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_25404"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/gui/mainmenu/MultiplayerOverlay.keyPressed`.

## com.example.client.mixin.MixinMinecraft

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_310;"}]}]`.

- `anubis$replaceTitleScreen(Lnet/minecraft/class_437;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_1507"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/gui/AnubisTitleScreen.<init>, net/anubis/client/gui/AnubisTitleScreen.enabled`.
- `anubis$windowTitle(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_24287"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/gui/AnubisTitleScreen.enabled`.
- `anubis$onTickStart(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_1574"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}]}}]`. Anubis delegates: `net/anubis/client/event/EventBus.drainAsync, net/anubis/client/event/impl/TickEvent.dispatch, net/anubis/client/util/PerformanceBudget.beginTick, net/anubis/client/util/pvp/ServerRotation.startTick`.
- `anubis$onTickEnd(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_1574"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/event/impl/TickEvent.dispatchPost`.
- `anubis$autoEatIgnoreCrosshair(Lnet/minecraft/class_239$class_240;)Lnet/minecraft/class_239$class_240;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_1583"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_239;method_17783()Lnet/minecraft/class_239$class_240;"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/AutoEatModule.isEating`.

## com.example.client.mixin.MixinMobEffectFogEnvironment

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_7286;"}]}]`.

- `anubis$cancelBlindnessDarknessFog(Lnet/minecraft/class_5636;Lnet/minecraft/class_1297;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_42593"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/NoRenderModule.hideBlindnessDarkness`.

## com.example.client.mixin.MixinModelBlockRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_778;"}]}]`.

- `anubis$xrayAlpha(F)F`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyArg;", "values": {"method": ["method_23073"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_4588;method_22920(Lnet/minecraft/class_4587$class_4665;Lnet/minecraft/class_777;[FFFFF[II)V"}}, "index": 6}}]`. Anubis delegates: ``.

## com.example.client.mixin.MixinMotionBlurGameRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_757;"}]}]`.

- `anubis$motionBlur(Lnet/minecraft/class_9779;ZLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_3192"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_758;method_71108()V"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/MotionBlurModule.afterWorld`.

## com.example.client.mixin.MixinMouseHandler

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_312;"}]}]`.

- `anubis$onMouseButton(JLnet/minecraft/class_11910;ILorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_1601"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/event/impl/MouseInputEvent.dispatch`.
- `anubis$wheel(JDDLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_1598"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/FreecamModule.scroll, net/anubis/client/module/impl/render/ZoomModule.scroll`.
- `anubis$cameraTurn(Lnet/minecraft/class_746;DDLcom/llamalad7/mixinextras/injector/wrapoperation/Operation;D)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_1606"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_746;method_5872(DD)V"}}]}}]`. Anubis delegates: `net/anubis/client/module/ModuleRegistry.get, net/anubis/client/module/impl/combat/AimAssistModule.isEnabled, net/anubis/client/module/impl/combat/AimAssistModule.nudge, net/anubis/client/module/impl/misc/FreecamModule.turn, net/anubis/client/module/impl/render/FreelookModule.cameraMode, net/anubis/client/module/impl/render/FreelookModule.turnCamera, net/anubis/client/module/impl/render/ZoomModule.turnScale`.

## com.example.client.mixin.MixinMovementCorrectionKeyboardInput

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_743;"}]}]`.

- `anubis$shapeInput(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_3129"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/util/pvp/InputControl.shape, net/anubis/client/util/pvp/ServerRotation.correctInput`.

## com.example.client.mixin.MixinMultiPlayerGameMode

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_636;"}]}]`.

- `anubis$beforeAttack(Lnet/minecraft/class_1657;Lnet/minecraft/class_1297;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_2918"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_636;method_2911()V"}}]}}]`. Anubis delegates: `net/anubis/client/module/ModuleRegistry.get, net/anubis/client/module/impl/combat/WTapRecodeModule.beforeAttack, net/anubis/client/module/impl/combat/WTapRecodeModule.isEnabled`.
- `anubis$afterAttack(Lnet/minecraft/class_1657;Lnet/minecraft/class_1297;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_2918"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/ModuleRegistry.get, net/anubis/client/module/impl/combat/WTapRecodeModule.afterAttack, net/anubis/client/module/impl/combat/WTapRecodeModule.isEnabled, net/anubis/client/module/impl/combat/melee/MeleeAttacks.noteAction`.
- `anubis$afterStab(Lnet/minecraft/class_12125;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_75407"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/ModuleRegistry.get, net/anubis/client/module/impl/combat/WTapRecodeModule.afterStab, net/anubis/client/module/impl/combat/WTapRecodeModule.isEnabled, net/anubis/client/module/impl/combat/melee/MeleeAttacks.noteAction`.
- `anubis$afterUseItem(Lnet/minecraft/class_1657;Lnet/minecraft/class_1268;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_2919"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/combat/melee/MeleeAttacks.noteAction`.
- `anubis$afterUseItemOn(Lnet/minecraft/class_746;Lnet/minecraft/class_1268;Lnet/minecraft/class_3965;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_2896"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/combat/melee/MeleeAttacks.noteAction`.
- `anubis$afterInteract(Lnet/minecraft/class_1657;Lnet/minecraft/class_1297;Lnet/minecraft/class_1268;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_2905"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/combat/melee/MeleeAttacks.noteAction`.
- `anubis$afterInteractAt(Lnet/minecraft/class_1657;Lnet/minecraft/class_1297;Lnet/minecraft/class_3966;Lnet/minecraft/class_1268;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_2917"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/combat/melee/MeleeAttacks.noteAction`.
- `anubis$afterRelease(Lnet/minecraft/class_1657;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_2897"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/combat/melee/MeleeAttacks.noteAction`.

## com.example.client.mixin.MixinNameProtectBossHealthOverlay

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_337;"}]}]`.

- `anubis$nameProtectBossName(Lnet/minecraft/class_2561;)Lnet/minecraft/class_2561;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_1796"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_345;method_5414()Lnet/minecraft/class_2561;"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NameProtectModule.protect`.

## com.example.client.mixin.MixinNameProtectClientPacketListener

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_634;"}]}]`.

- `anubis$nameProtectPlayerList(Lnet/minecraft/class_2703;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11113"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NameProtectModule.onPlayerInfoApplied`.
- `anubis$nameProtectDeathMessage(Lnet/minecraft/class_2561;)Lnet/minecraft/class_2561;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_34075"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_5892;comp_2276()Lnet/minecraft/class_2561;"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NameProtectModule.protect`.

## com.example.client.mixin.MixinNameProtectEntityRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_897;"}]}]`.

- `anubis$nameProtectNameTag(Lnet/minecraft/class_2561;)Lnet/minecraft/class_2561;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_62354"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_897;method_62426(Lnet/minecraft/class_1297;)Lnet/minecraft/class_2561;"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NameProtectModule.protect`.

## com.example.client.mixin.MixinNameProtectGui

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_329;"}]}]`.

- `anubis$nameProtectTitle(Lnet/minecraft/class_2561;)Lnet/minecraft/class_2561;`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyVariable;", "values": {"method": ["method_34004"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}, "argsOnly": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NameProtectModule.protect`.
- `anubis$nameProtectSubtitle(Lnet/minecraft/class_2561;)Lnet/minecraft/class_2561;`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyVariable;", "values": {"method": ["method_34002"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}, "argsOnly": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NameProtectModule.protect`.
- `anubis$nameProtectActionBar(Lnet/minecraft/class_2561;)Lnet/minecraft/class_2561;`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyVariable;", "values": {"method": ["method_1758"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}, "argsOnly": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NameProtectModule.protect`.
- `anubis$nameProtectSidebarTitle(Lnet/minecraft/class_2561;)Lnet/minecraft/class_2561;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_1757"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_266;method_1114()Lnet/minecraft/class_2561;"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NameProtectModule.protect`.
- `anubis$nameProtectSidebarLineWidth(Lnet/minecraft/class_2561;)Lnet/minecraft/class_2561;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_1757"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "FIELD", "opcode": 180, "ordinal": 0, "target": "Lnet/minecraft/class_329$class_9016;comp_2131:Lnet/minecraft/class_2561;"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NameProtectModule.protect`.
- `anubis$nameProtectSidebarLineDraw(Lnet/minecraft/class_2561;)Lnet/minecraft/class_2561;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_1757"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "FIELD", "opcode": 180, "ordinal": 1, "target": "Lnet/minecraft/class_329$class_9016;comp_2131:Lnet/minecraft/class_2561;"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NameProtectModule.protect`.

## com.example.client.mixin.MixinNoInvLeakGui

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_329;"}]}]`.

- `anubis$noInvLeakHotbar(Lnet/minecraft/class_1799;III)Lnet/minecraft/class_1799;`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyVariable;", "values": {"method": ["method_1762"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}, "argsOnly": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NoInvLeakModule.hotbar`.
- `anubis$noInvLeakItemName(Lnet/minecraft/class_332;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_1749"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NoInvLeakModule.hidesItemName, net/anubis/client/module/impl/misc/NoInvLeakModule.overlaying, net/anubis/client/module/impl/misc/noinvleak/StreamOverlay.itemName`.
- `anubis$noInvLeakSelection(Lnet/minecraft/class_332;Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/class_2960;IIII)Z`: `[{"type": "Lcom/llamalad7/mixinextras/injector/WrapWithCondition;", "values": {"method": ["method_1759"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_332;method_52706(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/class_2960;IIII)V"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NoInvLeakModule.hidesSelectedSlot, net/anubis/client/module/impl/misc/NoInvLeakModule.overlaying, net/anubis/client/module/impl/misc/noinvleak/StreamOverlay.selection`.

## com.example.client.mixin.MixinNoInvLeakContainerScreen

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_465;"}]}]`.

- `anubis$noInvLeakSlot(Lnet/minecraft/class_1735;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)Lnet/minecraft/class_1799;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_2385"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_1735;method_7677()Lnet/minecraft/class_1799;"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NoInvLeakModule.container, net/anubis/client/module/impl/misc/NoInvLeakModule.ownSlot`.
- `anubis$noInvLeakCarried(Lnet/minecraft/class_1799;II)Lnet/minecraft/class_1799;`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyVariable;", "values": {"method": ["method_2382"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}, "argsOnly": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NoInvLeakModule.carried`.
- `anubis$noInvLeakTooltip(Lnet/minecraft/class_332;IILorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_2380"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NoInvLeakModule.hidesTooltips, net/anubis/client/module/impl/misc/NoInvLeakModule.overlaying, net/anubis/client/module/impl/misc/NoInvLeakModule.ownSlot, net/anubis/client/module/impl/misc/noinvleak/StreamOverlay.tooltip`.

## com.example.client.mixin.MixinNoInvLeakSlot

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1735;"}]}]`.

- `anubis$noInvLeakSilhouette(Lnet/minecraft/class_2960;)Lnet/minecraft/class_2960;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;", "values": {"method": ["method_7679"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NoInvLeakModule.overlaying`.

## com.example.client.mixin.MixinNoRenderWeather

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_9976;"}]}]`.

- `anubis$hideWeather(Lnet/minecraft/class_4597;Lnet/minecraft/class_243;Lnet/minecraft/class_12077;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_62320"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/NoRenderModule.hideWeather`.
- `anubis$hideRainSplashes(Lnet/minecraft/class_638;Lnet/minecraft/class_4184;ILnet/minecraft/class_4066;ILorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_62319"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/NoRenderModule.hideWeather`.

## com.example.client.mixin.MixinNoRenderLavaFog

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_11401;"}]}]`.

- `anubis$clearLavaFog(Lnet/minecraft/class_7285;Lnet/minecraft/class_4184;Lnet/minecraft/class_638;FLnet/minecraft/class_9779;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_42591"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/NoRenderModule.hideLavaFog`.

## com.example.client.mixin.MixinNoRenderEntityFire

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_761;"}]}]`.

- `anubis$hideEntityFire(Lnet/minecraft/class_1297;FLorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_72914"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/NoRenderModule.hideEntityFire`.

## com.example.client.mixin.MixinFakeStatsScoreboard

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_269;"}]}]`.

- `anubis$fakeSidebar(Lnet/minecraft/class_8646;Lnet/minecraft/class_266;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_1158"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/FakeStats.redirect`.

## com.example.client.mixin.MixinFakeStatsPlayerTabOverlay

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_355;"}]}]`.

- `anubis$fakeStatsTabName(Lnet/minecraft/class_2561;Lnet/minecraft/class_640;)Lnet/minecraft/class_2561;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;", "values": {"method": ["method_1918"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/FakeStats.tabName`.
- `anubis$fakeStatsTabScore(Lnet/minecraft/class_269;Lnet/minecraft/class_9015;Lnet/minecraft/class_266;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)Lnet/minecraft/class_9013;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_1919"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_269;method_55430(Lnet/minecraft/class_9015;Lnet/minecraft/class_266;)Lnet/minecraft/class_9013;"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/FakeStats.tabScore`.
- `anubis$fakeStatsTabHeader(Lnet/minecraft/class_5348;)Lnet/minecraft/class_5348;`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyArg;", "values": {"method": ["method_1919"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "ordinal": 0, "target": "Lnet/minecraft/class_327;method_1728(Lnet/minecraft/class_5348;I)Ljava/util/List;"}}, "index": 0}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/FakeStats.tabText`.
- `anubis$fakeStatsTabFooter(Lnet/minecraft/class_5348;)Lnet/minecraft/class_5348;`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyArg;", "values": {"method": ["method_1919"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "ordinal": 1, "target": "Lnet/minecraft/class_327;method_1728(Lnet/minecraft/class_5348;I)Ljava/util/List;"}}, "index": 0}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/FakeStats.tabText`.

## com.example.client.mixin.MixinNameProtectGuiMessage

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_303;"}]}]`.

- `anubis$nameProtectChatLine(Lnet/minecraft/class_5348;)Lnet/minecraft/class_5348;`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyArg;", "values": {"method": ["method_75757"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_341;method_1850(Lnet/minecraft/class_5348;ILnet/minecraft/class_327;)Ljava/util/List;"}}, "index": 0}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NameProtectModule.protectChatLine`.

## com.example.client.mixin.MixinNameProtectPlayerTabOverlay

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_355;"}]}]`.

- `anubis$nameProtectTabName(Lnet/minecraft/class_2561;)Lnet/minecraft/class_2561;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;", "values": {"method": ["method_1918"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NameProtectModule.protect`.
- `anubis$nameProtectTabHeader(Lnet/minecraft/class_5348;)Lnet/minecraft/class_5348;`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyArg;", "values": {"method": ["method_1919"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "ordinal": 0, "target": "Lnet/minecraft/class_327;method_1728(Lnet/minecraft/class_5348;I)Ljava/util/List;"}}, "index": 0}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NameProtectModule.protectText`.
- `anubis$nameProtectTabFooter(Lnet/minecraft/class_5348;)Lnet/minecraft/class_5348;`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyArg;", "values": {"method": ["method_1919"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "ordinal": 1, "target": "Lnet/minecraft/class_327;method_1728(Lnet/minecraft/class_5348;I)Ljava/util/List;"}}, "index": 0}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NameProtectModule.protectText`.
- `anubis$nameProtectTabHead(Lnet/minecraft/class_640;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)Lnet/minecraft/class_8685;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_1919"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_640;method_52810()Lnet/minecraft/class_8685;"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NameProtectModule.hiddenHead, net/anubis/client/module/impl/misc/NameProtectModule.hidesName`.

## com.example.client.mixin.MixinNameProtectTextDisplay

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_8113$class_8123;"}]}]`.

- `anubis$nameProtectRefresh(Lnet/minecraft/class_8113$class_8123$class_8125;)Lnet/minecraft/class_8113$class_8123$class_8125;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_48905"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "FIELD", "opcode": 180, "ordinal": 0, "target": "Lnet/minecraft/class_8113$class_8123;field_42442:Lnet/minecraft/class_8113$class_8123$class_8125;"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NameProtectModule.generation`.
- `anubis$nameProtectText(Lnet/minecraft/class_2561;)Lnet/minecraft/class_2561;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_48905"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_8113$class_8123$class_8230;comp_1334()Lnet/minecraft/class_2561;"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/NameProtectModule.protect`.

## com.example.client.mixin.MixinNoJumpDelayLivingEntity

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1309;"}]}]`.

- `anubis$noJumpDelay(Lnet/minecraft/class_1309;ILcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_6007"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "FIELD", "target": "Lnet/minecraft/class_1309;field_6228:I", "opcode": 181}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/NoJumpDelayModule.rearm`.

## com.example.client.mixin.MixinPharaohAvatarRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1007;"}]}]`.

- `anubis$addHeadCosmetics(Lnet/minecraft/class_5617$class_5618;ZLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["<init>"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/PharaohHeaddressLayer.<init>`.

## com.example.client.mixin.MixinPlayer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1657;"}]}]`.

- `anubis$onAttack(Lnet/minecraft/class_1297;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_7324"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}]}}]`. Anubis delegates: `net/anubis/client/event/impl/PlayerAttackEntityEvent.dispatch`.
- `anubis$flySpeed(F)F`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;", "values": {"method": ["method_49484"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/FlyModule.getOffGroundSpeed`.

## com.example.client.mixin.MixinPlayerRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1007;"}]}]`.

- `anubis$injectSkin(Lnet/minecraft/class_11890;Lnet/minecraft/class_10055;FLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_62604"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/module/ModuleRegistry.get, net/anubis/client/module/impl/misc/SkinChangeModule.isEnabled, net/anubis/client/module/impl/misc/SkinChangeModule.skinFor`.
- `anubis$suppressVanillaNameTag(Lnet/minecraft/class_10055;Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;Lnet/minecraft/class_12075;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_4213"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/NametagsModule.hideVanillaNametags`.

## com.example.client.mixin.MixinProxyConnection

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_2535;"}]}]`.

- `anubis$routeThroughProxy(Lio/netty/bootstrap/Bootstrap;Lio/netty/channel/ChannelHandler;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;Ljava/net/InetSocketAddress;)Lio/netty/bootstrap/AbstractBootstrap;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_52271"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lio/netty/bootstrap/Bootstrap;handler(Lio/netty/channel/ChannelHandler;)Lio/netty/bootstrap/AbstractBootstrap;"}}]}}]`. Anubis delegates: `net/anubis/client/proxy/ProxyRouting.route`.

## com.example.client.mixin.MixinProxyServerStatusPinger

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_644;"}], "priority": 1100}]`.

- `anubis$routeLegacyPing(Lio/netty/bootstrap/Bootstrap;Lio/netty/channel/ChannelHandler;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;Ljava/net/InetSocketAddress;)Lio/netty/bootstrap/AbstractBootstrap;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_3001"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lio/netty/bootstrap/Bootstrap;handler(Lio/netty/channel/ChannelHandler;)Lio/netty/bootstrap/AbstractBootstrap;"}}], "require": 0}}]`. Anubis delegates: `net/anubis/client/proxy/ProxyRouting.route`.

## com.example.client.mixin.MixinRotationMultiPlayerGameMode

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_636;"}]}]`.

- `anubis$useItemRotation(Lnet/minecraft/class_1657;Lnet/minecraft/class_1268;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)Lnet/minecraft/class_1269;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapmethod/WrapMethod;", "values": {"method": ["method_2919"]}}]`. Anubis delegates: ``.
- `anubis$releaseRotation(Lnet/minecraft/class_1657;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapmethod/WrapMethod;", "values": {"method": ["method_2897"]}}]`. Anubis delegates: ``.
- `anubis$attackRotationGate(Lnet/minecraft/class_1657;Lnet/minecraft/class_1297;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_2918"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1, "order": 950}}]`. Anubis delegates: `net/anubis/client/util/pvp/ServerRotation.misses`.
- `anubis$interactRotationGate(Lnet/minecraft/class_1657;Lnet/minecraft/class_1297;Lnet/minecraft/class_1268;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_2905"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1, "order": 950}}]`. Anubis delegates: `net/anubis/client/util/pvp/ServerRotation.misses`.
- `anubis$interactAtRotationGate(Lnet/minecraft/class_1657;Lnet/minecraft/class_1297;Lnet/minecraft/class_3966;Lnet/minecraft/class_1268;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_2917"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1, "order": 950}}]`. Anubis delegates: `net/anubis/client/util/pvp/ServerRotation.misses`.
- `anubis$useItemOnRotationGate(Lnet/minecraft/class_746;Lnet/minecraft/class_1268;Lnet/minecraft/class_3965;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_2896"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1, "order": 950}}]`. Anubis delegates: `net/anubis/client/util/pvp/ServerRotation.missesBlock`.
- `anubis$startDestroyRotationGate(Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_2910"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1, "order": 950}}]`. Anubis delegates: `net/anubis/client/util/pvp/ServerRotation.missesBlock`.

## com.example.client.mixin.MixinScreenEffectRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_4603;"}]}]`.

- `anubis$undoCustomFov(Lnet/minecraft/class_4587;)Lnet/minecraft/class_4587;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_23067"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "NEW", "target": "()Lnet/minecraft/class_4587;"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/CustomFovModule.screenEffectScale`.
- `anubis$cancelFireOverlay(Lnet/minecraft/class_4587;Lnet/minecraft/class_4597;Lnet/minecraft/class_1058;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_23070"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/NoRenderModule.hideFireOverlay`.

## com.example.client.mixin.MixinSectionCompiler

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_9810;"}]}]`.

- `anubis$xrayBlockState(Lnet/minecraft/class_853;Lnet/minecraft/class_2338;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)Lnet/minecraft/class_2680;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_60904"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_853;method_8320(Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/XrayModule$Snapshot.alphaFor`.
- `anubis$xrayBlockLayer(Lnet/minecraft/class_2680;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)Lnet/minecraft/class_11515;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_60904"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_4696;method_23679(Lnet/minecraft/class_2680;)Lnet/minecraft/class_11515;"}}]}}]`. Anubis delegates: ``.
- `anubis$xrayFluidLayer(Lnet/minecraft/class_3610;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)Lnet/minecraft/class_11515;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_60904"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_4696;method_23680(Lnet/minecraft/class_3610;)Lnet/minecraft/class_11515;"}}]}}]`. Anubis delegates: ``.
- `anubis$xrayResetAlpha(Lnet/minecraft/class_4076;Lnet/minecraft/class_853;Lnet/minecraft/class_8251;Lnet/minecraft/class_750;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_60904"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: ``.

## com.example.client.mixin.MixinShaderEspLevelRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_761;"}]}]`.

- `anubis$glowEspKeepState(Lnet/minecraft/class_1297;FLorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_72914"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "RETURN"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/ShaderEspModule.extracted`.
- `anubis$glowEsp(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;Lnet/minecraft/class_4184;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_22710"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/ShaderEspModule.afterLevel`.

## com.example.client.mixin.MixinSkinChangeItemInHandRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_759;"}]}]`.

- `anubis$skinChangeArm(Lnet/minecraft/class_1007;Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;ILnet/minecraft/class_2960;ZLcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_3219", "method_3216"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_1007;method_4220(Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;ILnet/minecraft/class_2960;Z)V"}}, {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_1007;method_4221(Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;ILnet/minecraft/class_2960;Z)V"}}]}}]`. Anubis delegates: `net/anubis/client/module/ModuleRegistry.get, net/anubis/client/module/impl/misc/SkinChangeModule.isEnabled, net/anubis/client/module/impl/misc/SkinChangeModule.skinFor`.

## com.example.client.mixin.MixinSneakKeyboardInput

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_743;"}]}]`.

- `anubis$forceSneak(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_3129"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/SneakModule.doVanilla`.

## com.example.client.mixin.MixinSneakLocalPlayer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_746;"}]}]`.

- `anubis$packetSneak(Lnet/minecraft/class_10185;)Lnet/minecraft/class_10185;`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_5773"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "FIELD", "target": "Lnet/minecraft/class_744;field_54155:Lnet/minecraft/class_10185;", "opcode": 180}}], "require": 3, "allow": 3}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/SneakModule.doPacket`.

## com.example.client.mixin.MixinSneakServerboundInteractPacket

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_2824;"}]}]`.

- `anubis$packetSneak(Z)Z`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyVariable;", "values": {"method": ["<init>(IZLnet/minecraft/class_2824$class_5906;)V"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}, "ordinal": 0, "argsOnly": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/SneakModule.doPacket`.

## com.example.client.mixin.MixinSpamClientPacketListener

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_634;"}]}]`.

- `anubis$spamRelogin(Lnet/minecraft/class_2678;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_11120"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/SpamModule.onGameLeft`.
- `anubis$spamReconfiguration(Lnet/minecraft/class_8588;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_52798"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2600;method_11074(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;Lnet/minecraft/class_11980;)V", "shift": {"enum_type": "Lorg/spongepowered/asm/mixin/injection/At$Shift;", "value": "AFTER"}}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/SpamModule.onGameLeft`.

## com.example.client.mixin.MixinSpamMinecraft

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_310;"}]}]`.

- `anubis$spamGameLeft(Lnet/minecraft/class_437;ZZLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_18096"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/SpamModule.onGameLeft`.
- `anubis$spamDisconnectedScreen(Lnet/minecraft/class_437;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_1507"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/SpamModule.onDisconnectedScreen`.

## com.example.client.mixin.MixinSpeedDeltaTrackerTimer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_9779$class_9781;"}]}]`.

- `anubis$speedTimer(JLorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_60639"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "FIELD", "target": "Lnet/minecraft/class_9779$class_9781;field_51962:J", "opcode": 181}}]}}]`. Anubis delegates: ``.

## com.example.client.mixin.MixinSpeedLocalPlayer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_746;"}]}]`.

- `anubis$speedMove(Lnet/minecraft/class_243;Lnet/minecraft/class_1313;)Lnet/minecraft/class_243;`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/ModifyVariable;", "values": {"method": ["method_5784"], "at": {"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}, "argsOnly": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/SpeedModule.onSelfMove`.

## com.example.client.mixin.MixinSpeedMineMultiPlayerGameMode

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_636;"}]}]`.

- `anubis$speedMineDelay(Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_2902"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/SpeedMineModule.dropsDelay`.
- `anubis$speedMineProgress(Lnet/minecraft/class_2680;Lnet/minecraft/class_1657;Lnet/minecraft/class_1922;Lnet/minecraft/class_2338;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)F`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_2902"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_2680;method_26165(Lnet/minecraft/class_1657;Lnet/minecraft/class_1922;Lnet/minecraft/class_2338;)F"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/SpeedMineModule.scaleProgress`.

## com.example.client.mixin.MixinSwingAnimationItemInHandRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_759;"}]}]`.

- `anubis$swingStyle(Lnet/minecraft/class_759;FLnet/minecraft/class_4587;ILnet/minecraft/class_1306;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;Lnet/minecraft/class_1268;)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_3228"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_759;method_65816(FLnet/minecraft/class_4587;ILnet/minecraft/class_1306;)V"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/SwingAnimationModule.covers, net/anubis/client/module/impl/render/SwingAnimationModule.pose`.

## com.example.client.mixin.MixinSwingAnimationLivingEntity

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1309;"}]}]`.

- `anubis$swingTempo(I)I`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_6119", "method_23667"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_1309;method_6028()I"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/SwingAnimationModule.swingTicks`.
- `anubis$latchSwing(Lnet/minecraft/class_1268;ZLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_23667"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "FIELD", "target": "Lnet/minecraft/class_1309;field_6252:Z", "opcode": 181}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/SwingAnimationModule.latch`.

## com.example.client.mixin.MixinTimeChangerClientLevel

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_638;"}]}]`.

- `anubis$timeChangerServerDayTime(Lnet/minecraft/class_638$class_5271;JLcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_29089"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_638$class_5271;method_165(J)V"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/TimeChangerModule.interceptServerDayTime`.

## com.example.client.mixin.MixinTrialSpawnerRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_8984;"}]}]`.

- `anubis$cancelSpawnerMobExtract(Lnet/minecraft/class_11973;FLnet/minecraft/class_1297;Lnet/minecraft/class_898;DDLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_74396"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: `net/anubis/client/module/impl/render/NoRenderModule.hideSpawnerMob`.

## com.example.client.mixin.MixinTridentBoostTridentItem

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1835;"}]}]`.

- `anubis$tridentBoostPush(Lnet/minecraft/class_1657;DDDLcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_7840"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_1657;method_5762(DDD)V"}}], "require": 0}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/TridentBoostModule.riptideMultiplier`.
- `anubis$tridentBoostUseOutOfWater(Z)Z`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_7836"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_1657;method_5721()Z"}}], "require": 0}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/TridentBoostModule.allowOutOfWater`.
- `anubis$tridentBoostReleaseOutOfWater(Z)Z`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_7840"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_1657;method_5721()Z"}}], "require": 0}}]`. Anubis delegates: `net/anubis/client/module/impl/movement/TridentBoostModule.allowOutOfWater`.

## com.example.client.mixin.MixinTridentItem

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1835;"}]}]`.

- `anubis$allowOutOfWater(Z)Z`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_7836", "method_7840"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_1657;method_5721()Z"}}], "require": 0}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/TridentUtilModule.allowOutOfWater`.
- `anubis$scaleMinCharge(I)I`: `[{"type": "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;", "values": {"method": ["method_7840"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "CONSTANT", "args": ["intValue=10"]}}], "require": 0}}]`. Anubis delegates: `net/anubis/client/module/impl/misc/TridentUtilModule.minChargeTicks`.

## com.example.client.mixin.MixinVisGraph

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_852;"}]}]`.

- `anubis$noOcclusion(Lnet/minecraft/class_2338;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_3682"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}], "cancellable": 1}}]`. Anubis delegates: ``.

## com.example.client.mixin.MixinHandModelItemInHandRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_759;"}]}]`.

- `anubis$longRightArm(Lnet/minecraft/class_1007;Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;ILnet/minecraft/class_2960;ZLcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_3219", "method_3216"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_1007;method_4220(Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;ILnet/minecraft/class_2960;Z)V"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/HandModelModule.shoulder`.
- `anubis$longLeftArm(Lnet/minecraft/class_1007;Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;ILnet/minecraft/class_2960;ZLcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_3219", "method_3216"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_1007;method_4221(Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;ILnet/minecraft/class_2960;Z)V"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/HandModelModule.shoulder`.

## com.example.client.mixin.MixinWeaponInspectItemInHandRenderer

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_759;"}]}]`.

- `anubis$inspectHands(FLnet/minecraft/class_4587;Lnet/minecraft/class_11659;Lnet/minecraft/class_746;ILorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_22976"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/WeaponInspectModule.handsBegin`.
- `anubis$inspectItem(Lnet/minecraft/class_759;Lnet/minecraft/class_1309;Lnet/minecraft/class_1799;Lnet/minecraft/class_811;Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;ILcom/llamalad7/mixinextras/injector/wrapoperation/Operation;Lnet/minecraft/class_1268;)V`: `[{"type": "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;", "values": {"method": ["method_3228"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "INVOKE", "target": "Lnet/minecraft/class_759;method_3233(Lnet/minecraft/class_1309;Lnet/minecraft/class_1799;Lnet/minecraft/class_811;Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;I)V"}}]}}]`. Anubis delegates: `net/anubis/client/module/impl/render/HandModelModule.renderArm, net/anubis/client/module/impl/render/WeaponInspectModule.renderItem`.

## com.example.client.mixin.MixinWindow

Configuration: `anubis.mixins.json`. Targets: `[{"value": [{"class": "Lnet/minecraft/class_1041;"}]}]`.

- `anubis$setIcon(Lnet/minecraft/class_3262;Lnet/minecraft/class_8518;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["method_4491"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "TAIL"}}]}}]`. Anubis delegates: `net/anubis/client/gui/WindowIcon.apply`.

## com.example.client.mixin.indigo.MixinAbstractTerrainRenderContext

Configuration: `anubis.indigo.mixins.json`. Targets: `[{"value": [{"class": "Lnet/fabricmc/fabric/impl/client/indigo/renderer/render/AbstractTerrainRenderContext;"}], "remap": 0}]`.

- `anubis$xrayBufferQuad(Lnet/fabricmc/fabric/impl/client/indigo/renderer/mesh/MutableQuadViewImpl;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V`: `[{"type": "Lorg/spongepowered/asm/mixin/injection/Inject;", "values": {"method": ["bufferQuad"], "at": [{"type": "Lorg/spongepowered/asm/mixin/injection/At;", "values": {"value": "HEAD"}}]}}]`. Anubis delegates: ``.
