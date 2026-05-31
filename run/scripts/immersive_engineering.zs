#modloaded immersiveengineering
import mods.immersiveengineering.ArcFurnace;
import mods.roidtweaker.immersiveengineering.BulletBuilder;
import mods.roidtweaker.immersiveengineering.GardenCloche;
import mods.roidtweaker.immersiveengineering.GardenClocheMultiplierFunction;
import mods.roidtweaker.immersiveengineering.IChemEntityEffect;
import mods.roidtweaker.immersiveengineering.IChemBlockEffect;
import mods.roidtweaker.immersiveengineering.IRailgunImpact;
import mods.roidtweaker.immersiveengineering.SlagReplacer;
import mods.roidtweaker.immersiveengineering.IEGuns;
import mods.immersiveengineering.Blueprint;
import mods.immersiveengineering.MineralMix;
import mods.immersiveengineering.Excavator;
import mods.immersiveengineering.MetalPress;

import crafttweaker.item.IIngredient;
import crafttweaker.item.IItemStack;
import crafttweaker.liquid.ILiquidStack;
import crafttweaker.data.IData;
import crafttweaker.world.IWorld;
import crafttweaker.world.IBlockPos;
import crafttweaker.recipes.IRecipeFunction;
import crafttweaker.world.IFacing;
import crafttweaker.entity.IEntity;
import crafttweaker.entity.IEntityLivingBase;
import crafttweaker.player.IPlayer;
import crafttweaker.damage.IDamageSource;

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

//Bullet Builder
    val myBullet = BulletBuilder.get("super_bullet");
        myBullet.setTextures(["no_texture"]);
        myBullet.setCasing(<minecraft:carrot>);
        myBullet.setTickLimit(50);
        myBullet.setMovementDecay(1);
        myBullet.setGravity(1);
        myBullet.setBulletAmount(50);
        myBullet.setValidForTurret(true);
        myBullet.setProperCartridge(true);
        myBullet.setColor(0, 0x00FFFF);
        myBullet.setColorNBT(1, "nbtColorKey");
        myBullet.impactBlock(function(world as IWorld, pos as IBlockPos, sidehit as IFacing, shooter as IEntityLivingBase, bullet as IEntity, headshot as bool){
            print("Shot a block with a super bullet!");
        });
        myBullet.impactEntity(function(world as IWorld, target as IEntity, shooter as IEntityLivingBase, bullet as IEntity, headshot as bool){
            print("Shot an entity with a super bullet!");
            target.attackEntityFrom(IDamageSource.GENERIC(), 5.0);
        });
        myBullet.onFired(function(shooter as IPlayer, cartridge as IItemStack, projectile as IEntity, charged as bool){
            print("Fired a super bullet!");
        });
    myBullet.build();
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

//IE Guns
    IEGuns.addChemthrowerEffect(<liquid:creosote>, true, false, null, 100);
    //Note that "LIGHTNING_BOLT" is not a valid damage source
    IEGuns.addChemthrowerEffect(<liquid:water>, false, true, "LIGHTNING_BOLT", 1, [<potion:minecraft:strength>.makePotionEffect(100, 100)]);
    IEGuns.addChemthrowerEffect(<liquid:concrete>, true, true,
        function(target as IEntityLivingBase, shooter as IPlayer, thrower as IItemStack, fluid as ILiquidStack) as void{
            print("CONCRETE HIT AN ENTITY");
        } as IChemEntityEffect,
        function(world as IWorld, pos as IBlockPos, facing as IFacing, entityPlayer as IPlayer, itemStack as IItemStack, fluid as ILiquidStack) as void{
            print("CONCRETE HIT A BLOCK");
        } as IChemBlockEffect
    );

    IEGuns.addRailgunBullet(<minecraft:stick>, 100.0, 100.0, [[0x777777, 0xA4A4A4]] as int[][]);
    IEGuns.addRailgunBullet(<minecraft:golden_sword>, 100.0, 100.0,
        function(target as IEntity, shooter as IEntity) as bool{
            print("golden sword railgun hit an entity");
            return true;
        } as IRailgunImpact, [[0x777777, 0xA4A4A4]] as int[][]
    );
//Done!

//Metal Press
    MetalPress.addRecipeNBT(<minecraft:iron_sword>, <minecraft:iron_ingot>.withTag({marker:1}), <minecraft:stick>, 4000);
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

    Excavator.removeAllMinerals();
    print("After removing all minerals: ");
    MineralMix.printRegisteredMinerals();
    Excavator.addMineral(
        "Iron",
        3,
        0,
        ["oreIron"],
        [1]
    );
    print("After adding back one mineral: ");
    MineralMix.printRegisteredMinerals();


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
