package roidrole.roidtweaker.mods.immersiveengineering;

import blusunrize.immersiveengineering.api.crafting.BlueprintCraftingRecipe;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

import java.util.List;

@ZenRegister
@ZenExpansion("mods.immersiveengineering.Blueprint")
@SuppressWarnings("unused")
public class BlueprintExpansion {
    @ZenMethodStatic
    public static List<String> getRegisteredBlueprints(){
        return BlueprintCraftingRecipe.blueprintCategories;
    }

    @ZenMethodStatic
    public static void addVillagerTrade(String category, IItemStack price){
        BlueprintCraftingRecipe.addVillagerTrade(category, (ItemStack) price.getInternal());
    }

    @ZenMethodStatic
    public static void addBlueprint(String category){
        BlueprintCraftingRecipe.blueprintCategories.add(category);
    }
}
