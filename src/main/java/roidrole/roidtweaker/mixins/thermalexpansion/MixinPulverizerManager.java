package roidrole.roidtweaker.mixins.thermalexpansion;

import cofh.thermalexpansion.util.managers.machine.PulverizerManager;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import roidrole.roidtweaker.mods.thermalexpansion.Pulverizer;
import roidrole.roidtweaker.mods.thermalexpansion.RedstoneFurnace;

@Mixin(PulverizerManager.class)
public abstract class MixinPulverizerManager {
    @Inject(
        method = "isOre",
        at= @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void roidTweaker_oreOverride(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        Boolean override = Pulverizer.oreOverrides.getOverride(stack);
        if(override != null){
            cir.setReturnValue(override);
        }
    }
}
