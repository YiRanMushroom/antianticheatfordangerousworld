package com.github.yiranmushroom.antianticheat_dangerous_world.mixin;

import com.bawnorton.mixinsquared.TargetHandler;
import com.github.yiranmushroom.antianticheat_dangerous_world.mixins.api.ICheatConfig;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.authlib.GameProfile;
import net.minecraft.ChatFormatting;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.ServerOpList;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerList.class, priority = 1001)
public abstract class PlayerManagerMixinMixin {
    @Inject(method = "@MixinSquared:Handler", at = @At(value = "FIELD", target = "Laliveandwell/aliveandwell/config/CommonConfig;deathCount:I", opcode = Opcodes.GETSTATIC), remap = false)
    @TargetHandler(mixin = "aliveandwell.aliveandwell.mixins.aliveandwell.PlayerManagerMixin", name = "onPlayerConnect")
    private void aac$disableModeModificationAndCheatTracking(Connection connection, ServerPlayer player, CallbackInfo ca, CallbackInfo ci) {
        if (ICheatConfig.shouldReportCheatMessage()) {
            player.sendSystemMessage(Component.translatable("aacfdw.tips.info0").withStyle(ChatFormatting.GREEN));
        }
        ci.cancel();
    }

    @Shadow
    @Final
    private ServerOpList ops;

    @Shadow
    @Final
    private MinecraftServer server;

    @Shadow
    private boolean allowCheatsForAllPlayers;

    @ModifyReturnValue(method = "@MixinSquared:Handler", at = @At(value = "RETURN"), remap = false)
    @TargetHandler(mixin = "aliveandwell.aliveandwell.mixins.aliveandwell.PlayerManagerMixin", name = "method_14569")
    private boolean aac$alwaysAllowCheatTracking(boolean original, @Local(ordinal = 0, argsOnly = true)GameProfile gameProfile) {
        return original || this.ops.contains(gameProfile) || this.server.isSingleplayerOwner(gameProfile) && this.server.getWorldData().getAllowCommands() || this.allowCheatsForAllPlayers;
    }
}
