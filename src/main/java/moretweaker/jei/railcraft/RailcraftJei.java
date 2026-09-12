package moretweaker.jei.railcraft;

import java.lang.reflect.Field;
import java.util.Map;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mods.railcraft.api.fuel.FluidFuelManager;
import mods.railcraft.common.blocks.RailcraftBlocks;
import moretweaker.jei.MoreJeiPlugin;
import net.minecraft.item.ItemStack;

public class RailcraftJei implements MoreJeiPlugin {
   public void register(IRecipeCategoryRegistration registry) {
      registry.addRecipeCategories(new IRecipeCategory[]{new FluidFuelCategory(registry.getJeiHelpers().getGuiHelper())});
   }

   public void register(IModRegistry registry) {
      registry.addRecipeCatalyst(new ItemStack(RailcraftBlocks.BOILER_FIREBOX_FLUID.block()), new String[]{"moretweaker.railcraft.fluidfuel.recipes"});
      registry.handleRecipes(Map.Entry.class, FluidFuelWrapper::new, "moretweaker.railcraft.fluidfuel.recipes");

      try {
         Field boilerFuel = FluidFuelManager.class.getDeclaredField("boilerFuel");
         boilerFuel.setAccessible(true);
         registry.addRecipes(((Map)boilerFuel.get((Object)null)).entrySet(), "moretweaker.railcraft.fluidfuel.recipes");
      } catch (ReflectiveOperationException var3) {
      }

   }
}
