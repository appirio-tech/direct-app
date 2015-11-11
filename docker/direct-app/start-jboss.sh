#!/bin/bash

echo "ASP API=${ASP_API_URL}"

$JAVA_HOME/bin/java -Dasp.api-base-url=${ASP_API_URL} -server -Dsun.rmi.dgc.client.gcInterval=600000 -Dsun.rmi.dgc.server.gcInterval=600000 -Djavax.net.ssl.trustStore=TC.prod.ldap.keystore -XX:+UseParallelGC -XX:ParallelGCThreads=8 -Djboss.remoting.pre_2_0_compatible=true -XX:PermSize=512m -XX:MaxPermSize=1024m -XX:+CMSPermGenSweepingEnabled -XX:+CMSClassUnloadingEnabled -Dlog4j.debug -Djava.net.preferIPv4Stack=true -Djboss.remoting.pre_2_0_compatible=true -Djava.endorsed.dirs=$JBOSS_HOME/lib/endorsed -classpath $JBOSS_HOME/bin/run.jar:$JAVA_HOME/lib/tools.jar org.jboss.Main -c direct -b 0.0.0.0
