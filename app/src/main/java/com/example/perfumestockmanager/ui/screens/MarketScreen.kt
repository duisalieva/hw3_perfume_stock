package com.example.perfumestockmanager.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.perfumestockmanager.data.remote.MarketProductDto

@Composable
fun MarketScreen(
    perfumes: List<MarketProductDto>,
    isLoading: Boolean,
    errorMessage: String?,
    onRetry: () -> Unit,
    onBack: () -> Unit
) {

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),

        verticalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Perfume Market"
        )

        Button(
            onClick = onBack
        ) {
            Text("Back")
        }

        when {

            isLoading -> {

                CircularProgressIndicator()
            }

            errorMessage != null -> {

                Text(
                    text =
                        "Error: $errorMessage"
                )

                Button(
                    onClick = onRetry
                ) {
                    Text("Retry")
                }
            }

            perfumes.isEmpty() -> {

                Text(
                    text =
                        "No market perfumes found."
                )
            }

            else -> {

                LazyColumn(
                    verticalArrangement =
                        Arrangement.spacedBy(
                            12.dp
                        )
                ) {

                    items(
                        items = perfumes,
                        key = { perfume ->
                            perfume.id
                        }
                    ) { perfume ->

                        MarketPerfumeCard(
                            perfume = perfume
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MarketPerfumeCard(
    perfume: MarketProductDto
) {

    Card(
        modifier =
            Modifier.fillMaxWidth()
    ) {

        Column(
            modifier =
                Modifier.padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(
                    6.dp
                )
        ) {

            Text(
                text =
                    perfume.brand
                        ?: "Unknown brand"
            )

            Text(
                text =
                    perfume.title
            )

            Text(
                text =
                    "$${perfume.price}"
            )

            Text(
                text =
                    "Rating: ${perfume.rating}"
            )

            Text(
                text =
                    "Stock: ${perfume.stock}"
            )
        }
    }
}