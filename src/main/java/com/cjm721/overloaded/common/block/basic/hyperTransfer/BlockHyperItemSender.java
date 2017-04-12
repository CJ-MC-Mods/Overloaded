package com.cjm721.overloaded.common.block.basic;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.basic.base.AbstractBlockHyperSender;
import com.cjm721.overloaded.common.block.tile.TileHyperItemReceiver;
import com.cjm721.overloaded.common.block.tile.TileHyperItemSender;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static com.cjm721.overloaded.Overloaded.MODID;

/**
 * Created by CJ on 4/8/2017.
 */
public class BlockHyperItemSender extends AbstractBlockHyperSender {

    public BlockHyperItemSender() {
        super(Material.ROCK);

        setRegistryName("BlockHyperItemSender");
        setUnlocalizedName("BlockHyperItemSender");

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(OverloadedCreativeTabs.UTILITY);
        register();
        GameRegistry.registerTileEntity(TileHyperItemSender.class, MODID + ":hyperItemSender");
    }

    @Override
    public void registerRecipe() {

    }

    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "sideTest"), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
                return location;
            }
        };
        ModelLoader.setCustomStateMapper(this, ignoreState);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param worldIn
     * @param meta
     */
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileHyperItemSender();
    }

    @Override
    public String getType() {
        return "Item";
    }
}
