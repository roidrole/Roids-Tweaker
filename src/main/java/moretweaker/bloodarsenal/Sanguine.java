package moretweaker.bloodarsenal;

import arcaratus.bloodarsenal.recipe.SanguineInfusionRecipeRegistry;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenClass("moretweaker.bloodarsenal.Sanguine")
@ZenRegister
@ModOnly("bloodarsenal")
@SuppressWarnings("unused")
public class Sanguine {
   @ZenProperty
   public static final String BAD_POTION = "bad_potion";
   @ZenProperty
   public static final String BLOOD_LUST = "bloodlust";
   @ZenProperty
   public static final String FLAME = "flame";
   @ZenProperty
   public static final String SHARPNESS = "sharpness";
   @ZenProperty
   public static final String FORTUNATE = "fortunate";
   @ZenProperty
   public static final String LOOTING = "looting";
   @ZenProperty
   public static final String SILKY = "silky";
   @ZenProperty
   public static final String SMELTING = "smelting";
   @ZenProperty
   public static final String EXPERIENCED = "xperienced";
   @ZenProperty
   public static final String BENEFICIAL_POTION = "beneficial_potion";
   @ZenProperty
   public static final String QUICK_DRAW = "quick_draw";
   @ZenProperty
   public static final String SHADOW_TOOL = "shadow_tool";
   @ZenProperty
   public static final String AOD = "aod";
   @ZenProperty
   public static final String SIGIL = "sigil";

   private Sanguine() {
   }

   @ZenMethod
   @SuppressWarnings("unchecked")
   public static void addRecipe(IItemStack output, final int lpCost, IItemStack catalyst, IIngredient[] inputs) {
      if (inputs.length > 8) {
         CraftTweakerAPI.logError("Sanguine Recipes: 8 Inputs maximum, provided " + inputs.length);
      } else {
         final ItemStack out = CraftTweakerMC.getItemStack(output);
         final ItemStack c = CraftTweakerMC.getItemStack(catalyst);
         Object[] in = BloodArsenalUtils.toBAArray(Inputs.getObjects(inputs));
         int[] ca = BloodArsenalUtils.amountArray(inputs);
         final Pair<Object, Integer>[] in2 = new Pair[in.length];

         for(int i = 0; i < in.length; ++i) {
            in2[i] = Pair.of(in[i], ca[i]);
         }

         MoreTweaker.queuePostInitAction(new IAction() {
            public void apply() {
               SanguineInfusionRecipeRegistry.registerSanguineInfusionRecipe(out, lpCost, c, in2);
            }

            public String describe() {
               return "Adds a Sanguine-Recipe";
            }
         });
      }
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            SanguineInfusionRecipeRegistry.getInfusionRecipes().removeIf((r) -> !r.isModifier() && Inputs.matchesForRemoval(out, r.getOutput()));
         }

         public String describe() {
            return "Removes Sanguine-Recipes";
         }
      });
   }

   @ZenMethod
   @SuppressWarnings("unchecked")
   public static void addModifier(final String modifier, final int lpCost, IIngredient[] inputs) {
      if (inputs.length > 8) {
         CraftTweakerAPI.logError("Sanguine Recipes: 8 Inputs maximum, provided " + inputs.length);
      } else {
         Object[] in = BloodArsenalUtils.toBAArray(Inputs.getObjects(inputs));
         int[] ca = BloodArsenalUtils.amountArray(inputs);
         final Pair<Object, Integer>[] in2 = new Pair[in.length];

         for(int i = 0; i < in.length; ++i) {
            in2[i] = Pair.of(in[i], ca[i]);
         }

         MoreTweaker.queuePostInitAction(new IAction() {
            public void apply() {
               SanguineInfusionRecipeRegistry.registerModificationRecipe(lpCost, modifier, 1, in2);
            }

            public String describe() {
               return "Adds a Sanguine-Recipe";
            }
         });
      }
   }

   @ZenMethod
   public static void removeModifier(final String modifier) {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            SanguineInfusionRecipeRegistry.getInfusionRecipes().removeIf((r) -> r.isModifier() && r.getModifierKey().equalsIgnoreCase(modifier));
         }

         public String describe() {
            return "Removes Sanguine-Recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            SanguineInfusionRecipeRegistry.getInfusionRecipes().clear();
         }

         public String describe() {
            return "Removes all Sanguine recipes";
         }
      });
   }
}
