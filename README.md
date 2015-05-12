direct-app
==========

## building
To build, download the docker build container that has all of the build dependencies. You can then run the container to build your local source code.

1. Clone the github source directory
2. Rename `token.properties.docker` to `token.properties` in the source directory
3. Rename `topcoder_global.properties.docker` to `topcoder_global.properties`
4. Unzip [jboss-4.2.3.zip](http://downloads.sourceforge.net/project/jboss/JBoss/JBoss-4.2.3.GA/jboss-4.2.3.GA.zip?r=http%3A%2F%2Fsourceforge.net%2Fprojects%2Fjboss%2Ffiles%2FJBoss%2FJBoss-4.2.3.GA%2F) in your root source directory. The build will place jboss deployment files here. It also needs some of its libraries for the build itself.
5. Download the docker build image: `docker pull build.appirio.net:5050/direct-build`
6. Run the docker container to execute a build. The format of the command is `docker run -v <source dir>:/data -t direct-build <ant target(s)>`.

   For example, `docker run -v /Users/james/dev/topcoder/direct-app:/data -t direct-build clean package-direct deploy-prod`

> NOTE: the source directory should be writeable to Docker so use a directory under `/Users/<username>`

## running locally
In this configuration, we'll run the direct app in a docker container locally but it unfortunately requires many dependencies so we'll need to run several containers and connect to the dev database. To run, follow these steps.

1. Add your IP address to the direct-app-nat security group in the topcoder-dev AWS account.
1. Add this entry to your local /etc/hosts file: `<docker ip> docker.topcoder-dev.com`. This is needed for auth integration that requires the same top level domain. You can get the docker ip with the command `boot2docker ip`
2. Download the topcoder cache server image: `docker pull build.appirio.net:5050/tc-cache`
2. Run `docker run -d --name tc-cache -t build.appirio.net:5050/tc-cache`
2. Download the direct runtime docker image: `docker pull build.appirio.net:5050/direct-app`
8. Run the direct app with the command `docker run --name=direct-app -d -v <source dir>/jboss-4.2.3.GA/server/default:/data/jboss-4.2.3.GA/server/direct --link tc-cache:tc-cache -t build.appirio.net:5050/direct-app`. `<source dir>` = source directory described above. It should contain the jboss-4.2.3.GA directory you created before.
9. Download the direct web app with the command `docker pull build.appirio.net:5050/direct-web`
1. Run the direct web app with the command `docker run -d --name=direct-web -v /Users/james/dev/topcoder/direct-app/src/web:/data -p 443:443 --link direct-app:direct-app-jboss -t build.appirio.net:5050/direct-web`

   This will start the app with an endpoint available on port 443. You can now go to https://docker.topcoder-dev.com/direct/home.action

> NOTE: the SSL certificate is self-signed as will generate a warning/error when you access the site for the first time. Just accept it and continue.

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

