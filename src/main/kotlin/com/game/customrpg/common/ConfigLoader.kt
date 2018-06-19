package com.game.customrpg.common

import org.apache.logging.log4j.Logger
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.common.config.Configuration

class ConfigLoader(event: FMLPreInitializationEvent?) {
	init {
		logger = event!!.getModLog()
		config = Configuration(event.getSuggestedConfigurationFile())
		config!!.load()
		load()
	}

	companion object {
		private var config: Configuration? = null
		private var logger: Logger? = null
		var diamondBurnTime: Int = 0
		fun load() {
			logger!!.info("Started loading config. ")
			val comment: String?
			comment = "How many seconds can a diamond burn in a furnace. "
			diamondBurnTime = config!!.get(Configuration.CATEGORY_GENERAL, "diamondBurnTime", 640, comment).getInt()
			config!!.save()
			logger!!.info("Finished loading config. ")
		}

		fun logger(): Logger {
			return logger!!
		}
	}
}