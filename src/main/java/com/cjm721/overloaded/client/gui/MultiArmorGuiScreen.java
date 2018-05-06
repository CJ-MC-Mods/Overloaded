package com.cjm721.overloaded.client.gui;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.client.gui.button.GuiSlider;
import com.cjm721.overloaded.client.gui.button.GuiToggle;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.functional.armor.ItemMultiHelmet;
import com.cjm721.overloaded.network.packets.MultiArmorSettingsMessage;
import com.cjm721.overloaded.storage.GenericDataStorage;
import com.cjm721.overloaded.storage.IGenericDataStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Map;

import static com.cjm721.overloaded.item.functional.armor.MultiArmorConstants.*;
import static com.cjm721.overloaded.storage.GenericDataStorage.GENERIC_DATA_STORAGE;

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
        IGenericDataStorage data = getHelmetDataStorage(Minecraft.getMinecraft().player);

        if(data == null) {
            this.mc.displayGuiScreen(null);
            return;
        }

        Map<String, Float> floats = data.getFloatMap();
        Map<String, Boolean> booleans = data.getBooleanMap();

        this.save = addButton(new GuiButton(0, 100, 100, "Save"));
        this.cancel = addButton(new GuiButton(1, 100, 120, "Cancel"));
        this.flightSpeed = addButton(new GuiSlider(3, 100, 140, 0, OverloadedConfig.multiArmorConfig.maxFlightSpeed, floats.getOrDefault(FLIGHT_SPEED,  DEFAULT_ARMOR_FLIGHT_SPEED), "Flight Speed:"));
        this.groundSpeed = addButton(new GuiSlider(4, 100, 160, 0, OverloadedConfig.multiArmorConfig.maxGroundSpeed, floats.getOrDefault(GROUND_SPEED, DEFAULT_ARMOR_GROUND_SPEED), "Ground Speed:"));
        this.noClipFlightLock = addButton(new GuiToggle(5, 100, 180, booleans.getOrDefault(NOCLIP_FLIGHT_LOCK, DEFAULT_NOCLIP_FLIGHT_LOCK), "No Clip Flight Lock:"));
    }

    @Nullable
    private static IGenericDataStorage getHelmetDataStorage(EntityPlayer player) {
        for (ItemStack stack : player.getArmorInventoryList()) {
            if (stack.getItem() instanceof ItemMultiHelmet) {
                IGenericDataStorage cap = stack.getCapability(GENERIC_DATA_STORAGE, null);
                cap.suggestUpdate();
                return cap;
            }
        }
        return null;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (this.save == button) {
            IMessage message = new MultiArmorSettingsMessage(flightSpeed.getSliderValue(), groundSpeed.getSliderValue(), noClipFlightLock.getBooleanState());
            Overloaded.proxy.networkWrapper.sendToServer(message);
            this.mc.displayGuiScreen(null);
        } else if (this.cancel == button) {
            this.mc.displayGuiScreen(null);
        } else if (this.noClipFlightLock == button) {
            this.noClipFlightLock.toggle();
        }
    }
}
