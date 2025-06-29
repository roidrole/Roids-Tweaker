#modloaded f0-resources
import crafttweaker.world.IWorld;
import crafttweaker.world.IBlockPos;
import crafttweaker.item.IItemStack;
import crafttweaker.item.IIngredient;
import crafttweaker.recipes.IRecipeFunction;
import mods.roidtweaker.minecraft.IChunkPos;
import mods.roidtweaker.f0resources.IF0RWorld;
import mods.roidtweaker.f0resources.IChunkData;

recipes.addShapeless(<minecraft:stone> * 4, [<minecraft:iron_ore>, <minecraft:iron_ore>, <minecraft:iron_ore>, <minecraft:iron_ore>] as IIngredient[],
    function(out as IItemStack, ins, cInfo) as IItemStack{return out;} as IRecipeFunction,
    function(out, cInfo, player){
        //Required to do the other IWorld Expansions
        val world as IF0RWorld = cInfo.world.getF0RWorld();
        val here as IChunkPos = IChunkPos.getChunkPos(player.position as IBlockPos);

        //F0RWorld methods
        val chunks = world.getAllLoadedChunkData();
        if(chunks[here] != world.getLoadedChunkData(here)){
            print("Shouldn't happen!");
        } else {
            print("It works!");
        }

        world.unloadChunkData(here);
        world.unloadAllChunkData();


        val chunkData as IChunkData = IChunkData.createChunkData();
        print(chunkData.getSize());
        print(chunkData.getFluidLength());

        world.loadChunkData(here, chunkData);


    }
    );