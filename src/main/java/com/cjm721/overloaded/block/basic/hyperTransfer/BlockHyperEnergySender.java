package com.cjm721.overloaded.block.basic.hyperTransfer;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.basic.hyperTransfer.base.AbstractBlockHyperSender;
import com.cjm721.overloaded.block.tile.hyperTransfer.TileHyperEnergySender;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockHyperEnergySender extends AbstractBlockHyperSender implements ITileEntityProvider {

    public BlockHyperEnergySender() {
        super(Material.ROCK);
    }

    @Override
    public void baseInit() {
        setRegistryName("hyper_energy_sender");
        setTranslationKey("hyper_energy_sender");

        GameRegistry.registerTileEntity(TileHyperEnergySender.class, MODID + ":hyper_energy_sender");
    }

    @Override
    @Nonnull
    public String getType() {
        return "Energy";
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileHyperEnergySender();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerModel() {
        super.registerModel();
        ImageUtil.registerDynamicTexture(
                new ResourceLocation(MODID, "textures/blocks/hyper_energy_sender.png"),
                Overloaded.cachedConfig.textureResolutions.blockResolution);
    }
}
