package moretweaker;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.*;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;

import java.util.*;
import java.util.function.Function;

public class Inputs {
   private Inputs() {
   }

   public static ItemStack[] getStackList(IIngredient ingredient) {
      if (ingredient == null) {
         return new ItemStack[0];
      } else if (ingredient instanceof IItemStack) {
         return new ItemStack[]{InputHelper.toStack((IItemStack)ingredient)};
      } else if (ingredient instanceof IOreDictEntry) {
         return OreDictionary.getOres(((IOreDictEntry)ingredient).getName()).toArray(new ItemStack[0]);
      } else if (!(ingredient instanceof IngredientStack) && !(ingredient instanceof IngredientItem) && !(ingredient instanceof IngredientOr)) {
         return new ItemStack[0];
      } else {
         ItemStack[] stacks = InputHelper.toStacks(ingredient.getItemArray());
         return stacks == null ? new ItemStack[0] : stacks;
      }
   }

   public static Object getObject(IIngredient ingredient) {
      if (ingredient == null) {
         return ItemStack.EMPTY;
      } else if (ingredient instanceof IItemStack) {
         return InputHelper.toStack((IItemStack)ingredient);
      } else if (ingredient instanceof IngredientStack && ingredient.getInternal() instanceof IItemStack) {
         ItemStack stack = InputHelper.toStack((IItemStack)ingredient.getInternal());
         stack.setCount(ingredient.getAmount());
         return stack;
      } else if (ingredient instanceof IOreDictEntry) {
         return ((IOreDictEntry)ingredient).getName();
      } else if (!(ingredient instanceof IngredientStack) && !(ingredient instanceof IngredientItem) && !(ingredient instanceof IngredientOr)) {
         return ItemStack.EMPTY;
      } else {
         List<ItemStack> list = new ArrayList<>();

         for(IItemStack stack : ingredient.getItems()) {
            list.add(InputHelper.toStack(stack));
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

   public static Object[] getShapedObjects(IIngredient[][] ingredients) {
      char[][] chars = new char[][]{{'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'}};
      Map<Character, Object> inputs = new HashMap<>();
      if (ingredients.length >= 1 && ingredients.length <= 3) {
         int length = -1;

         for(int i = 0; i < ingredients.length; ++i) {
            if (length < 0) {
               if (ingredients[i].length < 1 || ingredients[i].length > 3) {
                  CraftTweakerAPI.logError("Could not generate shaped objects as of invalid input length: " + ingredients[i].length);
                  return new Object[0];
               }

               length = ingredients[i].length;
            } else if (ingredients[i].length != length) {
               CraftTweakerAPI.logError("Could not generate shaped objects as of different input lengths: " + length + ", " + ingredients[i].length);
               return new Object[0];
            }

            for(int j = 0; j < ingredients[i].length; ++j) {
               inputs.put(chars[i][j], getObject(ingredients[i][j]));
            }
         }

         String[] strs = new String[ingredients.length];

         for(int i = 0; i < ingredients.length; ++i) {
            switch (length) {
               case 2:
                  strs[i] = "" + chars[i][0] + chars[i][1];
                  break;
               case 3:
                  strs[i] = "" + chars[i][0] + chars[i][1] + chars[i][2];
                  break;
               default:
                  strs[i] = "" + chars[i][0];
            }
         }

         List<Object> list = new ArrayList<>(Arrays.asList(strs));

         for(Character chr : inputs.keySet()) {
            list.add(chr);
            list.add(inputs.get(chr));
         }

         return list.toArray(new Object[0]);
      } else {
         CraftTweakerAPI.logError("Could not generate shaped objects as of invalid input length: " + ingredients.length);
         return new Object[0];
      }
   }

   public static boolean matchesForRemoval(Object obj, ItemStack stack) {
      if (stack == null) {
         return false;
      } else if (obj instanceof ItemStack) {
         if (((ItemStack)obj).isEmpty() && stack.isEmpty()) {
            return true;
         } else {
            return ((ItemStack)obj).getItem() == stack.getItem() && (((ItemStack)obj).getMetadata() == stack.getMetadata() || ((ItemStack)obj).getMetadata() == 32767) && ItemStack.areItemStackTagsEqual((ItemStack)obj, stack);
         }
      } else if (obj instanceof Item) {
         return obj == stack.getItem();
      } else if (obj instanceof Block) {
         return Item.getItemFromBlock((Block)obj) == stack.getItem();
      } else if (obj instanceof String) {
         for(ItemStack entry : OreDictionary.getOres((String)obj)) {
            if (matchesForRemoval(entry, stack)) {
               return true;
            }
         }

         return false;
      } else if (obj instanceof List) {
         for(Object entry : (List<?>)obj) {
            if (matchesForRemoval(entry, stack)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static boolean matchesForRemoval(Object obj, Iterable<ItemStack> stacks) {
      for(ItemStack stack : stacks) {
         if (matchesForRemoval(obj, stack)) {
            return true;
         }
      }

      return false;
   }

   public static boolean matchesForRemoval(Object obj, ItemStack[] stacks) {
      for(ItemStack stack : stacks) {
         if (matchesForRemoval(obj, stack)) {
            return true;
         }
      }

      return false;
   }

   public static <T> boolean matchesForRemoval(Object obj, Iterable<T> stacks, Function<T, ItemStack> action) {
      for(T stack : stacks) {
         if (matchesForRemoval(obj, action.apply(stack))) {
            return true;
         }
      }

      return false;
   }

   public static <T> boolean matchesForRemovalArray(Object obj, Iterable<T> stacks, Function<T, ItemStack[]> action) {
      for(T stack : stacks) {
         if (matchesForRemoval(obj, action.apply(stack))) {
            return true;
         }
      }

      return false;
   }

   public static <T> boolean matchesForRemovalFlat(Object obj, Iterable<T> stacks, Function<T, Iterable<ItemStack>> action) {
      for(T stack : stacks) {
         if (matchesForRemoval(obj, action.apply(stack))) {
            return true;
         }
      }

      return false;
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
         ItemStack stack = InputHelper.toStack((IItemStack)obj);
         return new CraftingPart(Ingredient.fromStacks(stack), stack.getCount());
      } else if (obj instanceof IngredientStack && obj.getInternal() instanceof IItemStack) {
         ItemStack stack = InputHelper.toStack((IItemStack)obj.getInternal());
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

            stacks2.add(InputHelper.toStack(stack));
         }

         if (amount < 0) {
            amount = 1;
         }

         return new CraftingPart(Ingredient.fromStacks(stacks2.toArray(new ItemStack[0])), amount);
      }
   }

   public static CraftingPart[] getParts(IIngredient[] obj) {
      CraftingPart[] parts = new CraftingPart[obj.length];

      for(int i = 0; i < obj.length; ++i) {
         parts[i] = getPart(obj[i]);
      }

      return parts;
   }
}
