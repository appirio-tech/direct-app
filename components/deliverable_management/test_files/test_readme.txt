1. Initialize your Informix database. 
2. Execute the following sql scripts to set up the databases. 
-test_files\dbschema.sql

3. Update the following configuration files, you need to modify the connection string for 
databases. 
-test_files\SearchBundleManager.xml
-test_files\accuracytests\PersistenceDeliverableManagerConfig.xml
-test_files\accuracytests\PersistenceUploadManagerConfig.xml
-test_files\failure\db.xml
-test_files\stresstests\DBConnectionFactory.xml