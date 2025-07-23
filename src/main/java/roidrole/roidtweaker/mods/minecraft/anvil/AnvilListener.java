package roidrole.roidtweaker.mods.minecraft.anvil;

import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Comparator;

import static roidrole.roidtweaker.mods.minecraft.anvil.CTAnvil.recipes;

public class AnvilListener {
    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        recipes.stream().filter(r -> r.matches(event)).max(Comparator.comparing(r -> r.right.getAmount())).ifPresent(r -> r.apply(event));
    }

    //Returns the rest of the left input stack. Vanilla doesn't do this.
    //Originally copied from Rocky Tweaks, added container item check
    @SubscribeEvent
    public void onAnvilCraft(AnvilRepairEvent event) {
        recipes.stream()
            .filter(r -> r.matches(event))
            .max(Comparator.comparing(r -> r.right.getAmount()))
            .ifPresent(r -> {
                ItemStack leftInput = event.getItemInput();
                ItemStack rightInput = event.getIngredientInput();
                ItemStack remainder;
                if (leftInput.getCount() > r.left.getAmount()) {
                    remainder = leftInput.copy();
                    remainder.shrink(r.left.getAmount());
                    if (!event.getEntityPlayer().inventory.addItemStackToInventory(remainder)) {
                        event.getEntityPlayer().dropItem(remainder, true, false);
                    }
                }
                remainder = ItemStack.EMPTY;
                if(r.left.hasNewTransformers()){
                    remainder = CraftTweakerMC.getItemStack(r.left.applyNewTransform(CraftTweakerMC.getIItemStack(leftInput)));
                } else if(r.left.hasTransformers()){
                    remainder = CraftTweakerMC.getItemStack(r.left.applyTransform(CraftTweakerMC.getIItemStack(leftInput), CraftTweakerMC.getIPlayer(event.getEntityPlayer())));
                } else if (leftInput.getItem().hasContainerItem(leftInput)) {
                    remainder = leftInput.getItem().getContainerItem(leftInput);
                }
                if (!event.getEntityPlayer().inventory.addItemStackToInventory(remainder)) {
                    if (event.getEntityPlayer().dropItem(remainder, true, false) != null){
                        remainder = ItemStack.EMPTY;
                    }
                }
                if(r.right.hasNewTransformers()){
                    remainder = CraftTweakerMC.getItemStack(r.right.applyNewTransform(CraftTweakerMC.getIItemStack(rightInput)));
                } else if(r.right.hasTransformers()){
                    remainder = CraftTweakerMC.getItemStack(r.right.applyTransform(CraftTweakerMC.getIItemStack(rightInput), CraftTweakerMC.getIPlayer(event.getEntityPlayer())));
                } else if (rightInput.getItem().hasContainerItem(rightInput)) {
                    remainder = rightInput.getItem().getContainerItem(rightInput);
                }
                if(!remainder.isEmpty()) {
                    remainder.setCount(r.right.getAmount());
                    if (!event.getEntityPlayer().inventory.addItemStackToInventory(remainder)) {
                        event.getEntityPlayer().dropItem(remainder, true, false);
                    }
                }
            })
        ;
    }
}
