package com.cjm721.ibhstd.common.block;

import com.cjm721.ibhstd.common.IBHSTDCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by CJ on 4/2/2017.
 */
public abstract class ModBlock extends Block {
    public ModBlock(Material materialIn) {
        super(materialIn);
    }

    protected void register() {
        GameRegistry.register(this);
        registerItemForm();
        setCreativeTab(IBHSTDCreativeTab.INSTANCE);
    }

    private void registerItemForm() {
        GameRegistry.register(new ItemBlock(this), getRegistryName());
    }
}
