package roidrole.roidtweaker.mixins.baubles;

import baubles.api.cap.InjectableBauble;
import baubles.common.integration.ModCompatibility;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import roidrole.roidtweaker.mods.baubles.CTBauble;

@Mixin(ModCompatibility.class)
public abstract class MixinModCompatibility {
    @Inject(
        method = "getBaubleToInject(Lnet/minecraft/item/ItemStack;)Lbaubles/api/cap/InjectableBauble;",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void roidTweaker_injectCustomBaubles(ItemStack stack, CallbackInfoReturnable<InjectableBauble> cir){
        InjectableBauble bauble = CTBauble.additionalBaubles.get(stack.getItem());
        if(bauble != null){
            cir.setReturnValue(bauble);
        }
    }
}
