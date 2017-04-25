package credo.ge.credoapp.anotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by vakhtanggelashvili on 4/25/17.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

public @interface ViewBind {
    String name();
}
