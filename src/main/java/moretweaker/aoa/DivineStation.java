package moretweaker.aoa;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.MoreTweaker;
import net.minecraft.item.crafting.IRecipe;
import net.tslat.aoa3.crafting.recipes.UpgradeKitRecipe;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.EnumLoadStage;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

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

		DeferredLoader.load(EnumLoadStage.POST_INIT, () -> MoreTweaker.forgeAdd(IRecipe.class, recipe));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		DeferredLoader.load(EnumLoadStage.POST_INIT, () -> MoreTweaker.forgeRemove(IRecipe.class, (recipe) ->
			recipe instanceof UpgradeKitRecipe && output.matches(CraftTweakerMC.getIItemStackForMatching(recipe.getRecipeOutput()))
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.load(EnumLoadStage.POST_INIT, () -> {
			CraftTweakerAPI.logInfo("Removing all upgrade kit recipes.");
			MoreTweaker.forgeRemove(IRecipe.class, (r) -> r instanceof UpgradeKitRecipe);
		});
	}
}
