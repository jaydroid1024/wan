package com.jaydroid.net

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import okio.Buffer
import java.io.IOException
import java.io.InputStream
import java.security.*
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * 网络链接信息工具类,用于保护HTTPS连接
 * @author wangxuejie
 * @version 1.0
 * @date 2019-12-24 17:18
 */

class HttpsSecure {
    /**
     * 默认单向验证
     */
    private var sslType = ONE_VERIFY

    private var serverCerIs: InputStream? = null

    private var clientCerIs: InputStream? = null

    private var clientCerPassword = "123456"

    /**
     * 获得SSL配置
     */
    //设置可访问所有的Https网站
    //单向验证，设置具体的证书
    //双向验证
    val sslParams: SSLParams?
        get() {

            var sslParams: SSLParams? =
                null
            when (sslType) {
                NO_VERIFY -> sslParams =
                    getSslSocketFactory(
                        null,
                        null,
                        null
                    )
                ONE_VERIFY -> sslParams =
                    getSslSocketFactory(
                        if (serverCerIs != null) arrayOf(serverCerIs!!) else null,
                        null,
                        null
                    )
                TWO_VERIFY -> sslParams =
                    getSslSocketFactory(
                        if (serverCerIs != null) arrayOf(serverCerIs!!) else null,
                        clientCerIs, clientCerPassword
                    )
                else -> {
                }
            }
            return sslParams
        }

    class SSLParams {
        var sSLSocketFactory: SSLSocketFactory? = null

        var trustManager: X509TrustManager? = null
    }

    private inner class UnSafeHostnameVerifier : HostnameVerifier {
        override fun verify(hostname: String, session: SSLSession): Boolean {

            return true
        }
    }

    @SuppressLint("TrustAllX509TrustManager")
    private class UnSafeTrustManager : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {

        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {

        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {

            return arrayOf()
        }
    }


    private class MyTrustManager @Throws(NoSuchAlgorithmException::class, KeyStoreException::class)
    constructor(private val localTrustManager: X509TrustManager?) : X509TrustManager {
        private val defaultTrustManager: X509TrustManager?

        init {

            val var4 = TrustManagerFactory.getInstance(
                TrustManagerFactory
                    .getDefaultAlgorithm()
            )
            var4.init(null as KeyStore?)
            defaultTrustManager =
                chooseTrustManager(
                    var4.trustManagers
                )
        }


        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {

        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {

            try {
                defaultTrustManager?.checkServerTrusted(chain, authType)
            } catch (ce: CertificateException) {
                localTrustManager?.checkServerTrusted(chain, authType)
            }

        }


        override fun getAcceptedIssuers(): Array<X509Certificate?> {

            return arrayOfNulls(0)
        }
    }

    /**
     * init
     *
     * @param context       上下文
     * @param serverCer     server证书字符串
     * @param serverCerFile server证书文件
     * @param clientCer     client证书字符串
     * @param clientCerFile client证书文件
     * @param sslType       ssl类型
     */
    fun init(
        context: Context,
        serverCer: String?,
        serverCerFile: String?,
        clientCer: String?,
        clientCerFile: String?,
        sslType: Int
    ) {

        val applicationContext = context.applicationContext

        try {
            this.sslType = sslType

            if (!TextUtils.isEmpty(serverCer)) {
                serverCerIs = Buffer().writeUtf8(serverCer!!).inputStream()
            } else if (!TextUtils.isEmpty(serverCerFile)) {
                serverCerIs = applicationContext.assets.open(serverCerFile!!)
            }

            if (!TextUtils.isEmpty(clientCer)) {
                clientCerIs = Buffer().writeUtf8(clientCer!!).inputStream()
            } else if (!TextUtils.isEmpty(clientCerFile)) {
                clientCerIs = applicationContext.assets.open(clientCerFile!!)
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    companion object {

        /**
         * 证书name
         */
        const val CRT_NAME = "aoaosong.crt"

        /**
         * 设置可访问所有的Https网站
         */
        const val NO_VERIFY = 0

        /**
         * 单向验证
         */
        const val ONE_VERIFY = 1

        /**
         * 双向验证
         */
        const val TWO_VERIFY = 2

        fun getSslSocketFactory(
            certificates: Array<InputStream>?, bksFile: InputStream?, password: String?
        ): SSLParams {

            val sslParams = SSLParams()
            try {
                val trustManagers =
                    prepareTrustManager(
                        certificates
                    )
                val keyManagers =
                    prepareKeyManager(
                        bksFile,
                        password
                    )
                val sslContext = SSLContext.getInstance("TLS")
                val trustManager: X509TrustManager
                trustManager = if (trustManagers != null) {
                    MyTrustManager(
                        chooseTrustManager(
                            trustManagers
                        )
                    )
                } else {
                    UnSafeTrustManager()
                }
                sslContext.init(keyManagers, arrayOf<TrustManager>(trustManager), null)
                sslParams.sSLSocketFactory = sslContext.socketFactory
                sslParams.trustManager = trustManager
                return sslParams
            } catch (e: NoSuchAlgorithmException) {
                throw AssertionError(e)
            } catch (e: KeyManagementException) {
                throw AssertionError(e)
            } catch (e: KeyStoreException) {
                throw AssertionError(e)
            }

        }

        private fun prepareTrustManager(certificates: Array<InputStream>?): Array<TrustManager>? {

            if (certificates == null || certificates.isEmpty()) {
                return null
            }
            try {
                val certificateFactory = CertificateFactory.getInstance("X.509")
                val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
                keyStore.load(null)
                for ((index, certificate) in certificates.withIndex()) {
                    val certificateAlias = Integer.toString(index)
                    keyStore.setCertificateEntry(
                        certificateAlias, certificateFactory
                            .generateCertificate(certificate)
                    )
                    try {
                        certificate.close()
                    } catch (e: IOException) {
                    }

                }
                val trustManagerFactory: TrustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                trustManagerFactory.init(keyStore)

                return trustManagerFactory.trustManagers

            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: CertificateException) {
                e.printStackTrace()
            } catch (e: KeyStoreException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null

        }

        private fun prepareKeyManager(
            bksFile: InputStream?,
            password: String?
        ): Array<KeyManager>? {

            try {
                if (bksFile == null || password == null) {
                    return null
                }

                val clientKeyStore = KeyStore.getInstance("BKS")
                clientKeyStore.load(bksFile, password.toCharArray())
                val keyManagerFactory = KeyManagerFactory.getInstance(
                    KeyManagerFactory
                        .getDefaultAlgorithm()
                )
                keyManagerFactory.init(clientKeyStore, password.toCharArray())
                return keyManagerFactory.keyManagers

            } catch (e: KeyStoreException) {
                e.printStackTrace()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: UnrecoverableKeyException) {
                e.printStackTrace()
            } catch (e: CertificateException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        private fun chooseTrustManager(trustManagers: Array<TrustManager>?): X509TrustManager? {

            if (trustManagers == null) {
                return null
            }
            for (trustManager in trustManagers) {
                if (trustManager is X509TrustManager) {
                    return trustManager
                }
            }
            return null
        }
    }
}
