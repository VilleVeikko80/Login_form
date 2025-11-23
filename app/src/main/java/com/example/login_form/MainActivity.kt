package com.example.login_form

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

import com.example.login_form.ui.theme.Login_formTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Login_formTheme {

                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { innerPadding ->
                    LoginForm(
                        modifier = Modifier.padding(innerPadding),
                        snackbarHostState = snackbarHostState,
                        scope = scope
                    )
                }
            }
        }
    }
}

private val LoginPrimary = Color(0xFF3F51B5)   // sininen nappi + reunat

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center   // 👈 Card keskelle ruutua
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),   // pyöristetyt kulmat
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp          // kevyt varjo
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Otsikko
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                )

                // Username
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = "Email icon"
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LoginPrimary,
                        focusedLabelColor = LoginPrimary,
                        cursorColor = LoginPrimary
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password + silmä
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible }
                        ) {
                            Icon(
                                imageVector = if (passwordVisible)
                                    Icons.Filled.VisibilityOff
                                else
                                    Icons.Filled.Visibility,
                                contentDescription = if (passwordVisible)
                                    "Hide password"
                                else
                                    "Show password"
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LoginPrimary,
                        focusedLabelColor = LoginPrimary,
                        cursorColor = LoginPrimary
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Submit -nappi + snackbar
                Button(
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Login ei ole implementoitu",
                                actionLabel = "OK"
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LoginPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Text("Submit")
                }
            }
        }
    }
}

