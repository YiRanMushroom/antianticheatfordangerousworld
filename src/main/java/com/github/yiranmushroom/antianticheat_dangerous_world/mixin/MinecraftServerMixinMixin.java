package com.github.yiranmushroom.antianticheat_dangerous_world.mixin;

import com.bawnorton.mixinsquared.TargetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MinecraftServer.class, priority = 2000)
public abstract class MinecraftServerMixinMixin {
    @Shadow
    @Final
    protected WorldData worldData;

    @TargetHandler(mixin = "aliveandwell.aliveandwell.mixins.aliveandwell.MinecraftServerMixin",
            name = "method_3790")
    @Inject(method = "@MixinSquared:Handler", at = @At("HEAD"), cancellable = true)
    private void aac$overrideDefaultGameMode(CallbackInfoReturnable<GameType> cir) {
        cir.setReturnValue(this.worldData.getGameType());
    }
}
