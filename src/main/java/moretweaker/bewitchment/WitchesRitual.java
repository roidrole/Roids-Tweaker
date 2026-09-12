package moretweaker.bewitchment;

import com.bewitchment.api.registry.Ritual;
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
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ZenClass("moretweaker.bewitchment.WitchesRitual")
@ZenRegister
@ModOnly("bewitchment")
public class WitchesRitual {
   @ZenProperty
   public static final int NONE = -1;
   @ZenProperty
   public static final int GOLDEN = 0;
   @ZenProperty
   public static final int RITUAL = 1;
   @ZenProperty
   public static final int FIERY = 2;
   @ZenProperty
   public static final int PHASING = 3;
   @ZenProperty
   public static final int ANY = 4;

   @ZenMethod
   public static void addRecipe(String name, IItemStack[] outputs, IIngredient[] inputs, final String entityOutput, final String entityInput, final int power, final int ringSmall, final int ringMedium, final int ringLarge) {
      if (outputs.length > 5) {
         CraftTweakerAPI.logError("Witches rituals may not have more than 5 outputs.");
      }

      if (inputs.length > 10) {
         CraftTweakerAPI.logError("Witches rituals may not have more than 10 inputs.");
      }

      if (outputs.length <= 5 && inputs.length <= 10) {
         final List<ItemStack> out = Arrays.asList(CraftTweakerMC.getItemStacks(outputs));
         final List<Ingredient> in = Arrays.stream(Inputs.getParts(inputs)).map((c) -> c.ingredient).collect(Collectors.toList());
         final ResourceLocation rl = MoreTweaker.newRL();
         MoreTweaker.injectTranslation("ritual." + rl.getNamespace() + "." + rl.getPath(), name);
         MoreTweaker.queuePostInitAction(new IAction() {
            public void apply() {
               MoreTweaker.forgeAdd(Ritual.class, new MoreRitual(rl, in, out, entityInput, entityOutput, power, ringSmall, ringMedium, ringLarge));
            }

            public String describe() {
               return "Adds a recipe to the witches ritual list.";
            }
         });
      }
   }

   @ZenMethod
   public static void removeByOutput(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeRemove(Ritual.class, (r) -> Inputs.matchesForRemoval(out, r.output));
         }

         public String describe() {
            return "Removes some recipes from the witches ritual list by item output.";
         }
      });
   }

   @ZenMethod
   public static void removeByInput(IIngredient input) {
      final Object in = Inputs.getObject(input);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeRemove(Ritual.class, (r) -> Inputs.matchesForRemovalArray(in, r.input, Ingredient::getMatchingStacks));
         }

         public String describe() {
            return "Removes some recipes from the witches ritual list by item input.";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeRemove(Ritual.class, (r) -> true);
         }

         public String describe() {
            return "Removes all recipes from the witches ritual list.";
         }
      });
   }
}
