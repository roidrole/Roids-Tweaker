package roidrole.roidtweaker.mods.immersiveengineering;

import blusunrize.immersiveengineering.api.DimensionChunkCoords;
import blusunrize.immersiveengineering.api.tool.ExcavatorHandler;
import blusunrize.immersiveengineering.common.util.compat.crafttweaker.Excavator;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import crafttweaker.mc1120.world.MCBlockPos;
import roidrole.roidtweaker.mixins.immersiveengineering.IMTMineralMixAccessor;
import roidrole.roidtweaker.mods.immersiveengineering.helpers.IECTHelper;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.HashMap;
import java.util.Map;

//TODO : Consider IP support

@ModOnly("immersiveengineering")
@ZenRegister
@ZenExpansion("crafttweaker.world.IWorld")
@SuppressWarnings("unused")
public class IWorldExpansion {
	@ZenMethod
	public static void setMineralMix(IWorld world, IBlockPos pos, Excavator.MTMineralMix mix){
		DimensionChunkCoords dimPos = IECTHelper.getDimensionalChunkCoords(world, pos);
		ExcavatorHandler.MineralWorldInfo info = ExcavatorHandler.mineralCache.computeIfAbsent(dimPos, (key) -> new ExcavatorHandler.MineralWorldInfo());
		info.mineral = ((IMTMineralMixAccessor)mix).getMix();
	}

	@ZenMethod
	public static Excavator.MTMineralMix getMineralMix(IWorld world, IBlockPos pos){
		DimensionChunkCoords dimPos = IECTHelper.getDimensionalChunkCoords(world, pos);
		ExcavatorHandler.MineralWorldInfo info = ExcavatorHandler.mineralCache.get(dimPos);
		if(info == null){return null;}
		ExcavatorHandler.MineralMix mineral = info.mineral;
		return IECTHelper.getMTMineralMix(mineral);
	}

	@ZenMethod
	@ZenGetter("mineralMix")
	//Was in MMT, probably not needed. Still better than original implementation
	//If you need this, it's probably better I make specific compat for your needs
	public static Map<IBlockPos, Excavator.MTMineralMix[]> getMineralMap(IWorld world){
		//Map with IBlockPos as key and [original, override] as value
		Map<IBlockPos, Excavator.MTMineralMix[]> output = new HashMap<>(ExcavatorHandler.mineralCache.size());
		ExcavatorHandler.mineralCache.forEach((pos, info) -> output.put(
			new MCBlockPos(pos.x << 4, 0, pos.z << 4),
			new Excavator.MTMineralMix[]{
				IECTHelper.getMTMineralMix(info.mineral),
				IECTHelper.getMTMineralMix(info.mineralOverride)
			}
		));
		return output;
	}
}
