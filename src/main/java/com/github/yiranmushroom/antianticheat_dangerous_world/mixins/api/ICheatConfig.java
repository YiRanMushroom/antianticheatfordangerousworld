package com.github.yiranmushroom.antianticheat_dangerous_world.mixins.api;

import aliveandwell.aliveandwell.AliveAndWellMain;

public interface ICheatConfig {
    boolean getReportCheatMessage();

    boolean getShouldSave();

    void saved();

    public static boolean shouldReportCheatMessage() {
        return ((ICheatConfig) (AliveAndWellMain.config.getCommonConfig())).getReportCheatMessage();
    }

    public static boolean shouldSaveConfig() {
        return ((ICheatConfig) (AliveAndWellMain.config.getCommonConfig())).getShouldSave();
    }

    public static void markConfigSaved() {
        ((ICheatConfig) (AliveAndWellMain.config.getCommonConfig())).saved();
    }
}
