package com.example.perfumestockmanager.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.perfumestockmanager.data.local.PerfumeEntity

@Composable //что это значит?
fun PerfumeListScreen(

    perfumes: List<PerfumeEntity>, //передаем сюда лист с пафрюмами

    isLoading: Boolean, //параметр загрузки

    onPerfumeClick: (Int) -> Unit, //кнопка клика по парфюму (функция принимает инт (айди) и ничего не отдает)

    onAddClick: () -> Unit, //кнопка клика по add perfume

    onHistoryClick: () -> Unit //кнопка клика по истории (функции ничего не принимают и не отдают)

) {

    Column( //тут ниже все понятно
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Perfume Stock Manager",
            style =
                MaterialTheme.typography.headlineSmall
        )


        Row( //тут строка с двумя кнопками эдд и история
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    bottom = 12.dp
                ),
            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = onAddClick,
                modifier = Modifier.weight(1f)
            ) {

                Text("Add perfume")
            }


            Button(
                onClick = onHistoryClick,
                modifier = Modifier.weight(1f)
            ) {

                Text("History")
            }
        }


        when { //если идет процесс загрузки

            isLoading -> {

                Column(
                    modifier =
                        Modifier.fillMaxSize(),
                    horizontalAlignment =
                        Alignment.CenterHorizontally,
                    verticalArrangement =
                        Arrangement.Center
                ) {

                    CircularProgressIndicator() //то вот такой индикатор
                }
            }


            perfumes.isEmpty() -> { //или если лист передали пустым

                Column(
                    modifier =
                        Modifier.fillMaxSize(),
                    horizontalAlignment =
                        Alignment.CenterHorizontally,
                    verticalArrangement =
                        Arrangement.Center
                ) {

                    Text(
                        text =
                            "No perfumes yet", //то пишем что нет парфюмов
                        style =
                            MaterialTheme
                                .typography
                                .bodyLarge
                    )

                    Text(
                        text =
                            "Add your first perfume to start tracking stock.", //ну и это тоже пишем
                        modifier =
                            Modifier.padding(
                                top = 8.dp
                            )
                    )
                }
            }


            else -> { //если парфюмы все таки и есть и загрузка не идет, то

                LazyColumn {

                    items( //какие есть айтемы
                        items = perfumes,

                        key = {
                                perfume -> //ключ каждого парфюма это его айди
                            perfume.id
                        }

                    ) { perfume ->

                        PerfumeCard(
                            perfume = perfume, //у каждого парфюма есть кликабельность

                            onClick = { //и если кликнули то вызовется функция onPerfumeClick с айди данного парфюма
                                onPerfumeClick(
                                    perfume.id
                                )
                            }
                            // я думаю что вызовется вот эта функция
                            //onPerfumeClick = {
                            //                                        perfumeId ->
                            //
                            //                                    navController
                            //                                        .navigate(
                            //                                            "detail/$perfumeId"
                            //                                        )
                            //                                },
                            //и нас перенесет на экран detail/1 например
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun PerfumeCard( //отдельно каждая карточка парфюма

    perfume: PerfumeEntity, //принимает параметр perfume тип данных которого описан в классе PerfumeEntity

    onClick: () -> Unit

) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 6.dp
            )
            .clickable(
                onClick = onClick
            )
    ) {

        Column(
            modifier =
                Modifier.padding(16.dp)
        ) {

            Text(
                text =
                    "${perfume.brand} ${perfume.name}",
                style =
                    MaterialTheme
                        .typography
                        .titleMedium
            )


            Text(
                text =
                    "${perfume.remainingMl} / ${perfume.volumeMl} ml",
                modifier =
                    Modifier.padding(
                        top = 6.dp
                    )
            )


            Text(
                text =
                    "Status: ${
                        prettyStatus(
                            perfume.status
                        )
                    }"
            )
        }
    }
}


private fun prettyStatus(
    status: String
): String {

    return when (status) {

        "owned" -> "Owned"

        "wishlist" -> "Wishlist"

        "want_to_test" -> "Want to test"

        else -> status
    }
}