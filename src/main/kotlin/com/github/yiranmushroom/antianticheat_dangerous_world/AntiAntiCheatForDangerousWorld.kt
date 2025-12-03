package com.github.yiranmushroom.antianticheat_dangerous_world;

import com.github.yiranmushroom.antianticheat_dangerous_world.commands.registerCommands
import com.github.yiranmushroom.antianticheat_dangerous_world.mixins.api.IShouldApplyOp
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

object AntiAntiCheatForDangerousWorld : ModInitializer {
    @JvmField
    val MOD_ID: String = "antianticheatfordangerousworld";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
//	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @JvmField
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

    override fun onInitialize() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            registerCommands(dispatcher)
        }

        ServerPlayConnectionEvents.JOIN.register {handler, sender, server ->
            val player = handler.player
            val shouldApply = (player as IShouldApplyOp).shouldApplyOp
            if (shouldApply) {
                server.playerList.op(player.gameProfile)
            }
        }
    }
}