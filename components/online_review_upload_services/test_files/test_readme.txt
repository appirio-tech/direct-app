In order to run the tests for this component you should do the following:
1. Install AXIS 1.4 from http://ws.apache.org/axis/
2. Copy directory ${AXIS_HOME}/webapps/axis to ${TOMCAT_HOME}/webapps
3. Update the content of "fileStorageLocation" property from
   test_files/webapps/axis/WEB-INF/classes/com/cronos/onlinereview/services/uploads/upload_external_services.xml
   to match with your ${TOMCAT_HOME}/webapps/axis/WEB-INF/classes/test_files/upload/ location.
4. Copy into ${TOMCAT_HOME}/webapps/axis/WEB-INF directory the content of test_files/axis/WEB-INF/ directory.
5. Follow the instructions from http://ws.apache.org/axis/java/install.html in order to deploy the webservice.
   (See that there is a deploy.wsdd file in your test_files/ directory).

6. If your Tomcat server doesn't run on your machine or on the local port 8080 please update the value of
   END_POINT property in TestHelper.java file to match with your configuration.

7. Execute nant test.

Good luck.