package com.fghilmany.sharedpreference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SharedPreferenceManager(private val context: Context) {

    private var ourInstance : SharedPreferenceManager? = null
    private var prefs: SharedPreferences? = null

    val instance: SharedPreferenceManager get(){
        if (ourInstance == null){
            ourInstance = SharedPreferenceManager(context)
        }
        return ourInstance as SharedPreferenceManager
    }

    private var sharedName : String = "shared_preference"

    init {
        if (prefs == null){
            val spec = KeyGenParameterSpec.Builder(
                MasterKey.DEFAULT_MASTER_KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
            val masterKey = MasterKey.Builder(context)
                .setKeyGenParameterSpec(spec)
                .build()
            prefs = EncryptedSharedPreferences
                .create(
                    context,
                    sharedName,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
        }
    }

    fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value.toInt()) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value.toFloat()) }
            is Long -> edit { it.putLong(key, value.toLong()) }
            else -> {
                Log.e("shared_preference", "Unsupported Type: $value")
            }
        }
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    private fun SharedPreferences.clear() {
        edit { it.clear() }
    }


    fun setValue(key: String, value: Any?){
        prefs?.set(key, value)
    }

    fun clear(){
        prefs?.clear()
    }

    fun getString(key: String) = prefs?.getString(key, null)
    fun getBoolean(key: String) = prefs?.getBoolean(key, false)
    fun getInt(key: String) = prefs?.getInt(key, 0)
    fun getFloat(key: String) = prefs?.getFloat(key, 0f)
    fun getLong(key: String) = prefs?.getLong(key, 0L)

    companion object{
        const val SP_KEY = "sp_key"
    }

}