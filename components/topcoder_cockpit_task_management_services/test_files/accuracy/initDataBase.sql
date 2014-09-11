insert into direct_project_task_status (direct_project_task_status_id, name) values (0, 'NOT_STARTED');
insert into direct_project_task_status (direct_project_task_status_id, name) values (1, 'IN_PROGRESS');
insert into direct_project_task_status (direct_project_task_status_id, name) values (2, 'WAIT_ON_DEPENDENCY');
insert into direct_project_task_status (direct_project_task_status_id, name) values (3, 'COMPLETED');

insert into direct_project_task_priority (direct_project_task_priority_id, name) values (0, 'HIGH');
insert into direct_project_task_priority (direct_project_task_priority_id, name) values (1, 'LOW');
insert into direct_project_task_priority (direct_project_task_priority_id, name) values (2, 'NORMAL');

INSERT INTO project(project_id, project_name) VALUES(1, 'project1');
INSERT INTO project(project_id, project_name) VALUES(2, 'project2');


INSERT INTO user (user_id, email_address,handle) VALUES(1, '1@tc.com', 'u1');
INSERT INTO user (user_id, email_address,handle) VALUES(2, '2@tc.com', 'u2');

insert into direct_project_task_list (create_date, create_user, modify_date, modify_user, name, notes, project_id, is_active, is_default, direct_project_task_list_id) values (current, 'u1',  null,null,'n0','n1', 1, 1, 1, 1);
insert into direct_project_task_list_permitted_user(direct_project_task_list_id, user_id) values (1, 1);
insert into direct_project_task_list_permitted_user(direct_project_task_list_id, user_id) values (1, 2);