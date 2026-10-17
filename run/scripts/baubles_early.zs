#modloaded baubles contenttweaker
#loader contenttweaker
import mods.contenttweaker.VanillaFactory;
import crafttweaker.player.IPlayer;

val baubleTest = VanillaFactory.createBaubleItem("test_bauble");
baubleTest.rarity = "rare";
baubleTest.onWornTick = function(bauble, wearer) {
    if(wearer instanceof IPlayer) {
        var player as IPlayer = wearer;
        player.addExperience(1);
    }
};
baubleTest.onUnequipped = function(bauble, wearer) {
    if(wearer instanceof IPlayer) {
        var player as IPlayer = wearer;
        player.removeExperience(10);
    }
};
baubleTest.onEquipped = function(bauble, wearer) {
    if(wearer instanceof IPlayer) {
        var player as IPlayer = wearer;
        player.addExperience(10);
    }
};
//If bubbles is installed, custom types are supported
baubleTest.baubleType = "custom_type_1";
baubleTest.register();