direct-app
==========

Setup on VM:
* Once you get your VM, login into the VM with your private key and account 'direct'
* Back up token.properties and topcoder_global.properties in /home/direct/direct somewhere
* Delete the whole directory /home/direct/direct
* Git Clone the direct-app repo into folder /home/direct/direct.
* Copy the back up *.properties files back to /home/direct/direct

Setup on Local env - Refer to http://apps.topcoder.com/wiki/display/docs/TC+Direct+Setup+Guide
* Instead of using SVN, you will use the codes from this git repo

VM Info:
* http://apps.topcoder.com/wiki/display/projects/Direct+VM
* http://apps.topcoder.com/wiki/display/docs/VM+Image+2.5

Build/Compile
* run 'ant'

Deploy:
* simply run 'ant deploy' to build all the components and the direct and deploy the direct to jboss

