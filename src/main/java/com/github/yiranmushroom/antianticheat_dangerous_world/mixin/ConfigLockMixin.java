package com.github.yiranmushroom.antianticheat_dangerous_world.mixin;

import aliveandwell.aliveandwell.util.ConfigLock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ConfigLock.class)
public class ConfigLockMixin {
    @Inject(method = "isDefaultConfig", at = @At("RETURN"), cancellable = true, remap = false)
    private void aac$removeConfigLockDetection(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "isDefaultConfigConnect", at = @At("RETURN"), cancellable = true, remap = false)
    private void aac$removeConfigLockConnectDetection(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
