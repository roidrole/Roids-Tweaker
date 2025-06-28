package roidrole.roidtweaker.mixins.thermalexpansion;

import cofh.core.inventory.ComparableItemStack;
import cofh.thermalexpansion.util.managers.device.FisherManager;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(FisherManager.class)
public interface IFisherManagerAccessor {
    @Accessor(value = "fishList", remap = false)
    static List<ItemStack> getFishList(){
        throw new AssertionError();
    }
    @Accessor(value = "weightList", remap = false)
    static List<Integer> getWeightList(){
        throw new AssertionError();
    }
    @Accessor(value = "totalWeight", remap = false)
    static int getTotalWeight(){
        throw new AssertionError();
    }
    @Accessor(value = "baitMap", remap = false)
    static TObjectIntHashMap<ComparableItemStack> getBaitMap(){
        throw new AssertionError();
    }
    @Accessor(value = "totalWeight", remap = false)
    static void setTotalWeight(int totalWeight){
        throw new AssertionError();
    }
}
