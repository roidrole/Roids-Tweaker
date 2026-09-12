package moretweaker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.Locale;

import java.lang.reflect.Field;
import java.util.Map;

public class ClientProxy extends CommonProxy {
   public void registerTranslationsInjectHandler() {
      try {
         ((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener((resourceManager) -> this.updateTranslations());
         System.out.println("REGISTERED");
      } catch (ClassCastException ignored) { }

   }

   private void updateTranslations() {
      try {
         Field localeField = I18n.class.getDeclaredField("i18nLocale");
         localeField.setAccessible(true);
         Locale locale = (Locale)localeField.get((Object)null);
         Field propertiesField = Locale.class.getDeclaredField("properties");
         propertiesField.setAccessible(true);
         Map<String, String> translationsMap = (Map<String, String>)propertiesField.get(locale);

         translationsMap.putAll(MoreTweaker.getRuntimeTranslations());
      } catch (ClassCastException | NoClassDefFoundError | ReflectiveOperationException ignored) { }

   }
}
