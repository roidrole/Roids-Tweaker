package moretweaker.cfb;

import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.CraftingPart;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.blay09.mods.cookingforblockheads.api.SinkHandler;
import net.blay09.mods.cookingforblockheads.api.ToastOutputHandler;
import net.blay09.mods.cookingforblockheads.registry.CookingRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import roidrole.roidtweaker.mixins.cookingforblockheads.CookingRegistryAccessor;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.cfb.KitchenAppliances")
@ZenRegister
@ModOnly("cookingforblockheads")
@SuppressWarnings("unused")
public class KitchenAppliances {
   @ZenMethod
   public static void addToasterRecipe(IItemStack output, IIngredient input) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      final CraftingPart in = Inputs.getPart(input);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            ToastOutputHandler handler = (stackx) -> out.copy();

            for(ItemStack stack : in.toStackList()) {
               CookingRegistry.addToastHandler(stack, handler);
            }

         }

         public String describe() {
            return "Adds some toaster recipes.";
         }
      });
   }

   @ZenMethod
   public static void removeToasterRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            CookingRegistryAccessor.getToastHandlers().entrySet().removeIf((th) -> th.getValue() instanceof ToastOutputHandler && Inputs.matchesForRemoval(out, ((ToastOutputHandler)th.getValue()).getToasterOutput(th.getKey().copy())));
         }

         public String describe() {
            return "Removes some toaster recipes.";
         }
      });
   }

   @ZenMethod
   public static void removeAllToasterRecipes() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            CookingRegistryAccessor.getToastHandlers().clear();
         }

         public String describe() {
            return "Removes all toaster recipes.";
         }
      });
   }

   @ZenMethod
   public static void addSinkRecipe(IItemStack output, IIngredient input) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      final CraftingPart in = Inputs.getPart(input);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            SinkHandler handler = (stackx) -> out.copy();

            for(ItemStack stack : in.toStackList()) {
               CookingRegistry.addSinkHandler(stack, handler);
            }

         }

         public String describe() {
            return "Adds some sink recipes.";
         }
      });
   }

   @ZenMethod
   public static void removeSinkRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            CookingRegistryAccessor.getSinkHandlers()
                .entrySet()
                .removeIf((th) ->
                    Inputs.matchesForRemoval(out, th.getValue().getSinkOutput(th.getKey().copy()))
                );
         }

         public String describe() {
            return "Removes some sink recipes.";
         }
      });
   }

   @ZenMethod
   public static void removeAllSinkRecipes() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            CookingRegistryAccessor.getSinkHandlers().clear();
         }

         public String describe() {
            return "Removes all sink recipes.";
         }
      });
   }

   @ZenMethod
   public static void addOvenRecipe(IItemStack output, IIngredient input) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      final CraftingPart in = Inputs.getPart(input);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            for(ItemStack stack : in.toStackList()) {
               CookingRegistry.addSmeltingItem(stack, out.copy());
            }

         }

         public String describe() {
            return "Adds some oven recipes.";
         }
      });
   }

   @ZenMethod
   public static void removeOvenRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            CookingRegistryAccessor.getOvenRecipes().values().removeIf((stack) -> Inputs.matchesForRemoval(out, stack));
         }

         public String describe() {
            return "Removes some oven recipes.";
         }
      });
   }

   @ZenMethod
   public static void removeAllOvenRecipes() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            CookingRegistryAccessor.getOvenRecipes().clear();
         }

         public String describe() {
            return "Removes all oven recipes.";
         }
      });
   }

   @ZenMethod
   public static void addFoodShapeless(IItemStack output, IIngredient[] inputs) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      final Object[] in = Inputs.getObjects(inputs);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            CookingRegistry.addFoodRecipe(new ShapelessOreRecipe(MoreTweaker.newRL(), out, in));
         }

         public String describe() {
            return "Adds a food recipe.";
         }
      });
   }

   @ZenMethod
   public static void addFoodShaped(IItemStack output, IIngredient[][] inputs) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      final Object[] in = Inputs.getShapedObjects(inputs);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            CookingRegistry.addFoodRecipe(new ShapedOreRecipe(MoreTweaker.newRL(), out, in));
         }

         public String describe() {
            return "Adds a food recipe.";
         }
      });
   }

   @ZenMethod
   public static void removeFoodRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            CookingRegistryAccessor.getRecipeList().removeIf((r) -> Inputs.matchesForRemoval(out, r.getRecipeOutput()));
         }

         public String describe() {
            return "Removes some food recipes.";
         }
      });
   }

   @ZenMethod
   public static void removeAllFoodRecipes() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            CookingRegistryAccessor.getRecipeList().clear();
         }

         public String describe() {
            return "Removes all food recipes.";
         }
      });
   }
}
