package com.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.core.common.extension.asString
import com.core.common.extension.current
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    Column(modifier = modifier.then(Modifier.fillMaxSize())) {
        val date = remember { LocalDateTime.current(TimeZone.UTC) }
        val withoutTz = remember(date) {
            date.asString(
                format = "dd MMMM yyyy, HH:mm zzz",
                atZone = TimeZone.UTC,
                languageTag = "id_ID"
            )
        }

        val withTz = remember(date) {
            date.asString(
                format = "dd MMMM yyyy, HH:mm zzz",
                atZone = TimeZone.UTC,
                toZone = TimeZone.of("Asia/Jakarta"),
                languageTag = "id-ID"
            )
        }
        
        Text("CURRENT IN DEVICE => $date")
        Text("CURRENT IN LOCALE => $withoutTz")
        Text("CURRENT IN LOCALE WITH TZ => $withTz")
    }
}