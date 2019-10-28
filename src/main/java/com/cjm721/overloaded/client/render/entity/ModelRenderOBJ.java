package com.cjm721.overloaded.client.render.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.ISprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import org.lwjgl.opengl.GL11;

import java.util.Random;

/** Based off brandon3055's work * */
@OnlyIn(Dist.CLIENT)
public class ModelRenderOBJ extends RendererModel {

  public static ModelBakery BAKERY;

  private int displayList;
  private boolean compiled = false;
  private IBakedModel objModel;
  public float scale = 0;

  public ModelRenderOBJ(Model baseModel, ModelResourceLocation modelResourceLocation) {
    super(baseModel);
    objModel = Minecraft.getInstance().getModelManager().getModel(modelResourceLocation);
  }

  @Override
  public void render(float scale) {
    if (!this.isHidden && this.showModel) {
      if (!this.compiled) {
        this.compileDisplayList(scale);
      }
      GlStateManager.translatef(this.offsetX, this.offsetY, this.offsetZ);

      if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
        if (this.rotationPointX == 0.0F
            && this.rotationPointY == 0.0F
            && this.rotationPointZ == 0.0F) {
          GlStateManager.callList(this.displayList);
        } else {
          GlStateManager.translatef(
              this.rotationPointX * scale,
              this.rotationPointY * scale,
              this.rotationPointZ * scale);
          GlStateManager.callList(this.displayList);
          GlStateManager.translatef(
              -this.rotationPointX * scale,
              -this.rotationPointY * scale,
              -this.rotationPointZ * scale);
        }
      } else {
        GlStateManager.pushMatrix();
        GlStateManager.translatef(
            this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
        if (this.rotateAngleZ != 0.0F) {
          GlStateManager.rotatef(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
        }

        if (this.rotateAngleY != 0.0F) {
          GlStateManager.rotatef(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
        }

        if (this.rotateAngleX != 0.0F) {
          GlStateManager.rotatef(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
        }

        GlStateManager.callList(this.displayList);
        GlStateManager.popMatrix();
      }

      GlStateManager.translatef(-this.offsetX, -this.offsetY, -this.offsetZ);
    }
  }

  private void compileDisplayList(float scale) {
    if (this.scale == 0) {
      this.scale = scale;
    }

    if (objModel == null) {
      compiled = true;
      System.err.println(
          "Armor Model Display List could not be compiled!!! Armor model is broken!");
      return;
    }

    scale = this.scale;
    this.displayList = GLAllocation.generateDisplayLists(1);
    GlStateManager.newList(this.displayList, GL11.GL_COMPILE);

    GlStateManager.pushMatrix();
    GlStateManager.scalef(scale, scale, scale);
    GlStateManager.rotatef(180, -1, 0, 1);

    GlStateManager.bindTexture(Minecraft.getInstance().getTextureMap().getGlTextureId());
    ModelUtils.renderQuads(objModel.getQuads(null, null, new Random()));

    GlStateManager.popMatrix();

    GlStateManager.endList();
    this.compiled = true;
  }

  @Override
  public void renderWithRotation(float scale) {
    if (!this.isHidden && this.showModel) {
      if (!this.compiled) {
        this.compileDisplayList(scale);
      }

      GlStateManager.pushMatrix();
      GlStateManager.translatef(
          this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
      if (this.rotateAngleY != 0.0F) {
        GlStateManager.rotatef(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
      }

      if (this.rotateAngleX != 0.0F) {
        GlStateManager.rotatef(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
      }

      if (this.rotateAngleZ != 0.0F) {
        GlStateManager.rotatef(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
      }

      GlStateManager.callList(this.displayList);
      GlStateManager.popMatrix();
    }
  }

  @Override
  public void postRender(float scale) {
    if (!this.isHidden && this.showModel) {

      if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
        if (this.rotationPointX != 0.0F
            || this.rotationPointY != 0.0F
            || this.rotationPointZ != 0.0F) {
          GlStateManager.translatef(
              this.rotationPointX * scale,
              this.rotationPointY * scale,
              this.rotationPointZ * scale);
        }
      } else {
        GlStateManager.translatef(
            this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
        if (this.rotateAngleZ != 0.0F) {
          GlStateManager.rotatef(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
        }

        if (this.rotateAngleY != 0.0F) {
          GlStateManager.rotatef(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
        }

        if (this.rotateAngleX != 0.0F) {
          GlStateManager.rotatef(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
        }
      }
    }
  }
}
