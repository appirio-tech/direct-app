/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.accuracytests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cronos.onlinereview.autoscreening.management.PersistenceException;
import com.cronos.onlinereview.autoscreening.management.ScreeningManager;
import com.cronos.onlinereview.autoscreening.management.ScreeningManagerFactory;
import com.cronos.onlinereview.autoscreening.management.ScreeningResponse;
import com.cronos.onlinereview.autoscreening.management.ScreeningResult;
import com.cronos.onlinereview.autoscreening.management.ScreeningStatus;
import com.cronos.onlinereview.autoscreening.management.ScreeningTask;
import com.cronos.onlinereview.autoscreening.management.ScreeningTaskDoesNotExistException;
import com.cronos.onlinereview.autoscreening.management.db.DbScreeningManager;
import com.cronos.onlinereview.autoscreening.management.db.DefaultDbScreeningManager;
import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.idgenerator.IDGeneratorImpl;

import junit.framework.TestCase;

/**
 * <p>The accuracy test cases for DefaultDbScreeningManager class.</p>
 *
 * @author oodinary
 * @version 1.0
 */
public class DefaultDbScreeningManagerAccuracyTest extends TestCase {

	/**
     * <p>A ScreeningManager instance for testing.</p>
     */
    private ScreeningManager screeningManager = null;
    
    /**
     * <p>The default connection.</p>
     */
    Connection defaultConn = null;

    /**
     * <p>Initialization.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
    	AccuracyTestHelper.addConfig("accuracytests/config.xml");
    	AccuracyTestHelper.insertTestingData();
    	screeningManager = ScreeningManagerFactory.createScreeningManager();
    	
    	defaultConn = new DBConnectionFactoryImpl("com.cronos.onlinereview.autoscreening.management.db")
    		.createConnection();
    }

    /**
     * <p>Set screeningManager to null.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
    	AccuracyTestHelper.deleteTestingData();
    	screeningManager = null;
    	AccuracyTestHelper.clearConfig();
    	
    	defaultConn.close();
    	defaultConn = null;
    }
    
    /**
     * <p>Tests the accuracy of the ctor(DBConnectionFactoryImpl, IDGeneratorImpl).</p>
     * 
     * @throws ConfigurationException will never throw.
     * @throws UnknownConnectionException will never throw.
     * @throws IDGenerationException will never throw.
     */
    public void testCtor_DBConnectionFactoryImplIDGeneratorImpl_Accuracy()
    	throws UnknownConnectionException, ConfigurationException, IDGenerationException {

    	DBConnectionFactoryImpl dBConnectionFactory = new DBConnectionFactoryImpl(
        	"com.cronos.onlinereview.autoscreening.management.db");
    	IDGeneratorImpl iDGenerator = (IDGeneratorImpl) IDGeneratorFactory.getIDGenerator("screening_task_id_seq");
    	
    	DefaultDbScreeningManager defaultDbScreeningManager = 
    		new DefaultDbScreeningManager(dBConnectionFactory, iDGenerator);
        assertNotNull("DefaultDbScreeningManager should be accurately created.", defaultDbScreeningManager);
        
        // Gets idGenerator by reflection.
        Object idGen = AccuracyTestHelper.getPrivateField(DefaultDbScreeningManager.class, 
        		defaultDbScreeningManager, "idGenerator");

        assertEquals("The idGenerator should be set correctly.",
        		iDGenerator, idGen);
    }
    
    /**
     * <p>Tests the accuracy of the ctor(DBConnectionFactoryImpl, String, IDGeneratorImpl).</p>
     * 
     * @throws ConfigurationException will never throw.
     * @throws UnknownConnectionException will never throw.
     * @throws IDGenerationException will never throw.
     */
    public void testCtor_DBConnectionFactoryImplStringIDGeneratorImpl_Accuracy()
    	throws UnknownConnectionException, ConfigurationException, IDGenerationException {

    	DBConnectionFactoryImpl dBConnectionFactory = new DBConnectionFactoryImpl(
        	"com.cronos.onlinereview.autoscreening.management.db");
    	IDGeneratorImpl iDGenerator = (IDGeneratorImpl) IDGeneratorFactory.getIDGenerator("screening_task_id_seq");
    	
    	DefaultDbScreeningManager defaultDbScreeningManager = 
    		new DefaultDbScreeningManager(dBConnectionFactory, "default_conn", iDGenerator);
        assertNotNull("DefaultDbScreeningManager should be accurately created.", defaultDbScreeningManager);
        
        // Gets idGenerator by reflection.
        Object idGen = AccuracyTestHelper.getPrivateField(DefaultDbScreeningManager.class, 
        		defaultDbScreeningManager, "idGenerator");

        assertEquals("The idGenerator should be set correctly.",
        		iDGenerator, idGen);
        
        // Gets conn name by reflection.
        Object connName = AccuracyTestHelper.getPrivateField(DbScreeningManager.class, 
        		defaultDbScreeningManager, "connectionName");

        assertEquals("The connName should be set correctly.",
        		"default_conn", connName);
    }
    
    /**
     * <p>Tests the accuracy of the initiatingScreeningTask method.</p>
     *
     * @throws Exception will never throw.
     */
    public void testInitiatingScreeningTask_Accuracy()
    	throws Exception {
        
    	screeningManager.initiateScreening(56, "Operator ABCDE");
    	
    	// Tests the insertion.
    	PreparedStatement ps = defaultConn.prepareStatement("select * from screening_task where upload_id = 56");
    	ResultSet rs = ps.executeQuery();
    	
    	int num = 0;
    	while (rs.next()) {
    		num ++;
    		// Asserts.
	    	String cUser = rs.getString("create_user");
	    	assertEquals("The createUser should be set correctly.",
	        		"Operator ABCDE", cUser);
    	}
    	assertEquals("There should be only one record.", 1, num);
    }
    
    /**
     * <p>Tests the accuracy of the getScreeningTasks(long[]) method.</p>
     * 
     * @throws ScreeningTaskDoesNotExistException 
     * @throws PersistenceException 
     */
    public void testGetScreeningTasks_LongArray_Accuracy()
    	throws PersistenceException, ScreeningTaskDoesNotExistException {
    	
    	ScreeningTask[] tasks = screeningManager.getScreeningTasks(new long[] {52});
    	
    	assertEquals("There should be one record.", 1, tasks.length);
    	
    	// Assets
    	assertEquals("The id should be the same.", 72, tasks[0].getId());
    	assertEquals("The upload id should be the same.", 52, tasks[0].getUpload());
    	assertEquals("The Screener should be the same.", 82, tasks[0].getScreener());
    	assertEquals("The CreationUser should be the same.",
    			"screening_task_create_user_2", tasks[0].getCreationUser());
    	assertEquals("The ModificationUser should be the same.", 
    			"screening_task_modify_user_2", tasks[0].getModificationUser());
    	
    	ScreeningStatus screeningStatus = tasks[0].getScreeningStatus();
    	assertEquals("The id should be the same.", 62, screeningStatus.getId());
    	assertEquals("The name should be the same.", "Screening", screeningStatus.getName());
    }
    
    /**
     * <p>Tests the accuracy of the getScreeningTasks(long[], boolean) method.</p>
     * 
     * @throws ScreeningTaskDoesNotExistException 
     * @throws PersistenceException 
     */
    public void testGetScreeningTasks_LongArrayBool_Accuracy()
    	throws PersistenceException, ScreeningTaskDoesNotExistException {
    	
    	ScreeningTask[] tasks = screeningManager.getScreeningTasks(new long[] {52}, true);
    	
    	assertEquals("There should be one record.", 1, tasks.length);
    	
    	// Assets
    	assertEquals("The id should be the same.", 72, tasks[0].getId());
    	assertEquals("The upload id should be the same.", 52, tasks[0].getUpload());
    	assertEquals("The Screener should be the same.", 82, tasks[0].getScreener());
    	assertEquals("The CreationUser should be the same.",
    			"screening_task_create_user_2", tasks[0].getCreationUser());
    	assertEquals("The ModificationUser should be the same.", 
    			"screening_task_modify_user_2", tasks[0].getModificationUser());
    	
    	ScreeningStatus screeningStatus = tasks[0].getScreeningStatus();
    	assertEquals("The id should be the same.", 62, screeningStatus.getId());
    	assertEquals("The name should be the same.", "Screening", screeningStatus.getName());
    }
    
    /**
     * <p>Tests the accuracy of the getScreeningTasks(long[], boolean) method.</p>
     * <p>Not exists.</p>
     * 
     * @throws ScreeningTaskDoesNotExistException 
     * @throws PersistenceException 
     */
    public void testGetScreeningTasks_LongArrayBool_Accuracy2()
    	throws PersistenceException, ScreeningTaskDoesNotExistException {
    	
    	ScreeningTask[] tasks = screeningManager.getScreeningTasks(new long[] {10000}, true);
    	
    	assertEquals("There should be zero record.", 1, tasks.length);
    	
    	// Assets
    	assertNull("It should be null.", tasks[0]);
    }
    
    /**
     * <p>Tests the accuracy of the getScreeningDetails(long) method.</p>
     * 
     * @throws ScreeningTaskDoesNotExistException 
     * @throws PersistenceException 
     */
    public void testGetScreeningDetails_Long_Accuracy()
    	throws PersistenceException, ScreeningTaskDoesNotExistException {
    	
    	ScreeningTask task = screeningManager.getScreeningDetails(51);
    	ScreeningResult[] results = task.getAllScreeningResults();
    	
    	// Assets
    	assertEquals("The id should be the same.", 71, task.getId());
    	assertEquals("The upload id should be the same.", 51, task.getUpload());
    	assertEquals("The Screener should be the same.", 81, task.getScreener());
    	assertEquals("The CreationUser should be the same.",
    			"screening_task_create_user_1", task.getCreationUser());
    	assertEquals("The ModificationUser should be the same.", 
    			"screening_task_modify_user_1", task.getModificationUser());
    	
    	ScreeningStatus screeningStatus = task.getScreeningStatus();
    	assertEquals("The id should be the same.", 62, screeningStatus.getId());
    	assertEquals("The name should be the same.", "Screening", screeningStatus.getName());
    	
    	assertEquals("The length should be same.", 1, results.length);
    	assertEquals("The id should be the same.", 111, results[0].getId());
    	assertEquals("The text should be the same.", "dynamic_response_text_1", results[0].getDynamicText());

    	ScreeningResponse response = results[0].getScreeningResponse();
    	assertEquals("The id should be same.", 101, response.getId());
    	assertEquals("The code should be same.", "response_code_1", response.getResponseCode());
    	assertEquals("The text should be same.", "response_text_1", response.getResponseText());
    }
}
