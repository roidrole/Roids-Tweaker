package moretweaker.railcraft;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.crafting.Crafters;
import mods.railcraft.api.crafting.ICokeOvenCrafter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.railcraft.CokeOven")
@ZenRegister
@ModOnly("railcraft")
@SuppressWarnings("unused")
public class CokeOven {
	@ZenMethod
	public static void add(IItemStack output, IIngredient input, ILiquidStack liquid, @Optional(valueLong = 1800L) final int ticks) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final FluidStack fluid = CraftTweakerMC.getLiquidStack(liquid);
		ICokeOvenCrafter.ICokeOvenRecipeBuilder builder = Crafters.cokeOven()
			.newRecipe(RailUtil.toInput(input))
			.output(out)
			.fluid(fluid)
			.time(ticks)
			.name(Utils.newRL());

		DeferredLoader.postInit(builder::register);
	}

	@ZenMethod
	public static void remove(IIngredient output) {
		final Predicate<ItemStack> filter = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.from(
			Crafters.cokeOven().getRecipes(),
			filter,
			ICokeOvenCrafter.IRecipe::getOutput
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(
			Crafters.cokeOven().getRecipes(),
			"coke oven"
		));
	}
}
