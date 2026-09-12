package moretweaker.jei.infinitycraft;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import moretweaker.jei.MoreJei;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nonnull;

public class CompressorCategory implements IRecipeCategory<CompressorWrapper> {
   public static final String id = "moretweaker.infinitycraft.compressor.recipes";
   private final IDrawable background;
   private final String localizedName;

   public CompressorCategory(IGuiHelper guiHelper) {
      this.background = guiHelper.createDrawable(MoreJei.GUI_VANILLA, 49, 168, 76, 18);
      this.localizedName = I18n.format("tile.block_compressor.name", new Object[0]);
   }

   public @Nonnull IDrawable getBackground() {
      return this.background;
   }

   public @Nonnull String getUid() {
      return "moretweaker.infinitycraft.compressor.recipes";
   }

   public @Nonnull String getTitle() {
      return this.localizedName;
   }

   public @Nonnull String getModName() {
      return "Infinity Craft";
   }

   public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull CompressorWrapper recipeWrapper, @Nonnull IIngredients ii) {
      IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
      stacks.init(0, true, 0, 0);
      stacks.init(1, false, 58, 0);
      stacks.set(ii);
   }
}
