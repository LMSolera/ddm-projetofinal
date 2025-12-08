package com.example.ddm_projetofinal.ui.feature.expenses

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Commute
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ddm_projetofinal.model.Expense
import com.example.ddm_projetofinal.model.Trip
import com.example.ddm_projetofinal.model.User
import com.example.ddm_projetofinal.model.expense1
import com.example.ddm_projetofinal.model.expense2
import com.example.ddm_projetofinal.model.expense3
import com.example.ddm_projetofinal.model.expense4
import com.example.ddm_projetofinal.model.user1
import com.example.ddm_projetofinal.ui.components.BottomMenuElement
import com.example.ddm_projetofinal.ui.components.ExpenseCardBig
import com.example.ddm_projetofinal.ui.components.ExpenseCardSmall
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExpensesScreen (
    userInfo: User,
    navigateHome: (User) -> Unit,
    navigateUser: (User) -> Unit,
    navigateTrips: (User) -> Unit,
    viewModel: ExpensesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.getExpenses(userInfo.id)
    viewModel.getRecentTrips(userInfo.id)

    LaunchedEffect(uiState.message.isNotBlank() && uiState.error != null) {
        kotlinx.coroutines.delay(3000)
        viewModel.disperseMessage()
    }

    Scaffold (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .safeDrawingPadding(),
        bottomBar = {
            BottomMenuElement(
                disabledIndex = 4,
                userInfo = userInfo,
                navigateHome = { navigateHome(userInfo) },
                navigateUser = { navigateUser(userInfo) },
                navigateTrips = { navigateTrips(userInfo) },
                navigateExpenses = {}
            )
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

            Spacer(modifier = Modifier.height(16.dp))

            Row (
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                var buttonFontSize = 12.sp
                var buttonColors = ButtonColors(
                    Color(0xFFC5C5C5),
                    Color(0xFF000000),
                    Color(0xFF000000),
                    Color(0xFFFFFFFF))
                Button (
                    colors = buttonColors,
                    enabled = if (uiState.selectedContent == "listing") {false} else {true},
                    onClick = {viewModel.updateSelectedContent("listing")}
                ) {
                    Text (
                        text = "Despesas",
                        fontSize = buttonFontSize
                    )
                }
                Button (
                    colors = buttonColors,
                    enabled = if (uiState.selectedContent  == "creation") {false} else {true},
                    onClick = {viewModel.updateSelectedContent("creation")}
                ) {
                    Text (
                        text = "Nova Despesa",
                        fontSize = buttonFontSize
                    )
                }
                Button (
                    colors = buttonColors,
                    enabled = if (uiState.selectedContent  == "overall") {false} else {true},
                    onClick = {viewModel.updateSelectedContent("overall")}
                ) {
                    Text (
                        text = "Visão Geral",
                        fontSize = buttonFontSize
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (uiState.selectedContent) {
                "overall" -> Overall(uiState.recentExpenses)
                "creation" -> Creation({ expense ->
                        if (expense.id.isEmpty()) {
                            viewModel.insertNewExpense(
                                    userId = userInfo.id,
                                    tripId = expense.tripId,
                                    observation = expense.observation,
                                    value = expense.value,
                                    date = expense.date,
                                    type = expense.type
                                )
                        }
                     },
                    uiState.recentTrips)
                "listing" -> Listing(
                    uiState.recentExpenses,
                    uiState.recentTrips,
                    { toBeDeleted ->
                        viewModel.deleteExpense(toBeDeleted) },
                )
            }

            uiState.error?.let{
                Spacer(modifier = Modifier.height(12.dp))
                if (it) {
                    Card (
                        colors = CardColors(
                            MaterialTheme.colorScheme.errorContainer,
                            MaterialTheme.colorScheme.error,
                            Color(0xFFFFFFFF),
                            Color(0xFFFFFFFF))
                    ) {
                        Text (
                            modifier = Modifier
                                .padding(8.dp),
                            text = uiState.message
                        )
                    }
                } else {
                    Card (
                        colors = CardColors(
                            MaterialTheme.colorScheme.secondaryContainer,
                            MaterialTheme.colorScheme.onSecondaryContainer,
                            Color(0xFFFFFFFF),
                            Color(0xFFFFFFFF))
                    ) {
                        Text (
                            modifier = Modifier
                                .padding(8.dp),
                            text = uiState.message
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun Overall (
    expenses: List<Expense>
) {
    var valorTotal = 0.00
    var valorTotalTransportes = 0.00
    var valorTotalAlimentos = 0.00
    var valorTotalEstadia = 0.00
    var valorTotalOutros = 0.00
    Card (
        modifier = Modifier
            .fillMaxWidth(),
        border = BorderStroke(1.dp, Color(0xFFC9C3CF)),
        colors = CardColors(
            Color(0xFFFCF5FD),
            Color(0xFF000000),
            Color(0xFF000000),
            Color(0xFF000000)
        ),
    ) {
        Column (
            modifier = Modifier
                .padding(12.dp)
        ) {
            Text (
                text = "Gastos totais:",
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(12.dp))



            expenses.forEach({ expense ->
                valorTotal += expense.value
                when (expense.type) {
                    "Transporte" -> valorTotalTransportes += expense.value
                    "Comida" -> valorTotalAlimentos += expense.value
                    "Estadia" -> valorTotalEstadia += expense.value
                    "Outros" -> valorTotalOutros += expense.value
                }
            })

            Text (
                text = if (expenses.isEmpty())
                {"Sem despesas registradas"}
                else {"R$" + String.format("%.2f", valorTotal)},
                fontSize = 24.sp,
                fontWeight = FontWeight(850)
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Card (
        modifier = Modifier
            .fillMaxWidth(),
        border = BorderStroke(1.dp, Color(0xFFC9C3CF)),
        colors = CardColors(
            Color(0xFFFCF5FD),
            Color(0xFF000000),
            Color(0xFF000000),
            Color(0xFF000000)
        ),
    ) {
        Column (
            modifier = Modifier
                .padding(12.dp)
        ) {
            Text (
                text = "Gastos gerais",
                fontWeight = FontWeight(700),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon (
                    modifier = Modifier
                        .size(28.dp),
                    imageVector = Icons.Default.Commute,
                    contentDescription = "Ícone de um carrinho de compras",
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text (
                        text = "Gastos com transporte",
                        fontWeight = FontWeight(750)
                    )
                    Text (
                        text = "R$ " + String.format("%.2f", valorTotalTransportes),
                        color = Color(0xFF818181)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon (
                    modifier = Modifier
                        .size(28.dp),
                    imageVector = Icons.Default.Home,
                    contentDescription = "Ícone de uma casa",
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text (
                        text = "Gastos com estadia",
                        fontWeight = FontWeight(750)
                    )
                    Text (
                        text = "R$ " + String.format("%.2f", valorTotalEstadia),
                        color = Color(0xFF818181)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon (
                    modifier = Modifier
                        .size(28.dp),
                    imageVector = Icons.Default.Fastfood,
                    contentDescription = "Ícone de um lanche de fastfood",
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text (
                        text = "Gastos com alimentos",
                        fontWeight = FontWeight(750)
                    )
                    Text (
                        text = "R$ " + String.format("%.2f", valorTotalAlimentos),
                        color = Color(0xFF818181)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon (
                    modifier = Modifier
                        .size(28.dp),
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = "Ícone de três pontos horizontais",
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text (
                        text = "Outros",
                        fontWeight = FontWeight(750)
                    )
                    Text (
                        text = "R$ " + String.format("%.2f", valorTotalOutros),
                        color = Color(0xFF818181)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Creation (
    onConfirm: (Expense) -> Unit,
    trips: List<Trip>
) {
    val focusManager = LocalFocusManager.current
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var showTypeMenu by remember { mutableStateOf(false) }
    var showTripMenu by remember { mutableStateOf(false) }

    var observation by remember { mutableStateOf("")}
    var value by remember { mutableStateOf("")}
    var type by remember { mutableStateOf("")}
    var trip by remember { mutableStateOf("")}
    var tripId by remember { mutableStateOf("")}
    var selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""


    Column {
        OutlinedTextField (
            label = {
                Text (
                    text = "Observação"
                )
            },
            value = observation,
            onValueChange = { observation = it},
            modifier = Modifier
                .fillMaxWidth(),
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

        OutlinedTextField (
            label = {
                Text (
                    text = "Valor (R$)"
                )
            },
            value = value,
            onValueChange = { value = it},
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            )
        )

        OutlinedTextField (
            label = {
                Text (
                    text = "Tipo"
                )
            },
            value = type,
            onValueChange = { type = it},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                IconButton (
                    onClick = {  showTypeMenu = !showTypeMenu}
                ) {
                    Icon (
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Um ícone de uma seta para baixo"
                    )
                }
            }
        )

        if (showTypeMenu) {
            DropdownMenu(
                expanded = showTypeMenu,
                offset = DpOffset(-30.dp,-150.dp),
                onDismissRequest = { showTypeMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Comida") },
                    onClick = {
                        type = "Comida"
                        showTypeMenu = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Transporte") },
                    onClick = {
                        type = "Transporte"
                        showTypeMenu = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Estadia") },
                    onClick = {
                        type = "Estadia"
                        showTypeMenu = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Outros") },
                    onClick = {
                        type = "Outros"
                        showTypeMenu = false
                    }
                )
            }
        }

        OutlinedTextField (
            label = {
                Text (
                    text = "Viagem"
                )
            },
            value = trip,
            onValueChange = { trip = it},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                IconButton (
                    onClick = {  showTripMenu = !showTripMenu}
                ) {
                    Icon (
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Um ícone de uma seta para baixo"
                    )
                }
            }
        )

        if (showTripMenu) {
            DropdownMenu(
                expanded = showTripMenu,
                offset = DpOffset(-30.dp,-90.dp),
                onDismissRequest = { showTripMenu = false }
            ) {
                if (trips.isEmpty()) {
                    trip = "Sem viagens registradas"
                } else {
                    trips.forEach { tripObject ->
                        DropdownMenuItem(
                            text = { Text(tripObject.title) },
                            onClick = {
                                trip = tripObject.title
                                tripId = tripObject.id
                                showTripMenu = false
                            }
                        )
                    }
                }
            }
        }
        Column (
            modifier = Modifier
                .height(65.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            var buttonColors = ButtonColors(
                Color(0xFF000000),
                Color(0xFFFFFFFF),
                Color(0xFF5C5C5C),
                Color(0xFFC7C7C7)
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button (
                    onClick = {
                        observation = ""
                        type = ""
                        trip = ""
                        value = ""
                        datePickerState.selectedDateMillis = null
                        focusManager.clearFocus()
                    },
                    colors = buttonColors
                ) {
                    Text (
                        "Limpar"
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button (
                    onClick = {
                        onConfirm(
                            Expense (
                                id = "",
                                ownerId = "",
                                tripId = tripId,
                                observation = observation,
                                value = value.toDouble(),
                                date = selectedDate.toString(),
                                type = type
                            )
                        )

                        observation = ""
                        type = ""
                        trip = ""
                        value = ""
                        datePickerState.selectedDateMillis = null
                        focusManager.clearFocus()
                    },
                    colors = buttonColors,
                    enabled = if (!observation.isEmpty()
                        && !selectedDate.isEmpty()
                        && !value.isEmpty()
                        && !type.isEmpty()
                        && !trip.isEmpty()) {true} else {false}
                ) {
                    Text (
                        text = "Concluir"
                    )
                }
            }
        }
    }
}

@Composable
fun Listing (
    expenses: List<Expense>,
    trips: List<Trip>,
    onDelete: (Expense) -> Unit,
) {
    var detailsPopup by remember { mutableStateOf(false) }
    var detailsContent by remember { mutableStateOf<Expense?> (null) }

    if (expenses.isEmpty()) {
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
                items = expenses,
                key = { it.id ?: "" }
            ) { expense ->
                ExpenseCardSmall(
                    expenseInfo = expense,
                    editEnabled = true,
                    onEditPress = {
                        detailsPopup = !detailsPopup
                        expense.also { detailsContent = it }
                    }
                )
                Spacer( modifier = Modifier.height(4.dp))
            }
        }
    }

    if (detailsPopup) {
        Popup (
            onDismissRequest = { detailsPopup = false},
            alignment = Alignment.Center
        ) {
            detailsContent?.let {
                var title: String = ""
                trips.forEach(){ trip ->
                    if (trip.id == it.tripId) {
                        title = trip.title
                    }
                }
                ExpenseCardBig(it,
                    title,
                    { detailsPopup = false },
                    { detailsPopup = false
                    onDelete(it)})
            }
        }
    }
}

@Preview
@Composable
fun CreationPreview () {
    Creation({}, emptyList())
}

@Preview
@Composable
fun OverallPreview () {
    Overall(emptyList())
}

@Preview
@Composable
fun ListingPreviewWithExpenses () {
    Listing(mutableListOf(expense1, expense2, expense3, expense4), emptyList(), {})
}

@Preview
@Composable
fun ListingPreviewEmpty () {
    Listing(emptyList(), emptyList(), {})
}

@Preview
@Composable
fun ExpensesScreenPreview () {
    ExpensesScreen(user1, {}, {}, {})
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis + 50000000))
}