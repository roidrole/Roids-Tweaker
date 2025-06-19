package roidrole.roidtweaker.mixins.forge.villager;

import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipeList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MerchantRecipeList.class)
public class MerchantRecipeListMixin {
    @Inject(
        method = "areItemStacksExactlyEqual(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z",
        at = @At("HEAD"),
        cancellable = true
    )
    void roidTweaker_compareDamage(ItemStack invStack, ItemStack recipeStack, CallbackInfoReturnable<Boolean> cir){
        if(recipeStack.getItemDamage() == 32767){cir.setReturnValue(recipeStack.getItem().equals(invStack.getItem()));}
    }
}
