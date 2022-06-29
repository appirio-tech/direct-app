direct-app
==========

## building
To build, you should have installed jdk7 and maven3

1. Run `./install-third-dep.sh` to install all dependence
2. Rename `token.properties.docker` to `token.properties` in the source directory
3. Run `mvn clean package -DskipTests=true` to build ear package

> NOTE: the ear file is in the ear-web/target/

## running locally
In this configuration, we'll run the direct app in a docker container locally but it unfortunately requires many dependencies so we'll need to run several containers and connect to the dev database. To run, follow these steps.

> NOTE: it is assumed you are running with the latest Docker toolbox and Docker compose (1.5+)

1. Add this entry to your local /etc/hosts file: `<docker ip> docker.topcoder-dev.com`. This is needed for auth integration that requires the same top level domain. You can get the docker ip with the command `docker-machine ip default`
2. Set the following environment variables:
* TC_DEV_NAT_DIR : Local directory containing the pem file for accessing the dev NAT instance (used to create a tunnel to the dev informix instances)
* TC_DIRECT_SRC_HOME : Local directory for the root direct-app directory
3. Run `docker-compose up` from the `root dir`  containing the `docker-compose.yml` file


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


## Summary of the changes for maven

1. Change `src/java/main` to `src/main/java`, `src/java/test` to `src/test/java` for all java modules
2. Add `install-third-dep.sh` to install all dependence
3. Add `pom.xml` to every java module
4. Using `maven build` and `maven-resources-plugin` to build target file
5. Move `root/src` to `root/app/src` to build `direct.war` file
6. Add `ear-web` to build `direct.ear` and other packages that need to move to jboss
7. Add `Dockerfile`, `docker-compose.yml` to root dir

