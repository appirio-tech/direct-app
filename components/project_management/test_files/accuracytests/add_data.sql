DELETE FROM id_sequences where name = 'project_id_seq';
DELETE FROM id_sequences where name = 'project_audit_id_seq';

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('project_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('project_audit_id_seq', 1, 20, 0);

INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Inactive', 'Inactive', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Cancelled - Failed Review', 'Cancelled - Failed Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Cancelled - Failed Screening', 'Cancelled - Failed Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(6, 'Cancelled - Zero Submissions', 'Cancelled - Zero Submissions', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(7, 'Completed', 'Completed', 'System', CURRENT, 'System', CURRENT);

        INSERT INTO project_type_lu(project_type_id, name, description, 
        create_user, create_date, modify_user, modify_date, is_generic) VALUES (1, 
        'Topcoder', 'Topcoder Component', 'topcoder', CURRENT, 'topcoder', 
        CURRENT, 1); 
        INSERT INTO project_type_lu(project_type_id, name, 
        description, create_user, create_date, modify_user, modify_date, is_generic) VALUES 
        (2, 'Customer', 'Customer Component', 'topcoder', CURRENT, 'topcoder', 
        CURRENT, 1);

        INSERT INTO audit_action_type_lu(audit_action_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES 
        (1, 'name', 'desc', 'topcoder', CURRENT, 'topcoder', CURRENT);


        INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (1, 1, '.Net', '.NET Component', 'topcoder', CURRENT, 'topcoder', CURRENT);
        INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (2, 1, 'Java', 'JAVA Component', 'topcoder', CURRENT, 'topcoder', CURRENT);
        INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (3, 2, 'Customer .Net', 'Customer .NET Component', 'topcoder', CURRENT, 'topcoder', CURRENT);
        INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (4, 2, 'Customer Java', 'Customer JAVA Component', 'topcoder', CURRENT, 'topcoder', CURRENT);


        INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
                VALUES (1, 'property 1', 'project property 1', 'topcoder', CURRENT, 'topcoder', CURRENT);
        INSERT INTO project_info_type_lu (project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
                VALUES (2, 'property 2', 'project property 2', 'topcoder', CURRENT, 'topcoder', CURRENT);
        INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
                VALUES (3, 'property 3', 'project property 3', 'topcoder', CURRENT, 'topcoder', CURRENT);
        INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
                VALUES (4, 'property 4', 'project property 4', 'topcoder', CURRENT, 'topcoder', CURRENT);

INSERT INTO project_studio_specification (project_studio_spec_id, goals, target_audience, branding_guidelines, disliked_design_websites ,
	other_instructions , winning_criteria, submitters_locked_between_rounds, round_one_introduction, round_two_introduction, colors  , fonts   ,
	layout_and_size, create_user, create_date, modify_user, modify_date) VALUES (1, 'H', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'c1', 'f1', 'l1', 'user', CURRENT, 'topcoder', CURRENT);
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date, project_studio_spec_id) VALUES (101, 7, 1, 'A', CURRENT, 'System', CURRENT, 1);
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date, project_studio_spec_id) VALUES (102, 7, 1, 'A', CURRENT, 'System', CURRENT, 1);


INSERT INTO prize_type_lu (prize_type_id, description) values (1, 'Development');
INSERT INTO prize_type_lu (prize_type_id, description) values (2, 'Design');
INSERT INTO prize_type_lu (prize_type_id, description) values (3, 'Srm');