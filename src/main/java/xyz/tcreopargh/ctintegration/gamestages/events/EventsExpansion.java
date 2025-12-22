package xyz.tcreopargh.ctintegration.gamestages.events;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventHandle;
import crafttweaker.api.event.IEventManager;
import crafttweaker.util.EventList;
import crafttweaker.util.IEventHandler;
import net.darkhax.gamestages.event.GameStageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import roidrole.roidtweaker.RoidTweakerConfig;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("crafttweaker.events.IEventManager")
@ZenRegister
@ModOnly("gamestages")
public class EventsExpansion {

    private static final EventList<CTGameStageAddEvent> stageAddEvents = new EventList<>();
    private static final EventList<CTGameStageRemoveEvent> stageRemoveEvents = new EventList<>();
    private static final EventList<CTGameStageAddedEvent> stageAddedEvents = new EventList<>();
    private static final EventList<CTGameStageRemovedEvent> stageRemovedEvents = new EventList<>();
    private static final EventList<CTGameStageClearedEvent> stageClearedEvents = new EventList<>();

    @ZenMethod
    public static IEventHandle onGameStageAdd(IEventManager manager, IEventHandler<CTGameStageAddEvent> event) {
        if(!RoidTweakerConfig.eventCategory.allowGameStagesEvents){
            CraftTweakerAPI.logError("Trying to add a GameStage event when GameStages event are turned off");
        }
        return stageAddEvents.add(event);
    }

    @ZenMethod
    public static IEventHandle onGameStageRemove(IEventManager manager, IEventHandler<CTGameStageRemoveEvent> event) {
        if(!RoidTweakerConfig.eventCategory.allowGameStagesEvents){
            CraftTweakerAPI.logError("Trying to add a GameStage event when GameStages event are turned off");
        }
        return stageRemoveEvents.add(event);
    }

    @ZenMethod
    public static IEventHandle onGameStageAdded(IEventManager manager, IEventHandler<CTGameStageAddedEvent> event) {
        if(!RoidTweakerConfig.eventCategory.allowGameStagesEvents){
            CraftTweakerAPI.logError("Trying to add a GameStage event when GameStages event are turned off");
        }
        return stageAddedEvents.add(event);
    }

    @ZenMethod
    public static IEventHandle onGameStageRemoved(IEventManager manager, IEventHandler<CTGameStageRemovedEvent> event) {
        if(!RoidTweakerConfig.eventCategory.allowGameStagesEvents){
            CraftTweakerAPI.logError("Trying to add a GameStage event when GameStages event are turned off");
        }
        return stageRemovedEvents.add(event);
    }

    @ZenMethod
    public static IEventHandle onGameStageCleared(IEventManager manager, IEventHandler<CTGameStageClearedEvent> event) {
        if(!RoidTweakerConfig.eventCategory.allowGameStagesEvents){
            CraftTweakerAPI.logError("Trying to add a GameStage event when GameStages event are turned off");
        }
        return stageClearedEvents.add(event);
    }


    public static final class EventHandler {
        @SubscribeEvent
        public static void onGameStageAdd(GameStageEvent.Add event) {
            if (stageAddEvents.hasHandlers()) {
                stageAddEvents.publish(new CTGameStageAddEvent(event));
            }
        }

        @SubscribeEvent
        public static void onGameStageRemove(GameStageEvent.Remove event) {
            if (stageRemoveEvents.hasHandlers()) {
                stageRemoveEvents.publish(new CTGameStageRemoveEvent(event));
            }
        }

        @SubscribeEvent
        public static void onGameStageAdded(GameStageEvent.Added event) {
            if (stageAddedEvents.hasHandlers()) {
                stageAddedEvents.publish(new CTGameStageAddedEvent(event));
            }
        }

        @SubscribeEvent
        public static void onGameStageRemoved(GameStageEvent.Removed event) {
            if (stageRemovedEvents.hasHandlers()) {
                stageRemovedEvents.publish(new CTGameStageRemovedEvent(event));
            }
        }

        @SubscribeEvent
        public static void onGameStageCleared(GameStageEvent.Cleared event) {
            if (stageClearedEvents.hasHandlers()) {
                stageClearedEvents.publish(new CTGameStageClearedEvent(event));
            }
        }
    }

}
