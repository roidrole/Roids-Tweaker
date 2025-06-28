package roidrole.roidtweaker.mixins.thermalexpansion;

import cofh.core.util.ItemWrapper;
import cofh.thermalexpansion.util.managers.machine.ExtruderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ExtruderManager.class)
public interface IExtruderManagerAccessor {
    @Accessor(value = "recipeMapIgneous", remap = false)
    static Map<ItemWrapper, ExtruderManager.ExtruderRecipe> getRecipeMapIgneous(){
        throw new AssertionError();
    }

    @Accessor(value = "recipeMapSedimentary",remap = false)
    static Map<ItemWrapper, ExtruderManager.ExtruderRecipe> getRecipeMapSedimentary(){
        throw new AssertionError();
    }
}
