package moretweaker.railcraft;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.recipes.MCRecipeShaped;
import crafttweaker.mc1120.recipes.MCRecipeShapeless;
import mods.railcraft.api.crafting.Crafters;
import mods.railcraft.api.crafting.IRollingMachineCrafter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.FunctionInvoke;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.railcraft.RollingMachine")
@ZenRegister
@ModOnly("railcraft")
@SuppressWarnings("unused")
public class RollingMachine {
	@ZenMethod
	public static void addShaped(IItemStack output, IIngredient[][] inputs, @Optional(valueLong = 100L) final int ticks) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final IRecipe recipe = new MCRecipeShaped(inputs, output, null, null, false, false);
		final IRollingMachineCrafter.IRollingMachineRecipeBuilder builder = Crafters.rollingMachine()
			.newRecipe(out)
			.time(ticks)
			.name(Utils.newRL());

		DeferredLoader.postInit(FunctionInvoke.from(builder::recipe, recipe));
	}

	@ZenMethod
	public static void addShapeless(IItemStack output, IIngredient[] inputs, @Optional(valueLong = 100L) final int ticks) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final IRecipe recipe = new MCRecipeShapeless(inputs, output, null, null, false);
		final IRollingMachineCrafter.IRollingMachineRecipeBuilder builder = Crafters.rollingMachine()
			.newRecipe(out)
			.time(ticks)
			.name(Utils.newRL());

		DeferredLoader.postInit(FunctionInvoke.from(builder::recipe, recipe));
	}

	@ZenMethod
	public static void remove(IIngredient output) {
		Predicate<ItemStack> filter = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.from(
			Crafters.rollingMachine().getRecipes(),
			filter,
			IRollingMachineCrafter.IRollingRecipe::getRecipeOutput
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(Crafters.rollingMachine().getRecipes(), "Rolling Machine"));
	}
}
