1. The test cases for AbstractDbPhasePersistence, AbstractInformixPhasePersistence are included in
InformixPhasePersistenceTest, and I added the test cases in InformixPhasePersistenceTest for the added
method in those two abstract classes.

2. I added my demo in CS in green.

3. Please config the database using phase_management.sql in test_files folder.

4. Please config the DB specification in configV11.xml, db_conf.xml, DBConfig.xml, project_phase_management.jar\DBConfig.xml in test_files

5. Because the DefaultWorkdays is not serializable, but the Project uses the DefaultWorkdays, and Phase uses the Project. So the phase argument
could not be serialized and could not be used in EJB when using the remote configuration. To make the demo workable, I changed the code of
DefaultWorkdays. This will not affect the component because this component has nothing to to with Workdays. I attached the Workdays.jar in
test_files

6. To compile the test class (EJB Demo), please include jbossall-client.jar from JBOSS_HOME/client

7. I copy my build.xml in the test_files folder.

8. To run the DemoTestV11, please deploy the EJB to JBoss container. I use the JBoss 4.0.4.
a) After run the "ant", copy all the libraries and build\dist\lib\tcs\phase_management_persistence\1.1\phase_management_persistence.jar
   to JBOSS_HOME/server/default/lib, the libraries are: base_exception.jar, configuration_manager.jar, db_connection_factory.jar,
   id_generator.jar, ifxjdbc.jar, ifxjdbcx.jar, ifxlang.jar, ifxlsupp.jar, ifxsqlj.jar, ifxtools.jar, phase_management.jar, project_phases.jar, typesafe_enum.jar,
   workdays.jar 
b) After run "ant compile_tests", Copy PhaseBean.class, PhaseRemoteHome.class, PhaseRemoteObject.class from
   build\testClasses\com\topcoder\management\phase\db to
   test_files\project_phase_management.jar\com\topcoder\management\phase\db
c) Copy test_files/project_phase_management.jar folder to JBOSS_HOME/server/default/deploy
d) Start up the JBoss to deploy the EJB.
e) Run the test
