package com.hfad.moneyapp

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

// TermsActivity
class TermsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyAppTheme {
                TermsScreen(onClose = { finish() })
            }
        }
    }
}

@Composable
fun TermsScreen(onClose: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBarWithClose(onClose = onClose, title = "記帳軟體使用者條款")
        }
    ) { innerPadding ->
        // 將內邊距正確應用到可滾動內容
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()) // 啟用垂直滾動
        ) {
            TermsContent()
        }
    }
}

@Composable
fun TermsContent() {
    // 條款內容
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "一、接受條款\n" +
                    "歡迎使用本記帳軟體。在註冊帳號或使用本服務之前，請仔細閱讀以下使用者條款。當您註冊或使用本服務時，即表示您已同意並接受以下所有條款與條件。\n" +
                    "\n" +
                    "二、帳號註冊與管理\n" +
                    "1.\t帳號註冊：\n" +
                    "\t●用戶需提供有效的電子郵件地址與必要的個人資料註冊帳號。註冊後，請妥善保管個人帳號資訊，並對其使用負責。\n" +
                    "2.\t帳號安全：\n" +
                    "\t●用戶應妥善保管帳號密碼，避免洩露。如發現未經授權的帳號使用行為，請立即通知我們。\n" +
                    "3.\t資料真實性：\n" +
                    "\t●用戶提供的個人資料需真實、準確，如發現資料不實，本公司有權暫停或終止帳號。\n" +
                    "\n" +
                    "三、隱私政策與資料保護\n" +
                    "1.\t數據收集與使用：\n" +
                    "\t●本軟體將遵循隱私政策，僅在合法範圍內收集和使用用戶數據，並確保數據安全性。\n" +
                    "2.\t數據存儲與安全：\n" +
                    "\t●用戶的數據將進行加密存儲，並採取業界標準的安全措施保護資料安全，防止未經授權的存取。\n" +
                    "3.\t隱私權保護：\n" +
                    "\t●除非法律要求，否則我們不會向任何第三方分享您的個人資料。\n" +
                    "\n" +
                    "四、用戶責任與義務\n" +
                    "1.\t合法使用：\n" +
                    "\t●用戶需遵守相關法律法規，不得利用本軟體進行任何非法或違反公序良俗的活動。\n" +
                    "2.\t準確數據輸入：\n" +
                    "\t●用戶需保證其輸入的財務數據的準確性，系統根據數據生成的報表和分析結果僅供參考。\n" +
                    "3.\t不得惡意攻擊：\n" +
                    "\t●禁止任何形式的系統攻擊、數據竊取或未經授權的存取行為，違者將依法追究責任。\n" +
                    "\n" +
                    "五、服務變更與終止\n" +
                    "1.\t服務變更：\n" +
                    "\t●我們保留對服務進行更改、更新或中止的權利，變更將事先通知用戶。\n" +
                    "2.\t服務終止：\n" +
                    "\t●用戶違反本條款、提交虛假資料或從事非法行為，將被終止使用權限。\n" +
                    "\n" +
                    "六、免責聲明\n" +
                    "1.\t數據準確性：\n" +
                    "\t●本軟體提供的財務建議僅供參考，無法保證所有數據與分析結果的絕對準確性。\n" +
                    "2.\t不可抗力：\n" +
                    "\t●由於自然災害、技術故障或其他不可抗力事件造成的資料損失，系統不承擔賠償責任。\n" +
                    "\n" +
                    "七、法律適用與爭議解決\n" +
                    "1.\t法律適用：\n" +
                    "\t●本條款適用於系統運營所在的司法管轄區。\n" +
                    "2.\t爭議解決：\n" +
                    "\t●如有爭議，應以友好協商為主，無法協商時，應提交運營所在地的法院處理。\n" +
                    "\n" +
                    "請務必仔細閱讀並接受上述條款後再進行註冊。感謝您選擇我們的記帳軟體！",
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
    }
}

@Composable
fun TopAppBarWithClose(onClose: () -> Unit, title: String) {
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
fun TermsScreenPreview() {
    MoneyAppTheme {
        TermsScreen(onClose = {})
    }
}
