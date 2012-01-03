package com.mulesoft.mongo.client;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.ClientResponse.Status;

public class MongoRestServiceProxyImpl implements MongoRestServiceProxy {
    private Logger logger = LoggerFactory.getLogger(MongoRestServiceProxyImpl.class);

    // Note: inject-able using Spring
    private String serviceUri = "http://localhost:9002/api/mongo";
    private String serviceUser = "admin";
    private String servicePassword = "r3$tfuLM0ng0";

    @Override
    public String createDatabase(com.mulesoft.mongo.to.request.Database database) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, database);
        String location = null;
        if (response.getStatus() == Status.CREATED.getStatusCode()) {
            location = response.getLocation().toString();
        } else {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return location;
    }

    @Override
    public com.mulesoft.mongo.to.response.Database findDatabase(String dbName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName;
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        com.mulesoft.mongo.to.response.Database database = null;
        if (response.getStatus() == Status.OK.getStatusCode()) {
            database = response.getEntity(com.mulesoft.mongo.to.response.Database.class);
        } else {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return database;
    }

    @Override
    public com.mulesoft.mongo.to.response.Database updateDatabase(com.mulesoft.mongo.to.request.Database database) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + database.getName();
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .put(ClientResponse.class, database);
        com.mulesoft.mongo.to.response.Database updatedDatabase = null;
        if (response.getStatus() == Status.OK.getStatusCode() || response.getStatus() == Status.CREATED.getStatusCode()) {
            updatedDatabase = response.getEntity(com.mulesoft.mongo.to.response.Database.class);
        } else {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return updatedDatabase;
    }

    @Override
    public boolean deleteDatabase(String dbName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName;
        ClientResponse response = clientHandle.resource(uri).delete(ClientResponse.class);
        boolean deleted = response.getStatus() == Status.OK.getStatusCode();
        if (!deleted) {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return deleted;
    }

    @Override
    public List<com.mulesoft.mongo.to.response.Database> findDatabases() {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases";
        return clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<com.mulesoft.mongo.to.response.Database>>() {
                });
    }

    @Override
    public boolean deleteDatabases() {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases";
        ClientResponse response = clientHandle.resource(uri).delete(ClientResponse.class);
        boolean deleted = response.getStatus() == Status.OK.getStatusCode();
        if (!deleted) {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return deleted;
    }

    @Override
    public String createCollection(String dbName, com.mulesoft.mongo.to.request.Collection collection) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, collection);
        String location = null;
        if (response.getStatus() == Status.CREATED.getStatusCode()) {
            location = response.getLocation().toString();
        } else {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return location;
    }

    @Override
    public com.mulesoft.mongo.to.response.Collection findCollection(String dbName, String collName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName;
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        com.mulesoft.mongo.to.response.Collection collection = null;
        if (response.getStatus() == Status.OK.getStatusCode()) {
            collection = response.getEntity(com.mulesoft.mongo.to.response.Collection.class);
        } else {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return collection;
    }

    @Override
    public com.mulesoft.mongo.to.response.Collection updateCollection(String dbName,
            com.mulesoft.mongo.to.request.Collection collection) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collection.getName();
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .put(ClientResponse.class, collection);
        com.mulesoft.mongo.to.response.Collection updatedCollection = null;
        if (response.getStatus() == Status.OK.getStatusCode() || response.getStatus() == Status.CREATED.getStatusCode()) {
            updatedCollection = response.getEntity(com.mulesoft.mongo.to.response.Collection.class);
        } else {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return updatedCollection;
    }

    @Override
    public boolean deleteCollection(String dbName, String collName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName;
        ClientResponse response = clientHandle.resource(uri).delete(ClientResponse.class);
        boolean deleted = response.getStatus() == Status.OK.getStatusCode();
        if (!deleted) {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return deleted;
    }

    @Override
    public List<com.mulesoft.mongo.to.response.Collection> findCollections(String dbName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections";
        return clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<com.mulesoft.mongo.to.response.Collection>>() {
                });
    }

    @Override
    public boolean deleteCollections(String dbName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections";
        ClientResponse response = clientHandle.resource(uri).delete(ClientResponse.class);
        boolean deleted = response.getStatus() == Status.OK.getStatusCode();
        if (!deleted) {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return deleted;
    }

    @Override
    public String createIndex(String dbName, String collName, com.mulesoft.mongo.to.request.Index index) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/indexes";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, index);
        String location = null;
        if (response.getStatus() == Status.CREATED.getStatusCode()) {
            location = response.getLocation().toString();
        } else {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return location;
    }

    @Override
    public com.mulesoft.mongo.to.response.Index findIndex(String dbName, String collName, String indexName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/indexes/" + indexName;
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        com.mulesoft.mongo.to.response.Index index = null;
        if (response.getStatus() == Status.OK.getStatusCode()) {
            index = response.getEntity(com.mulesoft.mongo.to.response.Index.class);
        } else {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return index;
    }

    @Override
    public boolean deleteIndex(String dbName, String collName, String indexName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/indexes/" + indexName;
        ClientResponse response = clientHandle.resource(uri).delete(ClientResponse.class);
        boolean deleted = response.getStatus() == Status.OK.getStatusCode();
        if (!deleted) {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return deleted;
    }

    @Override
    public List<com.mulesoft.mongo.to.response.Index> findIndexes(String dbName, String collName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/indexes";
        return clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<com.mulesoft.mongo.to.response.Index>>() {
                });
    }

    @Override
    public boolean deleteIndexes(String dbName, String collName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/indexes";
        ClientResponse response = clientHandle.resource(uri).delete(ClientResponse.class);
        boolean deleted = response.getStatus() == Status.OK.getStatusCode();
        if (!deleted) {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return deleted;
    }

    @Override
    public String createDocument(String dbName, String collName, com.mulesoft.mongo.to.request.Document document) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/documents";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, document);
        String location = null;
        if (response.getStatus() == Status.CREATED.getStatusCode()) {
            location = response.getLocation().toString();
        } else {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return location;
    }

    @Override
    public com.mulesoft.mongo.to.response.Document findDocument(String dbName, String collName, String documentName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/documents/" + documentName;
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        com.mulesoft.mongo.to.response.Document document = null;
        if (response.getStatus() == Status.OK.getStatusCode()) {
            document = response.getEntity(com.mulesoft.mongo.to.response.Document.class);
        } else {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return document;
    }

    @Override
    public com.mulesoft.mongo.to.response.Document updateDocument(String dbName, String collName,
            com.mulesoft.mongo.to.request.Document document) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/documents/"
                + document.getName();
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .put(ClientResponse.class, document);
        com.mulesoft.mongo.to.response.Document updatedDocument = null;
        if (response.getStatus() == Status.OK.getStatusCode() || response.getStatus() == Status.CREATED.getStatusCode()) {
            updatedDocument = response.getEntity(com.mulesoft.mongo.to.response.Document.class);
        } else {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return updatedDocument;
    }

    @Override
    public boolean deleteDocument(String dbName, String collName, String documentName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/documents/" + documentName;
        ClientResponse response = clientHandle.resource(uri).delete(ClientResponse.class);
        boolean deleted = response.getStatus() == Status.OK.getStatusCode();
        if (!deleted) {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return deleted;
    }

    @Override
    public List<com.mulesoft.mongo.to.response.Document> findDocuments(String dbName, String collName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/documents";
        return clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<com.mulesoft.mongo.to.response.Document>>() {
                });
    }

    @Override
    public boolean deleteDocuments(String dbName, String collName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/documents";
        ClientResponse response = clientHandle.resource(uri).delete(ClientResponse.class);
        boolean deleted = response.getStatus() == Status.OK.getStatusCode();
        if (!deleted) {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return deleted;
    }

    @Override
    public boolean ping() {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/ping";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        boolean alive = response.getStatus() == Status.OK.getStatusCode();
        if (!alive) {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return alive;
    }

    @Override
    public boolean shutdown() {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/shutdown";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        boolean shutdown = response.getStatus() == Status.OK.getStatusCode();
        if (!shutdown) {
            logger.error("uri=" + uri + ", status=" + response.getStatus());
        }
        return shutdown;
    }

    @Override
    public void setServiceUser(String serviceUser) {
        this.serviceUser = serviceUser;
    }

    @Override
    public void setServicePassword(String servicePassword) {
        this.servicePassword = servicePassword;
    }

    @Override
    public void setServiceUri(String serviceUri) {
        this.serviceUri = serviceUri;
    }
}
