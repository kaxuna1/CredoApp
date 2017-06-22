package credo.ge.credoapp.anotations

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by kakha on 4/27/2017.
 */

@Target(AnnotationTarget.FIELD)
@Retention(RetentionPolicy.RUNTIME)
annotation class ObjectsListFieldTypeViewAnottion (val name:String,
                                                   val displayField:String,
                                                   val isMethod:Boolean,
                                                   val type:String,
                                                   val sqlData:Boolean,
                                                   val page:Int=0,
                                                   val canAddToDb:Boolean,
                                                   val joinField:String,
                                                   val visibilityPatern:Array<String> = arrayOf(),
                                                   val position:Int,
                                                   val objectType:String = "",
                                                   val filter:String = "")