package moretweaker.jei.buildcraft;

import buildcraft.api.BCBlocks.Silicon;
import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IntegrationRecipe;
import buildcraft.lib.recipe.IntegrationRecipeBasic;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import moretweaker.MoreTweaker;
import moretweaker.jei.MoreJeiPlugin;
import net.minecraft.item.ItemStack;

public class BuildCraftJei implements MoreJeiPlugin {
   public void register(IRecipeCategoryRegistration registry) {
      registry.addRecipeCategories(new IRecipeCategory[]{new IntegrationCategory(registry.getJeiHelpers().getGuiHelper())});
   }

   public void register(IModRegistry registry) {
      registry.addRecipeCatalyst(new ItemStack(Silicon.INTEGRATION_TABLE), new String[]{"moretweaker.buildcraft.integration.recipes"});
      registry.handleRecipes(IntegrationRecipeBasic.class, IntegrationWrapper::new, "moretweaker.buildcraft.integration.recipes");
      List<IntegrationRecipeBasic> integration = new ArrayList();

      for(IntegrationRecipe recipe : BuildcraftRecipeRegistry.integrationRecipes.getAllRecipes()) {
         if (recipe instanceof IntegrationRecipeBasic) {
            integration.add((IntegrationRecipeBasic)recipe);
         } else {
            MoreTweaker.logger.warn("Could not add Integration-Table Recipe to JEI: Not an instance of IntegrationRecipeBasic");
         }
      }

      registry.addRecipes(integration, "moretweaker.buildcraft.integration.recipes");
   }
}
