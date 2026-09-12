package moretweaker.jei;

import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

public interface MoreJeiPlugin {
   default void register(IRecipeCategoryRegistration registry) {
   }

   default void register(IModRegistry registry) {
   }
}
