package roidrole.roidtweaker.mixins.thebetweenlands;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.throwables.MixinException;
import thebetweenlands.api.recipes.IPurifierRecipe;
import thebetweenlands.common.recipe.purifier.PurifierRecipe;

import java.util.List;

@Mixin(PurifierRecipe.class)
public interface AccPurifierRecipe {
	@Accessor(remap = false)
	static List<IPurifierRecipe> getRECIPES(){
		throw new MixinException("Mixin "+ AccPurifierRecipe.class.getCanonicalName() + " didn't apply");
	}
}
