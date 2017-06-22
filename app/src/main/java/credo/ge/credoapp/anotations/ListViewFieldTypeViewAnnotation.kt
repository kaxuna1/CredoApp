package credo.ge.credoapp.anotations

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by kaxge on 6/15/2017.
 */
@Target(AnnotationTarget.FIELD)
@Retention(RetentionPolicy.RUNTIME)
annotation class ListViewFieldTypeViewAnnotation (val name:String,val position:Int, val page:Int=0)