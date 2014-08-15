package com.xaw4.craftcraft.util;

import java.util.ArrayList;

import com.xaw4.craftcraft.constants.ModProperties;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/**
 * icon utility for non block IIcons
 * shamelessly copied from CrazyPants ( https://github.com/CrazyPants/EnderIO/blob/master/src/main/java/crazypants/render/IconUtil.java )
 *
 */
public class IconUtil {

  public static interface IIconProvider {

    public void registerIcons(IIconRegister register);

    /** 0 = terrain.png, 1 = items.png */
    public int getTextureType();
  }

  private static ArrayList<IIconProvider> iconProviders = new ArrayList<IIconProvider>();

  public static IIcon craftingNum1Texture;

  public static void init(){
	  MinecraftForge.EVENT_BUS.register(new IconUtil());
	    addIconProvider(new IIconProvider() {
	
	      @Override
	      public void registerIcons(IIconRegister register) {
	        craftingNum1Texture = register.registerIcon(ModProperties.MOD_ID + ":craftingNum1");
	      }
	
	      @Override
	      public int getTextureType() {
	        return 0;
	      }
	    });
  }
  

  public static void addIconProvider(IIconProvider registrar) {
    iconProviders.add(registrar);
  }

  @Mod.EventHandler
  @SubscribeEvent
  public void onIconLoad(TextureStitchEvent.Pre event) {
    for (IIconProvider reg : iconProviders) {
      if(reg.getTextureType() == event.map.getTextureType()) {
        reg.registerIcons(event.map);
      }
    }
  }

  public static IIcon getIconForItem(int itemId, int meta) {
    Item item = Item.getItemById(itemId);
    if(item == null) {
      return null;
    }
    return item.getIconFromDamage(meta);
  }

}
