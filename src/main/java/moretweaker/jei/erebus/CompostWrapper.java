package moretweaker.jei.erebus;

import com.google.common.collect.ImmutableList;
import erebus.ModBlocks;
import erebus.ModItems;
import erebus.recipes.ComposterRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class CompostWrapper implements IRecipeWrapper {
   private final ItemStack compost;
   private final ItemStack out;

   public CompostWrapper(ItemStack compost) {
      this.compost = compost.copy();
      ItemStack tmp = ComposterRegistry.isCompostable(compost);
      if (tmp == null || tmp.isEmpty()) {
         tmp = new ItemStack(ModItems.COMPOST);
      }

      this.out = tmp;
   }

   public void getIngredients(@Nonnull IIngredients ii) {
      List<List<ItemStack>> in = new ArrayList();
      in.add(ImmutableList.of(this.compost.copy()));
      in.add(ImmutableList.of(new ItemStack(ModBlocks.WALL_PLANTS, 1, 7), new ItemStack(ModBlocks.WALL_PLANTS_CULTIVATED, 1, 7)));
      ii.setInputLists(VanillaTypes.ITEM, in);
      ii.setOutput(VanillaTypes.ITEM, this.out.copy());
   }
}
