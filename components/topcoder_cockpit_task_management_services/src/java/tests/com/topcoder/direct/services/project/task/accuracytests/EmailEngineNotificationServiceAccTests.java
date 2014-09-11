/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.accuracytests;

import java.util.Arrays;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.topcoder.direct.services.project.task.impl.EmailEngineNotificationService;
import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskPriority;
import com.topcoder.direct.services.project.task.model.TaskStatus;
import com.topcoder.direct.services.project.task.model.UserDTO;
import com.topcoder.service.user.User;
import com.topcoder.service.user.UserService;
import com.topcoder.util.log.log4j.Log4jLogFactory;

/**
 * Accuracy tests for EmailEngineNotificationService.
 * 
 * @author amazingpig
 * @version 1.0
 */
public class EmailEngineNotificationServiceAccTests extends TestCase {
	/** 
     * Represents the EmailEngineNotificationService instance to test. 
     * */
    private EmailEngineNotificationService instance;
    /**
     * <p>Sets up the unit tests.</p>
     */
    public void setUp() throws Exception {
        instance = new EmailEngineNotificationService();
        instance.setEmailSender("from@tc.com");
        instance.setLog(new Log4jLogFactory().createLog("test"));
        instance.setTaskCreationEmailBodyTemplatePath("test_files/accuracy/taskcreationbody.txt");
        instance.setTaskCreationEmailSubjectTemplateText("test_files/accuracy/taskcreationsubject.txt");
        instance.setTaskStatusChangeEmailBodyTemplatePath("test_files/accuracy/statuschangebody.txt");
        instance.setTaskStatusChangeEmailSubjectTemplateText("test_files/accuracy/statuschangesubject.txt");

    }

    /**
     * <p>Cleans up the unit tests.</p>
     */
    public void tearDown() {
        instance = null;
    }

    /**
     * Accuracy test for method notifyTaskCreation.
     * @throws Exception 
     */
    public void test_notifyTaskCreation() throws Exception {
    	UserService us = EasyMock.createNiceMock(UserService.class);
    	EasyMock.expect(us.getUser(1)).andReturn(new User());
        EasyMock.expect(us.getEmailAddress(0)).andReturn("to1@tc.com").anyTimes();
    	EasyMock.expect(us.getEmailAddress(1)).andReturn("to1@tc.com").anyTimes();
    	EasyMock.expect(us.getEmailAddress(2)).andReturn("to2@tc.com").anyTimes();
		
    	EasyMock.replay(us);
		instance.setUserService(us);
    	
    	Task task = new Task();
    	task.setName("t1");
    	task.setStatus(TaskStatus.IN_PROGRESS);
    	task.setPriority(TaskPriority.HIGH);
    	UserDTO u1 = new UserDTO();
    	u1.setHandle("to1@tc.com");
    	u1.setUserId(1);
    	UserDTO u2 = new UserDTO();
    	u2.setHandle("to1@tc.com");
    	u2.setUserId(1);
		task.setAssignees(Arrays.asList(u1, u2));
		
		instance.notifyTaskCreation(1, task );
		//manually check the mails
    }
    
    /**
     * Accuracy test for method notifyTaskStatusChange.
     * @throws Exception 
     */
    public void test_notifyTaskStatusChange() throws Exception {
    	UserService us = EasyMock.createNiceMock(UserService.class);
    	EasyMock.expect(us.getUser(1)).andReturn(new User());
        EasyMock.expect(us.getEmailAddress(0)).andReturn("to1@tc.com").anyTimes();
        EasyMock.expect(us.getEmailAddress(1)).andReturn("to1@tc.com").anyTimes();
        EasyMock.expect(us.getEmailAddress(2)).andReturn("to2@tc.com").anyTimes();
        EasyMock.expect(us.getEmailAddress("admin")).andReturn("to2@tc.com").anyTimes();
        
    	EasyMock.replay(us);
		instance.setUserService(us);
    	
    	Task task = new Task();
    	task.setName("t1");
    	task.setStatus(TaskStatus.IN_PROGRESS);
    	task.setPriority(TaskPriority.HIGH);
    	UserDTO u1 = new UserDTO();
    	u1.setHandle("to1@tc.com");
    	u1.setUserId(1);
    	UserDTO u2 = new UserDTO();
    	u2.setHandle("to1@tc.com");
    	u2.setUserId(1);
		task.setAssignees(Arrays.asList(u1, u2));
		task.setCreatedBy("admin");
		
		instance.notifyTaskStatusChange(1, TaskStatus.COMPLETED, task );
        //manually check the mails
    }

}
