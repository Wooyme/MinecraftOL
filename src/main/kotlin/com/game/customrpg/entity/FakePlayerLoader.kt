package com.game.customrpg.entity

import com.mojang.authlib.GameProfile
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.world.WorldServer
import net.minecraftforge.common.util.FakePlayerFactory
import java.lang.ref.WeakReference
import java.util.*

/**
 * Created by user on 2017/7/16.
 */

class FakePlayerLoader
{
    init
    {
        fakePlayer = WeakReference<EntityPlayerMP>(null)
    }

    companion object {
        lateinit var fakePlayer:WeakReference<EntityPlayerMP>
        private val gameProfile:GameProfile=GameProfile(UUID.fromString("C3F2EF82-E759-53EA-9D69-0D6E394A00B8"), "[FakePlayer]")
        fun getFakePlayer(server:WorldServer):WeakReference<EntityPlayerMP>{
            if (fakePlayer.get() == null)
            {
                fakePlayer = WeakReference<EntityPlayerMP>(FakePlayerFactory.get(server, gameProfile));
            }
            else
            {
                fakePlayer.get()!!.worldObj = server;
            }
            return fakePlayer;
        }
    }
}