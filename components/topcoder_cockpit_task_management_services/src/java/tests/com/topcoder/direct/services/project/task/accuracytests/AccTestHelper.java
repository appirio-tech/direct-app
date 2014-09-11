/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.accuracytests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * Accuracy Helper.
 *
 * @author amazingpig
 * @version 1.0
 */
public class AccTestHelper {
    public static EntityManager entityManager;
    static {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        EntityManagerFactory emf = (EntityManagerFactory) ctx.getBean("entityManagerFactory");
        entityManager = emf.createEntityManager();
    }
    public static Object getPrivateField(Class<?> privateClass, Object instance, String fieldName) throws Exception {
        Field privateField = privateClass.getDeclaredField("fieldName");
        privateField.setAccessible(true);
        Object result = privateField.get(instance);
        privateField.setAccessible(false);
        return result;
    }

    public static void initDataBase() throws Exception {
    	runSql("test_files/accuracy" + File.separator + "initDataBase.sql", entityManager);
    }
    public static void clearUpDataBase() throws Exception {
    	runSql("test_files/accuracy" + File.separator + "clearUpDataBase.sql", entityManager);
    }

    private static void runSql(String filePath, EntityManager entityManager) throws Exception {
    	FileReader fr = new FileReader(filePath);
    	BufferedReader br = new BufferedReader(fr);
    	String sql = null;
    	entityManager.getTransaction().begin();
    	while (br.ready()) {
    		sql = br.readLine().trim();
    		if (sql.length() > 0 && !sql.startsWith("#")) {
    			entityManager.createNativeQuery(sql).executeUpdate();
    		}
    	}
    	entityManager.getTransaction().commit();
    	br.close();
    	fr.close();
    }

    public static <T> void persist(T obj) {
        entityManager.getTransaction().begin();
        entityManager.persist(obj);
        entityManager.getTransaction().commit();
    }
}
