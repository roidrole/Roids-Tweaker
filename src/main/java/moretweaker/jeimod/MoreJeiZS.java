package moretweaker.jeimod;

import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IIngredientType;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.plugins.jei.info.IngredientInfoRecipe;
import mezz.jei.plugins.jei.info.IngredientInfoRecipeCategory;
import moretweaker.Inputs;
import moretweaker.jei.MoreJei;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ZenClass("moretweaker.jei.MoreJei")
@ZenRegister
@ModOnly("jei")
public class MoreJeiZS {
   private static Field ingredientType;
   private static Field ingredients;

   @ZenMethod
   public static void removeDescription(IIngredient input) {
      final Object obj = Inputs.getObject(input);
      MoreJei.queueJeiRuntimeAction(new IAction() {
         public void apply() {
            MoreJei.run((runtime) -> {
               IngredientInfoRecipeCategory category = (IngredientInfoRecipeCategory)runtime.getRecipeRegistry().getRecipeCategory("jei.information");
               if (category != null) {
                  List<IRecipeWrapper> removal = new ArrayList<>();
                  runtime.getRecipeRegistry().getRecipeWrappers(category).forEach((wrapper) -> {
                     Pair<IIngredientType<?>, List<ItemStack>> properties = MoreJeiZS.getProperties(wrapper);
                     if (properties != null && properties.getKey() == VanillaTypes.ITEM && Inputs.matchesForRemoval(obj, properties.getValue())) {
                        removal.add(wrapper);
                     }

                  });
                  removal.forEach((wrapper) -> runtime.getRecipeRegistry().hideRecipe(wrapper, "jei.information"));
               }

            });
         }

         public String describe() {
            return "Removes JEI descriptions";
         }
      });
   }

   @ZenMethod
   public static void addDescription(IIngredient input, final String[] description) {
      final List<ItemStack> stacks = Arrays.asList(Inputs.getStackList(input));
      MoreJei.queueJeiRuntimeAction(new IAction() {
         public void apply() {
            MoreJei.run((runtime) -> {
               List<IngredientInfoRecipe<ItemStack>> wrappers = IngredientInfoRecipe.create(MoreJei.getHelpers().getGuiHelper(), stacks, VanillaTypes.ITEM, description);
               wrappers.forEach((wrapper) -> runtime.getRecipeRegistry().addRecipe(wrapper, "jei.information"));
            });
         }

         public String describe() {
            return "Removes JEI descriptions";
         }
      });
   }

   private static Pair<IIngredientType<?>, List<ItemStack>> getProperties(IngredientInfoRecipe<?> recipe) {
      try {
         if (ingredientType == null || ingredients == null) {
            ingredientType = IngredientInfoRecipe.class.getDeclaredField("ingredientType");
            ingredients = IngredientInfoRecipe.class.getDeclaredField("ingredients");
            ingredientType.setAccessible(true);
            ingredients.setAccessible(true);
         }

         return Pair.of((IIngredientType<?>) ingredientType.get(recipe), (List<ItemStack>)ingredients.get(recipe));
      } catch (ReflectiveOperationException var2) {
         return null;
      }
   }
}
