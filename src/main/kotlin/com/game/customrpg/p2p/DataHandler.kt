package com.game.customrpg.p2p

import com.game.customrpg.common.ConfigLoader
import com.game.customrpg.common.EventLoader
import com.game.customrpg.entity.EntityP2PPlayer
import net.minecraft.block.Block
import com.game.customrpg.entity.Action
import java.net.DatagramPacket
import java.net.InetAddress
import java.util.*
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.LinkedBlockingQueue

data class PlayerData(val entityData:EntityData,val host:InetAddress)
object DataHandler {
	val headLength = 2
	val PP = "PP".toByteArray()
	val BI = "BI".toByteArray()
	val BA = "BA".toByteArray()
	var players: MutableMap<String, EntityP2PPlayer> = HashMap()
	var worldActionQueue=LinkedBlockingQueue<Action>()
	var serverActionQueue=LinkedBlockingQueue<Action>()
	fun handleDataBytes(host:InetAddress,data: ByteArray?) {
		val head = ByteArray(headLength)
		val body = ByteArray(data!!.size - headLength)
		System.arraycopy(data, 0, head, 0, headLength)
		System.arraycopy(data, headLength, body, 0, data.size - headLength)
		if (Arrays.equals(head, PP)) {
			val playerData = ByteAndObject.ByteToObject(body) as EntityData
			worldActionQueue.offer(Action(Action.playerUpdate,PlayerData(playerData,host)))
		} else if (Arrays.equals(head, BI)) {
			val blockData = ByteAndObject.ByteToObject(body) as BlockData
			if(host==CustomP2PClient.serverip){
				serverActionQueue.offer(Action(Action.block,blockData))
			}else{
				worldActionQueue.offer(Action(Action.block,blockData))
			}
		} else if (Arrays.equals(head,BA)){
			val interactData=ByteAndObject.ByteToObject(body) as InteractData
			worldActionQueue.offer(Action(Action.interact,interactData))

		}
	}

}