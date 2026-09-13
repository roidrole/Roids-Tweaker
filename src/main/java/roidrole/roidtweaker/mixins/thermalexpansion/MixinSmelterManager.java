package roidrole.roidtweaker.mixins.thermalexpansion;

import cofh.thermalexpansion.util.managers.machine.SmelterManager;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import roidrole.roidtweaker.mods.thermalexpansion.InductionSmelter;

@Mixin(SmelterManager.class)
public abstract class MixinSmelterManager {
    @Inject(
        method = "isOre",
        at= @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void roidTweaker_oreOverride(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        Boolean override = InductionSmelter.oreOverrides.getOverride(stack);
        if(override != null){
            cir.setReturnValue(override);
        }
    }
}
