package roidrole.roidtweaker.mixins.bloodarsenal;

import arcaratus.bloodarsenal.recipe.RecipeSanguineInfusion;
import arcaratus.bloodarsenal.recipe.SanguineInfusionRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.throwables.MixinException;

import java.util.List;

@Mixin(SanguineInfusionRecipeRegistry.class)
public interface AccSanguineInfusionRecipeRegistry {
	@Accessor(remap = false)
	static List<RecipeSanguineInfusion> getInfusionRecipes(){
		throw new MixinException("Mixin "+ AccSanguineInfusionRecipeRegistry.class.getCanonicalName() + " didn't apply");
	}
}
