import crafttweaker.util.IRandom;
import crafttweaker.util.IAxisAlignedBB as AABB;
import crafttweaker.item.IItemStack;
import crafttweaker.item.IIngredient;
import crafttweaker.recipes.IRecipeFunction;
import crafttweaker.entity.IEntity;
import mods.ctutils.utils.Math;

//IIngredient
recipes.addShaped(<minecraft:golden_sword>, [
    (<minecraft:stick> * 3).spread(),
    (<minecraft:stick> * 3).spread(),
    (<minecraft:stick> * 3).spread()
]);

//IRandom
print("Testing Random");
print(Math.getRandom().nextGaussian());

//IWorld
recipes.addShapeless(<minecraft:stone> * 4, [<minecraft:iron_sword>, <minecraft:iron_sword>, <minecraft:iron_sword>, <minecraft:iron_sword>] as IIngredient[],
    function(out as IItemStack, ins, cInfo) as IItemStack{return out;} as IRecipeFunction,
    function(out, cInfo, player){
        //Should only execute these on the server
        if(cInfo.world.isRemote()){
            return;
        }
        val entityList as IEntity[] = cInfo.world.getEntitiesWithinAABB(
            AABB.create(
                cInfo.player.position3f.asBlockPos()
            ).grow(3)
        );
        for entity in entityList{
            if(!(isNull(entity.definition))){
                print(entity.definition.id);
            }
        }
    }
);