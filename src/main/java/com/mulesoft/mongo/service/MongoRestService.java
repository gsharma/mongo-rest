package com.mulesoft.mongo.service;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import com.mulesoft.mongo.to.request.Collection;
import com.mulesoft.mongo.to.request.Database;
import com.mulesoft.mongo.to.request.Document;
import com.mulesoft.mongo.to.request.Index;

public interface MongoRestService {

    // POST /databases
    public Response createDatabase(Database database, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // GET /databases/<dbName>
    public Response findDatabase(@PathParam("dbName") String dbName,
            @QueryParam("collDetails") @DefaultValue("false") boolean collDetails, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // PUT /databases/<dbName>
    public Response updateDatabase(Database database, @PathParam("dbName") String dbName, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // DELETE /databases/<dbName>
    public Response deleteDatabase(@PathParam("dbName") String dbName, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // GET /databases
    public Response findDatabases(@QueryParam("collDetails") @DefaultValue("false") boolean collDetails,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // DELETE /databases
    public Response deleteDatabases(@Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // POST /databases/<dbName>/collections
    public Response createCollection(@PathParam("dbName") String dbName, Collection collection,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // GET /databases/<dbName>/collections/<collName>
    public Response findCollection(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // PUT /databases/<dbName>/collections/<collName>
    public Response updateCollection(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            Collection collection, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // DELETE /databases/<dbName>/collections/<collName>
    public Response deleteCollection(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // GET /databases/<dbName>/collections
    public Response findCollections(@PathParam("dbName") String dbName, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // DELETE /databases/<dbName>/collections
    public Response deleteCollections(@PathParam("dbName") String dbName, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // POST /databases/<dbName>/collections/<collName>/indexes
    public Response createIndex(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            Index index, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // GET /databases/<dbName>/collections/<collName>/indexes/<index>
    public Response findIndex(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @PathParam("index") String index, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // DELETE /databases/<dbName>/collections/<collName>/indexes/<index>
    public Response deleteIndex(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @PathParam("index") String index, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // GET /databases/<dbName>/collections/<collName>/indexes
    public Response findIndexes(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // DELETE /databases/<dbName>/collections/<collName>/indexes
    public Response deleteIndexes(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // POST /databases/<dbName>/collections/<collName>/documents
    public Response createDocument(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            Document document, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // GET /databases/<dbName>/collections/<collName>/documents/<docName>
    public Response findDocument(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @PathParam("docName") String docName, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // PUT /databases/<dbName>/collections/<collName>/documents/<docName>
    public Response updateDocument(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @PathParam("docName") String docName, Document document, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // DELETE /databases/<dbName>/collections/<collName>/documents/<docName>
    public Response deleteDocument(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @PathParam("docName") String docName, @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);

    // GET /databases/<dbName>/collections/<collName>/documents
    public Response findDocuments(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // DELETE /databases/<dbName>/collections/<collName>/documents
    public Response deleteDocuments(@PathParam("dbName") String dbName, @PathParam("collName") String collName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext);

    // GET /ping
    public Response ping(@Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext);
}
