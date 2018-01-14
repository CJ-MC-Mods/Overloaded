package com.cjm721.overloaded.block.reactor;

import com.cjm721.overloaded.block.ModBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockFusionInterface extends ModBlock implements ITileEntityProvider {

    public BlockFusionInterface(@Nonnull Material materialIn) {
        super(materialIn);
    }

    @Override
    public void baseInit() {
        setRegistryName("fusion_interface");
        setUnlocalizedName("fusion_interface");


        GameRegistry.registerTileEntity(TileFusionCore.class, MODID + ":fusion_interface");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param worldIn
     * @param meta
     */
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileFusionInterface();
    }
}
