package moretweaker.jei.erebus;

import javax.annotation.Nonnull;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class OfferingCategory implements IRecipeCategory<OfferingWrapper> {
   public static final String id = "moretweaker.erebus.offering.recipes";
   private final IDrawable background;
   private final String localizedName;

   public OfferingCategory(IGuiHelper guiHelper) {
      ResourceLocation location = new ResourceLocation("moretweaker", "textures/gui/erebus_offering.png");
      this.background = guiHelper.createDrawable(location, 0, 0, 100, 37);
      this.localizedName = I18n.format("tile.erebus.altar_offering.name", new Object[0]);
   }

   @Nonnull
   public String getUid() {
      return "moretweaker.erebus.offering.recipes";
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

   public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull OfferingWrapper recipe, @Nonnull IIngredients ii) {
      IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
      guiItemStacks.init(0, true, 0, 0);
      guiItemStacks.init(1, true, 0, 20);
      guiItemStacks.init(2, true, 19, 10);
      guiItemStacks.init(3, false, 79, 10);
      guiItemStacks.set(ii);
   }
}
