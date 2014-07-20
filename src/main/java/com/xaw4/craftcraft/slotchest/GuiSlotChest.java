package com.xaw4.craftcraft.slotchest;

import org.lwjgl.opengl.GL11;

import com.xaw4.craftcraft.constants.ModProperties;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiSlotChest extends GuiContainer {
	
	TileEntitySlotChest te;
	
	private static final ResourceLocation texture = new ResourceLocation(ModProperties.MOD_ID, ModProperties.TEX_SLOTCHEST);
	
	public GuiSlotChest(InventoryPlayer invPlayer, TileEntitySlotChest te) {
		super(new ContainerSlotChest(invPlayer, te));
		
		this.te = te;
		
		xSize = 176;
		ySize = 166;
	}


	@Override
	protected void drawGuiContainerBackgroundLayer(float f,
			int x, int y) {
		FMLLog.finer("rendering gui (%s)", texture);
		GL11.glColor4f(1, 1, 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	
	
	
}
