package com.hfad.moneyapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.moneyapp.ui.theme.MoneyAppTheme
import java.text.SimpleDateFormat
import java.util.*

class AccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoneyAppTheme {
                AppContent(
                    navigateToSettings = { navigateToSettingActivity() },
                    navigateToAccount = { navigateToAccountActivity() },
                    navigateToHome = { navigateToReportActivity() }
                )
            }
        }
    }

    private fun navigateToSettingActivity() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToAccountActivity() {
        val intent = Intent(this, AccountActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToReportActivity() {
        val intent = Intent(this, ReportActivity::class.java)
        startActivity(intent)
    }
}

@Composable
fun AppContent(
    navigateToSettings: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToHome: () -> Unit
) {
    var isAdding by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(getTodayDate()) }

    // 交易紀錄資料 - 使用 MutableMap 來更新資料
    var transactionsData by remember {
        mutableStateOf(
            mutableMapOf(
                "2024年12月28日" to mutableListOf(
                    Transaction("支出", "飲食", "早餐", 50),
                    Transaction("支出", "飲品", "奶茶", 45),
                    Transaction("支出", "日用品", "洗髮精", 150),
                    Transaction("支出", "食物", "午餐", 120),
                    Transaction("支出", "健康", "維他命", 300),
                    Transaction("支出", "食物", "晚餐", 200),
                    Transaction("收入", "工作", "兼職薪水", 3500)
                ),
                "2024年12月27日" to mutableListOf(
                    Transaction("支出", "飲食", "早餐", 60),
                    Transaction("支出", "飲品", "果汁", 40),
                    Transaction("支出", "學習", "書籍", 450),
                    Transaction("支出", "食物", "午餐", 100),
                    Transaction("收入", "獎學金", "助學金", 2000)
                ),
                "2024年12月26日" to mutableListOf(
                    Transaction("支出", "食物", "午餐", 150),
                    Transaction("支出", "飲品", "氣泡水", 30),
                    Transaction("支出", "旅遊", "火車票", 1200)
                )
            )
        )
    }

    // 根據選擇的日期顯示對應的交易紀錄
    val transactionsForSelectedDate = transactionsData[selectedDate].orEmpty()

    if (isAdding) {
        AddTransactionPage(
            selectedDate = selectedDate,
            onDateSelected = { selectedDate = it },
            onAdd = { type, category, amount, note ->
                // 新增交易紀錄
                val updatedTransactions = transactionsData[selectedDate]?.toMutableList() ?: mutableListOf()
                updatedTransactions.add(Transaction(type, category, note, amount))
                transactionsData[selectedDate] = updatedTransactions
                isAdding = false
            },
            onCancel = { isAdding = false }
        )
    } else {
        TransactionListPage(
            selectedDate = selectedDate,
            transactions = transactionsForSelectedDate,
            onDateChange = { selectedDate = it },
            onAddClick = { isAdding = true },
            navigateToSettings = navigateToSettings,
            navigateToAccount = navigateToAccount,
            navigateToHome = navigateToHome
        )
    }
}

@Composable
fun TransactionListPage(
    selectedDate: String,
    transactions: List<Transaction>,
    onDateChange: (String) -> Unit,
    onAddClick: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToHome: () -> Unit
) {
    val income = transactions.filter { it.type == "收入" }.sumOf { it.amount }
    val expense = transactions.filter { it.type == "支出" }.sumOf { it.amount }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick, containerColor = Color(0xFFF7B977)) {
                Text("+", fontSize = 24.sp, color = Color.White)
            }
        },
        bottomBar = {
            AccountBottomNavigationBar(
                navigateToSettings = navigateToSettings,
                navigateToAccount = navigateToAccount,
                navigateToHome = navigateToHome
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onDateChange(getPreviousDate(selectedDate)) }) {
                    Text("<")
                }
                Text(selectedDate, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = {
                    val nextDate = getNextDate(selectedDate)
                    if (nextDate <= getTodayDate()) {
                        onDateChange(nextDate)
                    }
                }) {
                    Text(">")
                }
            }

            Text(
                "今日收入: $$income  今日支出: $$expense",
                modifier = Modifier.padding(16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                items(transactions.size) { index ->
                    val transaction = transactions[index]
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = transaction.type,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = transaction.category,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = transaction.note,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "$${transaction.amount}",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.End
                            )
                        }
                        Divider(color = Color.LightGray, thickness = 1.dp)
                    }
                }
            }
        }
    }
}

@Composable
fun AccountBottomNavigationBar(navigateToSettings: () -> Unit, navigateToAccount: () -> Unit, navigateToHome: () -> Unit) {
    BottomAppBar(containerColor = Color(0xFFF7B977)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = navigateToHome) {
                Image(
                    painter = painterResource(id = R.drawable.business_report),
                    contentDescription = "Home",
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = navigateToAccount) {
                Image(
                    painter = painterResource(id = R.drawable.money_bag),
                    contentDescription = "Account",
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = navigateToSettings) {
                Image(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = "Settings",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun AddTransactionPage(
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    onAdd: (String, String, Int, String) -> Unit,
    onCancel: () -> Unit
) {
    var type by remember { mutableStateOf("支出") }
    var category by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onCancel) {
                    Text("\u2716", fontSize = 24.sp, color = Color.Gray)
                }
                TextButton(onClick = { onDateSelected(getTodayDate()) }) {
                    Text(selectedDate, fontSize = 16.sp, color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("選擇交易類型", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Row(modifier = Modifier.fillMaxWidth()) {
                listOf("支出", "收入").forEach { typeOption ->
                    Button(
                        onClick = { type = typeOption },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (type == typeOption) Color(0xFFF7B977) else Color.Gray
                        ),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(typeOption, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("類別選擇", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Row(modifier = Modifier.fillMaxWidth()) {
                val categories = if (type == "收入") listOf("零用錢", "薪水", "投資") else listOf("飲食", "飲品", "日用品", "健康")
                categories.forEach { categoryOption ->
                    Button(
                        onClick = { category = categoryOption },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (category == categoryOption) Color(0xFFF7B977) else Color.Gray
                        ),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(categoryOption, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("金額輸入", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            TextField(
                value = amount,
                onValueChange = { if (it.all { char -> char.isDigit() }) amount = it },
                placeholder = { Text("輸入金額") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("備註", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            TextField(
                value = note,
                onValueChange = { note = it },
                placeholder = { Text("輸入備註 (選填)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(
                    onClick = { onAdd(type, category, amount.toIntOrNull() ?: 0, note) },
                    containerColor = Color(0xFFF7B977)
                ) {
                    Text("\u2714", fontSize = 24.sp, color = Color.White)
                }
            }
        }
    }
}

data class Transaction(val type: String, val category: String, val note: String, val amount: Int)

fun getTodayDate(): String {
    val sdf = SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
    return sdf.format(Date())
}

fun getPreviousDate(date: String): String {
    val sdf = SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.time = sdf.parse(date)
    calendar.add(Calendar.DATE, -1)
    return sdf.format(calendar.time)
}

fun getNextDate(date: String): String {
    val sdf = SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.time = sdf.parse(date)
    calendar.add(Calendar.DATE, 1)
    return sdf.format(calendar.time)
}

@Preview(showBackground = true)
@Composable
fun PreviewAppContent() {
    MoneyAppTheme {
        AppContent(
            navigateToSettings = {},
            navigateToAccount = {},
            navigateToHome = {}
        )
    }
}
