package com.example.perfumestockmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.perfumestockmanager.ui.PerfumeViewModel
import com.example.perfumestockmanager.ui.PerfumeViewModelFactory
import com.example.perfumestockmanager.ui.screens.AddEditPerfumeScreen
import com.example.perfumestockmanager.ui.screens.HistoryScreen
import com.example.perfumestockmanager.ui.screens.PerfumeDetailScreen
import com.example.perfumestockmanager.ui.screens.PerfumeListScreen
import com.example.perfumestockmanager.ui.theme.PerfumeStockManagerTheme
import com.example.perfumestockmanager.ui.screens.MarketScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )

        enableEdgeToEdge()


        val perfumeApplication =
            application as
                    PerfumeApplication


        val factory =
            PerfumeViewModelFactory(
                repository =
                    perfumeApplication
                        .repository
            )


        setContent {

            val viewModel:
                    PerfumeViewModel =
                viewModel(
                    factory = factory
                )


            val uiState
                    by viewModel
                        .uiState
                        .collectAsStateWithLifecycle()


            val navController =
                rememberNavController()


            val snackbarHostState =
                remember {
                    SnackbarHostState()
                }


            LaunchedEffect(
                uiState.message
            ) {

                val message =
                    uiState.message

                if (message != null) {

                    snackbarHostState
                        .showSnackbar(
                            message
                        )

                    viewModel
                        .clearMessage()
                }
            }


            PerfumeStockManagerTheme {

                Scaffold(

                    modifier =
                        Modifier.fillMaxSize(),

                    snackbarHost = {

                        SnackbarHost(
                            hostState =
                                snackbarHostState
                        )
                    }

                ) { innerPadding ->


                    NavHost(

                        navController =
                            navController,

                        startDestination =
                            "list",

                        modifier =
                            Modifier.padding(
                                innerPadding
                            )

                    ) {


                        composable(
                            route = "list"
                        ) {

                            PerfumeListScreen(

                                perfumes =
                                    uiState
                                        .perfumes,

                                isLoading =
                                    uiState
                                        .isLoading,

                                onPerfumeClick = {
                                        perfumeId ->

                                    navController
                                        .navigate(
                                            "detail/$perfumeId"
                                        )
                                },

                                onAddClick = {

                                    navController
                                        .navigate(
                                            "add"
                                        )
                                },

                                onHistoryClick = {

                                    navController
                                        .navigate(
                                            "history"
                                        )
                                },

                                onMarketClick = {

                                    navController
                                        .navigate("market")
                                }
                            )
                        }


                        composable(
                            route = "add"
                        ) {

                            AddEditPerfumeScreen(

                                existingPerfume =
                                    null,

                                onSave = {
                                        perfume ->

                                    viewModel
                                        .insertPerfume(
                                            perfume
                                        )

                                    navController
                                        .popBackStack()
                                },

                                onBack = {

                                    navController
                                        .popBackStack()
                                }
                            )
                        }


                        composable(

                            route =
                                "detail/{perfumeId}",

                            arguments =
                                listOf(

                                    navArgument(
                                        "perfumeId"
                                    ) {

                                        type =
                                            NavType
                                                .IntType
                                    }
                                )

                        ) {
                                backStackEntry ->


                            val perfumeId =
                                backStackEntry
                                    .arguments
                                    ?.getInt(
                                        "perfumeId"
                                    )
                                    ?: 0


                            val perfume =
                                uiState
                                    .perfumes
                                    .find {

                                        it.id ==
                                                perfumeId
                                    }


                            PerfumeDetailScreen(

                                perfume =
                                    perfume,

                                onEditClick = {

                                    navController
                                        .navigate(
                                            "edit/$perfumeId"
                                        )
                                },

                                onDeleteConfirmed = {

                                    if (
                                        perfume != null
                                    ) {

                                        viewModel
                                            .deletePerfume(
                                                perfume
                                            )

                                        navController
                                            .popBackStack()
                                    }
                                },

                                onRecordDecant = {
                                        amount,
                                        price ->

                                    if (
                                        perfume != null
                                    ) {

                                        viewModel
                                            .recordDecant(
                                                perfume =
                                                    perfume,

                                                amountMl =
                                                    amount,

                                                priceKzt =
                                                    price
                                            )
                                    }
                                },

                                onBack = {

                                    navController
                                        .popBackStack()
                                }
                            )
                        }


                        composable(

                            route =
                                "edit/{perfumeId}",

                            arguments =
                                listOf(

                                    navArgument(
                                        "perfumeId"
                                    ) {

                                        type =
                                            NavType
                                                .IntType
                                    }
                                )

                        ) {
                                backStackEntry ->


                            val perfumeId =
                                backStackEntry
                                    .arguments
                                    ?.getInt(
                                        "perfumeId"
                                    )
                                    ?: 0


                            val perfume =
                                uiState
                                    .perfumes
                                    .find {

                                        it.id ==
                                                perfumeId
                                    }


                            AddEditPerfumeScreen(

                                existingPerfume =
                                    perfume,

                                onSave = {
                                        updatedPerfume ->

                                    viewModel
                                        .updatePerfume(
                                            updatedPerfume
                                        )

                                    navController
                                        .popBackStack()
                                },

                                onBack = {

                                    navController
                                        .popBackStack()
                                }
                            )
                        }


                        composable(
                            route = "history"
                        ) {

                            HistoryScreen(

                                transactions =
                                    uiState
                                        .transactions,

                                onBack = {

                                    navController
                                        .popBackStack()
                                }
                            )
                        }
                        composable(
                            route = "market"
                        ) {
                            LaunchedEffect(Unit) {

                                if (
                                    uiState.marketPerfumes
                                        .isEmpty()
                                ) {

                                    viewModel
                                        .loadMarketPerfumes()
                                }
                            }

                            MarketScreen(
                                perfumes =
                                    uiState.marketPerfumes,

                                isLoading =
                                    uiState.isMarketLoading,

                                errorMessage =
                                    uiState.marketError,

                                onRetry = {
                                    viewModel
                                        .loadMarketPerfumes()
                                },

                                onBack = {
                                    navController
                                        .popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}