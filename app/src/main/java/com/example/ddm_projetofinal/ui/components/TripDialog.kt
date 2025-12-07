package com.example.ddm_projetofinal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.ddm_projetofinal.model.Trip
import com.example.ddm_projetofinal.model.trip1
import com.example.ddm_projetofinal.model.trip3

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import kotlin.math.absoluteValue

@Composable
fun TripDialog (
    tripInfo: Trip?,
    onConfirm: (Trip) -> Unit,
    onDismiss: () -> Unit
) {

    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    if (tripInfo != null) {
        title = tripInfo.title
        date = tripInfo.date
    }

    AlertDialog(
        onDismissRequest = onDismiss,
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
                )

                OutlinedTextField(
                    value = date,
                    onValueChange = {
                        date = dateMasking(it)
                    },
                    label = {
                        Text (
                            if (tripInfo == null) {"Data da viagem"} else {"Nova data"}
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {onConfirm},
                enabled = if (!title.isEmpty()
                    && !date.isEmpty())
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
