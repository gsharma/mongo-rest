package com.mulesoft.mongo.service;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mulesoft.mongo.util.ResourceClientFactory;
import com.sun.jersey.api.client.Client;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/META-INF/wiring-local.xml" })
public class MongoRestServiceTest {
    private Server server;
    private static final String testUsername = "admin";
    private static final String testPassword = "admin";
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
        // TODO: purge mongo
    }

    @Test
    public void testCreateDatabase() {
        assertTrue(true);
    }

    public void testFindDatabase() {
        assertTrue(true);
    }

    @Test
    public void testUpdateDatabase() {
        assertTrue(true);
    }

    @Test
    public void testDeleteDatabase() {
        assertTrue(true);
    }

    @Test
    public void testFindDatabases() {
        assertTrue(true);
    }

    @Test
    public void testDeleteDatabases() {
        assertTrue(true);
    }

    @Test
    public void testCreateCollection() {
        assertTrue(true);
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
