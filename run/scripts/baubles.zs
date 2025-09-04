#modloaded baubles
//Utils
import mods.ctintegration.util.ArrayUtil;
import crafttweaker.item.IItemStack;
import crafttweaker.recipes.IRecipeFunction;
//Bauble-related classes
import mods.ctintegration.baubles.IBauble;
import mods.ctintegration.baubles.IBaubleInventory;
import mods.ctintegration.bubbles.IBubbleInventory;
import mods.ctintegration.baubles.InjectableBauble;
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

//Custom Baubles (requires Bubbles)
val newBauble as InjectableBauble = IBauble.createBauble("RING");
    newBauble.setOnWornTick(function(stack as IItemStack, entity as IEntityLivingBase) as void{
        if(!entity.world.isRemote()){
            print("I'm Being Worn!");
        }
    });
    newBauble.setOnEquipped(function(stack as IItemStack, entity as IEntityLivingBase) as void{
        if(!entity.world.isRemote()){
            print("I'M EQUIPPED, LOL");
        }
    });
    newBauble.setOnUnequipped(function(stack as IItemStack, entity as IEntityLivingBase) as void{
        if(!entity.world.isRemote()){
            print("Fine, I'll shut up");
        }
    });
    newBauble.setCanUnequip(function(stack as IItemStack, entity as IEntityLivingBase) as bool{
        if(!stack.hasTag){
            print("Can't touch this");
            return false;
        }
        return stack.tag.OwnerDied.asBool();
    });
    newBauble.willAutoSync = function(stack as IItemStack, entity as IEntityLivingBase) as bool{
        return true;
    };
    newBauble.setOnDeath(function(slot as int, item as IItemStack, entity as IEntityLivingBase) as string{
        //For some reason, stack as IItemStack is needed here. Might revisit later.
        //Roundabout, but CT is weird that way.
        (entity as IPlayer).bubblesInventory.setStackInSlot(slot, (item as IItemStack).updateTag({"OwnerDied":true}));
        return "ALWAYS_KEEP";
    });
newBauble.register(<minecraft:fire_charge>.definition);

//Example for adding a potion effect
val feather_of_speed as InjectableBauble = IBauble.createBauble("RING");
    feather_of_speed.setOnWornTick(function(stack as IItemStack, entity as IEntityLivingBase) as void{
	    if (!entity.world.isRemote()) {
            if (entity.world.getWorldTime() % 10 == 0) {
                //You can also do fun stuff like setting the multiplier to the stack size
                entity.addPotionEffect(<potion:minecraft:speed>.makePotionEffect(100, stack.amount - 1));
            }
        }
    });
    feather_of_speed.setOnUnequipped(function(stack as IItemStack, entity as IEntityLivingBase) as void{
        if (!entity.world.isRemote()) {
            entity.removePotionEffect(<potion:minecraft:speed>);
        }
    });
feather_of_speed.register(<minecraft:feather>.definition);