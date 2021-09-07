package org.plixo.gsonplus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * for values, that should be saved
 * when {@link GsonPlusConfig}.shouldUseAnnotations() is true
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Serialize {
}
