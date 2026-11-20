package com.example.perfumestockmanager.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
fun AddEditPerfumeScreen(

    existingPerfume:
    PerfumeEntity? = null, //так как мы отрисовываем почти идентичные экраны при добавлении и при создании нового парфюма, этот параметр будет влиять на изменения между ними
// но я не понимаю самы проверку, что вообще значит эта строка? разве PerfumeEntity это не просто шаблон?
    onSave:
        (PerfumeEntity) -> Unit, //кнопка созранения, передаем туда почему то PerfumeEntity

    onBack:
        () -> Unit //кнопка назад

) {

    val perfumeId =
        existingPerfume?.id //если существует парфюм (то есть это экран edit, а не add) то сохраним айди в переменную


    var brand by rememberSaveable( //если поменяется айди
        perfumeId //и что такое ...inputs тоже не знаю...
    ) {

        mutableStateOf( //если изменяется что то то юай снова отрисовывается
            existingPerfume?.brand ?: "" //если пафрюм есть , то забираем brand, если нет то пишем ""
        )
    }


    var name by rememberSaveable( //везде и ниже то же самое, до конца не понимаю структуру
        perfumeId //rememberSaveable - конфигурация поворот экрана
    ) {

        mutableStateOf(
            existingPerfume?.name ?: ""
        )
    }


    var concentration by rememberSaveable(
        perfumeId
    ) {

        mutableStateOf(
            existingPerfume
                ?.concentration
                ?: ""
        )
    }


    var volumeText by rememberSaveable(
        perfumeId
    ) {

        mutableStateOf(
            existingPerfume
                ?.volumeMl
                ?.toString()
                ?: ""
        )
    }


    var remainingText by rememberSaveable(
        perfumeId
    ) {

        mutableStateOf(
            existingPerfume
                ?.remainingMl
                ?.toString()
                ?: ""
        )
    }


    var fragranceFamily by rememberSaveable(
        perfumeId
    ) {

        mutableStateOf(
            existingPerfume
                ?.fragranceFamily
                ?: ""
        )
    }


    var topNotes by rememberSaveable(
        perfumeId
    ) {

        mutableStateOf(
            existingPerfume
                ?.topNotes
                ?: ""
        )
    }


    var heartNotes by rememberSaveable(
        perfumeId
    ) {

        mutableStateOf(
            existingPerfume
                ?.heartNotes
                ?: ""
        )
    }


    var baseNotes by rememberSaveable(
        perfumeId
    ) {

        mutableStateOf(
            existingPerfume
                ?.baseNotes
                ?: ""
        )
    }


    var status by rememberSaveable(
        perfumeId
    ) {

        mutableStateOf(
            existingPerfume?.status
                ?: "owned"
        )
    }


    var errorMessage by rememberSaveable {

        mutableStateOf<String?>(
            null
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState() //создаёт и хранит текущее состояние прокрутки.
            )
            .padding(16.dp),
        verticalArrangement =
            Arrangement.spacedBy(12.dp) //по вертикали отступ 12дп?
    ) {

        Text(
            text =
                if (
                    existingPerfume == null //тут два варианта кнопки в зависимости от того, экран add или edit
                ) {
                    "Add perfume"
                } else {
                    "Edit perfume"
                }
        )


        OutlinedTextField(
            value = brand,
            onValueChange = {
                brand = it
            },
            label = {
                Text("Brand")
            },
            singleLine = true,
            modifier =
                Modifier.fillMaxWidth() //поле для бренда
        )


        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it //почему it?
            },
            label = {
                Text("Name")
            },
            singleLine = true, // не понимаю, типа только одна строка есть?
            modifier =
                Modifier.fillMaxWidth()
        )


        OutlinedTextField( //те же вопросы дальше, структура та же
            value = concentration,
            onValueChange = {
                concentration = it
            },
            label = {
                Text("Concentration")
            },
            singleLine = true,
            modifier =
                Modifier.fillMaxWidth()
        )


        OutlinedTextField(
            value = volumeText,
            onValueChange = {
                volumeText = it
            },
            label = {
                Text(
                    "Bottle volume, ml"
                )
            },
            keyboardOptions =
                KeyboardOptions(
                    keyboardType =
                        KeyboardType.Number
                ),
            singleLine = true,
            modifier =
                Modifier.fillMaxWidth()
        )


        OutlinedTextField(
            value = remainingText,
            onValueChange = {
                remainingText = it
            },
            enabled =
                status == "owned",
            label = {
                Text(
                    "Remaining, ml"
                )
            },
            keyboardOptions =
                KeyboardOptions(
                    keyboardType =
                        KeyboardType.Number
                ),
            singleLine = true,
            modifier =
                Modifier.fillMaxWidth()
        )


        Text("Status") //для статуса не поля, поэтому по другому


        Row(
            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            FilterChip( //три кнопки для трех статусов
                selected =
                    status == "owned",
                onClick = {
                    status = "owned"
                },
                label = {
                    Text("Owned")
                }
            )


            FilterChip(
                selected =
                    status == "wishlist",
                onClick = {
                    status = "wishlist"
                },
                label = {
                    Text("Wishlist")
                }
            )


            FilterChip(
                selected =
                    status ==
                            "want_to_test",
                onClick = {
                    status =
                        "want_to_test"
                },
                label = {
                    Text("Test")
                }
            )
        }


        OutlinedTextField( //продолжаем как раньше, причем OutlinedTextField это какая то встроенная функция для кнопки, незаполненной цветом?
            value = fragranceFamily,
            onValueChange = {
                fragranceFamily = it
            },
            label = {
                Text(
                    "Fragrance family"
                )
            },
            modifier =
                Modifier.fillMaxWidth()
        )


        OutlinedTextField(
            value = topNotes,
            onValueChange = {
                topNotes = it
            },
            label = {
                Text("Top notes")
            },
            modifier =
                Modifier.fillMaxWidth()
        )


        OutlinedTextField(
            value = heartNotes,
            onValueChange = {
                heartNotes = it
            },
            label = {
                Text("Heart notes")
            },
            modifier =
                Modifier.fillMaxWidth()
        )


        OutlinedTextField(
            value = baseNotes,
            onValueChange = {
                baseNotes = it
            },
            label = {
                Text("Base notes")
            },
            modifier =
                Modifier.fillMaxWidth()
        )


        if (
            errorMessage != null
        ) {

            Text(
                text =
                    errorMessage!!
            )
        }


        Button( //отрисовывем кнопку сохранения парфюма или изменений
            modifier =
                Modifier.fillMaxWidth(),

            onClick = {

                val volume =
                    volumeText
                        .toIntOrNull()


                val remaining =

                    if (
                        status == "owned"
                    ) {

                        remainingText
                            .toIntOrNull()

                    } else {

                        0
                    }


                errorMessage = when {

                    brand.isBlank() ->

                        "Brand is required"


                    name.isBlank() ->

                        "Name is required"


                    volume == null ||
                            volume <= 0 ->

                        "Bottle volume must be greater than 0"


                    remaining == null ->

                        "Enter a valid remaining volume"


                    remaining < 0 ->

                        "Remaining volume cannot be negative"


                    remaining > volume ->

                        "Remaining volume cannot exceed bottle volume"


                    else -> null
                }


                if (
                    errorMessage == null
                ) {

                    val perfume = //сохраняем весь парфюм
                        PerfumeEntity( //как обьект класса PerfumeEntity, то есть по шаблону получается

                            id =
                                existingPerfume
                                    ?.id
                                    ?: 0,

                            brand =
                                brand.trim(),

                            name =
                                name.trim(), //не знаю что значит trim - забрать из поля?

                            concentration =
                                concentration
                                    .trim()
                                    .ifBlank {
                                        null
                                    },

                            volumeMl =
                                volume!!,

                            remainingMl =
                                remaining!!,

                            fragranceFamily =
                                fragranceFamily
                                    .trim()
                                    .ifBlank {
                                        null
                                    },

                            topNotes =
                                topNotes
                                    .trim()
                                    .ifBlank {
                                        null
                                    },

                            heartNotes =
                                heartNotes
                                    .trim()
                                    .ifBlank {
                                        null
                                    },

                            baseNotes =
                                baseNotes
                                    .trim() //трим убирает пробелы
                                    .ifBlank {
                                        null
                                    },

                            status =
                                status
                        )


                    onSave(perfume)
                }
            }
        ) {

            Text(
                if (
                    existingPerfume == null
                ) {
                    "Save perfume"
                } else {
                    "Save changes"
                }
            )
        }


        OutlinedButton(
            onClick = onBack,
            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text("Back")
        }
    }
}