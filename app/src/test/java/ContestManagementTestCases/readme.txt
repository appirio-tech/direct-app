As currently the test suite is a standalone one(it doesn't depends on the source from the direct build), I provide a separate build.xml which can be easily run by itself.

Steps to run the tests:
1. Follow the deployment guide to deploy the application in the wiki
2. Start selenium server by "java -jar selenium-server-standalone-2.32.0.jar" from test_files
3. Run ant test

The detailed configuration can be found from "test_files/config.properties"

svn revision info:
direct: 228386
database: 83166

For the new requirement, there is no need to insert the generated testing data from the testing tool, simply run the test cases will be fine.