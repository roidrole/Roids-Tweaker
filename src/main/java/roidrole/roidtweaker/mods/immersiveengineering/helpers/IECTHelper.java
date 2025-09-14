package roidrole.roidtweaker.mods.immersiveengineering.helpers;

import blusunrize.immersiveengineering.api.ComparableItemStack;
import blusunrize.immersiveengineering.api.DimensionChunkCoords;
import blusunrize.immersiveengineering.api.tool.BelljarHandler;
import blusunrize.immersiveengineering.api.tool.ExcavatorHandler;
import blusunrize.immersiveengineering.common.util.compat.crafttweaker.Excavator;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;

import javax.xml.bind.TypeConstraintException;
import java.util.HashMap;
import java.util.Map;

public abstract class IECTHelper {
    public static Map<String, BelljarHandler.DefaultPlantHandler> HandlerMap = new HashMap<String, BelljarHandler.DefaultPlantHandler>(4){{
        put("crop", BelljarHandler.cropHandler);
        put("stem", BelljarHandler.stemHandler);
        put("stacking", BelljarHandler.stackingHandler);
    }};
    public static ComparableItemStack toComparableItemStack(IIngredient input){
        if(input instanceof IItemStack){
            ComparableItemStack output = new ComparableItemStack(CraftTweakerMC.getItemStack((IItemStack) input), false, false);
            if(((IItemStack) input).hasTag()){
                output.useNBT = true;
            }
            return output;
        }
        if(input instanceof IOreDictEntry){
            return new ComparableItemStack(((IOreDictEntry) input).getName());
        }
        throw new TypeConstraintException("Can't convert IIngredient type "+ input.getClass().getName()+ " to ComparableItemStack. Supported types are : IItemStack, IOreDictEntry");
    }

    public static Excavator.MTMineralMix getMTMineralMix(ExcavatorHandler.MineralMix mix){
        if(mix == null){
            return null;
        }
        return new Excavator.MTMineralMix(mix, ExcavatorHandler.mineralList.get(mix));
    }
    public static DimensionChunkCoords getDimensionalChunkCoords(IWorld world, IBlockPos pos){
        return new DimensionChunkCoords(world.getDimension(), pos.getX() >> 4, pos.getY() >> 4);
    }
}
