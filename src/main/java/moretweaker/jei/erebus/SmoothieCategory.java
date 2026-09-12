package moretweaker.jei.erebus;

import javax.annotation.Nonnull;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class SmoothieCategory implements IRecipeCategory<SmoothieWrapper> {
   public static final String id = "moretweaker.erebus.smoothie.recipes";
   private final IDrawable background;
   private final String localizedName;
   private final IDrawable fluidMeter;
   private final IDrawableAnimated progress;

   public SmoothieCategory(IGuiHelper guiHelper) {
      ResourceLocation location = new ResourceLocation("erebus", "textures/gui/container/smoothie_maker.png");
      this.background = guiHelper.createDrawable(location, 7, 5, 162, 75);
      this.localizedName = I18n.format("tile.erebus.smoothie_maker.name", new Object[0]);
      this.fluidMeter = guiHelper.createDrawable(location, 176, 38, 9, 73);
      this.progress = guiHelper.drawableBuilder(location, 176, 0, 73, 36).buildAnimated(200, StartDirection.TOP, false);
   }

   @Nonnull
   public String getUid() {
      return "moretweaker.erebus.smoothie.recipes";
   }

   @Nonnull
   public String getTitle() {
      return this.localizedName;
   }

   @Nonnull
   public String getModName() {
      return "Erebus";
   }

   @Nonnull
   public IDrawable getBackground() {
      return this.background;
   }

   public void drawExtras(Minecraft minecraft) {
      this.progress.draw(minecraft, 44, 21);
   }

   public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull SmoothieWrapper recipe, @Nonnull IIngredients ii) {
      IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
      guiItemStacks.init(0, true, 39, 3);
      guiItemStacks.init(1, true, 60, 24);
      guiItemStacks.init(3, true, 84, 24);
      guiItemStacks.init(4, true, 105, 3);
      guiItemStacks.init(5, true, 72, 3);
      guiItemStacks.init(6, false, 72, 57);
      guiItemStacks.set(ii);
      IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
      guiFluidStacks.init(7, true, 1, 1, 9, 73, 16000, true, this.fluidMeter);
      guiFluidStacks.init(8, true, 18, 1, 9, 73, 16000, true, this.fluidMeter);
      guiFluidStacks.init(9, true, 135, 1, 9, 73, 16000, true, this.fluidMeter);
      guiFluidStacks.init(10, true, 152, 1, 9, 73, 16000, true, this.fluidMeter);
      guiFluidStacks.set(ii);
   }
}
