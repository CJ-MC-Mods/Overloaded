package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.block.tile.TilePlayerInterface;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.client.render.tile.PlayerInterfaceRenderer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockPlayerInterface extends ModBlock implements ITileEntityProvider {

    public BlockPlayerInterface() {
        super(Material.ROCK);
    }

    @Override
    public void baseInit() {
        setRegistryName("player_interface");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TilePlayerInterface();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        ((TilePlayerInterface) worldIn.getTileEntity(pos)).setPlacer(placer);

        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerModel() {
//        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), null));
        ClientRegistry.bindTileEntitySpecialRenderer(TilePlayerInterface.class, new PlayerInterfaceRenderer());

        ImageUtil.registerDynamicTexture(
                new ResourceLocation(MODID, "textures/blocks/block_player.png"),
                Overloaded.cachedConfig.textureResolutions.blockResolution);
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote && hand == EnumHand.MAIN_HAND) {
            TileEntity te = worldIn.getTileEntity(pos);

            if (te instanceof TilePlayerInterface) {
                UUID placer = ((TilePlayerInterface) te).getPlacer();

                if (placer == null) {
                    player.sendMessage(new TextComponentString("Not bound to anyone..... ghosts placed this."));
                } else {
                    String username = UsernameCache.getLastKnownUsername(placer);
                    player.sendMessage(new TextComponentString("Bound to player: " + (username == null ? placer.toString() : username)));
                }
            }
        }

        return super.onBlockActivated(state, worldIn, pos, player, hand, side, hitX, hitY, hitZ);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new TilePlayerInterface();
    }
}
