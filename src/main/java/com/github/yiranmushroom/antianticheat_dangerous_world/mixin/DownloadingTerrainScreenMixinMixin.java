package com.github.yiranmushroom.antianticheat_dangerous_world.mixin;

import aliveandwell.aliveandwell.mixins.aliveandwell.client.DownloadingTerrainScreenMixin;
import com.bawnorton.mixinsquared.TargetHandler;
import com.github.yiranmushroom.antianticheat_dangerous_world.mixins.api.ICheatConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ReceivingLevelScreen.class, priority = 1001)
public abstract class DownloadingTerrainScreenMixinMixin extends Screen {
    protected DownloadingTerrainScreenMixinMixin(Component component) {
        super(component);
    }

    @Inject(method = "@MixinSquared:Handler", at = @At("RETURN"))
    @TargetHandler(mixin = "aliveandwell.aliveandwell.mixins.aliveandwell.client.DownloadingTerrainScreenMixin", name = "render")
    private void aac$renderCheatersMessage(PoseStack matrices, int mouseX, int mouseY, float delta, CallbackInfo originalCi, CallbackInfo targetCi) {
        if (ICheatConfig.shouldReportCheatMessage()) {
            var reportMessage = Component.translatable("aacfdw.loadgame.info0");
            // Color should be green: 0x55FF55
            ReceivingLevelScreen.drawCenteredString(matrices, this.font, reportMessage, this.width / 2, - this.font.lineHeight / 2 + 100, 0x55FF55);
        }
    }
}
