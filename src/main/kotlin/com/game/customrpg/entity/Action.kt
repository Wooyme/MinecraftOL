package com.game.customrpg.entity

import com.game.customrpg.common.ConfigLoader
import com.game.customrpg.common.EventLoader
import com.game.customrpg.p2p.*
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.server.MinecraftServer
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatComponentTranslation
import net.minecraft.world.World
import net.minecraft.world.WorldServer
import java.awt.Event
import java.util.*
import javax.xml.crypto.Data

/**
 * Created by user on 2017/7/5.
 */

class Action(val type:Int, val action:Any){
    fun doAction(entity: EntityP2PPlayer?=null){
        when(type) {
            playerUpdate->if(action is PlayerData){

                if(!DataHandler.players.contains(action.entityData.playerName)) {
                    if(30 > calcLength(action.entityData.posX,action.entityData.posY,action.entityData.posZ,EventLoader.mainPlayer.posX,EventLoader.mainPlayer.posY,EventLoader.mainPlayer.posZ)) {
                        val anotherPlayer = EntityP2PPlayer(EventLoader.world)
                        anotherPlayer.rotationYaw = action.entityData.yaw
                        anotherPlayer.rotationPitch = action.entityData.pitch
                        anotherPlayer.setPlayerNameAndHost(action.entityData.playerName, action.host)
                        anotherPlayer.setPositionAndUpdate(action.entityData.posX, action.entityData.posY, action.entityData.posZ)
                        DataHandler.players.put(action.entityData.playerName, anotherPlayer)
                        EventLoader.world.spawnEntityInWorld(anotherPlayer)
                    }
                }else{
                    DataHandler.players[action.entityData.playerName]!!.addAction(Action(Action.move,action.entityData))
                }
            }
            move-> if (action is EntityData && entity!=null) {
                    val (_,x, y, z,yaw,pitch) = action
                    if(30 < calcLength(x,y,z,EventLoader.mainPlayer.posX,EventLoader.mainPlayer.posY,EventLoader.mainPlayer.posZ)){
                        DataHandler.players.remove(entity.getPlayerName())
                        entity.setDead()
                    }else {
                        entity.rotationYaw = yaw
                        entity.rotationPitch = pitch
                        entity.setPositionAndUpdate(x, y, z)
                    }
                }
            block->if(action is BlockData){
                EventLoader.world.setBlock(action.position.x, action.position.y, action.position.z, Block.getBlockById(action.blockInfo.blockId), action.blockInfo.metadata, 2)
                EventLoader.mainPlayer.entityData.setLong("MapUpdateTime",Date().time)
            }
            interact->if(action is InteractData){
                val block=EventLoader.world.getBlock(action.position.x,action.position.y,action.position.z)
                block.onBlockActivated(EventLoader.world, action.position.x, action.position.y,action.position.z,FakePlayerLoader.getFakePlayer(EventLoader.world as WorldServer).get(),action.meta, 0F,0F,0F)
            }

        }
    }


    companion object {
        const val playerUpdate=1
        const val move=2
        const val block=3
        const val interact=4
        fun calcLength(sx:Double,sy:Double,sz:Double,tx:Double,ty:Double,tz:Double):Double{
            return Math.abs(Math.sqrt(sx*sz+sy*sy+sz*sz)-Math.sqrt(tx*tx+ty*ty+tz+tz))
        }
    }
}