package roidrole.roidtweaker.mods.minecraft.anvil;

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
    //Copied from Rocky Tweaks, lightly adapted to my format
    @SubscribeEvent
    public void onAnvilCraft(AnvilRepairEvent event) {
        recipes.stream()
            .filter(r -> !r.disabled && r.matches(event))
            .max(Comparator.comparing(r -> r.right.getAmount()))
            .ifPresent(r -> {
                if (event.getItemInput().getCount() > r.left.getAmount()) {
                    //TODO : test for container items
                    ItemStack remainder = event.getItemInput().copy();
                    remainder.shrink(r.left.getAmount());
                    if (!event.getEntityPlayer().inventory.addItemStackToInventory(remainder)) {
                        event.getEntityPlayer().dropItem(remainder, true, false);
                    }
                }
            })
        ;
    }
}
