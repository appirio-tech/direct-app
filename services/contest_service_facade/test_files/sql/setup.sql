INSERT INTO corporate_oltp:tc_direct_project (project_id ,name ,description ,user_id ,create_date ,modify_date) VALUES (corporate_oltp:project_sequence.NEXTVAL, 'Project #1', 'Project Description #1', 132456, CURRENT, CURRENT);
INSERT INTO corporate_oltp:tc_direct_project (project_id ,name ,description ,user_id ,create_date ,modify_date) VALUES (corporate_oltp:project_sequence.NEXTVAL, 'Project Competition #2', 'Project Description #2', 132456, CURRENT, CURRENT);
UPDATE studio_oltp:contest SET tc_direct_project_id = corporate_oltp:project_sequence.CURRVAL WHERE create_user_id = 132456;

INSERT INTO corporate_oltp:tc_direct_project (project_id ,name ,description ,user_id ,create_date ,modify_date) VALUES (corporate_oltp:project_sequence.NEXTVAL, 'Project #3', 'Project Description #3', 132458, CURRENT, CURRENT);
INSERT INTO corporate_oltp:tc_direct_project (project_id ,name ,description ,user_id ,create_date ,modify_date) VALUES (corporate_oltp:project_sequence.NEXTVAL, 'Project Competition #4', 'Project Description #4', 132458, CURRENT, CURRENT);
UPDATE studio_oltp:contest SET tc_direct_project_id = corporate_oltp:project_sequence.CURRVAL WHERE create_user_id = 132458;

UPDATE contest SET contest_status_id = 1, contest_detailed_status_id = 15 WHERE create_user_id IN (132456, 132458) AND contest_detailed_status_id IS NULL;

UPDATE submission_status_lu SET submission_status_desc = 'deleted' WHERE submission_status_id = 2;
INSERT INTO contest_change_history VALUES (2221, 122, CURRENT, 'heffan', 0, 'name', 'old name', 'new name');