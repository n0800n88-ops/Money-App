package com.hfad.moneyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.moneyapp.ui.theme.MoneyAppTheme

class AssignActivity : ComponentActivity() {
    companion object {
        var registeredName: String? = null
        var registeredUsername: String? = null
        var registeredPassword: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoneyAppTheme {
                RegisterScreen(
                    onLoginClick = { startActivity(Intent(this, LoginActivity::class.java)) },
                    onTermsClick = { startActivity(Intent(this, TermsActivity::class.java)) },
                    onPrivacyClick = { startActivity(Intent(this, PrivacyPolicyActivity::class.java)) },
                    onRegisterSuccess = { name, username, password ->
                        registeredName = name
                        registeredUsername = username
                        registeredPassword = password
                        Toast.makeText(this, "$name 註冊成功", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                )
            }
        }
    }
}

@Composable
fun RegisterScreen(
    onLoginClick: () -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    onRegisterSuccess: (String, String, String) -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF738F83)
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 24.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "註冊", fontSize = 24.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "已經有帳號了嗎? ", fontSize = 14.sp, color = Color.Gray)
                        Text(
                            text = "登入",
                            fontSize = 14.sp,
                            color = Color(0xFF2196F3),
                            modifier = Modifier.clickable { onLoginClick() }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    var name by remember { mutableStateOf("") }
                    var phone by remember { mutableStateOf("") }
                    var username by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }
                    var isPasswordVisible by remember { mutableStateOf(false) }

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("名字") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("手機號碼") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("設定帳號") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("設定密碼") },
                        singleLine = true,
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val icon =
                                if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(imageVector = icon, contentDescription = null)
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "點擊註冊即表示您同意 ",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "使用者條款",
                            fontSize = 12.sp,
                            color = Color(0xFF2196F3),
                            modifier = Modifier.clickable { onTermsClick() }
                        )
                        Text(text = " 與 ", fontSize = 12.sp, color = Color.Gray)
                        Text(
                            text = "隱私權政策",
                            fontSize = 12.sp,
                            color = Color(0xFF2196F3),
                            modifier = Modifier.clickable { onPrivacyClick() }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (!phone.matches(Regex("^09\\d{8}\$"))) {
                                Toast.makeText(context, "手機號碼格式不正確", Toast.LENGTH_SHORT).show()
                            } else if (!username.contains("@") || !username.endsWith(".com")) {
                                Toast.makeText(context, "帳號必須包含 @ 與 .com", Toast.LENGTH_SHORT).show()
                            } else if (password.length != 8) {
                                Toast.makeText(context, "密碼必須為八碼", Toast.LENGTH_SHORT).show()
                            } else {
                                onRegisterSuccess(name, username, password)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text(text = "註冊", color = Color.White)
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    MoneyAppTheme {
        RegisterScreen(
            onLoginClick = {},
            onTermsClick = {},
            onPrivacyClick = {},
            onRegisterSuccess = { _, _, _ -> }
        )
    }
}
