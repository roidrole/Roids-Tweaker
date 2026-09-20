package moretweaker.buildcraft;

import buildcraft.lib.recipe.AssemblyRecipeRegistry;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.predicates.PredicateAnyOfIterable;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenExpansion("mods.buildcraft.AssemblyTable")
@ZenRegister
@ModOnly("buildcraftsilicon")
@SuppressWarnings("unused")
public class AssemblyTable {
	@ZenMethod
	public static void remove(IIngredient output) {
		final Predicate<Iterable<ItemStack>> out = new PredicateAnyOfIterable<>(Utils.getStackMatcher(output));
		AssemblyRecipeRegistry.REGISTRY.values().removeIf(recipe -> out.test(recipe.getOutputPreviews()));
	}

	@ZenMethod
	public static void removeAll() {
		ListClear.clear(AssemblyRecipeRegistry.REGISTRY.entrySet(), "Assembly Table");
	}
}
