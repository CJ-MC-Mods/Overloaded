package com.cjm721.overloaded.common.block.basic;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlock;
import com.cjm721.overloaded.common.block.tile.TilePlayerInterface;
import com.cjm721.overloaded.common.config.RecipeEnabledConfig;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockPlayerInterface extends ModBlock implements ITileEntityProvider {

    public BlockPlayerInterface() {
        super(Material.WOOD);

        setRegistryName("player_interface");
        setUnlocalizedName("player_interface");

        setHardness(50);
        setCreativeTab(OverloadedCreativeTabs.TECH);
        register();

        GameRegistry.registerTileEntity(TilePlayerInterface.class, "player_interface");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TilePlayerInterface();
    }

    @Override
    public void registerRecipe() {
        if(RecipeEnabledConfig.playerInterface) {
            GameRegistry.addRecipe(new ItemStack(this), "NDN", "NEN", "NNN", 'N', Items.NETHER_STAR, 'E', Blocks.ENDER_CHEST, 'D', Blocks.DRAGON_EGG);
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        ((TilePlayerInterface)worldIn.getTileEntity(pos)).setPlacer(placer);

        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void registerModel() {

    }
}
