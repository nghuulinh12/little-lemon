package com.example.littlelemon

import android.content.Context

object SharedPrefsHelper {
    private const val PREF_NAME = "my_prefs"

    private fun getPrefs(context: Context) =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // ✅ SAVE USER DATA
    fun saveUserData(context: Context, firstName: String, lastName: String, email: String) {
        with(getPrefs(context).edit()) {
            putString("first_name", firstName)
            putString("last_name", lastName)
            putString("email", email)
            putBoolean("is_user_onboarded", true)
            apply()
        }
    }

    fun getFirstName(context: Context): String? =
        getPrefs(context).getString("first_name", null)

    fun getLastName(context: Context): String? =
        getPrefs(context).getString("last_name", null)

    fun getEmail(context: Context): String? =
        getPrefs(context).getString("email", null)

    fun isUserLoggedIn(context: Context): Boolean =
        getPrefs(context).getBoolean("is_user_onboarded", false)

    fun clearData(context: Context) {
        getPrefs(context).edit().clear().apply()
    }
}


