package moretweaker.railcraft;

import mods.railcraft.api.core.IIngredientSource;
import moretweaker.CraftingPart;

public class RailUtil {
   private RailUtil() {
   }

   public static IIngredientSource toInput(CraftingPart part) {
      return () -> part.ingredient;
   }
}
