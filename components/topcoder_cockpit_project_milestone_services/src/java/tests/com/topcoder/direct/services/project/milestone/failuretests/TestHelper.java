package com.topcoder.direct.services.project.milestone.failuretests;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

final class TestHelper {

	/**
	 * The properties file which hold the db configuration
	 */
    private final static Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(new FileInputStream("test_files/failure/db.properties"));
        } catch (FileNotFoundException e) {
            // ignore
        } catch (IOException e) {
            // ignore
        }
    }


    /**
     * <p>Create a new db connection to return.</p>
     *
     * @return the db connection.
     *
     * @throws Exception to JUnit
     */
    static Connection createConnection() throws Exception {
        String dbUrl = PROPERTIES.getProperty("dburl");
        String dbUser = PROPERTIES.getProperty("dbuser");
        String dbPassword = PROPERTIES.getProperty("dbpassword");
        String dbClass = PROPERTIES.getProperty("dbdriver");

        Class.forName(dbClass);

        if ((dbUser != null) && (!(dbUser.trim().length() == 0))) {
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } else {
            return DriverManager.getConnection(dbUrl);
        }
    }
    /**
     * <p>Executes the sql scripts in the given sql file.</p>
     *
     * @param connection JDBC connection.
     * @param sqlPath the path of the sql file to execute
     *
     * @throws Exception if exception occurs during database operation
     */
    static void executeSqlFile(Connection connection, String sqlPath)
        throws Exception {
        String[] sqlStatements = getFileAsString(sqlPath).split(";");

        Statement stmt = null;

        try {
            stmt = connection.createStatement();

            for (int i = 0; i < sqlStatements.length; i++) {
                if ((sqlStatements[i].trim().length() != 0) && !sqlStatements[i].trim().startsWith("--")) {
                    stmt.executeUpdate(sqlStatements[i]);
                }
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * Gets the file content as string.
     *
     * @param filePath the file path
     *
     * @return The content of file
     *
     * @throws Exception to JUnit
     */
    static String getFileAsString(String filePath) throws Exception {
        StringBuilder buf = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));

        try {
            String s;

            while ((s = in.readLine()) != null) {
                buf.append(s);
            }

            return buf.toString();
        } finally {
            in.close();
        }
    }
}
