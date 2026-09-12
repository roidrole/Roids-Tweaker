package moretweaker.voidcraft;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import moretweaker.ReflectionHelper;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tamaized.voidcraft.common.capabilities.vadeMecum.IVadeMecumCapability;
import tamaized.voidcraft.common.machina.addons.TERecipesAlchemy;
import tamaized.voidcraft.registry.VoidCraftTERecipes;

@ZenClass("moretweaker.voidcraft.VoidAlchemy")
@ZenRegister
@ModOnly("voidcraft")
public class VoidAlchemy {
   @ZenMethod
   public static void add(IItemStack output, IIngredient[] inputs, @Optional(valueLong = 350L) final int voidicPower) {
      if (inputs.length != 6) {
         CraftTweakerAPI.logError("For VoidAlchemy: Less or more than  six ingredients specified");
      } else {
         final ItemStack out = InputHelper.toStack(output);
         final ItemStack[][] in = new ItemStack[][]{{ItemStack.EMPTY}, {ItemStack.EMPTY}, {ItemStack.EMPTY}, {ItemStack.EMPTY}, {ItemStack.EMPTY}, {ItemStack.EMPTY}};

         for(int i = 0; i < in.length; ++i) {
            in[i] = Inputs.getStackList(inputs[i]);
         }

         MoreTweaker.queuePostInitAction(new IAction() {
            public void apply() {
               for(ItemStack stack0 : in[0]) {
                  for(ItemStack stack1 : in[1]) {
                     for(ItemStack stack2 : in[2]) {
                        for(ItemStack stack3 : in[3]) {
                           for(ItemStack stack4 : in[4]) {
                              for(ItemStack stack5 : in[5]) {
                                 VoidCraftTERecipes.alchemy.registerRecipe(ReflectionHelper.createNested(TERecipesAlchemy.AlchemyRecipe.class, VoidCraftTERecipes.alchemy, new Class[]{IVadeMecumCapability.Category.class, ItemStack[].class, ItemStack.class, Integer.TYPE}, null, new ItemStack[]{stack0, stack1, stack2, stack3, stack4, stack5}, out, voidicPower));
                              }
                           }
                        }
                     }
                  }
               }

            }

            public String describe() {
               return "Adds VoidAlchemy-Recipes";
            }
         });
      }
   }

   @ZenMethod
   public static void remove(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            VoidCraftTERecipes.alchemy.getList().removeIf((r) -> Inputs.matchesForRemoval(out, r.getOutput()));
         }

         public String describe() {
            return "Removes VoidAlchemy-Recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            VoidCraftTERecipes.alchemy.getList().clear();
         }

         public String describe() {
            return "Removes all VoidAlchemy recipes";
         }
      });
   }
}
