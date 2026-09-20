package moretweaker.railcraft;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.crafting.Crafters;
import mods.railcraft.api.crafting.IBlastFurnaceCrafter;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.railcraft.BlastFurnace")
@ZenRegister
@ModOnly("railcraft")
@SuppressWarnings("unused")
public class BlastFurnace {
	@ZenMethod
	public static void add(IItemStack output, IIngredient input, @Optional(valueLong = 1280L) final int ticks, @Optional final int slag) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		IBlastFurnaceCrafter.IBlastFurnaceRecipeBuilder builder = Crafters.blastFurnace()
			.newRecipe(RailUtil.toInput(input))
			.output(out)
			.time(ticks)
			.slagOutput(slag)
			.name(Utils.newRL());

		DeferredLoader.postInit(builder::register);
	}

	@ZenMethod
	public static void remove(IIngredient output) {
		final Predicate<ItemStack> filter = Utils.getStackMatcher(output);

		DeferredLoader.postInit(ListRemove.from(
			Crafters.blastFurnace().getRecipes(),
			filter,
			IBlastFurnaceCrafter.IRecipe::getOutput
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(Crafters.blastFurnace().getRecipes(), "BlastFurnace"));
	}
}
