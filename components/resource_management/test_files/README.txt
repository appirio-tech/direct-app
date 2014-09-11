1. Configure libs in build.xml
2. Run test_files/create_schema.sql in Informix
   NOTE: The target database should support transaction.
3. Change the connection parameters in test_files/Connection_Factory.xml
4. Run <ant test>