package com.huawei.agguard.demo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * 功能描述
 *
 * @author l00526060
 * @since 2021-01-24
 */

public class CertUtils {
    public static String getPublicKey(Context context, String packagename) throws PackageManager.NameNotFoundException, CertificateException {
        PackageInfo info = context.getPackageManager().getPackageInfo(packagename, PackageManager.GET_SIGNATURES);
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        ByteArrayInputStream stream = new ByteArrayInputStream(info.signatures[0].toByteArray());
        X509Certificate cert = (X509Certificate) certFactory.generateCertificate(stream);
        PublicKey publicKey = cert.getPublicKey();
        byte[] encoded = publicKey.getEncoded();
        return Base64.encodeToString(encoded, Base64.NO_WRAP);
    }
}
