package com.game.customrpg.command

import cpw.mods.fml.common.event.FMLServerStartingEvent

class CommandLoader(event: FMLServerStartingEvent?) {
	init {
		event!!.registerServerCommand(CommandP2P())
	}
}