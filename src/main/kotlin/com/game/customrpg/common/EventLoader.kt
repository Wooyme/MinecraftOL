package com.game.customrpg.common

import com.game.customrpg.entity.EntityP2PPlayer
import com.game.customrpg.p2p.*
import cpw.mods.fml.common.eventhandler.Event
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Blocks
import net.minecraft.server.MinecraftServer
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.event.entity.living.LivingSpawnEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.event.world.ChunkDataEvent
import net.minecraftforge.event.world.ChunkEvent
import net.minecraftforge.event.world.ChunkWatchEvent
import java.math.BigInteger
import java.net.DatagramPacket
import java.util.*

class EventLoader {
	var playerSendTimer:Int=0
    var heartSendTimer:Int=0
	init {
		MinecraftForge.EVENT_BUS.register(this)
	}

	@SubscribeEvent
	fun onBlockPlaced(event:BlockEvent.PlaceEvent){
		if(CustomP2PClient.isLogin && !event.player.worldObj.isRemote && event !is BlockEvent.MultiPlaceEvent) {
			val meta = event.world.getBlockMetadata(event.x, event.y, event.z)
			WorldSender.blockSender(Position(event.x, event.y, event.z), event.placedBlock, meta)
			mainPlayer.entityData.setLong("MapUpdateTime", Date().time)
		}
	}
    @SubscribeEvent
    fun onMutiBlockPlaced(event:BlockEvent.MultiPlaceEvent){
        if(CustomP2PClient.isLogin && !event.player.worldObj.isRemote) {
            for(block in event.replacedBlockSnapshots) {
                val meta = event.world.getBlockMetadata(block.x, block.y, block.z)
                WorldSender.blockSender(Position(block.x, block.y, block.z), block.currentBlock, meta)
            }
            mainPlayer.entityData.setLong("MapUpdateTime", Date().time)
        }
    }
	@SubscribeEvent
	fun onBlockBreaked(event:BlockEvent.BreakEvent){
		if(CustomP2PClient.isLogin && !event.player.worldObj.isRemote) {
			WorldSender.blockSender(Position(event.x, event.y, event.z), Blocks.air, 0)
			mainPlayer.entityData.setLong("MapUpdateTime", Date().time)
		}
	}
	@SubscribeEvent
	fun sendPlayerUpdate(event:LivingEvent.LivingUpdateEvent){
		if(event.entityLiving is EntityPlayer && CustomP2PClient.isLogin && CustomP2PClient.connects.isNotEmpty() && playerSendTimer>5 && !event.entity.worldObj.isRemote){
			playerSendTimer=0
			val player=event.entityLiving as EntityPlayer
			var playerData = EntityData(player.displayName, player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch)
			var playerBytes = "PP".toByteArray().plus(ByteAndObject.ObjectToByte(playerData)!!)
			for((key,value) in CustomP2PClient.connects) {
				val dataPacket = DatagramPacket(playerBytes, playerBytes.size, key, value)
				CustomP2PClient.ds.send(dataPacket)
			}
		}else{
			playerSendTimer++
		}
	}

	@SubscribeEvent
	fun onWorldUpdate(event:LivingEvent.LivingUpdateEvent){
		if(event.entity.worldObj.isRemote || !CustomP2PClient.isLogin)
			return
		if(EventLoader.world.capturedBlockSnapshots.isNotEmpty())
			ConfigLoader.logger().info("World:"+EventLoader.world.capturedBlockSnapshots.last().currentBlock.localizedName)
		if(DataHandler.serverActionQueue.isNotEmpty()) {
			while (DataHandler.serverActionQueue.isNotEmpty()) {
				val action = DataHandler.serverActionQueue.poll()
				action.doAction()
			}
		}else {
			while (DataHandler.worldActionQueue.isNotEmpty()) {
				val action = DataHandler.worldActionQueue.poll()
				action.doAction()
			}
		}
	}
	@SubscribeEvent
	fun onBlockActivated(event:PlayerInteractEvent){
		if(event.action==PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && !event.entityLiving.worldObj.isRemote && CustomP2PClient.isLogin){
			WorldSender.blockActivatedSender(Position(event.x,event.y,event.z),EventLoader.world.getBlockMetadata(event.x,event.y,event.z))
		}
	}
    @SubscribeEvent
    fun controlEntitySpawn(event:LivingSpawnEvent.CheckSpawn){
        if(!event.entityLiving.worldObj.isRemote){
            if(event.entityLiving !is EntityP2PPlayer){
                event.result=Event.Result.DENY
            }else{
                event.result=Event.Result.ALLOW
            }
        }
    }
    @SubscribeEvent
    fun sendHeartPacket(event:LivingEvent.LivingUpdateEvent){
        if(CustomP2PClient.isLogin) {
            if (heartSendTimer < 10) {
                heartSendTimer++
            } else {
                val dp = DatagramPacket(kotlin.ByteArray(1), 1, CustomP2PClient.serverip, CustomP2PClient.serverport)
                CustomP2PClient.ds.send(dp)
            }
        }
    }

	@SubscribeEvent
	fun chunkWatcher(event:ChunkWatchEvent.Watch){

	}

	companion object {
		var isOK=false
		lateinit var world: World
		lateinit var mainPlayer: EntityPlayerMP
		//var blockMap: MutableMap<Position<Int>, BlockInfo> = HashMap()
	}
}