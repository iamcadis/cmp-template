package com.core.local

import com.core.network.AuthResponse
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.getBooleanFlow
import kotlinx.coroutines.flow.Flow

internal class UserPreferencesImpl : UserPreferences {
    private val settings: Settings by lazy { Settings() }
    private val observableSettings: ObservableSettings by lazy {
        settings as ObservableSettings
    }

    override val accessToken: String
        get() = settings.getString(ACCESS_TOKEN, "")

    override val refreshToken: String?
        get() = settings.getStringOrNull(REFRESH_TOKEN)

    @OptIn(ExperimentalSettingsApi::class)
    override val userHasLogin: Flow<Boolean>
        get() = observableSettings.getBooleanFlow(USER_HAS_LOGIN, false)

    override fun saveToken(response: AuthResponse) {
        settings.apply {
            putString(ACCESS_TOKEN, response.accessToken)
            putString(REFRESH_TOKEN, response.refreshToken.orEmpty())
        }
    }

    override fun setUserHasLogin(hasLogin: Boolean) {
        settings.putBoolean(USER_HAS_LOGIN, hasLogin)
    }

    override fun clear() {
        settings.remove(ACCESS_TOKEN)
        settings.remove(REFRESH_TOKEN)
        settings.remove(USER_HAS_LOGIN)
    }

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val USER_HAS_LOGIN = "user_has_login"
    }
}