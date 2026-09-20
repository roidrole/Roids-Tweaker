package moretweaker.railcraft;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.crafting.Crafters;
import mods.railcraft.api.crafting.IOutputEntry;
import mods.railcraft.api.crafting.IRockCrusherCrafter;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import roidrole.roidtweaker.utils.predicates.PredicateAnyOfIterable;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.railcraft.RockCrusher")
@ZenRegister
@ModOnly("railcraft")
@SuppressWarnings("unused")
public class RockCrusher {
	@ZenMethod
	public static void add(IIngredient input, IItemStack[] outputs, final float[] chances, @Optional(valueLong = 100L) final int ticks) {
		if (outputs.length != chances.length) {
			CraftTweakerAPI.logError("Invalid RockCrusher recipe: outputs and chances have different legths: " + outputs.length + ", " + chances.length);
		}
		final ItemStack[] out = CraftTweakerMC.getItemStacks(outputs);

		IRockCrusherCrafter.IRockCrusherRecipeBuilder builder = Crafters.rockCrusher()
			.makeRecipe(RailUtil.toInput(input))
			.time(ticks)
			.name(Utils.newRL());
		for(int i = 0; i < out.length; ++i) {
			builder.addOutput(out[i], chances[i]);
		}

		DeferredLoader.postInit(builder::register);
	}

	@ZenMethod
	public static void remove(IIngredient output) {
		final Predicate<ItemStack> filterStack = Utils.getStackMatcher(output);
		final Predicate<Iterable<IOutputEntry>> filter = new PredicateAnyOfIterable<>(
			entry -> filterStack.test(entry.getOutput())
		);

		DeferredLoader.postInit(ListRemove.from(
			Crafters.rockCrusher().getRecipes(),
			filter,
			IRockCrusherCrafter.IRecipe::getOutputs
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(Crafters.rockCrusher().getRecipes(), "Rock Crusher"));
	}
}
