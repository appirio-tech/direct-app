INSERT INTO tc_direct_project (project_id ,name ,description ,user_id ,create_date ,modify_date) VALUES (project_sequence.NEXTVAL, 'Project #1', 'Project Description #1', 132456, CURRENT, CURRENT);
INSERT INTO tc_direct_project (project_id ,name ,description ,user_id ,create_date ,modify_date) VALUES (project_sequence.NEXTVAL, 'Project #2', 'Project Description #2', 132456, CURRENT, CURRENT);
INSERT INTO tc_direct_project (project_id ,name ,description ,user_id ,create_date ,modify_date) VALUES (project_sequence.NEXTVAL, 'Project Competition #3', 'Project Description #3', 132456, CURRENT, CURRENT);
INSERT INTO competition (competition_id ,project_id) VALUES (competition_sequence.NEXTVAL, project_sequence.CURRVAL);

INSERT INTO tc_direct_project (project_id ,name ,description ,user_id ,create_date ,modify_date) VALUES (project_sequence.NEXTVAL, 'Project #4', 'Project Description #4', 132458, CURRENT, CURRENT);
INSERT INTO tc_direct_project (project_id ,name ,description ,user_id ,create_date ,modify_date) VALUES (project_sequence.NEXTVAL, 'Project #5', 'Project Description #5', 132458, CURRENT, CURRENT);
INSERT INTO tc_direct_project (project_id ,name ,description ,user_id ,create_date ,modify_date) VALUES (project_sequence.NEXTVAL, 'Project #6', 'Project Description #6', 132458, CURRENT, CURRENT);
INSERT INTO tc_direct_project (project_id ,name ,description ,user_id ,create_date ,modify_date) VALUES (project_sequence.NEXTVAL, 'Project Competition #7', 'Project Description #7', 132458, CURRENT, CURRENT);
INSERT INTO competition (competition_id ,project_id) VALUES (competition_sequence.NEXTVAL, project_sequence.CURRVAL);
