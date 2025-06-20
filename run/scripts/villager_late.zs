import mods.roidtweaker.minecraft.villager.Villager;
import crafttweaker.item.IIngredient;
import crafttweaker.item.IItemStack;
import crafttweaker.util.IRandom;

val myCareer as IVillagerCareer = Villager.getCareer("minecraft:priest", 0)

myCareer.removeTrade();
Villager.getCareer("minecraft:librarian", "librarian").removeTrade(1);
Villager.getCareer("minecraft:butcher", 0).removeTrade(1, 0);
myCareer.addTrade(1, <minecraft:iron_sword>, <minecraft:iron_sword:*>, <minecraft:iron_ingot>);
myCareer.addTrade(1, <minecraft:iron_sword>, <minecraft:iron_sword:*>, <minecraft:iron_ingot>, function(random as IRandom, sell as IIngredient) as IItemStack{return <minecraft:iron_sword>;});
myCareer.addTradeAdvanced(1, function(random as IRandom) as IItemStack[]{
    val meta as int = random.nextInt(16);
    val wool as IItemStack = <minecraft:wool>.withDamage(meta);
    return [wool, wool, wool] as IItemStack[];
});
