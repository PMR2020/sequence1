package com.example.tp1_todolist.model

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Preference<T>(val context: Context,val name: String?, val default: T)//采用泛型实现会方便我们处理各种类型的preferences
    : ReadWriteProperty<Any?, T> {
    val prefs : SharedPreferences by lazy {
        context.getSharedPreferences("default", Context.MODE_PRIVATE)
    }

//    private val prefs: SharedPreferences by lazy {App.instance.applicationContext.getSharedPreferences(name, Context.MODE_PRIVATE) }


    override fun getValue(thisRef: Any?, property: KProperty<*>)
            : T {
        return findPreference(name, default)
    }
    override fun setValue(thisRef: Any?, property: KProperty<*>,
                          value: T) {
        putPreference(name, value)
    }

    //读取 preferences
    private fun <T> findPreference(name: String?, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException(
                "This type can't be saved into Preferences")
        }!!
        res as T
    }
    //写入 preferences
    private fun <U> putPreference(name: String?, value: U) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type can't be saved into Preferences")
        }.apply()
    }
}