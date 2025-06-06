package xyz.tcreopargh.ctintegration;

import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.tcreopargh.ctintegration.cot.BaubleEventHandler;
import xyz.tcreopargh.ctintegration.cot.BaubleItemRepresentation;
import xyz.tcreopargh.ctintegration.cot.BaubleVanillaFactoryExpansion;
import xyz.tcreopargh.ctintegration.gamestages.events.EventsExpansion;

@Mod(
        modid = Tags.MOD_ID,
        name = Tags.MOD_NAME,
        version = Tags.VERSION,
        dependencies = CTIntegrationMod.DEPENDENCIES
)
public class CTIntegrationMod {

    public static final String CT_NAMESPACE = "mods.ctintegration.";
    public static final String DEPENDENCIES = "required-after:crafttweaker";

    @Mod.Instance(Tags.MOD_ID)
    public static CTIntegrationMod INSTANCE;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        if (Loader.isModLoaded("gamestages")) {
            MinecraftForge.EVENT_BUS.register(EventsExpansion.EventHandler.class);
        }
        if (Loader.isModLoaded("contenttweaker") && Loader.isModLoaded("baubles")) {
            CraftTweakerAPI.registerClass(BaubleItemRepresentation.class);
            CraftTweakerAPI.registerClass(BaubleEventHandler.GetBaubleType.class);
            CraftTweakerAPI.registerClass(BaubleEventHandler.OnWornTick.class);
            CraftTweakerAPI.registerClass(BaubleEventHandler.CanEquip.class);
            CraftTweakerAPI.registerClass(BaubleEventHandler.canUnequip.class);
            CraftTweakerAPI.registerClass(BaubleEventHandler.OnEquipped.class);
            CraftTweakerAPI.registerClass(BaubleEventHandler.OnUnequipped.class);
            CraftTweakerAPI.registerClass(BaubleEventHandler.WillAutoSync.class);
            CraftTweakerAPI.registerClass(BaubleVanillaFactoryExpansion.class);
        }
    }
}
