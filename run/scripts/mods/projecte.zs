#modloaded projecte

import mods.ctintegration.projecte.FuelManager;
import mods.ctintegration.projecte.EMCManager;


print("emc: " + <minecraft:iron_ingot>.emc); // Prints 0
<minecraft:iron_ingot>.emc = 1111;
print("emc: " + <minecraft:iron_ingot>.emc); // Prints 0, but the emc value will be set to 1111 if you join a world

EMCManager.mapEMC();
print("emc: " + <minecraft:gold_ingot>.emc); // Prints 2048, which is the default value for gold ingot
<minecraft:gold_ingot>.emc = 2222;
print("emc: " + <minecraft:gold_ingot>.emc); // Prints 2048, but the emc value will be set to 2222 if you join a world

EMCManager.mapEMC();
print("emc: " + <minecraft:diamond>.emc); // Prints 4096, which is the default value for gold ingot
<minecraft:diamond>.emc = 3333;
EMCManager.mapEMC(<minecraft:diamond>); // Note: this method is way faster than a full map (between 100-∞x faster in vanilla). Recommended (full map included for backwards compatibility)
print("emc: " + <minecraft:diamond>.emc); // diamond
