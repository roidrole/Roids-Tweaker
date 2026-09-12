package moretweaker.jei.railcraft;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import moretweaker.jei.MoreJei;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nonnull;

public class FluidFuelCategory implements IRecipeCategory<FluidFuelWrapper> {
   public static final String id = "moretweaker.railcraft.fluidfuel.recipes";
   private final IDrawable background;
   protected final IDrawableAnimated flame;
   private final String localizedName;

   public FluidFuelCategory(IGuiHelper guiHelper) {
      this.background = guiHelper.drawableBuilder(MoreJei.GUI_VANILLA, 0, 134, 18, 34).addPadding(0, 0, 0, 144).build();
      this.flame = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(MoreJei.GUI_VANILLA, 82, 114, 14, 14), 300, StartDirection.TOP, true);
      this.localizedName = I18n.format("tile.railcraft.boiler.firebox.fluid.name", new Object[0]);
   }

   public @Nonnull IDrawable getBackground() {
      return this.background;
   }

   public @Nonnull String getUid() {
      return "moretweaker.railcraft.fluidfuel.recipes";
   }

   public @Nonnull String getTitle() {
      return this.localizedName;
   }

   public @Nonnull String getModName() {
      return "Railcraft";
   }

   public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull FluidFuelWrapper recipeWrapper, @Nonnull IIngredients ii) {
      IGuiFluidStackGroup fluids = recipeLayout.getFluidStacks();
      fluids.init(0, true, 0, 16, 18, 18, 1000, false, (IDrawable)null);
      fluids.set(ii);
   }
}
