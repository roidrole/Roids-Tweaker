#modloaded thebetweenlands

import moretweaker.betweenlands.Animator;
import moretweaker.betweenlands.Composter;
import moretweaker.betweenlands.DruidAltar;
import moretweaker.betweenlands.Mortar;
import moretweaker.betweenlands.Purifier;

Animator.addRecipe(<minecraft:golden_sword>, 5, 5, <minecraft:fire_charge>);
Animator.addRecipe(<minecraft:diamond_sword>, 5, 5, "minecraft:spider");

Animator.removeRecipe(<*>.only(function(stack){
	return stack.definition.id has <thebetweenlands:items_misc>.definition.id && stack.damage == 25;
}));
Animator.removeRecipe("thebetweenlands:sporeling");


Composter.addRecipe(<minecraft:golden_sword>, 10, 5);


DruidAltar.addRecipe(<minecraft:diamond>, [<minecraft:golden_sword>, <minecraft:golden_sword>, <minecraft:golden_sword>, <minecraft:golden_sword>]);
DruidAltar.removeRecipe(<thebetweenlands:swamp_talisman>);


Mortar.addRecipe(<minecraft:feather>, <minecraft:stick>);
Mortar.removeRecipe(<thebetweenlands:items_misc:27>);


Purifier.addRecipe(<ore:plankWood>, <minecraft:rotten_flesh>);
Purifier.removeRecipe(<thebetweenlands:cragrock>);