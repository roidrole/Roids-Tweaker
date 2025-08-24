package xyz.tcreopargh.ctintegration.forge;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.ctintegration.IEnergyStorage")
@SuppressWarnings("unused")
public interface IEnergyStorage {

    Object getInternal();

    @ZenMethod
    int receiveEnergy(int maxReceive, @Optional boolean simulate);

    @ZenMethod
    int extractEnergy(int maxExtract, @Optional boolean simulate);

    @ZenMethod
    @ZenGetter("energyStored")
    int getEnergyStored();

    @ZenMethod
    @ZenGetter("maxEnergyStored")
    int getMaxEnergyStored();

    @ZenMethod
    @ZenGetter
    boolean canExtract();

    @ZenMethod
    @ZenGetter
    boolean canReceive();
}
