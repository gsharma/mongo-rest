package com.mulesoft.mongo.service;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mongodb.Mongo;
import com.mulesoft.mongo.util.AppContext;
import com.mulesoft.mongo.util.ResourceClientFactory;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/META-INF/wiring-local.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class MongoRestServiceTest {
    private Server server;
    private static final String testUsername = "admin";
    private static final String testPassword = "r3$tfuLM0ng0";
    private static final String baseUri = "http://localhost:9002/api/mongo";
    private static final String baseDbUri = baseUri + "/databases";
    protected Client clientHandle;

    @BeforeClass
    public static void useTestOverrides() {
        System.setProperty("db.name", "test");
        System.setProperty("db.user", "");
        System.setProperty("db.password", "");
        System.setProperty("datastore.replicas", "");
        System.setProperty("domain", "localhost:9002");
    }

    @Before
    public void setUp() throws Exception {
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setWar(getWebappDirectory().getAbsolutePath());

        server = new Server(9002);
        server.setHandler(context);
        server.setStopAtShutdown(true);
        server.start();
        WebApplicationContextUtils.getWebApplicationContext(context.getServletContext());

        clientHandle = ResourceClientFactory.getClientHandle(testUsername, testPassword, 0, 0);
    }

    // Adjust this if needed
    private File getWebappDirectory() {
        File file = new File("./src/main/webapp/");
        if (!file.exists()) {
            file = new File("~/mongo-rest/src/main/webapp");
        }
        return file;
    }

    @After
    public void tearDown() throws Exception {
        try {
            if (server != null) {
                server.stop();
                server.destroy();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        Mongo mongo = (Mongo) AppContext.getApplicationContext().getBean("mongo");
        for (String dbName : mongo.getDatabaseNames()) {
            mongo.dropDatabase(dbName);
        }
    }

    @Test
    public void testCreateDatabase() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        db.setName("mongo-rest-test");
        ClientResponse response = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testFindDatabase() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        String dbName = "mongo-rest-test";
        db.setName(dbName);
        db.setWriteConcern(com.mulesoft.mongo.to.request.WriteConcern.SAFE);
        ClientResponse response = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        response = clientHandle.resource(response.getLocation()).type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        com.mulesoft.mongo.to.response.Database database = response
                .getEntity(com.mulesoft.mongo.to.response.Database.class);
        assertNotNull(database);
        assertEquals(dbName, database.getName());
        assertEquals(com.mulesoft.mongo.to.response.WriteConcern.SAFE, database.getWriteConcern());
    }

    @Test
    public void testUpdateDatabase() {
        assertTrue(true);
    }

    @Test
    public void testDeleteDatabase() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        String dbName = "mongo-rest-test";
        db.setName(dbName);
        ClientResponse response = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        response = clientHandle.resource(response.getLocation()).type(MediaType.APPLICATION_JSON_TYPE)
                .delete(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testFindDatabases() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        String dbName1 = "mongo-rest-test1";
        db.setName(dbName1);
        ClientResponse response = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        db = new com.mulesoft.mongo.to.request.Database();
        String dbName2 = "mongo-rest-test2";
        db.setName(dbName2);
        response = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        List<com.mulesoft.mongo.to.response.Database> databases = clientHandle.resource(baseDbUri)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<com.mulesoft.mongo.to.response.Database>>() {
                });
        assertEquals(2, databases.size());

        List<String> names = new ArrayList<String>(2);
        names.add(dbName1);
        names.add(dbName2);
        for (com.mulesoft.mongo.to.response.Database database : databases) {
            assertTrue(names.contains(database.getName()));
            assertEquals(com.mulesoft.mongo.to.response.WriteConcern.SAFE, database.getWriteConcern());
        }
    }

    @Test
    public void testDeleteDatabases() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        String dbName1 = "mongo-rest-test1";
        db.setName(dbName1);
        ClientResponse response = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        db = new com.mulesoft.mongo.to.request.Database();
        String dbName2 = "mongo-rest-test2";
        db.setName(dbName2);
        response = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        List<com.mulesoft.mongo.to.response.Database> databases = clientHandle.resource(baseDbUri)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<com.mulesoft.mongo.to.response.Database>>() {
                });
        assertEquals(2, databases.size());

        response = clientHandle.resource(baseDbUri).delete(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        databases = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<com.mulesoft.mongo.to.response.Database>>() {
                });
        assertEquals(0, databases.size());
    }

    @Test
    public void testCreateCollection() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        db.setName("mongo-rest-test");
        ClientResponse response = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI dbUrl = response.getLocation();
        com.mulesoft.mongo.to.request.Collection collection = new com.mulesoft.mongo.to.request.Collection();
        collection.setName("mongo-collection-1");
        response = clientHandle.resource(dbUrl + "/collections").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, collection);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        collection = new com.mulesoft.mongo.to.request.Collection();
        collection.setName("mongo-collection-2");
        response = clientHandle.resource(dbUrl + "/collections").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, collection);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        com.mulesoft.mongo.to.response.Database database = clientHandle.resource(dbUrl)
                .queryParam("collDetails", "true").type(MediaType.APPLICATION_JSON_TYPE)
                .get(com.mulesoft.mongo.to.response.Database.class);
        assertEquals(2, database.getCollections().size());

        for (com.mulesoft.mongo.to.response.Collection coll : database.getCollections()) {
            assertEquals(1, coll.getIndexes().size());
            assertEquals(0, coll.getDocuments().size());
        }
    }

    @Test
    public void testFindCollection() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        String dbName = "mongo-rest-test";
        db.setName(dbName);
        ClientResponse response = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI dbUrl = response.getLocation();
        com.mulesoft.mongo.to.request.Collection collection = new com.mulesoft.mongo.to.request.Collection();
        String collName = "mongo-test-collection";
        collection.setName(collName);
        response = clientHandle.resource(dbUrl + "/collections").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, collection);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        com.mulesoft.mongo.to.response.Collection foundCollection = clientHandle.resource(response.getLocation())
                .type(MediaType.APPLICATION_JSON_TYPE).get(com.mulesoft.mongo.to.response.Collection.class);
        assertEquals(1, foundCollection.getIndexes().size());
        assertEquals(0, foundCollection.getDocuments().size());
        assertEquals(collName, foundCollection.getName());
        assertEquals(dbName, foundCollection.getDbName());
    }

    @Test
    public void testUpdateCollection() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        String dbName = "mongo-rest-test";
        db.setName(dbName);
        ClientResponse response = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI dbUrl = response.getLocation();
        com.mulesoft.mongo.to.request.Collection collection = new com.mulesoft.mongo.to.request.Collection();
        String collName = "mongo-test-collection";
        collection.setName(collName);
        response = clientHandle.resource(dbUrl + "/collections").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, collection);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI collUrl = response.getLocation();
        com.mulesoft.mongo.to.response.Collection foundCollection = clientHandle.resource(collUrl)
                .type(MediaType.APPLICATION_JSON_TYPE).get(com.mulesoft.mongo.to.response.Collection.class);
        assertEquals(1, foundCollection.getIndexes().size());
        assertEquals(0, foundCollection.getDocuments().size());
        assertEquals(collName, foundCollection.getName());
        assertEquals(dbName, foundCollection.getDbName());

        collection.setWriteConcern(com.mulesoft.mongo.to.request.WriteConcern.FSYNC_SAFE);
        response = clientHandle.resource(collUrl).type(MediaType.APPLICATION_JSON_TYPE)
                .put(ClientResponse.class, collection);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        foundCollection = clientHandle.resource(collUrl).type(MediaType.APPLICATION_JSON_TYPE)
                .get(com.mulesoft.mongo.to.response.Collection.class);
        assertEquals(1, foundCollection.getIndexes().size());
        assertEquals(0, foundCollection.getDocuments().size());
        assertEquals(collName, foundCollection.getName());
        assertEquals(dbName, foundCollection.getDbName());
        assertEquals(com.mulesoft.mongo.to.response.WriteConcern.FSYNC_SAFE, foundCollection.getWriteConcern());
    }

    @Test
    public void testDeleteCollection() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        String dbName = "mongo-rest-test";
        db.setName(dbName);
        ClientResponse response = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI dbUrl = response.getLocation();
        com.mulesoft.mongo.to.request.Collection collection = new com.mulesoft.mongo.to.request.Collection();
        String collName = "mongo-test-collection";
        collection.setName(collName);
        response = clientHandle.resource(dbUrl + "/collections").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, collection);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI collUrl = response.getLocation();
        response = clientHandle.resource(collUrl).type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        response = clientHandle.resource(collUrl).delete(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        response = clientHandle.resource(collUrl).type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testFindCollections() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        String dbName = "mongo-rest-test";
        db.setName(dbName);
        ClientResponse response = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI dbUrl = response.getLocation();
        com.mulesoft.mongo.to.request.Collection collection = new com.mulesoft.mongo.to.request.Collection();
        String collName = "mongo-test-collection-1";
        collection.setName(collName);
        response = clientHandle.resource(dbUrl + "/collections").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, collection);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI collUrl1 = response.getLocation();
        response = clientHandle.resource(collUrl1).type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        collection = new com.mulesoft.mongo.to.request.Collection();
        collName = "mongo-test-collection-2";
        collection.setName(collName);
        response = clientHandle.resource(dbUrl + "/collections").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, collection);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI collUrl2 = response.getLocation();
        response = clientHandle.resource(collUrl2).type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        response = clientHandle.resource(dbUrl + "/collections").get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDeleteCollections() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        String dbName = "mongo-rest-test";
        db.setName(dbName);
        ClientResponse response = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI dbUrl = response.getLocation();
        com.mulesoft.mongo.to.request.Collection collection = new com.mulesoft.mongo.to.request.Collection();
        String collName = "mongo-test-collection-1";
        collection.setName(collName);
        response = clientHandle.resource(dbUrl + "/collections").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, collection);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI collUrl1 = response.getLocation();
        response = clientHandle.resource(collUrl1).type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        collection = new com.mulesoft.mongo.to.request.Collection();
        collName = "mongo-test-collection-2";
        collection.setName(collName);
        response = clientHandle.resource(dbUrl + "/collections").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, collection);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI collUrl2 = response.getLocation();
        response = clientHandle.resource(collUrl2).type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        response = clientHandle.resource(dbUrl + "/collections").delete(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        response = clientHandle.resource(collUrl1).type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
        response = clientHandle.resource(collUrl2).type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testCreateIndex() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        String dbName = "mongo-rest-test";
        db.setName(dbName);
        ClientResponse response = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI dbUrl = response.getLocation();
        com.mulesoft.mongo.to.request.Collection collection = new com.mulesoft.mongo.to.request.Collection();
        String collName = "mongo-test-collection";
        collection.setName(collName);
        response = clientHandle.resource(dbUrl + "/collections").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, collection);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI collUrl = response.getLocation();
        response = clientHandle.resource(collUrl).type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        com.mulesoft.mongo.to.request.Index index = new com.mulesoft.mongo.to.request.Index();
        index.setName("simple-index");
        index.setUnique(true);
        List<String> keys = new ArrayList<String>();
        keys.add("name");
        keys.add("age");
        index.setKeys(keys);
        response = clientHandle.resource(collUrl + "/indexes").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, index);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testFindIndex() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        String dbName = "mongo-rest-test";
        db.setName(dbName);
        ClientResponse response = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI dbUrl = response.getLocation();
        com.mulesoft.mongo.to.request.Collection collection = new com.mulesoft.mongo.to.request.Collection();
        String collName = "mongo-test-collection";
        collection.setName(collName);
        response = clientHandle.resource(dbUrl + "/collections").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, collection);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI collUrl = response.getLocation();
        response = clientHandle.resource(collUrl).type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        com.mulesoft.mongo.to.request.Index index1 = new com.mulesoft.mongo.to.request.Index();
        index1.setName("stats-index");
        index1.setUnique(true);
        List<String> keys = new ArrayList<String>();
        keys.add("name");
        keys.add("age");
        keys.add("height");
        index1.setKeys(keys);
        response = clientHandle.resource(collUrl + "/indexes").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, index1);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
        URI index1Url = response.getLocation();

        response = clientHandle.resource(index1Url).type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        com.mulesoft.mongo.to.response.Index foundIndex = response
                .getEntity(com.mulesoft.mongo.to.response.Index.class);
        assertNotNull(foundIndex);
        assertEquals(index1.getName(), foundIndex.getName());
        for (String key : index1.getKeys()) {
            assertTrue(foundIndex.getKeys().contains(key));
        }
        assertEquals(index1.isUnique(), foundIndex.isUnique());
    }

    @Test
    public void testDeleteIndex() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        String dbName = "mongo-rest-test";
        db.setName(dbName);
        ClientResponse response = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI dbUrl = response.getLocation();
        com.mulesoft.mongo.to.request.Collection collection = new com.mulesoft.mongo.to.request.Collection();
        String collName = "mongo-test-collection";
        collection.setName(collName);
        response = clientHandle.resource(dbUrl + "/collections").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, collection);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI collUrl = response.getLocation();
        response = clientHandle.resource(collUrl).type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        com.mulesoft.mongo.to.request.Index index = new com.mulesoft.mongo.to.request.Index();
        index.setName("stats-index");
        index.setUnique(true);
        List<String> keys = new ArrayList<String>();
        keys.add("name");
        keys.add("age");
        keys.add("height");
        index.setKeys(keys);
        response = clientHandle.resource(collUrl + "/indexes").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, index);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
        URI indexUrl = response.getLocation();

        response = clientHandle.resource(indexUrl).type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        response = clientHandle.resource(indexUrl).delete(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        response = clientHandle.resource(indexUrl).type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testFindIndexes() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        String dbName = "mongo-rest-test";
        db.setName(dbName);
        ClientResponse response = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI dbUrl = response.getLocation();
        com.mulesoft.mongo.to.request.Collection collection = new com.mulesoft.mongo.to.request.Collection();
        String collName = "mongo-test-collection";
        collection.setName(collName);
        response = clientHandle.resource(dbUrl + "/collections").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, collection);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI collUrl = response.getLocation();
        response = clientHandle.resource(collUrl).type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        com.mulesoft.mongo.to.request.Index index1 = new com.mulesoft.mongo.to.request.Index();
        index1.setName("stats-index");
        index1.setUnique(true);
        List<String> keys = new ArrayList<String>();
        keys.add("name");
        keys.add("age");
        keys.add("height");
        index1.setKeys(keys);
        response = clientHandle.resource(collUrl + "/indexes").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, index1);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        com.mulesoft.mongo.to.request.Index index2 = new com.mulesoft.mongo.to.request.Index();
        index2.setName("location-index");
        index2.setUnique(true);
        keys = new ArrayList<String>();
        keys.add("address");
        keys.add("phone");
        index2.setKeys(keys);
        response = clientHandle.resource(collUrl + "/indexes").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, index2);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        List<com.mulesoft.mongo.to.request.Index> indexes = new ArrayList<com.mulesoft.mongo.to.request.Index>(2);
        indexes.add(index1);
        indexes.add(index2);

        response = clientHandle.resource(collUrl + "/indexes").type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        List<com.mulesoft.mongo.to.response.Index> foundIndexes = response
                .getEntity(new GenericType<List<com.mulesoft.mongo.to.response.Index>>() {
                });
        assertEquals(2, foundIndexes.size());
        for (com.mulesoft.mongo.to.response.Index foundIndex : foundIndexes) {
            for (com.mulesoft.mongo.to.request.Index index : indexes) {
                if (index.getName().equals(foundIndex.getName())) {
                    for (String key : index.getKeys()) {
                        assertTrue(foundIndex.getKeys().contains(key));
                    }
                    assertEquals(index.isUnique(), foundIndex.isUnique());
                    assertEquals(dbName, foundIndex.getDbName());
                    assertEquals(collName, foundIndex.getCollectionName());
                }
            }
        }
    }

    @Test
    public void testDeleteIndexes() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        String dbName = "mongo-rest-test";
        db.setName(dbName);
        ClientResponse response = clientHandle.resource(baseDbUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI dbUrl = response.getLocation();
        com.mulesoft.mongo.to.request.Collection collection = new com.mulesoft.mongo.to.request.Collection();
        String collName = "mongo-test-collection";
        collection.setName(collName);
        response = clientHandle.resource(dbUrl + "/collections").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, collection);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        URI collUrl = response.getLocation();
        response = clientHandle.resource(collUrl).type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        com.mulesoft.mongo.to.request.Index index = new com.mulesoft.mongo.to.request.Index();
        index.setName("stats-index");
        index.setUnique(true);
        List<String> keys = new ArrayList<String>();
        keys.add("name");
        keys.add("age");
        keys.add("height");
        index.setKeys(keys);
        response = clientHandle.resource(collUrl + "/indexes").type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, index);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
        URI indexUrl = response.getLocation();

        response = clientHandle.resource(indexUrl).type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        response = clientHandle.resource(collUrl + "/indexes").delete(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        response = clientHandle.resource(indexUrl).type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
        response = clientHandle.resource(collUrl + "/indexes").type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        List<com.mulesoft.mongo.to.response.Index> foundIndexes = response
                .getEntity(new GenericType<List<com.mulesoft.mongo.to.response.Index>>() {
                });
        assertEquals(0, foundIndexes.size());
    }

    @Test
    public void testCreateDocument() {
        assertTrue(true);
    }

    @Test
    public void testFindDocument() {
        assertTrue(true);
    }

    @Test
    public void testUpdateDocument() {
        assertTrue(true);
    }

    @Test
    public void testDeleteDocument() {
        assertTrue(true);
    }

    @Test
    public void testFindDocuments() {
        assertTrue(true);
    }

    @Test
    public void testDeleteDocuments() {
        assertTrue(true);
    }

    @Test
    public void testPing() {
        ClientResponse response = clientHandle.resource(baseUri + "/ping").type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testShutdown() {
        ClientResponse response = clientHandle.resource(baseUri + "/ping").type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        response = clientHandle.resource(baseUri + "/shutdown").type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        response = clientHandle.resource(baseUri + "/ping").type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        assertEquals(Status.SERVICE_UNAVAILABLE.getStatusCode(), response.getStatus());
    }
}
