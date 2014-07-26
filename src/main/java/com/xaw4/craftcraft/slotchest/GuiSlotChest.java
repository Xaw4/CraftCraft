package com.xaw4.craftcraft.slotchest;

import org.lwjgl.opengl.GL11;

import com.xaw4.craftcraft.constants.ModProperties;
import com.xaw4.craftcraft.general.GuiFaceSelectButton;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiSlotChest extends GuiContainer {
	
	private final ResourceLocation texture;
	
	TileEntitySlotChest te;
	
	private GuiButton topButton;
	private static int x_top;
	private static int y_top;
	private static int buttonWidth;
	
	
	public GuiSlotChest(InventoryPlayer invPlayer, TileEntitySlotChest te) {
		super(new ContainerSlotChest(invPlayer, te));
		
		xSize = 176;
		ySize = 166;
		
		this.te = te;
		texture = getGuiBackgroundTexture();
		
		x_top = 23;
		y_top = 10;
		buttonWidth = 13;
		
		
		
	}
	
	protected ResourceLocation getGuiBackgroundTexture()
	{
		return new ResourceLocation(ModProperties.MOD_ID, ModProperties.TEX_SLOTCHEST);
	}


	@Override
	protected void drawGuiContainerBackgroundLayer(float f,
			int x, int y) {
		FMLLog.finer("rendering gui (%s)", texture);
		GL11.glColor4f(1, 1, 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}


	/* (non-Javadoc)
	 * @see net.minecraft.client.gui.inventory.GuiContainer#initGui()
	 */
	@Override
	public void initGui()
	{
		super.initGui();
		if (topButton == null)
		{
			topButton = new GuiFaceSelectButton(
					1, this.guiLeft + x_top, this.guiTop + y_top);
			// this.buttonList.add( new GuiButton(
			// 1, this.guiLeft + x_top, this.guiTop + y_top,
			// buttonWidth, buttonWidth, "1"));
		}
		if (!this.buttonList.contains(topButton))
		{
			this.buttonList.add(topButton);
		}
		
	}


	/* (non-Javadoc)
	 * @see net.minecraft.client.gui.GuiScreen#actionPerformed(net.minecraft.client.gui.GuiButton)
	 */
	@Override
	protected void actionPerformed(GuiButton button)
	{
		FMLLog.info("actionPerformed on %s", button);
		button.displayString=" _x _";
	}

	
	
	
}
