package moretweaker.jeimod;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.plugins.jei.info.IngredientInfoRecipe;
import mezz.jei.plugins.jei.info.IngredientInfoRecipeCategory;
import moretweaker.jei.MoreJei;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.jei.AccIngredientInfoRecipe;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.predicates.PredicateAnyOfIterable;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@ZenClass("moretweaker.jei.MoreJei")
@ZenRegister
@ModOnly("jei")
@SuppressWarnings("unused")
public class MoreJeiZS {
	@ZenMethod
	public static void removeDescription(IIngredient input) {
		final Predicate<Iterable<ItemStack>> filter = new PredicateAnyOfIterable<>(Utils.getStackMatcher(input));
		DeferredLoader.jeiRuntime(runtime -> {
			IRecipeRegistry registry = runtime.getRecipeRegistry();
			IngredientInfoRecipeCategory category = (IngredientInfoRecipeCategory)registry.getRecipeCategory("jei.information");
			if(category == null){
				return;
			}
			List<IRecipeWrapper> removal = new ArrayList<>();
			registry.getRecipeWrappers(category).forEach((wrapperRaw) -> {
				AccIngredientInfoRecipe<?> wrapper = (AccIngredientInfoRecipe<?>)wrapperRaw;
				if(wrapper.getIngredientType() != VanillaTypes.ITEM){
					return;
				}
				if (filter.test((Iterable<ItemStack>) wrapper.getIngredients())) {
					removal.add(wrapperRaw);
				}

			});
			removal.forEach((wrapper) -> registry.hideRecipe(wrapper, "jei.information"));
		});
	}

	@ZenMethod
	public static void addDescription(IIngredient input, final String[] description) {
		final List<ItemStack> stacks = Arrays.asList(CraftTweakerMC.getExamples(input));
		DeferredLoader.jeiRuntime(runtime -> {
			IRecipeCategory<IngredientInfoRecipe> category = runtime.getRecipeRegistry().getRecipeCategory("jei.information");
			List<IngredientInfoRecipe<ItemStack>> wrappers = IngredientInfoRecipe.create(MoreJei.getHelpers().getGuiHelper(), stacks, VanillaTypes.ITEM, description);
			wrappers.forEach((wrapper) -> runtime.getRecipeRegistry().addRecipe(wrapper, "jei.information"));
		});
	}
}
