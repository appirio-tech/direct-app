FROM centos:6

# install tools
RUN yum install -y \
  unzip \
  wget \
  git-all

WORKDIR /root

# install jdk7

RUN wget -O /root/jdk-7u79-linux-x64.rpm --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/7u79-b15/jdk-7u79-linux-x64.rpm"
RUN rpm -ivh jdk-7u79-linux-x64.rpm

# install apache-ant
RUN wget -O /root/apache-ant-1.7.1-bin.zip http://archive.apache.org/dist/ant/binaries/apache-ant-1.7.1-bin.zip
RUN unzip /root/apache-ant-1.7.1-bin.zip

# install grails
RUN wget -O /root/grails-1.3.7.zip http://dist.springframework.org.s3.amazonaws.com/release/GRAILS/grails-1.3.7.zip
RUN unzip /root/grails-1.3.7.zip

# install jboss
RUN wget -O /root/jboss-4.2.3.zip http://downloads.sourceforge.net/project/jboss/JBoss/JBoss-4.2.3.GA/jboss-4.2.3.GA.zip?r=http%3A%2F%2Fsourceforge.net%2Fprojects%2Fjboss%2Ffiles%2FJBoss%2FJBoss-4.2.3.GA
RUN unzip /root/jboss-4.2.3.zip

# set env variables
ENV JAVA_HOME /usr/java/jdk1.7.0_79
ENV JBOSS_HOME /root/jboss-4.2.3.GA
ENV GRAILS_HOME /root/grails-1.3.7
ENV ANT_HOME /root/apache-ant-1.7.1
ENV PATH $JAVA_HOME/bin:$GRAILS_HOME/bin:$ANT_HOME/bin:$PATH

# init apache-ant
COPY ant-libs.tgz /root/
RUN tar -xzvf /root/ant-libs.tgz -C ${ANT_HOME}/lib

# init grails
COPY json-rest-api-1.0.8.zip ${GRAILS_HOME}/plugins/grails-json-rest-api-1.0.8.zip

# init jboss
COPY jboss-service.xml ${JBOSS_HOME}/server/default/conf/
COPY myserver.keystore ${JBOSS_HOME}/server/default/conf/
COPY server.xml ${JBOSS_HOME}/server/default/deploy/jboss-web.deployer/
COPY run.conf ${JBOSS_HOME}/bin/

# remove useless files
RUN rm /root/jdk-7u79-linux-x64.rpm
RUN rm /root/jboss-4.2.3.zip
RUN rm /root/apache-ant-1.7.1-bin.zip
RUN rm /root/grails-1.3.7.zip
RUN rm /root/ant-libs.tgz

# create directories configured in properties file
RUN mkdir /root/temp_files
RUN mkdir -p /root/studiofiles/submissions

# expose jboss ports
EXPOSE 8180 443

