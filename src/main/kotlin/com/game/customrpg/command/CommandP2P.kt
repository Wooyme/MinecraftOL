package com.game.customrpg.command

import java.util.UUID
import com.game.customrpg.common.EventLoader
import com.game.customrpg.entity.EntityP2PPlayer
import com.game.customrpg.p2p.CustomP2PClient
import net.minecraft.client.Minecraft
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.server.MinecraftServer
import net.minecraft.util.ChatComponentText

class CommandP2P : CommandBase() {
	override fun getCommandName():String{
		return "p2p"
	}
	override fun getCommandUsage(p_71518_1_: ICommandSender): String {
		return "NO USAGE"
	}

	override fun processCommand(sender: ICommandSender, args: Array<String>) {
		if (args[0]=="login") {
			EventLoader.mainPlayer = CommandBase.getCommandSenderAsPlayer(sender)
			EventLoader.world = EventLoader.mainPlayer.worldObj
			Thread(CustomP2PClient("118.89.141.135", 8090)).start()

		} else if (args[0]=="spawn") {
			val playerMP=CommandBase.getCommandSenderAsPlayer(sender)
			val anotherPlayer = EntityP2PPlayer(playerMP.entityWorld)
			anotherPlayer.setPositionAndUpdate(playerMP.posX, playerMP.posY, playerMP.posZ)
			playerMP.entityWorld.spawnEntityInWorld(anotherPlayer)
		}
	}
}