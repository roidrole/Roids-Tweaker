package roidrole.roidtweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CTChatCommand;
import crafttweakerutils.preprocessors.OnSidePreprocessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import roidrole.roidtweaker.mods.baubles.BaubleEventHandler;
import roidrole.roidtweaker.mods.baubles.CTBaubleExpansion;
import roidrole.roidtweaker.mods.baubles.CTInjectableBauble;
import roidrole.roidtweaker.mods.immersiveengineering.GardenClocheCommand;
import roidrole.roidtweaker.mods.immersiveengineering.SlagReplacer;
import roidrole.roidtweaker.mods.minecraft.anvil.AnvilListener;
import roidrole.roidtweaker.mods.minecraft.villager.Villager;
import roidrole.roidtweaker.mods.minecraft.villager.VillagerCommand;
import roidrole.roidtweaker.proxy.CommonProxy;
import roidrole.roidtweaker.utils.DeferredLoader;
import xyz.tcreopargh.ctintegration.cot.BaubleItemRepresentation;
import xyz.tcreopargh.ctintegration.gamestages.events.EventsExpansion;

@Mod(
    modid = Tags.MOD_ID,
    name = Tags.MOD_NAME,
    version = Tags.VERSION,
    dependencies = RoidTweaker.DEPENDENCIES
)
public class RoidTweaker {
    public static final String DEPENDENCIES =
        "required-after:forge@[14.23.1,);"
      + "required-after:crafttweaker;"
      + "required-after:mixinbooter;"
      + "required-after:configanytime;";

    @SidedProxy(clientSide = "roidrole.roidtweaker.proxy.ClientProxy", serverSide = "roidrole.roidtweaker.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if(RoidTweakerConfig.eventCategory.allowAnvilRecipes) {
            MinecraftForge.EVENT_BUS.register(new AnvilListener());
        }
        if(RoidTweakerConfig.eventCategory.allowCustomBaubles) {
            MinecraftForge.EVENT_BUS.register(new BaubleEventHandler());
        }
        if (RoidTweakerConfig.eventCategory.allowGameStagesEvents) {
            MinecraftForge.EVENT_BUS.register(EventsExpansion.EventHandler.class);
        }

        CraftTweakerAPI.tweaker.getPreprocessorManager().registerPreprocessorAction("onside", OnSidePreprocessor::new);

        //Can't @ZenRegister because it throws a ClassNotFoundException, which CT catches and logs in full
        if(Loader.isModLoaded("baubles") && Loader.isModLoaded("contenttweaker")){
            CraftTweakerAPI.registerClass(BaubleItemRepresentation.class);
        }
        if(RoidTweakerConfig.eventCategory.allowCustomBaubles){
            CraftTweakerAPI.registerClass(CTInjectableBauble.class);
            CraftTweakerAPI.registerClass(CTBaubleExpansion.class);
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        CTChatCommand.registerCommand(new VillagerCommand());
        if(Loader.isModLoaded("immersiveengineering")){
            CTChatCommand.registerCommand(new GardenClocheCommand());
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        DeferredLoader.postInit();
        if(RoidTweakerConfig.mixinCategory.villagerCategory.replaceProfessionSetter) {
            Villager.setAllowedProfessions();
        }
        if(Loader.isModLoaded("immersiveengineering")){
            SlagReplacer.postInit();
        }
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        DeferredLoader.loadComplete();
    }
}
