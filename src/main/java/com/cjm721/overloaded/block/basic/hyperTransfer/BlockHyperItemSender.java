package com.cjm721.overloaded.block.basic.hyperTransfer;

import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.block.basic.hyperTransfer.base.AbstractBlockHyperSender;
import com.cjm721.overloaded.block.tile.hyperTransfer.TileHyperItemSender;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockHyperItemSender extends AbstractBlockHyperSender {

    public BlockHyperItemSender() {
        super(Material.ROCK);

        setRegistryName("hyper_item_sender");
        setUnlocalizedName("hyper_item_sender");

        setCreativeTab(OverloadedCreativeTabs.TECH);

        GameRegistry.registerTileEntity(TileHyperItemSender.class, MODID + ":hyper_item_sender");
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileHyperItemSender();
    }

    @Nonnull
    @Override
    public String getType() {
        return "Item";
    }
}
