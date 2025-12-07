package com.example.ddm_projetofinal.ui.components

import android.text.Editable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ddm_projetofinal.model.Trip
import com.example.ddm_projetofinal.model.trip1
import com.example.ddm_projetofinal.model.trip2
import com.example.ddm_projetofinal.model.trip3

@Composable
fun TripCard (
    tripInfo: Trip,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    editable: Boolean,
) {
    Card (
        border = BorderStroke(1.dp, Color(0xFFC9C3CF)),
        colors = CardColors(
            Color(0xFFFCF5FD),
            Color(0xFF000000),
            Color(0xFF000000),
            Color(0xFF000000)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row (
            modifier = Modifier
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon (
                modifier = Modifier
                    .size(52.dp)
                    .rotate(45f)
                    .background(Color(0xFFE8DCFD), MaterialTheme.shapes.extraLarge)
                    .padding(8.dp),
                imageVector = Icons.Default.AirplanemodeActive,
                contentDescription = "Um ícone de avião"
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text (
                    text = tripInfo.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(650)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text (
                    text = "Data marcada: " + tripInfo.date,
                    fontSize = 10.sp,
                )
            }
            if (editable) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton (
                        onClick = {
                            onDelete()
                        }
                    ) {
                        Icon (
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Ícone de uma lixeira"
                        )
                    }
                    IconButton (
                        onClick = {
                            onEdit()
                        }
                    ) {
                        Icon (
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Ícone de um lápis"
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TripCardPreview1 () {
    TripCard(trip1, {}, {}, true)
}
@Preview
@Composable
fun TripCardPreview2 () {
    TripCard(trip2, {}, {}, false)
}
@Preview
@Composable
fun TripCardPreview3 () {
    TripCard(trip3, {}, {}, true)
}