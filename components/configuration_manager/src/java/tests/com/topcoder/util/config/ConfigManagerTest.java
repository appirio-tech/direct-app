package com.topcoder.util.config;

import junit.framework.*;
import junit.extensions.*;
import java.io.*;
import java.util.*;


/**
 * This class is used to check the behavior of ConfigManager,
 * thus configuration files that it writes are well-formed.
 * To test the limits of config files that could be read is
 * a job for the Parser JUnit tests.
 */
public class ConfigManagerTest extends TestCase {

    private static String testDir ;
    private static ConfigManager cm;
    private ResourceBundle rb;
    private static Hashtable properties;
    private static File xmlConfig;
    private static File propertiesConfig;
    private boolean exceptionThrownOnLockTest = false;

    public ConfigManagerTest (String name) {
        super(name);
    }

    private static String createEntry(String key, String value, String type) {
    	if (type.equals(ConfigManager.CONFIG_XML_FORMAT)) {
    	    return
    		"<Property name=\"" +
    		key +
    		"\"><Value>" +
    		value +
    		"</Value>" + "</Property>";
    	}
    	if (type.equals(ConfigManager.CONFIG_PROPERTIES_FORMAT)) {
    	    return key + "=" + value;
    	}

	return "@@@@@@@@THIS SHOULD NOT HAPPEN@@@@@@@@@";

    }

    private static void change(File f, String type, Hashtable prop) {
    	try {
    	    PrintWriter out;

    	    out = new PrintWriter(new FileWriter(f));

            if(type.equals(ConfigManager.CONFIG_XML_FORMAT)) {
                out.println("<?xml version=\"1.0\"?>");
                out.println("<CMConfig version=\"v2\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"C:\\tcs\\Components\\Configuration Manager\\web\\doc\\CM v2 Schema.xsd\">");
            }

    	    Enumeration e = prop.keys();
    	    while (e.hasMoreElements()) {
                String key = (String)e.nextElement();
                String value = (String)prop.get(key);
                out.println (createEntry(key,value,type));
    	    }

            if(type.equals(ConfigManager.CONFIG_XML_FORMAT)) {
                out.println("</CMConfig>");
            }

    	    out.close();
    	} catch (Exception e) {
    	    throw new RuntimeException (e.getMessage());
    	}
    }

    private void verify (File f, Hashtable prop)
	throws ConfigManagerException {
        try {
            //ResourceBundle rb = cm.getResourceBundle(f.getName());
            Enumeration e = prop.keys();
            while (e.hasMoreElements()) {
                String key = (String)e.nextElement();
                String value = (String)prop.get(key);
                assertTrue(prop.get(key).equals(cm.getProperty(f.getName(), key)));
            }
        } catch(IOException e) {
            fail("IOException getting resource bundle");
        }
    }

    protected void setUp() {

    }

    /**
     * We assume tests are being run from Components/Configuration Manager
     * directory. The test
     */
    public static Test suite() {
    	try {

    	    testDir = "test_files/";

    	    System.err.println ("Test dir: " + testDir);
    	    cm = ConfigManager.getInstance();

    	    properties = new Hashtable();
    	    properties.put ("one", "smiling bag");
    	    properties.put ("two", "owls");

    	    xmlConfig = File.createTempFile ("tmp", ".xml",
    					     new File(testDir));
            // System.err.println ("Created " + xmlConfig.getPath());

    	    propertiesConfig = File.createTempFile ("tmp", ".properties",
    		    		    new File(testDir));
    		//propertiesConfig = new File("test_files/tmp.properties");

    	    //System.err.println ("Created " + propertiesConfig.getPath());

    	    xmlConfig.deleteOnExit();
    	    propertiesConfig.deleteOnExit();

    	    change(xmlConfig, ConfigManager.CONFIG_XML_FORMAT,properties);
    	    change(propertiesConfig, ConfigManager.CONFIG_PROPERTIES_FORMAT,properties);

    	} catch (Exception e) {
    	    System.out.println (e.getMessage());
    	    e.printStackTrace();
    	    throw new RuntimeException (e.getMessage());
    	}

    	TestSuite retval = new TestSuite();
    	// We need a certain order of execution,
    	// (not for all of the above, but for some).
    	// so we cannot just do it automatically.
    	// Because of this order, assume that

    	String [] testList =
    	    {"Initial",
    	     "GetNonExistentNamespace",
    	     "LoadProperties",
    	     "LoadIntoExistingNamespace",
    	     "AddUnknownType",
    	     "LoadXML",
             //"UpdateXML",
             //"UpdateProperties",
    	     "RefreshProperties",
    	     "RefreshXML",
    	     "RefreshAll",
    	     "LockA",
             "MultiProperties"
            };

    	for (int i=0;i<testList.length;i++){
    	    retval.addTest (new ConfigManagerTest("test"+testList[i]));
    	}
	return retval;
    }

    public void testInitial() {
	Assert.assertNotNull (cm);
    }

    public void testGetNonExistentNamespace() {
	try {
	    //rb  = cm.getResourceBundle ("XXXXXXXXXXXXXX");
        cm.getProperty("XXXXXXXXXXXXXX", "Bla");
	    fail ("Should throw UnknownNamespaceException");
	} catch (UnknownNamespaceException e) {

	} catch (Exception e) {
	    fail ("FAILURE: " + e.getMessage());
	}
    }

    public void testLoadProperties () {
	try {
	    //System.err.println ("Adding into " +
		//		propertiesConfig.getName());
	    cm.add(propertiesConfig.getName(),
		   testDir + propertiesConfig.getName(),
		   cm.CONFIG_PROPERTIES_FORMAT);

	    verify(propertiesConfig,
		   properties);
	}
	catch (Exception e) {
	    fail (e.getMessage());
	}
    }

    public void testLoadIntoExistingNamespace() {
	boolean success=false;
	try {
	    //System.err.println ("Adding into " +
		//		propertiesConfig.getName());
	    assertTrue (cm.existsNamespace(propertiesConfig.getName()));
	    cm.add (propertiesConfig.getName(),
		    testDir + propertiesConfig.getName(),
		    cm.CONFIG_PROPERTIES_FORMAT); // Dunno about this...
	    fail ("Should throw NamespaceAlreadyExistsException");
	} catch (NamespaceAlreadyExistsException e) {
	    success = true;
	} catch (Exception e) {
	    if (!success) fail (e.getMessage());
	}
    }

    public void testAddUnknownType() {
	try {
	    cm.add ("MALFORMED",
		    testDir + "test.malformed",
		    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	    fail ("Should throw UnknownConfigFormatException");

	} catch (UnknownConfigFormatException e) {

	} catch (Exception e) {
	    fail (e.getMessage());
	}
    }

    // Wishful thinking for now
    // This currently does not get tested as XMLConfigParser
    // parses a DTD implicit in the code
    public void testAddMalformedXML() {
	// Attempt to add a malformed file as an XML format
	try {
	    cm.add ("test2",
		    testDir + "test.malformed",
		    cm.CONFIG_XML_FORMAT);
	    fail ("Should throw ConfigParserException");

	} catch (ConfigParserException e) {

	} catch (Exception e) {
	    fail (e.getMessage());
	}
    }

    public void testLoadXML() {

	try {
	    cm.add (xmlConfig.getName(),
		    testDir + xmlConfig.getName(),
		    cm.CONFIG_XML_FORMAT);
	    verify(xmlConfig, properties);

	} catch (Exception e) {
	    fail (e.getMessage());
	}
    }

    public void testUpdateXML() {

        BufferedReader inNew = null;
        BufferedReader inOld = null;
        String temp = null;
        StringBuffer newString = new StringBuffer();
        StringBuffer oldString = new StringBuffer();
        StringTokenizer st = null;
        File newXML = null;

        properties.put("NewTest", "OldSkool");

        try {
            cm.createTemporaryProperties(xmlConfig.getName());
            cm.setProperty(xmlConfig.getName(), "NewTest", "OldSkool");
            cm.commit(xmlConfig.getName(), "JUnit");
        } catch(Exception e) {
            e.printStackTrace();
            fail("Could not properly update xml information");
        }

        try {
            newXML = File.createTempFile ("tmp", ".cxml",
                                new File(testDir));
            change(newXML, ConfigManager.CONFIG_XML_FORMAT,properties);
            inNew = new BufferedReader(new FileReader(testDir + newXML.getName()));
            inOld = new BufferedReader(new FileReader(testDir + xmlConfig.getName()));
            while((temp = inNew.readLine()) != null) {
                newString.append(temp);
            }
            while((temp = inOld.readLine()) != null) {
                oldString.append(temp);
            }
            if(!newString.toString().equals(oldString.toString())) {
                fail("Difference in the new and old xml files");
            }
        } catch(Exception e) {
            e.printStackTrace();
            fail("Could not compare new and old XML files.");
        } finally {
            try {
                inNew.close();
                inOld.close();
            } catch(Exception e) {
                fail("Could not close files");
            }
            newXML.deleteOnExit();
        }
    }

    public void testUpdateProperties() {

        BufferedReader inNew = null;
        BufferedReader inOld = null;
        String temp = null;
        File newProp = null;
        StringBuffer newString = new StringBuffer();
        StringBuffer oldString = new StringBuffer();
        StringTokenizer st = null;

        try {
            cm.createTemporaryProperties(propertiesConfig.getName());
            cm.setProperty(propertiesConfig.getName(), "NewTest", "OldSkool");
            cm.commit(propertiesConfig.getName(), "JUnit");
        } catch(Exception e) {
            e.printStackTrace();
            fail("Could not update properties");
        }

        try {
            newProp = File.createTempFile("tmp", ".cproperties",
                                new File(testDir));
            change(newProp, ConfigManager.CONFIG_PROPERTIES_FORMAT, properties);
            inNew = new BufferedReader(new FileReader(testDir + newProp.getName()));
            inOld = new BufferedReader(new FileReader(testDir + propertiesConfig.getName()));
            while((temp = inNew.readLine()) != null) {
                if(temp.charAt(0) != '#') {
                    newString.append(temp);
                    newString.append('\n');
                }
            }
            while((temp = inOld.readLine()) != null) {
                if(temp.charAt(0) != '#') {
                    oldString.append(temp);
                    oldString.append('\n');
                }
            }
            if(!newString.toString().equals(oldString.toString())) {
                fail("Difference in strings");
            }
        } catch(Exception e) {
            fail("Could not compare new and old properties files.");
        } finally {
            try {
                inNew.close();
                inOld.close();
            } catch(Exception e) {
                fail("Could not close files");
            }
            newProp.deleteOnExit();
        }
    }

    private Hashtable newProperty() {
	Hashtable newprop = new Hashtable();
	Enumeration e = properties.keys();
	String newkey = "";
	while (e.hasMoreElements()) {
	    String key = (String)e.nextElement();
	    String value = (String)properties.get(key);
	    newprop.put(key,value+".NEWVALUE");
	    newkey+=key;
	}
	newprop.put(newkey+".NEWVALUE", "NEWKEY");
	properties = newprop;
	return newprop;
    }

    public void testRefreshProperties() {

	try {
	    Hashtable newprop = newProperty();
	    change(propertiesConfig,
		   ConfigManager.CONFIG_PROPERTIES_FORMAT,
		   newprop);
	    cm.refresh(propertiesConfig.getName());
	    verify(propertiesConfig,
		   newprop);
	} catch (Exception e) {
	    fail (e.getMessage());
	}
    }

    public void testRefreshXML() {

	try {
	    Hashtable newprop = newProperty();
	    change(xmlConfig,
		   ConfigManager.CONFIG_XML_FORMAT,
		   newprop);
	    cm.refresh(xmlConfig.getName());
	    verify(xmlConfig,
		   newprop);
	} catch (Exception e) {
	    fail (e.getMessage());
	}
    }


    public void testRefreshAll() {

	try {
	    Hashtable newprop = newProperty();
	    change(xmlConfig,
		   ConfigManager.CONFIG_XML_FORMAT,
		   newprop);
	    change(propertiesConfig,
		   ConfigManager.CONFIG_PROPERTIES_FORMAT,
		   newprop);
	    cm.refreshAll();
	    verify(xmlConfig,
		   newprop);
	    verify(propertiesConfig,
		   newprop);
	} catch (Exception e) {
	    fail (e.getMessage());
	}
    }


    /**
     * Class used for testing locks in ConfigManager.java.  If two or more are
     * created, one of them should throw an Exception.
     *
     * @author Aaron Drew
     * @version 1.0
     */
    class LockTestThread extends Thread {
        String namespace;
        String property;

        /**
         * Prevents a LockTestThread from being created with no data.
         */
        private LockTestThread() {}

        /**
         * Creates a new LockTestThread.
         * @param namespace the namespace which this thread should access.
         * @param property the property which this thread should access.
         */
        public LockTestThread(String namespace, String property) {
            this.namespace = namespace;
            this.property = property;
        }

        /**
         * Main method for threads.  Run when start() is called.  Updates the
         * property in namespace a few times, and if other threads sharing
         * the same namespace and property exist it should throw an Exception.
         * If no Exceptions (of multiple access attempt types) are thrown a
         * failure occurs.
         */
        public void run() {
            try {
                //for(int i = 0; i < 10; i++) {
                    //System.err.println(i);
                    //String data = "" + i;
                    //cm.createTemporaryProperties(namespace);
                    //cm.setProperty(namespace, property, data);
                    cm.commit(namespace, "JUnit");
                //}
            } catch(Exception e) /*TODO change to proper exception type*/ {
                e.printStackTrace();
                exceptionThrownOnLockTest = true;
            }
        }
    }

    /**
     * Tests the locks which should be set in the ConfigManager.java when
     * two threads attempt to update a property simultaneously.
     */
    public void testLockA() {
        String namespace = "testNamespace1.properties";
        String property = "testProperty";
        exceptionThrownOnLockTest = false;
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileOutputStream(namespace));
            out.println(properties + "=\"" + 0 + "\"");
            out.close();
            cm.add(namespace, namespace, ConfigManager.CONFIG_PROPERTIES_FORMAT);
            /*
            Thread one = new LockTestThread(namespace, property);
            Thread two = new LockTestThread(namespace, property);
            */

            try {
                cm.createTemporaryProperties(namespace);
                cm.lock(namespace, "Me");
                cm.commit(namespace, "You");

                fail("Lock was not encountered");
            } catch(Exception e) {
                if(!(e instanceof ConfigLockedException)) {
                    e.printStackTrace();
                    fail(e.getMessage());
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            fail("Failed for unknown reason in testLock.  See command prompt.");
        } finally {
            try {
                out.close();
            } catch(Exception e) {
            }
            File f = new File(namespace);
            f.deleteOnExit();

            try {
                cm.refreshAll();
            } catch(Exception e) {
            }
        }
    }

    /**
     * Tests getProperties method to verify correct return value.
     */
    public void testMultiProperties() {
        String namespace = "testNamespace2.properties";
        String property = "testProperty";

        try {
            PrintWriter f = new PrintWriter(new FileOutputStream(namespace));
            f.println(property + "=AB");
            f.close();
            cm.add(namespace, namespace, ConfigManager.CONFIG_PROPERTIES_FORMAT);
            cm.createTemporaryProperties(namespace);
            cm.addToProperty(namespace, property, "CD");
            cm.commit(namespace, "JUnit");

            assertEquals("AB;CD", cm.getString(namespace, property));
        } catch(Exception e) {
            e.printStackTrace();
            fail("Failed for unknown reason in MultiProperties.  " +
                 "See command prompt.");
        } finally {
            File f = new File(namespace);
            f.deleteOnExit();
            //while(!f.delete()) {} // Keep trying till it works.
            f.deleteOnExit();
            try {
                cm.refreshAll();
            } catch(Exception e) {}
        }
    }

    /**
     * Tests multiple configurations within one XML file.
     */
    public void testMultiNamespaces() {
        try {
            cm.add("test_files/SampleMultipleConfig.XML");
            assertEquals("Value", cm.getProperty("Component A", "Prop1"));
            assertEquals("Value1;Value2", cm.getProperty("Component A", "Prop2"));
            assertEquals("Value1;Value2", cm.getProperty("Component B", "Prop1"));
            assertEquals("Value", cm.getProperty("Component B", "Prop2"));
        } catch(Exception e) {
            e.printStackTrace();
            fail("Unexpected Exception in testMultiNamespaces");
        }
    }

    /**
     * Tests writing XML files with multiple properties to disks.
     * @depends testMultiNamespaces
     */
    public void testMultiNamespaceWrite() {
        StringBuffer buffer = new StringBuffer();
        StringTokenizer st = null;
        String temp;
        BufferedReader in = null;

        try {
            cm.createTemporaryProperties("Component A");
            cm.setProperty("Component A", "Prop1", "Value1");
        } catch(Exception e) {
            e.printStackTrace();
            fail("Unexpected error in setting property");
        }
        try {
            cm.commit("Component A", "JUnit");

            in = new BufferedReader(new FileReader("test_files/SampleMultipleConfig.XML"));
            while((temp = in.readLine()) != null) {
                buffer.append(temp);
            }
            st = new StringTokenizer(buffer.toString(), "\n \t");
            buffer = new StringBuffer();
            while(st.hasMoreTokens()) {
                buffer.append(st.nextToken());
            }

            cm.createTemporaryProperties("Component A");
            cm.setProperty("Component A", "Prop1", "Value");
            cm.commit("Component A", "JUnit");
            cm.refreshAll();
            assertEquals("<?xmlversion=\"1.0\"?><CMConfigversion=\"v2\"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"xsi:noNamespaceSchemaLocation=\"C:\\tcs\\Components\\ConfigurationManager\\web\\doc\\CMv2Schema.xsd\"><Configname=\"ComponentA\"><Propertyname=\"Prop1\"><Value>Value1</Value></Property><Propertyname=\"Prop2\"><Value>Value1</Value><Value>Value2</Value></Property></Config><Configname=\"ComponentB\"><ListDelimiter>%</ListDelimiter><Propertyname=\"Prop1\"><Value>Value1</Value><Value>Value2</Value></Property><Propertyname=\"Prop2\"><Value>Value</Value></Property></Config></CMConfig>", buffer.toString());
            //fail("Able to commit multiple namespace xml");
        } catch(Exception e) {
            e.printStackTrace();
            fail("Error on writing multiple namespaces");
            try {
                cm.refreshAll();
            } catch(Exception ee) {
                fail("Could not refresh ConfigManager");
            }
        } finally {
            try {
                in.close();
            } catch(Exception e) {
            }
        }
    }

    /**
     * Class used for running timed tests on ConfigManager.java.
     *
     * @author Aaron Drew
     * @version 1.0
     */
    class LookupThread extends Thread {

        String []namespaces;
        String []properties;
        int toDo;
        Random generator;
        int i;

        /**
         * Prevents a LookupThread with no namespaces or properties from being
         * created.
         */
        private LookupThread() {}

        /**
         * Creates a new LookupThread.
         *
         * @param names The list of namespaces currently in the ConfigManager.
         * @param props The list of properties every namespace has.
         * @param num How many random properties to look up.
         * @param i The id of this thread.  Useful for debugging only.
         */
        public LookupThread(String []names, String []props, int num, int i) {
            this.namespaces = names;
            this.properties = props;
            this.toDo = num;
            this.i = i;

            generator = new Random(System.currentTimeMillis());
        }

        /**
         * Main method for threads.  Run when start is called.  Should not
         * be called directly.  At the end of this method, toDo will be 0
         * as long as no Exceptions occur.
         */
        public void run() {
            if(namespaces.length == 0) {
                fail("namespaces is too short.");
                return;
            }
            try {
                // Loop toDo times.
                for(; toDo > 0; toDo--) {
                    // Get a random Property from the ConfigManager.
                    cm.getProperty(namespaces[i],
                                   properties[generator.nextInt(
                                              properties.length)]);
                }
            } catch(Exception e) {
                e.printStackTrace();
                fail("Could not get a valid property!");
            }
        }
    }

    /**
     * Method to test the speed of looking up properties from the Configuration
     * Manager.  Creates a set of namespaces and properties which all of the
     * namespaces have.  Uses an array of LookupThreads to access the
     * Configuration Manager.  Changing the system clock during this method
     * will probably cause inaccurate results.
     */
    public void testSpeed() {
        int numThreads = 5; // Sets the number of threads
        int numLookups = 10; // Sets the number of property lookups per thread.
        long startTime;
        long endTime;
        String []properties = new String[25];
        String []namespaces = new String[numThreads];
        Thread []threads = new Thread[numThreads];

        // Build the property names.
        for(int i = 0; i < properties.length; i++) {
            properties[i] = "" + i;
        }

        // Build the set of all namespaces, and add properties to them.
        for(int i = 0; i < namespaces.length; i++) {
            try {
                namespaces[i] = "tmpTest" + i + ".properties";
                PrintWriter f = new PrintWriter(
                        new FileOutputStream(namespaces[i]));
                for(int k = 0; k < properties.length; k++) {
//                    f.println(properties[k] + "=\"" + k + "\"");
                }
                f.close();
                cm.add(namespaces[i], namespaces[i],
                       ConfigManager.CONFIG_PROPERTIES_FORMAT);
                cm.createTemporaryProperties(namespaces[i]);
                for(int k = 0; k < properties.length; k++) {
                        cm.setProperty(namespaces[i],
                                properties[k], "" + k);
                }
            } catch(Exception e) {
                e.printStackTrace();
                fail("Error in creating original namespace");
            }
        }

        startTime = System.currentTimeMillis();

        for(int i = 0; i < numThreads; i++) {
            threads[i] =new LookupThread(namespaces, properties, numLookups, i);
        }

        for(int i = 0; i < numThreads; i++) {
            threads[i].start();
        }

        for(int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch(InterruptedException e) {
                fail("Could not join Thread");
            }
        }

        endTime = System.currentTimeMillis();

        System.out.println("With " + numThreads + " threads each performing " +
                numLookups + " lookups in the Configuration Manager, " +
                "the total time was " + (endTime-startTime) + " milliseconds.");

        // Delete all the files created for this test.
        for(int i = 0; i < namespaces.length; i++) {
            File f = new File(namespaces[i]);
            if(!f.delete()) {
                f.deleteOnExit();
            }
        }
    }

}
