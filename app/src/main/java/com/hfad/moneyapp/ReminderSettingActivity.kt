package com.hfad.moneyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.moneyapp.ui.theme.MoneyAppTheme

class ReminderSettingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoneyAppTheme {
                ReminderSettingScreen(
                    onBackClick = { navigateToSettingActivity() },
                    onProfileClick = { navigateToSettingActivity() },
                    onAccountClick = { navigateToAccountActivity() },
                    onReportClick = { navigateToReportActivity() }
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
fun ReminderSettingScreen(
    onBackClick: () -> Unit,
    onProfileClick: () -> Unit,
    onAccountClick: () -> Unit,
    onReportClick: () -> Unit
) {
    var isReminderEnabled by remember { mutableStateOf(false) }
    val hours = (0..23).map { String.format("%02d:00", it) }
    val selectedHours = remember { mutableStateListOf<String>() }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "記帳提醒",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) { // 返回按鈕導航到 SettingActivity
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "返回",
                                tint = Color.Black
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFE7D4B5))
                )
                Divider(color = Color.Black, thickness = 1.dp) // 分隔線
            }
        },
        containerColor = Color(0xFFE7D4B5),
        bottomBar = {
            ReminderBottomNavigationBar(
                onProfileClick = onProfileClick,
                onAccountClick = onAccountClick,
                onReportClick = onReportClick
            )
        }
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
                    .wrapContentHeight(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF738F83)),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title Row with Switch
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "記帳提醒",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = isReminderEnabled,
                            onCheckedChange = {
                                isReminderEnabled = it
                                if (!isReminderEnabled) {
                                    selectedHours.clear()
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    // Time Selection Grid (Only visible when reminder is enabled)
                    if (isReminderEnabled) {
                        LazyColumn {
                            items(hours.chunked(2)) { pair ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    pair.forEach { hour ->
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .weight(1f)
                                                .clickable(
                                                    enabled = isReminderEnabled,
                                                    onClick = {
                                                        if (selectedHours.contains(hour)) {
                                                            selectedHours.remove(hour)
                                                        } else {
                                                            selectedHours.add(hour)
                                                        }
                                                    }
                                                )
                                        ) {
                                            Checkbox(
                                                checked = selectedHours.contains(hour),
                                                onCheckedChange = null,
                                                enabled = isReminderEnabled
                                            )
                                            Text(
                                                text = hour,
                                                color = if (isReminderEnabled) Color.Black else Color.Gray,
                                                fontSize = 14.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Confirm Button
                    Button(
                        onClick = {
                            if (selectedHours.isEmpty()) {
                                Toast.makeText(context, "請選擇至少一個時段", Toast.LENGTH_SHORT).show()
                            } else {
                                val selectedTimes = selectedHours.joinToString(", ")
                                Toast.makeText(
                                    context,
                                    "設定成功\n將會在 $selectedTimes 發送通知提醒您記帳",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        enabled = isReminderEnabled
                    ) {
                        Text(text = "確認", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun ReminderBottomNavigationBar(
    onProfileClick: () -> Unit,
    onAccountClick: () -> Unit,
    onReportClick: () -> Unit
) {
    BottomAppBar(containerColor = Color(0xFFF7B977)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onReportClick) { // 點擊跳到 ReportActivity
                Image(
                    painter = painterResource(id = R.drawable.business_report),
                    contentDescription = "Home",
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = onAccountClick) { // 點擊跳到 AccountActivity
                Image(
                    painter = painterResource(id = R.drawable.money_bag),
                    contentDescription = "Account",
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = onProfileClick) { // 點擊回到 SettingActivity
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
fun ReminderSettingScreenPreview() {
    MoneyAppTheme {
        ReminderSettingScreen(
            onBackClick = {},
            onProfileClick = {},
            onAccountClick = {},
            onReportClick = {}
        )
    }
}
