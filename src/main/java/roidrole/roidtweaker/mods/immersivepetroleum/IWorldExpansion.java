package roidrole.roidtweaker.mods.immersivepetroleum;

import blusunrize.immersiveengineering.api.DimensionChunkCoords;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import roidrole.roidtweaker.mods.immersiveengineering.helpers.IECTHelper;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("immersivepetroleum")
@ZenRegister
@ZenExpansion("crafttweaker.world.IWorld")
@SuppressWarnings("unused")
public class IWorldExpansion {
	@ZenMethod
	public static void setReservoir(IWorld world, IBlockPos pos, MTReservoir reservoir){
		DimensionChunkCoords dimPos = IECTHelper.getDimensionalChunkCoords(world, pos);
		PumpjackHandler.oilCache.put(dimPos, reservoir.internal);
	}

	@ZenMethod
	public static MTReservoir getReservoir(IWorld world, IBlockPos pos){
		PumpjackHandler.OilWorldInfo info = PumpjackHandler.getOilWorldInfo(CraftTweakerMC.getWorld(world), pos.getX() >> 4, pos.getY() >> 4);
		if(info.getType() == null){
			return null;
		}
		return new MTReservoir(info);
	}
}
