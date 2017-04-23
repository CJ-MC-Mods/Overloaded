package com.cjm721.overloaded.common.block.basic;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlock;
import com.cjm721.overloaded.common.block.tile.TileItemInterface;
import com.cjm721.overloaded.common.config.RecipeEnabledConfig;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockItemInterface extends ModBlock implements ITileEntityProvider {

    public BlockItemInterface() {
        super(Material.ROCK);

        setRegistryName("item_interface");
        setUnlocalizedName("item_interface");

        setHardness(10);
        setCreativeTab(OverloadedCreativeTabs.TECH);
        register();

        GameRegistry.registerTileEntity(TileItemInterface.class, "item_interface");
    }

    @Override
    public void registerRecipe() {
        if(RecipeEnabledConfig.itemInterface) {
            GameRegistry.addRecipe(new ItemStack(this), "NDN", "NEN", "NNN", 'N', Items.NETHER_STAR, 'E', Blocks.CHEST, 'D', Blocks.DRAGON_EGG);
        }
    }

    @Override
    public void registerModel() {

    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileItemInterface();
    }
}
