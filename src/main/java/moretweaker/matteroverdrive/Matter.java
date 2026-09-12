package moretweaker.matteroverdrive;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.oredict.IOreDictEntry;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.data.matter.ItemHandler;
import moretweaker.CraftingPart;
import moretweaker.Inputs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.HashSet;
import java.util.Set;

@ZenClass("moretweaker.matteroverdrive.Matter")
@ZenRegister
@ModOnly("matteroverdrive")
public class Matter {
   @ZenMethod
   public static void set(IIngredient input, final int matter) {
      final String oreIn;
      final CraftingPart in;
      if (input instanceof IOreDictEntry) {
         in = null;
         oreIn = ((IOreDictEntry)input).getName();
      } else {
         in = Inputs.getPart(input);
         oreIn = null;
      }

      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            if (oreIn != null) {
               MatterOverdrive.MATTER_REGISTRY.registerOre(oreIn, new ItemHandler(matter, true, Integer.MAX_VALUE));
            }

            if (in != null) {
               Set<Item> registered = new HashSet<>();

               for(ItemStack stack : in.toStackList()) {
                  if (!registered.contains(stack.getItem())) {
                     registered.add(stack.getItem());
                     MatterOverdrive.MATTER_REGISTRY.register(stack.getItem(), new ItemHandler(matter, true, Integer.MAX_VALUE));
                  }
               }
            }

         }

         public String describe() {
            return "Sets a matter value";
         }
      });
   }
}
