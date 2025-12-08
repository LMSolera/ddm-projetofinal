package com.example.ddm_projetofinal.ui.feature.register

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ddm_projetofinal.data.local.UserLocalDatabaseProvider
import com.example.ddm_projetofinal.data.local.UserLocalRepositoryImpl
import com.example.ddm_projetofinal.model.User

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreen (
    onSuccessfulRegister: (User) -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    val focusManager = LocalFocusManager.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val context = LocalContext.current.applicationContext
    val localDatabase = UserLocalDatabaseProvider.provide(context)
    val repository = UserLocalRepositoryImpl (
        dao = localDatabase.userLocalDao
    )
    viewModel.localRepository = repository

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.registerSucess) {
        if (uiState.registerSucess && uiState.user != null) {
            onSuccessfulRegister(uiState.user!!)
        }
    }

    Scaffold (
        modifier = Modifier
            .safeDrawingPadding()
            .blur(
                if (uiState.isLoading) {8.dp} else {0.dp}
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "AppName",
                fontSize = 36.sp,
                fontWeight = FontWeight(1000)
            )

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "Faça seu Registro",
                fontSize = 18.sp,
                fontWeight = FontWeight(750)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Degite um nome, email e senha válidos para registrar sua conta",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(end = 20.dp, start = 20.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                label = {
                    Text(
                        text = "Nome"
                    )
                },
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .width(350.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                label = {
                    Text(
                        text = "Email"
                    )
                },
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .width(350.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                label = {
                    Text(
                        text = "Password"
                    )
                },
                value = password,
                modifier = Modifier
                    .width(350.dp),
                onValueChange = { password = it },
                visualTransformation = if (passwordVisibility) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisibility = !passwordVisibility
                        }
                    ) {
                        Icon(
                            imageVector = if (passwordVisibility) {
                                Icons.Default.Visibility
                            } else {
                                Icons.Default.VisibilityOff
                            },
                            contentDescription = "Ícone de olho"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.register(name, email, password)
                },
                shape = MaterialTheme.shapes.extraSmall,
                modifier = Modifier
                    .width(300.dp),
                enabled = if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                    false
                } else {
                    true
                },
                colors = ButtonColors(
                    Color(0xFF000000),
                    Color(0xFFFFFFFF),
                    Color(0xFF5C5C5C),
                    Color(0xFFC7C7C7)
                )
            ) {
                Text(
                    text = "Registrar"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    navigateToLogin()
                },
                shape = MaterialTheme.shapes.extraSmall,
                colors = ButtonColors(
                    Color(0xFF000000),
                    Color(0xFFFFFFFF),
                    Color(0xFF000000),
                    Color(0xFF000000)
                )
            ) {
                Text(
                    text = "Já possuo uma conta"
                )
            }



            if (uiState.errorMessage != null) {
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
                        text = uiState.errorMessage.toString()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview () {
    RegisterScreen (
        {}, {}
    )
}