package com.cjm721.overloaded.client.gui;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.client.gui.button.GuiSlider;
import com.cjm721.overloaded.client.gui.button.GuiToggle;
import com.cjm721.overloaded.network.packets.MultiArmorSettingsMessage;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.io.IOException;

public class MultiArmorGuiScreen extends GuiScreen {

    private GuiButton save;
    private GuiButton cancel;
    private GuiSlider flightSpeed;
    private GuiSlider groundSpeed;
    private GuiToggle noClipFlightLock;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        this.save = addButton(new GuiButton(0, 100, 100, "Save"));
        this.cancel = addButton(new GuiButton(1, 100, 120, "Cancel"));
        this.flightSpeed = addButton(new GuiSlider(3, 100, 140, 0, 10, 1, "Flight Speed:"));
        this.groundSpeed = addButton(new GuiSlider(4, 100, 160, 0, 5, 2, "Ground Speed:"));
        this.noClipFlightLock = addButton(new GuiToggle(5, 100, 180, false, "No Clip Flight Lock:"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (this.save == button) {
            IMessage message = new MultiArmorSettingsMessage(flightSpeed.getSliderValue(), groundSpeed.getSliderValue(), noClipFlightLock.getBooleanState());
            Overloaded.proxy.networkWrapper.sendToServer(message);
        } else if (this.cancel == button) {
            this.mc.displayGuiScreen(null);
        } else if (this.noClipFlightLock == button) {
            this.noClipFlightLock.toggle();
        }
    }
}
