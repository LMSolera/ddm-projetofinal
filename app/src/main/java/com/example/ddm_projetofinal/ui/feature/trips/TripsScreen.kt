package com.example.ddm_projetofinal.ui.feature.trips

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ddm_projetofinal.model.Trip
import com.example.ddm_projetofinal.model.User
import com.example.ddm_projetofinal.model.trip1
import com.example.ddm_projetofinal.model.trip2
import com.example.ddm_projetofinal.model.trip3
import com.example.ddm_projetofinal.model.user1
import com.example.ddm_projetofinal.ui.components.BottomMenuElement
import com.example.ddm_projetofinal.ui.components.TripCard
import com.example.ddm_projetofinal.ui.components.TripDialog

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TripsScreen (
    userInfo: User,
    navigateHome: (User) -> Unit,
    navigateUser: (User) -> Unit,
    navigateExpenses: (User) -> Unit,
    viewModel: TripsViewModel = viewModel()
) {
    var showTripDialog by remember { mutableStateOf(false) }
    var tripDialogCache by remember { mutableStateOf<Trip?>(null) }

    val uiState by viewModel.uiState.collectAsState()
    viewModel.getRecentTrips(userInfo.id)

    Scaffold (
        modifier = Modifier
            .safeDrawingPadding()
            .fillMaxHeight()
            .fillMaxWidth(),
        bottomBar = {
            BottomMenuElement(
                disabledIndex = 3,
                userInfo = userInfo,
                navigateHome = { navigateHome(userInfo) },
                navigateUser = { navigateUser(userInfo) },
                navigateTrips = {},
                navigateExpenses = { navigateExpenses(userInfo) }
            )
        },
        floatingActionButton = {
            IconButton (
                modifier = Modifier
                    .background(Color(0xFFE8DCFD), MaterialTheme.shapes.medium)
                    .size(52.dp),
                onClick = {
                    showTripDialog = !showTripDialog
                    tripDialogCache = null
                }
            ) {
                Icon (
                    modifier = Modifier
                        .size(36.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "Im ícone de 'mais'"
                )
            }
        }
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .height(730.dp)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "AppName",
                fontSize = 24.sp,
                fontWeight = FontWeight(1000)
            )

            Spacer( modifier = Modifier.height(16.dp))

            Text (
                text = "Suas Viagens: " + uiState.recentTrips.size.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight(1000)
            )

            Spacer( modifier = Modifier.height(16.dp))

            var temporaryTrips: MutableList<Trip>? = null
            temporaryTrips = mutableListOf(trip1, trip2, trip3)

            if (uiState.recentTrips.isEmpty() || uiState.isLoading) {
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
                LazyColumn {
                    items(
                        items = uiState.recentTrips,
                        key = { it.id ?: "" }
                    ) { trip ->
                        TripCard (tripInfo = trip, {
                            showTripDialog = !showTripDialog
                            tripDialogCache = trip
                        }, {
                            viewModel.deleteTrip(trip)
                        }, true)
                        Spacer( modifier = Modifier.height(4.dp))
                    }
                }
            }

            if (showTripDialog) {
                TripDialog(tripDialogCache,
                    { trip ->
                        if (trip.id.isNotEmpty() && trip.ownerId.isNotEmpty()) {
                            viewModel.updateTrip(trip)
                            showTripDialog = false
                        } else {
                            viewModel.insertNewTrip(userInfo.id, trip.title, trip.date)
                            showTripDialog = false
                        }
                    },
                    {showTripDialog = false})
            }
        }
    }
}

@Preview
@Composable
fun TripsScreenPreview () {
    TripsScreen(user1, {}, {}, {})
}