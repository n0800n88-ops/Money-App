package com.hfad.moneyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.moneyapp.ui.theme.MoneyAppTheme

class LoginActivity : ComponentActivity() {

    // 初始化 UserViewModel
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoneyAppTheme {
                LoginScreen(
                    userViewModel = userViewModel,
                    onLoginSuccess = { name ->
                        Toast.makeText(this, "$name 登入成功", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginSuccessActivity::class.java)
                        startActivity(intent)
                    },
                    onRegisterClick = { startActivity(Intent(this, AssignActivity::class.java)) }
                )
            }
        }
    }
}

@Composable
fun LoginScreen(
    userViewModel: UserViewModel,
    onLoginSuccess: (String) -> Unit,
    onRegisterClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFFF9800)
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
                    Text(text = "登入", fontSize = 24.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(16.dp))

                    // 使用者輸入資料
                    var username by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }
                    var isPasswordVisible by remember { mutableStateOf(false) }
                    var errorMessage by remember { mutableStateOf("") }

                    // 帳號輸入框
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("帳號") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // 密碼輸入框
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("密碼") },
                        singleLine = true,
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val icon = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(imageVector = icon, contentDescription = null)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // 錯誤訊息顯示
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 登入按鈕
                    Button(
                        onClick = {
                            if (username == AssignActivity.registeredUsername &&
                                password == AssignActivity.registeredPassword
                            ) {
                                errorMessage = ""
                                val name = AssignActivity.registeredName ?: "使用者"
                                userViewModel.setUsername(name) // 存储用户名到 ViewModel
                                onLoginSuccess(name)
                            } else {
                                errorMessage = "帳號或密碼錯誤！"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90A4AE)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "登入", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 註冊選項
                    Row {
                        Text(text = "還沒有帳號？", color = Color.Gray)
                        Text(
                            text = "註冊",
                            color = Color(0xFF2196F3),
                            modifier = Modifier.clickable { onRegisterClick() }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MoneyAppTheme {
        LoginScreen(
            userViewModel = UserViewModel(), // 预览时传入 ViewModel 实例
            onLoginSuccess = {},
            onRegisterClick = {}
        )
    }
}