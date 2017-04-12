package com.cjm721.overloaded.common.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.common.block.ModBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

/**
 * Created by CJ on 4/12/2017.
 */
public abstract class AbstractBlockHyperNode extends ModBlock implements ITileEntityProvider {
    public AbstractBlockHyperNode(Material materialIn) {
        super(materialIn);
        defaultRegistery();
    }

    @Nonnull
    protected abstract String getType();

    @SideOnly(Side.CLIENT)
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
}
