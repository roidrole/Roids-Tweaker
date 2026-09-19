package moretweaker.fossil;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import fossilsarcheology.server.recipe.RecipeWorktable;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.fossil.ArcheologyWorkbench")
@ZenRegister
@ModOnly("fossil")
@SuppressWarnings("unused")
public class ArcheologyWorkbench {
	@ZenMethod
	public static void addRecipe(IIngredient input, IItemStack output) {
		addRecipe(input, output, CraftTweakerMC.getIItemStack(new ItemStack(FAItemRegistry.RELIC_SCRAP)));
	}

	@ZenMethod
	public static void addRecipe(IIngredient input, IItemStack output, IIngredient fuel) {
		final ItemStack[] in = CraftTweakerMC.getExamples(input);
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final ItemStack[] f = CraftTweakerMC.getExamples(fuel);
		DeferredLoader.postInit(() -> {
			for(ItemStack stack : in) {
				for(ItemStack theFuel : f) {
					FAMachineRecipeRegistry.worktableRecipes.add(new RecipeWorktable(stack, out, theFuel));
				}
			}
		});
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		final Predicate<ItemStack> outputFilter = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.from(
			FAMachineRecipeRegistry.worktableRecipes,
			outputFilter,
			RecipeWorktable::getOutput
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(FAMachineRecipeRegistry.worktableRecipes, "f&a workbench"));
	}
}
