package moretweaker.jei.infinitycraft;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Map;

public class CompressorWrapper implements IRecipeWrapper {
   private final ItemStack in;
   private final ItemStack out;

   public CompressorWrapper(Map.Entry<ItemStack, ItemStack> entry) {
      this(entry.getKey(), entry.getValue());
   }

   public CompressorWrapper(ItemStack in, ItemStack out) {
      this.in = in.copy();
      this.out = out.copy();
   }

   public void getIngredients(@Nonnull IIngredients ii) {
      ii.setInput(VanillaTypes.ITEM, this.in);
      ii.setOutput(VanillaTypes.ITEM, this.out);
   }
}
