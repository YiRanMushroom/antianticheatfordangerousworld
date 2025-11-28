package com.github.yiranmushroom.antianticheat_dangerous_world.mixin;

import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Difficulty;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CreateWorldScreen.class, priority = 1001)
public abstract class CreateWorldScreenMixin extends Screen {
    @Shadow
    private CycleButton<Boolean> commandsButton;

    @Shadow
    private CycleButton<CreateWorldScreen.SelectedGameMode> modeButton;

    @Shadow
    private CycleButton<Difficulty> difficultyButton;

    protected CreateWorldScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void aac$removeDangerousWorldWarning(CallbackInfo ci) {
        if (!this.modeButton.active) {
            this.commandsButton.active = true;
            this.modeButton.active = true;
            this.difficultyButton.active = true;
            this.modeButton.setValue(CreateWorldScreen.SelectedGameMode.SURVIVAL);
            this.difficultyButton.setValue(Difficulty.NORMAL);
        }
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void aac$logInit(CallbackInfo ci) {

    }
}
