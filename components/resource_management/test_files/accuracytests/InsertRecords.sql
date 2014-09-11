insert into project(project_id) values (1); 
insert into project(project_id) values (2);
insert into project(project_id) values (3);
insert into project(project_id) values (4);

insert into phase_type_lu (phase_type_id) values(1);
insert into phase_type_lu (phase_type_id) values(2);
insert into phase_type_lu (phase_type_id) values(3);
insert into phase_type_lu (phase_type_id) values(4);

insert into project_phase (project_phase_id, project_id, phase_type_id) values(1,1,1);
insert into project_phase (project_phase_id, project_id, phase_type_id) values(2,2,2);
insert into project_phase (project_phase_id, project_id, phase_type_id) values(3,3,3);
insert into project_phase (project_phase_id, project_id, phase_type_id) values(4,4,4);

insert into notification_type_lu(notification_type_id, name, description, create_user, create_date, modify_user, modify_date) values 
(1, 'type#1', 'accuracytest', 'tc', CURRENT, 'tc', CURRENT);

insert into notification_type_lu(notification_type_id, name, description, create_user, create_date, modify_user, modify_date) values 
(2, 'type#2', 'accuracytest', 'tc', CURRENT, 'tc', CURRENT);

insert into notification_type_lu(notification_type_id, name, description, create_user, create_date, modify_user, modify_date) values 
(3, 'type#3', 'accuracytest', 'tc', CURRENT, 'tc', CURRENT);

insert into resource_role_lu(resource_role_id, phase_type_id, name, description,
create_user, create_date, modify_user, modify_date) values 
(1, 1, 'role#1', 'accuracytest', 'tc', CURRENT, 'tc', CURRENT);

insert into resource_role_lu(resource_role_id, phase_type_id, name, description,
create_user, create_date, modify_user, modify_date) values 
(2, 2, 'role#2', 'accuracytest', 'tc', CURRENT, 'tc', CURRENT);

insert into resource_role_lu(resource_role_id, phase_type_id, name, description,
create_user, create_date, modify_user, modify_date) values 
(3, 3, 'role#3', 'accuracytest', 'tc', CURRENT, 'tc', CURRENT);

insert into resource_role_lu(resource_role_id, phase_type_id, name, description,
create_user, create_date, modify_user, modify_date) values 
(4, 4, 'role#4', 'accuracytest', 'tc', CURRENT, 'tc', CURRENT);

