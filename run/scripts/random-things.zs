#modloaded randomthings
import mods.roidtweaker.randomthings.Imbuing;
import mods.roidtweaker.randomthings.GoldenChicken;

Imbuing.removeRecipe(1); //Fire recipe since <mossy:cobblestone> hasn't been removed yet
Imbuing.removeRecipe(<minecraft:mossy_cobblestone>);
//Imbuing.clearRecipes();
Imbuing.addRecipe(<minecraft:diamond_sword>.withDamage(50), <minecraft:diamond_sword>, [<minecraft:gold_ingot>, <minecraft:gold_ingot>, null]);

//Removes the first recipe, in this case the only default ore -> ingot
GoldenChicken.removeRecipe(0);
GoldenChicken.addRecipe(<ore:ingotIron>, <minecraft:gold_ingot>, 100);
GoldenChicken.addRecipe(<ore:ingotGold>, <minecraft:diamond>, 100);