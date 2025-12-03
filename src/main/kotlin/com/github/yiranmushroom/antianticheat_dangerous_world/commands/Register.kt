package com.github.yiranmushroom.antianticheat_dangerous_world.commands

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack

fun registerCommands(dispatcher: CommandDispatcher<CommandSourceStack>) {
    // Register all commands here
    registerOverwriteEnableCommandCommand(dispatcher)
}