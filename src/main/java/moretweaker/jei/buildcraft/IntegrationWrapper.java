package moretweaker.jei.buildcraft;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.IngredientStack;
import buildcraft.api.recipes.IntegrationRecipe;
import buildcraft.lib.recipe.IntegrationRecipeBasic;
import com.google.common.collect.ImmutableList;
import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import moretweaker.CraftingPart;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class IntegrationWrapper implements IRecipeWrapper {
   private static Field outputField;
   private final IntegrationRecipe recipe;
   private final CraftingPart catalyst;
   private final CraftingPart[] inputs;
   private final ItemStack output;
   private final String mjString;

   public IntegrationWrapper(IntegrationRecipeBasic recipe) {
      this.recipe = recipe;
      ItemStack output;
      if (outputField == null) {
         output = new ItemStack(Blocks.BARRIER);
         output.setStackDisplayName("Could not obtain Buildcraft-Recipe Output for some reason. Report to MoreTweaker author.");
      } else {
         try {
            output = (ItemStack)outputField.get(recipe);
         } catch (ClassCastException | IllegalAccessException var4) {
            output = new ItemStack(Blocks.BARRIER);
            output.setStackDisplayName("Could not obtain Buildcraft-Recipe Output for some reason. Report to MoreTweaker author.");
         }
      }

      this.output = output;
      this.catalyst = this.toCraftingPart(recipe.getCenterStack());
      ImmutableList<IngredientStack> inputs = recipe.getRequirements(output);
      this.inputs = this.toCraftingParts((IngredientStack[])inputs.toArray(new IngredientStack[0]));
      this.mjString = recipe.getRequiredMicroJoules(output) / MjAPI.MJ + " MJ";
   }

   public void getIngredients(@Nonnull IIngredients ii) {
      List<List<ItemStack>> in = new ArrayList();
      in.add(this.catalyst.toStackList());

      for(int i = 0; i < 8; ++i) {
         if (i < this.inputs.length) {
            in.add(this.inputs[i].toStackList());
         } else {
            in.add(ImmutableList.of(ItemStack.EMPTY));
         }
      }

      ii.setInputLists(VanillaTypes.ITEM, in);
      ii.setOutput(VanillaTypes.ITEM, this.output);
   }

   public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
      minecraft.fontRenderer.drawString(this.mjString, 74, 56, Color.gray.getRGB());
   }

   private CraftingPart toCraftingPart(IngredientStack stack) {
      return new CraftingPart(stack.ingredient, stack.count);
   }

   private CraftingPart[] toCraftingParts(IngredientStack[] stacks) {
      CraftingPart[] parts = new CraftingPart[stacks.length];

      for(int i = 0; i < stacks.length; ++i) {
         parts[i] = this.toCraftingPart(stacks[i]);
      }

      return parts;
   }

   static {
      try {
         outputField = IntegrationRecipeBasic.class.getDeclaredField("output");
         outputField.setAccessible(true);
      } catch (SecurityException | NoSuchFieldException e) {
         ((Exception)e).printStackTrace();
         outputField = null;
      }

   }
}
