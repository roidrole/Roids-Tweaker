#modloaded immersiveengineering
import mods.immersiveengineering.ArcFurnace;
import mods.roidtweaker.immersiveengineering.GardenCloche;
import mods.roidtweaker.immersiveengineering.GardenClocheMultiplierFunction;
import mods.immersiveengineering.Blueprint;
import crafttweaker.item.IIngredient;
import crafttweaker.item.IItemStack;
import crafttweaker.data.IData;
import crafttweaker.world.IWorld;

ArcFurnace.addRecycling(<minecraft:golden_apple>);
ArcFurnace.removeRecyclingOutput(<minecraft:iron_ingot>);


Blueprint.addRecipe("my_awesome_category", <minecraft:iron_block>, (<minecraft:golden_sword> * 3).spread());
Blueprint.addBlueprint("super-super blueprint");
for type in Blueprint.getRegisteredBlueprints(){
    print(type);
}

Blueprint.addRecipe("super-super Blueprint", <minecraft:iron_block>, (<minecraft:golden_sword> * 3).spread());
Blueprint.addVillagerTrade("my_awesome_category", <minecraft:diamond_sword>);

GardenCloche.addFertilizer(<minecraft:fire_charge>, 0.1F);
GardenCloche.addFertilizer(<minecraft:iron_ingot>, function(fertilizer as IIngredient, seed as IItemStack, soil as IItemStack) as float{
    if(seed == <minecraft:reeds>){
        return 5.0f;
    }
    return 2.0f;
} as GardenClocheMultiplierFunction);
GardenCloche.removeFertilizer(<minecraft:dye:15>);

GardenCloche.addPlantHandler("super_cool");
GardenCloche.addCrop("crop", <immersiveengineering:metal>, [<minecraft:diamond>]);
GardenCloche.addCrop("stem", <minecraft:stick>, [<minecraft:diamond>], <minecraft:grass>);
GardenCloche.addCrop("stacking", <minecraft:iron_sword>, [<minecraft:diamond>], <minecraft:iron_block>, <minecraft:gold_block>.asBlock());
GardenCloche.addCrop("super_cool", <minecraft:golden_sword>, [<minecraft:diamond>], <minecraft:iron_block>, <blockstate:minecraft:log:variant=spruce>);
GardenCloche.removeCrop(<minecraft:wheat_seeds>);

GardenCloche.setSoilTexture(<minecraft:dirt>, "minecraft:blocks/chorus_plant"); //Full texture path is minecraft:textures/blocks/chorus_plant.png


mods.immersiveengineering.MetalPress.addRecipeNBT(<minecraft:iron_sword>, <minecraft:iron_ingot>.withTag({marker:1}), <minecraft:stick>, 4000);


mods.roidtweaker.immersiveengineering.SlagReplacer.setSlag(<minecraft:jungle_stairs>, <*>.only(
    function(stack as IItemStack) as bool{
        return stack.ores[0].name.startsWith("ingot");
    }
));
mods.roidtweaker.immersiveengineering.SlagReplacer.setSlag(<minecraft:fence>, <ore:crystalSlag>, "SLAG");
