package roidrole.roidtweaker.mixins.erebus;

import erebus.recipes.OfferingAltarRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.throwables.MixinException;

import java.util.List;

@Mixin(OfferingAltarRecipe.class)
public interface OfferingAltarRecipeAccessor {
	@Accessor
	static List<OfferingAltarRecipe> getList(){
		throw new MixinException("Mixin "+ OfferingAltarRecipeAccessor.class.getCanonicalName() + " didn't apply");
	}
}
