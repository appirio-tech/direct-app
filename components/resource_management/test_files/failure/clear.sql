-- List of requirements before running failure tests:
-- 1) At least one resource role with name as 'failuretests'
-- 2) At least one notification type with name as 'failuretests'
-- 3) At least one notification with external_ref_id as 1
-- 4) At least one notification with phase_id as 1
DELETE FROM resource;
DELETE FROM notification;
DELETE FROM notification_type_lu;
DELETE FROM resource_role_lu;
DELETE FROM submission;
DELETE FROM project_phase;
DELETE FROM phase_type_lu;
DELETE FROM project;