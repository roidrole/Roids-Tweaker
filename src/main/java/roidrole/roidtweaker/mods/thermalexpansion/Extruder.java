package roidrole.roidtweaker.mods.thermalexpansion;

import cofh.core.util.ItemWrapper;
import cofh.thermalexpansion.util.managers.machine.ExtruderManager;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import roidrole.roidtweaker.mixins.thermalexpansion.IExtruderManagerAccessor;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.EnumLoadStage;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("thermalexpansion")
@ZenRegister
@ZenClass("mods.thermalexpansion.Extruder")
@SuppressWarnings("unused")
public class Extruder {
    @ZenMethod
    public static void addRecipeIgneous(IItemStack output, int lava, int water, int energy){
        ExtruderManager.addRecipeIgneous(energy, CraftTweakerMC.getItemStack(output), new FluidStack(FluidRegistry.LAVA, lava), new FluidStack(FluidRegistry.WATER, water));
    }
    @ZenMethod
    public static void addRecipeSedimentary(IItemStack output, int lava, int water, int energy){
        ExtruderManager.addRecipeSedimentary(energy, CraftTweakerMC.getItemStack(output), new FluidStack(FluidRegistry.LAVA, lava), new FluidStack(FluidRegistry.WATER, water));
    }
    @ZenMethod
    public static void removeRecipeIgneous(IItemStack output){
        DeferredLoader.load(EnumLoadStage.LOAD_COMPLETE, () -> IExtruderManagerAccessor.getRecipeMapIgneous().remove(new ItemWrapper(CraftTweakerMC.getItemStack(output))));
    }
    @ZenMethod
    public static void removeRecipeSedimentary(IItemStack output){
        DeferredLoader.load(EnumLoadStage.LOAD_COMPLETE, () -> IExtruderManagerAccessor.getRecipeMapSedimentary().remove(new ItemWrapper(CraftTweakerMC.getItemStack(output))));
    }
}
