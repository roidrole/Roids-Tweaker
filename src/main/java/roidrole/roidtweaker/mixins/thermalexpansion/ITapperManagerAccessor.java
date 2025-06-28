package roidrole.roidtweaker.mixins.thermalexpansion;

import cofh.core.inventory.ComparableItemStack;
import cofh.core.util.BlockWrapper;
import cofh.thermalexpansion.util.managers.device.TapperManager;
import com.google.common.collect.SetMultimap;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(TapperManager.class)
public interface ITapperManagerAccessor {
    @Invoker(value = "addFertilizer", remap = false)
    static void invokeAddFertilizer(ItemStack fertilizer, int multiplier){
        throw new AssertionError();
    }
    @Accessor(value = "fertilizerMap", remap = false)
    static TObjectIntHashMap<ComparableItemStack> getFertilizerMap(){
        throw new AssertionError();
    }
    @Accessor(value = "leafMap",remap = false)
    static SetMultimap<BlockWrapper, BlockWrapper> getLeafMap(){
        throw new AssertionError();
    }
    @Accessor(value = "blockMap",remap = false)
    static Map<BlockWrapper, FluidStack> getBlockMap(){
        throw new AssertionError();
    }
}
