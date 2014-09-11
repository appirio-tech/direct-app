Steps to run through the tests:
1. Initialize your mysql services.
2. Execute the following sql scripts to set up the databases.
   - test_files\schema.sql
3. Update the following configuration files, you need to modify the connection string for databases.
   - test_files\DBConnectionFactory.xml
   - test_files\AccuracyTestConfig.xml
   - test_files\failuretests\config.xml
   - test_files\stresstests\config.xml
