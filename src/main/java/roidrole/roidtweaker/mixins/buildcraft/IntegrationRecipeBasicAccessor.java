package roidrole.roidtweaker.mixins.buildcraft;

import buildcraft.lib.recipe.IntegrationRecipeBasic;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(IntegrationRecipeBasic.class)
public interface IntegrationRecipeBasicAccessor {
	@Accessor(remap = false)
	ItemStack getOutput();
}
