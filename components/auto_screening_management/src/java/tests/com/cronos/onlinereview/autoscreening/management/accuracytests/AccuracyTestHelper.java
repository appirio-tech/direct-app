/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.accuracytests;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>Defines helper methods used in tests.</p>
 *
 * @author oodinary
 * @version 1.0
 */
public class AccuracyTestHelper {

	/**
     * <p>Gets the value of a private field in the given class. The field has the given name. The value is
     * retrieved from the given instance.</p>
     *
     * @param type the class which the private field belongs to.
     * @param instance the instance which the private field belongs to.
     * @param name the name of the private field to be retrieved.
     *
     * @return the value of the private field or <code>null</code> if any error occurs.
     */
    public static Object getPrivateField(Class type, Object instance, String name) {

        Field field = null;
        Object obj = null;

        try {
            // Get the reflection of the field and get the value
            field = type.getDeclaredField(name);
            field.setAccessible(true);
            obj = field.get(instance);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // Reset the accessibility
                field.setAccessible(false);
            }
        }

        return obj;
    }
    
    /**
     * <p>Clears the config.</p>
     *
     * @throws Exception unexpected exception.
     */
    public static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>Adds the config of given config file.</p>
     *
     * @param configFile the given config file.
     *
     * @throws Exception unexpected exception.
     */
    public static void addConfig(String configFile) throws Exception {
        clearConfig();

        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add(configFile);
    }
    
    /**
     * <p>Inserts the data for testing to the database.</p>
     */
    public static void insertTestingData() throws Exception {
    	deleteTestingData();

        Connection conn = new DBConnectionFactoryImpl("com.cronos.onlinereview.autoscreening.management.db")
                .createConnection();

        // project_category_lu table
        PreparedStatement stmt = conn.prepareStatement(
        		"insert into project_category_lu("
                + "project_category_id) values(?)");
        stmt.setLong(1, 1);
        stmt.executeUpdate();
        stmt.close();
        
        // project table
        stmt = conn.prepareStatement(
        		"insert into project("
                + "project_id, project_category_id) values(?, ?)");
        for (int i = 1; i <= 6; ++i) {
            stmt.setLong(1, i + 10);
            stmt.setLong(2, 1);
            stmt.executeUpdate();
        }
        stmt.close();
        
        // resource_info_type_lu table
        stmt = conn.prepareStatement(
        		"insert into resource_info_type_lu("
                + "resource_info_type_id, name) values(?, ?)");
        stmt.setLong(1, 31);
        stmt.setString(2, "resource_info_type_id_31");
        stmt.executeUpdate();
        stmt.close();
        
        // resource table
        stmt = conn.prepareStatement(
        		"insert into resource(resource_id) values(?)");
        for (int i = 1; i <= 6; ++i) {
            stmt.setLong(1, i + 40);
            stmt.executeUpdate();
        }
        stmt.close();
        
        // resource_info table
        stmt = conn.prepareStatement(
        		"insert into resource_info("
                + "resource_id, resource_info_type_id, value) values(?, ?, ?)");
        for (int i = 1; i <= 6; ++i) {
            stmt.setLong(1, i + 40);
            stmt.setLong(2, 31);
            stmt.setString(3, "resource_info_" + i);
            stmt.executeUpdate();
        }
        stmt.close();
        
        // upload table
        stmt = conn.prepareStatement(
        		"insert into upload("
                + "upload_id, project_id, resource_id) values(?, ?, ?)");
        for (int i = 1; i <= 6; ++i) {
            stmt.setLong(1, i + 50);
            stmt.setLong(2, i + 10);
            stmt.setLong(3, i + 40);
            stmt.executeUpdate();
        }
        stmt.close();
        
        // screening_status_lu table
        stmt = conn.prepareStatement(
        		"insert into screening_status_lu("
                + "screening_status_id, name, description, create_user, create_date, modify_user"
                + ", modify_date) "
                + "values(?, ?, ?, ?, ?, ?, ?)");
        stmt.setLong(1, 61);
        stmt.setString(2, "Pending");
        stmt.setString(3, "screening_status_lu_description");
        stmt.setString(4, "screening_status_lu_create_user");
        stmt.setDate(5, new Date(System.currentTimeMillis() + 100000));
        stmt.setString(6, "screening_status_lu_modify_user");
        stmt.setDate(7, new Date(System.currentTimeMillis() + 200000));
        stmt.executeUpdate();
        // add Screening
        stmt.setLong(1, 62);
        stmt.setString(2, "Screening");
        stmt.setString(3, "screening_status_lu_description");
        stmt.setString(4, "screening_status_lu_create_user");
        stmt.setDate(5, new Date(System.currentTimeMillis() + 100000));
        stmt.setString(6, "screening_status_lu_modify_user");
        stmt.setDate(7, new Date(System.currentTimeMillis() + 200000));
        stmt.executeUpdate();
        stmt.close();

        // screening_task table
        stmt = conn.prepareStatement(
        		"insert into screening_task("
                + "screening_task_id, upload_id, screening_status_id, screener_id, start_timestamp, create_user"
                + ", create_date, modify_user, modify_date) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
        for (int i = 1; i <= 5; ++i) {
            stmt.setLong(1, i + 70);
            stmt.setLong(2, i + 50);
            stmt.setLong(3, 62);
            // add some null value to field 4 and 5
            stmt.setLong(4, i + 80);
            stmt.setDate(5, new Date(System.currentTimeMillis() + (i + 10) * 100000));
            if (i == 3) {
                stmt.setObject(4, null);
                stmt.setObject(5, null);
            } else if (i == 4) {
                stmt.setObject(4, null);
            } else if (i == 5) {
                stmt.setObject(5, null);
            }
            stmt.setString(6, "screening_task_create_user_" + i);
            stmt.setDate(7, new Date(System.currentTimeMillis() + (i + 10) * 200000));
            stmt.setString(8, "screening_task_modify_user_" + i);
            stmt.setDate(9, new Date(System.currentTimeMillis() + (i + 10) * 300000));
            stmt.executeUpdate();
        }
        stmt.close();
        
        // response_severity_lu table
        stmt = conn.prepareStatement(
        		"insert into response_severity_lu("
                + "response_severity_id, name, description, create_user, create_date, modify_user"
                + ", modify_date) values(?, ?, ?, ?, ?, ?, ?)");
        for (int i = 1; i <= 5; ++i) {
            stmt.setLong(1, i + 90);
            stmt.setString(2, "response_severity_lu_name_" + i);
            stmt.setString(3, "response_severity_lu_description_" + i);
            stmt.setString(4, "response_severity_lu_create_user_" + i);
            stmt.setDate(5, new Date(System.currentTimeMillis() + (i + 20) * 100000));
            stmt.setString(6, "response_severity_lu_modify_user_" + i);
            stmt.setDate(7, new Date(System.currentTimeMillis() + (i + 20) * 200000));
            stmt.executeUpdate();
        }
        stmt.close();
        
        // screening_response_lu table
        stmt = conn.prepareStatement(
        		"insert into screening_response_lu("
                + "screening_response_id, response_severity_id, response_code, response_text, create_user, create_date"
                + ", modify_user, modify_date) " + "values(?, ?, ?, ?, ?, ?, ?, ?)");
        for (int i = 1; i <= 5; ++i) {
            stmt.setLong(1, i + 100);
            stmt.setLong(2, i + 90);
            stmt.setString(3, "response_code_" + i);
            stmt.setString(4, "response_text_" + i);
            stmt.setString(5, "create_user" + i);
            stmt.setDate(6, new Date(System.currentTimeMillis() + (i + 30) * 100000));
            stmt.setString(7, "modify_user" + i);
            stmt.setDate(8, new Date(System.currentTimeMillis() + (i + 30) * 100000));
            stmt.executeUpdate();
        }
        stmt.close();
        
        // screening_result table
        stmt = conn.prepareStatement(
        		"insert into screening_result("
                + "screening_result_id, screening_task_id, screening_response_id, dynamic_response_text, create_user"
                + ", create_date, modify_user, modify_date) " + "values(?, ?, ?, ?, ?, ?, ?, ?)");
        for (int i = 1; i <= 1; ++i) {
            stmt.setLong(1, i + 110);
            stmt.setLong(2, 71);
            stmt.setLong(3, i + 100);
            stmt.setString(4, "dynamic_response_text_" + i);
            stmt.setString(5, "create_user_" + i);
            stmt.setDate(6, new Date(System.currentTimeMillis() + (i + 40) * 100000));
            stmt.setString(7, "modify_user_" + i);
            stmt.setDate(8, new Date(System.currentTimeMillis() + (i + 40) * 100000));
            stmt.executeUpdate();
        }
        stmt.close();

        conn.close();
    }

    /**
     * <p>Deletes testing data.</p>
     */
    public static void deleteTestingData() throws Exception {
        Connection conn = new DBConnectionFactoryImpl("com.cronos.onlinereview.autoscreening.management.db")
                .createConnection();

        PreparedStatement stmt = conn.prepareStatement("delete from screening_result");
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("delete from screening_response_lu");
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("delete from response_severity_lu");
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("delete from screening_task");
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("delete from screening_status_lu");
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("delete from upload");
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("delete from resource_info");
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("delete from resource");
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("delete from resource_info_type_lu");
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("delete from project");
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("delete from project_category_lu");
        stmt.executeUpdate();
        stmt.close();

        conn.close();
    }
}
