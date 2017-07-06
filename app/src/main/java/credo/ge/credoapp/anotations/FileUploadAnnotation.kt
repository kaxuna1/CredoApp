package credo.ge.credoapp.anotations

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by kaxge on 7/4/2017.
 */
@Target(AnnotationTarget.FIELD)
@Retention(RetentionPolicy.RUNTIME)
annotation class FileUploadAnnotation(val name: String,
                                      val page: Int = 0,
                                      val visibilityPatern: Array<String> = arrayOf(),
                                      val position: Int)