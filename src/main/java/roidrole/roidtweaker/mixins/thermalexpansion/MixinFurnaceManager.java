package roidrole.roidtweaker.mixins.thermalexpansion;

import cofh.thermalexpansion.util.managers.machine.FurnaceManager;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import roidrole.roidtweaker.mods.thermalexpansion.RedstoneFurnace;

@Mixin(FurnaceManager.class)
public abstract class MixinFurnaceManager {
    @Inject(
        method = "isFood",
        at= @At(
            value = "INVOKE",
            target = "Lcofh/thermalexpansion/util/managers/machine/FurnaceManager;convertInput(Lnet/minecraft/item/ItemStack;)Lcofh/core/inventory/ComparableItemStackValidated;"
        ),
        cancellable = true,
        remap = false
    )
    private static void roidTweaker_foodOverride(ItemStack input, CallbackInfoReturnable<Boolean> cir){
        if(RedstoneFurnace.foodOverrideMap.isEmpty()){return;}
        IItemStack iinput = CraftTweakerMC.getIItemStack(input);
        RedstoneFurnace.foodOverrideMap.entrySet().stream()
            .filter(entry -> entry.getKey().matches(iinput))
            .findFirst()
            .ifPresent(entry -> cir.setReturnValue(entry.getValue()));
    }


    @Inject(
        method = "isOre",
        at= @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void roidTweaker_oreOverride(ItemStack input, CallbackInfoReturnable<Boolean> cir){
        if(RedstoneFurnace.oreOverrideMap.isEmpty()){return;}
        IItemStack iinput = CraftTweakerMC.getIItemStack(input);
        RedstoneFurnace.oreOverrideMap.entrySet().stream()
            .filter(entry -> entry.getKey().matches(iinput))
            .findFirst()
            .ifPresent(entry -> cir.setReturnValue(entry.getValue()));
    }
}
