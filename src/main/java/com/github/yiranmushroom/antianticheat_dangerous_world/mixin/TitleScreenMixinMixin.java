package com.github.yiranmushroom.antianticheat_dangerous_world.mixin;

import com.bawnorton.mixinsquared.TargetHandler;
import com.github.yiranmushroom.antianticheat_dangerous_world.mixins.api.ICheatConfig;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TitleScreen.class, priority = 1500)
public abstract class TitleScreenMixinMixin extends Screen {
    protected TitleScreenMixinMixin(Component component) {
        super(component);
    }

    @TargetHandler(mixin = "aliveandwell.aliveandwell.mixins.aliveandwell.client.TitleScreenMixin", name = "render")
    @Inject(method = "@MixinSquared:Handler", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;ceil(F)I"))
    private void acc$modifyRenderedString(PoseStack poseStack, int i, int j, float f, CallbackInfo ci, CallbackInfo targetCi,
                                          @Local(name = "string1") LocalRef<String> string1Ref) {
        if (ICheatConfig.shouldReportCheatMessage()) {
            string1Ref.set(string1Ref.get() + " 反作弊已关闭 By Yiran");
        }
    }
}
