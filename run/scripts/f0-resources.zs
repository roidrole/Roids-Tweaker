#modloaded f0-resources
import crafttweaker.world.IWorld;
import crafttweaker.world.IBlockPos;
import crafttweaker.item.IItemStack;
import crafttweaker.item.IIngredient;
import crafttweaker.recipes.IRecipeFunction;
import mods.roidtweaker.minecraft.IChunkPos;
import mods.roidtweaker.f0resources.F0RWorld;
import mods.roidtweaker.f0resources.ChunkData;
import mods.roidtweaker.f0resources.FluidData;
import mods.roidtweaker.f0resources.OreData;

//Would typically be in a custom multiblock machine or something. Go wild
recipes.addShapeless(<minecraft:stone> * 4, [<minecraft:iron_ore>, <minecraft:iron_ore>, <minecraft:iron_ore>, <minecraft:iron_ore>] as IIngredient[],
    function(out as IItemStack, ins, cInfo) as IItemStack{return out;} as IRecipeFunction,
    function(out, cInfo, player){
        //Should only execute these on the server
        if(cInfo.world.isRemote()){
            return;
        }

        val world as F0RWorld = cInfo.world.getF0RWorld();
        val here as IChunkPos = IChunkPos.getChunkPos(player.position as IBlockPos);

        world.removeChunkData();
        print(world.hasChunkData(here));

        val chunkData as ChunkData = ChunkData.create();
        chunkData.addData(FluidData.create(<liquid:water>, 10000));
        chunkData.addData(OreData.create(<minecraft:golden_sword:50>, 10000));

        print(chunkData.getSize());

        world.setChunkData(here, chunkData);


    }
);