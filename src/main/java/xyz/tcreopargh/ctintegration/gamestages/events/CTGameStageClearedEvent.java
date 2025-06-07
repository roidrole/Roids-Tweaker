package xyz.tcreopargh.ctintegration.gamestages.events;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.darkhax.gamestages.event.GameStageEvent;
import stanhebben.zenscript.annotations.ZenClass;
import xyz.tcreopargh.ctintegration.CTIntegration;

@ZenRegister
@ZenClass(CTIntegration.CTI_PACKAGE + "gamestages.GameStageClearedEvent")
@ModOnly("gamestages")
public class CTGameStageClearedEvent implements IPlayerEvent {

    private final GameStageEvent.Cleared event;

    public CTGameStageClearedEvent(GameStageEvent.Cleared event) {
        this.event = event;
    }
    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }

}
