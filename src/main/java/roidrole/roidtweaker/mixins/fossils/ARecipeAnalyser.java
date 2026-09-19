package roidrole.roidtweaker.mixins.fossils;

import fossilsarcheology.server.recipe.RecipeAnalyzer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RecipeAnalyzer.class)
public interface ARecipeAnalyser {
	@Accessor(remap = false)
	void setTotalWeight(float value);
}
