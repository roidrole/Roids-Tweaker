package roidrole.roidtweaker.internal;

import crafttweaker.api.minecraft.CraftTweakerMC;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.stream.Collectors;

import static roidrole.roidtweaker.mods.minecraft.anvil.AnvilCTClass.recipes;

@JEIPlugin
public class HEIPlugin implements IModPlugin {
    @Override
    public void register(@Nonnull IModRegistry registry) {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        registry.addRecipes(
            recipes.stream().filter(
                r -> !r.disabled
            ).map(recipe -> {
                ItemStack left = CraftTweakerMC.getItemStack(recipe.left);
                ItemStack output;
                if(recipe.output instanceof ItemStack){
                    output = (ItemStack)recipe.output;
                }else{
                    output = left;
                }
                    return jeiHelpers.getVanillaRecipeFactory().createAnvilRecipe(
                        Collections.singletonList(left),
                        Collections.singletonList(CraftTweakerMC.getItemStack(recipe.right)),
                        Collections.singletonList(output)
                    );
                }
            ).collect(Collectors.toList()),
            VanillaRecipeCategoryUid.ANVIL
        );
    }
}
