package alfinivia.integration.crafttweaker.crossmod;

import blusunrize.immersiveengineering.api.tool.BulletHandler;
import blusunrize.immersiveengineering.common.entities.EntityRevolvershot;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import crafttweaker.api.world.IWorld;
import crafttweaker.mc1120.world.MCFacing;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.ToIntFunction;

@ModOnly("immersiveengineering")
@ZenClass("mods.roidtweaker.immersiveengineering.BulletBuilder")
@ZenRegister
@SuppressWarnings("unused")
public class BulletBuilderRT {

	@ZenMethod
	public static BulletBuilderRT get(String name) {
		return new BulletBuilderRT(name);
	}

	private final String name;
	private ResourceLocation[] textures;
	private ItemStack casing = ItemStack.EMPTY;
	private IBulletEntityImpact entityImpact;
	private IBulletBlockImpact blockImpact;
	private IBulletFired fired;
	private boolean isProperCartridge = true;
	private boolean isValidForTurret = false;
	private int bulletAmount;
	private float gravity = 0;
	private float movementDecay = 0;
	private int tickLimit = 40;
	private final Map<Integer, ToIntFunction<ItemStack>> colorMap = new Object2ObjectArrayMap<>();

	public BulletBuilderRT(String name) {
		this.name = name;
	}

	@ZenMethod
	public void setTextures(String[] textures) {
		this.textures = new ResourceLocation[textures.length];
		for(int i = 0; i < textures.length; i++) {
			this.textures[i] = new ResourceLocation(textures[i]);
		}
	}

	@ZenMethod
	public void setCasing(IItemStack casing) {
		this.casing = CraftTweakerMC.getItemStack(casing);
	}


	@ZenMethod
	public void setTickLimit(int tickLimit) {
		this.tickLimit = tickLimit;
	}

	@ZenMethod
	public void setMovementDecay(float movementDecay) {
		this.movementDecay = movementDecay;
	}

	@ZenMethod
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	@ZenMethod
	public void setBulletAmount(int bulletAmount) {
		this.bulletAmount = bulletAmount;
	}

	@ZenMethod
	public void setValidForTurret(boolean validForTurret) {
		isValidForTurret = validForTurret;
	}

	@ZenMethod
	public void setProperCartridge(boolean properCartridge) {
		isProperCartridge = properCartridge;
	}

	@ZenMethod
	public void setColor(int layer, int color) {
		this.colorMap.put(layer, (stack) -> color);
	}

	@ZenMethod
	public void setColorNBT(int layer, String tag) {
		this.colorMap.put(layer, (stack -> getColorFromNBT(stack,tag)));
	}

	private int getColorFromNBT(ItemStack stack, String tag) {
		NBTTagCompound compound = stack.getTagCompound();

		if(compound == null || !compound.hasKey(tag)) {
			return 0xFFFFFFFF;
		} else {
			return compound.getInteger(tag);
		}
	}

	@ZenMethod
	public void impactBlock(IBulletBlockImpact impact) {
		this.blockImpact = impact;
	}

	@ZenMethod
	public void impactEntity(IBulletEntityImpact impact) {
		this.entityImpact = impact;
	}

	@ZenMethod
	public void onFired(IBulletFired fired) {
		this.fired = fired;
	}

	@ZenMethod
	public void build() {
		CraftTweakerAPI.logInfo("adding IE bullet "+this.name);
		if(textures == null || name == null) {
			CraftTweakerAPI.logError("Trying to build bullet "+this.name+" with a null texture.");
			return;
		}

		CustomBullet bullet = new CustomBullet(textures, casing);
		bullet.entityImpact = entityImpact;
		bullet.blockImpact = blockImpact;
		bullet.fired = fired;
		if(colorMap.isEmpty()){
			bullet.color = (stack, tintIndex) -> 0xFFFFFF;
		} else if(colorMap.size() == 1) {
			Map.Entry<Integer, ToIntFunction<ItemStack>> entry = colorMap.entrySet().iterator().next();
			int layer = entry.getKey();
			ToIntFunction<ItemStack> color = entry.getValue();
			bullet.color = (stack, tintIndex) -> {
				if(layer == tintIndex){
					return color.applyAsInt(stack);
				} else {
					return 0xFFFFFF;
				}
			};
		} else {
			bullet.color = (stack, tintIndex) -> colorMap.get(tintIndex).applyAsInt(stack);
		}
		bullet.isProperCartridge = isProperCartridge;
		bullet.isValidForTurret = isValidForTurret;
		bullet.projectileCount = bulletAmount;
		bullet.gravity = gravity;
		bullet.movementDecay = movementDecay;
		bullet.tickLimit = tickLimit;

		BulletHandler.registerBullet(name,bullet);
	}

	public static class CustomBullet implements BulletHandler.IBullet {
		ResourceLocation[] textures;
		boolean isProperCartridge = true;
		boolean isValidForTurret = false;
		int projectileCount;
		ItemStack casing;
		IItemColor color;
		IBulletEntityImpact entityImpact;
		IBulletBlockImpact blockImpact;
		IBulletFired fired;
		float gravity = 0;
		float movementDecay = 0;
		int tickLimit = 40;

		public CustomBullet(ResourceLocation[] textures, ItemStack casing) {
			this.textures = textures;
			this.casing = casing;
		}

		@Override
		public boolean isProperCartridge() {
			return isProperCartridge;
		}

		@Override
		public int getProjectileCount(@Nullable EntityPlayer shooter) {
			return projectileCount;
		}

		@Override
		public Entity getProjectile(@Nullable EntityPlayer shooter, ItemStack cartridge, Entity projectile, boolean charged) {
			if(projectile instanceof EntityRevolvershot) {
				EntityRevolvershot shot = (EntityRevolvershot) projectile;
				shot.setGravity(gravity);
				shot.setMovementDecay(movementDecay);
				shot.setTickLimit(tickLimit);
			}

			if(fired != null) {
				fired.apply(
					CraftTweakerMC.getIPlayer(shooter),
					CraftTweakerMC.getIItemStack(cartridge),
					CraftTweakerMC.getIEntity(projectile),
					charged
				);
			}

			return projectile;
		}

		@Override
		public void onHitTarget(World world, RayTraceResult rayTraceResult, @Nullable EntityLivingBase shooter, Entity bullet, boolean headshot) {
			if (rayTraceResult.entityHit != null && entityImpact != null) {
				entityImpact.apply(
					CraftTweakerMC.getIWorld(world),
					CraftTweakerMC.getIEntity(rayTraceResult.entityHit),
					CraftTweakerMC.getIEntityLivingBase(shooter),
					CraftTweakerMC.getIEntity(bullet),
					headshot
				);
			} else if (rayTraceResult.entityHit == null && blockImpact != null) {
				blockImpact.apply(
					CraftTweakerMC.getIWorld(world),
					CraftTweakerMC.getIBlockPos(rayTraceResult.getBlockPos()),
					new MCFacing(rayTraceResult.sideHit),
					CraftTweakerMC.getIEntityLivingBase(shooter),
					CraftTweakerMC.getIEntity(bullet),
					headshot
				);
			}
		}

		@Override
		public ItemStack getCasing(ItemStack itemStack) {
			return casing;
		}

		@Override
		public ResourceLocation[] getTextures() {
			return textures;
		}

		@Override
		public int getColour(ItemStack itemStack, int i) {
			if(color == null){
				return -1;
			}
			return color.colorMultiplier(itemStack, i);
		}

		@Override
		public boolean isValidForTurret() {
			return isValidForTurret;
		}
	}

	/*
	 * Functional interfaces
	 */

	@FunctionalInterface
	public interface IBulletFired {
		void apply(IPlayer shooter, IItemStack cartridge, IEntity projectile, boolean charged);
	}

	@FunctionalInterface
	public interface IBulletEntityImpact {
		void apply(IWorld world, IEntity target, IEntityLivingBase shooter, IEntity bullet, boolean headshot);
	}

	@FunctionalInterface
	public interface IBulletBlockImpact {
		void apply(IWorld world, IBlockPos pos, IFacing sidehit, IEntityLivingBase shooter, IEntity bullet, boolean headshot);
	}
}