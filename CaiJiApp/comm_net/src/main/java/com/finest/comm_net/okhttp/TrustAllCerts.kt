package com.finest.comm_net.okhttp

import java.security.cert.CertificateException
import java.security.cert.X509Certificate

import javax.net.ssl.X509TrustManager
import kotlin.jvm.Throws

/**
 * Created by XYZ on 2017/11/3.
 */

class TrustAllCerts : X509TrustManager {

    /**
     * 检查客户端的证书，若不信任该证书则抛出异常。由于我们不需要对客户端进行认证，因此我们只需要执行默认的信任管理器的这个方法。
     */
    @Throws(CertificateException::class)
    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {

    }

    /**
     * 检查服务器的证书，若不信任该证书同样抛出异常。通过自己实现该方法，可以使之信任我们指定的任何证书。
     * 在实现该方法时，也可以简单的不做任何处理，即一个空的函数体，由于不会抛出异常，它就会信任任何证书
     */
    @Throws(CertificateException::class)
    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {

    }

    /**
     * 返回受信任的X509证书
     */
    override fun getAcceptedIssuers(): Array<X509Certificate?> {
        val certificate: Array<X509Certificate?> = arrayOfNulls(0)
        return certificate
    }

}
