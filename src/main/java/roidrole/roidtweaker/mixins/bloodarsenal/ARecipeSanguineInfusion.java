package roidrole.roidtweaker.mixins.bloodarsenal;

import arcaratus.bloodarsenal.recipe.RecipeSanguineInfusion;
import net.minecraft.item.crafting.Ingredient;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(RecipeSanguineInfusion.class)
public interface ARecipeSanguineInfusion {
	@Accessor
	List<Pair<Ingredient, Integer>> getInputs();
}
