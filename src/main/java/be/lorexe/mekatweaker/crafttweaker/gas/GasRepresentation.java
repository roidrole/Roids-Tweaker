package be.lorexe.mekatweaker.crafttweaker.gas;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import roidrole.roidtweaker.RoidTweaker;
import roidrole.roidtweaker.RoidTweakerConfig;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenRegister
@ModOnly("mekanism")
@ZenClass("mods.mekatweaker.Gas")
public class GasRepresentation {
	@ZenProperty
	public String translationKey = null;
	public ResourceLocation icon = new ResourceLocation("mekanism:blocks/liquid/liquid");
	public ResourceLocation iconFlow = icon;
	@ZenProperty
	public int color = 0xFFFFFFFF;

	@ZenProperty
	public boolean needFluid = false;
	@ZenProperty
	public boolean needBucket = true;

	@ZenProperty
	public Fluid fluid = null;

	private boolean fromFluid = false;

	@Deprecated
	public GasRepresentation(String unlocalizedName) {
		this.translationKey = unlocalizedName;
	}

	public GasRepresentation(Fluid fluid) {
		this.fluid = fluid;
		this.fromFluid = true;
	}
	public GasRepresentation(String unlocalizedName, String icon) {
		this.translationKey = unlocalizedName;
		if(icon.contains(":")){
			this.icon = new ResourceLocation(icon);
		} else {
			this.icon = new ResourceLocation(RoidTweakerConfig.mekanismCategory.defaultDomain, icon);
		}
	}
	public GasRepresentation(String unlocalizedName, int color) {
		this.translationKey = unlocalizedName;
		this.color = color;
	}

	@ZenMethod
	public void setIcon(String icon) {
		this.icon = new ResourceLocation(RoidTweakerConfig.mekanismCategory.defaultDomain, icon);
	}
	@ZenMethod
	public void setIcon(String domain, String icon) {
		this.icon = new ResourceLocation(domain, icon);
	}
	@ZenMethod
	public String getIcon() {
		return this.icon.toString();
	}
	
	@ZenMethod
	public void setIconFlowing(String icon) {
		this.iconFlow = new ResourceLocation(RoidTweakerConfig.mekanismCategory.defaultDomain, icon);
	}
	@ZenMethod
	public void setIconFlowing(String domain, String icon) {
		this.iconFlow = new ResourceLocation(domain, icon);
	}
	@ZenMethod
	public String getIconFlowing() {
		return this.iconFlow.toString();
	}

	@ZenMethod
	public void setUnlocalizedName(String translatationKey){
		this.translationKey = translatationKey;
	}
	@ZenMethod
	public String getUnlocalizedName(){
		return this.translationKey;
	}

	@ZenMethod
	public void register() {
		if(!this.fromFluid && this.needFluid) {
			if(this.fluid == null) {
				if(FluidRegistry.getFluid(translationKey) == null) {
					this.fluid = new Fluid(translationKey, this.icon, this.iconFlow, this.color);
					FluidRegistry.registerFluid(this.fluid);

					Block blockFluid = new BlockFluidClassic(this.fluid, Material.WATER);
					blockFluid.setRegistryName(new ResourceLocation(RoidTweakerConfig.mekanismCategory.defaultDomain, translationKey));
					ForgeRegistries.BLOCKS.register(blockFluid);

					RoidTweaker.proxy.registerFluidBlockRendering(this.fluid);

					if(this.needBucket) {
						FluidRegistry.addBucketForFluid(this.fluid);
					}
				}
				else {
					this.fluid = FluidRegistry.getFluid(translationKey);
				}
			}
			
			this.fromFluid = true;
		}
		
		Gas gas;
		if(this.fromFluid) {
			gas = new Gas(this.fluid);
			if(this.translationKey != null) {
				gas.setTranslationKey(this.translationKey);
			}
		} else {
			gas = new Gas(this.translationKey, this.icon);
		}
		gas.setTint(this.color);
		
		GasRegistry.register(gas);
	}
}
