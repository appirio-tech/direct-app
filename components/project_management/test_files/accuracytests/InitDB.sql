INSERT INTO project_type_lu (project_type_id, name, description, create_user, create_date, modify_user, modify_date, is_generic) VALUES (1, 'More', 'More projects of this type', 'System', CURRENT, 'System', CURRENT, 1);
INSERT INTO project_type_lu (project_type_id, name, description, create_user, create_date, modify_user, modify_date, is_generic) VALUES (2, 'Less', 'Less projects of this type', 'System', CURRENT, 'System', CURRENT, 1);
INSERT INTO project_type_lu (project_type_id, name, description, create_user, create_date, modify_user, modify_date, is_generic) VALUES (3, 'No', 'No project of this type', 'System', CURRENT, 'System', CURRENT, 1);

INSERT INTO project_category_lu (project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (1, 1, 'Java', 'Java', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (2, 1, 'DotNet', 'DotNet', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (3, 2, 'C++', 'C++', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu (project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (4, 1, 'Java', 'Java', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_info_type_lu (project_info_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (1, 'name', 'name', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu (project_info_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (2, 'designer', 'designer', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu (project_info_type_id, name, description, create_user, create_date, modify_user, modify_date) VALUES (3, 'developer', 'developer', 'System', CURRENT, 'System', CURRENT);


INSERT INTO project_studio_specification (project_studio_spec_id, goals, target_audience, branding_guidelines, disliked_design_websites , other_instructions , winning_criteria, submitters_locked_between_rounds, round_one_introduction, round_two_introduction, colors  , fonts   , layout_and_size, create_user, create_date, modify_user, modify_date) VALUES (1, 'g1', 't1', 'b1', 'd1', 'o1', 'w1', 't', 'r1', 'r2', 'c1', 'f1', 'l1', 'user', current, 'user', current);

INSERT INTO project_studio_specification (project_studio_spec_id, goals, target_audience, branding_guidelines, disliked_design_websites , other_instructions , winning_criteria, submitters_locked_between_rounds, round_one_introduction, round_two_introduction, colors  , fonts   , layout_and_size, create_user, create_date, modify_user, modify_date) VALUES (2, 'g2', 't2', 'b2', 'd2', 'o2', 'w2', 't', 'r12', 'r2', 'c2', 'f2', 'l2', 'user', current, 'user', current);

INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(2, 'Inactive', 'Inactive', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(3, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(4, 'Cancelled - Failed Review', 'Cancelled - Failed Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(5, 'Cancelled - Failed Screening', 'Cancelled - Failed Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(6, 'Cancelled - Zero Submissions', 'Cancelled - Zero Submissions', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_status_lu(project_status_id, name, description, create_user, create_date, modify_user, modify_date) VALUES(7, 'Completed', 'Completed', 'System', CURRENT, 'System', CURRENT);


INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date) VALUES (1, 7, 1, 'A', CURRENT, 'System', CURRENT);
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date) VALUES (2, 1, 1, 'B', CURRENT, 'System', CURRENT);
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date) VALUES (3, 7, 2, 'C', CURRENT, 'E', CURRENT);
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date) VALUES (4, 1, 3, 'D', CURRENT, 'System', CURRENT);
INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date) VALUES (5, 3, 4, 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (1, 1, 'Configuration Manager', 'A', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (1, 2, 'isv', 'A', CURRENT, 'A', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (1, 3, 'WishingBone', 'A', CURRENT, 'A', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (2, 1, 'Project Manager', 'B', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (2, 2, 'tuenm', 'B', CURRENT, 'B', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (2, 3, 'TCSDEVELOPER', 'B', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (3, 1, 'ID Generator', 'C', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (4, 1, 'C++ Project', 'D', CURRENT, 'System', CURRENT);
INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (5, 1, 'Deleted Java Project', 'D', CURRENT, 'System', CURRENT);

INSERT INTO resource (resource_id, project_id) VALUES (1, 2);
INSERT INTO resource (resource_id, project_id) VALUES (2, 4);

INSERT INTO resource_info (resource_id, resource_info_type_id, value) VALUES (1, 1, 'administrator');
INSERT INTO resource_info (resource_id, resource_info_type_id, value) VALUES (2, 1, '999');
