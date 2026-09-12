package roidrole.roidtweaker.mixins.matteroverdrive;

import matteroverdrive.data.recipes.InscriberRecipe;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InscriberRecipe.class)
public interface InscriberRecipeAccessor {
	@Accessor
	void setMain(ItemStack main);
	@Accessor
	void setSec(ItemStack sec);
	@Accessor
	void setOutput(ItemStack output);
	@Accessor
	void setEnergy(int energy);
	@Accessor
	void setTime(int time);
}
