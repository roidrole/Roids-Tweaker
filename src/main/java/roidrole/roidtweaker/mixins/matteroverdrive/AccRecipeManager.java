package roidrole.roidtweaker.mixins.matteroverdrive;

import matteroverdrive.data.recipes.Recipe;
import matteroverdrive.data.recipes.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(RecipeManager.class)
public interface AccRecipeManager<M, R extends Recipe<M>> {
	@Accessor(remap = false)
	List<R> getRecipes();
}
