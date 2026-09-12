package moretweaker.mob_grinding_utils;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.mob_grinding_utils.ChickenFeed")
@ZenRegister
@ModOnly("mob_grinding_utils")
@SuppressWarnings("unused")
public class ChickenFeed {
   @ZenMethod
   public static void setSpawnEgg(final String mobId, IItemStack spawnegg) {
      final ItemStack stack = CraftTweakerMC.getItemStack(spawnegg);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            SpawnEggHandler.INSTANCE.setSpawnEgg(mobId, stack);
         }

         public String describe() {
            return "Sets a custom SpawnEgg for the GM Chicken Feed";
         }
      });
   }

   @ZenMethod
   public static void resetSpawnEgg(final String mobId) {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            SpawnEggHandler.INSTANCE.setDefaultEgg(mobId);
         }

         public String describe() {
            return "Resets a SpawnEgg for the GM Chicken Feed";
         }
      });
   }
}
