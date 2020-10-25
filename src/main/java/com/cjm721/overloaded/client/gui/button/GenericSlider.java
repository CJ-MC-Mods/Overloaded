package com.cjm721.overloaded.client.gui.button;

import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GenericSlider extends AbstractSlider {

  private final String baseText;
  private boolean dragging;
  private final float minValue;
  private final float maxValue;

  public GenericSlider(
      int x, int y, float minValue, float maxValue, float currentValue, String baseText) {
    super(x, y, 150, 20,new StringTextComponent(baseText), scaleDown(currentValue, minValue, maxValue));
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.baseText = baseText;

    resetDisplayString();
  }

  //    @Override
  //    protected int getHoverState(boolean mouseOver) {
  //        return 0;
  //    }
  //
  //
  //    @Override
  //    public boolean mouseDragged(double p_mouseDragged_1_, double p_mouseDragged_3_, int
  // p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
  //        return super.mouseDragged(p_mouseDragged_1_, p_mouseDragged_3_, p_mouseDragged_5_,
  // p_mouseDragged_6_, p_mouseDragged_8_);
  //    }
  //
  ////    @Override
  ////    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
  ////        if (this.visible) {
  ////            if (this.dragging) {
  ////                this.sliderValue = (float) (mouseX - (this.x + 4)) / (float) (this.width - 8)
  // * maxValue;
  ////                this.sliderValue = MathHelper.clamp(this.sliderValue, minValue, maxValue);
  ////                resetDisplayString();
  ////            }
  ////
  ////            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
  ////            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
  ////            this.drawTexturedModalRect(this.x + (int) (this.sliderValue / maxValue * (float)
  // (this.width - 8)), this.y, 0, 66, 4, 20);
  ////            this.drawTexturedModalRect(this.x + (int) (this.sliderValue / maxValue * (float)
  // (this.width - 8)) + 4, this.y, 196, 66, 4, 20);
  ////        }
  ////    }
  ////
  //    @Override
  //    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
  //        if (super.mousePressed(mc, mouseX, mouseY)) {
  //            this.sliderValue = (float) (mouseX - (this.x + 4)) / (float) (this.width - 8);
  //            this.sliderValue = MathHelper.clamp(this.sliderValue, minValue, maxValue);
  //            this.dragging = true;
  //            resetDisplayString();
  //            return true;
  //        } else {
  //            return false;
  //        }
  //    }
  ////
  ////    @Override
  ////    public void mouseReleased(int mouseX, int mouseY) {
  ////        this.dragging = false;
  ////    }
  //
  private void resetDisplayString() {
    this.setMessage(new StringTextComponent(String.format("%s %.2f", baseText, getEffectiveValue())));
  }
  //
  public double getEffectiveValue() {
    return scaleUp(this.sliderValue, this.minValue, this.maxValue);
  }

  private static double scaleUp(double unScaled, double min, double max) {
    return (max - min) * unScaled + min;
  }

  private static double scaleDown(double scaled, double min, double max) {
    scaled = MathHelper.clamp(scaled, min, max);
    return (scaled - min) / (max - min);
  }

  public void setBasedOnEffectiveValue(float effectiveValue) {
    this.sliderValue = scaleDown(effectiveValue, this.minValue, this.maxValue);
    resetDisplayString();
  }

  @Override
  protected void func_230979_b_() {
    // updateMessage?
    resetDisplayString();
  }

  @Override
  protected void func_230972_a_() {
    // applyValue?
  }
}
