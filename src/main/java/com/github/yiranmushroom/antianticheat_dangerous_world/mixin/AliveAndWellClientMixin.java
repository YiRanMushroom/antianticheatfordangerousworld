package com.github.yiranmushroom.antianticheat_dangerous_world.mixin;

import aliveandwell.aliveandwell.client.AliveAndWellClient;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.github.yiranmushroom.antianticheat_dangerous_world.AntiAntiCheatForDangerousWorld.LOGGER;

@Mixin(value = AliveAndWellClient.class)
public class AliveAndWellClientMixin {
    @WrapOperation(method = "onInitializeClient", at = @At(value = "INVOKE",
            target = "Laliveandwell/aliveandwell/registry/events/ScreenEventsClient;init()V"), remap = false)
    private void acc$cancelInitScreenEventsClient(Operation<Void> original) {
        LOGGER.info("AliveAndWellClient InitClientScreenEvents has been cancelled by AntiAntiCheatForDangerousWorld.");
    }
}
