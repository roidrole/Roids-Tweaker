package alfinivia.integration.crafttweaker.crossmod;

import blusunrize.immersiveengineering.api.ApiUtils;
import blusunrize.immersiveengineering.api.crafting.IngredientStack;
import blusunrize.immersiveengineering.api.tool.ChemthrowerHandler;
import blusunrize.immersiveengineering.api.tool.ChemthrowerHandler.ChemthrowerEffect;
import blusunrize.immersiveengineering.api.tool.RailgunHandler;
import blusunrize.immersiveengineering.common.util.compat.crafttweaker.CraftTweakerHelper;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.potions.IPotionEffect;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import crafttweaker.api.world.IWorld;
import crafttweaker.mc1120.liquid.MCLiquidStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;

@ModOnly("immersiveengineering")
@ZenClass("mods.alfinivia.ImmersiveEngineering")
@ZenRegister
public class ImmersiveEngineering {

    @ZenMethod
    public static void addChemthrowerEffect(ILiquidStack liquid, boolean isGas, boolean isFlammable, String source, float damage) {
        DamageSource damageSource = new DamageSource(source);
        addChemthrowerEffect(
            liquid,
            isGas,
            isFlammable,
            new ChemthrowerHandler.ChemthrowerEffect_Damage(damageSource, damage)
        );
    }

    @ZenMethod
    public static void addChemthrowerEffect(ILiquidStack liquid, boolean isGas, boolean isFlammable, String source, float damage, IPotionEffect[] effects) {
        DamageSource damageSource = source == null ? null : new DamageSource(source);
        PotionEffect[] actualEffects = new PotionEffect[effects.length];
        for(int i = 0; i < effects.length; i++) {
            actualEffects[i] = CraftTweakerMC.getPotionEffect(effects[i]);
        }
        addChemthrowerEffect(
            liquid,
            isGas,
            isFlammable,
            new ChemthrowerHandler.ChemthrowerEffect_Potion(damageSource, damage, actualEffects)
        );
    }

    @ZenMethod
    public static void addChemthrowerEffect(ILiquidStack liquid, boolean isGas, boolean isFlammable, IChemEntityEffect entityEffect, IChemBlockEffect blockEffect) {
        addChemthrowerEffect(
            liquid,
            isGas,
            isFlammable,
            new CustomChemEffect(entityEffect,blockEffect)
        );
    }

    public static void addChemthrowerEffect(ILiquidStack liquid, boolean isGas, boolean isFlammable, ChemthrowerEffect effect) {
        Fluid fluid = CraftTweakerMC.getLiquidStack(liquid).getFluid();
        ChemthrowerHandler.registerEffect(fluid, effect);
        if(isGas && !fluid.isGaseous()) {
            ChemthrowerHandler.registerGas(fluid);
        }
        if(isFlammable) {
            ChemthrowerHandler.registerFlammable(fluid);
        }
    }

    @ZenMethod
    public static void addRailgunBullet(IIngredient item, float damage, float gravity, int[][] colorMap) {
        addRailgunBullet(item, damage, gravity, null, colorMap);
    }

    @ZenMethod
    public static void addRailgunBullet(IIngredient item, float damage, float gravity, IRailgunImpact effect, int[][] colorMap) {
        RailgunHandler.RailgunProjectileProperties properties;
        if(effect != null) {
            properties = new CustomRailgunProperties(damage, gravity, effect);
        }
        else {
            properties = new RailgunHandler.RailgunProjectileProperties(damage, gravity);
        }
        properties.setColourMap(colorMap);
        IngredientStack stack = ApiUtils.createIngredientStack(CraftTweakerHelper.toObject(item));
        RailgunHandler.projectilePropertyMap.add(Pair.of(stack, properties));
    }

    public static class CustomChemEffect extends ChemthrowerEffect {
        IChemEntityEffect entityEffect;
        IChemBlockEffect blockEffect;

        public CustomChemEffect(IChemEntityEffect entityEffect, IChemBlockEffect blockEffect) {
            this.entityEffect = entityEffect;
            this.blockEffect = blockEffect;
        }

        //Because we want the FluidStack option to be the default
        @Override
        public void applyToEntity(EntityLivingBase entityLivingBase, @Nullable EntityPlayer entityPlayer, ItemStack itemStack, Fluid fluid) {
            applyToEntity(
                entityLivingBase,
                entityPlayer,
                itemStack,
                new FluidStack(fluid,1)
            );
        }

        @Override
        public void applyToEntity(EntityLivingBase entityLivingBase, @Nullable EntityPlayer entityPlayer, ItemStack itemStack, FluidStack fluid) {
            entityEffect.apply(
                CraftTweakerMC.getIEntityLivingBase(entityLivingBase),
                CraftTweakerMC.getIPlayer(entityPlayer),
                CraftTweakerMC.getIItemStack(itemStack),
                CraftTweakerMC.getILiquidStack(fluid)
            );
        }

        //Because we want the FluidStack option to be the default
        @Override
        public void applyToBlock(World world, RayTraceResult rayTraceResult, @Nullable EntityPlayer entityPlayer, ItemStack itemStack, Fluid fluid) {
            applyToBlock(
                world,
                rayTraceResult,
                entityPlayer,
                itemStack,
                new FluidStack(fluid,1)
            );
        }

        @Override
        public void applyToBlock(World world, RayTraceResult rayTraceResult, @Nullable EntityPlayer entityPlayer, ItemStack itemStack, FluidStack fluid) {
            BlockPos pos = rayTraceResult.getBlockPos();
            EnumFacing facing = rayTraceResult.sideHit;

            blockEffect.apply(
                CraftTweakerMC.getIWorld(world),
                CraftTweakerMC.getIBlockPos(pos),
                CraftTweakerMC.getIFacing(facing),
                CraftTweakerMC.getIPlayer(entityPlayer),
                CraftTweakerMC.getIItemStack(itemStack),
                new MCLiquidStack(fluid)
            );
        }
    }

    public static class CustomRailgunProperties extends RailgunHandler.RailgunProjectileProperties {
        IRailgunImpact impact;

        public CustomRailgunProperties(double damage, double gravity, IRailgunImpact impact) {
            super(damage, gravity);
            this.impact = impact;
        }

        @Override
        public boolean overrideHitEntity(Entity entityHit, Entity shooter) {
            return impact.apply(
                CraftTweakerMC.getIEntity(entityHit),
                CraftTweakerMC.getIEntity(shooter)
            );
        }
    }

    /*
     * Functional Interfaces
     */

    @ModOnly("immersiveengineering")
    @ZenClass("mods.alfinivia.IRailgunImpact")
    @ZenRegister
    public interface IRailgunImpact {
        boolean apply(IEntity target, IEntity shooter);
    }

    @ModOnly("immersiveengineering")
    @ZenClass("mods.alfinivia.IChemEntityEffect")
    @ZenRegister
    public interface IChemEntityEffect {
        void apply(IEntityLivingBase target, IPlayer shooter, IItemStack thrower, ILiquidStack fluid);
    }

    @ModOnly("immersiveengineering")
    @ZenClass("mods.alfinivia.IChemBlockEffect")
    @ZenRegister
    public interface IChemBlockEffect {
        void apply(IWorld world, IBlockPos pos, IFacing facing, IPlayer entityPlayer, IItemStack itemStack, ILiquidStack fluid);
    }
}
