package com.mulesoft.mongo.service;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

public interface MongoRestService {

    // POST /databases
    public Response createDatabase(Object database, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // GET /databases/<db>
    public Response findDatabase(@PathParam("dbName") String dbName, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // PUT /databases/<db>
    public Response updateDatabase(Object database, @PathParam("dbName") String dbName, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // DELETE /databases/<db>
    public Response deleteDatabase(@PathParam("dbName") String dbName, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // GET /databases
    public Response findDatabases(@Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // DELETE /databases
    public Response deleteDatabases(@Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // POST /databases/<db>/collections
    public Response createCollection(@PathParam("dbName") String dbName, Object collection,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // GET /databases/<db>/collections/<coll>
    public Response findCollection(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // PUT /databases/<db>/collections/<coll>
    public Response updateCollection(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            Object collection, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // DELETE /databases/<db>/collections/<coll>
    public Response deleteCollection(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // GET /databases/<db>/collections
    public Response findCollections(@PathParam("dbName") String dbName, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // DELETE /databases/<db>/collections
    public Response deleteCollections(@PathParam("dbName") String dbName, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // POST /databases/<db>/collections/<coll>/indexes
    public Response createIndex(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            Object index, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // GET /databases/<db>/collections/<coll>/indexes/<index>
    public Response findIndex(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @PathParam("index") String index, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // DELETE /databases/<db>/collections/<coll>/indexes/<index>
    public Response deleteIndex(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @PathParam("index") String index, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // GET /databases/<db>/collections/<coll>/indexes
    public Response findIndexes(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // DELETE /databases/<db>/collections/<coll>/indexes
    public Response deleteIndexes(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // POST /databases/<db>/collections/<coll>/documents
    public Response createDocument(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            Object document, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // GET /databases/<db>/collections/<coll>/documents/<docName>
    public Response findDocument(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @PathParam("docName") String docName, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // PUT /databases/<db>/collections/<coll>/documents/<docName>
    public Response updateDocument(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @PathParam("docName") String docName, Object document, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // DELETE /databases/<db>/collections/<coll>/documents/<docName>
    public Response deleteDocument(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @PathParam("docName") String docName, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // GET /databases/<db>/collections/<coll>/documents
    public Response findDocuments(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // DELETE /databases/<db>/collections/<coll>/documents
    public Response deleteDocuments(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // GET /ping
    public Response ping(@Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);
}
