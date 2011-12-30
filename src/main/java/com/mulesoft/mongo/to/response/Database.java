package com.mulesoft.mongo.to.response;

import java.util.List;

public class Database {
    private String name;
    private WriteConcern writeConcern;
    private List<Collection> collections;

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Database [name=").append(name).append(", writeConcern=").append(writeConcern)
                .append(", collections=").append(collections).append("]");
        return builder.toString();
    }
}
