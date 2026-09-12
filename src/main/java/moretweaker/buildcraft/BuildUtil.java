package moretweaker.buildcraft;

import buildcraft.api.recipes.IngredientStack;
import com.google.common.collect.ImmutableSet;
import crafttweaker.api.item.IIngredient;
import moretweaker.CraftingPart;
import moretweaker.Inputs;

public class BuildUtil {
   private BuildUtil() {
   }

   public static ImmutableSet<IngredientStack> getIngredients(IIngredient[] ingredients) {
      IngredientStack[] in2 = new IngredientStack[ingredients.length];

      for(int i = 0; i < in2.length; ++i) {
         CraftingPart part = Inputs.getPart(ingredients[i]);
         in2[i] = new IngredientStack(part.ingredient, part.amount);
      }

      return ImmutableSet.copyOf(in2);
   }
}
