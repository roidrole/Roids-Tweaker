package moretweaker;

import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.Locale;

public class ClientProxy extends CommonProxy {
   public void registerTranslationsInjectHandler() {
      try {
         ((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener((resourceManager) -> this.updateTranslations());
         System.out.println("REGISTERED");
      } catch (ClassCastException var2) {
      }

   }

   private void updateTranslations() {
      try {
         Field localeField = I18n.class.getDeclaredField("i18nLocale");
         localeField.setAccessible(true);
         Locale locale = (Locale)localeField.get((Object)null);
         Field propertiesField = Locale.class.getDeclaredField("properties");
         propertiesField.setAccessible(true);
         Map<String, String> translationsMap = (Map)propertiesField.get(locale);

         for(Map.Entry<String, String> entry : MoreTweaker.getRuntimeTranslations().entrySet()) {
            translationsMap.put(entry.getKey(), entry.getValue());
         }
      } catch (ClassCastException | NoClassDefFoundError | ReflectiveOperationException var7) {
      }

   }
}
