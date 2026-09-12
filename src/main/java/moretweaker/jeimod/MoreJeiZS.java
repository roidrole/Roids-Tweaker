package moretweaker.jeimod;

import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.plugins.jei.info.IngredientInfoRecipe;
import mezz.jei.plugins.jei.info.IngredientInfoRecipeCategory;
import moretweaker.Inputs;
import moretweaker.jei.MoreJei;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.jei.IngredientInfoRecipeAccessor;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ZenClass("moretweaker.jei.MoreJei")
@ZenRegister
@ModOnly("jei")
@SuppressWarnings("unused")
public class MoreJeiZS {
	@ZenMethod
	public static void removeDescription(IIngredient input) {
		final Object obj = Inputs.getObject(input);
		MoreJei.queueJeiRuntimeAction(new IAction() {
			public void apply() {
				MoreJei.run((runtime) -> {
					IngredientInfoRecipeCategory category = (IngredientInfoRecipeCategory)runtime.getRecipeRegistry().getRecipeCategory("jei.information");
					if (category != null) {
						List<IRecipeWrapper> removal = new ArrayList<>();
						runtime.getRecipeRegistry().getRecipeWrappers(category).forEach((wrapperRaw) -> {
							IngredientInfoRecipeAccessor<?> wrapper = (IngredientInfoRecipeAccessor<?>)wrapperRaw;
							if(wrapper.getIngredientType() != VanillaTypes.ITEM){
								return;
							}
							if (Inputs.matchesForRemoval(obj, (Iterable<ItemStack>) wrapper.getIngredients())) {
								removal.add(wrapperRaw);
							}

						});
						removal.forEach((wrapper) -> runtime.getRecipeRegistry().hideRecipe(wrapper, "jei.information"));
					}

				});
			}

			public String describe() {
				return "Removes JEI descriptions";
			}
		});
	}

	@ZenMethod
	public static void addDescription(IIngredient input, final String[] description) {
		final List<ItemStack> stacks = Arrays.asList(Inputs.getStackList(input));
		MoreJei.queueJeiRuntimeAction(new IAction() {
			public void apply() {
				MoreJei.run((runtime) -> {
					List<IngredientInfoRecipe<ItemStack>> wrappers = IngredientInfoRecipe.create(MoreJei.getHelpers().getGuiHelper(), stacks, VanillaTypes.ITEM, description);
					wrappers.forEach((wrapper) -> runtime.getRecipeRegistry().addRecipe(wrapper, "jei.information"));
				});
			}

			public String describe() {
				return "Removes JEI descriptions";
			}
		});
	}
}
