package com.example.widget

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.Image
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.size
import androidx.glance.text.Text

class MainWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = MainWidget()
}

class MainWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            var editing by remember { mutableStateOf(false) }
            var mobileNum by remember { mutableStateOf("") }
            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = mobileNum)
                Spacer(modifier = GlanceModifier.size(64.dp))
                Column {
                    Row {
                        repeat(3) {index ->
                            NumButton(text = (index + 1).toString()) {
                                mobileNum += (index + 1).toString()
                            }
                        }
                    }
                    Row {
                        repeat(3) {index ->
                            NumButton(text = (index + 4).toString()) {
                                mobileNum += (index + 4).toString()
                            }
                        }
                    }
                    Row {
                        repeat(3) {index ->
                            NumButton(text = (index + 7).toString()) {
                                mobileNum += (index + 7).toString()
                            }
                        }
                    }
                    Row {
                        Box(
                            modifier = GlanceModifier
                                .size(54.dp)
                                .clickable {
                                    val text = mobileNum
                                    mobileNum = text.substring(0, text.length -1)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                provider = ImageProvider(resId = R.drawable.backspace),
                                contentDescription = "backspace"
                            )
                        }
                        NumButton(text = "0") {
                            mobileNum += "0"
                        }
                        Box(
                            modifier = GlanceModifier
                                .size(54.dp)
                                .clickable {
                                    goToWhatsApp(context = context, mobileNum = mobileNum)
                                    mobileNum = ""
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                provider = ImageProvider(resId = R.drawable.ic_send),
                                contentDescription = "send"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun goToWhatsApp(context: Context, mobileNum: String) {
        val msg = "Hello"
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://api.whatsapp.com/send?phone=$mobileNum&text=$msg")
            ).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
    }
    
    @Composable
    fun NumButton(
        text: String,
        onClick: () -> Unit
    ) {
        Box(
            modifier = GlanceModifier
                .size(54.dp)
                .clickable {
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(text = text)
        }
    }

}
