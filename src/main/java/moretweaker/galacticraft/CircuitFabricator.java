package moretweaker.galacticraft;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import micdoodle8.mods.galacticraft.api.recipe.CircuitFabricatorRecipes;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.Utils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

@ZenClass("moretweaker.galacticraft.CircuitFabricator")
@ZenRegister
@ModOnly("galacticraftcore")
@SuppressWarnings("unused")
public class CircuitFabricator {
	@ZenMethod
	public static void add(IItemStack output, IIngredient[] inputs) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		if(out.isEmpty()){
			return;
		}
		if (inputs.length != 5) {
			CraftTweakerAPI.logError("CircuitFabricator expected 5 IIngredients but got " + inputs.length + ".");
		}
		final Object[] in = Utils.getObjects(inputs, Utils::getObjectOreStack);

		CircuitFabricatorRecipes.addRecipe(out, Arrays.asList(in));
	}

	@ZenMethod
	public static void remove(IItemStack output) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		if (!out.isEmpty()) {
			CircuitFabricatorRecipes.removeRecipe(out);
		}
	}
}
