# SSH tunnel to informix dev database
# usage: docker run -d --name=informixdev-tunnel -v /Users/james/ssh:/ssh -p 2020:2020 -t informixdev-tunnel
FROM gliderlabs/alpine:3.2

MAINTAINER james@appirio.com

RUN apk-install openssh-client

#VOLUME ["/ssh"]

#EXPOSE 2020

CMD ["ssh", "ec2-user@54.164.99.116", "-i", "/ssh/topcoder-dev-vpc-nat.pem", "-o", "StrictHostKeyChecking=no", "-vgN", "-L", "2020:10.15.94.50:2020"]
