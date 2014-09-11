insert into project_category_lu(project_category_id) values(1);

insert into project(project_id, project_category_id) values(11, 1);
insert into project(project_id, project_category_id) values(12, 1);
insert into project(project_id, project_category_id) values(13, 1);
insert into project(project_id, project_category_id) values(14, 1);
insert into project(project_id, project_category_id) values(15, 1);
insert into project(project_id, project_category_id) values(16, 1);

insert into resource_info_type_lu(resource_info_type_id, name) values(31, 'resource_info_type_id_31');

insert into resource(resource_id) values(41);
insert into resource(resource_id) values(42);
insert into resource(resource_id) values(43);
insert into resource(resource_id) values(44);
insert into resource(resource_id) values(45);
insert into resource(resource_id) values(46);

insert into resource_info(resource_id, resource_info_type_id, value) values(41, 31, 'resource_info');
insert into resource_info(resource_id, resource_info_type_id, value) values(42, 31, 'resource_info');
insert into resource_info(resource_id, resource_info_type_id, value) values(43, 31, 'resource_info');
insert into resource_info(resource_id, resource_info_type_id, value) values(44, 31, 'resource_info');
insert into resource_info(resource_id, resource_info_type_id, value) values(45, 31, 'resource_info');
insert into resource_info(resource_id, resource_info_type_id, value) values(46, 31, 'resource_info');

insert into upload(upload_id, project_id, resource_id) values(51, 11, 41);
insert into upload(upload_id, project_id, resource_id) values(52, 12, 42);
insert into upload(upload_id, project_id, resource_id) values(53, 13, 43);
insert into upload(upload_id, project_id, resource_id) values(54, 14, 44);
insert into upload(upload_id, project_id, resource_id) values(55, 15, 45);
insert into upload(upload_id, project_id, resource_id) values(56, 16, 46);

insert into screening_status_lu(screening_status_id, name, description, create_user, create_date, modify_user, modify_date) values(61, 'Pending', 'description', 'create_user', DATE('2006-11-10'), 'modify_user', DATE('2006-11-10'));
insert into screening_status_lu(screening_status_id, name, description, create_user, create_date, modify_user, modify_date) values(62, 'Screening', 'description', 'create_user', DATE('2006-11-10'), 'modify_user', DATE('2006-11-10'));

insert into screening_task(screening_task_id, upload_id, screening_status_id, screener_id, start_timestamp, create_user, create_date, modify_user, modify_date) values(100071, 51, 62, 81, DATE('2006-11-10'), 'screening_task_create_user', DATE('2006-11-10'), 'screening_task_modify_user', DATE('2006-11-10'));
insert into screening_task(screening_task_id, upload_id, screening_status_id, screener_id, start_timestamp, create_user, create_date, modify_user, modify_date) values(100072, 52, 62, 82, DATE('2006-11-10'), 'screening_task_create_user', DATE('2006-11-10'), 'screening_task_modify_user', DATE('2006-11-10'));
insert into screening_task(screening_task_id, upload_id, screening_status_id, screener_id, start_timestamp, create_user, create_date, modify_user, modify_date) values(100073, 53, 62, 83, DATE('2006-11-10'), 'screening_task_create_user', DATE('2006-11-10'), 'screening_task_modify_user', DATE('2006-11-10'));
insert into screening_task(screening_task_id, upload_id, screening_status_id, screener_id, start_timestamp, create_user, create_date, modify_user, modify_date) values(100074, 54, 62, 84, DATE('2006-11-10'), 'screening_task_create_user', DATE('2006-11-10'), 'screening_task_modify_user', DATE('2006-11-10'));
insert into screening_task(screening_task_id, upload_id, screening_status_id, screener_id, start_timestamp, create_user, create_date, modify_user, modify_date) values(100075, 55, 62, 85, DATE('2006-11-10'), 'screening_task_create_user', DATE('2006-11-10'), 'screening_task_modify_user', DATE('2006-11-10'));

insert into response_severity_lu(response_severity_id, name, description, create_user, create_date, modify_user, modify_date) values(91, 'name', 'description', 'tc', DATE('2006-11-10'), 'modify_user', DATE('2006-11-10'));
insert into response_severity_lu(response_severity_id, name, description, create_user, create_date, modify_user, modify_date) values(92, 'name', 'description', 'tc', DATE('2006-11-10'), 'modify_user', DATE('2006-11-10'));
insert into response_severity_lu(response_severity_id, name, description, create_user, create_date, modify_user, modify_date) values(93, 'name', 'description', 'tc', DATE('2006-11-10'), 'modify_user', DATE('2006-11-10'));
insert into response_severity_lu(response_severity_id, name, description, create_user, create_date, modify_user, modify_date) values(94, 'name', 'description', 'tc', DATE('2006-11-10'), 'modify_user', DATE('2006-11-10'));
insert into response_severity_lu(response_severity_id, name, description, create_user, create_date, modify_user, modify_date) values(95, 'name', 'description', 'tc', DATE('2006-11-10'), 'modify_user', DATE('2006-11-10'));

insert into screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date) values(101, 91, 'code', 'text', 'create_user', DATE('2006-11-10'), 'modify_user', DATE('2006-11-10'));
insert into screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date) values(102, 92, 'code', 'text', 'create_user', DATE('2006-11-10'), 'modify_user', DATE('2006-11-10'));
insert into screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date) values(103, 93, 'code', 'text', 'create_user', DATE('2006-11-10'), 'modify_user', DATE('2006-11-10'));
insert into screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date) values(104, 94, 'code', 'text', 'create_user', DATE('2006-11-10'), 'modify_user', DATE('2006-11-10'));
insert into screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date) values(105, 95, 'code', 'text', 'create_user', DATE('2006-11-10'), 'modify_user', DATE('2006-11-10'));

insert into screening_result(screening_result_id, screening_task_id, screening_response_id, dynamic_response_text, create_user, create_date, modify_user, modify_date) values(111, 100071, 101, 'dynamic_response_text', 'create_user', DATE('2006-11-10'), 'modify_user', DATE('2006-11-10'));
insert into screening_result(screening_result_id, screening_task_id, screening_response_id, dynamic_response_text, create_user, create_date, modify_user, modify_date) values(112, 100071, 102, 'dynamic_response_text', 'create_user', DATE('2006-11-10'), 'modify_user', DATE('2006-11-10'));
insert into screening_result(screening_result_id, screening_task_id, screening_response_id, dynamic_response_text, create_user, create_date, modify_user, modify_date) values(113, 100071, 103, 'dynamic_response_text', 'create_user', DATE('2006-11-10'), 'modify_user', DATE('2006-11-10'));
insert into screening_result(screening_result_id, screening_task_id, screening_response_id, dynamic_response_text, create_user, create_date, modify_user, modify_date) values(114, 100071, 104, 'dynamic_response_text', 'create_user', DATE('2006-11-10'), 'modify_user', DATE('2006-11-10'));
insert into screening_result(screening_result_id, screening_task_id, screening_response_id, dynamic_response_text, create_user, create_date, modify_user, modify_date) values(115, 100071, 105, 'dynamic_response_text', 'create_user', DATE('2006-11-10'), 'modify_user', DATE('2006-11-10'));

