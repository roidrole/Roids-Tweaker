package roidrole.roidtweaker.mixins.erebus;

import erebus.recipes.OfferingAltarRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.throwables.MixinException;

import java.util.List;

@Mixin(OfferingAltarRecipe.class)
public interface AccOfferingAltarRecipe {
	@Accessor(remap = false)
	static List<OfferingAltarRecipe> getList(){
		throw new MixinException("Mixin "+ AccOfferingAltarRecipe.class.getCanonicalName() + " didn't apply");
	}
}
