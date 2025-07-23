import crafttweaker.item.IIngredient;
mods.roidtweaker.minecraft.Anvil.addRecipe(<minecraft:stick>, <minecraft:carrot>, <minecraft:carrot_on_a_stick>, 3);
mods.roidtweaker.minecraft.Anvil.addRecipeShapeless(<minecraft:water_bucket>, <minecraft:lava_bucket>, <minecraft:obsidian>, 3);
mods.roidtweaker.minecraft.Anvil.remove(<minecraft:iron_sword>);
mods.roidtweaker.minecraft.Anvil.addRepair(<minecraft:shield:*> as IIngredient, <minecraft:stone> as IIngredient, 20);
mods.roidtweaker.minecraft.Anvil.addRepair(<minecraft:shield:*> as IIngredient, <ore:ingotIron> as IIngredient, 1 as float);