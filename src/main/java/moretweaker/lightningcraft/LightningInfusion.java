package moretweaker.lightningcraft;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import sblectric.lightningcraft.recipes.LightningInfusionRecipes;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.lightningcraft.LightningInfusion")
@ZenRegister
@ModOnly("lightningcraft")
@SuppressWarnings("unused")
public class LightningInfusion {
	@ZenMethod
	public static void add(IItemStack output, IIngredient catalyst, final int cost, IIngredient[] inputs) {
		if (inputs.length != 4) {
			CraftTweakerAPI.logError("Lightning-Infusion needs 4 inputs, got " + inputs.length);
		}
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final Object cl = Utils.getObjectListStack(catalyst);
		final Object[] in = Utils.getObjects(inputs, Utils::getObjectListStack);

		LightningInfusionRecipes.instance().addRecipe(out, cost, cl, in);
	}

	@ZenMethod
	public static void remove(IIngredient output) {
		final Predicate<ItemStack> obj = Utils.getStackMatcher(output);
		LightningInfusionRecipes.instance().getRecipeList().removeIf(recipe -> obj.test(recipe.getOutput()));
	}

	@ZenMethod
	public static void removeAll() {
		LightningInfusionRecipes.instance().getRecipeList().clear();
		ListClear.logMessage("Lightning Infusion");
	}
}
