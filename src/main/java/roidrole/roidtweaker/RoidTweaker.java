package roidrole.roidtweaker;

import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import roidrole.roidtweaker.mods.minecraft.anvil.AnvilListener;
import roidrole.roidtweaker.mods.minecraft.villager.VillagerCommand;
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
      + "required-after:mtlib;"
      + "required-after:mixinbooter;";
    public static final String CT_PACKAGE = "mods.roidtweaker.";
    public static DeferredLoader loader = new DeferredLoader();

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
        loader.postInit();
    }
}
