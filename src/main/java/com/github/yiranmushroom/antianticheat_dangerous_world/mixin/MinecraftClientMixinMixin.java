package com.github.yiranmushroom.antianticheat_dangerous_world.mixin;

import com.bawnorton.mixinsquared.TargetHandler;
import com.github.yiranmushroom.antianticheat_dangerous_world.mixins.api.ICheatConfig;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Minecraft.class, priority = 1001)
public abstract class MinecraftClientMixinMixin {
    @TargetHandler(mixin = "aliveandwell.aliveandwell.mixins.aliveandwell.client.MinecraftClientMixin", name = "method_24287")
    @Inject(method = "@MixinSquared:Handler", at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;toString()Ljava/lang/String;"))
    private void acc$addRemoveAntiCheatInfo(CallbackInfoReturnable<String> cir, @Local(name = "stringBuilder") StringBuilder stringBuilder) {
        if (ICheatConfig.shouldReportCheatMessage()) {
            stringBuilder.append(" (反作弊已关闭 By Yiran)");
        }
    }
}
