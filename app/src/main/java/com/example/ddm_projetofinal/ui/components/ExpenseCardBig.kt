package com.example.ddm_projetofinal.ui.components

import android.graphics.Paint.Align
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Commute
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteOutline
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
import androidx.compose.ui.draw.scale
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
fun ExpenseCardBig (
    expenseInfo: Expense
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
            .height(400.dp)
    ) {
        Column (
            modifier = Modifier
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = when (expenseInfo.type) {
                        "Comida" -> Icons.Default.Fastfood
                        "Transporte" -> Icons.Default.Commute
                        "Estadia" -> Icons.Default.Home
                        "Outros" -> Icons.Default.MoreHoriz
                        else -> Icons.Default.Clear
                    },
                    contentDescription = when (expenseInfo.type) {
                        "Comida" -> "Ícone de um rosto"
                        "Transporte" -> "Ícone de um carrinho de compras"
                        "Estadia" -> "Ícone de uma casa"
                        "Outros" -> "Ícone de três pontos verticais"
                        else -> "Ícone de um X"
                    },
                    modifier = Modifier
                        .background(Color(0xFFE8DCFD), MaterialTheme.shapes.extraLarge)
                        .padding(12.dp)
                )

                Spacer(modifier = Modifier.width(24.dp))

                Column(
                    modifier = Modifier
                        .width(200.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = expenseInfo.type + " - " + expenseInfo.date,
                        fontWeight = FontWeight(750)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "R$ " + String.format("%.2f", expenseInfo.value)
                    )
                }
            }

            Spacer (modifier = Modifier.height(16.dp))

            Text (
                text = expenseInfo.observation + "."
            )

            val scale = 1.5f
            Row (
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom
            ) {
                IconButton (
                    onClick = {}
                ) {
                    Icon (
                        modifier = Modifier
                            .scale(scale),
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Um ícone de 'voltar'",
                    )
                }
                IconButton (
                    onClick = {}
                ) {
                    Icon (
                        modifier = Modifier
                            .scale(scale),
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Um ícone de 'excluir'"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ExpenseCardBigPreview1 () {
    ExpenseCardBig(expense1)
}
@Preview
@Composable
fun ExpenseCardBigPreview2 () {
    ExpenseCardBig(expense2)
}
@Preview
@Composable
fun ExpenseCardBigPreview3 () {
    ExpenseCardBig(expense3)
}
@Preview
@Composable
fun ExpenseCardBigPreview4 () {
    ExpenseCardBig(expense4)
}