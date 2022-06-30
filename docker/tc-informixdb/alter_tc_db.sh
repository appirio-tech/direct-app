#!/bin/bash
#
#  name:        alter_tc_db.sh:
#  description: Update Informix db schema
#

set -o pipefail

function myfatal {
	if [ "${1}" -ne 0 ] ; then
		echo "${2}" >&2
		exit $1
	fi
}

export INFORMIX_HOME="/home/informix/"
INFORMIX_HOME="${INFORMIX_HOME%/}" # Strip the trailing / (if exists)

export INFORMIX_DATA_DIR="${INFORMIX_HOME}/tcdata/"
INFORMIX_DATA_DIR="${INFORMIX_DATA_DIR%/}"

export MYINFORMIX_DBSPACE="rootdbs"

source "${INFORMIX_HOME}/.bashrc"
source "${INFORMIX_HOME}/ifx_informixoltp_tcp.env"

export HOSTNAME=${HOSTNAME:-`hostname`}

echo ">>>    Update sqlhost ..."
sudo echo "${INFORMIXSERVER}        onsoctcp        ${HOSTNAME}               sqlexec" > "${INFORMIXSQLHOSTS}"
sudo echo "${INFORMIXSERVER}_dr        drsoctcp        ${HOSTNAME}               sqlexec_dr" >> "${INFORMIXSQLHOSTS}"
sudo chown informix: $INFORMIXSQLHOSTS
sudo chmod 744 $INFORMIXSQLHOSTS

oninit -vy
myfatal $? "*** Startup of ${INFORMIXSERVER} FAILED***"
echo "*** Startup of ${INFORMIXSERVER} SUCCESS ***"

echo "alter table 'informix'.upload add (url varchar(100))" | dbaccess tcs_catalog > /dev/null 2>&1
echo "update security_user set password='7dGdrcJuCUm4M9JZLae12Q=='" | dbaccess common_oltp > /dev/null 2>&1
myfatal $? "ALTER TABLE FAILED"

onmode -ky