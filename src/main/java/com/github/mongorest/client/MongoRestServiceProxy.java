package com.github.mongorest.client;

import java.util.List;

public interface MongoRestServiceProxy {

    public String createDatabase(com.github.mongorest.to.request.Database database);

    public com.github.mongorest.to.response.Database findDatabase(String dbName);

    public com.github.mongorest.to.response.Database updateDatabase(com.github.mongorest.to.request.Database database);

    public boolean deleteDatabase(String dbName);

    public List<com.github.mongorest.to.response.Database> findDatabases();

    public boolean deleteDatabases();

    public String createCollection(String dbName, com.github.mongorest.to.request.Collection collection);

    public com.github.mongorest.to.response.Collection findCollection(String dbName, String collName);

    public com.github.mongorest.to.response.Collection updateCollection(String dbName,
            com.github.mongorest.to.request.Collection collection);

    public boolean deleteCollection(String dbName, String collName);

    public List<com.github.mongorest.to.response.Collection> findCollections(String dbName);

    public boolean deleteCollections(String dbName);

    public String createIndex(String dbName, String collName, com.github.mongorest.to.request.Index index);

    public com.github.mongorest.to.response.Index findIndex(String dbName, String collName, String indexName);

    public boolean deleteIndex(String dbName, String collName, String indexName);

    public List<com.github.mongorest.to.response.Index> findIndexes(String dbName, String collName);

    public boolean deleteIndexes(String dbName, String collName);

    public String createDocument(String dbName, String collName, com.github.mongorest.to.request.Document document);

    public com.github.mongorest.to.response.Document findDocument(String dbName, String collName, String documentName);

    public com.github.mongorest.to.response.Document updateDocument(String dbName, String collName,
            com.github.mongorest.to.request.Document document);

    public boolean deleteDocument(String dbName, String collName, String documentName);

    public List<com.github.mongorest.to.response.Document> findDocuments(String dbName, String collName);

    public boolean deleteDocuments(String dbName, String collName);

    public boolean ping();

    public boolean shutdown();

    public void setServiceUser(String serviceUser);

    public void setServicePassword(String servicePassword);

    public void setServiceUri(String serviceUri);

}