package com.mew.practicalpasswordmanager.DataAndDB.Utlis

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

object Utility {

    // generate new AES key
    @Throws(Exception::class)
    fun generateAESKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256)
        return keyGenerator.generateKey()
    }

    // encrypt plaintext using AES method
    @Throws(Exception::class)
    fun encrypt(text: String, key: SecretKey?): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedBytes = cipher.doFinal(text.toByteArray())
        return android.util.Base64.encodeToString(encryptedBytes, android.util.Base64.DEFAULT)
    }

    // decryption of text using AES method
    @Throws(Exception::class)
    fun decrypt(ciphertext: String?, key: SecretKey?): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decodedBytes: ByteArray = android.util.Base64.decode(ciphertext, android.util.Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(decodedBytes)
        return String(decryptedBytes)
    }

    // convert AES key to string using Base64 encoding
    fun convertAESKeyToString(aesKey: SecretKey): String {
        val keyBytes = aesKey.encoded
        return Base64.getEncoder().encodeToString(keyBytes)
    }

    // convert string key to SecretKey
    fun convertStringToAESKey(aesKeyString: String?): SecretKey {
        val decodedKeyBytes: ByteArray = Base64.getDecoder().decode(aesKeyString)
        return SecretKeySpec(decodedKeyBytes, "AES")
    }
}