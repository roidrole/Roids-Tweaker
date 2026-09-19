package moretweaker.voidcraft;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.FunctionInvoke;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tamaized.voidcraft.common.machina.addons.TERecipesAlchemy;
import tamaized.voidcraft.registry.VoidCraftTERecipes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@ZenClass("moretweaker.voidcraft.VoidAlchemy")
@ZenRegister
@ModOnly("voidcraft")
@SuppressWarnings("unused")
public class VoidAlchemy {
	@ZenMethod
	public static void add(IItemStack output, IIngredient[] inputs, @Optional(valueLong = 350L) final int voidicPower) {
		if (inputs.length != 6) {
			CraftTweakerAPI.logError("For VoidAlchemy: Less or more than  six ingredients specified");
		}
		final ItemStack out = CraftTweakerMC.getItemStack(output);

		final ItemStack[][] ingredients = new ItemStack[6][];
		int length = 1;
		for (int i = 0; i < 6; i++) {
			ItemStack[] stacks = CraftTweakerMC.getExamples(inputs[i]);
			ingredients[i] = stacks;
			length *= stacks.length;
		}
		final List<TERecipesAlchemy.AlchemyRecipe> recipes = new ArrayList<>(length);
		final TERecipesAlchemy parent = new TERecipesAlchemy();
		//I blame MoreTweaker for using IIngredient here.
		for(ItemStack stack0 : ingredients[0]) {
			for(ItemStack stack1 : ingredients[1]) {
				for(ItemStack stack2 : ingredients[2]) {
					for(ItemStack stack3 : ingredients[3]) {
						for(ItemStack stack4 : ingredients[4]) {
							for(ItemStack stack5 : ingredients[5]) {
								//Ugly, but necessary since it's a non-static inner class
								TERecipesAlchemy.AlchemyRecipe recipe = parent.new AlchemyRecipe(
									null,
									new ItemStack[]{stack0, stack1, stack2, stack3, stack4, stack5},
									out,
									voidicPower
								);
								recipes.add(recipe);
							}
						}
					}
				}
			}
		}
		DeferredLoader.postInit(FunctionInvoke.from(VoidCraftTERecipes.alchemy::registerRecipe, recipes));
	}

	@ZenMethod
	public static void remove(IIngredient output) {
		final Predicate<ItemStack> filter = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.from(
			VoidCraftTERecipes.alchemy.getList(),
			filter,
			TERecipesAlchemy.AlchemyRecipe::getOutput
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(VoidCraftTERecipes.alchemy.getList(), "Void Alchemy"));
	}
}
