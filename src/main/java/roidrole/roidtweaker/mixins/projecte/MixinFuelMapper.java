package roidrole.roidtweaker.mixins.projecte;

import moze_intel.projecte.emc.EMCMapper;
import moze_intel.projecte.emc.FuelMapper;
import moze_intel.projecte.emc.SimpleStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import roidrole.roidtweaker.mods.projecte.FuelManager;

import java.util.Comparator;
import java.util.List;

@Mixin(FuelMapper.class)
public abstract class MixinFuelMapper {
    @Shadow(remap = false)
    private static void addToMap(ItemStack stack) {}

    @Final
    @Shadow(remap = false)
    private static List<SimpleStack> FUEL_MAP;

    //Start
    @Inject(
        method = "loadMap",
        at = @At(
            value= "INVOKE",
            target = "Lmoze_intel/projecte/emc/FuelMapper;addToMap(Lnet/minecraft/item/ItemStack;)V",
            ordinal = 0
        ),
        remap = false,
        cancellable = true
    )
    private static void cti_beforeAdd(CallbackInfo ci){
        FuelManager.customFuels.forEach(MixinFuelMapper::addToMap);
        if(FuelManager.removeDefaults){
            FUEL_MAP.sort(Comparator.comparing(EMCMapper::getEmcValue));
            ci.cancel();
        }
    }

    //End
    @Inject(
            method = "loadMap",
            at = @At(
                    value= "INVOKE",
                    target = "Ljava/util/List;sort(Ljava/util/Comparator;)V",
                    ordinal = 0
            ),
            remap = false
    )
    private static void cti_afterAdd(CallbackInfo ci){
        FUEL_MAP.removeAll(FuelManager.fuelsToRemove);
    }
}
