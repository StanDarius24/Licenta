package com.stannis.parser.checkSum

import mu.KotlinLogging
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


object CheckSum {

    fun getMD5EncryptedString(encTarget: String): String? {
        var mdEnc: MessageDigest? = null
        try {
            mdEnc = MessageDigest.getInstance("MD5")
        } catch (e: NoSuchAlgorithmException) {
            val logger = KotlinLogging.logger {}
            logger.error { "Exception while encrypting to md5" }
            logger.error { e.printStackTrace() }
        }
        mdEnc!!.update(encTarget.toByteArray(), 0, encTarget.length)
        var md5 = BigInteger(1, mdEnc.digest()).toString(16)
        while (md5.length < 32) {
            md5 = "0$md5"
        }
        return md5
    }

}