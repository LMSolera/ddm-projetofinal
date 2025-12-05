package com.example.ddm_projetofinal.ui.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ddm_projetofinal.model.Expense
import com.example.ddm_projetofinal.model.User
import com.example.ddm_projetofinal.model.expense1
import com.example.ddm_projetofinal.model.expense2
import com.example.ddm_projetofinal.model.expense3
import com.example.ddm_projetofinal.model.expense4
import com.example.ddm_projetofinal.model.usuario1
import com.example.ddm_projetofinal.ui.components.BottomMenuElement
import com.example.ddm_projetofinal.ui.components.ExpenseCardSmall

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen (
    userInfo: User,
    viewModel: HomeViewModel = viewModel()
) {
    Scaffold (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .safeDrawingPadding(),
        bottomBar = {
            BottomMenuElement(1)
        }
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
           Spacer( modifier = Modifier.height(16.dp))

            Text (
                text = "AppName",
                fontSize = 24.sp,
                fontWeight = FontWeight(1000)
            )

            Spacer( modifier = Modifier.height(16.dp))

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
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 24.dp, end = 24.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text (
                        text = userInfo.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(800)
                    )

                    Spacer( modifier = Modifier.height(4.dp))

                    Text (
                        text = userInfo.email,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer( modifier = Modifier.height(16.dp))

            Text (
                text = "Gastos Recentes",
                fontSize = 20.sp,
                fontWeight = FontWeight(1000)
            )

            Spacer( modifier = Modifier.height(16.dp))

            var temporaryExpenses: MutableList<Expense> = mutableListOf(expense1, expense2, expense3)
            // TODO: Quando estivermos extraindo expenses do banco, adicionar uma lógica pra usar
            // TODO: somente as três mais recentes
            LazyColumn {
                items(
                    items = temporaryExpenses,
                    key = { it.id ?: "" }
                ) { expense ->
                    ExpenseCardSmall(
                        expenseInfo = expense,
                        editEnabled = false,
                        onEditPress = {}
                    )
                    Spacer( modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview () {
    HomeScreen (usuario1)
}