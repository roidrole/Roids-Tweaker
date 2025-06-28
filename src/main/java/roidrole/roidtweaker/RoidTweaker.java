package roidrole.roidtweaker;

import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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
      + "required-after:jei;"
      + "required-after:crafttweaker;"
      + "required-after:mixinbooter;";
    public static final String CT_PACKAGE = "mods.roidtweaker.";

    @SidedProxy(clientSide = "roidrole.roidtweaker.proxy.ClientProxy", serverSide = "roidrole.roidtweaker.proxy..CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CTIntegration.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new AnvilListener());
        CTChatCommand.registerCommand(new VillagerCommand());
    }

    @Mod.EventHandler
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
    public void loadComplete(FMLLoadCompleteEvent event) {
        DeferredLoader.loadComplete();
    }
}
