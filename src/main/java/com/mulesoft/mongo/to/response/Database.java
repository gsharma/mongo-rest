package com.mulesoft.mongo.to.response;

import java.util.List;
import java.util.Map;

public class Database {
    private String name;
    private WriteConcern writeConcern;
    private List<Collection> collections;
    private Map<String, String> stats;
    private String locationUri;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WriteConcern getWriteConcern() {
        return writeConcern;
    }

    public void setWriteConcern(WriteConcern writeConcern) {
        this.writeConcern = writeConcern;
    }

    public List<Collection> getCollections() {
        return collections;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }

    public Map<String, String> getStats() {
        return stats;
    }

    public void setStats(Map<String, String> stats) {
        this.stats = stats;
    }

    public String getLocationUri() {
        return locationUri;
    }

    public void setLocationUri(String locationUri) {
        this.locationUri = locationUri;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Database [name=").append(name).append(", writeConcern=").append(writeConcern)
                .append(", collections=").append(collections).append(", stats=").append(stats).append(", locationUri=")
                .append(locationUri).append("]");
        return builder.toString();
    }
}
