package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.storage.stacks.intint.LongItemStack;
import com.cjm721.overloaded.tile.infinity.TileAlmostInfiniteBarrel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BlockAlmostInfiniteBarrel extends AbstractBlockHyperContainer {

    public BlockAlmostInfiniteBarrel(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void registerModel() {
//    ModelResourceLocation location =
//        new ModelResourceLocation(new ResourceLocation(MODID, "almost_infinite_barrel"), null);
//    //            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
//    // location);
//
//    ResizeableTextureGenerator.addToTextureQueue(
//        new ResizeableTextureGenerator.ResizableTexture(
//            new ResourceLocation(MODID, "textures/block/almost_infinite_barrel.png"),
//            new ResourceLocation(MODID, "textures/dynamic/blocks/almost_infinite_barrel.png"),
//            OverloadedConfig.INSTANCE.textureResolutions.blockResolution));
    }

    @Override
    protected void sendPlayerStatus(Level world, BlockPos pos, Player player) {
        LongItemStack stack = ((TileAlmostInfiniteBarrel) world.getBlockEntity(pos)).getStorage().status();
        if (stack.getItemStack().isEmpty()) {
            player.displayClientMessage(Component.literal("Item: EMPTY"), false);
        } else {
            // Have to Restrict Stack size due to Packet validation limits on stack size of 1-99
            ItemStack clonedStack = stack.getItemStack().copyWithCount(Math.min(64, stack.getItemStack().getCount()));
            player.displayClientMessage(
                    Component.literal("Item: ")
                            .append(clonedStack.getDisplayName())
                            .append(String.format(" Amount %,d", stack.getAmount())),
                    false);
        }
    }

    @Override
    public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileAlmostInfiniteBarrel(pos, state);
    }
}
