#modloaded unidict
#loader afterunidict

import mods.roidtweaker.unidict.UniDict;

//Adds an ore + fire charge -> ingot recipe for every metal having ore and ingot
//Yes, you can do this natively, but this unlocks all recipes
val oreKind as int = UniDict.getKind("ore");
val ingotKind as int = UniDict.getKind("ingot");

for resource in UniDict.getResources([oreKind, ingotKind]){
    print(resource.name);
    recipes.addShapeless(resource.getStack(ingotKind), [resource[oreKind], <minecraft:fire_charge>]);
}