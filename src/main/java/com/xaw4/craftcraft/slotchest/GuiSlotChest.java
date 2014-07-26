package com.xaw4.craftcraft.slotchest;

import org.lwjgl.opengl.GL11;

import com.xaw4.craftcraft.constants.ModProperties;
import com.xaw4.craftcraft.general.FaceConfiguration;
import com.xaw4.craftcraft.general.GuiFaceSelectButton;
import com.xaw4.craftcraft.handler.NetworkHandler;
import com.xaw4.craftcraft.messages.MessageSlotAssignment;
import com.xaw4.craftcraft.util.RelativeFace;

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

	private GuiButton botButton;
	private GuiButton topButton;
	private GuiButton frontButton;
	private GuiButton rightButton;
	private GuiButton backButton;
	private GuiButton leftButton;
	
	private static int x_origin = 19;
	private static int y_origin = 10;
	private static int buttonWidth = 13;
	private static int buttonSpacing = 1;
	private static int buttonSpacingBack = 2;
	
	
	public GuiSlotChest(InventoryPlayer invPlayer, TileEntitySlotChest te) {
		super(new ContainerSlotChest(invPlayer, te));
		
		xSize = 176;
		ySize = 166;
		
		this.te = te;
		texture = getGuiBackgroundTexture();
			x_origin = 9;
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
		
		// Bottom
		if (botButton == null || !this.buttonList.contains(botButton))
		{
			botButton = new GuiFaceSelectButton(RelativeFace.BOTTOM.ordinal(),
					this.guiLeft + x_origin + buttonSpacing + buttonWidth, 
					this.guiTop + y_origin + 2*(buttonSpacing + buttonWidth),
					te.getAssignedSlot(RelativeFace.BOTTOM));
		}
		if (!this.buttonList.contains(botButton))
		{
			this.buttonList.add(botButton);
		}
		// TOP
		if (topButton == null || !this.buttonList.contains(topButton))
		{
			topButton = new GuiFaceSelectButton(RelativeFace.TOP.ordinal(),
					this.guiLeft + x_origin + buttonSpacing + buttonWidth, 
					this.guiTop + y_origin,
					te.getAssignedSlot(RelativeFace.TOP));
		}
		if (!this.buttonList.contains(topButton))
		{
			this.buttonList.add(topButton);
		}
		// Front
		if (frontButton == null || !this.buttonList.contains(frontButton))
		{
			frontButton = new GuiFaceSelectButton(RelativeFace.FRONT.ordinal(),
					this.guiLeft + x_origin + buttonSpacing + buttonWidth, 
					this.guiTop + y_origin + buttonSpacing + buttonWidth,
					te.getAssignedSlot(RelativeFace.FRONT));
		}
		if (!this.buttonList.contains(frontButton))
		{
			this.buttonList.add(frontButton);
		}
		// Right
		if (rightButton == null || !this.buttonList.contains(rightButton))
		{
			rightButton = new GuiFaceSelectButton(RelativeFace.RIGHT.ordinal(),
					this.guiLeft + x_origin + 2*(buttonSpacing + buttonWidth), 
					this.guiTop + y_origin + buttonSpacing + buttonWidth,
					te.getAssignedSlot(RelativeFace.RIGHT));
		}
		if (!this.buttonList.contains(rightButton))
		{
			this.buttonList.add(rightButton);
		}
		// Back
		if (backButton == null || !this.buttonList.contains(backButton))
		{
			backButton = new GuiFaceSelectButton(RelativeFace.BACK.ordinal(),
					this.guiLeft + x_origin + buttonSpacing + buttonWidth * 2 + buttonSpacingBack, 
					this.guiTop + y_origin + buttonSpacing + buttonWidth * 2 + buttonSpacingBack, 
					te.getAssignedSlot(RelativeFace.BACK));
		}
		if (!this.buttonList.contains(backButton))
		{
			this.buttonList.add(backButton);
		}
		// Left
		if (leftButton == null || !this.buttonList.contains(leftButton))
		{
			leftButton = new GuiFaceSelectButton(RelativeFace.LEFT.ordinal(),
					this.guiLeft + x_origin, 
					this.guiTop + y_origin + buttonSpacing + buttonWidth,
					te.getAssignedSlot(RelativeFace.LEFT));
		}
		if (!this.buttonList.contains(leftButton))
		{
			this.buttonList.add(leftButton);
		}
		
	}


	/* (non-Javadoc)
	 * @see net.minecraft.client.gui.GuiScreen#actionPerformed(net.minecraft.client.gui.GuiButton)
	 */
	@Override
	protected void actionPerformed(GuiButton button)
	{
		FMLLog.info("actionPerformed on %s", button);
		if(button != null && button instanceof GuiFaceSelectButton)
		{
			GuiFaceSelectButton faceButton = (GuiFaceSelectButton) button;
			if (0 <= faceButton.id && faceButton.id < 6)
			{
				RelativeFace face = RelativeFace.values()[faceButton.id];
				int newAssignedSlot = te.incrementSlotAssignment(face);
				NetworkHandler.getInstance().sendToServer(new MessageSlotAssignment(face, newAssignedSlot));
				faceButton.setAssignedSlot(newAssignedSlot);
			}
		}
	}

	
	
	
}
