package moretweaker.voidcraft;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.FunctionInvoke;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tamaized.voidcraft.common.machina.addons.TERecipesMacerator;
import tamaized.voidcraft.registry.VoidCraftTERecipes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@ZenClass("moretweaker.voidcraft.VoidMacerator")
@ZenRegister
@ModOnly("voidcraft")
@SuppressWarnings("unused")
public class VoidMacerator {
	@ZenMethod
	public static void add(IItemStack output, IIngredient input, @Optional(valueLong = 200L) final int voidicPower) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final ItemStack[] in = CraftTweakerMC.getExamples(input);
		List<TERecipesMacerator.MaceratorRecipe> recipes = new ArrayList<>(in.length);
		for(ItemStack stack : in) {
			TERecipesMacerator.MaceratorRecipe recipe = new TERecipesMacerator().new MaceratorRecipe(
				new ItemStack[]{stack},
				out,
				voidicPower
			);
			recipes.add(recipe);
		}
		DeferredLoader.postInit(FunctionInvoke.from(
			VoidCraftTERecipes.macerator::registerRecipe,
			recipes
		));
	}

	@ZenMethod
	public static void remove(IIngredient output) {
		final Predicate<ItemStack> filter = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.from(
			VoidCraftTERecipes.macerator.getList(),
			filter,
			TERecipesMacerator.MaceratorRecipe::getOutput
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(VoidCraftTERecipes.macerator.getList(), "Void Macerator"));
	}
}
