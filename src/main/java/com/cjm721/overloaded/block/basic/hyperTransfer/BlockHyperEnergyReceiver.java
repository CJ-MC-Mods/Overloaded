package com.cjm721.overloaded.block.basic.hyperTransfer;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.basic.hyperTransfer.base.AbstractBlockHyperReceiver;
import com.cjm721.overloaded.block.tile.hyperTransfer.TileHyperEnergyReceiver;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockHyperEnergyReceiver extends AbstractBlockHyperReceiver {

    public BlockHyperEnergyReceiver() {
        super(Material.ROCK);
    }

    @Override
    public void baseInit() {
        setRegistryName("hyper_energy_receiver");
        setTranslationKey("hyper_energy_receiver");

        GameRegistry.registerTileEntity(TileHyperEnergyReceiver.class, MODID + ":hyper_energy_receiver");
    }

    @Override
    @Nonnull
    protected String getType() {
        return "Energy";
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileHyperEnergyReceiver();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel() {
        super.registerModel();

        ImageUtil.registerDynamicTexture(
                new ResourceLocation(MODID, "textures/blocks/hyper_energy_receiver.png"),
                Overloaded.cachedConfig.textureResolutions.blockResolution);
    }
}
