package moretweaker.lightningcraft;

import crafttweaker.api.item.IIngredient;
import moretweaker.Inputs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class LUtil {
   private LUtil() {
   }

   public static Object getObject(IIngredient ingredient) {
      Object obj = Inputs.getObject(ingredient);
      if (!(obj instanceof ItemStack) && !(obj instanceof List)) {
         return obj instanceof String ? OreDictionary.getOres((String)obj) : ItemStack.EMPTY;
      } else {
         return obj;
      }
   }

   public static Object[] getObjects(IIngredient[] ingredients) {
      Object[] objects = new Object[ingredients.length];

      for(int i = 0; i < ingredients.length; ++i) {
         objects[i] = getObject(ingredients[i]);
      }

      return objects;
   }
}
