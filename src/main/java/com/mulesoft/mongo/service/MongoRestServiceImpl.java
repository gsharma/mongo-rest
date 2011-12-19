package com.mulesoft.mongo.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Required;

import com.mongodb.Mongo;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MongoRestServiceImpl implements MongoRestService {
    private Mongo mongo;

    @POST
    @Path("/databases")
    @Override
    public Response createDatabase(Object database, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @PUT
    @Path("/databases/{dbName}")
    @Override
    public Response updateDatabase(Object database, @PathParam("dbName") String dbName, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/databases/{dbName}")
    @Override
    public Response deleteDatabase(@PathParam("dbName") String dbName, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @GET
    @Path("/databases/{dbName}")
    @Override
    public Response findDatabase(@PathParam("dbName") String dbName, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @GET
    @Path("/databases")
    @Override
    public Response findDatabases(@Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/databases")
    @Override
    public Response deleteDatabases(@Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @POST
    @Path("/databases/{dbName}/collections")
    @Override
    public Response createCollection(@PathParam("dbName") String dbName, Object collection,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @GET
    @Path("/databases/{dbName}/collections/{collName}")
    @Override
    public Response findCollection(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @PUT
    @Path("/databases/{dbName}/collections/{collName}")
    @Override
    public Response updateCollection(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            Object collection, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/databases/{dbName}/collections/{collName}")
    @Override
    public Response deleteCollection(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @GET
    @Path("/databases/{dbName}/collections")
    @Override
    public Response findCollections(@PathParam("dbName") String dbName, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/databases/{dbName}/collections")
    @Override
    public Response deleteCollections(@PathParam("dbName") String dbName, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @POST
    @Path("/databases/{dbName}/collections/{collName}")
    @Override
    public Response createIndex(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            Object index, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @GET
    @Path("/databases/{dbName}/collections/{collName}/indexes/{indexName}")
    @Override
    public Response findIndex(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @PathParam("indexName") String indexName, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/databases/{dbName}/collections/{collName}/indexes/{indexName}")
    @Override
    public Response deleteIndex(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @PathParam("indexName") String indexName, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @GET
    @Path("/databases/{dbName}/collections/{collName}/indexes")
    @Override
    public Response findIndexes(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/databases/{dbName}/collections/{collName}/indexes")
    @Override
    public Response deleteIndexes(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @POST
    @Path("/databases/{dbName}/collections/{collName}/documents")
    @Override
    public Response createDocument(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            Object document, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @GET
    @Path("/databases/{dbName}/collections/{collName}/documents/{docName}")
    @Override
    public Response findDocument(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @PathParam("docName") String docName, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @PUT
    @Path("/databases/{dbName}/collections/{collName}/documents/{docName}")
    @Override
    public Response updateDocument(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @PathParam("docName") String docName, Object document, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/databases/{dbName}/collections/{collName}/documents/{docName}")
    @Override
    public Response deleteDocument(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @PathParam("docName") String docName, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @GET
    @Path("/databases/{dbName}/collections/{collName}/documents")
    @Override
    public Response findDocuments(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/databases/{dbName}/collections/{collName}/documents")
    @Override
    public Response deleteDocuments(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @GET
    @Path("/ping")
    @Override
    public Response ping(@Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext) {
        return Response.ok().build();
    }

    @Required
    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }

    public Mongo getMongo() {
        return mongo;
    }
}
