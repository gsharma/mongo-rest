package com.mulesoft.mongo.util;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;

public final class ResourceClientFactory {
    private final static Logger logger = LoggerFactory.getLogger(ResourceClientFactory.class);

    private static Client clientHandle;// Cache and Reuse heavyweight handle
    private static final Object mutex = new Object();

    public static Client getClientHandle(String username, String password, int connectTimeoutMillis,
            int readTimeoutMillis) {
        if (clientHandle == null) { // Take a small hit on first invocation
            synchronized (mutex) {
                X509TrustManager trustManager = new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String string) throws CertificateException {
                        return;
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String string) throws CertificateException {
                        return;
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                };
                SSLContext sslContext;
                try {
                    sslContext = SSLContext.getInstance("SSL");
                } catch (NoSuchAlgorithmException nsae) {
                    String message = String.format("Unable to setup SSL: (%s) %s", nsae.getClass().getSimpleName(),
                            nsae.getMessage());
                    logger.error(message);
                    throw new RuntimeException(message, nsae);
                }

                try {
                    sslContext.init(null, new TrustManager[] { trustManager }, null);
                } catch (KeyManagementException kme) {
                    String message = String.format("Unable to configure SSL: (%s) %s", kme.getClass().getSimpleName(),
                            kme.getMessage());
                    logger.error(message);
                    throw new RuntimeException(message, kme);
                }

                ClientConfig config = new DefaultClientConfig();
                config.getClasses().add(JacksonJsonProvider.class);
                config.getSingletons().add(new JacksonJsonProvider(new ObjectMapper()));
                config.getSingletons().add(new BaseExceptionMapper());
                config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES,
                        new HTTPSProperties(new HostnameVerifier() {
                            @Override
                            public boolean verify(String s, SSLSession sslSession) {
                                return true;
                            }
                        }, sslContext));

                clientHandle = new Client(new URLConnectionClientHandler(), config);
                clientHandle.addFilter(new HTTPBasicAuthFilter(username, password));
                clientHandle.addFilter(new LoggingFilter());
                if (connectTimeoutMillis > 0) {
                    clientHandle.setConnectTimeout(connectTimeoutMillis);
                }
                if (readTimeoutMillis > 0) {
                    clientHandle.setReadTimeout(readTimeoutMillis);
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("Initialized client handle: " + clientHandle.getProperties());
                }
            }
        }
        return clientHandle;
    }

    public static void shutdown() {
        if (clientHandle != null) {
            // destroy and release
            clientHandle.destroy();
            clientHandle = null;
            if (logger.isDebugEnabled()) {
                logger.debug("Successfully shutdown client handle");
            }
        }
    }

    // Prevent instance escaping
    private ResourceClientFactory() {
    }
}
