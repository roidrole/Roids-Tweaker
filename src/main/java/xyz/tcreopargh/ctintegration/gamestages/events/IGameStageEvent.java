package xyz.tcreopargh.ctintegration.gamestages.events;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IPlayerEvent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.ctintegration.gamestages.IGameStageEvent")
@ModOnly("gamestages")
@SuppressWarnings("unused")
public interface IGameStageEvent extends IPlayerEvent {
    @ZenGetter("gameStage")
    @ZenMethod
    String getGameStage();
}
