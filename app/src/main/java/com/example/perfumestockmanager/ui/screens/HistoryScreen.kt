package com.example.perfumestockmanager.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.perfumestockmanager.data.local.DecantTransactionEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(

    transactions:
    List<DecantTransactionEntity>,

    onBack:
        () -> Unit

) {

    val totalMl =
        transactions.sumOf {
            it.amountMl
        }


    val totalRevenue =
        transactions.sumOf {
            it.priceKzt.toLong()
        }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Decant history",

            style =
                MaterialTheme
                    .typography
                    .headlineSmall
        )


        Text(
            text =
                "Total sold: $totalMl ml",

            modifier =
                Modifier.padding(
                    top = 12.dp
                )
        )


        Text(
            text =
                "Revenue: $totalRevenue ₸",

            modifier =
                Modifier.padding(
                    bottom = 12.dp
                )
        )


        OutlinedButton(
            onClick = onBack,

            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text("Back")
        }


        if (
            transactions.isEmpty()
        ) {

            Text(
                text =
                    "No decant transactions yet.",

                modifier =
                    Modifier.padding(
                        top = 24.dp
                    )
            )

        } else {

            LazyColumn(
                modifier =
                    Modifier.padding(
                        top = 12.dp
                    )
            ) {

                items(
                    items =
                        transactions,

                    key = {
                            transaction ->
                        transaction.id
                    }

                ) { transaction ->

                    TransactionCard(
                        transaction
                    )
                }
            }
        }
    }
}


@Composable
private fun TransactionCard(

    transaction:
    DecantTransactionEntity

) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 6.dp
            )
    ) {

        Column(
            modifier =
                Modifier.padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(
                    4.dp
                )
        ) {

            Text(
                text =
                    transaction.perfumeName,

                style =
                    MaterialTheme
                        .typography
                        .titleMedium
            )


            Text(
                text =
                    "${transaction.amountMl} ml"
            )


            Text(
                text =
                    "${transaction.priceKzt} ₸"
            )


            Text(
                text =
                    formatDate(
                        transaction
                            .createdAt
                    )
            )
        }
    }
}


private fun formatDate(
    timestamp: Long
): String {

    val formatter =
        SimpleDateFormat(
            "dd.MM.yyyy HH:mm",
            Locale.getDefault()
        )


    return formatter.format(
        Date(timestamp)
    )
}