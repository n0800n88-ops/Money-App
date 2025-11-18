package com.hfad.moneyapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.moneyapp.ui.theme.MoneyAppTheme

// PrivacyPolicyActivity
class PrivacyPolicyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyAppTheme {
                PrivacyPolicyScreen(onClose = { finish() })
            }
        }
    }
}

@Composable
fun PrivacyPolicyScreen(onClose: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(onClose = onClose, title = "記帳軟體隱私權政策")
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            PrivacyPolicyContent()
        }
    }
}

@Composable
fun PrivacyPolicyContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "一、適用範圍\n" +
                    "本隱私權條款適用於用戶在註冊、使用記帳軟體服務時所提供的個人資料與系統自動收集的資料。我們致力於保護您的個人隱私，請務必詳閱下列內容。\n" +
                    "\n" +
                    "二、資料收集與使用\n" +
                    "1.\t收集的資料類型：\n" +
                    "\t●個人識別資料：用戶註冊時提供的姓名、電子郵件地址、手機號碼等。\n" +
                    "\t●設備與系統資訊：包括裝置類型、操作系統版本、IP 地址等技術數據。\n" +
                    "\t●應用程式使用資料：包括記帳數據、交易記錄與使用者互動行為。\n" +
                    "2.\t資料的使用方式：\n" +
                    "\t●服務運營：用於帳號管理、數據備份與記帳數據分析。\n" +
                    "\t●個性化體驗：根據用戶的操作歷史提供個人化的財務建議與提醒。\n" +
                    "\t●安全性管理：用於身份驗證、風險評估與防止未經授權的存取。\n" +
                    "\n" +
                    "三、資料分享與揭露\n" +
                    "1.\t不會分享個人資料給第三方，除非符合下列情況：\n" +
                    "\t●用戶同意：獲得用戶的明確授權後方可分享。\n" +
                    "\t●法規要求：依法要求提供的資料。\n" +
                    "\t●系統運營需求：在需要使用第三方服務（如資料存儲、支付平台）時，僅限於達成該服務必要的資料。\n" +
                    "\n" +
                    "四、資料儲存與保護\n" +
                    "1.\t數據安全措施：\n" +
                    "\t●使用 AES-256 資料加密 保護敏感數據。\n" +
                    "\t●傳輸過程中使用 SSL/TLS 加密技術，確保數據安全傳輸。\n" +
                    "2.\t資料保存期限：\n" +
                    "\t●記帳數據將保存至用戶主動刪除或帳號註銷後 6 個月內刪除。\n" +
                    "\t●合法合約或其他法規規定的存儲要求例外。\n" +
                    "3.\t資料存取控制：\n" +
                    "\t●限制內部存取權限，僅授權人員可接觸相關資料。\n" +
                    "\n" +
                    "五、用戶權利與選擇\n" +
                    "1.\t資料查詢與更正：\n" +
                    "\t●用戶可隨時登入帳號檢視與更新個人資料。\n" +
                    "2.\t資料刪除與撤回同意：\n" +
                    "\t●用戶可透過應用程式或聯繫客服要求刪除個人資料，或撤回對某些資料的授權。\n" +
                    "3.\t隱私設定管理：\n" +
                    "\t●用戶可管理是否啟用應用內的個性化建議與數據分析。\n" +
                    "\n" +
                    "六、未成年人的隱私保護\n" +
                    "本應用程式僅供 16 歲以上 的用戶註冊與使用。未成年用戶應在法定監護人的同意與指導下進行註冊。若發現未經授權的未成年用戶資料，我們將立即刪除相關數據。\n" +
                    "\n" +
                    "七、隱私政策的修訂\n" +
                    "我們可能因應業務需求與法規變更適時修訂隱私權條款。修訂後的條款將公佈於官方網站與應用程式中，重大更動將主動通知用戶。\n" +
                    "\n" +
                    "八、聯繫我們\n" +
                    "若有任何關於隱私權條款的疑問或資料存取請求，請聯繫我們：\n" +
                    "電子郵件：privacy@accountingapp.com\n" +
                    "電話支援：+886-1234-5678\n" +
                    "\n" +
                    "感謝您信任我們的記帳軟體，我們將全力保護您的個人隱私！\n",
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
        // Add your privacy policy text content here...
    }
}

@Composable
fun TopAppBar(onClose: () -> Unit, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFCC33))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "\u2715",
            fontSize = 24.sp,
            modifier = Modifier
                .clickable { onClose() }
                .padding(end = 16.dp),
            color = Color.Black
        )
        Text(
            text = title,
            fontSize = 18.sp,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrivacyPolicyScreenPreview() {
    MoneyAppTheme {
        PrivacyPolicyScreen(onClose = {})
    }
}
