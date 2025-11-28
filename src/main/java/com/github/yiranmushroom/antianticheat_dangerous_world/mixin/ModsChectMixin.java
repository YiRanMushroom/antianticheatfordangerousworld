package com.github.yiranmushroom.antianticheat_dangerous_world.mixin;

import aliveandwell.aliveandwell.util.ModsChect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ModsChect.class)
public class ModsChectMixin {
    @Inject(method = "chectMods", at = @At("RETURN"), cancellable = true, remap = false)
    private void aac$removeClientDetection(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "chectModsServer", at = @At("RETURN"), cancellable = true, remap = false)
    private void aac$removeServerDetection(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}