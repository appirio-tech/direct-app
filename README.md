direct-app
==========

Setup on VM:
* Once you get your VM, login into the VM with your private key and account 'direct'
* Back up token.properties and topcoder_global.properties in /home/direct/direct somewhere
* Delete the whole directory /home/direct/direct
* Git Clone the direct-app repo into folder /home/direct/direct.
* Copy the back up *.properties files back to /home/direct/direct
* Among the *.properties, update topcoder_global.properties, add line 'direct_service_libdir=${libdir}/tcs/ejb' after tcs_libdir property
* Make sure JDK7 is used: run 
* echo "export JAVA_HOME=/opt/jdk1.7.0_17" >> ~/.bash_profile
* echo "export PATH=/opt/jdk1.7.0_17/bin:$PATH" >> ~/.bash_profile

Setup on Local env - Refer to http://apps.topcoder.com/wiki/display/docs/TC+Direct+Setup+Guide
* Instead of using SVN, you will use the codes from this git repo

VM Info:
* http://apps.topcoder.com/wiki/display/projects/Direct+VM
* http://apps.topcoder.com/wiki/display/docs/VM+Image+2.5

Build/Compile
* run 'ant deploy'

Deploy:
* simply run 'ant deploy' to build all the components and the direct and deploy the direct to jboss

