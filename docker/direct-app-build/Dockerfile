FROM centos:6

RUN yum install -y \
  java-1.6.0-openjdk-devel.x86_64 \
  unzip \
  tar

COPY grails-1.3.7.zip /root/
COPY apache-ant-1.7.1-bin.tar.gz /root/
COPY ant-libs.tgz /root/

RUN unzip -d /root /root/grails-1.3.7.zip && tar --directory=/root -xzf /root/apache-ant-1.7.1-bin.tar.gz && tar --directory=/root/apache-ant-1.7.1/lib -xzvf /root/ant-libs.tgz

# need to copy because dynamic download from repo fails
COPY json-rest-api-1.0.8.zip /root/grails-1.3.7/plugins/grails-json-rest-api-1.0.8.zip

ENV JAVA_HOME /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.35.x86_64
ENV GRAILS_HOME /root/grails-1.3.7
ENV ANT_HOME /root/apache-ant-1.7.1
ENV PATH $JAVA_HOME/bin:$GRAILS_HOME/bin:$ANT_HOME/bin:$PATH

VOLUME /data

WORKDIR /data

# ant dist-backend
ENTRYPOINT ["/root/apache-ant-1.7.1/bin/ant"]

