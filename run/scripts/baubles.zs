#modloaded baubles
//Utils
import mods.ctintegration.util.ArrayUtil;
import crafttweaker.item.IItemStack;
import crafttweaker.recipes.IRecipeFunction;
//Bauble-related classes
import mods.ctintegration.baubles.IBauble;
import mods.ctintegration.baubles.IBaubleInventory;
import mods.ctintegration.bubbles.IBubbleInventory;
//Expansions
import crafttweaker.entity.IEntityLivingBase;
import crafttweaker.player.IPlayer;

var ring as IItemStack = itemUtils.getItem("baubles:ring");
if(isNull(ring)){
    ring = itemUtils.getItem("baubles:max_verstappen");
}
val type as string = IBauble.getBaubleType(ring);
val slots as int[] = IBauble.getValidSlots(type);
print("Type is "+type+", available slots are : "+ArrayUtil.toString(slots));

recipes.addShapeless(<minecraft:carrot>, (<minecraft:golden_sword> * 3).spread(),
    function(out as IItemStack, ins, cInfo) as IItemStack{return out;} as IRecipeFunction,
    function(out, cInfo, player){
        val baubleInventory as IBaubleInventory = player.baublesInventory;
        baubleInventory.setStackInSlot(1, ring);
        if(!cInfo.world.isRemote()){
            print(
                "ring valid for slot 0: "+baubleInventory.isItemValidForSlot(0, ring, player) as string +
                ", and slot 1 : "+baubleInventory.isItemValid(1, ring) as string
            );
            print("There is "+baubleInventory.getSlotCount()+" slots");
            for item in baubleInventory{
                if(isNull(item)){
                    print("null");
                } else{
                    print(item.commandString);
                }
            }
            print("Is ring in inventory? "+baubleInventory.isBaubleEquipped(ring));
            print("Is ring in player? "+player.isBaubleEquipped(ring));
        }
    }
);

//Following won't work if using baubles!
recipes.addShapeless(<minecraft:carrot>, (<minecraft:iron_sword> * 3).spread(),
    function(out as IItemStack, ins, cInfo) as IItemStack{return out;} as IRecipeFunction,
    function(out, cInfo, player){
        val bubblesInventory as IBubbleInventory = player.bubblesInventory;
        bubblesInventory.growBaubleSlot("AMULET", 3);
        bubblesInventory.shrinkBaubleSlot("RING", 1);
        bubblesInventory.setBaubleSlot("BELT", 3);
    }
);

recipes.addShapeless(<minecraft:carrot>, (<minecraft:diamond_sword> * 3).spread(),
    function(out as IItemStack, ins, cInfo) as IItemStack{return out;} as IRecipeFunction,
    function(out, cInfo, player){
        player.bubblesInventory.resetBaubleSlots();
    }
);