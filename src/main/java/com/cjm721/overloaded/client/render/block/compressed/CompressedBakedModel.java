package com.cjm721.overloaded.client.render.block.compressed;

import com.cjm721.overloaded.common.block.compressed.BlockCompressed;
import com.google.common.base.Function;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class CompressedBakedModel implements IBakedModel {
    private final Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter;
    private final VertexFormat format;
    private final Map<Block,List<BakedQuad>> cache;

    private final IBakedModel defaultModel;

    CompressedBakedModel(VertexFormat format, IBlockState state, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        this.format = format;

        this.defaultModel = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(state);
        cache = new HashMap<>();
        this.bakedTextureGetter = bakedTextureGetter;
    }

    private void putVertex(UnpackedBakedQuad.Builder builder, Vec3d normal, double x, double y, double z, float u, float v, TextureAtlasSprite sprite) {
        for (int e = 0; e < format.getElementCount(); e++) {
            switch (format.getElement(e).getUsage()) {
                case POSITION:
                    builder.put(e, (float)x, (float)y, (float)z, 1.0f);
                    break;
                case COLOR:
                    builder.put(e, 1.0f, 1.0f, 1.0f, 1.0f);
                    break;
                case UV:
                    if (format.getElement(e).getIndex() == 0) {
                        u = sprite.getInterpolatedU(u);
                        v = sprite.getInterpolatedV(v);
                        builder.put(e, u, v, 0f, 1f);
                        break;
                    }
                case NORMAL:
                    builder.put(e, (float) normal.xCoord, (float) normal.yCoord, (float) normal.zCoord, 0f);
                    break;
                default:
                    builder.put(e);
                    break;
            }
        }
    }

    private BakedQuad createQuad(Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, TextureAtlasSprite sprite) {
        Vec3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();

        UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
        builder.setTexture(sprite);
        putVertex(builder, normal, v1.xCoord, v1.yCoord, v1.zCoord, 0, 0, sprite);
        putVertex(builder, normal, v2.xCoord, v2.yCoord, v2.zCoord, 0, 16, sprite);
        putVertex(builder, normal, v3.xCoord, v3.yCoord, v3.zCoord, 16, 16, sprite);
        putVertex(builder, normal, v4.xCoord, v4.yCoord, v4.zCoord, 16, 0, sprite);
        return builder.build();
    }

    @Override
    @Nonnull
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        Block block;
        if(state == null) {
            block = Blocks.COBBLESTONE;
        } else  {
            block = state.getBlock();
        }
        List<BakedQuad> quads; // = cache.get(block);
//
//        if(quads != null){
//            return quads;
//        }

        quads = new ArrayList<>();
        BlockCompressed compressedBlock = (BlockCompressed)block;
        IBakedModel tempModel = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(compressedBlock.getBaseBlock().getDefaultState());


        if(side == null) {
            for(EnumFacing face: EnumFacing.values()) {
                getQuadsForSide(face, quads, compressedBlock, tempModel);
            }
        } else {
            getQuadsForSide(side,quads,compressedBlock,tempModel);
        }


//        this.cache.put(block, quads);
        return quads;
    }

    private void getQuadsForSide(@Nonnull EnumFacing side, @Nonnull List<BakedQuad> quads, @Nonnull BlockCompressed compressedBlock,@Nonnull IBakedModel tempModel) {
        List<BakedQuad> baseQuads = tempModel.getQuads(compressedBlock.getBaseBlock().getDefaultState(), side,0); // .get(0).getSprite(); //.getFrameTextureData(0);

        if(baseQuads.size() == 0) {
            return;
        }

        TextureAtlasSprite sprite;// = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(new ResourceLocation("overloaded", "dynamic/logo").toString());
        sprite = baseQuads.get(0).getSprite();

        // z == 0
        switch(side) {
            case WEST: // X == 0
                quads.add(createQuad(new Vec3d(0, 1  , 0 ), new Vec3d(0,0 , 0 ),
                        new Vec3d(0, 0 , 1 ),new Vec3d(0, 1  , 1 ), sprite));
            case EAST: // X == 1
                quads.add(createQuad(new Vec3d(1, 1  , 1 ), new Vec3d(1, 0 , 1 ),
                        new Vec3d(1,0 , 0 ), new Vec3d(1, 1  , 0 ), sprite));
            case NORTH: // Z = 0
                quads.add(createQuad(new Vec3d(1, 1, 0), new Vec3d(1, 0, 0),
                        new Vec3d(0, 0, 0), new Vec3d(0, 1, 0), sprite));
            case SOUTH: // Z == 1
                quads.add(createQuad(new Vec3d(0, 1, 1), new Vec3d(0, 0, 1),
                        new Vec3d(1, 0, 1), new Vec3d(1, 1, 1), sprite));
            case UP:
                quads.add(createQuad(new Vec3d(0, 1,0), new Vec3d(0, 1, 1),
                        new Vec3d(1, 1, 1), new Vec3d(1, 1,0), sprite));
            case DOWN:
                quads.add(createQuad(new Vec3d(0 , 0, 1 ),new Vec3d(0 , 0,0 ),
                        new Vec3d(1  , 0,0 ), new Vec3d(1  , 0, 1 ),sprite));
        }
    }

    @Override
    @Nonnull
    public ItemOverrideList getOverrides() {
        return defaultModel.getOverrides();
    }

    @Override
    public boolean isAmbientOcclusion() {
        return defaultModel.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return defaultModel.isGui3d();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return defaultModel.isBuiltInRenderer();
    }

    @Override
    @Nonnull
    public TextureAtlasSprite getParticleTexture() {
        return defaultModel.getParticleTexture();
    }

    @Deprecated
    @Override
    @Nonnull
    public ItemCameraTransforms getItemCameraTransforms() {
        return defaultModel.getItemCameraTransforms();
    }
}
