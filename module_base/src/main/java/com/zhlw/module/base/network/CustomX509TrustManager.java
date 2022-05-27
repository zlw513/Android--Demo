package com.zhlw.module.base.network;

import android.util.Log;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public final class CustomX509TrustManager implements X509TrustManager {

    private static final String TAG = "CustomX509TrustManager";

    /**
     * 证书过期
     */
    public static final String MSG_CERTIFICATE_EXCEPTION_CERTIFICATE_EXPIRED = "CertificateException CertificateExpired";

    private X509TrustManager mSystemDefaultTrustManager;

    public CustomX509TrustManager() {
        mSystemDefaultTrustManager = systemDefaultTrustManager();
    }

    private X509TrustManager systemDefaultTrustManager() {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            return (X509TrustManager) trustManagers[0];
        } catch (GeneralSecurityException e) {
            throw new AssertionError(); // The system has no TLS. Just give up.
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        mSystemDefaultTrustManager.checkClientTrusted(chain, authType);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        for (X509Certificate certificate : chain) {
            //第一步 校验证书是否过期 过期则加上固定的标识
            try {
                certificate.checkValidity();
            } catch (CertificateExpiredException e) {
                Log.w(TAG, "checkServerTrusted  checkValidity warn catch CertificateExpiredException ");
                throw new CertificateException(
                        MSG_CERTIFICATE_EXCEPTION_CERTIFICATE_EXPIRED);
            } catch (CertificateNotYetValidException e) {
                Log.w(TAG, "checkServerTrusted  checkValidity warn catch CertificateNotYetValidException ");
                throw new CertificateException(
                        MSG_CERTIFICATE_EXCEPTION_CERTIFICATE_EXPIRED);
            }
        }
        mSystemDefaultTrustManager.checkServerTrusted(chain, authType);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return mSystemDefaultTrustManager.getAcceptedIssuers();
    }
}