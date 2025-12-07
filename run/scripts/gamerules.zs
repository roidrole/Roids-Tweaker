import crafttweaker.world.IWorld;
import crafttweaker.event.PlayerLoggedInEvent;
import mods.ctutils.world.IGameRules;

//Can't be done during load because it needs a world object
events.onPlayerLoggedIn(function(event as PlayerLoggedInEvent){
    val thisGR as IGameRules = event.player.world.gameRules;
    thisGR.setOrCreateGameRule("int_rule", "1");
    thisGR.setOrCreateGameRule("double rule", "1.005");
    print("int parsed as int: "+thisGR.getInt("int_rule"));
    print("double parsed as int: "+thisGR.getInt("double rule"));
    print("int parsed as double: "+thisGR.getDouble("int_rule"));
    print("double parsed as double: "+thisGR.getDouble("double rule"));
    print("Non-existant rule as double: "+thisGR.getDouble("not_a_rule_has_this_name"));
});