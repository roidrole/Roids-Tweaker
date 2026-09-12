package moretweaker.jei.buildcraft;

import javax.annotation.Nonnull;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class IntegrationCategory implements IRecipeCategory<IntegrationWrapper> {
   public static final String id = "moretweaker.buildcraft.integration.recipes";
   private final IDrawable background;
   private final String localizedName;

   public IntegrationCategory(IGuiHelper guiHelper) {
      ResourceLocation location = new ResourceLocation("buildcraftsilicon", "textures/gui/integration_table.png");
      this.background = guiHelper.createDrawable(location, 16, 21, 145, 72);
      this.localizedName = I18n.format("tile.integrationTableBlock.name", new Object[0]);
   }

   @Nonnull
   public String getUid() {
      return "moretweaker.buildcraft.integration.recipes";
   }

   @Nonnull
   public String getTitle() {
      return this.localizedName;
   }

   @Nonnull
   public String getModName() {
      return "BuildCraft";
   }

   @Nonnull
   public IDrawable getBackground() {
      return this.background;
   }

   public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IntegrationWrapper recipe, @Nonnull IIngredients ii) {
      IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
      guiItemStacks.init(0, true, 27, 27);
      guiItemStacks.init(1, true, 2, 2);
      guiItemStacks.init(2, true, 27, 2);
      guiItemStacks.init(3, true, 52, 2);
      guiItemStacks.init(4, true, 2, 27);
      guiItemStacks.init(5, true, 52, 27);
      guiItemStacks.init(6, true, 2, 52);
      guiItemStacks.init(7, true, 27, 52);
      guiItemStacks.init(8, true, 52, 52);
      guiItemStacks.init(9, false, 121, 27);
      guiItemStacks.set(ii);
   }
}
