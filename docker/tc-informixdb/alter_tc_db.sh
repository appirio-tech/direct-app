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

echo "INSERT INTO tc_direct_project(project_id, name, description, user_id, fixed_bug_contest_fee, percentage_bug_contest_fee, create_date, modify_date, project_status_id, project_forum_id, direct_project_type_id, direct_project_category_id, completion_date, demand_work_id) VALUES (535, 'IWP web site', 'iwikiphone website project', 132456, null, null, '2009-02-17 05:27:39.0', '2013-08-13 12:20:36.0', 1, null, null, null, null, null)" | dbaccess corporate_oltp > /dev/null 2>&1
echo "INSERT INTO tc_direct_project(project_id, name, description, user_id, fixed_bug_contest_fee, percentage_bug_contest_fee, create_date, modify_date, project_status_id, project_forum_id, direct_project_type_id, direct_project_category_id, completion_date, demand_work_id) VALUES (21, 'Cockpit Test Project 1', 'Cockpit Test Project 1', 132456, null, null, '2008-07-14 17:39:38.0', '2013-08-13 12:20:36.0', 2, null, null, null, null, null)" | dbaccess corporate_oltp > /dev/null 2>&1
echo "INSERT INTO tc_direct_project(project_id, name, description, user_id, fixed_bug_contest_fee, percentage_bug_contest_fee, create_date, modify_date, project_status_id, project_forum_id, direct_project_type_id, direct_project_category_id, completion_date, demand_work_id) VALUES (336, 'TC Developments TCO DataShot', 'TC Developments TCO DataShot', 132456, null, null, '2008-11-13 13:12:38.0', '2013-08-13 12:20:36.0', 1, null, null, null, null, null)" | dbaccess corporate_oltp > /dev/null 2>&1
echo "INSERT INTO tc_direct_project(project_id, name, description, user_id, fixed_bug_contest_fee, percentage_bug_contest_fee, create_date, modify_date, project_status_id, project_forum_id, direct_project_type_id, direct_project_category_id, completion_date, demand_work_id) VALUES (337, 'TopCoder Trophies', 'TopCoder Trophies', 132456, null, null, '2008-11-13 14:34:28.0', '2013-08-13 12:20:36.0', 5, null, null, null, null, null)" | dbaccess corporate_oltp > /dev/null 2>&1
echo "INSERT INTO tc_direct_project(project_id, name, description, user_id, fixed_bug_contest_fee, percentage_bug_contest_fee, create_date, modify_date, project_status_id, project_forum_id, direct_project_type_id, direct_project_category_id, completion_date, demand_work_id) VALUES (24, 'Truveo Developer Challenge', '$100,000 Truveo Developer Challenge, Powered by TopCoder. Ends 7/21', 132456, null, null, '2008-07-14 21:17:13.0', '2013-08-13 12:20:36.0', 1, null, null, null, null, null)" | dbaccess corporate_oltp > /dev/null 2>&1

echo "INSERT INTO user_permission_grant(user_permission_grant_id, user_id, resource_id, permission_type_id, is_studio) VALUES (1, 132456, 535, 3, 1)" | dbaccess corporate_oltp > /dev/null 2>&1
echo "INSERT INTO user_permission_grant(user_permission_grant_id, user_id, resource_id, permission_type_id, is_studio) VALUES (2, 132456, 21, 1, 1)" | dbaccess corporate_oltp > /dev/null 2>&1
echo "INSERT INTO user_permission_grant(user_permission_grant_id, user_id, resource_id, permission_type_id, is_studio) VALUES (3, 132456, 336, 1, 1)" | dbaccess corporate_oltp > /dev/null 2>&1
echo "INSERT INTO user_permission_grant(user_permission_grant_id, user_id, resource_id, permission_type_id, is_studio) VALUES (4, 132456, 337, 3, 1)" | dbaccess corporate_oltp > /dev/null 2>&1
echo "INSERT INTO user_permission_grant(user_permission_grant_id, user_id, resource_id, permission_type_id, is_studio) VALUES (5, 132456, 24, 3, 1)" | dbaccess corporate_oltp > /dev/null 2>&1

echo "INSERT INTO project(project_id,project_status_id,project_category_id,create_user,create_date,modify_user,modify_date,tc_direct_project_id,project_sub_category_id) VALUES (30008097,3,14,'22773727','2009-10-24 11:33:44.000','251989','2011-08-31 09:01:59.000',535,NULL)" | dbaccess tcs_catalog > /dev/null 2>&1
echo "INSERT INTO project(project_id,project_status_id,project_category_id,create_user,create_date,modify_user,modify_date,tc_direct_project_id,project_sub_category_id) VALUES (30021308,7,20,'22773727','2009-02-20 08:00:00.000','22773727','2014-08-20 00:00:00.000',535,NULL)" | dbaccess tcs_catalog > /dev/null 2>&1
echo "INSERT INTO project(project_id,project_status_id,project_category_id,create_user,create_date,modify_user,modify_date,tc_direct_project_id,project_sub_category_id) VALUES (30020799,3,17,'279551','2008-07-14 17:05:00.000','279551','2014-08-20 00:00:00.000',21,NULL)" | dbaccess tcs_catalog > /dev/null 2>&1
echo "INSERT INTO project(project_id,project_status_id,project_category_id,create_user,create_date,modify_user,modify_date,tc_direct_project_id,project_sub_category_id) VALUES (30020800,3,17,'279551','2008-07-14 17:08:00.000','279551','2014-08-20 00:00:00.000',21,NULL)" | dbaccess tcs_catalog > /dev/null 2>&1
echo "INSERT INTO project(project_id,project_status_id,project_category_id,create_user,create_date,modify_user,modify_date,tc_direct_project_id,project_sub_category_id) VALUES (30020828,2,16,'279551','2008-10-08 10:53:00.000','279551','2014-08-20 00:00:00.000',21,NULL)" | dbaccess tcs_catalog > /dev/null 2>&1
echo "INSERT INTO project(project_id,project_status_id,project_category_id,create_user,create_date,modify_user,modify_date,tc_direct_project_id,project_sub_category_id) VALUES (30054904,2,38,'22838965','2019-05-06 11:23:24.000','22838965','2019-05-06 11:23:24.000',21,NULL)" | dbaccess tcs_catalog > /dev/null 2>&1
echo "INSERT INTO project(project_id,project_status_id,project_category_id,create_user,create_date,modify_user,modify_date,tc_direct_project_id,project_sub_category_id) VALUES (30020423,3,16,'22677383','2008-11-14 14:00:00.000','22677383','2014-08-20 00:00:00.000',336,NULL)" | dbaccess tcs_catalog > /dev/null 2>&1
echo "INSERT INTO project(project_id,project_status_id,project_category_id,create_user,create_date,modify_user,modify_date,tc_direct_project_id,project_sub_category_id) VALUES (30021083,7,16,'22677383','2008-11-14 14:00:00.000','22677383','2014-08-20 00:00:00.000',336,NULL)" | dbaccess tcs_catalog > /dev/null 2>&1
echo "INSERT INTO project(project_id,project_status_id,project_category_id,create_user,create_date,modify_user,modify_date,tc_direct_project_id,project_sub_category_id) VALUES (30020424,3,16,'22677383','2008-11-14 14:00:00.000','22677383','2014-08-20 00:00:00.000',337,NULL)" | dbaccess tcs_catalog > /dev/null 2>&1
echo "INSERT INTO project(project_id,project_status_id,project_category_id,create_user,create_date,modify_user,modify_date,tc_direct_project_id,project_sub_category_id) VALUES (30021084,7,16,'22677383','2008-11-14 14:00:00.000','22677383','2014-08-20 00:00:00.000',337,NULL)" | dbaccess tcs_catalog > /dev/null 2>&1
echo "INSERT INTO project(project_id,project_status_id,project_category_id,create_user,create_date,modify_user,modify_date,tc_direct_project_id,project_sub_category_id) VALUES (30020801,3,21,'251280','2008-07-17 14:00:00.000','251280','2014-08-20 00:00:00.000',24,NULL)" | dbaccess tcs_catalog > /dev/null 2>&1

echo "update project set tc_direct_project_id=535 where project_id > 800 and project_id <900" | dbaccess tcs_catalog > /dev/null 2>&1
myfatal $? "ALTER TABLE FAILED"

onmode -ky