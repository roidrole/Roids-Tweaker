#modloaded thermalexpansion
import mods.thermalexpansion.Collector;
import mods.thermalexpansion.Extruder;
import mods.thermalexpansion.Fisher;
import mods.thermalexpansion.InductionSmelter;
import mods.thermalexpansion.Pulverizer;
import mods.thermalexpansion.RedstoneFurnace;
import mods.thermalexpansion.Tapper;

Collector.addCatalyst(<minecraft:fish>, 500, 500);
Collector.removeCatalyst(<minecraft:soul_sand>);

Extruder.addRecipeIgneous(<minecraft:fish>, 500, 500, 500);
Extruder.addRecipeSedimentary(<minecraft:soul_sand>, 500, 500, 500);
Extruder.removeRecipeIgneous(<minecraft:cobblestone>);
Extruder.removeRecipeSedimentary(<minecraft:sand>);

Fisher.addFish(<minecraft:sand>, 3);
Fisher.removeFish(<minecraft:fish>);
Fisher.addBait(<minecraft:soul_sand>, 2);
Fisher.removeBait(<thermalfoundation:bait>);

InductionSmelter.addOreOverride(<ore:blockGlassHardened>, true);

Pulverizer.addOreOverride(<minecraft:glass>, true);

RedstoneFurnace.addOreOverride(<minecraft:sand>, true);
RedstoneFurnace.addFoodOverride(<minecraft:sand>, true);

//Will override the output for oak
Tapper.addRecipe(<liquid:water> * 50, <blockstate:minecraft:log>, []);
//Will add leaves for spruce
Tapper.addRecipe(<liquid:water> * 50, <blockstate:minecraft:diamond_block>, []);