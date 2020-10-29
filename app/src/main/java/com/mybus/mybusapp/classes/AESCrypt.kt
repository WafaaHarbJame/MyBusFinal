package com.mybus.mybusapp.classes

import android.util.Base64
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.jvm.Throws

object AESCrypt {
    private const val ALGORITHM = "AES"
    private const val KEY = "1Hbfh667adfDEJ78"

    fun encrypt(value: String): String {
        val key: Key = generateKey()
        val cipher: Cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedByteValue: ByteArray =
            cipher.doFinal(value.toByteArray(charset("utf-8")))
        return Base64.encodeToString(encryptedByteValue, Base64.DEFAULT)
    }

    @Throws(Exception::class)
    fun decrypt(value: String?): String? {
        val key = generateKey()
        val cipher =
            Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decryptedValue64 =
            Base64.decode(value, Base64.DEFAULT)
        val decryptedByteValue = cipher.doFinal(decryptedValue64)
        return String(decryptedByteValue, charset("utf-8"))
    }


    private fun generateKey(): Key {
        return SecretKeySpec(KEY.toByteArray(), ALGORITHM)
    }
}