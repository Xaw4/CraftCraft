package com.xaw4.craftcraft.general;

import org.lwjgl.opengl.GL11;

import com.xaw4.craftcraft.constants.ModProperties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

public class GuiFaceSelectButton extends GuiButton
{
	protected  static final ResourceLocation buttonTextures = new ResourceLocation(ModProperties.MOD_ID, "textures/gui/widgets.png");

	protected static int _width = 13;
	
	public GuiFaceSelectButton(int id, int x, int y)
	{
		super(id, x, y, 13, 13, " ");
	}

	 /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft minecraft, int mouse_x, int mouse_y)
    {
        if (this.visible)
        {
            FontRenderer fontrenderer = minecraft.fontRenderer;
            minecraft.getTextureManager().bindTexture(buttonTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = mouse_x >= this.xPosition && mouse_y >= this.yPosition && 
            				mouse_x < this.xPosition + this.width && mouse_y < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 0 + k * _width, this.width, this.height);
//            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
//            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
//            
            this.mouseDragged(minecraft, mouse_x, mouse_y);
            int l = 14737632;

            if (packedFGColour != 0)
            {
                l = packedFGColour;
            }
            else if (!this.enabled)
            {
                l = 10526880;
            }
            else if (this.field_146123_n)
            {
                l = 16777120;
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
        }
    }

}
