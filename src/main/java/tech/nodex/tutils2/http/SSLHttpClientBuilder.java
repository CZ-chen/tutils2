package tech.nodex.tutils2.http;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by 陈朝(chenzhao@rongcapital.cn) on 2016-8-11.
 */
public class SSLHttpClientBuilder {
    public static OkHttpClient build(File keyStoreFile, String keyPwd){
        try {
            KeyStore clientStore = loadKeyStore(keyStoreFile,keyPwd);
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory
                    .getDefaultAlgorithm());
            kmf.init(clientStore,keyPwd.toCharArray());
            KeyManager[] kms = kmf.getKeyManagers();
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kms, null, new SecureRandom());

            return new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectionPool(new ConnectionPool(10, 60, TimeUnit.SECONDS))
                    .build();
        } catch (Exception e) {
            throw new IllegalArgumentException("Can not create SSLContext.",e);
        }
    }

    private static KeyStore loadKeyStore(File file,String pwd) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(file);
        keyStore.load(instream, pwd.toCharArray());
        instream.close();
        return keyStore;
    }
}
