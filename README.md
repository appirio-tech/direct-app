direct-app
==========

## building
To build, download the docker build container that has all of the build dependencies. You can then run the container to build your local source code. 

1. Clone the github source directory
2. Rename `token.properties.docker` to `token.properties` in the source directory
3. Rename `topcoder_global.properties.docker` to `topcoder_global.properties`
4. Unzip [jboss-4.2.3.zip](http://downloads.sourceforge.net/project/jboss/JBoss/JBoss-4.2.3.GA/jboss-4.2.3.GA.zip?r=http%3A%2F%2Fsourceforge.net%2Fprojects%2Fjboss%2Ffiles%2FJBoss%2FJBoss-4.2.3.GA%2F) in your root source directory. The build will place jboss deployment files here. It also needs some of its libraries for the build itself.
5. Run the docker container to execute a build. The format of the command is `docker run --rm=true -v <source dir>:/data -t appiriodevops/tc-direct-app-build <ant target(s)>`.
Make sure `base` is `/data` in `topcoder_global.properties`.

   For example, `docker run --rm=true -v /Users/james/dev/topcoder/direct-app:/data -t appiriodevops/tc-direct-app-build clean package-direct deploy-prod`

> NOTE: the source directory should be writeable to Docker so use a directory under `/Users/<username>`

## running locally
In this configuration, we'll run the direct app in a docker container locally but it unfortunately requires many dependencies so we'll need to run several containers and connect to the dev database. To run, follow these steps.

> NOTE: it is assumed you are running with the latest Docker toolbox and Docker compose (1.5+)

1. Add this entry to your local /etc/hosts file: `<docker ip> docker.topcoder-dev.com`. This is needed for auth integration that requires the same top level domain. You can get the docker ip with the command `docker-machine ip default`
2. Set the following environment variables:
* TC_DEV_NAT_DIR : Local directory containing the pem file for accessing the dev NAT instance (used to create a tunnel to the dev informix instances)
* TC_DIRECT_SRC_HOME : Local directory for the root direct-app directory
3. Run `docker-compose up` from the `docker` subdirectory containing the `docker-compose.yml` file


   This will start the app with an endpoint available on port 443. You can now go to https://docker.topcoder-dev.com/direct/enterpriseDashboard/activeContests.action

> NOTE: the SSL certificate is self-signed as will generate a warning/error when you access the site for the first time. Just accept it and continue.

---

## Test Users

direct_user/topcoder2001  (Use this user to login to Direct and create challenges in the Topcoder DEV environment. You can alsoo use this user to manipulate challenges in Online Review).

## **old** instructions

Setup on Local env - Refer to http://apps.topcoder.com/wiki/display/docs/TC+Direct+Setup+Guide
* Instead of using SVN, you will use the codes from this git repo

VM Info:
* http://apps.topcoder.com/wiki/display/projects/Direct+VM
* http://apps.topcoder.com/wiki/display/docs/VM+Image+2.5

Build/Compile
* run 'ant deploy'

Deploy:
* simply run 'ant deploy' to build all the components and the direct and deploy the direct to jboss



