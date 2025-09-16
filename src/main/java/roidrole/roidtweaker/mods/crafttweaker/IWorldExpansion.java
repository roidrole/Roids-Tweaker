package roidrole.roidtweaker.mods.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.util.IAxisAlignedBB;
import crafttweaker.api.util.Position3f;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.registry.EntityEntry;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;
import java.util.Objects;

@ZenExpansion("crafttweaker.world.IWorld")
@ZenRegister
@SuppressWarnings("unused")
public class IWorldExpansion {
	@ZenMethod
	public static IBlockPos getTopBlock(IWorld world, IBlockPos pos){
		World worldNative = CraftTweakerMC.getWorld(world);
		Chunk chunk = worldNative.getChunk(CraftTweakerMC.getBlockPos(pos));
		BlockPos blockpos;

		for (blockpos = new BlockPos(pos.getX(), chunk.getTopFilledSegment() + 16, pos.getZ()); blockpos.getY() >= 0; blockpos = blockpos.down()) {
			Material material = worldNative.getBlockState(blockpos).getMaterial();
			if (material != Material.AIR) {
				break;
			}
		}
		return CraftTweakerMC.getIBlockPos(blockpos);
	}
	@ZenMethod
	public static IBlockPos getTopSolidBlock(IWorld world, IBlockPos pos){
		World worldNative = CraftTweakerMC.getWorld(world);
		Chunk chunk = worldNative.getChunk(CraftTweakerMC.getBlockPos(pos));
		BlockPos blockpos;

		for (blockpos = new BlockPos(pos.getX(), chunk.getTopFilledSegment() + 16, pos.getZ()); blockpos.getY() >= 0; blockpos = blockpos.down()) {
			Material material = worldNative.getBlockState(blockpos).getMaterial();
			if (material != Material.AIR && !material.isLiquid()) {
				break;
			}
		}
		return CraftTweakerMC.getIBlockPos(blockpos);
	}

	@ZenMethod
	public static void playSound(IWorld world, String soundResourceLocation, String soundCategory, Position3f location, float volume, float pitch, @Optional boolean distanceDelay) {
		World mcWorld = CraftTweakerMC.getWorld(world);
		mcWorld.playSound(location.getX(), location.getY(), location.getZ(),
			Objects.requireNonNull(SoundEvent.REGISTRY.getObject(
				new ResourceLocation(soundResourceLocation))),
			SoundCategory.getByName(soundCategory),
			volume, pitch, distanceDelay);
	}

	@ZenMethod
	@Deprecated
	public static IEntityItem[] getEntityItemsWithinAABB(IWorld world, IAxisAlignedBB aabb){
		List<EntityItem> nativeList = CraftTweakerMC.getWorld(world).getEntitiesWithinAABB(EntityItem.class, ((AxisAlignedBB)aabb.getInternal()));
		return nativeList.stream().map(CraftTweakerMC::getIEntityItem).toArray(IEntityItem[]::new);
	}

	@ZenMethod
	public static IEntity[] getEntitiesWithinAABB(IWorld world, IAxisAlignedBB aabb, IEntityDefinition entity){
		List<? extends Entity> nativeList = CraftTweakerMC.getWorld(world).getEntitiesWithinAABB(((EntityEntry) entity.getInternal()).getEntityClass(), ((AxisAlignedBB)aabb.getInternal()));
		return nativeList.stream().map(CraftTweakerMC::getIEntity).toArray(IEntity[]::new);
	}
}
