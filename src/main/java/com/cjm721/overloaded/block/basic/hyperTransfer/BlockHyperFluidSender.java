package com.cjm721.overloaded.block.basic.hyperTransfer;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.basic.hyperTransfer.base.AbstractBlockHyperSender;
import com.cjm721.overloaded.block.tile.hyperTransfer.TileHyperFluidSender;
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

public class BlockHyperFluidSender extends AbstractBlockHyperSender {

    public BlockHyperFluidSender() {
        super(Material.ROCK);
    }

    @Override
    public void baseInit() {
        setRegistryName("hyper_fluid_sender");
        setUnlocalizedName("hyper_fluid_sender");

        GameRegistry.registerTileEntity(TileHyperFluidSender.class, MODID + ":hyper_fluid_sender");
    }

    @Nonnull
    @Override
    public String getType() {
        return "Fluid";
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileHyperFluidSender();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel() {
        super.registerModel();

        ImageUtil.registerDynamicTexture(
                new ResourceLocation(MODID, "textures/blocks/hyper_fluid_sender.png"),
                Overloaded.cachedConfig.textureResolutions.blockResolution);
    }
}
