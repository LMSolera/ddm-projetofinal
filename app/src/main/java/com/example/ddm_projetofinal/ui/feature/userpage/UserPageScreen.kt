package com.example.ddm_projetofinal.ui.feature.userpage

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.ddm_projetofinal.model.User
import com.example.ddm_projetofinal.model.user1
import com.example.ddm_projetofinal.ui.components.BottomMenuElement
import com.example.ddm_projetofinal.ui.components.EmailChangeDialog
import com.example.ddm_projetofinal.ui.components.NameChangeDialog
import com.example.ddm_projetofinal.ui.components.PasswordChangeDialog

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserPageScreen (
    userInfo: User,
    onLogOut: () -> Unit,
    onCredentialsUpdate: (User) -> Unit,
    navigateHome: (User) -> Unit,
    navigateTrips: (User) -> Unit,
    navigateExpenses: (User) -> Unit,
    viewModel: UserPageViewModel = viewModel()
) {
    var emailDialog by remember { mutableStateOf(false) }
    var nameDialog by remember { mutableStateOf(false) }
    var passwordDialog by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.updatedCredentials) {
        if (uiState.updatedCredentials && uiState.newUserCredentials != null) {
            kotlinx.coroutines.delay(2000)
            onCredentialsUpdate(uiState.newUserCredentials!!)
        }
    }

    Scaffold (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .safeDrawingPadding(),
        bottomBar = {
            BottomMenuElement(
                disabledIndex = 2,
                userInfo = userInfo,
                navigateHome = { navigateHome(userInfo) },
                navigateUser = {},
                navigateTrips = { navigateTrips(userInfo) },
                navigateExpenses = { navigateExpenses(userInfo) }
            )
        }
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text (
                text = "AppName",
                fontSize = 24.sp,
                fontWeight = FontWeight(1000)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Icon (
                modifier = Modifier
                    .background(Color(0xFFE8DCFD), MaterialTheme.shapes.extraLarge)
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .padding(8.dp)
                    .size(120.dp),
                imageVector = Icons.Default.Person,
                contentDescription = "Ícone de pessoa",

            )

            Spacer(modifier = Modifier.height(16.dp))

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
            ) {
                Column (
                    modifier = Modifier
                        .padding(24.dp)
                ) {
                    Text (
                        text = userInfo.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(750)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text (
                        text = userInfo.email,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(60.dp))

                    Text (
                        text = "Alterar Senha",
                        fontSize = 16.sp,
                        color = Color(0xFF22084A),
                        fontWeight = FontWeight(750),
                        modifier = Modifier
                            .clickable {
                                passwordDialog = !passwordDialog
                            }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text (
                        text = "Alterar Email",
                        fontSize = 16.sp,
                        color = Color(0xFF22084A),
                        fontWeight = FontWeight(750),
                        modifier = Modifier
                            .clickable {
                                emailDialog = !emailDialog
                            }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text (
                        text = "Alterar Nome de Usuário",
                        fontSize = 16.sp,
                        color = Color(0xFF22084A),
                        fontWeight = FontWeight(750),
                        modifier = Modifier
                            .clickable {
                                nameDialog = !nameDialog
                            }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text (
                        text = "Sair",
                        fontSize = 16.sp,
                        color = Color(0xFFC60000),
                        fontWeight = FontWeight(750),
                        modifier = Modifier
                            .clickable {
                                onLogOut()
                            }
                    )

                    if (emailDialog) {
                        EmailChangeDialog (
                            userInfo = userInfo,
                            onDismiss = { emailDialog = false },
                            onConfirm = { newUserInfo ->
                                emailDialog = false
                                viewModel.updateUser(User(
                                    id = newUserInfo.id,
                                    name = newUserInfo.name,
                                    email = newUserInfo.email,
                                    password = newUserInfo.password))
                            }
                        )
                    }
                    if (nameDialog) {
                        NameChangeDialog (
                            userInfo = userInfo,
                            onDismiss = { nameDialog = false },
                            onConfirm = { newUserInfo ->
                                nameDialog = false
                                viewModel.updateUser(User(
                                    id = newUserInfo.id,
                                    name = newUserInfo.name,
                                    email = newUserInfo.email,
                                    password = newUserInfo.password))
                            }
                        )
                    }
                    if (passwordDialog) {
                        PasswordChangeDialog (
                            userInfo = userInfo,
                            onDismiss = { passwordDialog = false },
                            onConfirm = { newUserInfo ->
                                passwordDialog = false
                                viewModel.updateUser(User(
                                    id = newUserInfo.id,
                                    name = newUserInfo.name,
                                    email = newUserInfo.email,
                                    password = newUserInfo.password))
                            }
                        )
                    }
                }
            }

            uiState.error?.let {
                if (it) {
                    Spacer(modifier = Modifier.height(8.dp))
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
                    Spacer(modifier = Modifier.height(8.dp))
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

@Preview
@Composable
fun UserPageScrenPreview () {
    UserPageScreen(user1, {}, {}, {}, {}, {})
}