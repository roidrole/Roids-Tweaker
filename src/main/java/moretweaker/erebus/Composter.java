package moretweaker.erebus;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import erebus.recipes.ComposterRegistry;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ZenClass("moretweaker.erebus.Composter")
@ZenRegister
@ModOnly("erebus")
public class Composter {
   private static Field whiteListField = null;
   private static Field blackListField = null;

   @ZenMethod
   public static void add(IItemStack compostable) {
      final ItemStack c = CraftTweakerMC.getItemStack(compostable);
      c.setCount(1);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Composter.getWhitelist().add(c);
            Composter.getBlacklist().removeIf((stack) -> Composter.isMatching(stack, c));
         }

         public String describe() {
            return "Adds an item to the composter.";
         }
      });
   }

   @ZenMethod
   public static void addAll(IItemStack[] compostables) {
      ItemStack[] c = CraftTweakerMC.getItemStacks(compostables);
      final ArrayList<ItemStack> all = new ArrayList<>();
      Collections.addAll(all, c);
      all.forEach((stack) -> stack.setCount(1));
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Composter.getWhitelist().addAll(all);
            Composter.getBlacklist().removeIf((stack) -> Composter.hasOneMatching(stack, all));
         }

         public String describe() {
            return "Adds some items to the composter.";
         }
      });
   }

   @ZenMethod
   public static void remove(IItemStack nonCompostable) {
      final ItemStack c = CraftTweakerMC.getItemStack(nonCompostable);
      c.setCount(1);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Composter.getBlacklist().add(c);
            Composter.getWhitelist().removeIf((stack) -> Composter.isMatching(stack, c));
         }

         public String describe() {
            return "Removes an item from the composter.";
         }
      });
   }

   @ZenMethod
   public static void removeAll(IItemStack[] nonCompostables) {
      ItemStack[] c = CraftTweakerMC.getItemStacks(nonCompostables);
      final ArrayList<ItemStack> all = new ArrayList<>();
      Collections.addAll(all, c);
      all.forEach((stack) -> stack.setCount(1));
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Composter.getBlacklist().addAll(all);
            Composter.getWhitelist().removeIf((stack) -> Composter.hasOneMatching(stack, all));
         }

         public String describe() {
            return "Removes some items from the composter.";
         }
      });
   }

   private static boolean hasOneMatching(ItemStack in, List<ItemStack> matcher) {
      for(ItemStack stack : matcher) {
         if (isMatching(in, stack)) {
            return true;
         }
      }

      return false;
   }

   private static boolean isMatching(ItemStack in, ItemStack matcher) {
      return ItemStack.areItemsEqual(in, matcher) && (in.getMetadata() == matcher.getMetadata() || 32767 == matcher.getMetadata());
   }

   private static ArrayList<ItemStack> getWhitelist() {
      try {
         if (whiteListField == null) {
            whiteListField = ComposterRegistry.class.getDeclaredField("registry");
            whiteListField.setAccessible(true);
         }

         return (ArrayList<ItemStack>)whiteListField.get((Object)null);
      } catch (ClassCastException | ReflectiveOperationException e) {
         CraftTweakerAPI.logError("Could not get mutable list for erebus composter whitelist: " + e.getMessage());
         throw new RuntimeException("Could not get mutable list for erebus composter whitelist: " + e.getMessage(), e);
      }
   }

   private static ArrayList<ItemStack> getBlacklist() {
      try {
         if (blackListField == null) {
            blackListField = ComposterRegistry.class.getDeclaredField("blacklist");
            blackListField.setAccessible(true);
         }

         return (ArrayList<ItemStack>)blackListField.get((Object)null);
      } catch (ClassCastException | ReflectiveOperationException e) {
         CraftTweakerAPI.logError("Could not get mutable list for erebus composter blacklist: " + e.getMessage());
         throw new RuntimeException("Could not get mutable list for erebus composter blacklist: " + e.getMessage(), e);
      }
   }
}
