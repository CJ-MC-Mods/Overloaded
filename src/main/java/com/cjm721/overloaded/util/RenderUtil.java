package com.cjm721.overloaded.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class RenderUtil {
    @SideOnly(Side.CLIENT)
    public static void renderModel(
            final IBakedModel model,
            final IBlockState state,
            final World worldObj,
            final BlockPos blockPos,
            final int alpha )
    {
        final Tessellator tessellator = Tessellator.getInstance();
        final VertexBuffer worldRenderer = tessellator.getBuffer();
        worldRenderer.begin( GL11.GL_QUADS, DefaultVertexFormats.ITEM );

        for ( final EnumFacing enumfacing : EnumFacing.values() )
        {
            renderQuads( alpha, worldRenderer, model.getQuads( null, enumfacing, 0 ), state, worldObj, blockPos );
        }

        renderQuads( alpha, worldRenderer, model.getQuads( null, null, 0 ), state, worldObj, blockPos );
        tessellator.draw();
    }

    @SideOnly(Side.CLIENT)
    public static void renderQuads(
            final int alpha,
            final VertexBuffer renderer,
            final List<BakedQuad> quads,
            final IBlockState state,
            final World worldObj,
            final BlockPos blockPos )
    {
        for (BakedQuad bakedquad: quads)
        {
            final int color = bakedquad.getTintIndex() == -1 ? alpha | 0xffffff : getTint( alpha, bakedquad.getTintIndex(), state, worldObj, blockPos );
            net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor( renderer, bakedquad, color );
        }
    }

    @SideOnly(Side.CLIENT)
    public static int getTint(final int alpha, final int tintIndex, final IBlockState state, final World worldObj, final BlockPos blockPos ) {
        return alpha | Minecraft.getMinecraft().getBlockColors().colorMultiplier(state, worldObj, blockPos, tintIndex );
    }

    @SideOnly(Side.CLIENT)
    public static void renderGhostModel(
            final IBakedModel baked,
            final IBlockState state,
            final World worldObj,
            final BlockPos blockPos)
    {
        final int alpha = 0xaa000000;
        GlStateManager.bindTexture( Minecraft.getMinecraft().getTextureMapBlocks().getGlTextureId() );
        GlStateManager.color( 1.0f, 1.0f, 1.0f, 1.0f );

        GlStateManager.enableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.blendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );
        GlStateManager.colorMask( false, false, false, false );

        renderModel( baked, state, worldObj, blockPos, alpha );
        GlStateManager.colorMask( true, true, true, true );
        GlStateManager.depthFunc( GL11.GL_LEQUAL );
        renderModel( baked, state, worldObj, blockPos, alpha );

        GlStateManager.disableBlend();
    }
}
