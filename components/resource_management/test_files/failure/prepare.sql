-- List of requirements before running failure tests:
-- 1) At least one resource role with name as 'failuretests'
-- 2) At least one notification type with name as 'failuretests'
-- 3) At least one notification with external_ref_id as 1
-- 4) At least one notification with phase_id as 1

-- Following is a sample configuration.
-- If it confict with your system, you can change it freely as long as above requirements are met.

-- insert records into table project.
insert into project (project_id) values (1);

-- insert records into table phase_type_lu.
insert into phase_type_lu (phase_type_id) values(1);

-- insert records into table phase.
insert into project_phase (project_phase_id, project_id, phase_type_id) values(1,1,1);

-- insert records into table submission.
insert into submission (submission_id) values(121);
insert into submission (submission_id) values(1200);

-- resource role with name = failuretests.
insert into resource_role_lu (resource_role_id, phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
values(1, 1, 'failuretests', 'description', 'create_user', '2006-01-01 11:11:11.111', 'modify_user', '2006-01-01 11:11:11.111');

-- notification type with name = failuretests
insert into notification_type_lu (notification_type_id, name, description, create_user, create_date, modify_user, modify_date)
values(1, 'failuretests', 'description', 'create_user', '2006-01-01 11:11:11.111', 'modify_user', '2006-01-01 11:11:11.111');

-- notification with external_ref_id = 1
insert into notification (project_id, external_ref_id, notification_type_id, create_user, create_date, modify_user, modify_date)
values(1, 1, 1, 'create_user', '2006-01-01 11:11:11.111', 'modify_user', '2006-01-01 11:11:11.111');

-- resource with phase_id = 1
insert into resource (resource_id, resource_role_id, project_id, project_phase_id, create_user, create_date, modify_user, modify_date)
values(1, 1, 1, 1, 'create_user', '2006-01-01 11:11:11.111', 'modify_user', '2006-01-01 11:11:11.111');