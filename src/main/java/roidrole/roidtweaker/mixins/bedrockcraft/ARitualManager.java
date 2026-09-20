package roidrole.roidtweaker.mixins.bedrockcraft;

import bedrockcraft.ritual.RitualManager;
import bedrockcraft.ritual.RitualRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.throwables.MixinException;

import java.util.LinkedList;

@Mixin(RitualManager.class)
public interface ARitualManager {
	@Accessor
	static LinkedList<RitualRecipe> getRecipes(){
		throw new MixinException("Mixin "+ ARitualManager.class.getCanonicalName() + " didn't apply");
	}
}
