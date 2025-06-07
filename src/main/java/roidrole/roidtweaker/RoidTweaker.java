package roidrole.roidtweaker;

import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import rocks.gameonthe.rockytweaks.crafttweaker.merchant.MerchantCommand;
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

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        CTIntegration.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        CTChatCommand.registerCommand(new MerchantCommand());
    }
}
