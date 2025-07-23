package roidrole.roidtweaker;

import crafttweaker.api.minecraft.CraftTweakerMC;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.plugins.vanilla.anvil.AnvilRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;
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
            recipes.stream()
                .filter(r -> !(r instanceof AnvilRecipes.DisabledRecipe))
                .map(r -> {
                    if(r instanceof AnvilRecipes.Repair){
                        return new CustomAnvilRepairWrapper((AnvilRecipes.Repair)r);
                    } else {
                        return new CustomAnvilRecipeWrapper(
                            r.left.getItems().stream().map(CraftTweakerMC::getItemStack).collect(Collectors.toList()),
                            r.right.getItems().stream().map(CraftTweakerMC::getItemStack).collect(Collectors.toList()),
                            Collections.singletonList(r.output),
                            r.xpCost
                        );
                    }
                })
                .collect(Collectors.toList()),
            VanillaRecipeCategoryUid.ANVIL
        );
    }



    public static class CustomAnvilRecipeWrapper extends AnvilRecipeWrapper {
        private final int xpCost;

        public CustomAnvilRecipeWrapper(List<ItemStack> left, List<ItemStack> right, List<ItemStack> output,  int xpCost) {
            super(left, right, output);
            this.xpCost = xpCost;
        }

        @Override
        //Copy of AnvilRecipeWrapper, modified to use our xpCost instead of scanning
        public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
            int cost;
            if(this.xpCost != 0) {
                cost = this.xpCost;
            } else {
                cost = 1;
            }

            String text = I18n.format("container.repair.cost", Integer.toString(cost));

            int mainColor;
            EntityPlayerSP player = minecraft.player;
            if (player != null &&
                (cost >= 40 || cost > player.experienceLevel) &&
                !player.capabilities.isCreativeMode
            ) {
                // Show red if the player doesn't have enough levels
                mainColor = 0xFFFF6060;
            } else {
                mainColor = 0xFF80FF20;
            }

            drawRepairCost(minecraft, text, mainColor, recipeWidth);
        }
        //Copy of the private method
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
    public static class CustomAnvilRepairWrapper extends CustomAnvilRecipeWrapper {

        public CustomAnvilRepairWrapper(AnvilRecipes.Repair recipe) {
            super(
                recipe.left.getItems().stream().map(item -> {
                    ItemStack stack = CraftTweakerMC.getItemStack(item);
                    stack.setItemDamage(stack.getMaxDamage());
                    return stack;
                }).collect(Collectors.toList()),
                recipe.right.getItems().stream().map(CraftTweakerMC::getItemStack).collect(Collectors.toList()),
                recipe.left.getItems().stream().map(item -> {
                    ItemStack stack = CraftTweakerMC.getItemStack(item);
                    if(recipe.repairRatio > 0) {
                        stack.setItemDamage(Math.round(stack.getMaxDamage()*(1-recipe.repairRatio)));
                    } else {
                        stack.setItemDamage(stack.getMaxDamage() - recipe.repair);
                    }
                    return stack;
                }).collect(Collectors.toList()),
                1
            );
        }
    }
}
