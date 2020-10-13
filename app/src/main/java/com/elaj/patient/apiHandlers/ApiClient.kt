package com.elaj.patient.apiHandlers

import com.elaj.patient.classes.GlobalData
import com.elaj.patient.classes.TLSSocketFactory
import com.google.gson.GsonBuilder
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import kotlin.jvm.Throws

object ApiClient {
    val BASE_URL: String = GlobalData.ApiURL
    private var retrofit: Retrofit? = null
    private var retrofitCustom: Retrofit? = null
    private var retrofitLong: Retrofit? = null
    private val spec = ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
        .supportsTlsExtensions(true)
        .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
        .cipherSuites(
            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
            CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
            CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
            CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
            CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
            CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA
        )
        .build()

    fun getCustomClient(baseUrl: String?): Retrofit? {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        //        if (retrofit == null) {
        retrofitCustom = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okClient)
            .build()
        //        }
        return retrofitCustom
    }

    val client: Retrofit?
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okClient)
                    .build()
            }
            return retrofit
        }

    val longClient: Retrofit?
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            if (retrofitLong == null) {
                retrofitLong = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(longOkClient)
                    .build()
            }
            return retrofitLong
        }//            ProviderInstaller.installIfNeeded(RootApplication.getInstance());
// Create a trust manager that does not validate certificate chains
    // Install the all-trusting trust manager
//            final SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
// Create an ssl socket factory with our all-trusting manager
//            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
    //            client.sslSocketFactory(new TLSSocketFactory());

    //                .addInterceptor(interceptor)
    private val okClient: OkHttpClient
        private get() {
            val specs: MutableList<ConnectionSpec> =
                ArrayList()
            specs.add(spec)
            specs.add(ConnectionSpec.COMPATIBLE_TLS)
            specs.add(ConnectionSpec.CLEARTEXT)
            val client =
                OkHttpClient.Builder() //                .addInterceptor(interceptor)
                    .connectionSpecs(specs)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
            try { //            ProviderInstaller.installIfNeeded(RootApplication.getInstance());
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts =
                    arrayOf<TrustManager>(
                        object : X509TrustManager {
                            @Throws(CertificateException::class)
                            override fun checkClientTrusted(
                                chain: Array<X509Certificate>,
                                authType: String
                            ) {
                            }

                            @Throws(CertificateException::class)
                            override fun checkServerTrusted(
                                chain: Array<X509Certificate>,
                                authType: String
                            ) {
                            }

                            override fun getAcceptedIssuers(): Array<X509Certificate> {
                                return arrayOf()
                            }
                        }
                    )
                // Install the all-trusting trust manager
                //            final SSLContext sslContext = SSLContext.getInstance("SSL");
                //            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                //            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                client.sslSocketFactory(
                    TLSSocketFactory(),
                    trustAllCerts[0] as X509TrustManager
                )
                //            client.sslSocketFactory(new TLSSocketFactory());
                client.hostnameVerifier { hostname, session -> true }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
            return client.build()
        }//            ProviderInstaller.installIfNeeded(RootApplication.getInstance());
// Create a trust manager that does not validate certificate chains
    // Install the all-trusting trust manager
//            final SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
// Create an ssl socket factory with our all-trusting manager
//            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
    //            client.sslSocketFactory(new TLSSocketFactory());

    //                .addInterceptor(interceptor)
    private val longOkClient: OkHttpClient
        private get() {
            val specs: MutableList<ConnectionSpec> =
                ArrayList()
            specs.add(spec)
            specs.add(ConnectionSpec.COMPATIBLE_TLS)
            specs.add(ConnectionSpec.CLEARTEXT)
            val client =
                OkHttpClient.Builder() //                .addInterceptor(interceptor)
                    .connectionSpecs(specs)
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
            try { //            ProviderInstaller.installIfNeeded(RootApplication.getInstance());
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts =
                    arrayOf<TrustManager>(
                        object : X509TrustManager {
                            @Throws(CertificateException::class)
                            override fun checkClientTrusted(
                                chain: Array<X509Certificate>,
                                authType: String
                            ) {
                            }

                            @Throws(CertificateException::class)
                            override fun checkServerTrusted(
                                chain: Array<X509Certificate>,
                                authType: String
                            ) {
                            }

                            override fun getAcceptedIssuers(): Array<X509Certificate> {
                                return arrayOf()
                            }
                        }
                    )
                // Install the all-trusting trust manager
                //            final SSLContext sslContext = SSLContext.getInstance("SSL");
                //            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                //            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                client.sslSocketFactory(
                    TLSSocketFactory(),
                    trustAllCerts[0] as X509TrustManager
                )
                //            client.sslSocketFactory(new TLSSocketFactory());
                client.hostnameVerifier { hostname, session -> true }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
            return client.build()
        }
}
