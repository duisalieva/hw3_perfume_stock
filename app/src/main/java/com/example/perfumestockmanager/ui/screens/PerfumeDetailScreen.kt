package com.example.perfumestockmanager.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.perfumestockmanager.data.local.PerfumeEntity

@Composable
fun PerfumeDetailScreen(

    perfume: PerfumeEntity?,

    onEditClick: () -> Unit,

    onDeleteConfirmed: () -> Unit,

    onRecordDecant:
        (Int, Int) -> Unit,

    onBack: () -> Unit

) {

    if (perfume == null) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                "Perfume not found"
            )


            Button(
                onClick = onBack,
                modifier =
                    Modifier.padding(
                        top = 16.dp
                    )
            ) {

                Text("Back")
            }
        }

        return
    }


    var showDeleteDialog
            by rememberSaveable {

                mutableStateOf(false)
            }


    var showDecantDialog
            by rememberSaveable {

                mutableStateOf(false)
            }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalArrangement =
            Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text =
                "${perfume.brand} ${perfume.name}",

            style =
                MaterialTheme
                    .typography
                    .headlineSmall
        )


        DetailValue(
            label = "Concentration",
            value =
                perfume.concentration
                    ?: "-"
        )


        DetailValue(
            label = "Bottle volume",
            value =
                "${perfume.volumeMl} ml"
        )


        DetailValue(
            label = "Remaining",
            value =
                "${perfume.remainingMl} ml"
        )


        DetailValue(
            label = "Fragrance family",
            value =
                perfume.fragranceFamily
                    ?: "-"
        )


        DetailValue(
            label = "Top notes",
            value =
                perfume.topNotes
                    ?: "-"
        )


        DetailValue(
            label = "Heart notes",
            value =
                perfume.heartNotes
                    ?: "-"
        )


        DetailValue(
            label = "Base notes",
            value =
                perfume.baseNotes
                    ?: "-"
        )


        DetailValue(
            label = "Status",
            value =
                perfume.status
        )


        Button(
            onClick = {
                showDecantDialog = true
            },

            enabled =
                perfume.status == "owned" &&
                        perfume.remainingMl > 0,

            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text("Record decant")
        }


        Button(
            onClick = onEditClick,

            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text("Edit")
        }


        Button(
            onClick = {
                showDeleteDialog = true
            },

            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text("Delete")
        }


        OutlinedButton(
            onClick = onBack,

            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text("Back")
        }
    }


    if (showDeleteDialog) {

        AlertDialog(

            onDismissRequest = {
                showDeleteDialog =
                    false
            },

            title = {
                Text(
                    "Delete perfume?"
                )
            },

            text = {
                Text(
                    "This perfume will be removed from your collection."
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        showDeleteDialog =
                            false

                        onDeleteConfirmed()
                    }
                ) {

                    Text("Delete")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {

                        showDeleteDialog =
                            false
                    }
                ) {

                    Text("Cancel")
                }
            }
        )
    }


    if (showDecantDialog) {

        DecantDialog(

            remainingMl =
                perfume.remainingMl,

            onDismiss = {

                showDecantDialog =
                    false
            },

            onConfirm = {
                    amount,
                    price ->

                showDecantDialog =
                    false

                onRecordDecant(
                    amount,
                    price
                )
            }
        )
    }
}


@Composable
private fun DetailValue(

    label: String,

    value: String

) {

    Column {

        Text(
            text = label,
            style =
                MaterialTheme
                    .typography
                    .labelMedium
        )

        Text(
            text = value,
            style =
                MaterialTheme
                    .typography
                    .bodyLarge
        )
    }
}


@Composable
private fun DecantDialog(

    remainingMl: Int,

    onDismiss: () -> Unit,

    onConfirm:
        (Int, Int) -> Unit

) {

    var amountText
            by rememberSaveable {

                mutableStateOf("")
            }


    var priceText
            by rememberSaveable {

                mutableStateOf("")
            }


    var error
            by rememberSaveable {

                mutableStateOf<String?>(
                    null
                )
            }


    AlertDialog(

        onDismissRequest =
            onDismiss,

        title = {

            Text("Record decant")
        },

        text = {

            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        8.dp
                    )
            ) {

                Text(
                    "Available: $remainingMl ml"
                )


                OutlinedTextField(
                    value =
                        amountText,

                    onValueChange = {
                        amountText = it
                    },

                    label = {
                        Text("Amount, ml")
                    },

                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType =
                                KeyboardType.Number
                        )
                )


                OutlinedTextField(
                    value =
                        priceText,

                    onValueChange = {
                        priceText = it
                    },

                    label = {
                        Text("Price, KZT")
                    },

                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType =
                                KeyboardType.Number
                        )
                )


                if (error != null) {

                    Text(
                        text = error!!
                    )
                }
            }
        },

        confirmButton = {

            TextButton(
                onClick = {

                    val amount =
                        amountText
                            .toIntOrNull()

                    val price =
                        priceText
                            .toIntOrNull()


                    error = when {

                        amount == null ||
                                amount <= 0 ->

                            "Enter a valid amount"


                        amount >
                                remainingMl ->

                            "Not enough perfume remaining"


                        price == null ||
                                price < 0 ->

                            "Enter a valid price"


                        else -> null
                    }


                    if (
                        error == null
                    ) {

                        onConfirm(
                            amount!!,
                            price!!
                        )
                    }
                }
            ) {

                Text("Record")
            }
        },

        dismissButton = {

            TextButton(
                onClick =
                    onDismiss
            ) {

                Text("Cancel")
            }
        }
    )
}