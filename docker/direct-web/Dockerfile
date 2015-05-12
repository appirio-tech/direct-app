FROM nginx

COPY direct-web-nginx.conf /etc/nginx/conf.d/
COPY ssl /etc/nginx/ssl/

VOLUME /data

#CMD ["/usr/sbin/nginx"]

EXPOSE 8080
