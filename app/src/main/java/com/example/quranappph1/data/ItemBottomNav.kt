package com.example.quranappph1.data

import com.example.quranappph1.R

data class ItemBottomNav(
    val iconNav: Int,
    val routeNav: String
)

val itemBottomList = listOf(
    ItemBottomNav(iconNav = R.drawable.book_icon, routeNav = Screen.Home.route),
    ItemBottomNav(iconNav = R.drawable.time_icon, routeNav = Screen.waktu.route),
    ItemBottomNav(iconNav = R.drawable.kaba_icon, routeNav = Screen.Qibla.route),
    ItemBottomNav(iconNav = R.drawable.settings_icon, routeNav = Screen.Settings.route)
)

//ItemBottomNav(iconNav = R.drawable.change_qari, routeNav = "baca")