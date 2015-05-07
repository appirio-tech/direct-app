direct-app
==========

## building
1. Clone the github source directory
2. Rename `token.properties.docker` to `token.properties` in the source directory
3. Rename `topcoder_global.properties.docker` to `topcoder_global.properties`
4. Unzip [jboss-4.2.3.zip](http://downloads.sourceforge.net/project/jboss/JBoss/JBoss-4.2.3.GA/jboss-4.2.3.GA.zip?r=http%3A%2F%2Fsourceforge.net%2Fprojects%2Fjboss%2Ffiles%2FJBoss%2FJBoss-4.2.3.GA%2F) in your root source directory. The build will place jboss deployment files here. It also needs some of its libraries for the build itself.
3. Download the docker build image: `docker pull build.appirio.net:5050/direct-build`
4. Run the docker container to execute a build. The format of the command is `docker run -v <source dir>:/data -t direct-build <ant target(s)>`. 

   For example, `docker run -v /Users/james/dev/direct-app:/data -t clean package-direct deploy-prod`

> NOTE: the source directory should be writeable to Docker so use a directory under `/Users/<username>`

## running locally
1. Create a direct VM (this will be used for an informix database)
2. Add this entry to your /etc/hosts file: `<vm ip> vm.cloud.topcoder.com`
3. Download the direct runtime image: `docker pull build.appirio.net:5050/docker-app-run`
4. Run the direct app with the command `docker run -p 8080:8080 --name=direct-app -d -v <source dir>/jboss-4.2.3.GA/server/default:/data/jboss-4.2.3.GA/server/direct -t direct-app-run`

   This will start the app with an endpoint available on port 8080.
---
## **old** instructions
Setup on VM:
* Once you get your VM, login into the VM with your private key and account 'direct'
* Back up token.properties and topcoder_global.properties in /home/direct/direct somewhere
* Delete the whole directory /home/direct/direct
* Git Clone the direct-app repo into folder /home/direct/direct.
* Copy the back up *.properties files back to /home/direct/direct
* Among the *.properties, update topcoder_global.properties, add line 'direct_service_libdir=${libdir}/tcs/ejb' after tcs_libdir property
* Make sure JDK7 is used: run
* + export JAVA_HOME=/opt/jdk1.7.0_17
* + export PATH=/opt/jdk1.7.0_17/bin:$PATH

Setup on Local env - Refer to http://apps.topcoder.com/wiki/display/docs/TC+Direct+Setup+Guide
* Instead of using SVN, you will use the codes from this git repo

VM Info:
* http://apps.topcoder.com/wiki/display/projects/Direct+VM
* http://apps.topcoder.com/wiki/display/docs/VM+Image+2.5

Build/Compile
* run 'ant deploy'

Deploy:
* simply run 'ant deploy' to build all the components and the direct and deploy the direct to jboss

