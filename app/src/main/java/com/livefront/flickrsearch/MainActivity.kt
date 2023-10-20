package com.livefront.flickrsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.livefront.flickrsearch.data.network.FlickrPhoto
import com.livefront.flickrsearch.ui.screens.detail.DetailView
import com.livefront.flickrsearch.ui.screens.search.SearchView
import com.livefront.flickrsearch.ui.theme.FlickrSearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickrSearchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "searchScreen"
                    ) {
                        composable("searchScreen") {
                            SearchView(
                                modifier = Modifier.fillMaxSize(),
                                navController = navController
                            )
                        }
                        composable(
                            route = "detailScreen/{resultJson}",
                            arguments = listOf(navArgument("resultJson") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val resultJson = backStackEntry.arguments?.getString("resultJson")
                            val result = Gson().fromJson(resultJson, FlickrPhoto::class.java)
                            DetailView(
                                modifier = Modifier.fillMaxSize(),
                                photo = result,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}