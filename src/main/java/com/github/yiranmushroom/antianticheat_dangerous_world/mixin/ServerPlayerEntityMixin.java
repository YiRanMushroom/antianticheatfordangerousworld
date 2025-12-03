package com.github.yiranmushroom.antianticheat_dangerous_world.mixin;

import com.github.yiranmushroom.antianticheat_dangerous_world.mixins.api.IShouldApplyOp;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerEntityMixin implements IShouldApplyOp {
    @Unique
    private boolean aac$shouldOpWhenJoin = false;

    @Inject(method = "readAdditionalSaveData", at = @At("RETURN"))
    private void acc$readFromNBTAdditional(CompoundTag nbt, CallbackInfo ci) {
        if (nbt.contains("aac$shouldOpWhenJoin")) {
            this.aac$shouldOpWhenJoin = nbt.getBoolean("aac$shouldOpWhenJoin");
        } else {
            this.aac$shouldOpWhenJoin = false;
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("RETURN"))
    private void acc$writeToNBTAdditional(CompoundTag compoundTag, CallbackInfo ci) {
        compoundTag.putBoolean("aac$shouldOpWhenJoin", this.aac$shouldOpWhenJoin);
    }


    @Override
    public boolean getShouldApplyOp() {
        return this.aac$shouldOpWhenJoin;
    }

    @Override
    public void setShouldApplyOp(boolean value) {
        this.aac$shouldOpWhenJoin = value;
    }
}
