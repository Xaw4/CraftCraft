package com.xaw4.craftcraft.slotcrafter;

import com.xaw4.craftcraft.general.AbstractSlotContainer;
import com.xaw4.craftcraft.general.AbstractSlotTileEntity;
import com.xaw4.craftcraft.util.RelativeFace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSlotCrafter extends AbstractSlotContainer
{
	public ContainerSlotCrafter(InventoryPlayer inventoryPlayer,
			TileEntitySlotCrafter te)
	{
		super(inventoryPlayer, te);
	}

}
