/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import junit.framework.TestCase;

/**
 * Unit tests for the class: Notification.
 *
 * @author kinfkong
 * @version 1.0
 */
public class NotificationTest extends TestCase {

    /**
     * Tests constructor: Notification(long, NotificationType, long). With null notificationType,
     * IllegalArgumentException should be thrown.
     */
    public void testNotification_NullNotificationType() {
        try {
            new Notification(1, null, 1);
            fail("IllegalArgument should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor: Notification(long, NotificationType, long). With non-positive project id,
     * IllegalArgumentException should be thrown.
     */
    public void testNotification_NonPostiveProjectId() {
        try {
            new Notification(0, new NotificationType(), 1);
            fail("IllegalArgument should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor: Notification(long, NotificationType, long). With non-positive external id,
     * IllegalArgumentException should be thrown.
     */
    public void testNotification_NonPostiveExternalId() {
        try {
            new Notification(1, new NotificationType(), -1);
            fail("IllegalArgument should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor: Notification(long, NotificationType, long). Accuracy tests to check if the fields are set
     * properly.
     */
    public void testNotification_Accuracy() {
        NotificationType type = new NotificationType();
        Notification notification = new Notification(100, type, 200);
        // assert not null
        assertNotNull("The instance cannot be created.", notification);
        assertEquals("The project id does not set properly.", 100, notification.getProject());
        assertEquals("The notificationType does not set properly.", type, notification.getNotificationType());
        assertEquals("The external id does not set properly.", 200, notification.getExternalId());
    }

    /**
     * Tests method: getProject(). Checks if this method works properly.
     */
    public void testGetProject() {
        NotificationType type = new NotificationType();
        Notification notification = new Notification(100, type, 200);
        assertEquals("The project id does not set properly.", 100, notification.getProject());
    }

    /**
     * Tests method: getNotificationType(). Checks if this method works properly.
     */
    public void testGetNotificationType() {
        NotificationType type = new NotificationType();
        Notification notification = new Notification(100, type, 200);
        assertEquals("The notificationType does not set properly.", type, notification.getNotificationType());
    }

    /**
     * Tests method: getExternalId(). Checks if this method works properly.
     */
    public void testGetExternalId() {
        NotificationType type = new NotificationType();
        Notification notification = new Notification(100, type, 200);
        assertEquals("The external id does not set properly.", 200, notification.getExternalId());
    }

    /**
     * <p>
     * Tests the functionality of XML Serialization of JAXB.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testMarshalAndUnmarshal() throws Exception {
        // create the entity object to serialize
        NotificationType type = new NotificationType();
        type.setId(1);
        type.setName("UnitTest");
        type.setDescription("Desc");
        Notification notification = new Notification(100, type, 200);
        notification.setCreationUser("Ivern");
        notification.setCreationTimestamp(new Date());
        notification.setModificationUser("FireIce");
        notification.setModificationTimestamp(new Date());

        // Marshalling
        JAXBContext jaxbContext = JAXBContext.newInstance(AuditableResourceStructure.class, NotificationType.class, Notification.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
        OutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(notification, os);

        // Unmarshalling
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Notification unmarshalledNotificatoin = (Notification) unmarshaller.unmarshal(new ByteArrayInputStream(os
                .toString().getBytes()));

        // verification.
        assertNotNull("The object is not unmarshalled.", unmarshalledNotificatoin);
        assertEquals("Incorrect project.", notification.getProject(), unmarshalledNotificatoin.getProject());
        assertEquals("Incorrect externalId.", notification.getExternalId(), unmarshalledNotificatoin.getExternalId());
        assertEquals("Incorrect CreationUser.", notification.getCreationUser(), unmarshalledNotificatoin.getCreationUser());
        assertEquals("Incorrect Creation Timestamp.", notification.getCreationTimestamp(), unmarshalledNotificatoin
                .getCreationTimestamp());
        assertEquals("Incorrect ModificationUser.", notification.getModificationUser(), unmarshalledNotificatoin.getModificationUser());
        assertEquals("Incorrect ModificationTimestamp.", notification.getModificationTimestamp(), unmarshalledNotificatoin
                .getModificationTimestamp());

        NotificationType unmarshalledType = unmarshalledNotificatoin.getNotificationType();
        assertEquals("Incorrect id.", type.getId(), unmarshalledType.getId());
        assertEquals("Incorrect Name.", type.getName(), unmarshalledType.getName());
        assertEquals("Incorrect Description.", type.getDescription(), unmarshalledType.getDescription());
    }
}
