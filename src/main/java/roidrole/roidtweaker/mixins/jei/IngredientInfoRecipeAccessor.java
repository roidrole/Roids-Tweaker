package roidrole.roidtweaker.mixins.jei;

import mezz.jei.api.recipe.IIngredientType;
import mezz.jei.plugins.jei.info.IngredientInfoRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(IngredientInfoRecipe.class)
public interface IngredientInfoRecipeAccessor<T> {
	@Accessor(remap = false)
	List<T> getIngredients();

	@Accessor(remap = false)
	IIngredientType<T> getIngredientType();
}
