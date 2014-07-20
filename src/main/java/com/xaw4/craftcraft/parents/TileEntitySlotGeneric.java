package com.xaw4.craftcraft.parents;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntitySlotGeneric extends TileEntity  implements ISidedInventory {

	private int facing = 2;
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
		return entityPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
	}
	
	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}
	
	//ISidedInventory
	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		int[] sides = {side ==0 || side == 1 ? side : (side-2+facing)%4+2};
		return sides;
	}

	public int getFacing()
	{
		ForgeDirection fd;
		return facing;
	}

	public void setFacing(int facing)
	{
		this.facing = facing;
	}
	
	
}
