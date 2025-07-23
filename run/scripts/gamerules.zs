import crafttweaker.world.IWorld;
import crafttweaker.event.PlayerLoggedInEvent;
import mods.ctutils.world.IGameRules;

events.onPlayerLoggedIn(function(event as PlayerLoggedInEvent){
    val thisGR as IGameRules = event.player.world.gameRules;
    thisGR.setOrCreateGameRule("Gamerule nobody-cares", "1");
    print(thisGR.getInt("Gamerule nobody-cares"));
});