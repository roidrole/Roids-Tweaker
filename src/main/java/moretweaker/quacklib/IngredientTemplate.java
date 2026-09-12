package moretweaker.quacklib;

import java.util.List;
import moretweaker.CraftingPart;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import therealfarfetchd.quacklib.common.api.recipe.ItemTemplate;

import javax.annotation.Nonnull;

public class IngredientTemplate implements ItemTemplate {
   private final ItemStack outStack;
   private final CraftingPart part;

   public IngredientTemplate(CraftingPart part) {
      this.part = part;
      List<ItemStack> stacks = part.toStackList();
      if (stacks.isEmpty()) {
         this.outStack = ItemStack.EMPTY;
      } else {
         this.outStack = (ItemStack)stacks.get(0);
      }

   }

   public IngredientTemplate(ItemStack stack) {
      this.part = new CraftingPart(Ingredient.fromStacks(new ItemStack[]{stack}), stack.getCount());
      this.outStack = stack.copy();
   }

   public boolean isSameItem(@Nonnull ItemStack itemStack) {
      return this.part.ingredient.apply(itemStack);
   }

   public boolean isValid() {
      return !this.part.toStackList().isEmpty();
   }

   public @Nonnull ItemStack makeStack() {
      return this.outStack.copy();
   }
}
