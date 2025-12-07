package com.example.ddm_projetofinal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.ddm_projetofinal.model.User
import com.example.ddm_projetofinal.model.user1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordChangeDialog (
    userInfo: User,
    onDismiss: () -> Unit,
    onConfirm: (User) -> Unit
) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var newPasswordConfirm by remember { mutableStateOf("") }

    var oldPasswordVisibility by remember { mutableStateOf(false) }
    var newPasswordVisibility by remember { mutableStateOf(false) }
    var newPasswordConfirmVisibility by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Alterar senha",
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = oldPassword,
                    onValueChange = {
                        oldPassword = it
                    },
                    label = {
                        Text("Senha antiga")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (oldPasswordVisibility) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton (
                            onClick = {
                                oldPasswordVisibility = !oldPasswordVisibility
                            }
                        ) {
                            Icon (
                                imageVector = if (oldPasswordVisibility) {
                                    Icons.Default.Visibility
                                } else {
                                    Icons.Default.VisibilityOff
                                },
                                contentDescription = "Ícone de olho"
                            )
                        }
                    }
                )

                OutlinedTextField(
                    value = newPassword,
                    onValueChange = {
                        newPassword = it
                    },
                    label = {
                        Text("Senha nova")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (newPasswordVisibility) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton (
                            onClick = {
                                newPasswordVisibility = !newPasswordVisibility
                            }
                        ) {
                            Icon (
                                imageVector = if (newPasswordVisibility) {
                                    Icons.Default.Visibility
                                } else {
                                    Icons.Default.VisibilityOff
                                },
                                contentDescription = "Ícone de olho"
                            )
                        }
                    }
                )

                OutlinedTextField(
                    value = newPasswordConfirm,
                    onValueChange = {
                        newPasswordConfirm = it
                    },
                    label = {
                        Text("Confirmar Senha Nova")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (newPasswordConfirmVisibility) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton (
                            onClick = {
                                newPasswordConfirmVisibility = !newPasswordConfirmVisibility
                            }
                        ) {
                            Icon (
                                imageVector = if (newPasswordConfirmVisibility) {
                                    Icons.Default.Visibility
                                } else {
                                    Icons.Default.VisibilityOff
                                },
                                contentDescription = "Ícone de olho"
                            )
                        }
                    }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm (
                        User(
                            id =  userInfo.id,
                            name = userInfo.name,
                            email = userInfo.email,
                            password = newPassword)
                    )
                },
                enabled = if (!oldPassword.isEmpty()
                    && oldPassword.equals(userInfo.password)
                    && !newPassword.isEmpty()
                    && !newPasswordConfirm.isEmpty()
                    && newPassword.equals(newPasswordConfirm))
                {true} else {false}
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Preview
@Composable
fun PasswordChangeDialogPreview () {
    PasswordChangeDialog(user1, {}, {})
}