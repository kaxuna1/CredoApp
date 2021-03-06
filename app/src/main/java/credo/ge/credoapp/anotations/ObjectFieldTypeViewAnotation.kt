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
                                              val requiredForSave: Boolean = false,
                                              val sqlData:Boolean,
                                              val booleanFilterField:String = "",
                                              val booleanFilterVal:Boolean = false,
                                              val canAddToDb:Boolean,
                                              val visibilityPatern:Array<String> = arrayOf(""),
                                              val position:Int,
                                              val page:Int=0,
                                              val filterWith:String = "",
                                              val filterWithField:String = "")