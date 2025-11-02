package com.example.littlelemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            }, contentType = ContentType.Application.Json)
        }
    }

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        return try {
            val response: MenuNetwork =
                httpClient.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
                    .body()
            response.menu
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LittleLemonTheme {
                val sharedPrefs = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                val isUserOnboarded = sharedPrefs.getBoolean("is_user_onboarded", false)
                val navController = rememberNavController()
                MyNavigation(
                    navController = navController,
                    isUserOnboarded = isUserOnboarded
                )
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val menu = fetchMenu()
            if (menu.isNotEmpty()) {
                saveMenuToDatabase(menu)
            }
        }

    }
}
