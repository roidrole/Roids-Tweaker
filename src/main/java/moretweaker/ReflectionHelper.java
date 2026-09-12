package moretweaker;

import java.lang.reflect.Constructor;

public class ReflectionHelper {
   private ReflectionHelper() {
   }

   public static <T> T createNested(Class<T> clazz, Object enclosing, Class<?>[] param_types, Object... params) {
      if (param_types.length != params.length) {
         throw new IllegalArgumentException("param_types.length != params.length");
      } else {
         try {
            Class<?>[] types = new Class[params.length + 1];
            Object[] args = new Object[params.length + 1];
            types[0] = enclosing.getClass();
            args[0] = enclosing;

            for(int i = 1; i < types.length; ++i) {
               types[i] = param_types[i - 1];
               args[i] = params[i - 1];
            }

            Constructor<T> c = clazz.getDeclaredConstructor(types);
            c.setAccessible(true);
            return c.newInstance(args);
         } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Could not create nested class: " + clazz.getName(), e);
         }
      }
   }
}
