package moretweaker.buildcraft;

import buildcraft.api.recipes.IngredientStack;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.List;

public class BuildUtil {
   public static List<IngredientStack> getIngredients(IIngredient[] ingredients) {
      List<IngredientStack> list = Arrays.asList(new IngredientStack[ingredients.length]);

      for(int i = 0; i < ingredients.length; ++i) {
         IIngredient ingredientCT = ingredients[i];
         Ingredient ingredient = CraftTweakerMC.getIngredient(ingredientCT);
         list.set(i, new IngredientStack(ingredient, ingredientCT.getAmount()));
      }

      return list;
   }
}
