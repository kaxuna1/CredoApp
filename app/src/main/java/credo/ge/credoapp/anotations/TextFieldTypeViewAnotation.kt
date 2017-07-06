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
                                            val required: Boolean = false,
                                            val requiredForSave: Boolean = false,
                                            val mask: String = "",
                                            val hint: String = "",
                                            val length:Int = 255,
                                            val page: Int = 0,
                                            val allowed_chars: String = "",
                                            val visibilityPatern: Array<String> = arrayOf(),
                                            val position: Int)
