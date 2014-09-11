-- Entries for accuracy test
INSERT INTO template(id, category, name, creation_date, description)  VALUES(1, 1, 'New_Design', null, 'This is a design phases template');

INSERT INTO phase_type(id, template_id, type_id, name)  VALUES(1, 1, 1, 'Submission');
INSERT INTO phase_type(id, template_id, type_id, name)  VALUES(2, 1, 2, 'Screening');
INSERT INTO phase_type(id, template_id, type_id, name)  VALUES(3, 1, 3, 'Review');
INSERT INTO phase_type(id, template_id, type_id, name)  VALUES(4, 1, 4, 'Appeals');
INSERT INTO phase_type(id, template_id, type_id, name)  VALUES(5, 1, 5, 'Appeals Response');
INSERT INTO phase_type(id, template_id, type_id, name)  VALUES(6, 1, 6, 'Aggregation');
INSERT INTO phase_type(id, template_id, type_id, name)  VALUES(7, 1, 7, 'Aggregation Review');
INSERT INTO phase_type(id, template_id, type_id, name)  VALUES(8, 1, 8, 'Final Fixes');
INSERT INTO phase_type(id, template_id, type_id, name)  VALUES(9, 1, 9, 'Final Review');
INSERT INTO phase_type(id, template_id, type_id, name)  VALUES(10, 1, 10, 'Component Preparation');

INSERT INTO phase(id, phase_type_id, template_id, phase_id, time_length)  VALUES(1, 1, 1, 1, 604800000);
INSERT INTO phase(id, phase_type_id, template_id, phase_id, time_length)  VALUES(2, 2, 1, 2, 86400000);
INSERT INTO phase(id, phase_type_id, template_id, phase_id, time_length)  VALUES(3, 3, 1, 3, 259200000);
INSERT INTO phase(id, phase_type_id, template_id, phase_id, time_length)  VALUES(4, 4, 1, 4, 86400000);
INSERT INTO phase(id, phase_type_id, template_id, phase_id, time_length)  VALUES(5, 5, 1, 5, 86400000);
INSERT INTO phase(id, phase_type_id, template_id, phase_id, time_length)  VALUES(6, 6, 1, 6, 43200000);
INSERT INTO phase(id, phase_type_id, template_id, phase_id, time_length)  VALUES(7, 7, 1, 7, 43200000);
INSERT INTO phase(id, phase_type_id, template_id, phase_id, time_length)  VALUES(8, 8, 1, 8, 172800000);
INSERT INTO phase(id, phase_type_id, template_id, phase_id, time_length)  VALUES(9, 9, 1, 9, 86400000);
INSERT INTO phase(id, phase_type_id, template_id, phase_id, time_length)  VALUES(10, 10, 1, 10, 86400000);

INSERT INTO dependency(id, dependent_id, dependency_id, dependent_start, dependency_start, lag_time)
VALUES(1, 2, 1, 't', 'f', 0);
INSERT INTO dependency(id, dependent_id, dependency_id, dependent_start, dependency_start, lag_time) 
VALUES(2, 3, 2, 't', 'f', 0);
INSERT INTO dependency(id, dependent_id, dependency_id, dependent_start, dependency_start, lag_time) 
VALUES(3, 4, 3, 't', 'f', 0);
INSERT INTO dependency(id, dependent_id, dependency_id, dependent_start, dependency_start, lag_time) 
VALUES(4, 5, 4, 't', 'f', 0);
INSERT INTO dependency(id, dependent_id, dependency_id, dependent_start, dependency_start, lag_time) 
VALUES(5, 6, 5, 't', 'f', 0);
INSERT INTO dependency(id, dependent_id, dependency_id, dependent_start, dependency_start, lag_time) 
VALUES(6, 7, 6, 't', 'f', 0);
INSERT INTO dependency(id, dependent_id, dependency_id, dependent_start, dependency_start, lag_time) 
VALUES(7, 8, 7, 't', 'f', 0);
INSERT INTO dependency(id, dependent_id, dependency_id, dependent_start, dependency_start, lag_time) 
VALUES(8, 9, 8, 't', 'f', 0);
INSERT INTO dependency(id, dependent_id, dependency_id, dependent_start, dependency_start, lag_time) 
VALUES(9, 10, 9, 't', 'f', 0);

-- Entries for tesing templates with duplicate name
INSERT INTO template(id, category, name, creation_date, description)  VALUES(2, 1, 'Duplicate Name', null, 'This is a duplicate template');
INSERT INTO template(id, category, name, creation_date, description)  VALUES(3, 1, 'Duplicate Name', null, 'This is a duplicate template');
INSERT INTO template(id, category, name, creation_date, description)  VALUES(4, 1, 'Duplicate Name', null, 'This is a dduplicate template');

--- Entries for cyclic test
INSERT INTO template(id, category, name, creation_date, description)  VALUES(5, 1, 'Cyclic Template', null, 'This is a dduplicate template');
INSERT INTO phase_type(id, template_id, type_id, name)  VALUES(11, 5, 11, 'Component Preparation');
INSERT INTO phase(id, phase_type_id, template_id, phase_id, time_length)  VALUES(11, 11, 5, 11, 604800000);
INSERT INTO phase(id, phase_type_id, template_id, phase_id, time_length)  VALUES(12, 11, 5, 12, 86400000);
INSERT INTO dependency(id, dependent_id, dependency_id, dependent_start, dependency_start, lag_time)
VALUES(10, 11, 12, 't', 'f', 0);
INSERT INTO dependency(id, dependent_id, dependency_id, dependent_start, dependency_start, lag_time) 
VALUES(11, 12, 11, 't', 'f', 0);

--- Entries for wrong phase test
INSERT INTO template(id, category, name, creation_date, description)  VALUES(6, 2, 'invalid phase', null, 'phase with phase type not in template');
INSERT INTO phase(id, phase_type_id, template_id, phase_id, time_length)  VALUES(13, 11, 6, 11, 604800000);

--- Entries for wrong dependency
INSERT INTO template(id, category, name, creation_date, description)  VALUES(7, 3, 'invalid dependency', null, 'dependency with the phase not in template');
INSERT INTO phase_type(id, template_id, type_id, name)  VALUES(12, 7, 13, 'Component Preparation');
INSERT INTO phase(id, phase_type_id, template_id, phase_id, time_length)  VALUES(14, 12, 7, 13, 604800000);
INSERT INTO dependency(id, dependent_id, dependency_id, dependent_start, dependency_start, lag_time) 
VALUES(13, 14, 13, 't', 'f', 0);

--- Empty template
INSERT INTO template(id, category, name, creation_date, description)  VALUES(18, 2, 'empty template', null, 'empty template');
INSERT INTO template(id, category, name, creation_date, description)  VALUES(8, 4, 'cate40', null, 'cate40');
INSERT INTO template(id, category, name, creation_date, description)  VALUES(9, 4, 'cate41', null, 'cate41');
INSERT INTO template(id, category, name, creation_date, description)  VALUES(10, 4, 'cate42', null, 'cate42');
INSERT INTO template(id, category, name, creation_date, description)  VALUES(11, 4, 'cate43', null, 'cate43');

INSERT INTO template(id, category, name, creation_date, description)  VALUES(12, 5, 'research', null, 'research');
INSERT INTO template(id, category, name, creation_date, description)  VALUES(13, 2, 'test', null, 'test');
INSERT INTO template(id, category, name, creation_date, description)  VALUES(19, 1, 'Development', null, null);
INSERT INTO template(id, category, name, creation_date, description)  VALUES(16, 1, 'New_Development', '2007-12-04 14:45:2', 'This is a development phases template');
INSERT INTO template(id, category, name, creation_date, description)  VALUES(17, 1, 'Design', null, null);

--- default template for category 1
INSERT INTO template(id, category, name, creation_date, description)  VALUES(20, 1, 'New_Design_Default', null, 'This is the default design phases template');
INSERT INTO default_template(id, template_id, category)  VALUES(1, 20, 1);
INSERT INTO default_template(id, template_id, category)  VALUES(2, 8, 4);
INSERT INTO default_template(id, template_id, category)  VALUES(3, 9, 4)



