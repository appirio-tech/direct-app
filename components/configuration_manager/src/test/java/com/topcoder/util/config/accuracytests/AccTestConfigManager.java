/**
 * Created by IntelliJ IDEA.
 * User: kcottrell
 * Date: Feb 7, 2004
 * Time: 4:29:03 PM
 * To change this template use Options | File Templates.
 */
package com.topcoder.util.config.accuracytests;

import com.topcoder.util.config.*;
import junit.framework.TestCase;

import java.io.*;
import java.util.ArrayList;

public class AccTestConfigManager extends TestCase{

    ConfigManager cm = null;
    File propertiesFile = null;
    ArrayList origRecOrder = null;

    protected void setUp() throws Exception {

        //propertiesFile = File.createTempFile("accuracy", ".properties",
        //                                     new File("C:/TopCoder/Review Projects/Configuration Manager/test_files"));
        propertiesFile = File.createTempFile("accuracy", ".properties", new File("test_files"));
        PrintWriter writer = new PrintWriter(new FileWriter(propertiesFile));
        writer.println("# List of Chains");
        writer.println("Chains=Kroger;Safeway");
        writer.println("# List of States");
        writer.println("Kroger.States=NY;OH");
        writer.println("Kroger.NY.Stores=Store1384;Store3123");
        writer.println("Kroger.OH.Stores=Store932;Store889");
        writer.println("Kroger.NY.Store1384.Sales=4000");
        writer.println("Kroger.OH.Store932.Sales=2000");
        writer.println("Kroger.OH.Store889.Sales=3000");
        writer.println("Safeway.States=NY;NJ");
        writer.println("Safeway.NY.Stores=Store5555;Store5556");
        writer.println("Safeway.NY.Store5555.Sales=8000");
        writer.println("Safeway.NY.Store5556.Sales=8001");
        writer.println("Safeway.NJ.Stores=Store6000;Store6002");
        writer.println("Safeway.NJ.Store6000.Sales=7000");
        writer.println("Safeway.NJ.Store6002.Sales=10001");
        writer.println("# Kroger store 3123 sales");
        writer.println("Kroger.NY.Store3123.Sales=2999");
        writer.close();

        origRecOrder = fileRecOrder(propertiesFile);

        try {
            cm = ConfigManager.getInstance();
            cm.add("GroceryChains", propertiesFile.getName(),
                   ConfigManager.CONFIG_PROPERTIES_FORMAT);
        }
        catch (Exception e) {
            fail ("Unexpected exception: " + e.getMessage());
        }
    }

    protected void tearDown() throws Exception {
        if (cm.existsNamespace("GroceryChains")) {
            cm.removeNamespace("GroceryChains");
        }
        propertiesFile.deleteOnExit();
    }

    public void testConfigManager() {
        try
        {
            int totalSales = totalChainSales();
            assertEquals(totalSales, 45001);

            cm.createTemporaryProperties("GroceryChains");
            cm.setProperty("GroceryChains", "Kroger.CT.Store55.Sales", "9000");
            cm.addToProperty("GroceryChains", "Kroger.States", "CT");
            cm.addToProperty("GroceryChains", "Kroger.CT.Stores", "Store55");
            cm.getPropertyObject("GroceryChains", "Kroger").addComment("Kroger store info");
            cm.commit("GroceryChains", "manager");

            totalSales = totalChainSales();
            assertEquals(totalSales, 54001);

            cm.createTemporaryProperties("GroceryChains");
            cm.removeProperty("GroceryChains", "Safeway");
            cm.removeValue("GroceryChains", "Chains", "Safeway");
            cm.commit("GroceryChains", "manager");

            totalSales = totalChainSales();
            assertEquals(totalSales, 20999);

            cm.removeNamespace("GroceryChains");

            assertFalse(cm.existsNamespace("GroceryChains"));

            totalSales = totalChainSales();
            assertEquals(totalSales, 0);
        }
        catch (ConfigManagerException cme)
        {
            fail("Unexpected ConfigManagerException" + cme.getMessage());
        }
        catch (Exception e) {
            fail("Unexpected Exception: " + e.getMessage());
        }
    }

    public void testPropertyOrdering() {
        try
        {
            cm.createTemporaryProperties("GroceryChains");
            cm.setProperty("GroceryChains", "Kroger.CT.Store55.Sales", "9000");
            cm.setProperty("GroceryChains", "Kroger.CT.Store56.Sales", "9000");
            cm.setProperty("GroceryChains", "Kroger.NY.Store111.Sales", "1000");
            cm.setProperty("GroceryChains", "Stop&Shop.CT.Store1.Sales", "5000");
            cm.setProperty("GroceryChains", "Stop&Shop.CT.Store2.Sales", "5004");
            cm.commit("GroceryChains", "manager");

            ArrayList newRecOrder = fileRecOrder(this.propertiesFile);
            for (int i=0; i<this.origRecOrder.size(); i++)
            {
                assertEquals(this.origRecOrder.get(i).toString(), newRecOrder.get(i).toString() );
            }
        }
        catch (ConfigManagerException cme) {
            fail("Unexpected ConfigManagerException" + cme.getMessage());
        }
        catch (Exception e) {
            fail ("Unexpected Exception: " + e.getMessage());
        }
    }

        public void testUsers() {
        try
        {
            cm.createTemporaryProperties("GroceryChains");
            cm.setProperty("GroceryChains", "Kroger.CT.Store58.Sales", "9000");
            cm.setProperty("GroceryChains", "Kroger.CT.Store59.Sales", "9000");
            cm.setProperty("GroceryChains", "Kroger.NY.Store112.Sales", "1000");
            cm.setProperty("GroceryChains", "Stop&Shop.CT.Store3.Sales", "5000");
            cm.setProperty("GroceryChains", "Stop&Shop.CT.Store4.Sales", "5004");
            cm.commit("GroceryChains", "manager");

            assertTrue(cm.canLock("GroceryChains", "frank"));
            cm.lock("GroceryChains", "frank");
            cm.createTemporaryProperties("GroceryChains");
            cm.removeProperty("GroceryChains", "Stop&Shop");

            assertFalse(cm.canLock("GroceryChains", "manager"));
         }
        catch (ConfigManagerException cme)
        {
            fail("Unexpected ConfigManagerException" + cme.getMessage());
        }
        catch (Exception e) {
            fail("Unexpected Exception: " + e.getMessage());
        }

        try {
            cm.lock("GroceryChains", "manager");
        }
        catch (ConfigLockedException cle) {
            // Expected Exception
            return;
        }
        catch (ConfigManagerException cme) {
            fail ("ConfigLockedException expected");
        }
        fail ("ConfigLockedException expected");
    }

    private int totalChainSales() throws UnknownNamespaceException {
        int totalSales=0;
        if (cm.existsNamespace("GroceryChains"))
        {
            String[] chains = cm.getStringArray("GroceryChains","Chains");
            for (int i=0; i<chains.length; i++)
            {
                String[] states = cm.getStringArray("GroceryChains", chains[i]+".States");
                for (int j=0; j<states.length; j++)
                {
                    String[] stores = cm.getStringArray("GroceryChains",
                                                        chains[i]+"."+states[j]+".Stores");
                    for (int k=0; k<stores.length; k++)
                    {
                        String prop = chains[i]+"."+states[j]+"."+stores[k]+".Sales";
                        String amt = cm.getString("GroceryChains", prop);
                        totalSales += Integer.parseInt(amt);
                    }
                }
            }
        }
        return totalSales;
    }

    private ArrayList fileRecOrder(File f) {
        ArrayList lines = new ArrayList();
        try {
            LineNumberReader reader = new LineNumberReader(new FileReader(f));
            lines.add(reader.readLine());
        }
        catch (FileNotFoundException fnfe)
        {
            fail("File not found: " + f.getAbsolutePath());
        }
        catch (IOException ioe)
        {
            fail("IOException reading: " + f.getAbsolutePath());
        }
        return lines;
    }

}
