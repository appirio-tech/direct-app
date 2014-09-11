INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('resource_id_seq', 1, 1, 0); 
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('notification_type_id_seq', 1, 1, 0); 
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('resource_role_id_seq', 1, 1, 0);
  
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('project_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('project_audit_id_seq', 1, 1, 0);


INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('phase_id_seq', 1000, 1, 0);

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('upload_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('upload_type_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('upload_status_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('submission_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('submission_status_id_seq', 1, 1, 0);

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('scorecard_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('scorecard_group_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('scorecard_section_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('scorecard_question_id_seq', 1, 1, 0);

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('screening_task_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('screening_result_id_seq', 1, 1, 0);

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('review_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('review_item_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('review_comment_id_seq', 1, 1, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('review_item_comment_id_seq', 1, 1, 0);

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('team_id_seq', 1, 1, 0);

insert into audit_action_type_lu values (1, "Create", "Create", "System", current, "System", current);
insert into audit_action_type_lu values (2, "Delete", "Delete", "System", current, "System", current);
insert into audit_action_type_lu values (3, "Update", "Update", "System", current, "System", current);

INSERT INTO project_type_lu(project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Component', 'Component', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_type_lu(project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Application', 'Application', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 1, 'Design', 'Design', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 1, 'Development', 'Development', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 1, 'Security', 'Security', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 1, 'Process', 'Process', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 1, 'Testing Competition', 'Testing Competition', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(6, 2, 'Specification', 'Specification', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(7, 2, 'Architecture', 'Architecture', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(8, 2, 'Component Production', 'Component Production', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(9, 2, 'Quality Assurance', 'Quality Assurance', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(10, 2, 'Deployment', 'Deployment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(11, 2, 'Security', 'Security', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(12, 2, 'Process', 'Process', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(13, 2, 'Testing Competition', 'Testing Competition', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_category_lu(project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(14, 2, 'Assembly Competition', 'Assembly Competition', 'System', CURRENT, 'System', CURRENT);

INSERT INTO scorecard_type_lu(scorecard_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Screening', 'Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO scorecard_type_lu(scorecard_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Review', 'Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO scorecard_type_lu(scorecard_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Approval', 'Approval', 'System', CURRENT, 'System', CURRENT);

INSERT INTO scorecard_status_lu(scorecard_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT);
INSERT INTO scorecard_status_lu(scorecard_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Inactive', 'Inactive', 'System', CURRENT, 'System', CURRENT);
INSERT INTO scorecard_status_lu(scorecard_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT);

INSERT INTO scorecard_question_type_lu(scorecard_question_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Scale (1-4)', 'Scale (1-4)', 'System', CURRENT, 'System', CURRENT);
INSERT INTO scorecard_question_type_lu(scorecard_question_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Scale (1-10)', 'Scale (1-10)', 'System', CURRENT, 'System', CURRENT);
INSERT INTO scorecard_question_type_lu(scorecard_question_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Test Case', 'Test Case', 'System', CURRENT, 'System', CURRENT);
INSERT INTO scorecard_question_type_lu(scorecard_question_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Yes/No', 'Yes/No', 'System', CURRENT, 'System', CURRENT);
INSERT INTO scorecard_question_type_lu(scorecard_question_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Dynamic', 'Dynamic', 'System', CURRENT, 'System', CURRENT);

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

INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'External Reference ID', 'External Reference ID', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Component ID', 'Component ID', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Version ID', 'Version ID', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Developer Forum ID', 'Developer Forum ID', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Root Catalog ID', 'Root Catelog ID', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(6, 'Project Name', 'Project Name', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(7, 'Project Version', 'Project Version', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(8, 'SVN Module', 'SVN Module', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(9, 'Autopilot Option', 'Autopilot Option', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(10, 'Status Notification', 'Status Notification', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(11, 'Timeline Notification', 'Timeline Notification', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(12, 'Public', 'Public', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(13, 'Rated', 'Rated', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(14, 'Eligibility', 'Eligibility', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(15, 'Payments Required', 'Payments Required', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(16, 'Payments', 'Payments', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(17, 'Notes', 'Notes', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(18, 'Deactivated Timestamp', 'Deactivated Timestamp', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(19, 'Deactivated Phase', 'Deactivated Phase', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(20, 'Deactivated Reason', 'Deactivated Reason', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(21, 'Completion Timestamp', 'Completion Timestamp', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(22, 'Rated Timestamp', 'Rated Timestamp', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(23, 'Winner External Reference ID', 'Winner External Reference ID', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(24, 'Runner-up External Reference ID', 'Runner-up External Reference ID', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(25, 'Event Flag', 'Event Flag', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(26, 'Digital Run Flag', 'Digital Run Flag', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(27, 'AutoPilot AD Change', 'AutoPilot AD Change', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(28, 'Allow multiple submissions', 'Allow multiple submission', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(29, 'Contest Indicator', 'Whether or not this proje', 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(30, 'DR points', 'DR points to award', 'pulky', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(31,"Admin Fee","Admin Fee", 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(32,"Billing Project","Billing Project", 'System', CURRENT, 'System', CURRENT);
INSERT INTO project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(33,"Review Cost","Review Cost", 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(34, 'Confidentiality Type', 'Confidentiality Type', 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(35, 'Spec Review Cost', 'Spec Review Cost', 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(36,'First Place Cost','First Place Cost', 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(37,'Second Place Cost','Second Place Cost', 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(38,'Reliability Bonus Cost','Reliability Bonus Cost', 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(39,'Milestone Bonus Cost','Milestone Bonus Cost', 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(40,'Cost Level','Cost Level', 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(41, 'Approval Required', 'Approval Required', 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(42, 'Requires Other Fixes', 'Requires Other Fixes', 'System', CURRENT, 'System', CURRENT);
insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(43, 'Send Winner Emails', 'Send Winner Emails', 'System', CURRENT, 'System', CURRENT);

INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Registration', 'Registration', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Submission', 'Submission', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Screening', 'Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Review', 'Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Appeals', 'Appeals', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(6, 'Appeals Response', 'Appeals Response', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(7, 'Aggregation', 'Aggregation', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(8, 'Aggregation Review', 'Aggregation Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(9, 'Final Fix', 'Final Fix', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(10, 'Final Review', 'Final Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(11, 'Approval', 'Approval', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(12, 'Post-Mortem', 'Post-Mortem', 'System', CURRENT, 'System', CURRENT);

INSERT INTO phase_status_lu(phase_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Scheduled', 'Scheduled', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_status_lu(phase_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Open', 'Open', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_status_lu(phase_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Closed', 'Closed', 'System', CURRENT, 'System', CURRENT);

INSERT INTO phase_criteria_type_lu(phase_criteria_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Scorecard ID', 'Scorecard ID', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_criteria_type_lu(phase_criteria_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Registration Number', 'Registration Number', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_criteria_type_lu(phase_criteria_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Submission Number', 'Submission Number', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_criteria_type_lu(phase_criteria_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'View Response During Appeals', 'View Response During Appeals', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_criteria_type_lu(phase_criteria_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Manual Screening', 'Manual Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_criteria_type_lu(phase_criteria_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(6, 'Reviewer Number', 'Reviewer Number', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_criteria_type_lu(phase_criteria_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(7, 'Post-Mortem Reviewer Number', 'Post-Mortem Reviewer Number', 'System', CURRENT, 'System', CURRENT);  

INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (1, NULL, 'Submitter', 'Submitter', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (2, 3, 'Primary Screener', 'Primary Screener', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (3, 3, 'Screener', 'Screener', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (4, 4, 'Reviewer', 'Reviewer', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (5, 4, 'Accuracy Reviewer', 'Accuracy Reviewer', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (6, 4, 'Failure Reviewer', 'Failure Reviewer', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (7, 4, 'Stress Reviewer', 'Stress Reviewer', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (8, 7, 'Aggregator', 'Aggregator', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (9, 10, 'Final Reviewer', 'Final Reviewer', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (10, NULL, 'Approver', 'Approver', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (11, NULL, 'Designer', 'Designer', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (12, NULL, 'Observer', 'Observer', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (13, NULL, 'Manager', 'Manager', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (14, NULL, 'Copilot', 'Copilot', 'System', '2010-01-13 08:54:35.000', 'System', '2010-01-13 08:54:35.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (15, NULL, 'Client Manager', 'Client Manager', 'System', '2010-01-13 08:54:35.000', 'System', '2010-01-13 08:54:35.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (16, NULL, 'Post-Mortem Reviewer', 'Post-Mortem Reviewer', 'System', current, 'System', current);
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (1001, NULL, 'Team Captain', 'Team Captain', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (1002, NULL, 'Free Agent', 'Free Agent', 'System', '2006-11-02 20:14:24.000', 'System', '2006-11-02 20:14:24.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (1003, NULL, 'Payment Manager', 'Payment Manager', 'System', '2009-03-16 20:27:00.000', 'System', '2009-03-16 20:27:00.000');
INSERT INTO resource_role_lu(resource_role_id,phase_type_id,name,description,
create_user,create_date,modify_user,modify_date) VALUES (86, NULL, 'Deactivated', 'Deactivated', 'System', '2008-12-04 14:51:25.000', 'System', '2008-12-04 14:51:25.000');

INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'External Reference ID', 'External Reference ID', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Handle', 'Handle', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Email', 'Email', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Rating', 'Rating', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Reliability', 'Reliability', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(6, 'Registration Date', 'Registration Date', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(7, 'Payment', 'Payment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(8, 'Payment Status', 'Payment Status', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(9, 'Screening Score', 'Screening Score', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(10, 'Initial Score', 'Initial Score', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(11, 'Final Score', 'Final Score', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_info_type_lu(resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(12, 'Placement', 'Placement', 'System', CURRENT, 'System', CURRENT);

INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Submission', 'Submission', 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Test Case', 'Test Case', 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Final Fix', 'Final Fix', 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload_type_lu(upload_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Review Document', 'Review Document', 'System', CURRENT, 'System', CURRENT);

INSERT INTO upload_status_lu(upload_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT);
INSERT INTO upload_status_lu(upload_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT);

INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Failed Screening', 'Failed Manual Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Failed Review', 'Failed Review', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Completed Without Win', 'Completed Without Win', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_status_lu(submission_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT);

INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Comment', 'Comment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Recommended', 'Recommended', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Required', 'Required', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Appeal', 'Appeal', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Appeal Response', 'Appeal Response', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(6, 'Aggregation Comment', 'Aggregator Comment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(7, 'Aggregation Review Comment', 'Aggregator Comment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(8, 'Submitter Comment', 'Submitter Comment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(9, 'Final Fix Comment', 'Final Fix Comment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(10, 'Final Review Comment', 'Final Review Comment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(11, 'Manager Comment', 'Manager Comment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(12, 'Approval Review Comment', 'Approval Review Comment', 'System', CURRENT, 'System', CURRENT);
INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(13, 'Approval Review Comment - Other Fixes', 'The final product meets all requirement but requires other fixes before it can be used', 'System', CURRENT, 'System', CURRENT);

INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(1, 2, 1, 'Submission', 'Submission', 0, 0, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(2, 3, 2, 'Screening Scorecard', 'Screening Scorecard', 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(3, 3, 3, 'Screening Scorecard', 'Screening Scorecard', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(4, 4, 4, 'Review Scorecard', 'Review Scorecard', 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(5, 4, 5, 'Review Scorecard', 'Review Scorecard', 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(6, 4, 6, 'Review Scorecard', 'Review Scorecard', 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(7, 4, 7, 'Review Scorecard', 'Review Scorecard', 1, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(8, 4, 5, 'Accuracy Test Cases', 'Accuracy Test Cases', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(9, 4, 6, 'Failure Test Cases', 'Failure Test Cases', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(10, 4, 7, 'Stress Test Cases', 'Stress Test Cases', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(11, 6, 4, 'Appeal Responses', 'Appeal Responses', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(12, 6, 5, 'Appeal Responses', 'Appeal Responses', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(13, 6, 6, 'Appeal Responses', 'Appeal Responses', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(14, 6, 7, 'Appeal Responses', 'Appeal Responses', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(15, 7, 8, 'Aggregation', 'Aggregation', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(16, 8, 4, 'Aggregation Review', 'Aggregation Review', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(17, 8, 5, 'Aggregation Review', 'Aggregation Review', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(18, 8, 6, 'Aggregation Review', 'Aggregation Review', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(19, 8, 7, 'Aggregation Review', 'Aggregation Review', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(20, 9, 1, 'Final Fix', 'Final Fix', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(21, 9, 1, 'Scorecard Comment', 'Scorecard Comment', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(22, 10, 9, 'Final Review', 'Final Review', 0, 1, 'System', CURRENT, 'System', CURRENT);
INSERT INTO deliverable_lu(deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES(23, 11, 10, 'Approval', 'Approval', 1, 1, 'System', CURRENT, 'System', CURRENT);

INSERT INTO notification_type_lu(notification_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Timeline Notification', 'Timeline Notification', 'System', CURRENT, 'System', CURRENT);

INSERT INTO screening_status_lu(screening_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Pending', 'Pending', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_status_lu(screening_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Screening', 'Screening', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_status_lu(screening_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Failed', 'Failed', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_status_lu(screening_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(4, 'Passed', 'Passed', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_status_lu(screening_status_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(5, 'Passed with Warning', 'Passed with Warning', 'System', CURRENT, 'System', CURRENT);

INSERT INTO response_severity_lu(response_severity_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Fatal Error', 'Fatal Error', 'System', CURRENT, 'System', CURRENT);
INSERT INTO response_severity_lu(response_severity_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Warning', 'Warning', 'System', CURRENT, 'System', CURRENT);
INSERT INTO response_severity_lu(response_severity_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(3, 'Success', 'Success', 'System', CURRENT, 'System', CURRENT);

INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(1, 1, 'TCS-001', 'Your submission distribution is not a jar file.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(2, 1, 'TCS-002', 'Your submission distribution is not a zip file.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(3, 1, 'TCS-003', 'Your submission does not conform to the directory standard.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(4, 1, 'TCS-004', 'Your submission does not contain a component specification document in rich text format (rtf).', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(5, 1, 'TCS-005', 'Your submission does not contain a /log directory from the successful execution of the unit tests.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(6, 1, 'TCS-006', 'Your submission is missing the appropriate unit test log files.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(7, 1, 'TCS-007', 'Your submission does not contain a zargo or zuml file.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(8, 1, 'TCS-008', 'Your submission does not contain one or more use cases.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(9, 1, 'TCS-009', 'Your submission does not contain one or more class diagrams.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(10, 1, 'TCS-010', 'Your submission does not contain one or more sequence diagrams.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(11, 1, 'TCS-011', 'Your submission does not contain source code under /src.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(12, 1, 'TCS-012', 'Your submission does not contain test source code under /src.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(13, 2, 'TCS-013', 'Checkstyle has produced the following warnings.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(14, 2, 'TCS-014', 'Your submission contains personal information.', 'System', CURRENT, 'System', CURRENT);
INSERT INTO screening_response_lu(screening_response_id, response_severity_id, response_code, response_text, create_user, create_date, modify_user, modify_date)
  VALUES(15, 3, 'TCS-015', 'Your submission has passed the auto screening process.', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_type_lu (project_type_id, name, description, is_generic, create_user, create_date, modify_user, modify_date)
       VALUES (4, 'Generic', 'Generic (can not have projects created of that type)', 't', 'System', CURRENT, 'System', CURRENT);

INSERT INTO project_category_lu (project_category_id, project_type_id, name, description, create_user, create_date, modify_user, modify_date)
       VALUES (28, 4, 'Generic Scorecards',
               'Generic scorecards are available for selection when creating projects of all categories',
               'System', CURRENT, 'System', CURRENT);

INSERT INTO scorecard_type_lu (scorecard_type_id, name, description, create_user, create_date, modify_user, modify_date)
       VALUES (4, 'Post-Mortem', 'Post-Mortem', 'System', CURRENT, 'System', CURRENT);

INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, per_submission,
                            required, create_user, create_date, modify_user, modify_date)
       VALUES (24, 12, 16, 'Post-Mortem Review', 'Post-Mortem Review', 0, 1, 'System', CURRENT, 'System', CURRENT );

INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)
  VALUES('submission_type_id_seq', 1, 1, 0);

insert into project_info_type_lu(project_info_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(44, 'Post-Mortem Required', 'Post-Mortem Required', 'System', CURRENT, 'System', CURRENT);

INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(1, 'Contest Submission', 'The contest submission', 'System', CURRENT, 'System', CURRENT);
INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(2, 'Specification Submission', 'The specification submission', 'System', CURRENT, 'System', CURRENT);

INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(13, 'Specification Submission', 'Specification Submission', 'System', CURRENT, 'System', CURRENT);
INSERT INTO phase_type_lu(phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(14, 'Specification Review', 'Specification Review', 'System', CURRENT, 'System', CURRENT);

INSERT INTO comment_type_lu(comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(14, 'Specification Review Comment', 'Specification Review Comment', 'System', CURRENT, 'System', CURRENT);

INSERT INTO resource_role_lu(resource_role_id, phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(17, 13, 'Specification Submitter', 'Specification Submitter', 'System', CURRENT, 'System', CURRENT);
INSERT INTO resource_role_lu(resource_role_id, phase_type_id, name, description, create_user, create_date, modify_user, modify_date)
  VALUES(18, 14, 'Specification Reviewer', 'Specification Reviewer', 'System', CURRENT, 'System', CURRENT);

INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES (25, 13, 1, 'Specification Submission', 'Specification Submission', 0, 0, 'System', CURRENT, 'System', CURRENT );
INSERT INTO deliverable_lu (deliverable_id, phase_type_id, resource_role_id, name, description, per_submission, required, create_user, create_date, modify_user, modify_date)
  VALUES (26, 14, 15, 'Specification Review', 'Specification Review', 1, 1, 'System', CURRENT, 'System', CURRENT );
  

INSERT INTO link_type_lu(link_type_id, link_type_name,allow_overlap) VALUES(1,'Depends On', 0);
INSERT INTO link_type_lu(link_type_id, link_type_name,allow_overlap) VALUES(2,'Is Related To', 1);
INSERT INTO link_type_lu(link_type_id, link_type_name,allow_overlap) VALUES(3,'Requires Spec Review', 0);
INSERT INTO link_type_lu(link_type_id, link_type_name,allow_overlap) VALUES(4,'For Design', 0);
INSERT INTO link_type_lu(link_type_id, link_type_name,allow_overlap) VALUES(5,'Repost For', 1);
