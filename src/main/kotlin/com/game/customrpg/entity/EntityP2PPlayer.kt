package com.game.customrpg.entity

import com.game.customrpg.p2p.CustomP2PClient
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import com.game.customrpg.p2p.DataHandler
import net.minecraft.entity.EntityFlying
import java.net.InetAddress
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

class EntityP2PPlayer(world: World?) : EntityFlying(world) {
	private lateinit var name:String
	private var actionList= LinkedBlockingQueue<Action>()
	private var updateTime:Long=0
	private lateinit var hostIp:InetAddress

	init {
		if (!CustomP2PClient.isLogin) {
			this.setDead()
		}else {
			this.setSize(0.6f, 1.8f)
			this.tasks.taskEntries.clear()
			this.targetTasks.taskEntries.clear()
			this.updateTime=Date().time
		}
	}

	fun addAction(action:Action){
		this.actionList.offer(action)
	}

	fun getProfession():Int{
		return 0
	}
	fun setPlayerNameAndHost(name: String,host:InetAddress) {
		this.name = name
		this.hostIp=host
		this.customNameTag=this.name
		this.alwaysRenderNameTag=true
	}

	fun getPlayerName():String{
		return this.name
	}
	override fun getHeldItem():ItemStack?{
		return ItemStack(Items.apple)
	}

	override fun onLivingUpdate() {
		super.onLivingUpdate()
		while(this.actionList.isNotEmpty()) {
			val action = this.actionList.take()
			action.doAction(this)
			this.updateTime=Date().time
		}
		if(this.actionList.isEmpty() && Date().time-this.updateTime>5*60*1000){
			DataHandler.players.remove(this.name)
			//CustomP2PClient.connects.remove(this.hostIp)
			this.setDead()
		}
	}
}