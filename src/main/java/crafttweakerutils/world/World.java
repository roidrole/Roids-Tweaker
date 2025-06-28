package crafttweakerutils.world;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;


@ZenRegister
@ZenExpansion("crafttweaker.world.IWorld")
@SuppressWarnings("unused")
public class World {

	@ZenMethod
	public static boolean canSeeSky(IWorld world, IBlockPos pos) {
		BlockPos p = (BlockPos) pos.getInternal();
		net.minecraft.world.World w = (net.minecraft.world.World) world.getInternal();

		return w.canSeeSky(p);

	}

	@ZenMethod
	public static boolean canSnowAt(IWorld world, IBlockPos pos, boolean lightCheck) {
		BlockPos p = (BlockPos) pos.getInternal();
		net.minecraft.world.World w = (net.minecraft.world.World) world.getInternal();

		return w.canSnowAt(p, lightCheck);
	}

	@ZenMethod
	public static boolean canBlockFreeze(IWorld world, IBlockPos pos, boolean noWaterAdj) {
		BlockPos p = (BlockPos) pos.getInternal();
		net.minecraft.world.World w = (net.minecraft.world.World) world.getInternal();

		return w.canBlockFreeze(p, noWaterAdj);
	}

	@ZenMethod
	public static IGameRules getGameRules(IWorld world) {
		net.minecraft.world.World w = (net.minecraft.world.World) world.getInternal();
		return new MCGameRules(w.getGameRules());
	}

	@ZenMethod
	public static int getSkyLight(IWorld world, IBlockPos pos)
	{
		net.minecraft.world.World w = (net.minecraft.world.World)world.getInternal();
		BlockPos p = (BlockPos)pos.getInternal();

		return w.getLightFor(EnumSkyBlock.SKY, p);
	}

	@ZenMethod
	public static int getBlockLight(IWorld world, IBlockPos pos)
	{
		net.minecraft.world.World w = (net.minecraft.world.World)world.getInternal();
		BlockPos p = (BlockPos)pos.getInternal();

		return w.getLightFor(EnumSkyBlock.BLOCK, p);
	}

}
