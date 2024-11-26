package presentation.screen.main_screen

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cafe.adriel.voyager.core.screen.Screen
import offer.composeapp.generated.resources.Res
import offer.composeapp.generated.resources.star
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.fragments.home.HomeScreen
import presentation.fragments.MoreScreen
import presentation.fragments.policies.MyPoliciesScreen
import utils.AppColors


class MyHomeScreen : Screen {

    @OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val navController = rememberNavController()
        val items = listOf(BottomNavItem.Home, BottomNavItem.MyPolicies, BottomNavItem.More)

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { /* Handle top left icon click */ }) {
                            Icon(
                                painter = painterResource(Res.drawable.star),
                                contentDescription = "Menu"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Handle top right icon click */ }) {
                            Icon(Icons.Filled.MoreVert, contentDescription = "More")
                        }
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) { innerPadding ->
            // NavHost to handle screen navigation
            NavHost(
                navController = navController,
                startDestination = BottomNavItem.Home.route,
                modifier = Modifier.padding(innerPadding),
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(100)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(100)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(100)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
                    )
                }
            ) {
                composable(BottomNavItem.Home.route) { HomeScreen() }
                composable(BottomNavItem.MyPolicies.route) { MyPoliciesScreen() }
                composable(BottomNavItem.More.route) { MoreScreen() }
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun BottomNavigationBar(navController: NavController) {
        val items = listOf(BottomNavItem.Home, BottomNavItem.MyPolicies, BottomNavItem.More)
        val currentBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry.value?.destination?.route

        NavigationBar(
            containerColor = Color.White
        ) {
            items.forEach { screen ->
                NavigationBarItem(
                    //label = { Text(screen.label) },
                    selected = currentRoute == screen.route,
                    onClick = {
                        if (currentRoute != screen.route) {
                            navController.navigate(screen.route) {
                                // Set the start destination route, not ID
                                popUpTo(BottomNavItem.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    //icon = { Icon(painter = painterResource(screen.icon) , contentDescription = null) },
                    icon = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Transparent, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painter = painterResource(screen.icon),
                                    contentDescription = screen.label
                                )
                                Text(screen.label)
                            }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AppColors.AppColor,
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = AppColors.AppColor,
                        unselectedTextColor = Color.Gray
                    )
                )
            }
        }
    }

    // Bottom Navigation Items
    sealed class BottomNavItem @OptIn(ExperimentalResourceApi::class) constructor(
        val route: String,
        val icon: DrawableResource,
        val label: String
    ) {
        @OptIn(ExperimentalResourceApi::class)
        object Home : BottomNavItem("home", Res.drawable.star, "Home")

        @OptIn(ExperimentalResourceApi::class)
        object MyPolicies : BottomNavItem("my_policies", Res.drawable.star, "My Policies")

        @OptIn(ExperimentalResourceApi::class)
        object More : BottomNavItem("more", Res.drawable.star, "More")
    }
}


