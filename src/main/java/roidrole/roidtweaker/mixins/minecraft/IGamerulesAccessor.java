package roidrole.roidtweaker.mixins.minecraft;

import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.TreeMap;

@Mixin(GameRules.class)
public interface IGamerulesAccessor {
	@Accessor(value = "rules")
	TreeMap<String, GameRules.Value> getRules();
}
