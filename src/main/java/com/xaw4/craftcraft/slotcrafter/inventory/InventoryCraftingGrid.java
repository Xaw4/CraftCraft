package com.xaw4.craftcraft.slotcrafter.inventory;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.InventoryBasic;

public class InventoryCraftingGrid extends InventoryBasic
{
	List<CraftingSlot> craftingSlots;


	public InventoryCraftingGrid()
	{
		super("inventory.slot.crafting", false, 9);
		craftingSlots = new ArrayList<CraftingSlot>(9);
	}
	
	void addSlotToGrid(CraftingSlot slot)
	{
		if(!craftingSlots.contains(slot))
		{
			craftingSlots.add(slot);
		}
	}
	
	/**
	 * @return the craftingSlots
	 */
	public List<CraftingSlot> getCraftingSlots()
	{
		return craftingSlots;
	}

	/**
	 * @param craftingSlots the craftingSlots to set
	 */
	public void setCraftingSlots(List<CraftingSlot> craftingSlots)
	{
		this.craftingSlots = craftingSlots;
	}
	
}
