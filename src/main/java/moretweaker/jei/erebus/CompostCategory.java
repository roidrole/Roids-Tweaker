package moretweaker.jei.erebus;

import javax.annotation.Nonnull;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class CompostCategory implements IRecipeCategory<CompostWrapper> {
   public static final String id = "moretweaker.erebus.compost.recipes";
   private final IDrawable background;
   private final String localizedName;
   private final IDrawableAnimated fuel;
   private final IDrawableAnimated compost;

   public CompostCategory(IGuiHelper guiHelper) {
      ResourceLocation location = new ResourceLocation("erebus", "textures/gui/container/composter.png");
      this.background = guiHelper.createDrawable(location, 55, 16, 85, 54);
      this.localizedName = I18n.format("tile.erebus.composter.name", new Object[0]);
      this.fuel = guiHelper.drawableBuilder(location, 176, 0, 16, 14).buildAnimated(400, StartDirection.TOP, true);
      this.compost = guiHelper.drawableBuilder(location, 176, 14, 30, 30).buildAnimated(200, StartDirection.LEFT, false);
   }

   @Nonnull
   public String getUid() {
      return "moretweaker.erebus.compost.recipes";
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
      this.fuel.draw(minecraft, 1, 20);
      this.compost.draw(minecraft, 21, 12);
   }

   public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull CompostWrapper recipe, @Nonnull IIngredients ii) {
      IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
      guiItemStacks.init(0, true, 0, 0);
      guiItemStacks.init(1, true, 0, 36);
      guiItemStacks.init(2, false, 60, 18);
      guiItemStacks.set(ii);
   }
}
