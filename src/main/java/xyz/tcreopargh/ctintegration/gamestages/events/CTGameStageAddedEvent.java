package xyz.tcreopargh.ctintegration.gamestages.events;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.darkhax.gamestages.event.GameStageEvent;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("mods.ctintegration.gamestages.GameStageAddedEvent")
@ModOnly("gamestages")
public class CTGameStageAddedEvent implements IGameStageEvent {

    private final GameStageEvent.Added event;

    public CTGameStageAddedEvent(GameStageEvent.Added event) {
        this.event = event;
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }

    @Override
    public String getGameStage() {
        return event.getStageName();
    }
}
