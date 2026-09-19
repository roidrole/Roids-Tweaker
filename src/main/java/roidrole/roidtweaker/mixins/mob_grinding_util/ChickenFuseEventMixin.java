package roidrole.roidtweaker.mixins.mob_grinding_util;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import mob_grinding_utils.events.ChickenFuseEvent;
import moretweaker.mob_grinding_utils.ChickenFeed;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChickenFuseEvent.class)
public abstract class ChickenFuseEventMixin {
	@WrapOperation(
		method = "startChickenFuse",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/nbt/NBTTagCompound;getString(Ljava/lang/String;)Ljava/lang/String;"
		)
	)
	private String otherResLoc(
		NBTTagCompound instance,
		String key,
		Operation<String> original,
		@Local(argsOnly = true) LivingEvent event
	){
		String name = original.call(instance, key);
		ItemStack stack = ChickenFeed.spawnEggOverrides.get(name);
		if(stack == null) {
			return name;
		}
		event.getEntity().entityDropItem(stack.copy(), 0.0F);
		return null;
	}
}
