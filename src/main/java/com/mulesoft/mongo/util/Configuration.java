package com.mulesoft.mongo.util;

public class Configuration {
    private String dataStoreName;
    private String dataStoreReplicas;
    private String dataStoreUsername;
    private String dataStorePassword;
    private long startupBreather;

    public Configuration() {
    }

    public long getStartupBreather() {
        return startupBreather;
    }

    public String getDataStoreName() {
        return dataStoreName;
    }

    public String getDataStoreReplicas() {
        return dataStoreReplicas;
    }

    public String getDataStoreUsername() {
        return dataStoreUsername;
    }

    public String getDataStorePassword() {
        return dataStorePassword;
    }

    public void setDataStoreName(String dataStoreName) {
        this.dataStoreName = dataStoreName;
    }

    public void setDataStoreReplicas(String dataStoreReplicas) {
        this.dataStoreReplicas = dataStoreReplicas;
    }

    public void setDataStoreUsername(String dataStoreUsername) {
        this.dataStoreUsername = dataStoreUsername;
    }

    public void setDataStorePassword(String dataStorePassword) {
        this.dataStorePassword = dataStorePassword;
    }

    public void setStartupBreather(long startupBreather) {
        this.startupBreather = startupBreather;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Configuration [dataStoreName=").append(dataStoreName).append(", dataStoreReplicas=")
                .append(dataStoreReplicas).append(", dataStoreUsername=").append(dataStoreUsername)
                .append(", dataStorePassword=").append(dataStorePassword).append(", startupBreather=")
                .append(startupBreather).append("]");
        return builder.toString();
    }
}