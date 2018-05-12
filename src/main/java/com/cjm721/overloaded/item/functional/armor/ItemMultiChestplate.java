package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.client.render.entity.RenderMultiChestplate;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ItemMultiChestplate extends AbstractMultiArmor {

    private RenderMultiChestplate armorModel;

    public ItemMultiChestplate() {
        super(0, EntityEquipmentSlot.CHEST);

        setRegistryName("multi_chestplate");
        setUnlocalizedName("multi_chestplate");

        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        if (armorModel == null)
            armorModel = new RenderMultiChestplate();
        return armorModel;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(this, 0, location);

        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID, "textures/armors/multi_body.png"),
                new ResourceLocation(MODID, "textures/dynamic/armors/multi_body.png"),
                Overloaded.cachedConfig.textureResolutions.multiArmorResolution));
        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID, "textures/armors/multi_left_arm.png"),
                new ResourceLocation(MODID, "textures/dynamic/armors/multi_left_arm.png"),
                Overloaded.cachedConfig.textureResolutions.multiArmorResolution));
        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID, "textures/armors/multi_right_arm.png"),
                new ResourceLocation(MODID, "textures/dynamic/armors/multi_right_arm.png"),
                Overloaded.cachedConfig.textureResolutions.multiArmorResolution));
    }
}
