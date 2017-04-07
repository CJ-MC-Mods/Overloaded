package com.cjm721.ibhstd.common.block.basic;

import com.cjm721.ibhstd.common.IBHSTDCreativeTabs;
import com.cjm721.ibhstd.common.block.ModBlock;
import com.cjm721.ibhstd.common.block.tile.TileCreativeGenerator;
import com.cjm721.ibhstd.common.block.tile.TileInfiniteBarrel;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static com.cjm721.ibhstd.IBHSTD.MODID;

/**
 * Created by CJ on 4/6/2017.
 */
public class BlockInfiniteBarrel extends ModBlock implements ITileEntityProvider {
    public BlockInfiniteBarrel() {
        super(Material.WOOD);

        setRegistryName("BlockInfiniteBarrel");
        setUnlocalizedName("BlockInfiniteBarrel");

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(IBHSTDCreativeTabs.UTILITY);
        register();
        GameRegistry.registerTileEntity(TileInfiniteBarrel.class, MODID + ":infiniteBarrel");
    }

    @Override
    public void registerRecipe() {

    }

    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "barrel"), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
                return location;
            }
        };
        ModelLoader.setCustomStateMapper(this, ignoreState);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileInfiniteBarrel();
    }
}
