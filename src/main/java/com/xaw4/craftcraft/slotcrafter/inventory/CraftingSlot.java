package com.xaw4.craftcraft.slotcrafter.inventory;


import java.util.List;

import org.lwjgl.util.Dimension;
import org.lwjgl.util.Rectangle;

import com.xaw4.craftcraft.constants.ModProperties;
import com.xaw4.craftcraft.init.ModBlocks;
import com.xaw4.craftcraft.util.IconUtil;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class CraftingSlot extends Slot
{

//    public static final ResourceLocation numberTexture = new ResourceLocation(ModProperties.MOD_ID, "textures/gui/craftnums.png");
	
	public CraftingSlot(InventoryCraftingGrid inv, int idx, 
			int x,int y)
	{
		super(inv, idx, x, y);
		inv.addSlotToGrid(this);
//		ResourceLocation numberTexture = new ResourceLocation(ModProperties.MOD_ID, "textures/blocks/craftingNum1");
//		this.setBackgroundIcon(ModBlocks.slotCrafter.getIcon(2, 0));
//		this.setBackgroundIconTexture(numberTexture);
//		this.getBackgroundIconTexture();
	}
	
	public boolean isMouseHovering(int x, int y)
	{
		Rectangle rectangle = new Rectangle(xDisplayPosition-1, yDisplayPosition-1, 18, 18);
		FMLLog.finer("Checking if %d, %d is in %s", x, y, rectangle);
		return rectangle.contains(x, y);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.Slot#onPickupFromSlot(net.minecraft.entity.player.EntityPlayer, net.minecraft.item.ItemStack)
	 */
	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack is)
	{
		FMLLog.info("onpickupfromslot %s; %s", player, is);
		super.onPickupFromSlot(player, is);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.Slot#getHasStack()
	 */
	@Override
	public boolean getHasStack()
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.Slot#isItemValid(net.minecraft.item.ItemStack)
	 */
	@Override
	public boolean isItemValid(ItemStack p_75214_1_)
	{
		// TODO Auto-generated method stub
		return super.isItemValid(p_75214_1_);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.Slot#getStack()
	 */
	@Override
	public ItemStack getStack()
	{
		return super.getStack();
	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.Slot#canTakeStack(net.minecraft.entity.player.EntityPlayer)
	 */
	@Override
	public boolean canTakeStack(EntityPlayer p_82869_1_)
	{
		return super.canTakeStack(p_82869_1_);
	}
}
