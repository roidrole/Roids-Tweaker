package moretweaker.bewitchment;

import com.bewitchment.api.registry.DistilleryRecipe;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListAdd;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import roidrole.roidtweaker.utils.predicates.PredicateAnyOfIterable;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@ZenClass("moretweaker.bewitchment.Distillery")
@ZenRegister
@ModOnly("bewitchment")
@SuppressWarnings("unused")
public class Distillery {
	@ZenMethod
	public static void addRecipe(IItemStack[] outputs, IIngredient[] inputs) {
		if (outputs.length > 6) {
			CraftTweakerAPI.logError("Recipes for the distillery may not have more than 6 outputs.");
			return;
		}

		if (inputs.length > 6) {
			CraftTweakerAPI.logError("Recipes for the distillery may not have more than 6 inputs.");
			return;
		}
		final List<ItemStack> out = Arrays.asList(CraftTweakerMC.getItemStacks(outputs));
		final List<Ingredient> in = Arrays.asList(Utils.getIngredients(inputs));

		DeferredLoader.postInit(
			ListAdd.from(DistilleryRecipe.class, new DistilleryRecipe(MoreTweaker.newRL(), in, out))
		);
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		final Predicate<Iterable<ItemStack>> outputFilter = new PredicateAnyOfIterable<>(Utils.getStackMatcher(output));
		DeferredLoader.postInit(ListRemove.from(
			DistilleryRecipe.class,
			recipe -> outputFilter.test(recipe.output)
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(DistilleryRecipe.class, "witches distillery"));
	}
}
