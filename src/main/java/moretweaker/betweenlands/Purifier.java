package moretweaker.betweenlands;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.thebetweenlands.PurifierRecipeAccessor;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.predicates.PredicateExtract;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thebetweenlands.api.recipes.IPurifierRecipe;
import thebetweenlands.common.BetweenlandsAPI;
import thebetweenlands.common.recipe.purifier.PurifierRecipe;
import thebetweenlands.common.recipe.purifier.PurifierRecipeAspectVial;
import thebetweenlands.common.recipe.purifier.PurifierRecipeStandard;
import thebetweenlands.common.registries.ItemRegistry;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

@ZenClass("moretweaker.betweenlands.Purifier")
@ZenRegister
@ModOnly("thebetweenlands")
@SuppressWarnings("unused")
public class Purifier {
	@ZenMethod
	public static void addRecipe(IIngredient input, IItemStack output) {
		BetweenlandsAPI.getInstance().registerPurifierRecipe(new PurifierRecipeCT(input, CraftTweakerMC.getItemStack(output)));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		Predicate<ItemStack> outputMatcher = Utils.getStackMatcher(output);
		PurifierRecipeAccessor.getRECIPES().removeIf(
			new PredicateExtract<>(outputMatcher, Purifier::guessOutput)
		);
	}

	@ZenMethod
	public static void removeAll() {
		ListClear.clear(PurifierRecipeAccessor.getRECIPES(), "Purifier");
	}

	private static ItemStack guessOutput(IPurifierRecipe recipe) {
		if (recipe instanceof PurifierRecipeStandard) {
			return recipe.getOutput(((PurifierRecipeStandard)recipe).getInput());
		} else if (recipe instanceof PurifierRecipeAspectVial){
			return new ItemStack(ItemRegistry.DENTROTHYST_FLUID_VIAL);
		} else if (recipe instanceof PurifierRecipeCT){
			return ((PurifierRecipeCT) recipe).output;
		} else {
			return recipe.getOutput(ItemStack.EMPTY);
		}
	}

	public static class PurifierRecipeCT extends PurifierRecipe {
		private final Predicate<ItemStack> input;
		private final ItemStack output;

		public PurifierRecipeCT(IIngredient input, ItemStack output) {
			this.input = Utils.getStackMatcher(input);
			this.output = output;
		}

		@Override
		public @Nonnull ItemStack getOutput(ItemStack itemStack) {
			return output;
		}

		@Override
		public boolean matchesInput(ItemStack itemStack) {
			return input.test(itemStack);
		}
	}
}
