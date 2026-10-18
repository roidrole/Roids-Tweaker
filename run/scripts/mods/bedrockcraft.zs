#modloaded bedrockcraft

import moretweaker.bedrockcraft.BedrockRitual;
import moretweaker.bedrockcraft.DustInfusion;

BedrockRitual.addRecipe(<minecraft:feather>, <minecraft:fire_charge>, 3, [<minecraft:golden_sword>, <minecraft:golden_sword>]);
BedrockRitual.removeRecipe(<bedrockcraft:void_component:*>);

DustInfusion.removeRecipe(<minecraft:redstone_block>);
DustInfusion.addRecipe(<minecraft:redstone_block>, <minecraft:diamond_block>);