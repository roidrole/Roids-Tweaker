package moretweaker.voidcraft;

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
import tamaized.voidcraft.common.machina.addons.TERecipesBlastFurnace;
import tamaized.voidcraft.registry.VoidCraftTERecipes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@ZenClass("moretweaker.voidcraft.VoidFurnace")
@ZenRegister
@ModOnly("voidcraft")
@SuppressWarnings("unused")
public class VoidFurnace {
	@ZenMethod
	public static void add(IItemStack output, IIngredient input1, IIngredient input2, @Optional(valueLong = 500L) final int voidicPower) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final ItemStack[] in1 = CraftTweakerMC.getExamples(input1);
		final ItemStack[] in2 = CraftTweakerMC.getExamples(input2);
		final List<TERecipesBlastFurnace.BlastFurnaceRecipe> recipes = new ArrayList<>(in1.length * in2.length);
		final TERecipesBlastFurnace parent = new TERecipesBlastFurnace();
		for(ItemStack stack1 : in1) {
			for(ItemStack stack2 : in2) {
				TERecipesBlastFurnace.BlastFurnaceRecipe recipe = parent.new BlastFurnaceRecipe(
					new ItemStack[]{stack1, stack2},
					out,
					voidicPower
				);
				recipes.add(recipe);
				VoidCraftTERecipes.blastFurnace.registerRecipe(recipe);
			}
		}
		DeferredLoader.postInit(FunctionInvoke.from(VoidCraftTERecipes.blastFurnace::registerRecipe, recipes));
	}

	@ZenMethod
	public static void remove(IIngredient output) {
		final Predicate<ItemStack> filter = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.from(
			VoidCraftTERecipes.blastFurnace.getList(),
			filter,
			TERecipesBlastFurnace.BlastFurnaceRecipe::getOutput
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(VoidCraftTERecipes.blastFurnace.getList(), "Void Furnace"));
	}
}
