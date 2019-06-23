package com.cjm721.overloaded.storage.energy;

import com.cjm721.overloaded.storage.LongEnergyStack;
import net.minecraft.nbt.CompoundNBT;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.common.truth.Truth.assertThat;

@RunWith(JUnit4.class)
public class LongEnergyStorageTest {

    private LongEnergyStorage storage;
    private boolean updateCalled;

    @Before
    public void setup() {
        storage = new LongEnergyStorage(() -> updateCalled = true);
        updateCalled = false;
    }

    @Test
    public void give_doAction_whenFalse() {
        LongEnergyStack simulatedOverflow = storage.give(new LongEnergyStack(1000), false);

        assertThat(simulatedOverflow.getAmount()).isEqualTo(0);
        assertThat(storage.status().getAmount()).isEqualTo(0);
        assertThat(updateCalled).isFalse();
    }

    @Test
    public void take_doAction_whenFalse() {
        // Setup Base energy
        storage.give(new LongEnergyStack(1000), true);
        updateCalled = false;

        LongEnergyStack simulatedTakenAmount = storage.take(new LongEnergyStack(1000), false);

        assertThat(simulatedTakenAmount.getAmount()).isEqualTo(1000);
        assertThat(storage.status().getAmount()).isEqualTo(1000);
        assertThat(updateCalled).isFalse();
    }

    @Test
    public void receiveEnergy_simulate_whenTrue() {
        int simulatedReceive = storage.receiveEnergy(1000, true);

        assertThat(simulatedReceive).isEqualTo(1000);
        assertThat(storage.status().getAmount()).isEqualTo(0);
        assertThat(updateCalled).isFalse();
    }

    @Test
    public void extractEnergy_simulate_whenTrue() {
        // Setup Base energy
        storage.give(new LongEnergyStack(1000), true);
        updateCalled = false;

        int simulatedExtract = storage.extractEnergy(1000, true);

        assertThat(simulatedExtract).isEqualTo(1000);
        assertThat(storage.status().getAmount()).isEqualTo(1000);
        assertThat(updateCalled).isFalse();
    }

    @Test
    public void give_moreThenAvailable() {
        // Setup Base Energy
        storage.give(new LongEnergyStack(Long.MAX_VALUE), true);
        updateCalled = false;

        LongEnergyStack overflow = storage.give(new LongEnergyStack(1000), true);

        assertThat(overflow.getAmount()).isEqualTo(1000);
        assertThat(storage.status().getAmount()).isEqualTo(Long.MAX_VALUE);
        assertThat(updateCalled).isTrue();
    }

    @Test
    public void take_moreThenAvailable() {
        // Setup Base energy
        storage.give(new LongEnergyStack(100), true);
        updateCalled = false;

        LongEnergyStack taken = storage.take(new LongEnergyStack(1000), true);

        assertThat(taken.getAmount()).isEqualTo(100);
        assertThat(storage.status().getAmount()).isEqualTo(0);
        assertThat(updateCalled).isTrue();
    }

    @Test
    public void receiveEnergy_simulate_whenFalse() {
        int simulatedReceive = storage.receiveEnergy(1000, false);

        assertThat(simulatedReceive).isEqualTo(1000);
        assertThat(storage.status().getAmount()).isEqualTo(1000);
        assertThat(updateCalled).isTrue();
    }

    @Test
    public void extractEnergy_simulate_whenFalse() {
        // Setup Base energy
        storage.give(new LongEnergyStack(1000), true);
        updateCalled = false;

        int simulatedExtract = storage.extractEnergy(1000, false);

        assertThat(simulatedExtract).isEqualTo(1000);
        assertThat(storage.status().getAmount()).isEqualTo(0);
        assertThat(updateCalled).isTrue();
    }

    @Test
    public void deserializeNBT_normalValues() {
        CompoundNBT tag = new CompoundNBT();
        tag.putLong("Count",  1000L);

        storage.deserializeNBT(tag);

        assertThat(storage.status().getAmount()).isEqualTo(1000);
    }

    @Test
    public void serializeNBT_normalValues() {
        CompoundNBT tag = new CompoundNBT();
        tag.putLong("Count",  1000L);
        storage.deserializeNBT(tag);

        CompoundNBT serializedTag = storage.serializeNBT();

        assertThat(serializedTag).isEqualTo(tag);
    }
}
