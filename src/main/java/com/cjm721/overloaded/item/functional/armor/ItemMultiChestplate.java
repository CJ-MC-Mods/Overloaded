package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.client.render.entity.RenderMultiChestplate;
import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.util.IModRegistrable;
import com.cjm721.overloaded.util.itemwrapper.IntEnergyWrapper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ItemMultiChestplate extends AbstractMultiArmor {

    private RenderMultiChestplate armorModel;

    public ItemMultiChestplate() {
        super( 0, EntityEquipmentSlot.CHEST);

        setRegistryName("multi_chestplate");
        setUnlocalizedName("multi_chestplate");

        Overloaded.proxy.itemToRegister.add(this);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        if(armorModel == null)
            armorModel = new RenderMultiChestplate();
        return armorModel;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(this, 0, location);

        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID,"textures/armors/multi_body.png"),
                new ResourceLocation(MODID,"textures/dynamic/armors/multi_body.png"),
                OverloadedConfig.textureResolutions.multiArmorResolution));
        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID,"textures/armors/multi_left_arm.png"),
                new ResourceLocation(MODID,"textures/dynamic/armors/multi_left_arm.png"),
                OverloadedConfig.textureResolutions.multiArmorResolution));
        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID,"textures/armors/multi_right_arm.png"),
                new ResourceLocation(MODID,"textures/dynamic/armors/multi_right_arm.png"),
                OverloadedConfig.textureResolutions.multiArmorResolution));
    }
}
