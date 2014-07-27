package com.xaw4.craftcraft.general;

import com.xaw4.craftcraft.constants.ModProperties;
import com.xaw4.craftcraft.creativetab.CraftCraftCreativeTab;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

/**
 * Created by Xaw4 on 25.06.2014.
 */
public abstract class BlockCC extends Block {

    private String unqualifiedName;

    
    
    protected BlockCC(String name) {
        super(Material.wood);
        this.unqualifiedName = name;
        this.setBlockName(name);
        this.setCreativeTab(CraftCraftCreativeTab.instance);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypePiston);
    }
    

    public void registerBlock()
    {
        GameRegistry.registerBlock(this, this.unqualifiedName);
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + this.getQualifiedName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(this.getQualifiedName());
    }
    
    public String getQualifiedName(){
        return ModProperties.MOD_ID + ":" + this.unqualifiedName;
    }
    
}
