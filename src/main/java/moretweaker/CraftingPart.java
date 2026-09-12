package moretweaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public final class CraftingPart {
   public static final CraftingPart EMPTY;
   public final Ingredient ingredient;
   public final int amount;

   public CraftingPart(Ingredient ingredient, int amount) {
      this.ingredient = ingredient;
      this.amount = amount;
   }

   public List<ItemStack> toStackList() {
      ItemStack[] stacks = this.ingredient.getMatchingStacks();
      List<ItemStack> list = new ArrayList();

      for(ItemStack stack : stacks) {
         ItemStack stack0 = stack.copy();
         stack0.setCount(this.amount);
         list.add(stack0);
      }

      return Collections.unmodifiableList(list);
   }

   public boolean matchesWithAmount(ItemStack stack) {
      return this.ingredient.apply(stack) && stack.getCount() == this.amount;
   }

   public ItemStack getAStack() {
      List<ItemStack> stacks = this.toStackList();
      return stacks.isEmpty() ? ItemStack.EMPTY : (ItemStack)stacks.get(0);
   }

   static {
      EMPTY = new CraftingPart(Ingredient.EMPTY, 1);
   }
}
