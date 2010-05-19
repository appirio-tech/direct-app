#!/bin/bash

echo ------------------------------
echo Stopping Cockpit JBoss server
echo Press y to confirm stopping
echo ------------------------------
./kill.sh

echo delete old json jar
rm -v /tmp/lib/third_party/struts/json-lib-2.1.jar

echo -------------------------------------------------------
echo Creating development distribution environment for
echo TC Direct System application
echo -------------------------------------------------------
mkdir -pv /home/cockpit/direct
cd /home/cockpit/direct
cp -rv /tmp/conf .
cp -rv /tmp/docs .
cp -rv /tmp/lib .
cp -rv /tmp/src .
cp /tmp/*.properties .
cp /tmp/*.xml .
cp /tmp/*.version .


echo ----------------------------------------
echo Copying static resources to Apache HTTP
echo server for hosting
echo ----------------------------------------
mkdir -pv /mnt/apache/tcdocs/images
mkdir -pv /mnt/apache/tcdocs/css
mkdir -pv /mnt/apache/tcdocs/scripts
cp -Rv /home/cockpit/direct/src/web/images/* /mnt/apache/tcdocs/images
cp -Rv /home/cockpit/direct/src/web/css/* /mnt/apache/tcdocs/css
cp -Rv /home/cockpit/direct/src/web/scripts/* /mnt/apache/tcdocs/scripts

echo -----------------------------------------------
echo Deploying Security EAR application
echo -----------------------------------------------
cd /home/cockpit/direct
#security.ear is deployed already on vm, skip this step
#ant deploy-security

echo -----------------------------------------------
echo Building and deploying Cockpit web application
echo -----------------------------------------------
cd /home/cockpit/direct
ant deploy

echo ------------------------------
echo Starting Cockpit JBoss server
echo ------------------------------
cd /home/cockpit/jboss-4.2.3.GA/bin
./start.sh

