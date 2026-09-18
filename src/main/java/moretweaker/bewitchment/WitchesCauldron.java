package moretweaker.bewitchment;

import com.bewitchment.api.registry.Brew;
import com.bewitchment.api.registry.CauldronRecipe;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListAdd;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import roidrole.roidtweaker.utils.predicates.PredicateAnyOfArray;
import roidrole.roidtweaker.utils.predicates.PredicateAnyOfIterable;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.function.Predicate;

@ZenClass("moretweaker.bewitchment.WitchesCauldron")
@ZenRegister
@ModOnly("bewitchment")
@SuppressWarnings("unused")
public class WitchesCauldron {
	@ZenMethod
	public static void addRecipe(IItemStack[] outputs, IIngredient[] inputs) {
		if (outputs.length > 3) {
			CraftTweakerAPI.logError("Recipes for the witches cauldron may not have more than 3 outputs.");
			return;
		}

		if (inputs.length > 10) {
			CraftTweakerAPI.logError("Recipes for the witches cauldron may not have more than 10 inputs.");
			return;
		}

		final CauldronRecipe recipe = new CauldronRecipe(
			MoreTweaker.newRL(),
			Arrays.asList(Utils.getIngredients(inputs)),
			Arrays.asList(CraftTweakerMC.getItemStacks(outputs))
		);
		DeferredLoader.postInit(ListAdd.from(CauldronRecipe.class, recipe));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		final Predicate<Iterable<ItemStack>> outputFilter = new PredicateAnyOfIterable<>(Utils.getStackMatcher(output));
		DeferredLoader.postInit(
			ListRemove.from(CauldronRecipe.class, recipe -> outputFilter.test(recipe.output))
		);
	}

	@ZenMethod
	public static void removeAllRecipes() {
		DeferredLoader.postInit(ListClear.from(CauldronRecipe.class, "witches cauldron"));
	}

	@ZenMethod
	public static void addBrew(IIngredient trigger, final String potion, @Optional(valueLong = 600L) final int duration, @Optional final int amplifier) {
		final Ingredient in = CraftTweakerMC.getIngredient(trigger);
		DeferredLoader.postInit(() -> {
			Potion p = Potion.getPotionFromResourceLocation(potion);
			if (p == null) {
				CraftTweakerAPI.logError("Could not find potion while trying to add a brew to the witches cauldron: " + potion);
			} else {
				MoreTweaker.forgeAdd(Brew.class, new Brew(MoreTweaker.newRL(), in, new PotionEffect(p, duration, amplifier)));
			}

		});
	}

	@ZenMethod
	public static void removeBrew(IIngredient trigger) {
		final Predicate<ItemStack[]> outputFilter = new PredicateAnyOfArray<>(Utils.getStackMatcher(trigger));
		DeferredLoader.postInit(ListRemove.from(Brew.class,
			recipe -> outputFilter.test(recipe.input.getMatchingStacks())
		));
	}

	@ZenMethod
	public static void removeAllBrews() {
		DeferredLoader.postInit(ListClear.from(Brew.class, "witches cauldron brew"));
	}
}
