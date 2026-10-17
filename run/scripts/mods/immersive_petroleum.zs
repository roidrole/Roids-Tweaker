#loader afterip
#modloaded immersivepetroleum
//That is a custom entry point defined in ZenUtils as afterip;immersivepetroleum;I;after
//Necessary to access config-defined IP reservoirs, as they are loaded during init

import crafttweaker.recipes.IRecipeFunction;
import crafttweaker.item.IIngredient;
import crafttweaker.item.IItemStack;
import crafttweaker.world.IBlockPos;

import mods.roidtweaker.immersivepetroleum.IReservoir;
import mods.immersivepetroleum.Reservoir;
import mods.ctutils.utils.Math;

for reservoir in Reservoir.getRegisteredReservoirs(){
	print(reservoir.name);
}
print(Reservoir.getRegisteredReservoirs().length);


//To do it properly, that recipe would be registered in #loader crafttweaker. Doesn't really matter here
recipes.addShapeless(<minecraft:stone> * 4, [<minecraft:gold_ore>, <minecraft:gold_ore>, <minecraft:gold_ore>, <minecraft:gold_ore>] as IIngredient[],
	function(out as IItemStack, ins, cInfo) as IItemStack{return out;} as IRecipeFunction,
	function(out, cInfo, player){
		//Should only execute these on the server
		if(cInfo.world.isRemote()){
			return;
		}

		val pos as IBlockPos = player.position as IBlockPos;
		var reservoir as IReservoir = cInfo.world.getReservoir(pos);
		if(isNull(reservoir)){
			print("null");
		} else {
			print(reservoir.name);
		}
		
		val newReservoir as IReservoir = Reservoir.getReservoir("lava");
		val rolledSize as int = Math.floor((cInfo.world.getRandom().nextGaussian() + 0.5 as double) * newReservoir.maxSize as double) as int;
		newReservoir.capacity = Math.clamp(rolledSize, newReservoir.minSize, newReservoir.maxSize);
		newReservoir.current = newReservoir.capacity;
		cInfo.world.setReservoir(pos, newReservoir);

		reservoir = cInfo.world.getReservoir(pos);
		if(isNull(reservoir)){
			print("null");
		} else {
			print(reservoir.name);
		}
	}
);