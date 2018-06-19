package com.game.customrpg.p2p

import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import com.game.customrpg.common.ConfigLoader
import com.game.customrpg.common.EventLoader
import net.minecraft.util.ChatComponentText
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import kotlin.collections.HashMap
data class DataPacket(val address:InetAddress,val port:Int,val data:ByteArray,val length:Int)
class CustomP2PClient(serverIP: String?, port: Int) : Runnable {
	inner class HolePunching(var ip:InetAddress,var port:Int):Runnable{
		var canStop:Boolean=false
		override fun run() {
			while(!canStop) {
				val dp = DatagramPacket("H1".toByteArray(), 2, this.ip, this.port)
				CustomP2PClient.ds.send(dp)
				Thread.sleep(100)
			}
		}
	}
    inner class ConnectsHandler:Runnable{
        var connectsPool=LinkedBlockingQueue<DataPacket>()
        private var holePunchingThreads=HashMap<InetAddress,HolePunching>()
        override fun run(){
            while(true) {
                val p = connectsPool.take()
                if (p.address==serverip) {
                    val rd: String = String(p.data, 0, p.length)
                    val info: List<String> = rd.split(" ")
                    if (info.size == 3 && info[0]=="CONNECT") {
                        val clientip = InetAddress.getByName(info[1])
                        val clientport = Integer.parseInt(info[2])
                        ConfigLoader.logger().info("CONNECT TO " + clientip.hostAddress + ":" + clientport)
                        val holePunching = HolePunching(clientip, clientport)
                        synchronized(this.holePunchingThreads) {
                            this.holePunchingThreads.put(clientip, holePunching)
                        }
                        Thread(holePunching).start()
                    } else {
                        DataHandler.handleDataBytes(p.address, p.data)
                    }
                } else {
                    val hData = ByteArray(2)
                    val ip = p.address.hostAddress
                    System.arraycopy(p.data, 0, hData, 0, 2)
                    if (Arrays.equals(hData, "H1".toByteArray())) {
                        val dp = DatagramPacket("H2".toByteArray(), 2, p.address, p.port)
                        ds.send(dp)
                    } else if (Arrays.equals(hData, "H2".toByteArray())) {
                        synchronized(this.holePunchingThreads) {
                            if (this.holePunchingThreads[p.address] != null) {
                                this.holePunchingThreads[p.address]!!.canStop = true
                                this.holePunchingThreads.remove(p.address)
                            }
                        }
                        if (!connects.contains(p.address)) {
                            connects.put(p.address, p.port)
                            EventLoader.mainPlayer.addChatComponentMessage(ChatComponentText("连接 $ip 成功"))
                        }
                    } else {
                        DataHandler.handleDataBytes(p.address, p.data)
                       // ConfigLoader.logger().info("GOT DATA FROM "+p.address.hostAddress+":"+p.port+" DATA:"+String(p.data))
                    }
                }
            }
        }
    }
	private val LOGIN = "LG".toByteArray()
    private val connectsHandler=ConnectsHandler()
	init {
		try {
			val loginData = LOGIN.plus(ByteAndObject.ObjectToByte(LoginData(EventLoader.mainPlayer.displayName,null,EventLoader.mainPlayer.entityData.getLong("MapUpdateTime")))!!)
			serverip = InetAddress.getByName(serverIP)
			serverport = port
			val dp = DatagramPacket(loginData, loginData.size, serverip, serverport)
			ds.send(dp)
			isLogin=true
			EventLoader.mainPlayer.addChatComponentMessage(ChatComponentText("登录成功"))
            Thread(connectsHandler).start()

		} catch (e: IOException) {
			ConfigLoader.logger().info(e)
		}
	}

	override fun run() {
		val buf = ByteArray(1024)
		val p = DatagramPacket(buf, 1024)
		while (true) {
			try {
				ds.receive(p)
                connectsHandler.connectsPool.offer(DataPacket(p.address,p.port,p.data.clone(),p.length))
				Arrays.fill(buf,0x0)
			} catch (e: IOException) {
				e.printStackTrace()
			}
		}
	}
	companion object {
		var connects = HashMap<InetAddress, Int>()
		var ds: DatagramSocket=DatagramSocket()
		var serverport: Int = 0
		var isLogin:Boolean=false
		lateinit var serverip: InetAddress
	}
}