package com.compose.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.compose.app.enums.NavItem
import com.compose.app.enums.NavItem.Companion.activeItem
import com.compose.app.ui.NavScaffold
import com.core.presentation.theme.AppTheme
import com.core.presentation.util.LocalScaffoldState
import com.core.presentation.util.ScaffoldState
import com.core.presentation.util.rememberScreenType
import com.navigation.LocalNavigator
import com.navigation.NavRoute
import com.navigation.navigate
import com.navigation.navigateAsTopNav
import kotlinx.collections.immutable.toPersistentList

@Composable
fun App() {
    val navItems = remember { NavItem.entries }
    val screenType = rememberScreenType()
    val navController = rememberNavController()
    val scaffoldState = rememberSaveable(saver = ScaffoldState.Saver) { ScaffoldState() }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentNavigationItem by remember(currentBackStackEntry) {
        derivedStateOf { navItems.activeItem(currentBackStackEntry) }
    }

    AppTheme {
        CompositionLocalProvider(
            LocalNavigator provides navController,
            LocalScaffoldState provides scaffoldState,
        ) {
            Surface {
                NavScaffold(
                    state = scaffoldState,
                    navItems = navItems.toPersistentList(),
                    selected = currentNavigationItem,
                    screenType = screenType,
                    onNavigate = { navController.navigateAsTopNav(destination = it) },
                    primaryActionContent = {
                        FloatingActionButton(
                            onClick = { navController.navigate(destination = NavRoute.Test) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null
                            )
                        }
                    },
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = NavRoute.Splash,
                        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
                        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
                        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
                        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
                        builder = { buildNavigationRoutes() }
                    )
                }
            }
        }
    }
}