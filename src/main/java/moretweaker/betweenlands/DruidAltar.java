package moretweaker.betweenlands;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.thebetweenlands.DruidAltarRecipeAccessor;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thebetweenlands.common.BetweenlandsAPI;
import thebetweenlands.common.recipe.misc.DruidAltarRecipe;

import java.util.function.Predicate;

@ZenClass("moretweaker.betweenlands.DruidAltar")
@ZenRegister
@ModOnly("thebetweenlands")
@SuppressWarnings("unused")
public class DruidAltar {
	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient[] inputs) {
		if (inputs.length != 4) {
			CraftTweakerAPI.logError("inputs.length not 4 in DruidAltar#addRecipe");
			return;
		}

		final ItemStack out = CraftTweakerMC.getItemStack(output);

		for(ItemStack in0 : CraftTweakerMC.getExamples(inputs[0])) {
			for(ItemStack in1 : CraftTweakerMC.getExamples(inputs[1])) {
				for(ItemStack in2 : CraftTweakerMC.getExamples(inputs[2])) {
					for(ItemStack in3 : CraftTweakerMC.getExamples(inputs[3])) {
						BetweenlandsAPI.getInstance().registerDruidAltarRecipe(new DruidAltarRecipe(in0, in1, in2, in3, out));
					}
				}
			}
		}
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		Predicate<ItemStack> outFilter = Utils.getStackMatcher(output);

		DruidAltarRecipeAccessor.getDruidAltarRecipes().removeIf(
			recipe -> outFilter.test(((DruidAltarRecipe)recipe).getDefaultOutput())
		);
	}

	@ZenMethod
	public static void removeAll() {
		ListClear.clear(DruidAltarRecipeAccessor.getDruidAltarRecipes(), "Druid Altar");
	}
}
