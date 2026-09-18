package moretweaker.cfb;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.recipes.MCRecipeShaped;
import moretweaker.MoreTweaker;
import net.blay09.mods.cookingforblockheads.api.SinkHandler;
import net.blay09.mods.cookingforblockheads.api.ToastOutputHandler;
import net.blay09.mods.cookingforblockheads.registry.CookingRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import roidrole.roidtweaker.mixins.cookingforblockheads.CookingRegistryAccessor;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.FunctionInvoke;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import roidrole.roidtweaker.utils.predicates.OutputMatching;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.cfb.KitchenAppliances")
@ZenRegister
@ModOnly("cookingforblockheads")
@SuppressWarnings("unused")
public class KitchenAppliances {
	@ZenMethod
	@SuppressWarnings("deprecation")
	public static void addToasterRecipe(IItemStack output, IIngredient input) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final ItemStack[] in = CraftTweakerMC.getExamples(input);
		ToastOutputHandler handler = (stack) -> out;

		DeferredLoader.postInit(() -> {
			for(ItemStack stack : in) {
				CookingRegistry.addToastHandler(stack, handler);
			}
		});
	}

	@ZenMethod
	@SuppressWarnings("deprecation")
	public static void removeToasterRecipe(IIngredient output) {
		Predicate<ItemStack> outputFilter = Utils.getStackMatcher(output);
		DeferredLoader.postInit(() ->
			CookingRegistryAccessor.getToastHandlers().entrySet().removeIf(
				(entry) ->
					entry.getValue() instanceof ToastOutputHandler &&
					outputFilter.test(((ToastOutputHandler)entry.getValue()).getToasterOutput(entry.getKey()))
			)
		);
	}

	@ZenMethod
	public static void removeAllToasterRecipes() {
		DeferredLoader.postInit(ListClear.from(CookingRegistryAccessor.getToastHandlers(), "toaster"));
	}

	@ZenMethod
	public static void addSinkRecipe(IItemStack output, IIngredient input) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final ItemStack[] in = CraftTweakerMC.getExamples(input);
		SinkHandler handler = (stack) -> out;

		DeferredLoader.postInit(() -> {
			for(ItemStack stack : in) {
				CookingRegistry.addSinkHandler(stack, handler);
			}
		});
	}

	@ZenMethod
	public static void removeSinkRecipe(IIngredient output) {
		final Predicate<ItemStack> out = Utils.getStackMatcher(output);
		DeferredLoader.postInit(() ->
			CookingRegistryAccessor.getSinkHandlers()
				.entrySet()
				.removeIf(entry ->
					out.test(entry.getValue().getSinkOutput(entry.getKey()))
				)
		);
	}

	@ZenMethod
	public static void removeAllSinkRecipes() {
		DeferredLoader.postInit(ListClear.from(CookingRegistryAccessor.getSinkHandlers(), "sink"));
	}

	@ZenMethod
	public static void addOvenRecipe(IItemStack output, IIngredient input) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final ItemStack[] in = CraftTweakerMC.getExamples(input);
		DeferredLoader.postInit(() -> {
			for (ItemStack stack : in) {
				CookingRegistry.addSmeltingItem(stack, out);
			}
		});
	}

	@ZenMethod
	public static void removeOvenRecipe(IIngredient output) {
		final Predicate<ItemStack> outputFilter = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.from(CookingRegistryAccessor.getOvenRecipes().values(), outputFilter));
	}

	@ZenMethod
	public static void removeAllOvenRecipes() {
		DeferredLoader.postInit(ListClear.from(CookingRegistryAccessor.getOvenRecipes(), "oven"));
	}

	@ZenMethod
	public static void addFoodShapeless(IItemStack output, IIngredient[] inputs) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final NonNullList<Ingredient> in = NonNullList.from(Ingredient.EMPTY, Utils.getIngredients(inputs));
		final IRecipe recipe = new ShapelessOreRecipe(MoreTweaker.newRL(), out, in);
		DeferredLoader.postInit(FunctionInvoke.from(CookingRegistry::addFoodRecipe, recipe));
	}

	@ZenMethod
	public static void addFoodShaped(IItemStack output, IIngredient[][] inputs) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final Ingredient[][] in = Utils.getIngredients(inputs);
		final IRecipe recipe = new MCRecipeShaped(inputs, output, null, null, false, false);
		DeferredLoader.postInit(FunctionInvoke.from(CookingRegistry::addFoodRecipe, recipe));
	}

	@ZenMethod
	public static void removeFoodRecipe(IIngredient output) {
		final Predicate<ItemStack> outputFilter = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.from(CookingRegistryAccessor.getRecipeList(), new OutputMatching(outputFilter)));
	}

	@ZenMethod
	public static void removeAllFoodRecipes() {
		DeferredLoader.postInit(ListClear.from(CookingRegistryAccessor.getRecipeList(), "food"));
	}
}
