package moretweaker.aoa;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.tslat.aoa3.crafting.recipes.UpgradeKitRecipe;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListAdd;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.aoa.DivineStation")
@ZenRegister
@ModOnly("aoa3")
@SuppressWarnings("unused")
public class DivineStation {
	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack upgradeKit, IItemStack output) {
		UpgradeKitRecipe recipe = new UpgradeKitRecipe(
			"",
			CraftTweakerMC.getItemStack(input),
			CraftTweakerMC.getItemStack(upgradeKit),
			CraftTweakerMC.getItemStack(output)
		);

		DeferredLoader.postInit(ListAdd.from(IRecipe.class, recipe));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		Predicate<ItemStack> outputMatcher = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.from(IRecipe.class, (recipe) ->
			recipe instanceof UpgradeKitRecipe && outputMatcher.test(recipe.getRecipeOutput())
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(IRecipe.class, "Upgrade Kit", UpgradeKitRecipe.class));
	}
}
