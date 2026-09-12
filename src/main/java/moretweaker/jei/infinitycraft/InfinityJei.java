package moretweaker.jei.infinitycraft;

import com.rowan662.infinitycraft.blocks.InfinityCraftBlocks;
import com.rowan662.infinitycraft.machines.CompressorRecipes;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import moretweaker.jei.MoreJeiPlugin;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Field;
import java.util.Map;

public class InfinityJei implements MoreJeiPlugin {
   public void register(IRecipeCategoryRegistration registry) {
      registry.addRecipeCategories(new CompressorCategory(registry.getJeiHelpers().getGuiHelper()));
   }

   public void register(IModRegistry registry) {
      registry.addRecipeCatalyst(new ItemStack(InfinityCraftBlocks.block_compressor), "moretweaker.infinitycraft.compressor.recipes");
      registry.handleRecipes(Map.Entry.class, CompressorWrapper::new, "moretweaker.infinitycraft.compressor.recipes");

      try {
         Field compressingMap = CompressorRecipes.class.getDeclaredField("compressingList");
         compressingMap.setAccessible(true);
         registry.addRecipes(((Map<?, ?>)compressingMap.get(CompressorRecipes.instance())).entrySet(), "moretweaker.infinitycraft.compressor.recipes");
      } catch (ReflectiveOperationException ignored) { }

   }
}
