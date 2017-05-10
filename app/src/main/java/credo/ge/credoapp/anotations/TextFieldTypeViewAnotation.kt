package credo.ge.credoapp.anotations

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by vakhtanggelashvili on 4/25/17.
 */

@Target(AnnotationTarget.FIELD)
@Retention(RetentionPolicy.RUNTIME)
annotation class TextFieldTypeViewAnotation(val name: String,
                                            val defaultValue: String,
                                            val type: String,
                                            val mask:String="",
                                            val allowed_chars:String="",
                                            val visibilityPatern:Array<String> = arrayOf())
