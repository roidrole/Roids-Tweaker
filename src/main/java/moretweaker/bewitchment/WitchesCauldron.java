package moretweaker.bewitchment;

import com.bewitchment.api.registry.Brew;
import com.bewitchment.api.registry.CauldronRecipe;
import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.bewitchment.WitchesCauldron")
@ZenRegister
@ModOnly("bewitchment")
public class WitchesCauldron {
   @ZenMethod
   public static void addRecipe(IItemStack[] outputs, IIngredient[] inputs) {
      if (outputs.length > 3) {
         CraftTweakerAPI.logError("Recipes for the witches cauldron may not have more than 3 outputs.");
      }

      if (inputs.length > 10) {
         CraftTweakerAPI.logError("Recipes for the witches cauldron may not have more than 10 inputs.");
      }

      if (outputs.length <= 3 && inputs.length <= 10) {
         final List<ItemStack> out = Arrays.asList(InputHelper.toStacks(outputs));
         final List<Ingredient> in = (List)Arrays.stream(Inputs.getParts(inputs)).map((c) -> c.ingredient).collect(Collectors.toList());
         MoreTweaker.queuePostInitAction(new IAction() {
            public void apply() {
               MoreTweaker.forgeAdd(CauldronRecipe.class, new CauldronRecipe(MoreTweaker.newRL(), in, out));
            }

            public String describe() {
               return "Adds a recipe to the witches cauldron.";
            }
         });
      }
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeRemove(CauldronRecipe.class, (r) -> Inputs.matchesForRemoval(out, (Iterable)r.output));
         }

         public String describe() {
            return "Removes some recipes from the witches cauldron.";
         }
      });
   }

   @ZenMethod
   public static void removeAllRecipes() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeRemove(CauldronRecipe.class, (r) -> true);
         }

         public String describe() {
            return "Removes all recipes from the witches cauldron.";
         }
      });
   }

   @ZenMethod
   public static void addBrew(IIngredient trigger, final String potion, @Optional(valueLong = 600L) final int duration, @Optional(valueLong = 0L) final int amplifier) {
      final Ingredient in = Inputs.getPart(trigger).ingredient;
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Potion p = Potion.getPotionFromResourceLocation(potion);
            if (p == null) {
               CraftTweakerAPI.logError("Could not find potion while trying to add a brew to the witches cauldron: " + potion);
            } else {
               MoreTweaker.forgeAdd(Brew.class, new Brew(MoreTweaker.newRL(), in, new PotionEffect(p, duration, amplifier)));
            }

         }

         public String describe() {
            return "Adds a brew to the witches cauldron.";
         }
      });
   }

   @ZenMethod
   public static void removeBrew(IIngredient trigger) {
      final Object in = Inputs.getObject(trigger);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            MoreTweaker.forgeRemove(Brew.class, (r) -> Inputs.matchesForRemoval(in, r.input.getMatchingStacks()));
         }

         public String describe() {
            return "Removes some brews from the witches cauldron.";
         }
      });
   }

   @ZenMethod
   public static void removeAllBrews() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeRemove(Brew.class, (r) -> true);
         }

         public String describe() {
            return "Removes all brews from the witches cauldron.";
         }
      });
   }
}
