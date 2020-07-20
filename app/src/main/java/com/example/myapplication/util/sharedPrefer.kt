package com.example.myapplication.util

import android.content.Context

class SharedPreferToken (context: Context){
    val TOKEN_KEY = "token_key"
    val PREFERENCE_TOKEN = "sharedPrefs"
    val preferense = context.getSharedPreferences(PREFERENCE_TOKEN, Context.MODE_PRIVATE)

    fun setToken(token:String){
        val shared = this.preferense
        val editor = shared.edit()
        var myToken = this.TOKEN_KEY
        editor.apply{
            putString(myToken, token)
        }.apply()
    }
    fun getToken() : String {
        val shared = this.preferense
        val savedString = shared.getString(this.TOKEN_KEY, "inexistente")
        return  savedString.toString()
    }

}