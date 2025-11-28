package com.github.yiranmushroom.antianticheat_dangerous_world.mixin;

import aliveandwell.aliveandwell.AliveAndWellMain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AliveAndWellMain.class)
public class AliveAndWellMainMixin {
    @Shadow(remap = false)
    public static boolean canCreative = true;
}
