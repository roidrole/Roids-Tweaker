package roidrole.roidtweaker.mods.forge.firstjoin;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventHandle;
import crafttweaker.api.event.IEventManager;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.util.EventList;
import crafttweaker.util.IEventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.HashSet;
import java.util.Set;

public class PlayerFirstJoinEvent extends PlayerEvent.PlayerLoggedInEvent {
    public static EventList<CTPlayerFirstJoinEvent> firstJoinEvents = new EventList<>();

    public PlayerFirstJoinEvent(EntityPlayer player) {
        super(player);
    }

    @ZenClass("mods.roidtweaker.events.CTPlayerFirstJoinEvent")
    @ZenRegister
    public static class CTPlayerFirstJoinEvent implements IPlayerEvent {
        PlayerFirstJoinEvent event;

        public CTPlayerFirstJoinEvent(PlayerFirstJoinEvent event){
            this.event = event;
        }
        @Override
        public IPlayer getPlayer() {
            return CraftTweakerMC.getIPlayer(event.player);
        }
    }

    @ZenExpansion("crafttweaker.events.IEventManager")
    @ZenRegister
    public static class CTFirstJoin{
        @ZenMethod
        @SuppressWarnings("unused")
        public static IEventHandle onPlayerFirstJoin(IEventManager manager, IEventHandler<CTPlayerFirstJoinEvent> event){
            return firstJoinEvents.add(event);
        }

        @SubscribeEvent
        public static void onPlayerFirstJoin(PlayerFirstJoinEvent event){
            firstJoinEvents.publish(new CTPlayerFirstJoinEvent(event));
        }
    }

    public static class EventFirer {
        public static Set<String> firstJoins = new HashSet<>();

        @SubscribeEvent(priority = EventPriority.LOW)
        public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
            if (firstJoins.contains(event.player.getName())) {
                firstJoins.remove(event.player.getName());
                if (!(event.player instanceof FakePlayer)) {
                    MinecraftForge.EVENT_BUS.post(new PlayerFirstJoinEvent(event.player));
                }
            }
        }
    }
}
