package roidrole.roidtweaker.utils.actions;

import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import net.minecraftforge.registries.RegistryBuilder;
import roidrole.roidtweaker.Tags;
import roidrole.roidtweaker.utils.DeferredLoader;

public class RegistryClear implements DeferredLoader.IAction {
	private final IForgeRegistryModifiable<?> registry;
	private final String recipeType;

	public <K extends IForgeRegistryEntry<K>> RegistryClear(final Class<K> registerClass, String recipeType) {
		IForgeRegistry<?> registry = GameRegistry.findRegistry(registerClass);
		if(registry instanceof IForgeRegistryModifiable){
			this.registry = (IForgeRegistryModifiable<?>) registry;
		} else {
			CraftTweakerAPI.logError("Trying to clear an invalid registry: " + registerClass.getCanonicalName() + ". Please report to " + Tags.MOD_NAME+" dev");
			//Setting a dummy registry just to avoid crashing/other actions not being applied.
			this.registry = (IForgeRegistryModifiable<?>) new RegistryBuilder<>().allowModification().create();
		}
		this.recipeType = recipeType;
	}

	@Override
	public void load() {
		this.registry.clear();
		CraftTweakerAPI.logInfo("Clearing all" + recipeType + "recipes");
	}
}
