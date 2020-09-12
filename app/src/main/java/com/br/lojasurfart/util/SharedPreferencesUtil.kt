package com.br.lojasurfart.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil() {

    private lateinit var applicationContext: Application
    private lateinit var mContext: Context

    private lateinit var sharedPref: SharedPreferences

    companion object {
        private const val PREFS_NAME = "@LojaSurfArt"
    }

    constructor (application: Application) : this() {
        applicationContext = application
        mContext = application

        sharedPref = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    constructor(context: Context) : this() {
        mContext = context

        sharedPref = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun setValueString(KEY_NAME: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, value)
        editor.apply()
    }

    fun removeValue(KEY_NAME: String) {
        sharedPref.edit().remove(KEY_NAME).apply()
    }

    fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    fun clear() {
        sharedPref.edit().clear().apply()
    }

}