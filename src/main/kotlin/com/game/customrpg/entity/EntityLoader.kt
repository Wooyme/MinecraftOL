package com.game.customrpg.entity

import com.game.customrpg.customrpg
import cpw.mods.fml.client.registry.RenderingRegistry
import cpw.mods.fml.common.registry.EntityRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.entity.Entity

class EntityLoader {
	init {
		registerEntity(EntityP2PPlayer::class.java, "P2PPlayer", 80, 3, true)
	}

	companion object {
		private var nextID = 0
		private fun registerEntity(entityClass: Class<out Entity>?, name: String?, trackingRange: Int,
								   updateFrequency: Int, sendsVelocityUpdates: Boolean) {
			EntityRegistry.registerModEntity(entityClass, name, nextID++, customrpg.instance, trackingRange, updateFrequency,
					sendsVelocityUpdates)
		}
	}
}