package network

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttpConfig
import org.mongodb.kbson.BuildConfig
import java.security.SecureRandom
import javax.net.ssl.SSLContext

internal actual fun HttpClientConfig<*>.configureForPlatform() {
    engine {
        this as OkHttpConfig
        config {
            if (BuildConfig.DEBUG) {
                val trustAllCert = AllCertsTrustManager()
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, arrayOf(trustAllCert), SecureRandom())
                sslSocketFactory(sslContext.socketFactory, trustAllCert)
            }
        }
    }
}