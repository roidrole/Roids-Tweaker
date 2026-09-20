package roidrole.roidtweaker.mixins.thebetweenlands;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.throwables.MixinException;
import thebetweenlands.api.recipes.IPestleAndMortarRecipe;
import thebetweenlands.common.recipe.mortar.PestleAndMortarRecipe;

import java.util.List;

@Mixin(PestleAndMortarRecipe.class)
public interface AccPestleAndMortarRecipe {
	@Accessor(remap = false)
	static List<IPestleAndMortarRecipe> getRecipes(){
		throw new MixinException("Mixin "+ AccPestleAndMortarRecipe.class.getCanonicalName() + " didn't apply");
	}
}
