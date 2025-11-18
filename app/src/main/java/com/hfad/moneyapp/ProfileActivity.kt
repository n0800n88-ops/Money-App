package com.hfad.moneyapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.moneyapp.ui.theme.MoneyAppTheme

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyAppTheme {
                ProfileScreen(
                    onBackClick = { finish() },
                    onNavigateToReport = {
                        startActivity(Intent(this, ReportActivity::class.java))
                    },
                    onNavigateToAccount = {
                        startActivity(Intent(this, AccountActivity::class.java))
                    },
                    onNavigateToSettings = {
                        startActivity(Intent(this, SettingActivity::class.java))
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBackClick: () -> Unit,
    onNavigateToReport: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "返回",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFFE7D4B5),
                    scrolledContainerColor = Color(0xFFE7D4B5)
                )
            )
        },
        bottomBar = {
            ProfileBottomNavigationBar(
                onNavigateToReport = onNavigateToReport,
                onNavigateToAccount = onNavigateToAccount,
                onNavigateToSettings = onNavigateToSettings
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFE7D4B5))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image
            Image(
                painter = painterResource(id = R.drawable.ic_profile_placeholder),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.White, CircleShape)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // User Name
            Text(
                text = "qq",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

            // User Info
            UserInfoField(label = "帳號", value = "qq@gmail.com")
            PasswordField(label = "密碼", value = "12345678")
            UserInfoField(label = "電話", value = "0912345678")
        }
    }
}

@Composable
fun UserInfoField(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFD3D3D3))
                .padding(8.dp)
        )
    }
}

@Composable
fun PasswordField(label: String, value: String) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFD3D3D3))
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Text(
                text = if (isPasswordVisible) value else "•".repeat(value.length),
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                contentDescription = if (isPasswordVisible) "隱藏密碼" else "顯示密碼",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { isPasswordVisible = !isPasswordVisible },
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun ProfileBottomNavigationBar(
    onNavigateToReport: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFFF7B977)
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { onNavigateToReport() },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.business_report),
                    contentDescription = "Report",
                    modifier = Modifier.size(24.dp)
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { onNavigateToAccount() },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.money_bag),
                    contentDescription = "Account",
                    modifier = Modifier.size(24.dp)
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { onNavigateToSettings() },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = "Settings",
                    modifier = Modifier.size(24.dp)
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MoneyAppTheme {
        ProfileScreen(
            onBackClick = {},
            onNavigateToReport = {},
            onNavigateToAccount = {},
            onNavigateToSettings = {}
        )
    }
}
