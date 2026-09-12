package roidrole.roidtweaker.mixins.matteroverdrive;

import matteroverdrive.data.recipes.InscriberRecipe;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InscriberRecipe.class)
public interface InscriberRecipeAccessor {
	@Accessor(remap = false)
	void setMain(ItemStack main);
	@Accessor(remap = false)
	void setSec(ItemStack sec);
	@Accessor(remap = false)
	void setOutput(ItemStack output);
	@Accessor(remap = false)
	void setEnergy(int energy);
	@Accessor(remap = false)
	void setTime(int time);
}
