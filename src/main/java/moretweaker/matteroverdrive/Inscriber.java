package moretweaker.matteroverdrive;

import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import matteroverdrive.data.recipes.InscriberRecipe;
import matteroverdrive.init.MatterOverdriveRecipes;
import matteroverdrive.tile.TileEntityInscriber;
import moretweaker.CraftingPart;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.matteroverdrive.InscriberRecipeAccessor;
import roidrole.roidtweaker.mixins.matteroverdrive.RecipeManagerAccessor;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenClass("moretweaker.matteroverdrive.Inscriber")
@ZenRegister
@ModOnly("matteroverdrive")
public class Inscriber {

	@ZenMethod
	public static void addRecipe(IIngredient input1, IIngredient input2, IItemStack output, final int energy, final int time) {
		final CraftingPart in1 = Inputs.getPart(input1);
		final CraftingPart in2 = Inputs.getPart(input2);
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		MoreTweaker.queuePostInitAction(new IAction() {
			public void apply() {
				for(ItemStack stack1 : in1.toStackList()) {
					for(ItemStack stack2 : in2.toStackList()) {
						MatterOverdriveRecipes.INSCRIBER.register(Inscriber.getRecipe(stack1, stack2, out, energy, time));
					}
				}

			}

			public String describe() {
				return "Adds some Inscriber recipes";
			}
		});
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		final Object out = Inputs.getObject(output);
		MoreTweaker.queuePostInitAction(new IAction() {
			public void apply() {
				Inscriber.getMutableRecipeList().removeIf((r) -> Inputs.matchesForRemoval(out, r.getOutput()));
			}

			public String describe() {
				return "Removes some Inscriber recipes";
			}
		});
	}

	@ZenMethod
	public static void removeAll() {
		MoreTweaker.queuePostInitAction(new IAction() {
			public void apply() {
				Inscriber.getMutableRecipeList().clear();
			}

			public String describe() {
				return "Removes all inscriber recipes";
			}
		});
	}

	private static InscriberRecipe getRecipe(ItemStack main, ItemStack sec, ItemStack output, int energy, int ticks) {
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
