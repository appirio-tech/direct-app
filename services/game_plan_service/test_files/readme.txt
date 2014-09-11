
1. Setup the database locally. test_files/informix, contains the scripts to setup the database locally. To successfully setup the database locally, you should
1.1 If you don't have a big enough DBSpace, please reinstall your informix with custom wizzard or create a new 'ol_topcoder' (this can easy your config way) server instance by specifying the storage size explicitly, to have a big DBSpace, I suggest 2G space.
1.2 AGS Server Studio from IBM website can be used to help you doing database related operations.
1.3 Modify "CREATE DATABASE common_oltp IN datadbs WITH BUFFERED LOG;" like statements in the scripts file to use your created DBSpace.
1.4 The script provided by PM is not totally executable, I have fixed most of them, but there may be some minor issue remains, if you use AGS Server Studio, you can do like stop at the broken place.
and fix it and continue (Execute From the Cursor). Or you can request VM from PM. You should first setup common_oltp.
1.5 after database is setup, run test_files/testData.sql to insert the test data used for test cases of this component.

2. update hibernate_software.cfg.xml, hibernate_studio.cfg.xml, META-INF/informix-ds.xml under test_files directory to connect the informix database.
3. The three entities and its tests are put in cockpit_facade_Util directory, since there is a build dependency problem with LoginUtils class, it is removed, as it will not affect this component. see http://forums.topcoder.com/?module=Thread&threadID=675862&start=0.
4. Please update settings in build-dependencies.xml file. and in build-override.xml, a new task ('test.setup') is defined to build and package the EAR file and deploy to JBoss. You should first run it, and start JBoss.
5. JBoss 4.2.3 is used for this component.
6. if your JBoss is not run in the locally environment, please modify test_files/jndi.properties.
7. a copy of cockpit_facade_util.jar and security_manager.jar are put in test_files directory, in case you don't have it.
8. The sqls used in this component is updated to have separate names, or the call will not return the desired data, it is different from directly run the sql in commandline.
9. EAR file should be deployed to JBoss and JBoss should be started, before run the test cases.


change db information in test_files/accuracy/db.properties and test_files/failure/*.xml