package com.xaw4.craftcraft.slotcrafter.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class OutputSlot extends Slot
{
	private boolean inputSlot = true;
	
	public OutputSlot(IInventory inventory, int slotNum, 
			int displayX,int displayY)
	{
		super(inventory, slotNum, displayX, displayY);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.Slot#isItemValid(net.minecraft.item.ItemStack)
	 */
	@Override
	public boolean isItemValid(ItemStack itemStack)
	{
		return false;
	}
	
	
	
}
