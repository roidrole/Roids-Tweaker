package roidrole.roidtweaker.mixins.erebus;

import erebus.recipes.SmoothieMakerRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.throwables.MixinException;

import java.util.List;

@Mixin(SmoothieMakerRecipe.class)
public interface AccSmoothieMakerRecipe {
	@Accessor(remap = false)
	static List<SmoothieMakerRecipe> getRecipes(){
		throw new MixinException("Mixin "+ AccSmoothieMakerRecipe.class.getCanonicalName() + " didn't apply");
	}
}
