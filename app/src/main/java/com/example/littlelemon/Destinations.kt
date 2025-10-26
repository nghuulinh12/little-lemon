package com.example.littlelemon

interface Destinations {
    val route: String
//    val icon: Int
    val title: String
}

object Onboarding : Destinations {
    override val route = "Onboarding"
    override val title = "Onboarding"
}

object Home : Destinations {
    override val route = "Home"
//    override val icon = R.drawable.ic_home
    override val title = "Home"
}

object Menu : Destinations {
    override val route = "Menu"
//    override val icon = R.drawable.ic_menu
    override val title = "Menu"
}

object Profile : Destinations {
    override val route = "Profile"
//    override val icon = R.drawable.ic_location
    override val title = "Profile"
}