package moretweaker.bloodarsenal;

import arcaratus.bloodarsenal.recipe.RecipeSanguineInfusion;
import arcaratus.bloodarsenal.recipe.SanguineInfusionRecipeRegistry;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import org.apache.commons.lang3.tuple.Pair;
import roidrole.roidtweaker.mixins.bloodarsenal.ARecipeSanguineInfusion;
import roidrole.roidtweaker.mixins.bloodarsenal.ASanguineInfusionRecipeRegistry;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListAdd;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

import java.util.List;
import java.util.function.Predicate;

@ZenClass("moretweaker.bloodarsenal.Sanguine")
@ZenRegister
@ModOnly("bloodarsenal")
@SuppressWarnings("unused")
public class Sanguine {
	@ZenProperty
	public static final String BAD_POTION = "bad_potion";
	@ZenProperty
	public static final String BLOOD_LUST = "bloodlust";
	@ZenProperty
	public static final String FLAME = "flame";
	@ZenProperty
	public static final String SHARPNESS = "sharpness";
	@ZenProperty
	public static final String FORTUNATE = "fortunate";
	@ZenProperty
	public static final String LOOTING = "looting";
	@ZenProperty
	public static final String SILKY = "silky";
	@ZenProperty
	public static final String SMELTING = "smelting";
	@ZenProperty
	public static final String EXPERIENCED = "xperienced";
	@ZenProperty
	public static final String BENEFICIAL_POTION = "beneficial_potion";
	@ZenProperty
	public static final String QUICK_DRAW = "quick_draw";
	@ZenProperty
	public static final String SHADOW_TOOL = "shadow_tool";
	@ZenProperty
	public static final String AOD = "aod";
	@ZenProperty
	public static final String SIGIL = "sigil";

	private Sanguine() {
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, final int lpCost, IItemStack catalyst, IIngredient[] inputs) {
		if (inputs.length > 8) {
			CraftTweakerAPI.logError("Sanguine Recipes: 8 Inputs maximum, provided " + inputs.length);
			return;
		}
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final ItemStack c = CraftTweakerMC.getItemStack(catalyst);

		final RecipeSanguineInfusion recipe = new RecipeSanguineInfusion(out, lpCost, c);
		List<Pair<Ingredient, Integer>> in = ((ARecipeSanguineInfusion)recipe).getInputs();
		//Loads the ingredients directly into in. Zero-copy, keeping final fields final.
		Sanguine.getBAIngredients(inputs, in);

		DeferredLoader.postInit(ListAdd.from(ASanguineInfusionRecipeRegistry.getInfusionRecipes(), recipe));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		final Predicate<ItemStack> outputFilter = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.from(
			SanguineInfusionRecipeRegistry.getInfusionRecipes(),
			outputFilter,
			RecipeSanguineInfusion::getOutput
		));
	}

	@ZenMethod
	public static void addModifier(final String modifier, final int lpCost, IIngredient[] inputs) {
		if (inputs.length > 8) {
			CraftTweakerAPI.logError("Sanguine Recipes: 8 Inputs maximum, provided " + inputs.length);
			return;
		}
		RecipeSanguineInfusion recipe = new RecipeSanguineInfusion(lpCost, modifier).setLevelMultiplier(1);
		Sanguine.getBAIngredients(inputs, ((ARecipeSanguineInfusion)recipe).getInputs());

		DeferredLoader.postInit(ListAdd.from(ASanguineInfusionRecipeRegistry.getInfusionRecipes(), recipe));
	}

	@ZenMethod
	public static void removeModifier(final String modifier) {
		Predicate<String> modifierFilter = Predicate.isEqual(modifier);
		DeferredLoader.postInit(ListRemove.from(
			SanguineInfusionRecipeRegistry.getInfusionRecipes(),
			modifierFilter,
			RecipeSanguineInfusion::getModifierKey
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(SanguineInfusionRecipeRegistry.getInfusionRecipes(), "Sanguine"));
	}

	//Out is assumed to be empty
	public static void getBAIngredients(IIngredient[] ingredients, List<Pair<Ingredient, Integer>> out){
		for (IIngredient ingredient : ingredients) {
			out.add(Pair.of(CraftTweakerMC.getIngredient(ingredient), ingredient.getAmount()));
		}
	}
}
