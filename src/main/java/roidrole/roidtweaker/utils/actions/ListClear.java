package roidrole.roidtweaker.utils.actions;

import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import net.minecraftforge.registries.RegistryBuilder;
import roidrole.roidtweaker.Tags;
import roidrole.roidtweaker.utils.DeferredLoader;

import java.util.Collection;
import java.util.Map;

public abstract class ListClear implements DeferredLoader.IAction{

	public static String getMessage(String recipeType){
		return "Clearing all" + recipeType + "recipes";
	}

	public static ListClear from(Collection<?> collection){
		return new listClearSimple(collection);
	}
	public static ListClear from(Collection<?> collection, String recipeType){
		return new ListClearMessage(collection, recipeType);
	}
	public static ListClear from(Map<?, ?> map, String recipeType){
		return new ListClearMessage(map.entrySet(), recipeType);
	}
	public static <K extends IForgeRegistryEntry<K>> ListClear from(final Class<K> registerClass, String recipeType){
		return new RegistryClear(registerClass, recipeType);
	}

	private static class listClearSimple extends ListClear {
		private final Collection<?> collection;

		private listClearSimple(Collection<?> collection) {
			this.collection = collection;
		}

		@Override
		public void load() {
			this.collection.clear();
		}
	}

	private static class ListClearMessage extends ListClear {
		private final Collection<?> collection;
		private final String recipeType;

		private ListClearMessage(Collection<?> collection, String recipeType) {
			this.collection = collection;
			this.recipeType = recipeType;
		}

		@Override
		public void load() {
			this.collection.clear();
			CraftTweakerAPI.logInfo(ListClear.getMessage(recipeType));
		}
	}

	private static class RegistryClear extends ListClear {
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
}
