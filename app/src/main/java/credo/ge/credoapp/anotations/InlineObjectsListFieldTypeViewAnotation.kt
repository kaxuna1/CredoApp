package credo.ge.credoapp.anotations

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by kaxge on 6/8/2017.
 */
@Target(AnnotationTarget.FIELD)
@Retention(RetentionPolicy.RUNTIME)
annotation class InlineObjectsListFieldTypeViewAnotation (val name:String,
                                                          val displayField:String,
                                                          val isMethod:Boolean,
                                                          val type:String,
                                                          val sqlData:Boolean,
                                                          val page:Int=0,
                                                          val canAddToDb:Boolean,
                                                          val joinField:String,
                                                          val visibilityPatern:Array<String> = arrayOf(),
                                                          val position:Int)