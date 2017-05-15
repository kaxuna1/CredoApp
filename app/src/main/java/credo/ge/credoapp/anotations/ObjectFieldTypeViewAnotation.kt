package credo.ge.credoapp.anotations

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */
@Target(AnnotationTarget.FIELD)
@Retention(RetentionPolicy.RUNTIME)
annotation class ObjectFieldTypeViewAnotation(val name:String,
                                              val displayField:String,
                                              val isMethod:Boolean,
                                              val type:String,
                                              val sqlData:Boolean,
                                              val canAddToDb:Boolean,
                                              val visibilityPatern:Array<String> = arrayOf(),
                                              val position:Int,
                                              val filterWith:String = "")