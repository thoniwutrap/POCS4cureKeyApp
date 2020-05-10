package com.app.poc.securekey

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        key.text = Keys.apiKey()
        btnWriteFile.setOnClickListener {
            val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
          //  val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

            val fileToWrite = "contact.json"
            val encryptedFile = EncryptedFile.Builder(
                File(this.filesDir, fileToWrite),
                this,
                Keys.apiKey(),
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build()

            encryptedFile.openFileOutput().bufferedWriter().use {
                it.write("MY SUPER-SECRET INFORMATIONfsdfdsfsdfsdfsdfds")
            }
        }

        btnReadFile.setOnClickListener {
            val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
            val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

            val fileToRead = "contact.json"
            val encryptedFile = EncryptedFile.Builder(
                File(this.filesDir, fileToRead),
                this,
                Keys.apiKey(),
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build()

            val contents = encryptedFile.openFileInput().bufferedReader().useLines { lines ->
                lines.fold("") { working, line ->
                    "$working\n$line"
                }
            }

            Log.e("fsfd",contents)
        }
    }
}
