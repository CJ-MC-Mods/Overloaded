package com.cjm721.overloaded.client.gui.button;

import com.google.common.primitives.Floats;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class GuiPositiveFloatTextField extends GuiTextField {

    private final float min;
    private final float max;

    public GuiPositiveFloatTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int width, int height, float currentValue, float min, float max) {
        super(componentId, fontrendererObj, x, y, width, height);
        this.min = min;
        this.max = max;
        this.setValidator(this::floatValidate);
        this.setText(Float.toString(currentValue));
    }

    private boolean floatValidate(String text) {
        String validChars = "0123456789.-Ee";


        for(char c: text.toCharArray()) {
            if(!validChars.contains(c + "")) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setFocused(boolean isFocusedIn) {
        super.setFocused(isFocusedIn);

        if(!isFocusedIn) {
            this.setText(Float.toString(getFloatValue()));
        }
    }

    public float getFloatValue() {
        String text = this.getText();
        if(text.isEmpty()) {
            return min;
        }

        try {
            float value;
            if(text.endsWith(".")) {
                value = Float.parseFloat(text.substring(0,text.length()-1));
            } else {
                value = Float.parseFloat(text);
            }

            if(value < min) {
                value = min;
            }

            if(value > max) {
                value = max;
            }

            return value;
        } catch (NumberFormatException ignored) {
            return min;
        }
    }


}
