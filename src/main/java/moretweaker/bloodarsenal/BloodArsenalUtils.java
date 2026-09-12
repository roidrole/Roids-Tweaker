package moretweaker.bloodarsenal;

import arcaratus.bloodarsenal.recipe.RecipeFilter;
import crafttweaker.api.item.IIngredient;
import java.util.List;
import moretweaker.Inputs;

public class BloodArsenalUtils {
   private BloodArsenalUtils() {
   }

   public static int[] amountArray(IIngredient[] in) {
      int[] amounts = new int[in.length];

      for(int i = 0; i < in.length; ++i) {
         if (in[i] == null) {
            amounts[i] = 0;
         } else {
            amounts[i] = in[i].getAmount();
         }
      }

      return amounts;
   }

   public static Object toBAObject(Object obj) {
      return obj instanceof List ? new RecipeFilter((x) -> Inputs.matchesForRemoval(obj, x)) : obj;
   }

   public static Object[] toBAArray(Object[] obj) {
      for(int i = 0; i < obj.length; ++i) {
         obj[i] = toBAObject(obj[i]);
      }

      return obj;
   }
}
