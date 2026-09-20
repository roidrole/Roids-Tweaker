package moretweaker.jei;

import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

public interface MoreJeiPlugin {
   void register(IRecipeCategoryRegistration registry);

   void register(IModRegistry registry);
}
