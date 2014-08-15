package com.xaw4.craftcraft.slotcrafter;

import java.lang.ref.WeakReference;
import java.util.List;

import com.xaw4.craftcraft.CraftCraft;
import com.xaw4.craftcraft.general.AbstractSlotContainer;
import com.xaw4.craftcraft.general.AbstractSlotGui;
import com.xaw4.craftcraft.general.AbstractSlotTileEntity;
import com.xaw4.craftcraft.general.ContainerInternal;
import com.xaw4.craftcraft.general.FaceConfiguration;
import com.xaw4.craftcraft.handler.NetworkHandler;
import com.xaw4.craftcraft.init.ModObject;
import com.xaw4.craftcraft.messages.MessageCraftConfig;
import com.xaw4.craftcraft.messages.MessageSlotAssignment;
import com.xaw4.craftcraft.slotchest.ContainerSlotChest;
import com.xaw4.craftcraft.slotchest.GuiSlotChest;
import com.xaw4.craftcraft.slotchest.TileEntitySlotChest;
import com.xaw4.craftcraft.slotcrafter.inventory.CraftingSlot;
import com.xaw4.craftcraft.slotcrafter.inventory.InventoryCraftingGrid;
import com.xaw4.craftcraft.slotcrafter.inventory.OutputSlot;

import static com.xaw4.craftcraft.util.CheckUtils.isInRange;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;

/**
 * @author Xaw4
 * 
 */
public class TileEntitySlotCrafter extends AbstractSlotTileEntity
{

	/** the key used in the NBT for all slots (the root TagList) */
	protected static final String CRAFTING_CONFIG_KEY_NBT = "CraftCfg";

	private int stackLimit = 64;

	/** inventory used for display */
	private InventoryCraftingGrid craftingGrid;

	/** inventory used for crafting */
	private InventoryCrafting craftingMatrix;

	private int[] craftingConfig = new int[9];

	public TileEntitySlotCrafter()
	{
		super();
		for (int i = 0; i < craftingConfig.length; ++i)
		{
			craftingConfig[i] = FaceConfiguration.UNASSIGNED;
		}
	}

	/**
	 * @return the craftingGrid
	 */
	public InventoryCraftingGrid getCraftingGrid()
	{
		if (craftingGrid == null)
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
	 * @param mouseX
	 *            mouse position relative to gui origin
	 * @param mouseY
	 *            mouse position relative to gui origin
	 * @param mouseButton
	 *            button presses (0=left, 1= right, 2= scroll)
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
				if (craftingSlot.isMouseHovering(mouseX, mouseY))
				{
					int craftingIdx = craftingSlot.getSlotIndex();
					if (mouseButton == 0)
					{
						NetworkHandler.getInstance().sendToServer(
								new MessageCraftConfig(craftingIdx,
										MessageCraftConfig.INCREASE_MESSAGE));
					}
					else if (mouseButton == 1)
					{
						NetworkHandler.getInstance().sendToServer(
								new MessageCraftConfig(craftingIdx,
										MessageCraftConfig.DECREASE_MESSAGE));
					}
					FMLLog.finer("Slot clicked %d|%d|%s",
							craftingSlot.getSlotIndex(),
							craftingConfig[craftingSlot.getSlotIndex()],
							craftingSlot);
				}
			}
		}
	}

	public void increaseCraftingConfig(int craftingIdx)
	{
		if (!isInRange(craftingIdx, 0, craftingConfig.length))
		{
			return;
		}
		if (craftingConfig[craftingIdx] == FaceConfiguration.UNASSIGNED)
		{
			craftingConfig[craftingIdx] = 0;
		}
		else
		{
			craftingConfig[craftingIdx]++;
		}

		if (!isInRange(craftingConfig[craftingIdx], 0, 5))
		{
			craftingConfig[craftingIdx] = FaceConfiguration.UNASSIGNED;
		}
	}

	public void decreaseCraftingConfig(int craftingIdx)
	{
		if (!isInRange(craftingIdx, 0, craftingConfig.length))
		{
			return;
		}
		if (craftingConfig[craftingIdx] == FaceConfiguration.UNASSIGNED)
		{
			craftingConfig[craftingIdx] = 4;
		}
		else
		{
			craftingConfig[craftingIdx]--;
		}

		if (!isInRange(craftingConfig[craftingIdx], 0, 5))
		{
			craftingConfig[craftingIdx] = FaceConfiguration.UNASSIGNED;
		}
	}

	public int getCraftingConfig(int gridSlotNum)
	{
		if (isInRange(gridSlotNum, 0, 9))
		{
			return craftingConfig[gridSlotNum];
		}
		return FaceConfiguration.UNASSIGNED;
	}

	/**
	 * checks if amount of items in the slots + recipe would possibly work if
	 * there are not enough items in the slots it doesnt even matter if the
	 * recipe is a valid recipe
	 * 
	 * @return true if there are enough items, false otherwise
	 */
	protected boolean preCheckRecipe()
	{
		boolean isAnyRecipe = false;
		int[] slotAmounts = new int[5];
		for (int cfg : craftingConfig)
		{
			if (isInRange(cfg, 0, 5))
			{
				slotAmounts[cfg]++;
				isAnyRecipe = true;
			}
		}

		for (int i = 0; i < 5; i++)
		{
			if (slotAmounts[i] > 0)
			{
				if (getStackInSlot(i) == null
						|| getStackInSlot(i).stackSize < slotAmounts[i])
				{
					return false;
				}
			}
		}
		return isAnyRecipe;
	}

	protected boolean attemptCrafting()
	{
		if(!preCheckRecipe())
		{
			return false;
		}
		builtCraftingTable();

		// if (!this.worldObj.isRemote)
		// {
		// FMLLog.warning("try to build a fakePlayer on client");
		// }
		// EntityPlayer fakePlayer = CraftCraft.proxy
		// .getFakePlayer((WorldServer) this.worldObj, xCoord, yCoord + 1,
		// zCoord).get();
		ItemStack result =
				CraftingManager.getInstance().findMatchingRecipe(
						craftingMatrix, worldObj);

		FMLLog.info("Try to craft,  result= %s", result);
		if(result != null)
		{
			ItemStack outputStack = this.getSlot(5);
			if(outputStack == null || outputStack.stackSize == 0)
			{
				this.setSlot(5, result);
			}
		}
		
		return result != null;
	}

	protected InventoryCrafting builtCraftingTable()
	{
		if (craftingMatrix == null)
		{
			craftingMatrix = new InventoryCrafting(
					new ContainerInternal(), 3, 3);
		}
		for (int i = 0; i < 9; i++)
		{
			//TODO: Maybe check for an configured, but empty slot
			craftingMatrix.setInventorySlotContents(i,
					getRoutedContentForConfiguration(i));
		}
		return craftingMatrix;
	}

	/**
	 * 
	 * @param configIdx
	 *            the index in the craftingConfig array
	 * @return a copy of the item configured in the crafting slot grid or null
	 *         if unassigned
	 */
	protected ItemStack getRoutedContentForConfiguration(int configIdx)
	{
		int slotIdx = craftingConfig[configIdx];
		if (slotIdx == FaceConfiguration.UNASSIGNED)
		{
			return null;
		}
		return this.getStackInSlot(slotIdx).copy();
	}

	// AbstractSlotTileEntity
	@Override
	public AbstractSlotContainer getServerContainer(int guiId,
			InventoryPlayer inventory, AbstractSlotTileEntity slotTileEntity)
	{
		if (guiId == ModObject.slotCrafter.id
				&& slotTileEntity instanceof TileEntitySlotCrafter)
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
		if (guiId == ModObject.slotCrafter.id
				&& slotTileEntity instanceof TileEntitySlotCrafter)
		{
			TileEntitySlotCrafter tesc = (TileEntitySlotCrafter) slotTileEntity;
			tesc.log();
			return new GuiSlotCrafter(inventory, tesc);
		}
		return null;
	}

	// TileEntity

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.tileentity.TileEntity#updateEntity()
	 */
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (this.worldObj.isRemote
				&& this.worldObj.getTotalWorldTime() % 20 == 0)
		{
			boolean success = attemptCrafting();
			FMLLog.finer("crafting: %b <%s>", success, this);
			
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xaw4.craftcraft.general.AbstractSlotTileEntity#writeToNBT(net.minecraft
	 * .nbt.NBTTagCompound)
	 */
	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setIntArray(CRAFTING_CONFIG_KEY_NBT, craftingConfig);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xaw4.craftcraft.general.AbstractSlotTileEntity#readFromNBT(net.minecraft
	 * .nbt.NBTTagCompound)
	 */
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		int[] readCraftingConfig = compound
				.getIntArray(CRAFTING_CONFIG_KEY_NBT);
		if (readCraftingConfig.length == craftingConfig.length)
		{
			for (int i = 0; i < readCraftingConfig.length; i++)
			{
				if (!isInRange(readCraftingConfig[i], 0, 5))
				{
					readCraftingConfig[i] = FaceConfiguration.UNASSIGNED;
				}
			}
			this.craftingConfig = readCraftingConfig;
		}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xaw4.craftcraft.general.AbstractSlotTileEntity#setInventorySlotContents
	 * (int, net.minecraft.item.ItemStack)
	 */
	@Override
	public void setInventorySlotContents(int slotNum, ItemStack itemStack)
	{
		if (0 <= slotNum && slotNum < 5)
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
