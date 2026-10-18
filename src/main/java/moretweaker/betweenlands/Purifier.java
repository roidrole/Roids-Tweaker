package moretweaker.betweenlands;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import roidrole.roidtweaker.mixins.thebetweenlands.AccPurifierRecipe;
import roidrole.roidtweaker.utils.BucketedLinkedCollection;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.predicates.PredicateExtract;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thebetweenlands.api.recipes.IPurifierRecipe;
import thebetweenlands.common.BetweenlandsAPI;
import thebetweenlands.common.item.misc.ItemMisc;
import thebetweenlands.common.recipe.purifier.PurifierRecipe;
import thebetweenlands.common.recipe.purifier.PurifierRecipeAspectVial;
import thebetweenlands.common.recipe.purifier.PurifierRecipeStandard;
import thebetweenlands.common.registries.FluidRegistry;
import thebetweenlands.common.registries.ItemRegistry;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@ZenClass("moretweaker.betweenlands.Purifier")
@ZenRegister
@ModOnly("thebetweenlands")
@SuppressWarnings("unused")
public class Purifier {
	@ZenMethod
	public static void addRecipe(IIngredient input, IItemStack output) {
		PurifierRecipeCT recipe = new PurifierRecipeCT(input, CraftTweakerMC.getItemStack(output));
		BetweenlandsAPI.getInstance().registerPurifierRecipe(recipe);
		AddRecipeJEI.getInstance().addRecipe(new PurifierRecipeCTWrapper(recipe));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		Predicate<ItemStack> outputMatcher = Utils.getStackMatcher(output);
		AccPurifierRecipe.getRECIPES().removeIf(
			new PredicateExtract<>(outputMatcher, Purifier::guessOutput)
		);
	}

	@ZenMethod
	public static void removeAll() {
		ListClear.clear(AccPurifierRecipe.getRECIPES(), "Purifier");
	}

	private static ItemStack guessOutput(IPurifierRecipe recipe) {
		if (recipe instanceof PurifierRecipeStandard) {
			return recipe.getOutput(((PurifierRecipeStandard)recipe).getInput());
		} else if (recipe instanceof PurifierRecipeAspectVial){
			return new ItemStack(ItemRegistry.DENTROTHYST_FLUID_VIAL);
		} else if (recipe instanceof PurifierRecipeCT){
			return ((PurifierRecipeCT) recipe).output;
		} else {
			//Getting recipe output from an empty input is not safe, and I will not try/catch here
			return ItemStack.EMPTY;
		}
	}

	public static class PurifierRecipeCT extends PurifierRecipe {
		private final Ingredient input;
		private final ItemStack output;

		public PurifierRecipeCT(IIngredient input, ItemStack output) {
			this.input = CraftTweakerMC.getIngredient(input);
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

	//The default JEI API only automatically adds wrappers for simple recipes
	public static class PurifierRecipeCTWrapper implements IRecipeWrapper {
		private static final List<ItemStack> sulfur = Collections.singletonList(ItemMisc.EnumItemMisc.SULFUR.create(1));
		private static final FluidStack swampWater = new FluidStack(FluidRegistry.SWAMP_WATER, 250);

		private final PurifierRecipeCT recipe;

		public PurifierRecipeCTWrapper(PurifierRecipeCT recipe) {
			this.recipe = recipe;
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputLists(VanillaTypes.ITEM, Arrays.asList(
				Arrays.asList(recipe.input.getMatchingStacks()),
				sulfur
			));
			ingredients.setOutput(VanillaTypes.ITEM, recipe.output);
			ingredients.setInput(VanillaTypes.FLUID, swampWater);
		}
	}

	//Because the betweenlands will only do single-stack JEI recipes
	public static class AddRecipeJEI implements DeferredLoader.IActionConsume<IModRegistry> {
		public static AddRecipeJEI INSTANCE;
		private final Collection<IRecipeWrapper> wrappers;

		private AddRecipeJEI() {
			this.wrappers = new BucketedLinkedCollection<>(4);
		}

		public static AddRecipeJEI getInstance(){
			if(INSTANCE == null){
				INSTANCE = new AddRecipeJEI();
				DeferredLoader.jeiRegister(INSTANCE);
			}
			return INSTANCE;
		}

		public void addRecipe(IRecipeWrapper wrapper){
			wrappers.add(wrapper);
		}

		@Override
		public void load(IModRegistry registry) {
			registry.addRecipes(wrappers, "thebetweenlands:purifier");
			INSTANCE = null;
		}
	}
}
