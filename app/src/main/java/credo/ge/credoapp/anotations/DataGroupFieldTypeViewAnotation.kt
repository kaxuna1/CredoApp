package credo.ge.credoapp.anotations

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by kaxge on 6/5/2017.
 */
@Target(AnnotationTarget.FIELD)
@Retention(RetentionPolicy.RUNTIME)
annotation class DataGroupFieldTypeViewAnotation(val name: String,
                                                 val multiField: Boolean = false,
                                                 val page: Int = 0,
                                                 val position: Int)