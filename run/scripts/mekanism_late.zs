#modloaded mekanism
import mods.mekatweaker.GasFactory;

//translation key is the same as the liquid, but prepended by gas.
GasFactory.createGas(<liquid:lava>).register();

GasFactory.createGas("helium17", "blocks/helium17").register();

var myGas = GasFactory.createGas(<liquid:lava>);
    myGas.setIcon("blocks/helium17");
    myGas.setTranslationKey("betterlava");
myGas.register();