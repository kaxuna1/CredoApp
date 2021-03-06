package credo.ge.credoapp.anotations

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by kaxge on 6/13/2017.
 */
@Target(AnnotationTarget.FIELD)
@Retention(RetentionPolicy.RUNTIME)
annotation class ButtonFieldTypeViewAnnotation(val name: String,
                                               val page: Int = 0,
                                               val icon: Int = 0,
                                               val position: Int);