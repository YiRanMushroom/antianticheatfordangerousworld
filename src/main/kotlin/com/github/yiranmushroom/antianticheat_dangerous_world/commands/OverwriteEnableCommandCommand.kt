package com.github.yiranmushroom.antianticheat_dangerous_world.commands

import com.github.yiranmushroom.antianticheat_dangerous_world.mixins.api.IShouldApplyOp
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import net.minecraft.ChatFormatting
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import net.minecraft.world.level.storage.PrimaryLevelData

/*
* val source : CommandSourceStack = ctx.source
            val server = source.server
            val player = source.player
* */

fun registerOverwriteEnableCommandCommand(dispatcher: CommandDispatcher<CommandSourceStack>) {
    dispatcher.register(
        LiteralArgumentBuilder.literal<CommandSourceStack>("overwrite_enable_command")
            .then(
                RequiredArgumentBuilder.argument<CommandSourceStack, String>(
                    "isEnableString",
                    StringArgumentType.word()
                ).suggests { _, builder ->
                    builder.suggest("enable")
                    builder.suggest("disable")
                    builder.buildFuture()
                }
                    .then(
                        RequiredArgumentBuilder.argument<CommandSourceStack, String>(
                            "allOrPlayer",
                            StringArgumentType.word()
                        )
                            .suggests { _, builder ->
//                                builder.suggest("all")
                                builder.suggest("me")
                                builder.buildFuture()
                            }
                            .executes { ctx ->
                                val source: CommandSourceStack = ctx.source
                                val isEnabled = when (StringArgumentType.getString(ctx, "isEnableString").lowercase()) {
                                    "enable" -> true
                                    "disable" -> false
                                    else -> {
                                        source.sendFailure(
                                            Component.literal("Invalid isEnableString argument. Use 'enable' or 'disable'.")
                                                .withStyle(
                                                    ChatFormatting.RED
                                                )
                                        )
                                        return@executes 0
                                    }
                                }

                                val isAll = when (StringArgumentType.getString(ctx, "allOrPlayer").lowercase()) {
                                    "all" -> true
                                    "me" -> false
                                    else -> {
                                        source.sendFailure(
                                            Component.literal("Invalid allOrPlayer argument. Use 'all' or 'me'.")
                                                .withStyle(
                                                    ChatFormatting.RED
                                                )
                                        )
                                        return@executes 0
                                    }
                                }

                                if (isAll) {
                                    source.sendSystemMessage(
                                        Component.literal("The 'all' option is currently disabled for safety reasons.")
                                            .withStyle(ChatFormatting.RED))
                                    /*source.server.playerList.isAllowCheatsForAllPlayers = isEnabled
                                    val serverWorldData = source.server.worldData
                                    if (serverWorldData is PrimaryLevelData) {
                                        serverWorldData.settings.allowCommands = isEnabled
                                    } else {
                                        source.sendSystemMessage(
                                            Component.literal("Warning: Unable to update world settings for allow commands. This command's effect will not be persistent.")
                                                .withStyle(ChatFormatting.YELLOW))
                                    }

                                    val playerList = source.server.playerList;

                                    // notify all players about the change
                                    for (player in playerList.players) {
                                        if (player != null) {
                                            playerList.sendPlayerPermissionLevel(player )
                                        }
                                    }

                                    source.sendSuccess(
                                        Component.literal("Set allow cheats for all players to $isEnabled")
                                            .withStyle(ChatFormatting.GREEN),
                                        true
                                    )*/
                                } else {
                                    if (!source.server.isDedicatedServer) {
                                        source.sendSystemMessage(
                                            Component.literal("You should use all for non-dedicated server, this command is not guaranteed to work properly.")
                                                .withStyle(ChatFormatting.YELLOW)
                                        )
                                    }

                                    val player = source.player?.gameProfile ?: run {
                                        source.sendFailure(
                                            Component.literal("Cannot find your player profile.")
                                                .withStyle(ChatFormatting.RED)
                                        )
                                        return@executes 0
                                    }

                                    if (!source.server.isSingleplayerOwner(player)) {
                                        source.sendFailure(
                                            Component.literal("Only the singleplayer owner can use overwrite_enable_command.")
                                                .withStyle(ChatFormatting.RED)
                                        )
                                    }

                                    val ishouldApplyOp = source.player as IShouldApplyOp

                                    if (isEnabled) {
                                        source.server.playerList.op(player)
                                        ishouldApplyOp.shouldApplyOp = true
                                    } else {
                                        source.server.playerList.deop(player)
                                        ishouldApplyOp.shouldApplyOp = false
                                    }

                                    source.sendSuccess(
                                        Component.literal("You have been opped to enable cheats.")
                                            .withStyle(ChatFormatting.GREEN),
                                        true
                                    )
                                }

                                1
                            }
                    )
            )
    );

}