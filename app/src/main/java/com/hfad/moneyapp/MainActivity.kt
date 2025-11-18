package com.hfad.moneyapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.hfad.moneyapp.ui.theme.MoneyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 延遲2秒後跳轉到註冊頁面
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, AssignActivity::class.java)
            startActivity(intent)
            finish() // 結束當前頁面
        }, 2000)

        // 設置 UI
        setContent {
            MoneyAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF738F83)), // 設置背景顏色
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 儲蓄罐圖片
            Image(
                painter = painterResource(id = R.drawable.piggy_bank),
                contentDescription = "Piggy Bank",
                modifier = Modifier
                    .size(150.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 標語文字
            Text(
                text = "SAVE SMART!\n  SAVE EASY!",
                style = TextStyle(
                    color = Color(0xFFF4E1C1), // 設置文字顏色
                    fontSize = 20.sp, // 文字大小
                    fontWeight = FontWeight.Bold // 加粗字體
                ),
                lineHeight = 24.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MoneyAppTheme {
        MainScreen()
    }
}
