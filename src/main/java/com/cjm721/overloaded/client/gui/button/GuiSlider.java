package com.cjm721.overloaded.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSlider extends GuiButton {

    private final String baseText;
    private float sliderValue;
    private boolean dragging;
    private final float minValue;
    private final float maxValue;

    public GuiSlider(int buttonId, int x, int y, float minValueIn, float maxValue, float currentValue, String baseText)
    {
        super(buttonId, x, y, 150, 20, "");
        this.sliderValue = 1.0F;
        this.minValue = minValueIn;
        this.maxValue = maxValue;
        this.sliderValue = currentValue;
        this.baseText = baseText;
        this.displayString = baseText + sliderValue;
    }

    @Override
    protected int getHoverState(boolean mouseOver)
    {
        return 0;
    }

    @Override
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            if (this.dragging)
            {
                this.sliderValue = (float)(mouseX - (this.x + 4)) / (float)(this.width - 8) * maxValue;
                this.sliderValue = MathHelper.clamp(this.sliderValue, minValue, maxValue);
                resetDisplayString();
            }

            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.x + (int)(this.sliderValue / maxValue * (float)(this.width - 8)), this.y, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.x + (int)(this.sliderValue / maxValue * (float)(this.width - 8)) + 4, this.y, 196, 66, 4, 20);
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        if (super.mousePressed(mc, mouseX, mouseY))
        {
            this.sliderValue = (float)(mouseX - (this.x + 4)) / (float)(this.width - 8);
            this.sliderValue = MathHelper.clamp(this.sliderValue, minValue, maxValue);
            this.dragging = true;
            resetDisplayString();
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY)
    {
        this.dragging = false;
    }

    private void resetDisplayString() {
        this.displayString = String.format("%s %.2f", baseText, sliderValue);
    }

    public float getSliderValue() {
        return sliderValue;
    }
}
