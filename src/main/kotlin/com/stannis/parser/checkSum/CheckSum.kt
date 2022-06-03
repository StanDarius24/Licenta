package com.stannis.parser.checkSum

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


object CheckSum {

    fun getMD5EncryptedString(encTarget: String): String? {
        var mdEnc: MessageDigest? = null
        try {
            mdEnc = MessageDigest.getInstance("MD5")
        } catch (e: NoSuchAlgorithmException) {
            println("Exception while encrypting to md5")
            e.printStackTrace()
        }
        mdEnc!!.update(encTarget.toByteArray(), 0, encTarget.length)
        var md5 = BigInteger(1, mdEnc.digest()).toString(16)
        while (md5.length < 32) {
            md5 = "0$md5"
        }
        return md5
    }

}