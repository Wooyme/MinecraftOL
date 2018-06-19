package com.game.customrpg.client

import com.game.customrpg.common.CommonProxy
import com.game.customrpg.entity.EntityP2PPlayer
import cpw.mods.fml.client.registry.RenderingRegistry
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraft.client.renderer.entity.RenderSnowMan
import net.minecraft.client.renderer.entity.RenderVillager

class ClientProxy : CommonProxy() {
	override fun preInit(event: FMLPreInitializationEvent?) {
		super.preInit(event)
		RenderingRegistry.registerEntityRenderingHandler(EntityP2PPlayer::class.java, RenderCustomNPC())
	}
	override fun init(event: FMLInitializationEvent?) {
		super.init(event)
	}

	override fun postInit(event: FMLPostInitializationEvent?) {
		super.postInit(event)
	}
}