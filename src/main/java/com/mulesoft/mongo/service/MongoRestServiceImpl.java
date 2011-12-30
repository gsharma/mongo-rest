package com.mulesoft.mongo.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mulesoft.mongo.exception.AuthenticationException;
import com.mulesoft.mongo.exception.AuthorizationException;
import com.mulesoft.mongo.security.Credentials;
import com.mulesoft.mongo.util.HttpStatusMapper;
import com.mulesoft.mongo.util.HttpStatusMapper.ClientError;
import com.mulesoft.mongo.util.HttpStatusMapper.ServerError;
import com.mulesoft.mongo.util.Utils;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/api/mongo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MongoRestServiceImpl implements MongoRestService {
    private Logger logger = LoggerFactory.getLogger(MongoRestServiceImpl.class);
    private Mongo mongo;

    @POST
    @Path("/databases")
    @Override
    public Response createDatabase(com.mulesoft.mongo.to.request.Database database, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        Response response = Response.status(ServerError.RUNTIME_ERROR.code())
                .entity(ServerError.RUNTIME_ERROR.message()).build();
        try {
            Credentials credentials = authenticateAndAuthorize(headers, uriInfo, securityContext);
            String dbName = constructDbNamespace(credentials.getUserName(), database.getName());
            if (mongo.getDatabaseNames().contains(dbName)) {
                return Response.status(HttpStatusMapper.ClientError.ALREADY_EXISTS.code()).build();
            }

            DB db = mongo.getDB(dbName);
            db.slaveOk();
            if (database.getWriteConcern() != null) {
                db.setWriteConcern(database.getWriteConcern().getMongoWriteConcern());
            }

            DBCollection userStats = db.getCollection("stats");
            userStats.ensureIndex("statsIndex");

            URI statusSubResource = uriInfo.getBaseUriBuilder().path(MongoRestServiceImpl.class)
                    .path("/databases/" + database.getName()).build();
            response = Response.created(statusSubResource).build();
        } catch (Exception exception) {
            response = lobException(exception, headers, uriInfo);
        }

        return response;
    }

    @PUT
    @Path("/databases/{dbName}")
    @Override
    public Response updateDatabase(com.mulesoft.mongo.to.request.Database database, @PathParam("dbName") String dbName,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        Response response = Response.status(ServerError.RUNTIME_ERROR.code())
                .entity(ServerError.RUNTIME_ERROR.message()).build();
        try {
            Credentials credentials = authenticateAndAuthorize(headers, uriInfo, securityContext);
            String dbNamespace = constructDbNamespace(credentials.getUserName(), dbName);
            boolean created = true;
            if (mongo.getDatabaseNames().contains(dbName)) {
                created = false;
            }
            DB db = mongo.getDB(dbNamespace);
            if (database.getWriteConcern() != null) {
                db.setWriteConcern(database.getWriteConcern().getMongoWriteConcern());
            }

            URI statusSubResource = uriInfo.getBaseUriBuilder().path(MongoRestServiceImpl.class)
                    .path("/databases/" + dbName).build();
            if (created) {
                db.slaveOk();
                DBCollection userStats = db.getCollection("stats");
                userStats.ensureIndex("statsIndex");
                response = Response.created(statusSubResource).build();
            } else {
                response = Response.ok(statusSubResource).build();
            }
        } catch (Exception exception) {
            response = lobException(exception, headers, uriInfo);
        }

        return response;
    }

    @DELETE
    @Path("/databases/{dbName}")
    @Override
    public Response deleteDatabase(@PathParam("dbName") String dbName, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        Response response = Response.status(ServerError.RUNTIME_ERROR.code())
                .entity(ServerError.RUNTIME_ERROR.message()).build();
        try {
            Credentials credentials = authenticateAndAuthorize(headers, uriInfo, securityContext);
            String dbNamespace = constructDbNamespace(credentials.getUserName(), dbName);
            if (mongo.getDatabaseNames().contains(dbNamespace)) {
                mongo.dropDatabase(dbNamespace);
                response = Response.ok().build();
            } else {
                response = Response.status(ClientError.NOT_FOUND.code()).build();
            }
        } catch (Exception exception) {
            response = lobException(exception, headers, uriInfo);
        }

        return response;
    }

    @GET
    @Path("/databases/{dbName}")
    @Override
    public Response findDatabase(@PathParam("dbName") String dbName,
            @QueryParam("collDetails") @DefaultValue("false") boolean collDetails, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        Response response = Response.status(ServerError.RUNTIME_ERROR.code())
                .entity(ServerError.RUNTIME_ERROR.message()).build();
        try {
            Credentials credentials = authenticateAndAuthorize(headers, uriInfo, securityContext);
            String dbNamespace = constructDbNamespace(credentials.getUserName(), dbName);
            if (mongo.getDatabaseNames().contains(dbNamespace)) {
                DB db = mongo.getDB(dbNamespace);
                com.mulesoft.mongo.to.response.Database database = new com.mulesoft.mongo.to.response.Database();
                database.setName(dbName);
                database.setWriteConcern(com.mulesoft.mongo.to.response.WriteConcern.fromMongoWriteConcern(db
                        .getWriteConcern()));
                if (collDetails) {
                    List<com.mulesoft.mongo.to.response.Collection> collections = new ArrayList<com.mulesoft.mongo.to.response.Collection>();
                    for (String collName : db.getCollectionNames()) {
                        DBCollection dbCollection = db.getCollection(collName);
                        com.mulesoft.mongo.to.response.Collection collection = new com.mulesoft.mongo.to.response.Collection();
                        collection.setDbName(dbName);
                        collection.setName(collName);
                        collection.setWriteConcern(com.mulesoft.mongo.to.response.WriteConcern
                                .fromMongoWriteConcern(dbCollection.getWriteConcern()));
                        List<DBObject> indexInfos = dbCollection.getIndexInfo();
                        List<com.mulesoft.mongo.to.response.Index> indexes = new ArrayList<com.mulesoft.mongo.to.response.Index>();
                        for (DBObject indexInfo : indexInfos) {
                            com.mulesoft.mongo.to.response.Index index = new com.mulesoft.mongo.to.response.Index();
                            index.setDbName(dbName);
                            index.setCollectionName(collName);
                            index.setKeys(indexInfo.keySet());
                            indexes.add(index);
                        }
                        collection.setIndexes(indexes);
                        dbCollection.getCount();// TODO
                        collections.add(collection);
                    }
                    database.setCollections(collections);
                }
                response = Response.ok(database).build();
            } else {
                response = Response.status(ClientError.NOT_FOUND.code()).build();
            }
        } catch (Exception exception) {
            response = lobException(exception, headers, uriInfo);
        }

        return response;
    }

    @GET
    @Path("/databases")
    @Override
    public Response findDatabases(@QueryParam("collDetails") @DefaultValue("false") boolean collDetails,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        Response response = Response.status(ServerError.RUNTIME_ERROR.code())
                .entity(ServerError.RUNTIME_ERROR.message()).build();
        try {
            Credentials credentials = authenticateAndAuthorize(headers, uriInfo, securityContext);
            List<com.mulesoft.mongo.to.response.Database> databases = new ArrayList<com.mulesoft.mongo.to.response.Database>();
            String dbNamePrefix = "dbs:" + credentials.getUserName();
            for (String dbName : mongo.getDatabaseNames()) {
                if (dbName.startsWith(dbNamePrefix)) {
                    DB db = mongo.getDB(dbName);
                    com.mulesoft.mongo.to.response.Database database = new com.mulesoft.mongo.to.response.Database();
                    database.setName(dbName.substring(dbNamePrefix.length() + 1));
                    database.setWriteConcern(com.mulesoft.mongo.to.response.WriteConcern.fromMongoWriteConcern(db
                            .getWriteConcern()));
                    if (collDetails) {
                        // TODO
                    }
                    databases.add(database);
                }
            }
            response = Response.ok(databases).build();
        } catch (Exception exception) {
            response = lobException(exception, headers, uriInfo);
        }

        return response;
    }

    @DELETE
    @Path("/databases")
    @Override
    public Response deleteDatabases(@Context HttpHeaders headers, @Context UriInfo uriInfo,
            @Context SecurityContext securityContext) {
        Response response = Response.status(ServerError.RUNTIME_ERROR.code())
                .entity(ServerError.RUNTIME_ERROR.message()).build();
        try {
            Credentials credentials = authenticateAndAuthorize(headers, uriInfo, securityContext);
            List<String> databases = new ArrayList<String>();
            String dbNamePrefix = "dbs:" + credentials.getUserName();
            for (String dbName : mongo.getDatabaseNames()) {
                if (dbName.startsWith(dbNamePrefix)) {
                    mongo.dropDatabase(dbName);
                    databases.add(dbName.substring(dbNamePrefix.length() + 1));
                }
            }
            response = Response.ok("Deleted databases: " + databases).build();
        } catch (Exception exception) {
            response = lobException(exception, headers, uriInfo);
        }

        return response;
    }

    @POST
    @Path("/databases/{dbName}/collections")
    @Override
    public Response createCollection(@PathParam("dbName") String dbName,
            com.mulesoft.mongo.to.request.Collection collection, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        Response response = Response.status(ServerError.RUNTIME_ERROR.code())
                .entity(ServerError.RUNTIME_ERROR.message()).build();
        try {
            Credentials credentials = authenticateAndAuthorize(headers, uriInfo, securityContext);
            String dbNamespace = constructDbNamespace(credentials.getUserName(), dbName);
            if (mongo.getDatabaseNames().contains(dbNamespace)) {
                DB db = mongo.getDB(dbNamespace);
                DBCollection dbCollection = db.getCollection(collection.getName());
                if (collection.getWriteConcern() != null) {
                    dbCollection.setWriteConcern(collection.getWriteConcern().getMongoWriteConcern());
                }
                dbCollection.ensureIndex("defaultIndex");

                URI statusSubResource = uriInfo.getBaseUriBuilder().path(MongoRestServiceImpl.class)
                        .path("/databases/" + dbName + "/collections/" + collection.getName()).build();
                response = Response.created(statusSubResource).build();
            } else {
                response = Response.status(ClientError.NOT_FOUND.code()).entity(dbName + " does not exist").build();
            }
        } catch (Exception exception) {
            response = lobException(exception, headers, uriInfo);
        }

        return response;
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
            com.mulesoft.mongo.to.request.Collection collection, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
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
            com.mulesoft.mongo.to.request.Index index, @Context HttpHeaders headers, @Context UriInfo uriInfo,
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
            com.mulesoft.mongo.to.request.Document document, @Context HttpHeaders headers, @Context UriInfo uriInfo,
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
            @PathParam("docName") String docName, com.mulesoft.mongo.to.request.Document document,
            @Context HttpHeaders headers, @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
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

    /**
     * 1. Authenticate and Authorize (SAC) via SecurityService<br>
     * 2. Maintain cached Lease/Client on Server-side
     **/
    private Credentials authenticateAndAuthorize(HttpHeaders headers, UriInfo uriInfo, SecurityContext securityContext)
            throws AuthenticationException, AuthorizationException {
        if (logger.isDebugEnabled()) {
            logRequestContext(headers, uriInfo);
        }
        String username = null, password = null;
        List<String> requestHeader = headers.getRequestHeader("authorization");
        try {
            for (String authHeader : requestHeader) {
                String[] pieces = authHeader.split(" ");
                if ("basic".equalsIgnoreCase(pieces[0].trim())) {
                    String[] userPasswd = Utils.EncodingUtils.decodeBase64(pieces[1].trim()).split(":");
                    username = userPasswd[0].trim().toLowerCase();
                    password = userPasswd[1].trim();
                    break;
                }
            }
        } catch (Exception exception) {
            throw new AuthenticationException(
                    "iON Alerts Service expects Base64 encoded Authorization header containing username and password",
                    exception);
        }
        if (Utils.StringUtils.isNullOrEmpty(username) || Utils.StringUtils.isNullOrEmpty(password)) {
            throw new AuthenticationException(
                    "iON Alerts Service expects Base64 encoded Authorization header containing username and password");
        }

        String userIP = null;
        requestHeader = headers.getRequestHeader("x-real-ip");
        if (requestHeader != null && !requestHeader.isEmpty()) {
            userIP = requestHeader.get(0);
        }

        String userAgent = null;
        requestHeader = headers.getRequestHeader("user-agent");
        if (requestHeader != null && !requestHeader.isEmpty()) {
            userAgent = requestHeader.get(0);
        }

        return new Credentials(null, null, username, password, userIP, userAgent, uriInfo.getRequestUri().toString());
    }

    private void logError(String message, Throwable exception, HttpHeaders headers, UriInfo uriInfo) {
        logRequestContext(headers, uriInfo);
        logger.error(message, exception);
    }

    private void logRequestContext(HttpHeaders headers, UriInfo uriInfo) {
        MultivaluedMap<String, String> headerParams = headers.getRequestHeaders();
        StringBuffer buffer = new StringBuffer("Headers=");
        for (Entry<String, List<String>> header : headerParams.entrySet()) {
            buffer.append(header.getKey()).append("=").append(header.getValue()).append(" ");
        }
        buffer.append("uri=").append(uriInfo.getRequestUri());
        logger.debug(buffer.toString());
    }

    private Response lobException(Exception exception, HttpHeaders headers, UriInfo uriInfo) {
        Response response = null;
        if (exception instanceof AuthenticationException || exception instanceof AuthorizationException) {
            logError("alerts failed", exception, headers, uriInfo);
            response = Response.status(ClientError.UNAUTHORIZED.code()).entity(ClientError.UNAUTHORIZED.message())
                    .build();
        } else {
            logError("alerts failed", exception, headers, uriInfo);
            response = Response.status(ServerError.RUNTIME_ERROR.code()).entity(ServerError.RUNTIME_ERROR.message())
                    .build();
        }
        return response;
    }

    private String constructDbNamespace(String userName, String dbName) {
        return "dbs:" + userName + ":" + dbName;
    }

    @Required
    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }

    public Mongo getMongo() {
        return mongo;
    }
}