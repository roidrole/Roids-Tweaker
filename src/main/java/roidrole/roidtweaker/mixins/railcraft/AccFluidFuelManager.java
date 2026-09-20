package roidrole.roidtweaker.mixins.railcraft;

import mods.railcraft.api.fuel.FluidFuelManager;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.throwables.MixinException;

import java.util.Map;

@Mixin(FluidFuelManager.class)
public interface AccFluidFuelManager {
	@Accessor(remap = false)
	static Map<FluidStack, Integer> getBoilerFuel(){
		throw new MixinException("Mixin "+ AccFluidFuelManager.class.getCanonicalName() + " didn't apply");
	}
}
