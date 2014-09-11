/*
 * ConfigManagerStressTests.java
 * Copyright © 2004, TopCoder, Inc. All rights reserved
 */

package com.topcoder.util.config.stresstests;

import com.topcoder.util.config.*;
import junit.framework.TestCase;

import java.io.*;
import java.util.*;

/**
 * @author amitc
 *
 * This class stress tests the ConfigManager Component.
 *
 */
public class ConfigManagerStressTests extends TestCase {

	/* Number of Properties to be read/written during tests */
	private int NO_OF_PROPERTIES = 1000;

	/* Number of threads to be spawned during multi threaded test */
	private int NO_OF_THREADS = 50;

	/* Number of namespaces to created */
	private int NO_NAME_SPACES = 10;

	/* Config Manager and File objects */
	ConfigManager cm = null;
	ConfigManager cmThread = null;
	File testfile = null;
	File testwfile = null;
	File propfile = null;

	/* Setup routine for creating the the two test files */
	protected void setUp() throws Exception {
		testfile =
			File.createTempFile(
				"stress",
				".properties",
				new File("test_files/"));
		testwfile =
			File.createTempFile(
				"stress2",
				".properties",
				new File("test_files/"));
		propfile =
			File.createTempFile(
				"props",
				".properties",
				new File("test_files/"));
		PrintWriter writer = new PrintWriter(new FileWriter(testfile));

		int i, j;
		writer.println("# Reading Test");
		for (i = 0; i < NO_OF_PROPERTIES; i++) {
			writer.println("property" + i + "=" + i);
		}
		writer.close();

		writer = new PrintWriter(new FileWriter(testwfile));
		writer.println("# Writing Test");
		writer.close();

		writer = new PrintWriter(new FileWriter(propfile));
		writer.println("# Property Test");
		for (i = 1; i < NO_OF_PROPERTIES / 10; i++) {
			for (j = 0; j < i; j++) {
				writer.print("a" + j + (j != i - 1 ? "." : ""));
			}
			writer.println("=" + i);
		}
		writer.close();
	}

	/* Teardown routine for destroying the two test files */
	protected void tearDown() throws Exception {
		testfile.deleteOnExit();
		testwfile.deleteOnExit();
		propfile.deleteOnExit();
	}

	/* Stress tests nested properties */
	public void testConfigManagerNestedProperties() throws Exception {
		int i, j;

		cm = ConfigManager.getInstance();
		cm.add(
			"PropSpace",
			propfile.getAbsolutePath(),
			ConfigManager.CONFIG_PROPERTIES_FORMAT);
		assertTrue(cm.existsNamespace("PropSpace"));

		Property root = null;
		for (i = 1; i < NO_OF_PROPERTIES / 10; i++) {
			root = cm.getPropertyObject("PropSpace", "a0");
			for (j = 1; j < i; j++) {
				root = root.getProperty("a" + j);
			}
			assertTrue(Integer.parseInt(root.getValue()) == i);
		}
		cm.removeNamespace("PropSpace");
	}

	/* Stress tests multiple namespaces */
	public void testConfigManagerMultipleNamespaces() throws Exception {
		int i;

		cm = ConfigManager.getInstance();

		for (i = 0; i < NO_NAME_SPACES; i++) {
			cm.add(
				"TestSpace" + i,
				testfile.getAbsolutePath(),
				ConfigManager.CONFIG_PROPERTIES_FORMAT);
		}

		for (i = 0; i < NO_NAME_SPACES; i++) {
			assertTrue(cm.existsNamespace("TestSpace" + i));
		}

		for (i = 0; i < NO_NAME_SPACES; i++) {
			cm.removeNamespace("TestSpace" + i);
		}

		for (i = 0; i < NO_NAME_SPACES; i++) {
			assertFalse(cm.existsNamespace("TestSpace" + i));
		}
	}

	/* Stress tests the ConfigManager reading methods */
	public void testConfigManagerReading() throws Exception {
		int i;
		long time;

		cm = ConfigManager.getInstance();
		assertFalse(cm.existsNamespace("TestSpace"));
		cm.add(
			"TestSpace",
			testfile.getAbsolutePath(),
			ConfigManager.CONFIG_PROPERTIES_FORMAT);
		assertTrue(cm.existsNamespace("TestSpace"));

		time = System.currentTimeMillis();
		for (i = 0; i < NO_OF_PROPERTIES; i++) {
			String val = cm.getString("TestSpace", "property" + i);
			assertEquals(Integer.parseInt(val), i);
		}

		time = System.currentTimeMillis() - time;
		System.out.println(
			"Reading "
				+ NO_OF_PROPERTIES
				+ " properties using getString() took "
				+ time
				+ " ms");

		time = System.currentTimeMillis();
		for (i = 0; i < NO_OF_PROPERTIES; i++) {
			String val = (String) cm.getProperty("TestSpace", "property" + i);
			assertEquals(Integer.parseInt(val), i);
		}
		cm.removeNamespace("TestSpace");

		time = System.currentTimeMillis() - time;
		System.out.println(
			"Reading "
				+ NO_OF_PROPERTIES
				+ " properties using getProperty() took "
				+ time
				+ " ms");
	}

	/* Stress tests the ConfigManager read/write methods */
	public void testConfigManagerReadWrite() throws Exception {
		int i, j;
		long time;

		cm = ConfigManager.getInstance();
		assertFalse(cm.existsNamespace("TestWSpace"));
		cm.add(
			"TestWSpace",
			testwfile.getAbsolutePath(),
			ConfigManager.CONFIG_PROPERTIES_FORMAT);
		assertTrue(cm.existsNamespace("TestWSpace"));

		time = System.currentTimeMillis();
		cm.createTemporaryProperties("TestWSpace");
		for (i = 0; i < NO_OF_PROPERTIES; i++) {
			String[] StrArray = new String[NO_OF_PROPERTIES / 50];
			for (j = 0; j < NO_OF_PROPERTIES / 50; j++) {
				StrArray[j] = "" + (i + j);

			}
			cm.addToProperty("TestWSpace", "property" + i, "");
			cm.setProperty("TestWSpace", "property" + i, StrArray);
		}
		cm.commit("TestWSpace", "user");

		time = System.currentTimeMillis() - time;
		System.out.println(
			"Writing "
				+ NO_OF_PROPERTIES
				+ " multiple properties took "
				+ time
				+ " ms");

		Enumeration e = cm.getPropertyNames("TestWSpace");
		i = 0;
		while (e.hasMoreElements()) {
			String property = (String) e.nextElement();
			assertEquals(property, "property" + i);
			i++;
		}

		time = System.currentTimeMillis();
		cm.createTemporaryProperties("TestWSpace");
		for (i = 0; i < NO_OF_PROPERTIES; i++) {
			cm.setProperty("TestWSpace", "property" + i, "" + i);
		}

		cm.commit("TestWSpace", "user");
		time = System.currentTimeMillis() - time;
		System.out.println(
			"Updating "
				+ NO_OF_PROPERTIES
				+ " properties took "
				+ time
				+ " ms");

		time = System.currentTimeMillis();
		cm.createTemporaryProperties("TestWSpace");
		for (i = 0; i < NO_OF_PROPERTIES; i++) {
			String val = cm.getString("TestWSpace", "property" + i);
			assertEquals(Integer.parseInt(val), i);
			cm.removeProperty("TestWSpace", "property" + i);
			assertTrue(
				(cm.getTemporaryString("TestWSpace", "property" + i)) == null);
		}
		cm.commit("TestWSpace", "user");
		time = System.currentTimeMillis() - time;
		System.out.println(
			"Removing "
				+ NO_OF_PROPERTIES
				+ " properties took "
				+ time
				+ " ms");

		for (i = 0; i < NO_OF_PROPERTIES; i++) {
			String val = cm.getString("TestWSpace", "property" + i);
			assertEquals(val, null);
		}

		cm.removeNamespace("TestWSpace");
	}

	/**
	 * Performs multi threaded stress test by spawning NO_THREADS of CMTester()
	 */
	public void testConfigManagerMT() throws Exception {
		cmThread = ConfigManager.getInstance();
		cmThread.add(
			"TestSpace",
			testfile.getAbsolutePath(),
			ConfigManager.CONFIG_PROPERTIES_FORMAT);
		cmThread.add(
			"TestSpace2",
			testfile.getAbsolutePath(),
			ConfigManager.CONFIG_PROPERTIES_FORMAT);


		CMTester[] threads = new CMTester[NO_OF_THREADS];

		long time = System.currentTimeMillis();

		for (int i = 0; i < threads.length; ++i) {
			threads[i] = new CMTester();
		}
		for (int i = 0; i < threads.length; ++i) {
			threads[i].start();
			Thread.sleep(1);
		}
		for (int i = 0; i < threads.length; ++i) {
			threads[i].join();
//			assertTrue("Failed thread #" + i, threads[i].ranOkay());
		}
		System.out.println("All Threads Completed");

		time = System.currentTimeMillis() - time;
		System.out.println(
			"Multithreaded Test running "
				+ NO_OF_THREADS
				+ " took "
				+ time
				+ " ms");
		cmThread.removeNamespace("TestSpace");
		cmThread.removeNamespace("TestSpace2");
	}

	/**
	 * Thread that executes a single instance of CMTester.
	 */
	class CMTester extends Thread {

		/* Status variable for indicating successful execution */
		private boolean status = true;

		/* Method for verifying successful execution */
		public boolean ranOkay() {
			return status;
		}

		/* Primary thread method */
		/*
		   Tests for concurrent updates were removed based
		   on comments in developer forum regarding the
		   incomplete design specification in this regard
		 */
		public void run() {
			try {
				int r = (int) Math.random() * 6;

				/* Update random property in namespace TestSpace and commit the changes by 'user1' */
				/*if (r == 0) {
					assertTrue(cmThread.existsNamespace("TestSpace"));
					cmThread.createTemporaryProperties("TestSpace");
					cmThread.setProperty(
						"TestSpace",
						"property" + (int) (Math.random() * 50),
						"" + (int) (Math.random() * 50));
					sleep(1);
					while (cmThread.canLock("TestSpace", "user1") == false) {
						sleep(1);
					}
					cmThread.commit("TestSpace", "user1");
				}*/

				/* Update random property in namespace TestWSpace and commit the changes by 'user2' */
				/*if (r == 1) {
					assertTrue(cmThread.existsNamespace("TestWSpace"));
					cmThread.createTemporaryProperties("TestWSpace");
					cmThread.setProperty(
						"TestWSpace",
						"property" + (int) (Math.random() * 50),
						"" + (int) (Math.random() * 50));
					sleep(1);
					while (cmThread.canLock("TestWSpace", "user2") == false) {
						sleep(1);
					}
					cmThread.commit("TestWSpace", "user2");
				}*/

				/* Read random property from namespace TestSpace */
				if (r == 2) {
					assertTrue(cmThread.existsNamespace("TestSpace"));
					String val =
						cmThread.getString(
							"TestSpace",
							"property" + (int) (Math.random() * 50));
					assertTrue(val != null);
					sleep(1);
				}

				/* Update random property in namespace TestWSpace and commit the changes by 'user1' */
				/*if (r == 3) {
					assertTrue(cmThread.existsNamespace("TestWSpace"));
					cmThread.createTemporaryProperties("TestWSpace");
					cmThread.setProperty(
						"TestWSpace",
						"property" + (int) (Math.random() * 50),
						"" + (int) (Math.random() * 50));
					sleep(1);
					while (cmThread.canLock("TestWSpace", "user1") == false) {
						sleep(1);
					}
					cmThread.commit("TestWSpace", "user1");
				}*/

				/* Update random property in namespace TestSpace and commit the changes by 'user2' */
				/*if (r == 4) {
					assertTrue(cmThread.existsNamespace("TestSpace"));
					cmThread.createTemporaryProperties("TestSpace");
					cmThread.setProperty(
						"TestSpace",
						"property" + (int) (Math.random() * 50),
						"" + (int) (Math.random() * 50));
					sleep(1);
					while (cmThread.canLock("TestSpace", "user2") == false) {
						sleep(1);
					}
					cmThread.commit("TestSpace", "user2");
				}*/

				/* Read random property from namespace TestSpace2 */
				if (r == 5) {
					assertTrue(cmThread.existsNamespace("TestSpace2"));
					String val =
						cmThread.getString(
							"TestSpace2",
							"property" + (int) (Math.random() * 50));
					assertTrue(val != null);
					sleep(1);
				}
			} catch (final Exception ex) {
				status = false;
				ex.printStackTrace();
			}
		}
	}
}
