#!/bin/bash

# need to bind to an address accessible outside
$JAVA_HOME/bin/java -server -Djava.endorsed.dirs=$JBOSS_HOME/lib/endorsed -classpath $JBOSS_HOME/bin/run.jar:$JAVA_HOME/lib/tools.jar org.jboss.Main -c cache -b `hostname --ip-address`
