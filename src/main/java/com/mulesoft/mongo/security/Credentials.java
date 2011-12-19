package com.mulesoft.mongo.security;

/**
 * An immutable basic security credentials data object.
 */
public final class Credentials {
    private final String domain;
    private final String applicationId;
    private final String userName;
    private final String userPassword;
    private final String userIP;
    private final String userAgent;
    private final String requestedURL;

    public Credentials(String domain, String applicationId, String userName, String userPassword, String userIP,
            String userAgent, String requestedURL) {
        this.domain = domain;
        this.applicationId = applicationId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userIP = userIP;
        this.userAgent = userAgent;
        this.requestedURL = requestedURL;
    }

    public String getDomain() {
        return domain;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserIP() {
        return userIP;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getRequestedURL() {
        return requestedURL;
    }

    @Override
    public String toString() {
        return "Credentials: domain=" + domain + ", applicationId=" + applicationId + ", userName=" + userName
                + ", userIP=" + userIP + ", userAgent=" + userAgent + " requestedURL=" + requestedURL;
    }
}
