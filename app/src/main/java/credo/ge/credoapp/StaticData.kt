package credo.ge.credoapp

import com.orm.SugarRecord

import java.util.HashMap

import credo.ge.credoapp.models.Person

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */

object StaticData {
    internal var classHashMap = HashMap<String, Class<SugarRecord<*>>>()
    internal var data = HashMap<String,List<Any>>()
}
