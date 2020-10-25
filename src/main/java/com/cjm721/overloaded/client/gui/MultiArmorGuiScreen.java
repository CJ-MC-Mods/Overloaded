package com.cjm721.overloaded.client.gui;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.client.gui.button.GenericSlider;
import com.cjm721.overloaded.client.gui.button.GuiPositiveFloatTextField;
import com.cjm721.overloaded.client.gui.button.ToggleButton;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.functional.armor.ItemMultiHelmet;
import com.cjm721.overloaded.item.functional.armor.MultiArmorConstants.DataKeys;
import com.cjm721.overloaded.item.functional.armor.MultiArmorConstants.Default;
import com.cjm721.overloaded.network.packets.MultiArmorSettingsMessage;
import com.cjm721.overloaded.storage.IGenericDataStorage;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

import static com.cjm721.overloaded.capabilities.CapabilityGenericDataStorage.GENERIC_DATA_STORAGE;

@OnlyIn(Dist.CLIENT)
public class MultiArmorGuiScreen extends Screen {

  private GenericSlider flightSpeed;
  private GenericSlider groundSpeed;
  private ToggleButton noClipFlightLock;
  private GuiPositiveFloatTextField flightSpeedTextBox;
  private GuiPositiveFloatTextField groundSpeedTextBox;
  private ToggleButton flightEnabled;
  private ToggleButton feedEnabled;
  private ToggleButton healEnabled;
  private ToggleButton removeHarmfulEnabled;
  private ToggleButton giveAirEnabled;
  private ToggleButton extinguishEnabled;

  public MultiArmorGuiScreen() {
    super(new StringTextComponent("The TITLE HOW IS THIS SHOWN"));
  }

  @Override
  public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(matrixStack);
    super.render(matrixStack, mouseX, mouseY, partialTicks);
  }

  @Override
  public boolean isPauseScreen() {
    return false;
  }

  @Override
  protected void init() {
    super.init();

    LazyOptional<IGenericDataStorage> opData = getHelmetDataStorage(Minecraft.getInstance().player);
    if (!opData.isPresent()) {
      this.minecraft.displayGuiScreen(null);
      this.minecraft.player.sendStatusMessage(
          new StringTextComponent("Multi-Helmet not equipped."), true);
      return;
    }

    IGenericDataStorage data =
        opData.orElseThrow(() -> new RuntimeException("Impossible Condition"));

    Map<String, Float> floats = data.getFloatMap();
    Map<String, Boolean> booleans = data.getBooleanMap();

        addButton(
            new Button(
                this.width / 2,
                this.height / 4 + 100,
                150,
                20,
                new StringTextComponent("Save"),
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
                  Overloaded.proxy.networkWrapper.sendToServer(message);
                  this.minecraft.displayGuiScreen(null);
                }));
        addButton(
            new Button(
                this.width / 2 - 150,
                this.height / 4 + 100,
                150,
                20,
                new StringTextComponent("Cancel"),
                b -> this.minecraft.displayGuiScreen(null)));

    float flightSpeedValue = floats.getOrDefault(DataKeys.FLIGHT_SPEED, Default.FLIGHT_SPEED);
    this.flightSpeed =
        addButton(
            new GenericSlider(
                this.width / 2 - 150,
                this.height / 4,
                0,
                (float) OverloadedConfig.INSTANCE.multiArmorConfig.maxFlightSpeed,
                flightSpeedValue,
                "Flight Speed:"));
    this.flightSpeedTextBox =
        addButton(new GuiPositiveFloatTextField(
            this.font,
            this.flightSpeed.x,
            this.flightSpeed.y,
            this.flightSpeed.getWidth(),
            this.flightSpeed.getHeightRealms(),
            flightSpeedValue,
            0,
            (float) OverloadedConfig.INSTANCE.multiArmorConfig.maxFlightSpeed));
    this.flightSpeedTextBox.setVisible(false);
    this.flightEnabled =
        addButton(
            new ToggleButton(
                this.width / 2 - 150,
                this.height / 4 + 20,
                booleans.getOrDefault(DataKeys.FLIGHT, Default.FLIGHT),
                "Flight:"));

    float groundSpeedValue = floats.getOrDefault(DataKeys.GROUND_SPEED, Default.GROUND_SPEED);
    this.groundSpeed =
        addButton(
            new GenericSlider(
                this.width / 2,
                this.height / 4,
                0,
                (float) OverloadedConfig.INSTANCE.multiArmorConfig.maxGroundSpeed,
                groundSpeedValue,
                "Ground Speed:"));
    this.groundSpeedTextBox =
        addButton(new GuiPositiveFloatTextField(
            this.font,
            this.groundSpeed.x,
            this.groundSpeed.y,
            this.groundSpeed.getWidth(),
            this.groundSpeed.getHeightRealms(),
            groundSpeedValue,
            0,
            (float) OverloadedConfig.INSTANCE.multiArmorConfig.maxGroundSpeed));
    this.groundSpeedTextBox.setVisible(false);

    this.feedEnabled =
        addButton(
            new ToggleButton(
                this.width / 2,
                this.height / 4 + 20,
                booleans.getOrDefault(DataKeys.FEED, Default.FEED),
                "Feeder:"));
    this.healEnabled =
        addButton(
            new ToggleButton(
                this.width / 2 - 150,
                this.height / 4 + 40,
                booleans.getOrDefault(DataKeys.HEAL, Default.HEAL),
                "Healer:"));
    this.removeHarmfulEnabled =
        addButton(
            new ToggleButton(
                this.width / 2,
                this.height / 4 + 40,
                booleans.getOrDefault(DataKeys.REMOVE_HARMFUL, Default.REMOVE_HARMFUL),
                "Remove Harmful Potions:"));
    this.giveAirEnabled =
        addButton(
            new ToggleButton(
                this.width / 2 - 150,
                this.height / 4 + 60,
                booleans.getOrDefault(DataKeys.GIVE_AIR, Default.GIVE_AIR),
                "Airer:"));
    this.extinguishEnabled =
        addButton(
            new ToggleButton(
                this.width / 2,
                this.height / 4 + 60,
                booleans.getOrDefault(DataKeys.EXTINGUISH, Default.EXTINGUISH),
                "Extinguisher:"));
    this.noClipFlightLock =
        addButton(
            new ToggleButton(
                this.width / 2 - 75,
                this.height / 4 + 80,
                booleans.getOrDefault(DataKeys.NOCLIP_FLIGHT_LOCK, Default.NOCLIP_FLIGHT_LOCK),
                "No Clip Flight Lock:"));
  }

  @Nullable
  private static LazyOptional<IGenericDataStorage> getHelmetDataStorage(PlayerEntity player) {
    for (ItemStack stack : player.getArmorInventoryList()) {
      if (stack.getItem() instanceof ItemMultiHelmet) {
        return stack.getCapability(GENERIC_DATA_STORAGE);
      }
    }
    return LazyOptional.empty();
  }

  @Override
  public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
    this.flightSpeedTextBox.mouseClicked(mouseX, mouseY, mouseButton);
    this.groundSpeedTextBox.mouseClicked(mouseX, mouseY, mouseButton);

    switch (mouseButton) {
      case 0: // Left Click
        break;
      case 1: // Right Click
        if (this.flightSpeed.visible) {
          this.flightSpeedTextBox.setVisible(true);
          this.flightSpeed.visible = false;

          this.flightSpeedTextBox.setText(Float.toString((float)this.flightSpeed.getEffectiveValue()));

          this.groundSpeedTextBox.setVisible(true);
          this.groundSpeed.visible = false;

          this.groundSpeedTextBox.setText(Float.toString((float)this.groundSpeed.getEffectiveValue()));
        } else {
          this.flightSpeedTextBox.setVisible(false);
          this.flightSpeed.visible = true;

          this.flightSpeed.setBasedOnEffectiveValue(this.flightSpeedTextBox.getFloatValue());

          this.groundSpeedTextBox.setVisible(false);
          this.groundSpeed.visible = true;

          this.groundSpeed.setBasedOnEffectiveValue(this.groundSpeedTextBox.getFloatValue());
        }
        break;
      case 2: // Middle Click
        break;
    }
    return super.mouseClicked(mouseX, mouseY, mouseButton);
  }

  //    @Override
  //    protected void keyTyped(char typedChar, int keyCode) throws IOException {
  //        if (this.flightSpeedTextBox.textboxKeyTyped(typedChar, keyCode)) {
  //            this.flightSpeed.setBasedOnEffectiveValue(this.flightSpeedTextBox.getFloatValue());
  //        }
  //
  //        if (this.groundSpeedTextBox.textboxKeyTyped(typedChar, keyCode)) {
  //            this.groundSpeed.setBasedOnEffectiveValue(this.groundSpeedTextBox.getFloatValue());
  //        }
  //
  //        super.keyTyped(typedChar, keyCode);
  //    }
}
