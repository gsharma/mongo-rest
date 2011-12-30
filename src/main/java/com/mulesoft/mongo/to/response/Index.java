package com.mulesoft.mongo.to.response;

import java.util.Set;

public class Index {
    private String collectionName;
    private String dbName;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Index [collectionName=").append(collectionName).append(", dbName=").append(dbName)
                .append(", keys=").append(keys).append("]");
        return builder.toString();
    }

}
