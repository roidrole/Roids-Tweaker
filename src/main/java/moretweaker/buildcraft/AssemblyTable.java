package moretweaker.buildcraft;

import buildcraft.lib.recipe.AssemblyRecipeRegistry;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.Utils;
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
		CraftTweakerAPI.apply(new IAction() {
			public void apply() {
				final Predicate<ItemStack> out = Utils.getStackMatcher(output);
				AssemblyRecipeRegistry.REGISTRY.values().removeIf((r) -> Utils.anyMatch(out, r.getOutputPreviews()));
			}

			public String describe() {
				return "Removing assembly table recipes for " + output.toCommandString();
			}
		});
	}

	@ZenMethod
	public static void removeAll() {
		CraftTweakerAPI.apply(new IAction() {
			public void apply() {
				AssemblyRecipeRegistry.REGISTRY.clear();
			}

			public String describe() {
				return "Removing all assembly table recipes";
			}
		});
	}
}
