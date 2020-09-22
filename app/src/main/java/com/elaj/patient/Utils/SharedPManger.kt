package com.elaj.patient.Utils

import android.content.Context
import android.content.SharedPreferences

class SharedPManger(context: Context) {
    var preferences: SharedPreferences
    fun SetData(key: String?, value: String?) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun SetData(key: String?, value: Int) {
        val editor = preferences.edit()
        editor.putInt(key, value)
        editor.commit()
    }

    fun SetData(key: String?, value: Float) {
        val editor = preferences.edit()
        editor.putFloat(key, value)
        editor.commit()
    }

    fun SetData(key: String?, value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun getDataString(key: String?): String? {
        return preferences.getString(key, null)
    }

    fun getDataString(key: String?, the_default: String?): String? {
        return preferences.getString(key, the_default)
    }

    fun getDataInt(key: String?): Int {
        return preferences.getInt(key, 0)
    }

    fun getDataInt(key: String?, defult: Int): Int {
        return preferences.getInt(key, defult)
    }

    fun getDataFloat(key: String?): Float {
        return preferences.getFloat(key, 0f)
    }

    fun getDataBool(key: String?): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun getDataBool(key: String?, The_default: Boolean): Boolean {
        return preferences.getBoolean(key, The_default)
    }

    init {
        preferences = context.getSharedPreferences("hululSettings", 0)
    }
}
