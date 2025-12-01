package com.github.yiranmushroom.antianticheat_dangerous_world.mixins;

import com.bawnorton.mixinsquared.api.MixinCanceller;

import java.util.List;
import java.util.Set;


public class ModMixinCanceller implements MixinCanceller {
    ModMixinCanceller() {
    }

    private static final Set<String> MixinsToCancel = Set.of(
//            "aliveandwell.aliveandwell.mixins.aliveandwell.client.ClientPlayerInteractionManagerMixin",
            "aliveandwell.aliveandwell.mixins.aliveandwell.client.EditGameRulesScreenMixin",
            "aliveandwell.aliveandwell.mixins.aliveandwell.client.CreateWorldScreenMixin",
            "aliveandwell.aliveandwell.mixins.aliveandwell.client.ClientWorldPropertiesMixin",
            "aliveandwell.aliveandwell.mixins.aliveandwell.client.GameOptionsMixin",
//            "aliveandwell.aliveandwell.mixins.aliveandwell.PlayerManagerMixin",
            "aliveandwell.aliveandwell.mixins.aliveandwell.world.LevelPropertiesMixin",
            "aliveandwell.aliveandwell.mixins.aliveandwell.world.LevelInfoMixin"
    );

    @Override
    public boolean shouldCancel(List<String> list, String s) {
        if (MixinsToCancel.contains(s)) {
            System.out.println("Cancelling mixin: " + s);
            return true;
        }
        return false;
    }
}
