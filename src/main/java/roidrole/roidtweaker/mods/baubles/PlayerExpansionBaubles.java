package roidrole.roidtweaker.mods.baubles;

import baubles.api.BaublesApi;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("baubles")
@ZenExpansion("crafttweaker.player.IPlayer")
@ZenRegister
@SuppressWarnings("unused")
public class PlayerExpansionBaubles {

    @ZenGetter("baublesInventory")
    @ZenMethod
    public static CTBaubleInventory getBaublesInventory(IPlayer player) {
        return new CTBaubleInventory(BaublesApi.getBaublesHandler(CraftTweakerMC.getPlayer(player)));
    }
    @ZenGetter("bubblesInventory")
    @ZenMethod
    public static CTBubbleInventory getBubblesInventory(IPlayer player) {
        return new CTBubbleInventory(BaublesApi.getBaublesContainer(CraftTweakerMC.getPlayer(player)));
    }

    @ZenMethod
    public static int isBaubleEquipped(IPlayer player, IItemStack bauble) {
        EntityPlayer mcPlayer = CraftTweakerMC.getPlayer(player);
        ItemStack mcItemStack = CraftTweakerMC.getItemStack(bauble);
        return BaublesApi.isBaubleEquipped(mcPlayer, mcItemStack.getItem());
    }
}
