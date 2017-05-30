package com.cjm721.overloaded.client.render.entity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.cjm721.overloaded.Overloaded.MODID;

@SideOnly(Side.CLIENT)
public class ArmorSecondarySpritesRegister {
    @SubscribeEvent
    public void texturePre(TextureStitchEvent.Pre event) {
        event.getMap().registerSprite(new ResourceLocation(MODID, "armors/multi_belt"));
        event.getMap().registerSprite(new ResourceLocation(MODID, "armors/multi_left_arm"));
        event.getMap().registerSprite(new ResourceLocation(MODID, "armors/multi_right_arm"));
    }
}
