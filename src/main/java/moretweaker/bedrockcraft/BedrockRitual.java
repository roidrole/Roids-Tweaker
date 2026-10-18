package moretweaker.bedrockcraft;

import bedrockcraft.ritual.RitualManager;
import bedrockcraft.ritual.RitualRecipe;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import roidrole.roidtweaker.mixins.bedrockcraft.AccRitualManager;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.FunctionInvoke;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
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
		switch(inputs.length){
			case 2:
			case 3:
			case 4:
			case 6:
			case 12:{
				break;
			}
			default:{
				CraftTweakerAPI.logError("Invalid input count for a bedrock ritual. Should be 2, 3, 4, 6 or 12. Amount received: "+ inputs.length);
				return;
			}
		}
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final Ingredient c = CraftTweakerMC.getIngredient(center);
		Ingredient[] in = Utils.getIngredients(inputs);
		final int amount = Math.max(0, Math.min(bedrockAmount, 320));
		DeferredLoader.postInit(FunctionInvoke.from(RitualManager::addRecipe, out, c, amount, in));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		Predicate<ItemStack> outputMatcher = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.from(
			AccRitualManager.getRecipes(),
			outputMatcher,
			RitualRecipe::output
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(
			AccRitualManager.getRecipes(),
			"Bedrock Ritual"
		));
	}
}
