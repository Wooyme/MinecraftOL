package com.game.customrpg.p2p

/**
 * Created by user on 2017/7/16.
 */
import net.minecraft.util.EnumFacing
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import java.io.Serializable

data class BlockData(val position: Position<Int>, val blockInfo: BlockInfo) : Serializable {
    companion object {
        private const val serialVersionUID=748613371076455051
    }
}
data class BlockInfo(val blockId: Int, val metadata: Int) : Serializable{
    companion object {
        private const val serialVersionUID=748613371076455052
    }
}
data class Position<T>(val x:T, val y: T, val z: T) : Serializable {
    companion object {
        private const val serialVersionUID=748613371076455053
    }
}
data class EntityData(val playerName: String, val posX: Double, val posY: Double,val posZ: Double,val yaw: Float,val pitch: Float) : Serializable {
    companion object {
        private const val serialVersionUID=748613371076455054
    }
}
data class LoginData(val username:String,val password:String?=null,val lastUpdateTime:Long):Serializable{
    companion object {
        private const val serialVersionUID=748613371076455055
    }
}
data class InteractData(val position:Position<Int>,val meta:Int):Serializable{
    companion object {
        private const val serialVersionUID=748613371076455056
    }
}