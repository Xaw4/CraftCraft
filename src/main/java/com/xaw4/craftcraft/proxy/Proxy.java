package com.xaw4.craftcraft.proxy;

import java.lang.ref.WeakReference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;

/**
 * Created by Xaw4 on 16.07.2014.
 */
public interface Proxy
{

	void registerRenderers();

	WeakReference<EntityPlayer> getFakePlayer(WorldServer world);
	
	WeakReference<EntityPlayer> getFakePlayer(WorldServer world, int x, int y, int z);
	
}
