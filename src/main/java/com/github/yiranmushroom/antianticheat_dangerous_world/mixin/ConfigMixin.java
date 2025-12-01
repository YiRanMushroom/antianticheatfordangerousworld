package com.github.yiranmushroom.antianticheat_dangerous_world.mixin;

import aliveandwell.aliveandwell.config.Config;
import com.github.yiranmushroom.antianticheat_dangerous_world.mixins.api.ICheatConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Config.class, remap = false)
public abstract class ConfigMixin {
    @Shadow
    public abstract void save();

    @Inject(method = "load", at = @At("RETURN"))
    private void aac$writeConfigIfNeeded(CallbackInfo ci) {
        if (ICheatConfig.shouldSaveConfig()) {
            this.save();
        }
    }

    @Inject(method = "save", at = @At("RETURN"))
    private void aac$markConfigSaved(CallbackInfo ci) {
        ICheatConfig.markConfigSaved();
    }
}
