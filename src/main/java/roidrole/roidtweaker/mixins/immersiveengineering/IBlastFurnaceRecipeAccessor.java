package roidrole.roidtweaker.mixins.immersiveengineering;

import blusunrize.immersiveengineering.api.crafting.BlastFurnaceRecipe;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlastFurnaceRecipe.class)
public interface IBlastFurnaceRecipeAccessor {
    @Accessor(value = "slag", remap = false)
    void setSlag(ItemStack slag);
}
