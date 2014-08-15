package com.xaw4.craftcraft.slotchest;

import org.lwjgl.opengl.GL11;

import com.xaw4.craftcraft.constants.ModProperties;
import com.xaw4.craftcraft.general.AbstractSlotGui;
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

public class GuiSlotChest extends AbstractSlotGui<TileEntitySlotChest> {
	
	public GuiSlotChest(InventoryPlayer invPlayer, TileEntitySlotChest te) {
		super(te, new ContainerSlotChest(invPlayer, te));
	}
	
	protected ResourceLocation getGuiBackgroundTexture()
	{
		if(texture != null)
		{
			return texture;
		}
		return new ResourceLocation(ModProperties.MOD_ID, ModProperties.TEX_SLOTCHEST);
	}

}
