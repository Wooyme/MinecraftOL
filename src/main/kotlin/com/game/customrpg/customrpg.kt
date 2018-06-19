package com.game.customrpg

import com.game.customrpg.common.CommonProxy
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.Mod.Instance
import cpw.mods.fml.common.SidedProxy
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.event.FMLServerStartingEvent

@Mod(modid = customrpg.MODID, name = customrpg.NAME, version = customrpg.VERSION, acceptedMinecraftVersions = "1.7.10")
class customrpg {
	@EventHandler
	fun preInit(event: FMLPreInitializationEvent?) {
// TODO
		proxy!!.preInit(event)
	}

	@EventHandler
	fun init(event: FMLInitializationEvent?) {
// TODO
		proxy!!.init(event)
	}

	@EventHandler
	fun postInit(event: FMLPostInitializationEvent?) {
// TODO
		proxy!!.postInit(event)
	}

	@EventHandler
	fun serverStarting(event: FMLServerStartingEvent?) {
		proxy!!.serverStarting(event)
	}

	companion object {
		@SidedProxy(clientSide = "com.game.customrpg.client.ClientProxy", serverSide = "com.game.customrpg.common.CommonProxy")
		var proxy: CommonProxy? = null
		const val MODID: String = "customrpg"
		const val NAME: String = "Custom RPG"
		const val VERSION: String = "1.0.0"
		@Instance(MODID)
		var instance: customrpg? = null
	}
}