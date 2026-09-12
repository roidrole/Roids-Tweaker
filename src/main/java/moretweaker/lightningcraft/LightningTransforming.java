package moretweaker.lightningcraft;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import java.util.List;
import net.minecraft.item.ItemStack;
import sblectric.lightningcraft.recipes.LightningTransformRecipes;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.lightningcraft.LightningTransforming")
@ZenRegister
@ModOnly("lightningcraft")
public class LightningTransforming {
   @ZenMethod
   public static void add(IItemStack output, IItemStack[] inputs) {
      final ItemStack out = InputHelper.toStack(output);
      final List<ItemStack> in = InputHelper.toNonNullList(InputHelper.toStacks(inputs));
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            LightningTransformRecipes.instance().addRecipe(out, in);
         }

         public String describe() {
            return "Adds a LightningTransforming-Recipe";
         }
      });
   }
}
