import crafttweaker.item.IIngredient;
mods.roidtweaker.Anvil.addRecipe(<minecraft:stick>, <minecraft:carrot>, <minecraft:carrot_on_a_stick>, 3);
mods.roidtweaker.Anvil.remove(<minecraft:iron_sword>);
mods.roidtweaker.Anvil.addRepair(<minecraft:shield:*> as IIngredient, <minecraft:stone> as IIngredient, 20);
mods.roidtweaker.Anvil.addRepair(<minecraft:shield> as IIngredient, <ore:ingotIron> as IIngredient, 1 as float);