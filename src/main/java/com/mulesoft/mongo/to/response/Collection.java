package com.mulesoft.mongo.to.response;

import java.util.List;

public class Collection {
    private String name;
    private String dbName;
    private List<Document> documents;
    private List<Index> indexes;
    private WriteConcern writeConcern;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public List<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }

    public WriteConcern getWriteConcern() {
        return writeConcern;
    }

    public void setWriteConcern(WriteConcern writeConcern) {
        this.writeConcern = writeConcern;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Collection [name=").append(name).append(", dbName=").append(dbName).append(", documents=")
                .append(documents).append(", indexes=").append(indexes).append(", writeConcern=").append(writeConcern)
                .append("]");
        return builder.toString();
    }
}
