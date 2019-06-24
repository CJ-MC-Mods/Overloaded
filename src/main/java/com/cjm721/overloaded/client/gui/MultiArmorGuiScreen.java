package com.cjm721.overloaded.client.gui;

//
// public class MultiArmorGuiScreen extends GuiScreen {
//
//    private GuiButton save;
//    private GuiButton cancel;
//    private GuiSlider flightSpeed;
//    private GuiSlider groundSpeed;
//    private GuiToggle noClipFlightLock;
//    private GuiPositiveFloatTextField flightSpeedTextBox;
//    private GuiPositiveFloatTextField groundSpeedTextBox;
//    private GuiToggle flightEnabled;
//    private GuiToggle feedEnabled;
//    private GuiToggle healEnabled;
//    private GuiToggle removeHarmfulEnabled;
//    private GuiToggle giveAirEnabled;
//    private GuiToggle extinguishEnabled;
//
//    @Override
//    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//        this.drawDefaultBackground();
//        super.drawScreen(mouseX, mouseY, partialTicks);
//
//        this.flightSpeedTextBox.drawTextBox();
//        this.groundSpeedTextBox.drawTextBox();
//    }
//
//    @Override
//    public boolean doesGuiPauseGame() {
//        return false;
//    }
//
//    @Override
//    public void initGui() {
//        IGenericDataStorage data = getHelmetDataStorage(Minecraft.getMinecraft().player);
//
//        if (data == null) {
//            this.mc.displayGuiScreen(null);
//            this.mc.player.sendStatusMessage(new StringTextComponent("Multi-Helmet not
// equipped."), true);
//            return;
//        }
//
//        Map<String, Float> floats = data.getFloatMap();
//        Map<String, Boolean> booleans = data.getBooleanMap();
//
//
//        int id = 0;
//        this.save = addButton(new GuiButton(id++, this.width / 2, this.height / 4 + 100, 150, 20,
// "Save"));
//        this.cancel = addButton(new GuiButton(id++, this.width / 2 - 150, this.height / 4 + 100,
// 150, 20, "Cancel"));
//
//        float flightSpeedValue = floats.getOrDefault(DataKeys.FLIGHT_SPEED, Default.FLIGHT_SPEED);
//        this.flightSpeed = addButton(new GuiSlider(id++, this.width / 2 - 150, this.height / 4, 0,
// OverloadedConfig.INSTANCE.multiArmorConfig.maxFlightSpeed, flightSpeedValue, "Flight Speed:"));
//        this.flightSpeedTextBox = new GuiPositiveFloatTextField(id++, this.fontRenderer,
// this.flightSpeed.x, this.flightSpeed.y, this.flightSpeed.width, this.flightSpeed.height,
// flightSpeedValue, 0, OverloadedConfig.INSTANCE.multiArmorConfig.maxFlightSpeed);
//        this.flightSpeedTextBox.setVisible(false);
//
//        float groundSpeedValue = floats.getOrDefault(DataKeys.GROUND_SPEED, Default.GROUND_SPEED);
//        this.groundSpeed = addButton(new GuiSlider(id++, this.width / 2, this.height / 4, 0,
// OverloadedConfig.INSTANCE.multiArmorConfig.maxGroundSpeed, groundSpeedValue, "Ground Speed:"));
//        this.groundSpeedTextBox = new GuiPositiveFloatTextField(id++, this.fontRenderer,
// this.groundSpeed.x, this.groundSpeed.y, this.groundSpeed.width, this.groundSpeed.height,
// groundSpeedValue, 0, OverloadedConfig.INSTANCE.multiArmorConfig.maxGroundSpeed);
//        this.groundSpeedTextBox.setVisible(false);
//
//        this.flightEnabled = addButton(new GuiToggle(id++, this.width / 2 - 150, this.height / 4 +
// 20, booleans.getOrDefault(DataKeys.FLIGHT, Default.FLIGHT), "Flight:"));
//        this.feedEnabled = addButton(new GuiToggle(id++, this.width / 2, this.height / 4 + 20,
// booleans.getOrDefault(DataKeys.FEED, Default.FEED), "Feeder:"));
//        this.healEnabled = addButton(new GuiToggle(id++, this.width / 2 - 150, this.height / 4 +
// 40, booleans.getOrDefault(DataKeys.HEAL, Default.HEAL), "Healer:"));
//        this.removeHarmfulEnabled = addButton(new GuiToggle(id++, this.width / 2, this.height / 4
// + 40, booleans.getOrDefault(DataKeys.REMOVE_HARMFUL, Default.REMOVE_HARMFUL), "Remove Harmful
// Potions:"));
//        this.giveAirEnabled = addButton(new GuiToggle(id++, this.width / 2 - 150, this.height / 4
// + 60, booleans.getOrDefault(DataKeys.GIVE_AIR, Default.GIVE_AIR), "Airer:"));
//        this.extinguishEnabled = addButton(new GuiToggle(id++, this.width / 2, this.height / 4 +
// 60, booleans.getOrDefault(DataKeys.EXTINGUISH, Default.EXTINGUISH), "Extinguisher:"));
//        this.noClipFlightLock = addButton(new GuiToggle(id++, this.width / 2 - 75, this.height / 4
// + 80, booleans.getOrDefault(DataKeys.NOCLIP_FLIGHT_LOCK, Default.NOCLIP_FLIGHT_LOCK), "No Clip
// Flight Lock:"));
//    }
//
//    @Nullable
//    private static IGenericDataStorage getHelmetDataStorage(PlayerEntity player) {
//        for (ItemStack stack : player.getArmorInventoryList()) {
//            if (stack.getItem() instanceof ItemMultiHelmet) {
//                IGenericDataStorage cap = stack.getCapability(GENERIC_DATA_STORAGE, null);
//                cap.suggestUpdate();
//                return cap;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    protected void actionPerformed(GuiButton button) {
//        if (this.save == button) {
//            IMessage message = new MultiArmorSettingsMessage(flightSpeed.getSliderValue(),
// groundSpeed.getSliderValue(), noClipFlightLock.getBooleanState(),
// this.flightEnabled.getBooleanState(), this.feedEnabled.getBooleanState(),
// this.healEnabled.getBooleanState(), this.removeHarmfulEnabled.getBooleanState(),
// this.giveAirEnabled.getBooleanState(), this.extinguishEnabled.getBooleanState());
//            Overloaded.proxy.networkWrapper.sendToServer(message);
//            this.mc.displayGuiScreen(null);
//        } else if (this.cancel == button) {
//            this.mc.displayGuiScreen(null);
//        } else if (button instanceof GuiToggle) {
//            ((GuiToggle) button).toggle();
//        }
//    }
//
//    @Override
//    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
//        this.flightSpeedTextBox.mouseClicked(mouseX, mouseY, mouseButton);
//        this.groundSpeedTextBox.mouseClicked(mouseX, mouseY, mouseButton);
//
//        switch (mouseButton) {
//            case 0: // Left Click
//                super.mouseClicked(mouseX, mouseY, mouseButton);
//                break;
//            case 1: // Right Click
//                if (this.flightSpeed.visible) {
//                    this.flightSpeedTextBox.setVisible(true);
//                    this.flightSpeed.visible = false;
//
//
// this.flightSpeedTextBox.setText(Float.toString(this.flightSpeed.getSliderValue()));
//
//                    this.groundSpeedTextBox.setVisible(true);
//                    this.groundSpeed.visible = false;
//
//
// this.groundSpeedTextBox.setText(Float.toString(this.groundSpeed.getSliderValue()));
//                } else {
//                    this.flightSpeedTextBox.setVisible(false);
//                    this.flightSpeed.visible = true;
//
//                    this.flightSpeed.setSliderValue(this.flightSpeedTextBox.getFloatValue());
//
//                    this.groundSpeedTextBox.setVisible(false);
//                    this.groundSpeed.visible = true;
//
//                    this.groundSpeed.setSliderValue(this.groundSpeedTextBox.getFloatValue());
//                }
//                break;
//            case 2: // Middle Click
//                break;
//        }
//    }
//
//    @Override
//    protected void keyTyped(char typedChar, int keyCode) throws IOException {
//        if (this.flightSpeedTextBox.textboxKeyTyped(typedChar, keyCode)) {
//            this.flightSpeed.setSliderValue(this.flightSpeedTextBox.getFloatValue());
//        }
//
//        if (this.groundSpeedTextBox.textboxKeyTyped(typedChar, keyCode)) {
//            this.groundSpeed.setSliderValue(this.groundSpeedTextBox.getFloatValue());
//        }
//
//        super.keyTyped(typedChar, keyCode);
//    }
// }
