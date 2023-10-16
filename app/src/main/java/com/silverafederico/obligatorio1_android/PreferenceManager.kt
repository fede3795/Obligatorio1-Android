package com.silverafederico.obligatorio1_android

import android.content.Context
import android.content.SharedPreferences
import com.silverafederico.obligatorio1_android.Entities.Note

class PreferenceManager(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE)
    private val PREF_NAME = "MyAppPreferences"
    private val PREF_KEY_USERNAME = "username"
    private val PREF_KEY_PASSWORD = "password"

    private val context: Context = context

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveCredentials(username: String, password: String, userPreferences: SharedPreferences) {
        val editor = userPreferences.edit()
        editor.putString(PREF_KEY_USERNAME, username)
        editor.putString(PREF_KEY_PASSWORD, password)
        editor.apply()
    }

    fun getSavedCredentials(): Pair<String, String>? {
        val username = preferences.getString("username", null)
        val password = preferences.getString("password", null)

        return if (username != null && password != null) {
            Pair(username, password)
        } else {
            null
        }
    }

    fun clearCredentials(username: String) {
        val sharedPreferences = context.getSharedPreferences("User_$username", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

}