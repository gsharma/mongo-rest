package com.mulesoft.mongo.to.request;

public class Database {
    private String name;
    private WriteConcern writeConcern;

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Database [name=").append(name).append(", writeConcern=").append(writeConcern).append("]");
        return builder.toString();
    }
}
