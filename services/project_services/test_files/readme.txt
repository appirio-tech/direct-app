1. In order to start ejb environment, please put Project_Services_1.1.jar to the jboss servier directory, for example: {jboss_home}\server\all\deploy\Project_Services_1.1.jar

2. version 1.0 use BaseException 1.0, and version 1.1 changed to use BaseException 1.1.
The Exceptions' "Thread Safety" should be "not thread safe since the parent is not thread
safe", I have fixed all of them.

3. And I use ExceptionUtils to check object not null and string not null or empty. I think it is a better choice than use self-defined check methods.

4. the follow class need be Serializable:
 1) java.io.NotSerializableException: com.topcoder.date.workdays.DefaultWorkdays
 2) java.io.NotSerializableException: com.topcoder.util.config.DefaultConfigManager
 3) java.io.NotSerializableException: com.topcoder.util.config.Namespace
 4) java.io.NotSerializableException: com.topcoder.util.config.XMLConfigProperties
 5) java.io.NotSerializableException: com.topcoder.search.builder.filter.Filter

5. all the libs can be found in the Project_Services_1.1.jar/lib directory, you can use them for unit test. And I also put the build.xml and you can use it directly.

6. I have do many refactoring on the version 1.0.

7. all the methods added in version 1.1 have be test completely, both the line and branch coverage are 100%.

^-^ Merry Christmas and Happy New Year ^_^

Yours sincerely,
TCSDEVELOPER