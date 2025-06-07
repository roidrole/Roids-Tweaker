package xyz.tcreopargh.ctintegration.gamestages.events;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.darkhax.gamestages.event.GameStageEvent;
import stanhebben.zenscript.annotations.ZenClass;
import xyz.tcreopargh.ctintegration.CTIntegration;

@ZenRegister
@ZenClass(CTIntegration.CT_PACKAGE + "gamestages.GameStageRemovedEvent")
@ModOnly("gamestages")
public class CTGameStageRemovedEvent implements IGameStageEvent {

    private final GameStageEvent.Removed event;

    public CTGameStageRemovedEvent(GameStageEvent.Removed event) {
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
