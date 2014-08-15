package com.xaw4.craftcraft.proxy;

import java.lang.ref.WeakReference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import com.xaw4.craftcraft.constants.ModProperties;

/**
 * Created by Xaw4 on 16.07.2014.
 */
public abstract class CommonProxy implements Proxy
{

	WeakReference<EntityPlayer> fakePlayer =
			new WeakReference<EntityPlayer>(null);

	@Override
	public WeakReference<EntityPlayer> getFakePlayer(WorldServer world,
			int x, int y, int z)
	{
		WeakReference<EntityPlayer> pl = getFakePlayer(world);
		pl.get().posX = x;
		pl.get().posY = y;
		pl.get().posZ = z;
		
		return fakePlayer;
	}

	@Override
	public WeakReference<EntityPlayer> getFakePlayer(WorldServer world)
	{
		if (fakePlayer.get() == null)
		{
			EntityPlayer fk =
					FakePlayerFactory.get(world, ModProperties.GAME_PROFILE);
			fakePlayer = new WeakReference<EntityPlayer>(fk);
		}
		if(fakePlayer.get().worldObj != world)
		{
			fakePlayer.get().worldObj = world;
		}
		return fakePlayer;
	}
	
	public void clearFakePlayersInv()
	{
		if(fakePlayer.get() != null)
		{
			EntityPlayer player = fakePlayer.get();
			IInventory inv = player.inventory;
			for(int i = inv.getSizeInventory(); i > 0; i--)
			{
				inv.setInventorySlotContents(i, null);
			}
		}
	}
}
