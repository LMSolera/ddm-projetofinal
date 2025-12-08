package com.example.ddm_projetofinal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Commute
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ddm_projetofinal.model.Expense
import com.example.ddm_projetofinal.model.expense1
import com.example.ddm_projetofinal.model.expense2
import com.example.ddm_projetofinal.model.expense3
import com.example.ddm_projetofinal.model.expense4

@Composable
fun ExpenseCardSmall (
    expenseInfo: Expense,
    editEnabled: Boolean,
    onEditPress: (Expense) -> Unit
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
            .height(80.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon (
                imageVector = when (expenseInfo.type) {
                    "Comida" -> Icons.Default.Fastfood
                    "Transporte" -> Icons.Default.Commute
                    "Estadia" -> Icons.Default.Home
                    "Outros" -> Icons.Default.MoreHoriz
                    else -> Icons.Default.Clear
                },
                contentDescription = when (expenseInfo.type) {
                    "Comida" -> "Ícone de um lanche de fastfood"
                    "Transporte" -> "Ícone de um carrinho de compras"
                    "Estadia" -> "Ícone de uma casa"
                    "Outros" -> "Ícone de três pontos horizontais"
                    else -> "Ícone de um X"
                },
                modifier = Modifier
                    .background(Color(0xFFE8DCFD), MaterialTheme.shapes.extraLarge)
                    .padding(12.dp)
            )

            Spacer (modifier = Modifier.width(24.dp))

            Column (
                modifier = Modifier
                    .fillMaxHeight()
                    .width(200.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text (
                    text = expenseInfo.type + " - " + expenseInfo.date,
                    fontWeight = FontWeight(750)
                )

                Spacer (modifier = Modifier.height(6.dp))

                Text (
                    text = "R$ " + String.format("%.2f", expenseInfo.value)
                )
            }

            Spacer (modifier = Modifier.width(24.dp))

            IconButton (
                onClick = {
                    onEditPress(expenseInfo)
                },
                enabled = editEnabled,
                colors = IconButtonColors(
                    Color(0x00000000),
                    Color(0xFF000000),
                    Color(0x00000000),
                    Color(0x00FFFFFF)
                )
            ) {
                Icon (
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Ícon de edição"
                )
            }
        }
    }
}

@Preview
@Composable
fun ExpenseCardSmallPreview1 () {
    ExpenseCardSmall(expense1, false, {})
}
@Preview
@Composable
fun ExpenseCardSmallPreview2 () {
    ExpenseCardSmall(expense2, true, {})
}
@Preview
@Composable
fun ExpenseCardSmallPreview3 () {
    ExpenseCardSmall(expense3, false, {})
}
@Preview
@Composable
fun ExpenseCardSmallPreview4 () {
    ExpenseCardSmall(expense4, true, {})
}
