DELETE FROM phase_dependency;
DELETE FROM project_phase;
DELETE FROM phase_type_lu;
DELETE FROM phase_status_lu;
DELETE FROM project;

INSERT INTO project(project_id) VALUES(1);
INSERT INTO project(project_id) VALUES(2);
INSERT INTO project(project_id) VALUES(3);

INSERT INTO phase_status_lu(phase_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Scheduled', 'Scheduled Status', 'System1', '2006-01-01 11:11:11.111', 'System2', '2006-01-01 11:11:11.222');
INSERT INTO phase_status_lu(phase_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Open', 'Open', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_status_lu(phase_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Closed', 'Closed', 'System', CURRENT, 'System', CURRENT);

INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Auto', 'Auto Phase', 'System1', '2006-01-01 11:11:11.111', 'System2', '2006-01-01 11:11:11.222');
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Manual', 'Manual Phase', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time,
  scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration,
  create_user, create_date, modify_user, modify_date)
  VALUES(1, 1, 1, 1, '2006-01-01 11:11:11.111', '2006-01-01 11:11:11.111', '2006-01-01 11:11:22.111',
  '2006-01-01 11:11:11.111', '2006-01-02 11:11:22.111', 6000,
  'reviewer', '2006-01-01 11:11:11.111', 'reviewer', '2006-01-01 11:11:11.111');
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time,
  scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration,
  create_user, create_date, modify_user, modify_date)
  VALUES(2, 1, 2, 2, '2006-01-01 11:11:11.111', '2006-01-01 11:11:11.111', '2006-01-01 11:11:22.111',
  '2006-01-01 11:11:11.111', '2006-01-02 11:11:11.111', 6000,
  'reviewer', '2006-01-01 11:11:11.111', 'reviewer', '2006-01-01 11:11:11.111');
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time,
  scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration,
  create_user, create_date, modify_user, modify_date)
  VALUES(3, 1, 2, 3, '2006-01-01 11:11:11.111', '2006-01-01 11:11:11.111', '2006-01-01 11:11:22.111',
  '2006-01-01 11:11:11.111', '2006-01-02 11:11:11.111', 6000,
  'reviewer', '2006-01-01 11:11:11.111', 'reviewer', '2006-01-01 11:11:11.111');
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time,
  scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration,
  create_user, create_date, modify_user, modify_date)
  VALUES(4, 1, 1, 1, '2006-01-01 11:11:11.111', '2006-01-01 11:11:11.111', '2006-01-01 11:11:22.111',
  '2006-01-01 11:11:11.111', '2006-01-02 11:11:11.111', 6000,
  'reviewer', '2006-01-01 11:11:11.111', 'reviewer', '2006-01-01 11:11:11.111');
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time,
  scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration,
  create_user, create_date, modify_user, modify_date)
  VALUES(5, 2, 1, 3, '2006-01-01 11:11:11.222', '2006-01-01 11:11:11.333', '2006-01-01 11:11:22.444',
  '2006-01-01 11:11:11.555', '2006-01-01 11:11:11.666', 6000,
  'reviewer-1', '2006-01-01 11:11:11.777', 'reviewer-2', '2006-01-01 11:11:11.888');
INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, fixed_start_time,
  scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration,
  create_user, create_date, modify_user, modify_date)
  VALUES(6, 2, 1, 1, NULL, '2006-01-01 11:11:11.111', '2006-01-01 11:11:22.111',
  NULL, NULL, 6000,
  'reviewer', '2006-01-01 11:11:11.111', 'reviewer', '2006-01-01 11:11:11.111');

INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
  VALUES(1, 2, 1, 0, 12000, 'reviewer-1', '2006-01-01 11:11:11.777', 'reviewer-2', '2006-01-01 11:11:11.888');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
  VALUES(2, 3, 0, 1, 24000, 'reviewer-1', '2006-01-01 11:11:11.777', 'reviewer-2', '2006-01-01 11:11:11.888');
INSERT INTO phase_dependency (dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time, create_user, create_date, modify_user, modify_date)
  VALUES(1, 4, 1, 0, 12000, 'reviewer-1', '2006-01-01 11:11:11.777', 'reviewer-2', '2006-01-01 11:11:11.888');