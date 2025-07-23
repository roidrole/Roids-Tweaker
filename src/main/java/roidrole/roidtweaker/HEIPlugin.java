package roidrole.roidtweaker;

import crafttweaker.api.minecraft.CraftTweakerMC;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.plugins.vanilla.anvil.AnvilRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mods.minecraft.anvil.AnvilRecipes;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static roidrole.roidtweaker.mods.minecraft.anvil.CTAnvil.recipes;

@JEIPlugin
@SuppressWarnings("unused")
public class HEIPlugin implements IModPlugin {
    @Override
    public void register(@Nonnull IModRegistry registry) {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        registry.addRecipes(
            recipes.stream().filter(
                r -> !(r instanceof AnvilRecipes.DisabledRecipe)
            ).map(recipe -> {
                ItemStack left = CraftTweakerMC.getItemStack(recipe.left);
                ItemStack output;
                if(recipe.output instanceof ItemStack){
                    output = recipe.output;
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

    public static class CustomAnvilRecipeWrapper extends AnvilRecipeWrapper {
        public CustomAnvilRecipeWrapper(List<ItemStack> leftInput, List<ItemStack> rightInputs, List<ItemStack> outputs) {
            super(leftInput, rightInputs, outputs);
        }

        @Override
        private void drawRepairCost(Minecraft minecraft, String text, int mainColor, int recipeWidth) {
            int shadowColor = 0xFF000000 | (mainColor & 0xFCFCFC) >> 2;
            int width = minecraft.fontRenderer.getStringWidth(text);
            int x = recipeWidth - 2 - width;
            int y = 27;

            if (minecraft.fontRenderer.getUnicodeFlag()) {
                Gui.drawRect(x - 2, y - 2, x + width + 2, y + 10, 0xFF000000);
                Gui.drawRect(x - 1, y - 1, x + width + 1, y + 9, 0xFF3B3B3B);
            } else {
                minecraft.fontRenderer.drawString(text, x + 1, y, shadowColor);
                minecraft.fontRenderer.drawString(text, x, y + 1, shadowColor);
                minecraft.fontRenderer.drawString(text, x + 1, y + 1, shadowColor);
            }

            minecraft.fontRenderer.drawString(text, x, y, mainColor);
        }
    }
}
