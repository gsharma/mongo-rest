package com.mulesoft.mongo.client;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;

public class MongoRestServiceProxyImpl implements MongoRestServiceProxy {
    private Logger logger = LoggerFactory.getLogger(MongoRestServiceProxyImpl.class);

    // Note: injectable using Spring
    private String serviceUser = "admin";
    private String servicePassword = "r3$tfuLM0ng0";
    private String serviceUri = "http://localhost:9002/api/mongo";

    @Override
    public com.mulesoft.mongo.to.response.Database createDatabase(com.mulesoft.mongo.to.request.Database database) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return null; // TODO
    }

    @Override
    public com.mulesoft.mongo.to.response.Database findDatabase(String dbName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName;
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return null; // TODO
    }

    @Override
    public com.mulesoft.mongo.to.response.Database updateDatabase(com.mulesoft.mongo.to.request.Database database) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + database.getName();
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return null; // TODO
    }

    @Override
    public boolean deleteDatabase(String dbName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName;
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return false; // TODO
    }

    @Override
    public List<com.mulesoft.mongo.to.response.Database> findDatabases() {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return null; // TODO
    }

    @Override
    public boolean deleteDatabases() {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return false; // TODO
    }

    @Override
    public String createCollection(String dbName, com.mulesoft.mongo.to.request.Collection collection) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return null; // TODO
    }

    @Override
    public com.mulesoft.mongo.to.response.Collection findCollection(String dbName, String collName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName;
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return null; // TODO
    }

    @Override
    public com.mulesoft.mongo.to.response.Collection updateCollection(String dbName,
            com.mulesoft.mongo.to.request.Collection collection) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collection.getName();
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return null; // TODO
    }

    @Override
    public boolean deleteCollection(String dbName, String collName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName;
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return false; // TODO
    }

    @Override
    public List<com.mulesoft.mongo.to.response.Collection> findCollections(String dbName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return null; // TODO
    }

    @Override
    public boolean deleteCollections(String dbName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return false; // TODO
    }

    @Override
    public String createIndex(String dbName, String collName, com.mulesoft.mongo.to.request.Index index) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/indexes";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return null; // TODO
    }

    @Override
    public com.mulesoft.mongo.to.response.Index findIndex(String dbName, String collName, String indexName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/indexes/" + indexName;
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return null; // TODO
    }

    @Override
    public boolean deleteIndex(String dbName, String collName, String indexName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/indexes/" + indexName;
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return false; // TODO
    }

    @Override
    public List<com.mulesoft.mongo.to.response.Index> findIndexes(String dbName, String collName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/indexes";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return null; // TODO
    }

    @Override
    public boolean deleteIndexes(String dbName, String collName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/indexes";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return false; // TODO
    }

    @Override
    public String createDocument(String dbName, String collName, com.mulesoft.mongo.to.request.Document document) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/documents";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return null; // TODO
    }

    @Override
    public com.mulesoft.mongo.to.response.Document findDocument(String dbName, String collName, String documentName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/documents/" + documentName;
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return null; // TODO
    }

    @Override
    public com.mulesoft.mongo.to.response.Document updateDocument(String dbName, String collName,
            com.mulesoft.mongo.to.request.Document document) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/documents/"
                + document.getName();
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return null; // TODO
    }

    @Override
    public boolean deleteDocument(String dbName, String collName, String documentName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/documents/" + documentName;
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return false; // TODO
    }

    @Override
    public List<com.mulesoft.mongo.to.response.Document> findDocuments(String dbName, String collName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/documents";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return null; // TODO
    }

    @Override
    public boolean deleteDocuments(String dbName, String collName) {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/databases/" + dbName + "/collections/" + collName + "/documents";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        response.getStatus();
        return false; // TODO
    }

    @Override
    public boolean ping() {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/ping";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        return response.getStatus() == Status.OK.getStatusCode();
    }

    @Override
    public boolean shutdown() {
        Client clientHandle = MongoServiceConnection.getClientHandle(serviceUser, servicePassword, 0, 0);
        String uri = serviceUri + "/shutdown";
        ClientResponse response = clientHandle.resource(uri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        return response.getStatus() == Status.OK.getStatusCode();
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
