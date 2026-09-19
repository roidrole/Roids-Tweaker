package moretweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;

import java.util.ArrayList;
import java.util.List;

public class Inputs {
   private Inputs() {
   }

   public static Object getObject(IIngredient ingredient) {
      if (ingredient == null) {
         return ItemStack.EMPTY;
      } else if (ingredient instanceof IItemStack) {
         return CraftTweakerMC.getItemStack((IItemStack)ingredient);
      } else if (ingredient instanceof IngredientStack && ingredient.getInternal() instanceof IItemStack) {
         ItemStack stack = CraftTweakerMC.getItemStack((IItemStack)ingredient.getInternal());
         stack.setCount(ingredient.getAmount());
         return stack;
      } else if (ingredient instanceof IOreDictEntry) {
         return ((IOreDictEntry)ingredient).getName();
      } else if (!(ingredient instanceof IngredientStack) && !(ingredient instanceof IngredientItem) && !(ingredient instanceof IngredientOr)) {
         return ItemStack.EMPTY;
      } else {
         List<ItemStack> list = new ArrayList<>();

         for(IItemStack stack : ingredient.getItems()) {
            list.add(CraftTweakerMC.getItemStack(stack));
         }

         return list;
      }
   }

   public static Object[] getObjects(IIngredient[] ingredients) {
      Object[] objects = new Object[ingredients.length];

      for(int i = 0; i < ingredients.length; ++i) {
         objects[i] = getObject(ingredients[i]);
      }

      return objects;
   }


   public static Ingredient getIngredient(Object ingredient) {
      if (ingredient == null) {
         return Ingredient.EMPTY;
      } else if (ingredient instanceof ItemStack) {
         return Ingredient.fromStacks((ItemStack)ingredient);
      } else if (ingredient instanceof String) {
         return new OreIngredient((String)ingredient);
      } else if (ingredient instanceof Item) {
         return Ingredient.fromItem((Item)ingredient);
      } else if (ingredient instanceof Block) {
         return Ingredient.fromItem(Item.getItemFromBlock((Block)ingredient));
      } else if (!(ingredient instanceof List)) {
         return Ingredient.EMPTY;
      } else {
         List<Ingredient> converted = new ArrayList<>();

         for(Object o : (List<?>)ingredient) {
            converted.add(getIngredient(o));
         }

         return Ingredient.merge(converted);
      }
   }

   public static Ingredient[] getIngredients(Object[] ingredients) {
      Ingredient[] objects = new Ingredient[ingredients.length];

      for(int i = 0; i < ingredients.length; ++i) {
         objects[i] = getIngredient(ingredients[i]);
      }

      return objects;
   }

   public static CraftingPart getPart(IIngredient obj) {
      if (obj instanceof IItemStack) {
         ItemStack stack = CraftTweakerMC.getItemStack((IItemStack)obj);
         return new CraftingPart(Ingredient.fromStacks(stack), stack.getCount());
      } else if (obj instanceof IngredientStack && obj.getInternal() instanceof IItemStack) {
         ItemStack stack = CraftTweakerMC.getItemStack((IItemStack)obj.getInternal());
         return new CraftingPart(Ingredient.fromStacks(stack), obj.getAmount());
      } else if (obj instanceof IOreDictEntry) {
         return !OreDictionary.doesOreNameExist(((IOreDictEntry)obj).getName()) ? CraftingPart.EMPTY : new CraftingPart(new OreIngredient(((IOreDictEntry)obj).getName()), obj.getAmount());
      } else if (obj instanceof IngredientStack && obj.getInternal() instanceof IOreDictEntry) {
         return !OreDictionary.doesOreNameExist(((IOreDictEntry)obj.getInternal()).getName()) ? CraftingPart.EMPTY : new CraftingPart(new OreIngredient(((IOreDictEntry)obj.getInternal()).getName()), obj.getAmount());
      } else if (obj instanceof IngredientOr && obj.getInternal() instanceof IIngredient[]) {
         List<IItemStack> stacks = obj.getItems();
         int amount = -1;

         for(IItemStack stack : stacks) {
            if (amount == -1) {
               amount = stack.getAmount();
            } else if (amount != -2 && amount != stack.getAmount()) {
               CraftTweakerAPI.logWarning("Could not retain stack-size for " + obj + " when converting to CraftingPart. Using 1 instead.");
               amount = -2;
            }
         }

         if (amount < 0) {
            amount = 1;
         }

         IIngredient[] parts = (IIngredient[])obj.getInternal();
         List<Ingredient> parts2 = new ArrayList<>();

         for(IIngredient part : parts) {
            parts2.add(getPart(part).ingredient);
         }

         return new CraftingPart(new CompoundIngredient(parts2) {
         }, amount);
      } else if (!(obj instanceof IngredientStack) && !(obj instanceof IngredientItem) && !(obj instanceof IngredientOr)) {
         return CraftingPart.EMPTY;
      } else {
         List<IItemStack> stacks = obj.getItems();
         List<ItemStack> stacks2 = new ArrayList<>();
         int amount = -1;

         for(IItemStack stack : stacks) {
            if (amount == -1) {
               amount = stack.getAmount();
            } else if (amount != -2 && amount != stack.getAmount()) {
               CraftTweakerAPI.logWarning("Could not retain stack-size for " + obj + " when converting to CraftingPart. Using 1 instead.");
               amount = -2;
            }

            stacks2.add(CraftTweakerMC.getItemStack(stack));
         }

         if (amount < 0) {
            amount = 1;
         }

         return new CraftingPart(Ingredient.fromStacks(stacks2.toArray(new ItemStack[0])), amount);
      }
   }
}
