package com.example.quranappph1.data

sealed class Screen(val route: String) {
    object Home: Screen(route = "home")

    object OnBoarding1: Screen(route = "onBoarding1")

    object OnBoarding2: Screen(route = "onBoarding2")

    object OnBoarding3: Screen(route = "onBoarding3")

    object OnBoarding4: Screen(route = "onBoarding4")

    object Read: Screen(route = "read?&surahNumber={surahNumber}&juzNumber={juzNumber}&pageNumber={pageNumber}&index={index}"){
        fun createRoute(
            surahNumber: Int?,
            juzNumber: Int?,
            pageNumber: Int?,
            index: Int?
        ): String{
            return "read?&surahNumber=${surahNumber}&juzNumber=${juzNumber}&pageNumber=${pageNumber}&index=${index}"
        }
    }
    object Qibla: Screen(route = "qibla")

    object Settings: Screen(route = "settings")

    object waktu: Screen(route = "waktu")

    object bookmarks: Screen(route = "bookmarks")

    object Search: Screen(route = "search")
}
