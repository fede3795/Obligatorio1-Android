package com.silverafederico.obligatorio1_android

import android.content.Context
import android.content.SharedPreferences
import com.silverafederico.obligatorio1_android.Entities.Note

class PreferenceManager(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE)

    private val notes: SharedPreferences = context.getSharedPreferences("NotesPreferences", Context.MODE_PRIVATE)

    fun saveCredentials(username: String, password: String) {
        val editor = preferences.edit()
        editor.putString("username", username)
        editor.putString("password", password)
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

    fun clearCredentials() {
        val editor = preferences.edit()
        editor.remove("username")
        editor.remove("password")
        editor.apply()
    }

    fun saveNotes(noteList: List<Note>){
        val editor = notes.edit()
    }
}