FROM openjdk:7-alpine3.7

RUN apk add --update bash unzip tar wget

WORKDIR /data

RUN ["wget", "-O", "/data/jboss-4.2.3.zip", "http://downloads.sourceforge.net/project/jboss/JBoss/JBoss-4.2.3.GA/jboss-4.2.3.GA.zip?r=http%3A%2F%2Fsourceforge.net%2Fprojects%2Fjboss%2Ffiles%2FJBoss%2FJBoss-4.2.3.GA"]

RUN ["unzip", "/data/jboss-4.2.3.zip"]

ENV JBOSS_HOME /data/jboss-4.2.3.GA

ADD ./start-jboss.sh /data/jboss-4.2.3.GA/bin/
ADD ./TC.prod.ldap.keystore /data/jboss-4.2.3.GA/bin/

ADD ./ear-web/target/direct.ear /data/jboss-4.2.3.GA/server/default/deploy/direct.ear
ADD ./ear-web/target/conf/ /data/jboss-4.2.3.GA/server/default/conf/
ADD ./ear-web/target/direct-ds.xml /data/jboss-4.2.3.GA/server/default/deploy/
ADD ./jboss_files/lib /data/jboss-4.2.3.GA/server/default/lib
ADD ./lib/third_party/jwt/commons-codec-1.9.jar /data/jboss-4.2.3.GA/server/default/lib/commons-codec.jar

ADD ./lib/third_party/aws-java-sdk/aws-java-sdk-1.0.004.jar /data/jboss-4.2.3.GA/server/default/lib/
ADD ./lib/third_party/informix/ifxjdbc.jar /data/jboss-4.2.3.GA/server/default/lib/
ADD ./lib/third_party/informix/ifxjdbcx.jar /data/jboss-4.2.3.GA/server/default/lib/
ADD ./lib/third_party/jboss-cache/jboss-cache-jdk50.jar /data/jboss-4.2.3.GA/server/default/lib/
ADD ./lib/third_party/jboss-cache/jgroups.jar /data/jboss-4.2.3.GA/server/default/lib/

RUN mkdir /data/temp_files
RUN rm /data/jboss-4.2.3.zip

CMD ["/data/jboss-4.2.3.GA/bin/start-jboss.sh"]
