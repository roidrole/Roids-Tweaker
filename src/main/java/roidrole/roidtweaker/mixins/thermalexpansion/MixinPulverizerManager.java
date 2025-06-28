package roidrole.roidtweaker.mixins.thermalexpansion;

import cofh.thermalexpansion.util.managers.machine.PulverizerManager;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import roidrole.roidtweaker.mods.thermalexpansion.Pulverizer;

@Mixin(PulverizerManager.class)
public abstract class MixinPulverizerManager {
    @Inject(
        method = "isOre",
        at= @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void roidTweaker_oreOverride(ItemStack input, CallbackInfoReturnable<Boolean> cir){
        if(Pulverizer.oreOverrideMap.isEmpty()){return;}
        IItemStack iinput = CraftTweakerMC.getIItemStack(input);
        Pulverizer.oreOverrideMap.entrySet().stream()
            .filter(entry -> entry.getKey().matches(iinput))
            .findFirst()
            .ifPresent(entry -> cir.setReturnValue(entry.getValue()));
    }
}
