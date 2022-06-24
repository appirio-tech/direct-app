/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.failuretests;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.EntityManager;

import org.hibernate.ejb.Ejb3Configuration;

import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.util.config.ConfigManager;

/**
 * the util for junit test using.
 *
 * @author AK_47
 * @version 1.0
 */
public final class TestHelper {

    /**
     * the <code>EntityManager</code> for testing.
     */
    private static final EntityManager MANAGER = getEntityManager();

    /**
     * the private ctor to avoid create instance.
     */
    private TestHelper() {
    }

    /**
     * Returns the <code>EntityManager</code> instance for testing.
     *
     * @return the <code>EntityManager</code> instance.
     */
    public static EntityManager getEntityManager() {
        if (MANAGER != null){
            return MANAGER;
        }
        Ejb3Configuration cfg = new Ejb3Configuration();
        cfg.configure("hibernate.cfg.xml");

        return cfg.buildEntityManagerFactory().createEntityManager();
    }

    /**
     * Set fields of auditableEntity.
     *
     * @param auditableEntity the auditableEntity to set
     */
    public static void setAuditableEntity(AuditableEntity auditableEntity) {
        auditableEntity.setCreateUsername("createUser");
        auditableEntity.setModifyUsername("modifyUser");
        auditableEntity.setCreateDate(new Date());
        auditableEntity.setModifyDate(new Date());
        auditableEntity.setName("name");
    }


    /**
     * Sets the value of a private field in the given class.
     *
     * @param type the class which the private field belongs to
     * @param instance the instance which the private field belongs to
     * @param name the name of the private field to be set
     * @param value the value to set
     */
    public static void setField(Class type, Object instance, String name, Object value) {
        Field field = null;
        try {
            field = type.getDeclaredField(name);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                field.setAccessible(false);
            }
        }
    }

    /**
     * Remove all the namespace.
     *
     * @throws Exception
     *                 to JUnit
     */
    public static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * Add the namespace.
     *
     * @param filename
     *                the config filename
     * @throws Exception
     *                 to JUnit
     */
    public static void addConfig(String filename) throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(filename);
    }
}
