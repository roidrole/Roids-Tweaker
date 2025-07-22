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
import roidrole.roidtweaker.mods.forge.firstjoin.PlayerFirstJoinEvent;
import roidrole.roidtweaker.mods.immersiveengineering.GardenClocheCommand;
import roidrole.roidtweaker.mods.immersiveengineering.SlagReplacer;
import roidrole.roidtweaker.mods.minecraft.anvil.AnvilListener;
import roidrole.roidtweaker.mods.minecraft.villager.Villager;
import roidrole.roidtweaker.mods.minecraft.villager.VillagerCommand;
import roidrole.roidtweaker.proxy.CommonProxy;
import roidrole.roidtweaker.utils.DeferredLoader;
import xyz.tcreopargh.ctintegration.CTIntegration;

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
    public static final String CT_PACKAGE = "mods.roidtweaker.";

    @SidedProxy(clientSide = "roidrole.roidtweaker.proxy.ClientProxy", serverSide = "roidrole.roidtweaker.proxy..CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    @SuppressWarnings("unused")
    public void preInit(FMLPreInitializationEvent event) {
        CTIntegration.preInit();
        CraftTweakerAPI.tweaker.getPreprocessorManager().registerPreprocessorAction("onside", OnSidePreprocessor::new);
    }

    @Mod.EventHandler
    @SuppressWarnings("unused")
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(AnvilListener.class);
        if(RoidTweakerConfig.mixinCategory.allowPlayerFirstJoinEvent) {
            MinecraftForge.EVENT_BUS.register(PlayerFirstJoinEvent.EventFirer.class);
            MinecraftForge.EVENT_BUS.register(PlayerFirstJoinEvent.CTFirstJoin.class);
        }
        CTChatCommand.registerCommand(new VillagerCommand());
        if(Loader.isModLoaded("immersiveengineering")){
            CTChatCommand.registerCommand(new GardenClocheCommand());
        }
    }

    @Mod.EventHandler
    @SuppressWarnings("unused")
    public void postInit(FMLPostInitializationEvent event){
        DeferredLoader.postInit();
        if(RoidTweakerConfig.mixinCategory.villagerCategory.allowCustomProfessionSetter) {
            Villager.setAllowedProfessions();
        }
        if(Loader.isModLoaded("immersiveengineering")){
            SlagReplacer.postInit();
        }
    }

    @Mod.EventHandler
    @SuppressWarnings("unused")
    public void loadComplete(FMLLoadCompleteEvent event) {
        DeferredLoader.loadComplete();
    }
}
