package com.topcoder.util.config.stresstests;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Test case that verifies thread safety for Configuration Manager.
 *
 * @author WishingBone
 * @version 2.1.5
 */
public class ConcurrencyTests extends TestCase {

    /** The number of threads to execute. */
    private static final int THREADS = 100;
    /** The number of iteration to execute. */
    private static final int ITERATION = 100;

    /**
     * Empty constructor.
     */
    public ConcurrencyTests() {
    }

    /**
     * Tests whether getInstance() will instantiate only a single instance.
     *
     * @throws Exception to JUnit.
     */
    public void testGetInstance() throws Exception {
        // Set the cached instance field to null so that it will be instantiated.
        Field field = ConfigManager.class.getDeclaredField("defaultConfigManager");
        field.setAccessible(true);
        field.set(null, null);
        // Create and start the threads.
        GetInstanceRunner[] runners = new GetInstanceRunner[THREADS];
        for (int i = 0; i < THREADS; ++i) {
            runners[i] = new GetInstanceRunner();
            runners[i].start();
        }
        for (int i = 0; i < THREADS; ++i) {
            runners[i].join();
        }
        // Verify only a single instance has been instantiated.
        for (int i = 1; i < THREADS; ++i) {
            assertEquals("Mutliple ConfigManager instances created.",
                    runners[0].getConfigManager(), runners[i].getConfigManager());
        }
    }

    /**
     * The thread implementation to test getInstance().
     */
    private static class GetInstanceRunner extends Thread {
        /** The instance returned by getInstance(). */
        private ConfigManager cm = null;
        /** Empty constructor. */
        public GetInstanceRunner() {
        }
        /** Thread body, obtain the singleton instance. */
        public void run() {
            cm = ConfigManager.getInstance();
        }
        /** Get the obtained instance. */
        public ConfigManager getConfigManager() {
            return cm;
        }
    }

    /**
     * Tests all the config manager methods are thread safe.
     *
     * @throws Exception to JUnit.
     */
    public void testExecution() throws Exception {
        ExecutionRunner[] runners = new ExecutionRunner[THREADS];
        for (int i = 0; i < THREADS; ++i) {
            runners[i] = new ExecutionRunner("ns" + i);
            runners[i].setUp();
        }
        for (int i = 0; i < THREADS; ++i) {
            runners[i].start();
        }
        for (int i = 0; i < THREADS; ++i) {
            runners[i].join();
            runners[i].tearDown();
        }
    }

    /**
     * The thread implementation to test the methods.
     */
    private static class ExecutionRunner extends Thread {
        /** The config manager instance to use. */
        private ConfigManager cm = ConfigManager.getInstance();
        /** The namespace to work with. */
        private String namespace = null;
        /** The file to support the namespace. */
        private File file = null;
        /** Create the thread with specified namespace. */
        public ExecutionRunner(String namespace) {
            this.namespace = namespace;
        }
        /** Set up with thread.  Create a temporary properties file. */
        public void setUp() throws Exception {
            file = File.createTempFile("concurrency", ".properties", new File("test_files"));
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.println("a=a");
            writer.println("a.b=ab");
            writer.println("a.b.c=abc");
            writer.close();
        }
        /** Tear down the thread.  Remove the temp file. */
        public void tearDown() throws Exception {
            file.delete();
        }
        /** Thread body, execute various config manager methods. */
        public void run() {
            try {
                for (int i = 0; i < ITERATION; ++i) {
                    cm.add(namespace, file.getName(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
                    assertTrue(cm.existsNamespace(namespace));
                    cm.getAllNamespaces();
                    assertEquals(file.getAbsolutePath(), cm.getConfigFilename(namespace));
                    assertEquals(ConfigManager.CONFIG_PROPERTIES_FORMAT, cm.getConfigFormat(namespace));
                    assertEquals("abc", cm.getProperty(namespace, "a.b.c"));
                    cm.getPropertyNames(namespace);
                    assertNotNull(cm.getPropertyObject(namespace, "a.b"));
                    assertEquals("a", cm.getString(namespace, "a"));
                    assertEquals("a", cm.getStringArray(namespace, "a")[0]);
                    cm.createTemporaryProperties(namespace);
                    cm.addToProperty(namespace, "a.b.c.d", "abcd");
                    cm.setProperty(namespace, "a.b.c.d", "abcd");
                    cm.setProperty(namespace, "a.b.c.d", new String[] {"abcd", "abcd2"});
                    cm.removeValue(namespace, "a.b.c.d", "abcd2");
                    cm.removeProperty(namespace, "a.b.c.d");
                    cm.getTemporaryPropertyNames(namespace);
                    cm.getTemporaryPropertyObject(namespace, "a.b.c");
                    assertEquals("ab", cm.getTemporaryString(namespace, "a.b"));
                    assertEquals("a", cm.getTemporaryStringArray(namespace, "a")[0]);
                    cm.commit(namespace, "stress");
                    cm.removeNamespace(namespace);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
