package com.xaw4.craftcraft.slotcrafter;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.lwjgl.opengl.GL11;

import com.xaw4.craftcraft.constants.ModProperties;
import com.xaw4.craftcraft.general.AbstractSlotGui;
import com.xaw4.craftcraft.general.FaceConfiguration;
import com.xaw4.craftcraft.general.GuiFaceSelectButton;
import com.xaw4.craftcraft.handler.NetworkHandler;
import com.xaw4.craftcraft.init.ModBlocks;
import com.xaw4.craftcraft.messages.MessageSlotAssignment;
import com.xaw4.craftcraft.slotcrafter.inventory.CraftingSlot;
import com.xaw4.craftcraft.slotcrafter.inventory.InventoryCraftingGrid;
import com.xaw4.craftcraft.util.CheckUtils;
import com.xaw4.craftcraft.util.RelativeFace;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.ResourceLocation;

public class GuiSlotCrafter extends AbstractSlotGui<TileEntitySlotCrafter> {
	
	public GuiSlotCrafter(InventoryPlayer invPlayer, TileEntitySlotCrafter te) {
		super(te, new ContainerSlotCrafter(invPlayer, te));

		xSize = 176;
		ySize = 166+24;
	}
	
	protected ResourceLocation getGuiBackgroundTexture()
	{
		if(texture != null)
		{
			return texture;
		}
		return new ResourceLocation(ModProperties.MOD_ID, ModProperties.TEX_SLOTCRAFTER);
	}
	
	

	/* (non-Javadoc)
	 * @see com.xaw4.craftcraft.general.AbstractSlotGui#drawGuiContainerBackgroundLayer(float, int, int)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		super.drawGuiContainerBackgroundLayer(f, x, y);
		InventoryCraftingGrid grid = te.getCraftingGrid();
		for(CraftingSlot slot : grid.getCraftingSlots())
		{
			int assignedSlot = te.getCraftingConfig(slot.getSlotIndex());
			if( CheckUtils.isInRange(assignedSlot, 0, 5))
			{
				Minecraft.getMinecraft().renderEngine.bindTexture(texture);
				drawTexturedModalRect(
						guiLeft+slot.xDisplayPosition,
						guiTop+slot.yDisplayPosition, xSize, 16*assignedSlot, 16, 16);
			}
		}
	}

	/* (non-Javadoc)
	 * @see net.minecraft.client.gui.inventory.GuiContainer#mouseClicked(int, int, int)
	 */
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if(te instanceof TileEntitySlotCrafter)
		{
			TileEntitySlotCrafter craftTe = (TileEntitySlotCrafter) te;
			craftTe.handleMouseClickOnGui(mouseX-guiLeft, mouseY-guiTop, mouseButton);
			
		}
	}
	
	

}
