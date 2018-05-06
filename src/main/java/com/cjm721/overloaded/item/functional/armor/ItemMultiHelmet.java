package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.client.render.entity.RenderMultiHelmet;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.network.packets.MultiArmorSettingsMessage;
import com.cjm721.overloaded.storage.GenericDataCapabilityProvider;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.Collection;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.storage.GenericDataStorage.GENERIC_DATA_STORAGE;

public class ItemMultiHelmet extends AbstractMultiArmor {

    private RenderMultiHelmet armorModel;

    public ItemMultiHelmet() {
        super(0, EntityEquipmentSlot.HEAD);

        setRegistryName("multi_helmet");
        setUnlocalizedName("multi_helmet");

        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        if (armorModel == null)
            armorModel = new RenderMultiHelmet();
        return armorModel;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
        ModelLoader.setCustomModelResourceLocation(this, 0, location);

        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID, "textures/armors/multi_helmet.png"),
                new ResourceLocation(MODID, "textures/dynamic/armors/multi_helmet.png"),
                OverloadedConfig.textureResolutions.multiArmorResolution));
    }

    public void updateSettings(EntityPlayerMP entityPlayerMP, MultiArmorSettingsMessage message) {
        for(ItemStack itemStack: entityPlayerMP.getArmorInventoryList()) {
            if(itemStack.getItem() == this) {
                updateSettings(itemStack,message);
            }
        }
    }

    @Override
    public Collection<ICapabilityProvider> collectCapabilities(@Nonnull Collection<ICapabilityProvider> collection, ItemStack stack, @Nullable NBTTagCompound nbt) {
        collection.add(new GenericDataCapabilityProvider());
        return super.collectCapabilities(collection, stack, nbt);
    }

    private void updateSettings(ItemStack itemStack, MultiArmorSettingsMessage message) {
        itemStack.getCapability(GENERIC_DATA_STORAGE,null);
        System.out.println("Update Settings");
    }
}
