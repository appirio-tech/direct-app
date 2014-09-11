DELETE FROM resource_info WHERE resource_id IN (1, 2, 3, 4, 5);
DELETE FROM resource_info_type_lu WHERE resource_info_type_id IN (1, 2, 3, 4, 5);
DELETE FROM resource_submission WHERE resource_id IN (1, 2, 3, 4, 5);
DELETE FROM resource WHERE resource_id IN (1, 2, 3, 4, 5);

DELETE FROM notification WHERE project_id IN (1, 2, 3, 4, 5);
DELETE FROM notification_type_lu WHERE notification_type_id IN (1, 2, 3, 4, 5);
DELETE FROM resource_role_lu WHERE resource_role_id IN (1, 2, 3, 4, 5);

DELETE FROM submission WHERE submission_id IN (1, 2, 3, 4, 5);
DELETE FROM project WHERE project_id IN (1, 2, 3, 4, 5);
