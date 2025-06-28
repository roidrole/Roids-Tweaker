package roidrole.roidtweaker.mixins.immersiveengineering;

import blusunrize.immersiveengineering.api.crafting.ArcFurnaceRecipe;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ArcFurnaceRecipe.class)
public interface IArcFurnaceRecipeAccessor {
    @Accessor(value = "slag", remap = false)
    void setSlag(ItemStack slag);
}
