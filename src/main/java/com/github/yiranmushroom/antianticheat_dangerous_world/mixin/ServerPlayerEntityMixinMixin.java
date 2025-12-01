package com.github.yiranmushroom.antianticheat_dangerous_world.mixin;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ServerPlayer.class, priority = 1001)
public abstract class ServerPlayerEntityMixinMixin extends Player {
    public ServerPlayerEntityMixinMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile, @Nullable ProfilePublicKey profilePublicKey) {
        super(level, blockPos, f, gameProfile, profilePublicKey);
    }

    @TargetHandler(mixin = "aliveandwell.aliveandwell.mixins.aliveandwell.enity.ServerPlayerEntityMixin", name = "cheat")
    @WrapMethod(method = "@MixinSquared:Handler")
    public void aac$disableCheatTracking(Operation<Void> original) {
        // Do nothing to disable cheat tracking
    }

    @TargetHandler(mixin = "aliveandwell.aliveandwell.mixins.aliveandwell.enity.ServerPlayerEntityMixin", name = "cheatItem")
    @WrapMethod(method = "@MixinSquared:Handler")
    public boolean aac$alwaysReturnFalseForCheatItem(Operation<Boolean> original) {
        return false;
    }

    @TargetHandler(mixin = "aliveandwell.aliveandwell.mixins.aliveandwell.enity.ServerPlayerEntityMixin", name = "readCustomDataFromNbt")
//    @Definition(id = "nbt", local = @Local(name = "nbt"))
//    @Definition(id = "cheatItem", field = "Laliveandwell/aliveandwell/mixins/aliveandwell/enity/ServerPlayerEntityMixin;cheatItem:Z")
//    @Definition(id = "getBoolean", method = "Lnet/minecraft/nbt/CompoundTag;getBoolean(Ljava/lang/String;)Z")
//    @WrapOperation(method = "@MixinSquared:Handler", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    @WrapOperation(method = "@MixinSquared:Handler", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getBoolean(Ljava/lang/String;)Z"))
    private boolean aac$doNotReadCheatItemExpression(CompoundTag instance, String string, Operation<Boolean> original) {
        if (string.equals("cheatItem") || string.equals("IsCheat")) {
            return false; // Always return false to avoid reading cheatItem
        } else {
            return original.call(instance, string);
        }
    }

    @TargetHandler(mixin = "aliveandwell.aliveandwell.mixins.aliveandwell.enity.ServerPlayerEntityMixin", name = "writeCustomDataToNbt")
//    @Definition(id = "nbt", local = @Local(name = "nbt"))
//    @Definition(id = "cheatItem", field = "Laliveandwell/aliveandwell/mixins/aliveandwell/enity/ServerPlayerEntityMixin;cheatItem:Z")
//    @Definition(id = "putBoolean", method = "Lnet/minecraft/nbt/CompoundTag;putBoolean(Ljava/lang/String;Z)V")
//    @WrapOperation(method = "@MixinSquared:Handler", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    @WrapOperation(method = "@MixinSquared:Handler", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;putBoolean(Ljava/lang/String;Z)V"))
    private void aac$doNotWriteCheatItemExpression(CompoundTag instance, String string, boolean b, Operation<Void> original) {
        if (string.equals("cheatItem") || string.equals("IsCheat")) {
            // Do nothing to avoid writing cheatItem
        } else {
            original.call(instance, string, b);
        }
    }
}
