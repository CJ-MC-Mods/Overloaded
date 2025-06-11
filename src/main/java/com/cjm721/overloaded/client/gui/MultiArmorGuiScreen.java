package com.cjm721.overloaded.client.gui;

import com.cjm721.overloaded.client.gui.button.GenericSlider;
import com.cjm721.overloaded.client.gui.button.ToggleButton;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.functional.armor.ItemMultiHelmet;
import com.cjm721.overloaded.item.functional.armor.MultiArmorConstants.DataKeys;
import com.cjm721.overloaded.item.functional.armor.MultiArmorConstants.Default;
import com.cjm721.overloaded.network.packets.MultiArmorSettingsMessage;
import com.cjm721.overloaded.storage.IGenericDataStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.components.Button;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;

import java.util.Map;

import static com.cjm721.overloaded.capabilities.CapabilityGenericDataStorage.GENERIC_DATA_STORAGE_ITEM;

@OnlyIn(Dist.CLIENT)
public class MultiArmorGuiScreen extends net.minecraft.client.gui.screens.Screen {

  private GenericSlider flightSpeed;
  private GenericSlider groundSpeed;
  private ToggleButton noClipFlightLock;
  //  private GuiPositiveFloatTextField flightSpeedTextBox;
  //  private GuiPositiveFloatTextField groundSpeedTextBox;
  private ToggleButton flightEnabled;
  private ToggleButton feedEnabled;
  private ToggleButton healEnabled;
  private ToggleButton removeHarmfulEnabled;
  private ToggleButton giveAirEnabled;
  private ToggleButton extinguishEnabled;

  public MultiArmorGuiScreen() {
    super(Component.literal("The TITLE HOW IS THIS SHOWN"));
  }

  @Override
  public void render(GuiGraphics matrixStack, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(matrixStack, mouseX, mouseY, partialTicks);

    super.render(matrixStack, mouseX, mouseY, partialTicks);
  }

  @Override
  public boolean isPauseScreen() {
    return false;
  }

  @Override
  protected void init() {
    super.init();

    @Nullable IGenericDataStorage data = getHelmetDataStorage(Minecraft.getInstance().player);
    if (data == null) {
      this.minecraft.setScreen(null);
      this.minecraft.player.displayClientMessage(
          Component.literal("Multi-Helmet not equipped."), true);
      return;
    }

    Map<String, Float> floats = data.getFloatMap();
    Map<String, Boolean> booleans = data.getBooleanMap();

    addRenderableWidget(
        Button.builder(
                Component.literal("Save"),
                b -> {
                  MultiArmorSettingsMessage message =
                      new MultiArmorSettingsMessage(
                          (float) flightSpeed.getEffectiveValue(),
                          (float) groundSpeed.getEffectiveValue(),
                          noClipFlightLock.getBooleanState(),
                          this.flightEnabled.getBooleanState(),
                          this.feedEnabled.getBooleanState(),
                          this.healEnabled.getBooleanState(),
                          this.removeHarmfulEnabled.getBooleanState(),
                          this.giveAirEnabled.getBooleanState(),
                          this.extinguishEnabled.getBooleanState());
                  PacketDistributor.sendToServer(message);
                  this.minecraft.setScreen(null);
                })
            .pos(this.width / 2, this.height / 4 + 100)
            .size(150, 20)
            .build());

    addRenderableWidget(
        Button.builder(Component.literal("Cancel"), b -> this.minecraft.setScreen(null))
            .pos(this.width / 2 - 150, this.height / 4 + 100)
            .size(150, 20)
            .build());

    float flightSpeedValue = floats.getOrDefault(DataKeys.FLIGHT_SPEED, Default.FLIGHT_SPEED);
    this.flightSpeed =
        addRenderableWidget(
            new GenericSlider(
                this.width / 2 - 150,
                this.height / 4,
                0,
                (float) OverloadedConfig.INSTANCE.multiArmorConfig.maxFlightSpeed,
                flightSpeedValue,
                "Flight Speed:"));
    //    this.flightSpeedTextBox =
    //        addRenderableWidget(
    //            new EditBox(
    //                this.font,
    //                this.flightSpeed.getX(),
    //                this.flightSpeed.getY(),
    //                this.flightSpeed.getWidth(),
    //                this.flightSpeed.getHeight(),
    //                flightSpeedValue,
    //                0,
    //                (float) OverloadedConfig.INSTANCE.multiArmorConfig.maxFlightSpeed));
    //    this.flightSpeedTextBox.setVisible(false);
    this.flightEnabled =
        addRenderableWidget(
            new ToggleButton(
                this.width / 2 - 150,
                this.height / 4 + 20,
                booleans.getOrDefault(DataKeys.FLIGHT, Default.FLIGHT),
                "Flight:"));

    float groundSpeedValue = floats.getOrDefault(DataKeys.GROUND_SPEED, Default.GROUND_SPEED);
    this.groundSpeed =
        addRenderableWidget(
            new GenericSlider(
                this.width / 2,
                this.height / 4,
                0,
                (float) OverloadedConfig.INSTANCE.multiArmorConfig.maxGroundSpeed,
                groundSpeedValue,
                "Ground Speed:"));
    //    this.groundSpeedTextBox =
    //        addRenderableWidget(
    //            new GuiPositiveFloatTextField(
    //                this.font,
    //                this.groundSpeed.x,
    //                this.groundSpeed.y,
    //                this.groundSpeed.getWidth(),
    //                this.groundSpeed.getHeight(),
    //                groundSpeedValue,
    //                0,
    //                (float) OverloadedConfig.INSTANCE.multiArmorConfig.maxGroundSpeed));
    //    this.groundSpeedTextBox.setVisible(false);

    this.feedEnabled =
        addRenderableWidget(
            new ToggleButton(
                this.width / 2,
                this.height / 4 + 20,
                booleans.getOrDefault(DataKeys.FEED, Default.FEED),
                "Feeder:"));
    this.healEnabled =
        addRenderableWidget(
            new ToggleButton(
                this.width / 2 - 150,
                this.height / 4 + 40,
                booleans.getOrDefault(DataKeys.HEAL, Default.HEAL),
                "Healer:"));
    this.removeHarmfulEnabled =
        addRenderableWidget(
            new ToggleButton(
                this.width / 2,
                this.height / 4 + 40,
                booleans.getOrDefault(DataKeys.REMOVE_HARMFUL, Default.REMOVE_HARMFUL),
                "Remove Harmful Potions:"));
    this.giveAirEnabled =
        addRenderableWidget(
            new ToggleButton(
                this.width / 2 - 150,
                this.height / 4 + 60,
                booleans.getOrDefault(DataKeys.GIVE_AIR, Default.GIVE_AIR),
                "Airer:"));
    this.extinguishEnabled =
        addRenderableWidget(
            new ToggleButton(
                this.width / 2,
                this.height / 4 + 60,
                booleans.getOrDefault(DataKeys.EXTINGUISH, Default.EXTINGUISH),
                "Extinguisher:"));
    this.noClipFlightLock =
        addRenderableWidget(
            new ToggleButton(
                this.width / 2 - 75,
                this.height / 4 + 80,
                booleans.getOrDefault(DataKeys.NOCLIP_FLIGHT_LOCK, Default.NOCLIP_FLIGHT_LOCK),
                "No Clip Flight Lock:"));
  }

  @Nullable
  private static IGenericDataStorage getHelmetDataStorage(Player player) {
    for (ItemStack stack : player.getArmorSlots()) {
      if (stack.getItem() instanceof ItemMultiHelmet) {
        return stack.getCapability(GENERIC_DATA_STORAGE_ITEM);
      }
    }
    return null;
  }

  @Override
  public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
    //    this.flightSpeedTextBox.mouseClicked(mouseX, mouseY, mouseButton);
    //    this.groundSpeedTextBox.mouseClicked(mouseX, mouseY, mouseButton);

    //    switch (mouseButton) {
    //      case 0: // Left Click
    //        break;
    //      case 1: // Right Click
    //        if (this.flightSpeed.visible) {
    //          this.flightSpeedTextBox.setVisible(true);
    //          this.flightSpeed.visible = false;
    //
    //          this.flightSpeedTextBox.setValue(
    //              Float.toString((float) this.flightSpeed.getEffectiveValue()));
    //
    //          this.groundSpeedTextBox.setVisible(true);
    //          this.groundSpeed.visible = false;
    //
    //          this.groundSpeedTextBox.setValue(
    //              Float.toString((float) this.groundSpeed.getEffectiveValue()));
    //        } else {
    //          this.flightSpeedTextBox.setVisible(false);
    //          this.flightSpeed.visible = true;
    //
    //          this.flightSpeed.setBasedOnEffectiveValue(this.flightSpeedTextBox.getFloatValue());
    //
    //          this.groundSpeedTextBox.setVisible(false);
    //          this.groundSpeed.visible = true;
    //
    //          this.groundSpeed.setBasedOnEffectiveValue(this.groundSpeedTextBox.getFloatValue());
    //        }
    //        break;
    //      case 2: // Middle Click
    //        break;
    //    }
    return super.mouseClicked(mouseX, mouseY, mouseButton);
  }

  @Override
  public boolean charTyped(char typedChar, int keyCode) {
    //    if (this.flightSpeedTextBox.textboxKeyTyped(typedChar, keyCode)) {
    //      this.flightSpeed.setBasedOnEffectiveValue(this.flightSpeedTextBox.getFloatValue());
    //    }
    //
    //    if (this.groundSpeedTextBox.textboxKeyTyped(typedChar, keyCode)) {
    //      this.groundSpeed.setBasedOnEffectiveValue(this.groundSpeedTextBox.getFloatValue());
    //    }

    return super.charTyped(typedChar, keyCode);
  }
}
