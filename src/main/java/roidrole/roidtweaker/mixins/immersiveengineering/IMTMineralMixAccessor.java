package roidrole.roidtweaker.mixins.immersiveengineering;

import blusunrize.immersiveengineering.api.tool.ExcavatorHandler;
import blusunrize.immersiveengineering.common.util.compat.crafttweaker.Excavator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Excavator.MTMineralMix.class)
public interface IMTMineralMixAccessor {
	@Accessor(value = "mix", remap = false)
	ExcavatorHandler.MineralMix getMix();

	@Accessor(value = "weight", remap = false)
	int getWeight();

	@Accessor(value = "weight", remap = false)
	void setWeight(int weight);
}
