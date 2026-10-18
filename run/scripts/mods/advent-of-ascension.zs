#modloaded aoa3

import moretweaker.aoa.DivineStation;
import moretweaker.aoa.InfusionTable;

//Divine Table
DivineStation.removeAll();
for i in 0 to 16 {
	DivineStation.addRecipe(<minecraft:wool>, <minecraft:dye>.definition.makeStack(i), <minecraft:wool>.definition.makeStack(i));
}
DivineStation.removeRecipe(<minecraft:wool>.only(function(item){return item.damage < 5;}));
DivineStation.addRecipe(<minecraft:gold_ingot>, <minecraft:diamond>, <minecraft:wheat_seeds>);

//Infusion Table
InfusionTable.removeAll();
for i in 0 to 16 {
	InfusionTable.addRecipe(<minecraft:wool>.definition.makeStack(i), <minecraft:wool>, [<minecraft:dye>.definition.makeStack(i)]);
}
InfusionTable.removeRecipe(<minecraft:wool>.only(function(item){return item.damage < 5;}));
InfusionTable.addRecipe(<minecraft:gold_ingot>, <minecraft:iron_ingot>, [<minecraft:diamond>, <minecraft:wheat_seeds>]);

//Infusion Table Enchantments
InfusionTable.addRecipe("minecraft:protection", 3, [<minecraft:shield>], 3, 4, 5);
InfusionTable.addRecipe("minecraft:fire_protection", 3, [<minecraft:fire_charge>], 3, 4, 5);
InfusionTable.removeRecipe("minecraft:fire_protection");