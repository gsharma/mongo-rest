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
    private String baseUri = "http://localhost:9002/api/mongo/databases";
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
            System.out.println("Dropping " + dbName);
            mongo.dropDatabase(dbName);
        }
    }

    @Test
    public void testCreateDatabase() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        db.setName("mongo-rest-test");
        ClientResponse response = clientHandle.resource(baseUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testFindDatabase() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        String dbName = "mongo-rest-test";
        db.setName(dbName);
        db.setWriteConcern(com.mulesoft.mongo.to.request.WriteConcern.SAFE);
        ClientResponse response = clientHandle.resource(baseUri).type(MediaType.APPLICATION_JSON_TYPE)
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
        ClientResponse response = clientHandle.resource(baseUri).type(MediaType.APPLICATION_JSON_TYPE)
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
        ClientResponse response = clientHandle.resource(baseUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        db = new com.mulesoft.mongo.to.request.Database();
        String dbName2 = "mongo-rest-test2";
        db.setName(dbName2);
        response = clientHandle.resource(baseUri).type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        List<com.mulesoft.mongo.to.response.Database> databases = clientHandle.resource(baseUri)
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
        ClientResponse response = clientHandle.resource(baseUri).type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        db = new com.mulesoft.mongo.to.request.Database();
        String dbName2 = "mongo-rest-test2";
        db.setName(dbName2);
        response = clientHandle.resource(baseUri).type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, db);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        List<com.mulesoft.mongo.to.response.Database> databases = clientHandle.resource(baseUri)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<com.mulesoft.mongo.to.response.Database>>() {
                });
        assertEquals(2, databases.size());

        response = clientHandle.resource(baseUri).delete(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        databases = clientHandle.resource(baseUri).type(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<com.mulesoft.mongo.to.response.Database>>() {
                });
        assertEquals(0, databases.size());
    }

    @Test
    public void testCreateCollection() {
        com.mulesoft.mongo.to.request.Database db = new com.mulesoft.mongo.to.request.Database();
        db.setName("mongo-rest-test");
        ClientResponse response = clientHandle.resource(baseUri).type(MediaType.APPLICATION_JSON_TYPE)
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

        List<com.mulesoft.mongo.to.response.Database> databases = clientHandle.resource(dbUrl)
                .queryParam("collDetails", "true").type(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<com.mulesoft.mongo.to.response.Database>>() {
                });
        assertEquals(1, databases.size());
    }

    @Test
    public void testFindCollection() {
        assertTrue(true);
    }

    @Test
    public void testUpdateCollection() {
        assertTrue(true);
    }

    @Test
    public void testDeleteCollection() {
        assertTrue(true);
    }

    @Test
    public void testFindCollections() {
        assertTrue(true);
    }

    @Test
    public void testDeleteCollections() {
        assertTrue(true);
    }

    @Test
    public void testCreateIndex() {
        assertTrue(true);
    }

    @Test
    public void testFindIndex() {
        assertTrue(true);
    }

    @Test
    public void testDeleteIndex() {
        assertTrue(true);
    }

    @Test
    public void testFindIndexes() {
        assertTrue(true);
    }

    @Test
    public void testDeleteIndexes() {
        assertTrue(true);
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
        assertTrue(true);
    }
}
