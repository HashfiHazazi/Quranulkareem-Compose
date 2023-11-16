package com.example.quranappph1

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quranappph1.data.Screen
import com.example.quranappph1.data.itemBottomList
import com.example.quranappph1.data.kotpref.SettingsPrefrences
import com.example.quranappph1.screens.BookMarksScreen
import com.example.quranappph1.screens.HomeScreen
import com.example.quranappph1.screens.OnBoardingScreen1
import com.example.quranappph1.screens.OnBoardingScreen2
import com.example.quranappph1.screens.OnBoardingScreen3
import com.example.quranappph1.screens.OnBoardingScreen4
import com.example.quranappph1.screens.PengaturanScreen
import com.example.quranappph1.screens.QiblaFinderScreen
import com.example.quranappph1.screens.ReadQuranScreen
import com.example.quranappph1.screens.SearchScreen
import com.example.quranappph1.screens.SplashScreen
import com.example.quranappph1.screens.WaktuSholat
import com.example.quranappph1.ui.theme.QuranAppPH1Theme
import com.example.quranappph1.utils.GlobalState

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            QuranAppPH1Theme (
                useDarkTheme = GlobalState.isDarkMode
            ){
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    var navController: NavHostController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    Scaffold(
                        modifier = Modifier,
                        bottomBar = {
                            if (itemBottomList.any { it.routeNav == currentRoute}) {
                                NavigationBar(
                                ) {
                                    itemBottomList.map { item ->
                                        NavigationBarItem(
                                            selected = currentRoute == item.routeNav,
                                            onClick = {
                                                navController.navigate(item.routeNav) {
                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    restoreState = true
                                                    launchSingleTop = true
                                                }
                                            },
                                            icon = {
                                                Icon(
                                                    modifier = Modifier.size(24.dp),
                                                    painter = painterResource(id = item.iconNav),
                                                    contentDescription = item.routeNav
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            startDestination = if (SettingsPrefrences.isOnBoarding && GlobalState.isOnBoarding == true) Screen.OnBoarding1.route else Screen.Home.route
                        ) {
                            composable(Screen.Home.route) {
                                HomeScreen(
                                    goToRead = { surahNumber, juzNumber, pageNumber, index->
                                        navController.navigate(
                                            Screen.Read.createRoute(
                                                surahNumber = surahNumber,
                                                juzNumber = juzNumber,
                                                pageNumber = pageNumber,
                                                index = index
                                            )
                                        )
                                    },
                                    goToBookmarks = {
                                        navController.navigate(
                                            Screen.bookmarks.route
                                        )
                                    },
                                    goToSearch = {
                                        navController.navigate(
                                            Screen.Search.route
                                        )
                                    }
                                )
                            }
                            composable(Screen.Search.route){
                                SearchScreen(
                                    goToRead = { surahNumber, juzNumber, pageNumber, index ->
                                        navController.navigate(
                                            Screen.Read.createRoute(
                                                surahNumber = surahNumber,
                                                juzNumber = juzNumber,
                                                pageNumber = pageNumber,
                                                index = index
                                            )
                                        )
                                    },
                                    goToHome = {
                                        navController.navigateUp()
                                    }
                                )
                            }
                            composable(Screen.waktu.route) {
                                WaktuSholat()
                            }

                            composable(Screen.Qibla.route) {
                                QiblaFinderScreen()
                            }

                            composable(Screen.Settings.route) {
                                PengaturanScreen()
                            }
                            composable(Screen.bookmarks.route) {
                                BookMarksScreen(
                                    goToHomeScreen = {
                                        navController.navigate(Screen.Home.route)
                                    },
                                    goToRead = { surahNumber, juzNumber, pageNumber, index ->
                                        navController.navigate(
                                            Screen.Read.createRoute(
                                                surahNumber = surahNumber,
                                                juzNumber = juzNumber,
                                                pageNumber = pageNumber,
                                                index = index
                                            )
                                        )
                                    }
                                )
                            }
                            composable(
                                Screen.Read.route,
                                arguments = listOf(
                                    navArgument("surahNumber") {
                                        type = NavType.IntType
                                        defaultValue = -1
                                    },
                                    navArgument("juzNumber") {
                                        type = NavType.IntType
                                        defaultValue = -1
                                    },
                                    navArgument("pageNumber") {
                                        type = NavType.IntType
                                        defaultValue = -1
                                    },
                                    navArgument("index") {
                                        type = NavType.IntType
                                        defaultValue = -1
                                    }
                                )
                            ) {
                                val surahNumber = it.arguments?.getInt("surahNumber") ?: -1
                                val juzNumber = it.arguments?.getInt("juzNumber") ?: -1
                                val pageNumber = it.arguments?.getInt("pageNumber") ?: -1
                                val index = it.arguments?.getInt("index") ?: -1
                                ReadQuranScreen(
                                    surahNumber = surahNumber,
                                    juzNumber = juzNumber,
                                    pageNumber = pageNumber,
                                    index = index,
                                    goBack = { navController.navigateUp() }
                                )
                            }
                            composable(Screen.OnBoarding1.route) {
                                OnBoardingScreen1(
                                    goToOnBoarding2 = {
                                        navController.navigate(Screen.OnBoarding2.route)
                                    },
                                    goToOnBoarding4 = {
                                        navController.navigate(Screen.OnBoarding4.route)
                                    }
                                )
                            }
                            composable(Screen.OnBoarding2.route) {
                                OnBoardingScreen2(
                                    goToOnBoarding1 = {
                                        navController.navigate(Screen.OnBoarding1.route)
                                    },
                                    goToOnBoarding3 = {
                                        navController.navigate(Screen.OnBoarding3.route)
                                    }
                                )
                            }
                            composable(Screen.OnBoarding3.route) {
                                OnBoardingScreen3(
                                    goToOnBoarding2 = {
                                        navController.navigate(Screen.OnBoarding2.route)
                                    },
                                    goToOnBoarding4 = {
                                        navController.navigate(Screen.OnBoarding4.route)
                                    }
                                )
                            }

                            composable(Screen.OnBoarding4.route){
                                OnBoardingScreen4(
                                    goToOnBoarding3 = {
                                     navController.navigate(Screen.OnBoarding3.route)
                                    },
                                    goToHome = {
                                        navController.navigate(Screen.Home.route)
                                    }
                                )
                            }
                            composable("splash") {
                                SplashScreen(navController = navController.navigate("onBoarding1"))
                            }
                        }
                    }
                }
            }
        }
    }
}