package roidrole.roidtweaker.mixins.thermalexpansion;

import cofh.thermalexpansion.util.managers.machine.FurnaceManager;
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
        Boolean override = RedstoneFurnace.foodOverrides.getOverride(input);
        if(override != null){
            cir.setReturnValue(override);
        }
    }


    @Inject(
        method = "isOre",
        at= @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void roidTweaker_oreOverride(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        Boolean override = RedstoneFurnace.oreOverrides.getOverride(stack);
        if(override != null){
            cir.setReturnValue(override);
        }
    }
}
