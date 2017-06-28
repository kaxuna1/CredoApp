package credo.ge.credoapp.anotations

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by kaxge on 5/24/2017.
 */@Target(AnnotationTarget.CLASS)
@Retention(RetentionPolicy.RUNTIME)
annotation class ParserClassAnnotation ( val cols:Array<String> = arrayOf(),val dataModel:Boolean = true);