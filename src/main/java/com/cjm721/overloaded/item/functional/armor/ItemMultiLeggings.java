package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.client.render.entity.RenderMultiLeggings;
import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.util.IModRegistrable;
import com.cjm721.overloaded.util.itemwrapper.IntEnergyWrapper;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ItemMultiLeggings extends AbstractMultiArmor {

    private RenderMultiLeggings armorModel;

    public ItemMultiLeggings() {
        super(0, EntityEquipmentSlot.LEGS);

        setRegistryName("multi_leggings");
        setUnlocalizedName("multi_leggings");

        GameRegistry.register(this);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        if(armorModel == null)
            armorModel = new RenderMultiLeggings();

        return armorModel;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
        ModelLoader.setCustomModelResourceLocation(this, 0, location);

        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID,"textures/armors/multi_leg.png"),
                new ResourceLocation(MODID,"textures/dynamic/armors/multi_leg.png"),
                OverloadedConfig.textureResolutions.multiArmorResolution));
        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID,"textures/armors/multi_belt.png"),
                new ResourceLocation(MODID,"textures/dynamic/armors/multi_belt.png"),
                OverloadedConfig.textureResolutions.multiArmorResolution));
    }

    @Override
    public void registerRecipe() {
        if(OverloadedConfig.recipeEnabledConfig.customLeggings) {
            GameRegistry.addRecipe(new ItemStack(this), "NEN", "N N", "N N", 'N', ModBlocks.netherStarBlock, 'E', ModItems.energyCore);
        }
    }
}
