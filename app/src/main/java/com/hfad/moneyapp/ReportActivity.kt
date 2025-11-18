package com.hfad.moneyapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.moneyapp.ui.theme.MoneyAppTheme

class ReportActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoneyAppTheme {
                ReportPage(
                    navigateToReport = { navigateToActivity(ReportActivity::class.java) },
                    navigateToAccount = { navigateToActivity(AccountActivity::class.java) },
                    navigateToSettings = { navigateToActivity(SettingActivity::class.java) }
                )
            }
        }
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}

@Composable
fun ReportPage(
    navigateToReport: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToSettings: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("支出") }
    var selectedMonth by remember { mutableStateOf(getCurrentMonth()) }

    Scaffold(
        bottomBar = {
            ReportBottomNavigationBar(
                navigateToReport = navigateToReport,
                navigateToAccount = navigateToAccount,
                navigateToSettings = navigateToSettings
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color(0xFFFFF6E5))
            ) {
                MonthSelector(
                    selectedMonth = selectedMonth,
                    onPreviousMonth = { selectedMonth = getPreviousMonth(selectedMonth) },
                    onNextMonth = { selectedMonth = getNextMonth(selectedMonth) }
                )
                TabSelector(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
                when (selectedTab) {
                    "收入" -> IncomePage(selectedMonth)
                    "支出" -> ExpensePage(selectedMonth)
                    "總額" -> SummaryPage(selectedMonth)
                }
            }
        }
    )
}

@Composable
fun ReportBottomNavigationBar(
    navigateToReport: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToSettings: () -> Unit
) {
    BottomAppBar(containerColor = Color(0xFFF7B977)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(onClick = navigateToReport) {
                Image(
                    painter = painterResource(id = R.drawable.business_report),
                    contentDescription = "Report",
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
fun MonthSelector(selectedMonth: String, onPreviousMonth: () -> Unit, onNextMonth: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("<", modifier = Modifier.clickable { onPreviousMonth() }, fontSize = 24.sp)
        Text(
            selectedMonth,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Text(
            ">",
            modifier = Modifier.clickable { onNextMonth() },
            fontSize = 24.sp,
            color = if (isFutureMonth(selectedMonth)) Color.Gray else Color.Black
        )
    }
}

@Composable
fun TabSelector(selectedTab: String, onTabSelected: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf("支出", "收入", "總額").forEach { tab ->
            Text(
                text = tab,
                modifier = Modifier
                    .clickable { onTabSelected(tab) }
                    .background(
                        if (selectedTab == tab) Color(0xFFF7B977) else Color(0xFFE0E0E0),
                        MaterialTheme.shapes.medium
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun IncomePage(month: String) {
    val incomes = if (month == "2024年11月") {
        listOf(
            Quadruple("1", "工作", "薪資", 18000),
            Quadruple("2", "投資", "股票收益", 6000),
            Quadruple("3", "零用錢", "家庭補貼", 2500),
            Quadruple("4", "獎學金", "學術競賽獎金", 2000),
            Quadruple("5", "販售", "二手物品出售", 1100),
            Quadruple("6", "中獎", "抽獎收入", 1000)
        )
    } else {
        listOf(
            Quadruple("1", "兼職", "薪水", 3500),
            Quadruple("2", "股票", "分紅", 1200),
            Quadruple("3", "助學金", "資助", 2000),
            Quadruple("4", "寫作", "稿費", 800),
            Quadruple("5", "販售", "二手商品", 1000),
            Quadruple("6", "小獎", "獎金", 500),
            Quadruple("7", "債券", "收益", 900)
        )
    }
    DisplayDetailedTransactions(title = "本月總收入", amount = incomes.sumOf { it.fourth }, transactions = incomes)
}

@Composable
fun ExpensePage(month: String) {
    val expenses = if (month == "2024年11月") {
        listOf(
            Quadruple("1", "飲食", "早餐", 50),
            Quadruple("2", "飲食", "午餐", 120),
            Quadruple("3", "飲食", "晚餐", 250),
            Quadruple("4", "飲品", "咖啡", 80),
            Quadruple("5", "購物", "衣服", 1500),
            Quadruple("6", "購物", "鞋子", 2000),
            Quadruple("7", "健康", "健身會員費", 1000),
            Quadruple("8", "學習", "課程費用", 3000),
            Quadruple("9", "娛樂", "遊樂園門票", 800),
            Quadruple("10", "日用品", "洗髮精", 150),
            Quadruple("11", "家庭", "水電費", 2500),
            Quadruple("12", "旅遊", "車票", 800),
            Quadruple("13", "訂閱", "音樂平台", 300),
            Quadruple("14", "運動", "羽毛球場租金", 400)
        )
    } else {
        listOf(
            Quadruple("1", "飲食", "早餐", 50),
            Quadruple("2", "飲品", "奶茶", 45),
            Quadruple("3", "日用品", "洗髮精", 150),
            Quadruple("4", "飲食", "午餐", 120),
            Quadruple("5", "飲品", "咖啡", 60),
            Quadruple("6", "健康", "維他命", 300),
            Quadruple("7", "學習", "書籍", 450),
            Quadruple("8", "娛樂", "電影票", 350),
            Quadruple("9", "家庭", "水電費", 2000),
            Quadruple("10", "旅遊", "火車票", 1200)
        )
    }
    DisplayDetailedTransactions(title = "本月總支出", amount = expenses.sumOf { it.fourth }, transactions = expenses)
}

@Composable
fun SummaryPage(month: String) {
    val incomes = if (month == "2024年11月") {
        listOf(18000f, 6000f, 2500f, 2000f, 1100f, 1000f)
    } else {
        listOf(3500f, 1200f, 2000f, 800f, 1000f, 500f, 900f)
    }

    val expenses = if (month == "2024年11月") {
        listOf(50f, 120f, 250f, 80f, 1500f, 2000f, 1000f, 3000f, 800f, 150f, 2500f, 800f, 300f, 400f)
    } else {
        listOf(50f, 45f, 150f, 120f, 60f, 300f, 450f, 350f, 2000f, 1200f)
    }

    val incomeCategories = mapOf("薪水" to incomes[0], "投資" to incomes[1], "其他" to incomes.drop(2).sum())
    val expenseCategories = mapOf(
        "飲食" to expenses.take(3).sum(),
        "購物" to expenses.subList(4, 6).sum(),
        "娛樂" to expenses.subList(8, 10).sum(),
        "其他" to expenses.filterIndexed { i, _ -> i !in listOf(0, 1, 2, 4, 5, 8, 9) }.sum()
    )

    val totalIncome = incomes.sum().toInt()
    val totalExpense = expenses.sum().toInt()
    val balance = totalIncome - totalExpense

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("收入與支出比例", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            PieChartWithLegend(data = incomeCategories, title = "收入比例")
            PieChartWithLegend(data = expenseCategories, title = "支出比例")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.LightGray, thickness = 0.5.dp)
        Text("本月總收入: $$totalIncome", fontSize = 16.sp)
        Text("本月總支出: $$totalExpense", fontSize = 16.sp)
        Divider(color = Color.LightGray, thickness = 0.5.dp)
        Text("本月結餘: $$balance", fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun PieChartWithLegend(data: Map<String, Float>, title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, fontWeight = FontWeight.Bold)
        Row(verticalAlignment = Alignment.CenterVertically) {
            PieChart(data = data)
            Column(modifier = Modifier.padding(start = 8.dp)) {
                data.keys.forEachIndexed { index, key ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(getColorByIndex(index))
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(key, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun PieChart(data: Map<String, Float>) {
    Canvas(modifier = Modifier.size(100.dp)) {
        var startAngle = 0f
        data.entries.forEachIndexed { index, entry ->
            val sweepAngle = entry.value / data.values.sum() * 360f
            drawArc(
                color = getColorByIndex(index),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true
            )
            startAngle += sweepAngle
        }
    }
}

fun getColorByIndex(index: Int): Color {
    val colors = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow, Color.Cyan)
    return colors[index % colors.size]
}

@Composable
fun DisplayDetailedTransactions(
    title: String,
    amount: Int,
    transactions: List<Quadruple<String, String, String, Int>>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("$title: $$amount", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(transactions) { transaction ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = transaction.first,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = transaction.second,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = transaction.third,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "$${transaction.fourth}",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )

                }
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}

data class Quadruple<A, B, C, D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)

fun getCurrentMonth(): String {
    return "2024年12月"
}

fun getPreviousMonth(current: String): String {
    return "2024年11月"
}

fun getNextMonth(current: String): String {
    return "2024年12月"
}

fun isFutureMonth(current: String): Boolean {
    return false
}

@Preview(showBackground = true)
@Composable
fun ReportPagePreview() {
    MoneyAppTheme {
        ReportPage({}, {}, {})
    }
}
