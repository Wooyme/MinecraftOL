package com.game.customrpg.p2p

import java.io.IOException
import java.net.DatagramPacket
import java.net.InetAddress
import java.util.HashMap
import kotlin.collections.Map.Entry
import net.minecraft.block.Block

object WorldSender {
	fun blockSender(position: Position<Int>, block: Block, metadata: Int) {
		val binfo = BlockInfo(Block.getIdFromBlock(block), metadata)
		val databody = ByteAndObject.ObjectToByte(BlockData(position, binfo))
		val data = "BI".toByteArray().plus(databody!!)
		sender(data,true)
	}
	fun blockActivatedSender(position:Position<Int>,metadata:Int){
		val iData=InteractData(position,metadata)
		val databody = ByteAndObject.ObjectToByte(iData)
		val data="BA".toByteArray().plus(databody!!)
		sender(data)
	}
	fun sender(data:ByteArray,isToServer:Boolean=false){
		val entries = CustomP2PClient.connects.entries.iterator()
		while (entries.hasNext()) {
			val entry = entries.next()
			val dataPacket = DatagramPacket(data, data.size, entry.key, entry.value)
			try {
				CustomP2PClient.ds.send(dataPacket)
			} catch (e: IOException) {
				e.printStackTrace()
			}
		}
		if(isToServer) {
			val dataPacket = DatagramPacket(data, data.size, CustomP2PClient.serverip, CustomP2PClient.serverport)
			CustomP2PClient.ds.send(dataPacket)
		}
	}
}