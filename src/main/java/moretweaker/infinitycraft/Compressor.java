package moretweaker.infinitycraft;

import com.blamejared.mtlib.helpers.InputHelper;
import com.rowan662.infinitycraft.machines.CompressorRecipes;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import moretweaker.CraftingPart;
import moretweaker.Inputs;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.lang.reflect.Field;
import java.util.Map;

@ZenClass("moretweaker.infinitycraft.Compressor")
@ZenRegister
@ModOnly("infinitycraft")
public class Compressor {
   private static Field mapField = null;

   @ZenMethod
   public static void addRecipe(IIngredient input, IItemStack output, @Optional final int experience) {
      final CraftingPart in = Inputs.getPart(input);
      final ItemStack out = InputHelper.toStack(output);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            for(ItemStack stack : in.toStackList()) {
               CompressorRecipes.instance().addCompressingRecipe(stack, out, (float)experience);
            }

         }

         public String describe() {
            return "Adds some infinitycraft compressor recipes";
         }
      });
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            Compressor.getCompressorMap().values().removeIf((s) -> Inputs.matchesForRemoval(out, s));
         }

         public String describe() {
            return "Removes some infinitycraft compressor recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            Compressor.getCompressorMap().clear();
         }

         public String describe() {
            return "Removes all recipes for the infinitycraft Compressor";
         }
      });
   }

   private static Map<ItemStack, ItemStack> getCompressorMap() {
      try {
         if (mapField == null) {
            mapField = CompressorRecipes.class.getDeclaredField("compressingList");
            mapField.setAccessible(true);
         }

         return (Map<ItemStack, ItemStack>)mapField.get(CompressorRecipes.instance());
      } catch (ReflectiveOperationException e) {
         throw new RuntimeException(e);
      }
   }
}
