package moretweaker.fossil;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.fossil.CultureVat")
@ZenRegister
@ModOnly("fossil")
@SuppressWarnings("unused")
public class CultureVat {
	@ZenMethod
	public static void addRecipe(IIngredient input, IItemStack output) {
		final ItemStack[] in = CraftTweakerMC.getExamples(input);
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		DeferredLoader.postInit(() -> {
			for(ItemStack stack : in) {
				FAMachineRecipeRegistry.cultivateRecipes.put(stack, out);
			}
		});
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		final Predicate<ItemStack> outputFilter = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.fromValue(FAMachineRecipeRegistry.cultivateRecipes, outputFilter));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(FAMachineRecipeRegistry.cultivateRecipes, "f&a culture vat"));
	}
}
