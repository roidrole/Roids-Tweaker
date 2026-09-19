package moretweaker.lightningcraft;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import sblectric.lightningcraft.recipes.LightningCrusherRecipes;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.lightningcraft.LightningCrusher")
@ZenRegister
@ModOnly("lightningcraft")
@SuppressWarnings("unused")
public class LightningCrusher {
	@ZenMethod
	public static void add(IItemStack output, IIngredient input) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final Object in = Utils.getObjectListStack(input);
		LightningCrusherRecipes.instance().addRecipe(out, in);
	}

	@ZenMethod
	public static void remove(IIngredient output) {
		final Predicate<ItemStack> filter = Utils.getStackMatcher(output);
		LightningCrusherRecipes.instance().getRecipeList().removeIf(recipe -> filter.test(recipe.getOutput()));
	}

	@ZenMethod
	public static void removeAll() {
		LightningCrusherRecipes.instance().getRecipeList().clear();
		ListClear.logMessage("Lightning Crusher");
	}
}
