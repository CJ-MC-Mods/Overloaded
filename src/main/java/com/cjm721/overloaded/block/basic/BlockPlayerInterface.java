package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.block.tile.TilePlayerInterface;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.client.render.tile.PlayerInterfaceRenderer;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockPlayerInterface extends ModBlock implements ITileEntityProvider {

    public BlockPlayerInterface() {
        super(Material.WOOD);
    }

    @Override
    public void baseInit() {
        setRegistryName("player_interface");
        setUnlocalizedName("player_interface");

        GameRegistry.registerTileEntity(TilePlayerInterface.class, MODID + ":player_interface");
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
    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), null));
        ClientRegistry.bindTileEntitySpecialRenderer(TilePlayerInterface.class, new PlayerInterfaceRenderer());

        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID, "textures/blocks/block_player.png"),
                new ResourceLocation(MODID, "textures/dynamic/blocks/block_player.png"),
                OverloadedConfig.textureResolutions.blockResolution));
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos, EnumFacing side) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote && hand == EnumHand.MAIN_HAND) {
            TileEntity te = worldIn.getTileEntity(pos);

            if (te instanceof TilePlayerInterface) {
                UUID placer = ((TilePlayerInterface) te).getPlacer();

                GameProfile profile = worldIn.getMinecraftServer().getPlayerProfileCache().getProfileByUUID(placer);

                if (profile == null) {
                    playerIn.sendMessage(new TextComponentString(String.format("Bound to offline player: %s", placer.toString())));
                } else {
                    playerIn.sendMessage(new TextComponentString("Bound to player: " + profile.getName()));
                }
            }
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
