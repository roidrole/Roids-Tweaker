package moretweaker.jei.erebus;

import erebus.recipes.OfferingAltarRecipe;
import javax.annotation.Nonnull;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;

public class OfferingWrapper implements IRecipeWrapper {
   private final OfferingAltarRecipe recipe;

   public OfferingWrapper(OfferingAltarRecipe recipe) {
      this.recipe = recipe;
   }

   public void getIngredients(@Nonnull IIngredients ii) {
      ii.setInputLists(VanillaTypes.ITEM, ErebusJei.getStacksList(this.recipe.getInputs(), 3));
      ii.setOutput(VanillaTypes.ITEM, this.recipe.getOutput());
   }
}
