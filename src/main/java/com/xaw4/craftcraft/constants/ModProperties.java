package com.xaw4.craftcraft.constants;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.util.ResourceLocation;

/**
 * Created by Xaw4 on 25.06.2014.
 */
public class ModProperties {

    public static final String MOD_ID = "craftcraft";
    public static final String MOD_NAME = "CraftCraft";
    public static final String MOD_VERSION = "1.7.10-0.2";

    public static final String CLIENT_PROXY = "com.xaw4.craftcraft.proxy.ClientProxy";
    public static final String SERVER_PROXY = "com.xaw4.craftcraft.proxy.ServerProxy";

    public static final GameProfile GAME_PROFILE = new GameProfile(UUID.nameUUIDFromBytes("craftCraft".getBytes()), "[CraftCraft]");

    
    public static final String TEX_SLOTCHEST = "textures/gui/slotChestGui.png";
    public static final String TEX_SLOTCRAFTER = "textures/gui/slotCrafterGui.png";
    
    public static final String POSTFIX_FRONT = "_front";
    public static final String POSTFIX_TOP = "_top";
    
    public static final ResourceLocation RES_SLOT_1 = new ResourceLocation(MOD_ID,"textures/gui/craftingNum1.png");
}
