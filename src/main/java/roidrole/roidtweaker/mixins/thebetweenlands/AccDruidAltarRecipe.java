package roidrole.roidtweaker.mixins.thebetweenlands;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.throwables.MixinException;
import thebetweenlands.api.recipes.IDruidAltarRecipe;
import thebetweenlands.common.recipe.misc.DruidAltarRecipe;

import java.util.ArrayList;

@Mixin(DruidAltarRecipe.class)
public interface AccDruidAltarRecipe {
	@Accessor(remap = false)
	static ArrayList<IDruidAltarRecipe> getDruidAltarRecipes(){
		throw new MixinException("Mixin "+ AccDruidAltarRecipe.class.getCanonicalName() + " didn't apply");
	}
}
