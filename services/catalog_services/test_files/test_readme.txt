1. Initialize your Informix database. 
2. Execute the following sql scripts to set up the databases. 
-test_files\sql\create.ddl
-test_files\sql\id_generator.sql

3. Update the following configuration files, you need to modify the connection string for 
databases. 
-test_files\META-INF\persistence.xml
-test_files\com\topcoder\db\connectionfactory\DBConnectionFactory.xml

4. Modify the "build-dependencies.xml" for dependencies. 