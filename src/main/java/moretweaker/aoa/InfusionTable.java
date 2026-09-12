package moretweaker.aoa;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import java.lang.reflect.Field;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.tslat.aoa3.crafting.recipes.InfusionTableRecipe;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.aoa.InfusionTable")
@ZenRegister
@ModOnly("aoa3")
public class InfusionTable {
   @ZenMethod
   public static void addRecipe(IItemStack output, IItemStack mainInput, IIngredient[] inputs, @Optional(valueLong = 1L) final int infusionLevel, @Optional(valueLong = 0L) final int xpMin, @Optional(valueLong = 0L) final int xpMax) {
      final ItemStack out = InputHelper.toStack(output);
      final ItemStack in1 = InputHelper.toStack(mainInput);
      if (inputs.length <= 0 || inputs.length > 9) {
         CraftTweakerAPI.logError("No or more than 9 inputs given for InfusionTable recipe");
      }

      final NonNullList<Ingredient> in2 = NonNullList.create();

      for(IIngredient ingredient : inputs) {
         in2.add(Inputs.getPart(ingredient).ingredient);
      }

      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeAdd(IRecipe.class, new InfusionTableRecipe("", out, in1, in2, infusionLevel, xpMin, xpMax));
         }

         public String describe() {
            return "Adds an AOA InfusionTable recipe with item output.";
         }
      });
   }

   @ZenMethod
   public static void addRecipe(final String enchantmentId, final int enchantmentLevel, IIngredient[] inputs, @Optional(valueLong = 1L) final int infusionLevel, @Optional(valueLong = 0L) final int xpMin, @Optional(valueLong = 0L) final int xpMax) {
      if (inputs.length <= 0 || inputs.length > 9) {
         CraftTweakerAPI.logError("No or more than 9 inputs given for InfusionTable recipe");
      }

      final NonNullList<Ingredient> in2 = NonNullList.create();

      for(IIngredient ingredient : inputs) {
         in2.add(Inputs.getPart(ingredient).ingredient);
      }

      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Enchantment ench = Enchantment.getEnchantmentByLocation(enchantmentId);
            if (ench == null) {
               CraftTweakerAPI.logError("No enchantment found for given key to create an AOA InfusionTable recipe");
            } else {
               MoreTweaker.forgeAdd(IRecipe.class, new InfusionTableRecipe("", ench, enchantmentLevel, in2, infusionLevel, xpMin, xpMax));
            }
         }

         public String describe() {
            return "Adds an AOA InfusionTable recipe with enchantment output.";
         }
      });
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeRemove(IRecipe.class, (r) -> r instanceof InfusionTableRecipe && !((InfusionTableRecipe)r).isEnchanting() && Inputs.matchesForRemoval(out, r.getRecipeOutput()));
         }

         public String describe() {
            return "Removes AOA InfusionTable recipes by item output.";
         }
      });
   }

   @ZenMethod
   public static void removeRecipe(final String enchantmentId) {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Enchantment ench = Enchantment.getEnchantmentByLocation(enchantmentId);
            if (ench == null) {
               CraftTweakerAPI.logError("No enchantment found for given key to remove AOA InfusionTables");
            } else {
               try {
                  Field field = InfusionTableRecipe.class.getDeclaredField("enchantment");
                  field.setAccessible(true);
                  MoreTweaker.forgeRemove(IRecipe.class, (r) -> {
                     try {
                        return r instanceof InfusionTableRecipe && ((InfusionTableRecipe)r).isEnchanting() && field.get(r) == ench;
                     } catch (IllegalAccessException e) {
                        CraftTweakerAPI.logError("Could not get enchntment field of InfusionTableRecipe class  to remove recipes: " + e.getMessage());
                        return false;
                     }
                  });
               } catch (ReflectiveOperationException e) {
                  CraftTweakerAPI.logError("Could not get enchntment field of InfusionTableRecipe class  to remove recipes: " + e.getMessage());
               }

            }
         }

         public String describe() {
            return "Removes AOA InfusionTable recipes by enchantment output.";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeRemove(IRecipe.class, (r) -> r instanceof InfusionTableRecipe);
         }

         public String describe() {
            return "Removes all recipes for the AOA InfusionTable";
         }
      });
   }
}
