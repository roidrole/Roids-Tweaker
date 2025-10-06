#modloaded unidict

//This loader is declared in ZenUtils config as the following line :
// afterunidict;unidict;I;after
#loader afterunidict

import mods.roidtweaker.unidict.UniDict;
import mods.roidtweaker.unidict.IResource;

//Adds an ore + fire charge -> ingot recipe for every metal having ore and ingot
//Yes, you can do this natively, but this unlocks all recipes
val oreKind as int = UniDict.getKind("ore");
val ingotKind as int = UniDict.getKind("ingot");

for resource in UniDict.getResources([oreKind, ingotKind]){
    print(resource.name);
    recipes.addShapeless(resource.getStack(ingotKind), [resource[oreKind], <minecraft:fire_charge>]);
}

val iron as IResource = UniDict.getResource("Iron");
print("All iron metal parts:");
for item in iron.stacks{
    print(item.commandString);
}