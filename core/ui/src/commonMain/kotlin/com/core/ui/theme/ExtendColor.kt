package com.core.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.core.ui.theme.foundation.ExtendedColorScheme

val extendedLight = ExtendedColorScheme(
    success = Color(0xFF4C6700),
    onSuccess = Color(0xFFFFFFFF),
    successContainer = Color(0xFFA2D608),
    onSuccessContainer = Color(0xFF425900),
    warning = Color(0xFF805600),
    onWarning = Color(0xFFFFFFFF),
    warningContainer = Color(0xFFFFBA44),
    onWarningContainer = Color(0xFF704B00),
    danger = Color(0xFFBB0014),
    onDanger = Color(0xFFFFFFFF),
    dangerContainer = Color(0xFFE51B23),
    onDangerContainer = Color(0xFFFFFBFF),
)

val extendedDark = ExtendedColorScheme(
    success = Color(0xFFBDF334),
    onSuccess = Color(0xFF263500),
    successContainer = Color(0xFFA2D608),
    onSuccessContainer = Color(0xFF425900),
    warning = Color(0xFFFFDEB0),
    onWarning = Color(0xFF442C00),
    warningContainer = Color(0xFFFFBA44),
    onWarningContainer = Color(0xFF704B00),
    danger = Color(0xFFFFB4AB),
    onDanger = Color(0xFF690006),
    dangerContainer = Color(0xFFFF544B),
    onDangerContainer = Color(0xFF470003),
)


val LocalExtendedColorScheme = staticCompositionLocalOf { extendedLight }