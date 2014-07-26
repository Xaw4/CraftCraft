package com.xaw4.craftcraft.slotchest;

import com.sun.org.apache.bcel.internal.generic.ISUB;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSlotChest extends Container
{


	private TileEntitySlotChest te;

	private static final int SLOT_WIDTH = 18+4;
	private static final int FIRST_SLOT_X = 25;
	private static final int FIRST_SLOT_Y = 62;

	private static final int PLAYER_INV_Y = 142;
	
	private static final int SLOT_COUNT = 6;

	public ContainerSlotChest(InventoryPlayer inventoryPlayer,
			TileEntitySlotChest te)
	{
		this.te = te;
		addChestSlots();
		bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer)
	{
		return te.isUseableByPlayer(entityPlayer);
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
						8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, PLAYER_INV_Y));
		}
	}

	protected void addChestSlots()
	{
		for (int slotNum = 0; slotNum < SLOT_COUNT; slotNum++)
		{
			addSlotToContainer(new Slot(te, slotNum, 
					FIRST_SLOT_X + slotNum * SLOT_WIDTH,
					FIRST_SLOT_Y));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		// null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack())
		{
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			// merges the item into player inventory since its in the tileEntity
			if (slot < SLOT_COUNT)
			{
				if (!this.mergeItemStack(stackInSlot, 0, 35, true))
				{
					return null;
				}
			}
			// places it into the tileEntity is possible since its in the player
			// inventory
//			else if (!this.mergeItemStack(stackInSlot, 0, SLOT_COUNT, false))
//			{
//				return null;
//			}

			if (stackInSlot.stackSize == 0)
			{
				slotObject.putStack(null);
			}
			else
			{
				slotObject.onSlotChanged();
			}

			if (stackInSlot.stackSize == stack.stackSize)
			{
				return null;
			}
			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}

}
