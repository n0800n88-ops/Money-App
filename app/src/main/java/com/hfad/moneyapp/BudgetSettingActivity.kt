package com.hfad.moneyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.moneyapp.ui.theme.MoneyAppTheme

class BudgetSettingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoneyAppTheme {
                BudgetSettingScreen(
                    onReportClick = { navigateToReportActivity() },
                    onBackClick = { navigateToSettingActivity() },
                    onAccountClick = { navigateToAccountActivity() },
                    onProfileClick = { navigateToSettingActivity() }
                )
            }
        }
    }

    private fun navigateToReportActivity() {
        val intent = Intent(this, ReportActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToSettingActivity() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToAccountActivity() {
        val intent = Intent(this, AccountActivity::class.java)
        startActivity(intent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetSettingScreen(
    onReportClick: () -> Unit,
    onBackClick: () -> Unit,
    onAccountClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    var isReminderEnabled by remember { mutableStateOf(false) }
    var budgetAmount by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "超支提醒",
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
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFE7D4B5))
                )
                Divider(color = Color.Black, thickness = 1.dp)
            }
        },
        containerColor = Color(0xFFE7D4B5),
        bottomBar = {
            BudgetBottomNavigationBar(
                onReportClick = onReportClick,
                onAccountClick = onAccountClick,
                onProfileClick = onProfileClick
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "超支提醒",
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
                                    budgetAmount = ""
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "當支出接近預算時，系統會發送提醒通知您",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (isReminderEnabled) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "預算設定",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "$",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                TextField(
                                    value = budgetAmount,
                                    onValueChange = { newText ->
                                        budgetAmount = newText.filter { it.isDigit() }
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                    colors = TextFieldDefaults.textFieldColors(
                                        containerColor = Color(0xFFFAFAFA),
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Button(
                        onClick = {
                            if (budgetAmount.isEmpty()) {
                                Toast.makeText(context, "請輸入預算金額", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "設定成功，每個月預算為 $budgetAmount",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        enabled = isReminderEnabled
                    ) {
                        Text(text = "保存", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun BudgetBottomNavigationBar(
    onReportClick: () -> Unit,
    onAccountClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    BottomAppBar(containerColor = Color(0xFFF7B977)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onReportClick) {
                Image(
                    painter = painterResource(id = R.drawable.business_report),
                    contentDescription = "Report",
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = onAccountClick) {
                Image(
                    painter = painterResource(id = R.drawable.money_bag),
                    contentDescription = "Account",
                    modifier = Modifier.size(24.dp)
                )
            }
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
fun BudgetSettingScreenPreview() {
    MoneyAppTheme {
        BudgetSettingScreen(
            onReportClick = {},
            onBackClick = {},
            onAccountClick = {},
            onProfileClick = {}
        )
    }
}
