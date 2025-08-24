package roidrole.roidtweaker.mods.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.util.Position3f;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Objects;

@ZenExpansion("crafttweaker.world.IWorld")
@ZenRegister
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
}
