package com.hfad.moneyapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.moneyapp.ui.theme.MoneyAppTheme

class CardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoneyAppTheme {
                CardScreen(
                    onBackClick = { navigateToSettingActivity() },
                    onHomeClick = { navigateToReportActivity() },
                    onProfileClick = { navigateToSettingActivity() },
                    onAccountClick = { navigateToAccountActivity() }
                )
            }
        }
    }

    private fun navigateToSettingActivity() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToAccountActivity() {
        val intent = Intent(this, AccountActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToReportActivity() {
        val intent = Intent(this, ReportActivity::class.java)
        startActivity(intent)
        finish()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardScreen(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    onAccountClick: () -> Unit
) {
    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "管理卡片/帳戶",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "返回",
                                tint = Color.Black
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFF4D6A3))
                )
                Divider(color = Color.Black, thickness = 1.dp)
            }
        },
        containerColor = Color(0xFFF4D6A3),
        bottomBar = {
            BottomNavigationBar(
                onHomeClick = onHomeClick,
                onProfileClick = onProfileClick,
                onAccountClick = onAccountClick
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AccountScreen()
        }
    }
}

@Composable
fun AccountScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp) // 調整項目之間的間距
    ) {
        SectionWithTitle(title = "信用卡") {
            AccountCard(
                description = "",
                onClick = { /* TODO: Add credit card action */ }
            )
        }
        SectionWithTitle(title = "銀行帳戶") {
            AccountCard(
                description = "(700)01234567891234 已綁定",
                onClick = { /* TODO: Add bank account action */ }
            )
        }
        SectionWithTitle(title = "行動支付") {
            AccountCard(
                description = "LINE PAY 已綁定",
                onClick = { /* TODO: Add mobile payment action */ }
            )
        }
    }
}

@Composable
fun SectionWithTitle(title: String, content: @Composable () -> Unit) {
    Column {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp) // 與卡片的間距
        )
        content()
    }
}

@Composable
fun AccountCard(description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD0D9C6)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                if (description.isNotEmpty()) {
                    Text(text = description, fontSize = 14.sp, color = Color.Gray)
                }
            }
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    onAccountClick: () -> Unit
) {
    BottomAppBar(containerColor = Color(0xFFF7B977)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home 按鈕
            IconButton(onClick = onHomeClick) {
                Image(
                    painter = painterResource(id = R.drawable.business_report),
                    contentDescription = "Home",
                    modifier = Modifier.size(24.dp)
                )
            }

            // Account 按鈕
            IconButton(onClick = onAccountClick) {
                Image(
                    painter = painterResource(id = R.drawable.money_bag),
                    contentDescription = "Account",
                    modifier = Modifier.size(24.dp)
                )
            }

            // Profile 按鈕
            IconButton(onClick = onProfileClick) {
                Image(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = "Profile",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardScreenPreview() {
    MoneyAppTheme {
        CardScreen(
            onBackClick = {},
            onHomeClick = {},
            onProfileClick = {},
            onAccountClick = {}
        )
    }
}
