package com.mulesoft.mongo.to.response;

import java.util.Set;

public class Index {
    private String name;
    private String collectionName;
    private String dbName;
    private boolean unique;
    private Set<String> keys;

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public Set<String> getKeys() {
        return keys;
    }

    public void setKeys(Set<String> keys) {
        this.keys = keys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Index [name=").append(name).append(", collectionName=").append(collectionName)
                .append(", dbName=").append(dbName).append(", unique=").append(unique).append(", keys=").append(keys)
                .append("]");
        return builder.toString();
    }
}
