package com.xaw4.craftcraft.slotcrafter;

import java.util.List;

import com.xaw4.craftcraft.general.AbstractSlotContainer;
import com.xaw4.craftcraft.general.AbstractSlotGui;
import com.xaw4.craftcraft.general.AbstractSlotTileEntity;
import com.xaw4.craftcraft.init.ModObject;
import com.xaw4.craftcraft.slotchest.ContainerSlotChest;
import com.xaw4.craftcraft.slotchest.GuiSlotChest;
import com.xaw4.craftcraft.slotchest.TileEntitySlotChest;
import com.xaw4.craftcraft.slotcrafter.inventory.CraftingSlot;
import com.xaw4.craftcraft.slotcrafter.inventory.InventoryCraftingGrid;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

/**
 * @author Xaw4
 * 
 */
public class TileEntitySlotCrafter extends AbstractSlotTileEntity
{
	private int stackLimit = 64;

	private InventoryCraftingGrid craftingGrid;
	
	/**
	 * @return the craftingGrid
	 */
	public InventoryCraftingGrid getCraftingGrid()
	{
		if(craftingGrid == null)
		{
			craftingGrid = new InventoryCraftingGrid();
		}
		return craftingGrid;
	}
	
	public void setCraftingGrid(InventoryCraftingGrid craftingGrid)
	{
		this.craftingGrid = craftingGrid;
	}
	
	/**
	 * @param mouseX mouse position relative to gui origin
	 * @param mouseY mouse position relative to gui origin
	 * @param mouseButton button presses (0=left, 1= right, 2= scroll)
	 */
	@SideOnly(Side.CLIENT)
	public void handleMouseClickOnGui(int mouseX, int mouseY, int mouseButton)
	{
		FMLLog.info("mouseClicked <%d><%d><%d>", mouseX, mouseY, mouseButton);
		if (mouseButton == 0 || mouseButton == 1)
		{
			List<CraftingSlot> crafts = getCraftingGrid().getCraftingSlots();
			for (CraftingSlot craftingSlot : crafts)
			{
				if(craftingSlot.isMouseHovering(mouseX, mouseY)){
					FMLLog.info("Slot clicked %d %s", craftingSlot.getSlotIndex(), craftingSlot);
				}
			}
		}
	}
	
	// AbstractSlotTileEntity
	@Override
	public AbstractSlotContainer getServerContainer(int guiId,
			InventoryPlayer inventory, AbstractSlotTileEntity slotTileEntity)
	{
		if(guiId == ModObject.slotCrafter.id && slotTileEntity instanceof TileEntitySlotCrafter)
		{
			TileEntitySlotCrafter te = (TileEntitySlotCrafter) slotTileEntity;
			te.log();
			return new ContainerSlotCrafter(inventory, te);
		}
		return null;
	}

	@Override
	public AbstractSlotGui getClientGui(int guiId, InventoryPlayer inventory,
			AbstractSlotTileEntity slotTileEntity)
	{
		if (guiId == ModObject.slotCrafter.id && slotTileEntity instanceof TileEntitySlotCrafter)
		{
			TileEntitySlotCrafter tesc = (TileEntitySlotCrafter) slotTileEntity;
			tesc.log();
			return new GuiSlotCrafter(inventory, tesc);
		}
		return null;
	}
	

	
	// IInventory

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.inventory.IInventory#decrStackSize(int, int)
	 */
	@Override
	public ItemStack decrStackSize(int slotNum, int count)
	{
		ItemStack stack = getStackInSlot(slotNum);
		if (stack != null)
		{
			if (stack.stackSize <= count)
			{
				setInventorySlotContents(slotNum, null);
			}
			else
			{
				stack = stack.splitStack(count);
			}
			this.markDirty();
		}
		return stack;
	}

	/* (non-Javadoc)
	 * @see com.xaw4.craftcraft.general.AbstractSlotTileEntity#setInventorySlotContents(int, net.minecraft.item.ItemStack)
	 */
	@Override
	public void setInventorySlotContents(int slotNum, ItemStack itemStack)
	{
		if(0 <= slotNum && slotNum < 5)
		{
			super.setInventorySlotContents(slotNum, itemStack);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.inventory.IInventory#getStackInSlotOnClosing(int)
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int slotNum)
	{
		ItemStack stack = getStackInSlot(slotNum);
		setInventorySlotContents(slotNum, null);
		return stack;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.inventory.IInventory#getInventoryName()
	 */
	@Override
	public String getInventoryName()
	{
		return "SlotCrafer_Inv";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.inventory.IInventory#hasCustomInventoryName()
	 */
	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.inventory.IInventory#getInventoryStackLimit()
	 */
	@Override
	public int getInventoryStackLimit()
	{
		return stackLimit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.inventory.IInventory#isItemValidForSlot(int,
	 * net.minecraft.item.ItemStack)
	 */
	@Override
	public boolean isItemValidForSlot(int slotNum, ItemStack itemStack)
	{
		return 0 <= slotNum && slotNum < 5;
	}

	// ISidedInventory
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.inventory.ISidedInventory#canInsertItem(int,
	 * net.minecraft.item.ItemStack, int)
	 */
	@Override
	public boolean canInsertItem(int slotNum, ItemStack item,
			int side)
	{
		return 0 <= slotNum && slotNum < 5;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.inventory.ISidedInventory#canExtractItem(int,
	 * net.minecraft.item.ItemStack, int)
	 */
	@Override
	public boolean canExtractItem(int slotNum, ItemStack item,
			int side)
	{
		return 0 <= slotNum && slotNum < 6;
	}
	
	
}
