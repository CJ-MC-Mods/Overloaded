package com.cjm721.overloaded.block.basic.hyperTransfer;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.basic.hyperTransfer.base.AbstractBlockHyperSender;
import com.cjm721.overloaded.block.tile.hyperTransfer.TileHyperItemSender;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockHyperItemSender extends AbstractBlockHyperSender {

    public BlockHyperItemSender() {
        super(Material.ROCK);
    }

    @Override
    public void baseInit() {
        setRegistryName("hyper_item_sender");
        setUnlocalizedName("hyper_item_sender");

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

    @Override
    public void registerModel() {
        super.registerModel();

        ImageUtil.registerDynamicTexture(
                new ResourceLocation(MODID, "textures/blocks/hyper_item_sender.png"),
                Overloaded.cachedConfig.textureResolutions.blockResolution);
    }
}
