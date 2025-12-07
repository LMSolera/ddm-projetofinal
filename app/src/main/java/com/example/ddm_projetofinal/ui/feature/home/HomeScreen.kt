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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ddm_projetofinal.model.Expense
import com.example.ddm_projetofinal.model.Trip
import com.example.ddm_projetofinal.model.User
import com.example.ddm_projetofinal.model.expense1
import com.example.ddm_projetofinal.model.expense2
import com.example.ddm_projetofinal.model.expense3
import com.example.ddm_projetofinal.model.trip1
import com.example.ddm_projetofinal.model.user1
import com.example.ddm_projetofinal.ui.components.BottomMenuElement
import com.example.ddm_projetofinal.ui.components.ExpenseCardSmall
import com.example.ddm_projetofinal.ui.components.TripCard

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen (
    userInfo: User,
    navigateUser: (User) -> Unit,
    navigateTrips: (User) -> Unit,
    navigateExpenses: (User) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    viewModel.getRecentTrips(userInfo.id)
    viewModel.getExpenses(userInfo.id)

    Scaffold (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .safeDrawingPadding(),
        bottomBar = {
            BottomMenuElement(
                disabledIndex = 1,
                userInfo = userInfo,
                navigateHome = {},
                navigateUser = { navigateUser(userInfo) },
                navigateTrips = { navigateTrips(userInfo) },
                navigateExpenses = { navigateExpenses(userInfo) }
            )
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
                text = "Viagem mais Recente",
                fontSize = 20.sp,
                fontWeight = FontWeight(1000)
            )

            Spacer( modifier = Modifier.height(16.dp))

            if (uiState.recentTrips.isEmpty()) {
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
                    Text (
                        modifier = Modifier
                            .padding(12.dp),
                        text = "Nenhuma viagem registrada para este usuário...",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(750)
                    )
                }
            } else {
                TripCard(tripInfo = uiState.recentTrips.get(uiState.recentTrips.lastIndex),
                    {},
                    {},
                    false)
            }


            Spacer( modifier = Modifier.height(16.dp))

            Text (
                text = "Gastos Recentes",
                fontSize = 20.sp,
                fontWeight = FontWeight(1000)
            )

            Spacer( modifier = Modifier.height(16.dp))

            if (uiState.recentExpenses.isEmpty()) {
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
                    Text (
                        modifier = Modifier
                            .padding(12.dp),
                        text = "Nenhum gasto registrado para este usuário...",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(750)
                    )
                }
            } else {
                LazyColumn {
                    items(
                        items = if (uiState.recentExpenses.size >= 3 ) {
                            listOf(
                                uiState.recentExpenses.get(0),
                                uiState.recentExpenses.get(1),
                                uiState.recentExpenses.get(2)
                            )
                        } else {
                            uiState.recentExpenses
                        },
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
}

@Preview
@Composable
fun HomeScreenPreview () {
    HomeScreen (user1, {}, {}, {})
}