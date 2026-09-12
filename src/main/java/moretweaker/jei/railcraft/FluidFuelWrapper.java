package moretweaker.jei.railcraft;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.util.Map;

public class FluidFuelWrapper implements IRecipeWrapper {
   private final FluidStack fluid;
   private final String heatString;

   public FluidFuelWrapper(Map.Entry<FluidStack, Integer> entry) {
      this(entry.getKey(), entry.getValue());
   }

   public FluidFuelWrapper(FluidStack stack, int heatPerBucket) {
      this.fluid = stack.copy();
      this.fluid.amount = 1000;
      this.heatString = I18n.format("moretweaker.jei.railcraft.fluidfuel.tooltip", Integer.toString(heatPerBucket));
   }

   public void getIngredients(IIngredients ii) {
      ii.setInput(VanillaTypes.FLUID, this.fluid);
   }

   public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
      minecraft.fontRenderer.drawString(this.heatString, 24, 13, Color.gray.getRGB());
   }
}
