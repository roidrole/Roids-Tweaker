package roidrole.roidtweaker.mixins.immersiveengineering;

import blusunrize.immersiveengineering.api.ComparableItemStack;
import blusunrize.immersiveengineering.api.crafting.IngredientStack;
import blusunrize.immersiveengineering.api.tool.BelljarHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.HashMap;
import java.util.HashSet;

@Mixin(BelljarHandler.class)
public interface IBellJarHandlerAccessor {
    @Accessor(value = "fluidFertilizers", remap = false)
    static HashSet<BelljarHandler.FluidFertilizerHandler> getFluidFertilizers(){
        throw new AssertionError();
    }

    @Accessor(value = "itemFertilizers", remap = false)
    static HashSet<BelljarHandler.ItemFertilizerHandler> getItemFertilizers(){
        throw new AssertionError();
    }

    @Accessor(value = "plantHandlers", remap = false)
    static HashSet<BelljarHandler.IPlantHandler> getPlantHandlers(){
        throw new AssertionError();
    }

    @Accessor(value = "seedSoilMap", remap = false)
    static HashMap<ComparableItemStack, IngredientStack> getSeedSoilMap(){
        throw new AssertionError();
    }
    @Accessor(value = "seedOutputMap", remap = false)
    static HashMap<ComparableItemStack, ItemStack[]> getSeedOutputMap(){
        throw new AssertionError();
    }
    @Accessor(value = "seedRenderMap", remap = false)
    static HashMap<ComparableItemStack, IBlockState[]> getSeedRenderMap(){
        throw new AssertionError();
    }
    @Accessor(value = "soilTextureMap", remap = false)
    static HashMap<ComparableItemStack, ResourceLocation> getSoilTextureMap(){
        throw new AssertionError();
    }
}
