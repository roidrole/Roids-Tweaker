package moretweaker.jei.erebus;

import com.google.common.collect.ImmutableList;
import erebus.recipes.SmoothieMakerRecipe;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class SmoothieWrapper implements IRecipeWrapper {
   private final SmoothieMakerRecipe recipe;

   public SmoothieWrapper(SmoothieMakerRecipe recipe) {
      this.recipe = recipe;
   }

   public void getIngredients(@Nonnull IIngredients ii) {
      List<List<ItemStack>> in = ErebusJei.getStacksList(this.recipe.getInputs(), 4);
      in.add(ImmutableList.of(this.recipe.getContainer()));
      ii.setInputLists(VanillaTypes.ITEM, in);
      ii.setOutput(VanillaTypes.ITEM, this.recipe.getOutput());
      List<List<FluidStack>> fluids = new ArrayList();

      for(FluidStack stack : this.recipe.getFluids()) {
         fluids.add(ImmutableList.of(stack));
      }

      ii.setInputLists(VanillaTypes.FLUID, fluids);
   }
}
