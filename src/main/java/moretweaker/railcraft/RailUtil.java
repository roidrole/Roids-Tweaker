package moretweaker.railcraft;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.core.IIngredientSource;

public class RailUtil {
   private RailUtil() {
   }

   public static IIngredientSource toInput(IIngredient ingredient) {
      return () -> CraftTweakerMC.getIngredient(ingredient);
   }
}
