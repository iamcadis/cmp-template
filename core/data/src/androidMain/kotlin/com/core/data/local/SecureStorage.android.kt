package com.core.data.local

import android.content.Context
import android.os.Build
import android.util.Base64 as AndroidBase64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.RegistryConfiguration
import com.google.crypto.tink.hybrid.HybridConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.Base64 as JavaBase64


class AndroidSecureStorage(
    private val context: Context,
    private val dataStore: DataStore<Preferences>
) : SecureStorage {

    private val aead: Aead by lazy {
        HybridConfig.register()

        AndroidKeysetManager.Builder()
            .withSharedPref(context, "master_key_preference", "master_key_file")
            .withKeyTemplate(KeyTemplates.get("AES256_GCM"))
            .withMasterKeyUri("android-keystore://master_key")
            .build()
            .keysetHandle
            .getPrimitive(RegistryConfiguration.get(), Aead::class.java)
    }

    override suspend fun get(key: String): String? {
        val encryptedBase64 = dataStore.data.map { prefs ->
            prefs[stringPreferencesKey(key)]
        }.first() ?: return null

        return try {
            val encryptedBytes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                JavaBase64.getDecoder().decode(encryptedBase64)
            } else {
                AndroidBase64.decode(encryptedBase64, AndroidBase64.DEFAULT)
            }
            val decryptedBytes = aead.decrypt(encryptedBytes, null)
            String(decryptedBytes)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun set(key: String, value: String) : Boolean {
        val encryptedBytes = aead.encrypt(value.toByteArray(), null)
        val encryptedBase64 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            JavaBase64.getEncoder().encodeToString(encryptedBytes)
        } else {
            AndroidBase64.encodeToString(encryptedBytes, AndroidBase64.DEFAULT)
        }

        return dataStore.safeEdit { prefs ->
            prefs[stringPreferencesKey(key)] = encryptedBase64
        }
    }

    override suspend fun remove(key: String) : Boolean {
        return dataStore.safeEdit { prefs ->
            prefs.remove(stringPreferencesKey(key))
        }
    }

    suspend fun DataStore<Preferences>.safeEdit(
        action: (MutablePreferences) -> Unit
    ): Boolean {
        return try {
            this.edit { action(it) }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}