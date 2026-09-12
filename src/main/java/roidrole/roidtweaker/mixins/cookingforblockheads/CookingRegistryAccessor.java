package roidrole.roidtweaker.mixins.cookingforblockheads;

import net.blay09.mods.cookingforblockheads.api.SinkHandler;
import net.blay09.mods.cookingforblockheads.api.ToastHandler;
import net.blay09.mods.cookingforblockheads.registry.CookingRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.throwables.MixinException;

import java.util.List;
import java.util.Map;

@Mixin(CookingRegistry.class)
public interface CookingRegistryAccessor {

	@Accessor
	static List<IRecipe> getRecipeList(){
		throw new MixinException("Mixin "+CookingRegistryAccessor.class.getCanonicalName() + " didn't apply");
	}

	@Accessor
	static Map<ItemStack, ItemStack> getOvenRecipes(){
		throw new MixinException("Mixin "+CookingRegistryAccessor.class.getCanonicalName() + " didn't apply");
	}

	@Accessor
	static Map<ItemStack, SinkHandler> getSinkHandlers(){
		throw new MixinException("Mixin "+CookingRegistryAccessor.class.getCanonicalName() + " didn't apply");
	}

	@Accessor
	static Map<ItemStack, ToastHandler> getToastHandlers(){
		throw new MixinException("Mixin "+CookingRegistryAccessor.class.getCanonicalName() + " didn't apply");
	}
}
