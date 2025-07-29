#modloaded baubles
import mods.ctintegration.baubles.IBaubleInventory;
import mods.ctintegration.util.ArrayUtil;
import crafttweaker.item.IItemStack;
import crafttweaker.recipes.IRecipeFunction;

val type as string = IBaubleInventory.getBaubleType(<baubles:ring>);
val slots as int[] = IBaubleInventory.getValidSlots(type);
print("Type is "+type+", available slots are : "+ArrayUtil.toString(slots));

recipes.addShapeless(<minecraft:carrot>, (<minecraft:golden_sword> * 3).spread(),
    function(out as IItemStack, ins, cInfo) as IItemStack{return out;} as IRecipeFunction,
    function(out, cInfo, player){
        player.baublesInventory.setStackInSlot(1, <baubles:ring>);
        for item in player.baublesInventory{
            if(!cInfo.world.isRemote()){
                if(isNull(item)){
                    print("null");
                } else{
                    print(item.commandString);
                }
            }
        }
    }
);