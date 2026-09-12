package moretweaker.jei.erebus;

import com.google.common.collect.ImmutableList;
import erebus.ModBlocks;
import erebus.recipes.ComposterRegistry;
import erebus.recipes.OfferingAltarRecipe;
import erebus.recipes.SmoothieMakerRecipe;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import moretweaker.jei.MoreJeiPlugin;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class ErebusJei implements MoreJeiPlugin {
   public void register(IRecipeCategoryRegistration registry) {
      registry.addRecipeCategories(new OfferingCategory(registry.getJeiHelpers().getGuiHelper()), new CompostCategory(registry.getJeiHelpers().getGuiHelper()), new SmoothieCategory(registry.getJeiHelpers().getGuiHelper()));
   }

   public void register(IModRegistry registry) {
      registry.addRecipeCatalyst(new ItemStack(ModBlocks.ALTAR_OFFERING), "moretweaker.erebus.offering.recipes");
      registry.addRecipeCatalyst(new ItemStack(ModBlocks.COMPOSTER), "moretweaker.erebus.compost.recipes");
      registry.addRecipeCatalyst(new ItemStack(ModBlocks.SMOOTHIE_MAKER), "moretweaker.erebus.smoothie.recipes");
      registry.handleRecipes(OfferingAltarRecipe.class, OfferingWrapper::new, "moretweaker.erebus.offering.recipes");
      registry.handleRecipes(ItemStack.class, CompostWrapper::new, "moretweaker.erebus.compost.recipes");
      registry.handleRecipes(SmoothieMakerRecipe.class, SmoothieWrapper::new, "moretweaker.erebus.smoothie.recipes");
      registry.addRecipes(OfferingAltarRecipe.getRecipeList(), "moretweaker.erebus.offering.recipes");
      registry.addRecipes(SmoothieMakerRecipe.getRecipeList(), "moretweaker.erebus.smoothie.recipes");
      NonNullList<ItemStack> compost = NonNullList.create();
      IForgeRegistry<Item> itemRegistry = GameRegistry.findRegistry(Item.class);

      for(Item item : itemRegistry.getValuesCollection()) {
         CreativeTabs tab = item.getCreativeTab();
         if (tab != null) {
            item.getSubItems(tab, compost);
         }
      }

      compost.removeIf((stack) -> {
         ItemStack x = ComposterRegistry.isCompostable(stack);
         return x == null || x.isEmpty();
      });
      registry.addRecipes(compost, "moretweaker.erebus.compost.recipes");
   }

   public static List<List<ItemStack>> getStacksList(Object[] inputs, int sizeNeeded) {
      List<List<ItemStack>> in = new ArrayList<>();

      for(Object obj : inputs) {
         if (in.size() < sizeNeeded) {
            if (obj instanceof ItemStack) {
               in.add(ImmutableList.of((ItemStack)obj));
            } else if (!(obj instanceof List)) {
               if (obj instanceof String) {
                  in.add(OreDictionary.getOres((String)obj));
               }
            } else {
               List<ItemStack> stackList = new ArrayList<>();

               for(Object o : (List<?>)obj) {
                  if (o instanceof ItemStack) {
                     stackList.add((ItemStack)o);
                  }
               }

               in.add(stackList);
            }
         }
      }

      while(in.size() < sizeNeeded) {
         in.add(ImmutableList.of(ItemStack.EMPTY));
      }

      return in;
   }
}
