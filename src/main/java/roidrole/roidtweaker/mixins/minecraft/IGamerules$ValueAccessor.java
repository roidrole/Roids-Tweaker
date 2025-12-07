package roidrole.roidtweaker.mixins.minecraft;

import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameRules.Value.class)
public interface IGamerules$ValueAccessor {
	@Accessor(value = "valueDouble")
	double getValueDouble();
}
