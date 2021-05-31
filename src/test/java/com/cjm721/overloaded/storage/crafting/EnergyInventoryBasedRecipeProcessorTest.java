package com.cjm721.overloaded.storage.crafting;

import net.minecraft.item.crafting.IRecipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.common.truth.Truth.assertThat;

@RunWith(JUnit4.class)
public class EnergyInventoryBasedRecipeProcessorTest {

  private static final int MAX_SLOTS = 9;
  private static final int VANILLA_MAX_SLOT_MAGIC = 64;

  private EnergyInventoryBasedRecipeProcessor processor;

  @Before
  public void setup() {
    processor = new EnergyInventoryBasedRecipeProcessor(null,null,0,MAX_SLOTS, () -> {}) {
      @Override
      int energyCostPerRecipeOperation(IRecipe recipe) {
        return 0;
      }
    };
  }

  @Test
  public void getSlotLimit_thoughAllSlots() {
    for(int i = 0; i < processor.getSlots(); i++) {
      assertThat(processor.getSlotLimit(i)).isEqualTo(VANILLA_MAX_SLOT_MAGIC);
    }
  }

  @Test
  public void getSlotLimit_aboveSlots() {
    assertThat(processor.getSlotLimit(processor.getSlots())).isEqualTo(VANILLA_MAX_SLOT_MAGIC);
  }
}