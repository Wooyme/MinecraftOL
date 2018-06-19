package com.game.customrpg.common

import com.game.customrpg.command.CommandLoader
import com.game.customrpg.entity.EntityLoader
import com.game.customrpg.entity.FakePlayerLoader
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.event.FMLServerStartingEvent
import javassist.*

open class CommonProxy {
	open fun preInit(event: FMLPreInitializationEvent?) {
		ConfigLoader(event)
		EntityLoader()
		FakePlayerLoader()
		val classPool=ClassPool.getDefault()
	}

	open fun init(event: FMLInitializationEvent?) {
		EventLoader()
	}

	open fun postInit(event: FMLPostInitializationEvent?) {
	}

	open fun serverStarting(event: FMLServerStartingEvent?) {
		CommandLoader(event)
	}
}