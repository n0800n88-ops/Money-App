package com.hfad.moneyapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.moneyapp.ui.theme.MoneyAppTheme

class SettingActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoneyAppTheme {
                SettingScreen(
                    userViewModel = userViewModel,
                    onProfileClick = { navigateToProfileActivity() },
                    onLogoutClick = { navigateToAssignActivity() },
                    onReminderSettingClick = { navigateToReminderSettingActivity() },
                    onBudgetSettingClick = { navigateToBudgetSettingActivity() },
                    onNavigateToAccount = { navigateToAccountActivity() },
                    onCardSettingClick = { navigateToCardActivity() },
                    onReportClick = { navigateToReportActivity() } // 新增報表頁面導航
                )
            }
        }
    }

    private fun navigateToAssignActivity() {
        val intent = Intent(this, AssignActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToCardActivity() {
        val intent = Intent(this, CardActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToReminderSettingActivity() {
        val intent = Intent(this, ReminderSettingActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToBudgetSettingActivity() {
        val intent = Intent(this, BudgetSettingActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
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
fun SettingScreen(
    userViewModel: UserViewModel,
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onCardSettingClick: () -> Unit,
    onReminderSettingClick: () -> Unit,
    onBudgetSettingClick: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onReportClick: () -> Unit // 新增報表頁面點擊事件參數
) {
    val userName = userViewModel.username.collectAsState().value ?: "qq"

    Scaffold(
        containerColor = Color(0xFFF4D6A3),
        bottomBar = {
            BottomAppBar(containerColor = Color(0xFFF7B977)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onReportClick) { // 左邊按鈕跳轉至報表頁面
                        Image(
                            painter = painterResource(id = R.drawable.business_report),
                            contentDescription = "Report",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    IconButton(onClick = onNavigateToAccount) {
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
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE7D4B5)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_profile_placeholder),
                            contentDescription = "Profile Picture",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = userName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                }
                Text(
                    text = ">",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.clickable { onProfileClick() }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onCardSettingClick,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB29068))
            ) {
                Text(
                    text = "管理卡片/帳戶",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onReminderSettingClick,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB29068))
            ) {
                Text(
                    text = "記帳提醒",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onBudgetSettingClick,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB29068))
            ) {
                Text(
                    text = "超支提醒",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Divider(
                color = Color.Black,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onLogoutClick,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
            ) {
                Text(
                    text = "登出",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    val fakeViewModel = UserViewModel().apply {
        setUsername("Preview User")
    }
    MoneyAppTheme {
        SettingScreen(
            userViewModel = fakeViewModel,
            onCardSettingClick = {},
            onProfileClick = {},
            onLogoutClick = {},
            onReminderSettingClick = {},
            onBudgetSettingClick = {},
            onNavigateToAccount = {},
            onReportClick = {} // 假資料的報表頁面點擊事件
        )
    }
}
