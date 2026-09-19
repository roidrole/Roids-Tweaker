package moretweaker.matteroverdrive;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import matteroverdrive.data.recipes.InscriberRecipe;
import matteroverdrive.init.MatterOverdriveRecipes;
import matteroverdrive.tile.TileEntityInscriber;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.matteroverdrive.InscriberRecipeAccessor;
import roidrole.roidtweaker.mixins.matteroverdrive.RecipeManagerAccessor;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;
import java.util.function.Predicate;

@ZenClass("moretweaker.matteroverdrive.Inscriber")
@ZenRegister
@ModOnly("matteroverdrive")
@SuppressWarnings("unused")
public class Inscriber {

	@ZenMethod
	public static void addRecipe(IIngredient input1, IIngredient input2, IItemStack output, final int energy, final int time) {
		final ItemStack[] in1 = CraftTweakerMC.getExamples(input1);
		final ItemStack[] in2 = CraftTweakerMC.getExamples(input2);
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		DeferredLoader.postInit(() -> {
			for(ItemStack stack1 : in1) {
				for(ItemStack stack2 : in2) {
					MatterOverdriveRecipes.INSCRIBER.register(Inscriber.createRecipe(stack1, stack2, out, energy, time));
				}
			}
		});
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		final Predicate<ItemStack> out = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.from(
			Inscriber.getMutableRecipeList(),
			out,
			InscriberRecipe::getOutput
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(Inscriber.getMutableRecipeList(), "inscriber"));
	}

	private static InscriberRecipe createRecipe(ItemStack main, ItemStack sec, ItemStack output, int energy, int ticks) {
		InscriberRecipe recipeRaw = new InscriberRecipe();
		InscriberRecipeAccessor recipe = (InscriberRecipeAccessor) recipeRaw;
		recipe.setMain(main);
		recipe.setSec(sec);
		recipe.setOutput(output);
		recipe.setEnergy(energy);
		recipe.setTime(ticks);
		return recipeRaw;
	}

	@SuppressWarnings("unchecked")
	private static List<InscriberRecipe> getMutableRecipeList() {
		return ((RecipeManagerAccessor<TileEntityInscriber, InscriberRecipe>)MatterOverdriveRecipes.INSCRIBER).getRecipes();
	}
}
