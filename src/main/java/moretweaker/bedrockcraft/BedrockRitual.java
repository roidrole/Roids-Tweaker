package moretweaker.bedrockcraft;

import bedrockcraft.ritual.RitualManager;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.EnumLoadStage;
import roidrole.roidtweaker.utils.Utils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.bedrockcraft.BedrockRitual")
@ZenRegister
@ModOnly("bedrockcraft")
@SuppressWarnings("unused")
public class BedrockRitual {
	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient center, int bedrockAmount, IIngredient[] inputs) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final Ingredient c = CraftTweakerMC.getIngredient(center);
		Ingredient[] in = Utils.getIngredients(inputs);
		final int amount = Math.max(0, Math.min(bedrockAmount, 320));
		DeferredLoader.load(EnumLoadStage.POST_INIT,
			() -> RitualManager.addRecipe(out, c, amount, (Object[]) in)
		);
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		Predicate<ItemStack> outputMatcher = Utils.getStackMatcher(output);
		DeferredLoader.load(EnumLoadStage.POST_INIT, () ->
			RitualManager.removeRecipes(recipe -> outputMatcher.test(recipe.output()))
		);
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.load(EnumLoadStage.POST_INIT, () -> {
			CraftTweakerAPI.logInfo("Removing all recipes for the Bedrock Ritual");
			RitualManager.removeRecipes((r) -> true);
		});
	}
}
