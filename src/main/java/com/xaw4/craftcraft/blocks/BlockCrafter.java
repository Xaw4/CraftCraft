package com.xaw4.craftcraft.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Xaw4 on 25.06.2014.
 */
public class BlockCrafter extends BlockContainer {
    public BlockCrafter(Material p_i45386_1_) {
        super(p_i45386_1_);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return null;
    }
}
