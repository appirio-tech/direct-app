FROM appiriodevops/tc-database-scripts:latest

ADD alter_tc_db.sh /alter_tc_db.sh
RUN ./alter_tc_db.sh

ENTRYPOINT ["/bin/bash", "/informix_start.sh"]

# expose informix port
EXPOSE 2021