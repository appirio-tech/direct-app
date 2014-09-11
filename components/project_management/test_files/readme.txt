Hi, reviewers

Thanks for the review! Please take a look at the following details.

Step to run tests:
0. Before running the test, please make sure the database you are using is clean;
    You can run test_files/ClearDB.sql to clear the database;

1. Start informix and create a database (with option "WITH LOG").

2 Initialize the database with "test_files/project_management.sql".

3. Modify connection setting in "test_files/config.xml".

4. Ant test.

There are some notes for you:
1. Please use the correct old source here: http://forums.topcoder.com/?module=Thread&threadID=693781&start=0

2. You should only consider the coverage report for the new added sources, not the old sources.
This is confirmed by http://forums.topcoder.com/?module=Thread&threadID=693811&start=0

3. Please note that there is not tcDirectProjectId. So, I have to add it. Please check:
    http://forums.topcoder.com/?module=Thread&threadID=693872&start=0

4. The tests added or updated are:
    ProjectCategoryTest.java
    ProjectTest.java
    ProjectTypeTest.java
    ProjectManagerImplTest.java
    FileTypeUnitTests.java
    PrizeTypeUnitTests.java
    PrizeUnitTests.java
    ProjectStudioSpecificationUnitTests.java
    DemoTest.java
    
5. CS are updated accordingly.

6. For the code style and doc issues, they are all from the old sources. We are not going to fix this.
    Please check: http://forums.topcoder.com/?module=Thread&threadID=693743&start=0
    
    Thanks! And have a nice day!


