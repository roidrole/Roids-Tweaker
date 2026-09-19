package roidrole.roidtweaker.mixins.galacticraft;

import micdoodle8.mods.galacticraft.api.recipe.CompressorRecipes;
import net.minecraft.item.crafting.IRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.throwables.MixinException;

import java.util.List;

@Mixin(CompressorRecipes.class)
public interface ACompressorRecipes {
	@Accessor(remap = false)
	static List<IRecipe> getRecipes(){
		throw new MixinException("Mixin "+ ACompressorRecipes.class.getCanonicalName() + " didn't apply");
	}
	@Accessor(remap = false)
	static List<IRecipe> getRecipesAdventure(){
		throw new MixinException("Mixin "+ ACompressorRecipes.class.getCanonicalName() + " didn't apply");
	}
}
