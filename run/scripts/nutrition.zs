import mods.roidtweaker.nutrition.Nutrition;
import crafttweaker.recipes.IRecipeFunction;
import crafttweaker.item.IItemStack;

//Typically would be in an event handler or something
recipes.addShaped(<minecraft:feather>, [
		[<minecraft:diamond>, <minecraft:diamond>, null],
		[null, <minecraft:diamond>, <minecraft:diamond>],
		[<minecraft:diamond>, <minecraft:diamond>, null]
	],
	function(out as IItemStack, ins, cInfo) as IItemStack{return out;} as IRecipeFunction,
	function(out, cInfo, player){
		//Should only execute these on the server
		if(cInfo.world.isRemote()){
			return;
		}
		print("Before:");
		for nutrition in Nutrition.getNutrientList() {
			print(nutrition + ": " + player.getNutrient(nutrition));
		}
		player.addNutrient(<minecraft:bread>);
		player.addNutrient("protein", 5.0 as float);
		player.setNutrient("fruit", 100);
		player.resetNutrient("dairy");

		print("After:");
		for nutrition in Nutrition.getNutrientList() {
			print(nutrition + ": " + player.getNutrient(nutrition));
		}

	}
);