package moretweaker;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
      List<ItemStack> list = new ArrayList<>();

      for(ItemStack stack : stacks) {
         ItemStack stack0 = stack.copy();
         stack0.setCount(this.amount);
         list.add(stack0);
      }

      return Collections.unmodifiableList(list);
   }

   static {
      EMPTY = new CraftingPart(Ingredient.EMPTY, 1);
   }
}
