package moretweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.mc1120.actions.ActionSetTranslation;
import epicsquid.gadgetry.core.recipe.AlloyRecipe;
import epicsquid.gadgetry.machines.recipe.DistillingRecipe;
import epicsquid.gadgetry.machines.recipe.GrindingRecipe;
import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import fossilsarcheology.server.recipe.RecipeAnalyzer;
import moretweaker.mob_grinding_utils.SpawnEggHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import org.apache.logging.log4j.Logger;
import roidrole.roidtweaker.Tags;
import therealfarfetchd.quacklib.common.api.autoconf.DefaultFeatures;
import therealfarfetchd.quacklib.common.api.autoconf.FeatureManager;

import java.util.*;
import java.util.function.Predicate;

@Mod(
   modid = "moretweaker",
   name = "MoreTweaker",
   version = "1.0.19",
   dependencies = "required-after:crafttweaker;after:railcraft;after:draconicevolution@[2.3.17,);after:buildcraftcore@[7.99.24,);after:buildcraftsilicon@[7.99.24,);after:enderfuge;after:voidcraft;after:bloodarsenal@[1.12.2-2.1.0-25,);after:erebus@[1.0.20,);after:bedrockcraft@[1.0.1,);after:fossil@[8.0.1,);after:matteroverdrive;after:quacklib;after:infinitycraft;after:thebetweenlands;after:aoa3;after:bewitchment@[0.21.11,);after:hwell",
   acceptedMinecraftVersions = "[1.12.2]"
)
public class MoreTweaker {
   private static int next_id = 0;
   @Instance
   public static MoreTweaker instance;
   public static Logger logger;

   private static final Queue<IAction> postQueue = new LinkedList<>();

   @EventHandler
   public void preInit(FMLPreInitializationEvent event) {
      logger = event.getModLog();
      if (Loader.isModLoaded("quacklib")) {
         CraftTweakerAPI.logInfo("Enabling QuackLib alloy smelter");
         FeatureManager.INSTANCE.depend(DefaultFeatures.INSTANCE.getAlloyFurnace(), Tags.MOD_ID);
      }

   }

   @EventHandler
   public void init(FMLInitializationEvent event) {
      if (Loader.isModLoaded("mob_grinding_utils")) {
         MinecraftForge.EVENT_BUS.register(SpawnEggHandler.INSTANCE);
      }
   }

   @EventHandler
   public void postInit(FMLPostInitializationEvent event) {
      for(IAction action : postQueue) {
         CraftTweakerAPI.apply(action);
      }

      postQueue.clear();
      if (Loader.isModLoaded("gadgetrycore")) {
         CraftTweakerAPI.logInfo("Cleaning up Gadgetry-Core recipes");
         AlloyRecipe.metals.clear();
         AlloyRecipe.additives1.clear();
         AlloyRecipe.additives2.clear();

         for(AlloyRecipe alloyRecipe : AlloyRecipe.recipes) {
            AlloyRecipe.metals.add(alloyRecipe.inputs.get(0));
            AlloyRecipe.additives1.add(alloyRecipe.inputs.get(1));
            AlloyRecipe.additives2.add(alloyRecipe.inputs.get(2));
         }
      }

      if (Loader.isModLoaded("gadgetrymachines")) {
         CraftTweakerAPI.logInfo("Cleaning up Gadgetry-Machines recipes");
         DistillingRecipe.distillable_items.clear();
         DistillingRecipe.distillable_fluids.clear();

         for(DistillingRecipe distillingRecipe : DistillingRecipe.recipes) {
            DistillingRecipe.distillable_items.add(distillingRecipe.inputs.get(0));
            DistillingRecipe.distillable_fluids.add(distillingRecipe.fluid_in.getFluid());
         }

         GrindingRecipe.grindables.clear();

         for(GrindingRecipe grindingRecipe : GrindingRecipe.recipes) {
            GrindingRecipe.grindables.add(grindingRecipe.inputs.get(0));
         }
      }

      if (Loader.isModLoaded("fossil")) {
         CraftTweakerAPI.logInfo("Cleaning up Fossils Weights");
         Set<RecipeAnalyzer> newRecipes = new HashSet<>();
         Iterator<RecipeAnalyzer> itr = FAMachineRecipeRegistry.analyzerRecipes.iterator();

         while(itr.hasNext()) {
            RecipeAnalyzer recipe = itr.next();
            if (recipe.getTotalWeight() != 100.0F) {
               itr.remove();
               float factor = 100.0F / recipe.getTotalWeight();
               RecipeAnalyzer r = new RecipeAnalyzer(recipe.getInput());
               float weight = 0.0F;

               while(weight <= recipe.getTotalWeight()) {
                  Map.Entry<Float, ItemStack> entry = recipe.getDisplayMap().higherEntry(weight);
                  if (entry == null) {
                     break;
                  }

                  float chance = entry.getKey() - weight;
                  weight = entry.getKey();
                  r.addOutput(entry.getValue(), chance * factor);
               }

               newRecipes.add(r);
            }
         }

         FAMachineRecipeRegistry.analyzerRecipes.addAll(newRecipes);
         newRecipes = new HashSet<>();
         itr = FAMachineRecipeRegistry.sifterRecipes.iterator();

         while(itr.hasNext()) {
            RecipeAnalyzer recipe = itr.next();
            if (recipe.getTotalWeight() != 100.0F) {
               itr.remove();
               float factor = 100.0F / recipe.getTotalWeight();
               RecipeAnalyzer r = new RecipeAnalyzer(recipe.getInput());
               float weight = 0.0F;

               while(weight <= recipe.getTotalWeight()) {
                  Map.Entry<Float, ItemStack> entry = recipe.getDisplayMap().higherEntry(weight);
                  if (entry == null) {
                     break;
                  }

                  float chance = entry.getKey() - weight;
                  weight = entry.getKey();
                  r.addOutput(entry.getValue(), chance * factor);
               }

               newRecipes.add(r);
            }
         }

         FAMachineRecipeRegistry.sifterRecipes.addAll(newRecipes);
      }

   }

   public static void queuePostInitAction(IAction action) {
      postQueue.add(action);
   }

   public static ResourceLocation newRL() {
      return new ResourceLocation("moretweaker", "moretweakerrecipe" + next_id++);
   }

   public static <T extends IForgeRegistryEntry<T>> void forgeAdd(Class<T> registerClass, T entry) {
      IForgeRegistry<T> registry = GameRegistry.findRegistry(registerClass);
      if (entry.getRegistryName() == null) {
         entry.setRegistryName(newRL());
      }

      registry.register(entry);
   }

   public static <T extends IForgeRegistryEntry<T>> void forgeRemove(Class<T> registerClass, Predicate<T> removePredicate) {
      IForgeRegistry<T> registry = GameRegistry.findRegistry(registerClass);
      if(!(registry instanceof IForgeRegistryModifiable)){
         return;
      }

      List<ResourceLocation> removes = new ArrayList<>();

      for(T entry : registry.getValuesCollection()) {
          if (removePredicate.test(entry)) {
             removes.add(entry.getRegistryName());
          }
      }

      for(ResourceLocation remove : removes){
         try {
            ((IForgeRegistryModifiable<T>) registry).remove(remove);
		 } catch (Exception e) {
			 CraftTweakerAPI.logError(Tags.MOD_ID + " can't remove elements from registry " + registerClass.getCanonicalName() + ". Please report to " + Tags.MOD_ID + " author");
		 }
      }
   }

   public static void injectTranslation(String key, String value) {
      CraftTweakerAPI.apply(new ActionSetTranslation(null, key, value));
   }
}
