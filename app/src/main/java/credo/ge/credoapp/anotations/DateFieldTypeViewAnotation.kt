package credo.ge.credoapp.anotations

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by vakhtanggelashvili on 4/25/17.
 */
@Target(AnnotationTarget.FIELD)
@Retention(RetentionPolicy.RUNTIME)
annotation class DateFieldTypeViewAnotation (val name:String, val requiredForSave: Boolean = false, val page:Int=0,val position:Int)