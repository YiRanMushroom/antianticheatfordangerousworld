package com.github.yiranmushroom.antianticheat_dangerous_world.mixin;

import aliveandwell.aliveandwell.config.CommonConfig;
import com.github.yiranmushroom.antianticheat_dangerous_world.mixins.api.ICheatConfig;
import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.yiranmushroom.antianticheat_dangerous_world.AntiAntiCheatForDangerousWorld.LOGGER;

@Mixin(value = CommonConfig.class, remap = false)
public abstract class CommonConfigMixin implements ICheatConfig {
    @Unique
    private static boolean renderCheatersMessage = true;

    @Override
    public boolean getReportCheatMessage() {
        return renderCheatersMessage;
    }

    @Unique
    private static boolean shouldSave = true;

    @Override
    public boolean getShouldSave() {
        return shouldSave;
    }

    @Override
    public void saved() {
        shouldSave = false;
    }

    @Inject(method = "serialize", at = @At(value = "INVOKE", target = "Lcom/google/gson/JsonObject;addProperty(Ljava/lang/String;Ljava/lang/String;)V", ordinal = 0))
    private void aac$serializeAdditionalData(CallbackInfoReturnable<JsonObject> cir, @Local(name = "entry") JsonObject entry) {
        LOGGER.info("Serializing AAC additional config data...");
        entry.addProperty("desc_anticheat_disable:", "这个整合包被Yiran破解，是否显示相关破解信息？ This modpack's anti-cheat has been disabled by Yiran, do you want to show related messages?");
        entry.addProperty("renderCheatersMessage", renderCheatersMessage);
    }

    @Inject(method = "deserialize", at = @At(value = "FIELD", target = "Laliveandwell/aliveandwell/config/CommonConfig;netherDay:I", shift = At.Shift.AFTER, opcode = Opcodes.PUTSTATIC, ordinal = 0))
    private void aac$deserializeAdditionalData(JsonObject data, CallbackInfo ci) {
        LOGGER.info("Deserializing AAC additional config data...");
        var thisField = data.get("aliveandwell").getAsJsonObject().get("renderCheatersMessage");
        if (thisField != null) {
            renderCheatersMessage = thisField.getAsBoolean();
        } else {
            renderCheatersMessage = true;
            shouldSave = true;
        }
    }

//    @Inject(method = "deserialize", at = @At("RETURN"))
//    private void aac$serializeIfNotFound(JsonObject data, CallbackInfo ci, @Share("notFound")LocalBooleanRef notFoundRef) {
//        if (notFoundRef.get()) {
//            this.serialize();
//        }
//    }
}
