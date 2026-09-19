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
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tamaized.voidcraft.common.machina.addons.TERecipeInfuser;
import tamaized.voidcraft.registry.VoidCraftTERecipes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@ZenClass("moretweaker.voidcraft.VoidInfusion")
@ZenRegister
@ModOnly("voidcraft")
@SuppressWarnings("unused")
public class VoidInfusion {
	@ZenMethod
	public static void add(IItemStack output, IIngredient input, final int voidicFluid) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final ItemStack[] in = CraftTweakerMC.getExamples(input);
		final List<TERecipeInfuser.InfuserRecipe> recipes = new ArrayList<>(in.length);

		TERecipeInfuser parent = new TERecipeInfuser();
		for(ItemStack stack : in) {
			TERecipeInfuser.InfuserRecipe recipe = parent.new InfuserRecipe(
				new ItemStack[]{stack},
				out,
				voidicFluid
			);
			recipes.add(recipe);
		}

		DeferredLoader.postInit(FunctionInvoke.from(
			VoidCraftTERecipes.infuser::registerRecipe,
			recipes
		));
	}

	@ZenMethod
	public static void remove(IIngredient output) {
		final Predicate<ItemStack> filter = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.from(
			VoidCraftTERecipes.infuser.getList(),
			filter,
			TERecipeInfuser.InfuserRecipe::getOutput
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(VoidCraftTERecipes.infuser.getList(), "Void Infusion"));
	}
}
