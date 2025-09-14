#modloaded immersiveengineering
import mods.immersiveengineering.ArcFurnace;
import mods.roidtweaker.immersiveengineering.GardenCloche;
import mods.roidtweaker.immersiveengineering.GardenClocheMultiplierFunction;
import mods.roidtweaker.immersiveengineering.SlagReplacer;
import mods.immersiveengineering.Blueprint;
import mods.immersiveengineering.MineralMix;
import mods.immersiveengineering.Excavator;
import crafttweaker.item.IIngredient;
import crafttweaker.item.IItemStack;
import crafttweaker.data.IData;
import crafttweaker.world.IWorld;
import crafttweaker.world.IBlockPos;
import crafttweaker.recipes.IRecipeFunction;

//Arc Furnace
    ArcFurnace.addRecycling(<minecraft:golden_apple>);
    ArcFurnace.removeRecyclingOutput(<minecraft:iron_ingot>);
//Done!

//Blueprint
    Blueprint.addRecipe("my_awesome_category", <minecraft:iron_block>, (<minecraft:golden_sword> * 3).spread());
    Blueprint.addBlueprint("super-super blueprint");
    for type in Blueprint.getRegisteredBlueprints(){
        print(type);
    }

    Blueprint.addRecipe("super-super Blueprint", <minecraft:iron_block>, (<minecraft:golden_sword> * 3).spread());
    Blueprint.addVillagerTrade("my_awesome_category", <minecraft:diamond_sword>);
//Done!

//Garden Cloche
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
//Done!

//Metal Press
    mods.immersiveengineering.MetalPress.addRecipeNBT(<minecraft:iron_sword>, <minecraft:iron_ingot>.withTag({marker:1}), <minecraft:stick>, 4000);
//Done!

//Excavator and Mineral Mix
    for mineral in Excavator.getRegisteredMinerals(){
        print(mineral.toString());
    }
    MineralMix.printRegisteredMinerals();
    val iron as MineralMix = Excavator.getMineral("Iron");
    print(iron as string);
    print(iron.getWeight());
    iron.setWeight(5);
    print(iron.getWeight());
    for ore in iron.getOres(){
        print(ore.entry.commandString);
    }
    for dim in iron.dimensionWhitelist{
        print(dim);
    }
    iron.dimensionWhitelist = [0] as int[];
    for dim in iron.dimensionWhitelist{
        print(dim);
    }
    for dim in iron.dimensionBlacklist{
        print(dim);
    }
    iron.dimensionBlacklist = [0] as int[];
    for dim in iron.dimensionBlacklist{
        print(dim);
    }

    recipes.addShapeless(<minecraft:stone> * 4, [<minecraft:iron_ore>, <minecraft:iron_ore>, <minecraft:iron_ore>, <minecraft:iron_ore>] as IIngredient[],
        function(out as IItemStack, ins, cInfo) as IItemStack{return out;} as IRecipeFunction,
        function(out, cInfo, player){
            //Should only execute these on the server
            if(cInfo.world.isRemote()){
                return;
            }
            val pos as IBlockPos = player.position as IBlockPos;
            cInfo.world.setMineralMix(pos, Excavator.getMineral("Iron"));
            print(cInfo.world.getMineralMix(pos) as string);

            val mapMix as MineralMix[][IBlockPos] = cInfo.world.getMineralMap();
            if(isNull(mapMix[player.position as IBlockPos])){
                print("null");
            } else {
                print(mapMix[player.position as IBlockPos][0] as string);
            }

            cInfo.world.setMineralMix(pos, Excavator.getMineral("Silt"));
            print(cInfo.world.getMineralMix(pos) as string);
        }
    );
//Done!


//Slag Replacer
    SlagReplacer.setSlag(<minecraft:jungle_stairs>, <*>.only(
        function(stack as IItemStack) as bool{
            return stack.ores[0].name.startsWith("ingot");
        }
    ));
    SlagReplacer.setSlag(<minecraft:fence>, <ore:crystalSlag>, "SLAG");
//Done!
