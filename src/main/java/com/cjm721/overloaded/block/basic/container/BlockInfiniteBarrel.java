package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.block.tile.infinity.TileInfiniteBarrel;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.storage.IHyperHandler;
import com.cjm721.overloaded.storage.IHyperType;
import com.cjm721.overloaded.storage.LongItemStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.util.CapabilityHyperItem.HYPER_ITEM_HANDLER;

public class BlockInfiniteBarrel extends AbstractBlockInfiniteContainer {

    public BlockInfiniteBarrel() {
        super(ModBlock.getDefaultProperties());
    }

    @Override
    public void baseInit() {
        setRegistryName("infinite_barrel");
//        setTranslationKey("infinite_barrel");

//        GameRegistry.registerTileEntity(TileInfiniteBarrel.class, MODID + ":infinite_barrel");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "infinite_barrel"), null);
//        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID, "textures/blocks/infinite_barrel.png"),
                new ResourceLocation(MODID, "textures/dynamic/blocks/infinite_barrel.png"),
                Overloaded.cachedConfig.textureResolutions.blockResolution));
    }

    @Override
    protected void sendPlayerStatus(World world, BlockPos pos, PlayerEntity player) {
        LongItemStack stack = ((TileInfiniteBarrel) world.getTileEntity(pos)).getStorage().status();
        if (stack.getItemStack().isEmpty()) {
            player.sendStatusMessage(new StringTextComponent("Item: EMPTY"), false);
        } else {
            player.sendStatusMessage(new StringTextComponent("Item: ").appendSibling(stack.getItemStack().getTextComponent()).appendText(String.format(" Amount %,d", stack.getAmount())), false);
        }
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileInfiniteBarrel();
    }

    @Nonnull
    @Override
    <T extends IHyperHandler> Capability<T> getHyperCapabilityType() {
        return (Capability<T>)HYPER_ITEM_HANDLER;
    }
}