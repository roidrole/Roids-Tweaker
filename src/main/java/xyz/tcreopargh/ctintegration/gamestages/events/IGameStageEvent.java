package xyz.tcreopargh.ctintegration.gamestages.events;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IPlayerEvent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.tcreopargh.ctintegration.CTIntegration;

@ZenRegister
@ZenClass(CTIntegration.CT_PACKAGE + "gamestages.IGameStageEvent")
@ModOnly("gamestages")
@SuppressWarnings("unused")
public interface IGameStageEvent extends IPlayerEvent {
    @ZenGetter("gameStage")
    @ZenMethod
    String getGameStage();
}
