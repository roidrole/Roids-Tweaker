package roidrole.roidtweaker.mods.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.fml.common.registry.EntityEntry;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenExpansion("crafttweaker.entity.IEntity")
@SuppressWarnings("unused")
public class IEntityExpansion {
	@ZenMethod
	public static boolean hasDefinition(IEntity instance, IEntityDefinition definition){
		return CraftTweakerMC.getEntity(instance).getClass() == ((EntityEntry)definition.getInternal()).getEntityClass();
	}
}
