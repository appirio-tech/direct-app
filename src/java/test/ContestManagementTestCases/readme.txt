Steps to run the tests:
1. Follow the deployment guide to deploy the application in the wiki
2. Prepare the test data as from http://apps.topcoder.com/forums/?module=Thread&threadID=727603
3. Insert the test_files/updated.sql to database tcs_catalog
4. Start selenium server by "java -jar selenium-server.jar" from test_files
5. Run ant test


In addition, the test cases can be integrated to the current source by the following step.

1. Copy folders "lib", "src", "test_files" to the svn checkout root.
2. Replace the build.xml from the root with the one from docs folder.
3. Start selenium server by "java -jar selenium-server.jar" from test_files
4. Run ant ftest