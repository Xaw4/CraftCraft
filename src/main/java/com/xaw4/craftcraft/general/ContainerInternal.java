package com.xaw4.craftcraft.general;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * a container, that can't be used by a player
 * 
 * @author Xaw4
 *
 */
public class ContainerInternal extends Container
{

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return false;
	}

}
