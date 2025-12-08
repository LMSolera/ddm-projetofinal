package com.example.ddm_projetofinal.ui.components

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import com.example.ddm_projetofinal.model.Trip
import com.example.ddm_projetofinal.model.trip1
import com.example.ddm_projetofinal.model.trip3
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.ddm_projetofinal.ui.feature.expenses.convertMillisToDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDialog (
    tripInfo: Trip?,
    onConfirm: (Trip) -> Unit,
    onDismiss: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var datePickerState = rememberDatePickerState()

    var title by remember { mutableStateOf("") }

    var id: String = ""
    var ownerId: String = ""

    if (tripInfo != null) {
        id = tripInfo.id
        ownerId = tripInfo.ownerId
        title = tripInfo.title
        datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = SimpleDateFormat("dd/MM/yyyy").parse(tripInfo.date).time)
    }

    var selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = if (tripInfo == null) {"Criar nova viagem"} else {"Editar viagem"},
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    label = {
                        Text(
                            text = if (tripInfo == null) {"Título da viagem"} else {"Novo título"}
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField ( // Campo do Material 3, mt agradeço à eles :praying:
                    value = selectedDate,
                    onValueChange = {},
                    label = { Text("Data da despesa") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = !showDatePicker }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select date"
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                )

                if (showDatePicker) {
                    Popup(
                        onDismissRequest = { showDatePicker = false },
                        alignment = Alignment.Center,
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = 64.dp)
                                .shadow(elevation = 8.dp)
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(16.dp)
                        ) {
                            DatePicker(
                                state = datePickerState,
                                showModeToggle = false
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm (
                        Trip (
                            id = id,
                            ownerId = ownerId,
                            title = title,
                            date = selectedDate.toString()))
                  },
                enabled = if (!title.isEmpty()
                    && !selectedDate.isEmpty())
                {true} else {false}
            ) {
                Text(
                    if (tripInfo == null) {"Criar"} else {"Atualizar"}
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

fun dateMasking (date: String): String {
    if (date.length > 10) {
        return date.dropLast(date.length - 10)
    }
    return date
}

@Preview
@Composable
fun TripDialogPreview1 () {
    TripDialog (null, {}, {})
}

@Preview
@Composable
fun TripDialogPreview2 () {
    TripDialog (trip1, {}, {})
}

@Preview
@Composable
fun TripDialogPreview3 () {
    TripDialog (trip3, {}, {})
}
