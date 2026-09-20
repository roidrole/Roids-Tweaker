package roidrole.roidtweaker.mixins.erebus;

import erebus.recipes.ComposterRegistry;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.throwables.MixinException;

import java.util.List;

@Mixin(ComposterRegistry.class)
public interface AccComposterRegistry {
	@Accessor(remap = false)
	static List<ItemStack> getBlacklist(){
		throw new MixinException("Mixin "+ AccComposterRegistry.class.getCanonicalName() + " didn't apply");
	}

	@Accessor(remap = false)
	static List<ItemStack> getRegistry(){
		throw new MixinException("Mixin "+ AccComposterRegistry.class.getCanonicalName() + " didn't apply");
	}
}
