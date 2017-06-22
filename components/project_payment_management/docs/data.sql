INSERT INTO 'informix'.project_payment_type_lu(project_payment_type_id,name,mergeable) VALUES (1, 'Contest Payment', 'f');
INSERT INTO 'informix'.project_payment_type_lu(project_payment_type_id,name,mergeable) VALUES (2, 'Contest Milestone Payment', 'f');
INSERT INTO 'informix'.project_payment_type_lu(project_payment_type_id,name,mergeable) VALUES (3, 'Review Payment', 't');
INSERT INTO 'informix'.project_payment_type_lu(project_payment_type_id,name,mergeable) VALUES (4, 'Copilot Payment', 'f');

-- Component Design
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (1, 2, 0.0, 0.0, 0.01);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (1, 4, 0.0, 0.12, 0.05);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (1, 8, 10.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (1, 9, 0.0, 0.05, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (1, 18, 30.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (1, 14, 300.0, 0.0, 0.0);

-- Component Development
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (2, 2, 0.0, 0.0, 0.02);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (2, 4, 0.0, 0.2, 0.05);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (2, 5, 0.0, 0.2, 0.05);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (2, 6, 0.0, 0.2, 0.05);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (2, 7, 0.0, 0.2, 0.05);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (2, 8, 10.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (2, 9, 0.0, 0.05, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (2, 18, 30.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (2, 14, 300.0, 0.0, 0.0);

-- Specification
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (6, 2, 0.0, 0.0, 0.01);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (6, 4, 0.0, 0.08, 0.03);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (6, 8, 10.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (6, 9, 0.0, 0.03, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (6, 18, 40.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (6, 14, 600.0, 0.0, 0.0);

-- Architecture
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (7, 2, 0.0, 0.0, 0.01);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (7, 4, 0.0, 0.12, 0.05);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (7, 8, 10.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (7, 9, 0.0, 0.05, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (7, 18, 50.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (7, 14, 600.0, 0.0, 0.0);

-- Bug Hunt
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (9, 18, 50.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (9, 14, 600.0, 0.0, 0.0);

-- Test Suites
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (13, 2, 0.0, 0.0, 0.01);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (13, 4, 0.0, 0.12, 0.05);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (13, 8, 10.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (13, 9, 0.0, 0.03, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (13, 18, 50.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (13, 14, 300.0, 0.0, 0.0);

-- Assembly
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (14, 2, 0.0, 0.0, 0.01);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (14, 4, 0.0, 0.13, 0.05);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (14, 8, 10.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (14, 9, 0.0, 0.05, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (14, 18, 50.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (14, 14, 600.0, 0.0, 0.0);

-- UI Prototype
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (19, 2, 0.0, 0.0, 0.01);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (19, 4, 0.0, 0.08, 0.03);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (19, 8, 10.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (19, 9, 0.0, 0.03, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (19, 18, 50.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (19, 14, 600.0, 0.0, 0.0);

-- Conceptualization
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (23, 2, 0.0, 0.0, 0.01);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (23, 4, 0.0, 0.12, 0.03);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (23, 8, 10.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (23, 9, 0.0, 0.05, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (23, 18, 30.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (23, 14, 600.0, 0.0, 0.0);

-- RIA Build
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (24, 2, 0.0, 0.0, 0.01);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (24, 4, 0.0, 0.08, 0.03);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (24, 8, 10.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (24, 9, 0.0, 0.03, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (24, 18, 50.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (24, 14, 600.0, 0.0, 0.0);

-- Test Scenarios
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (26, 2, 0.0, 0.0, 0.01);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (26, 4, 0.0, 0.12, 0.05);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (26, 8, 10.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (26, 9, 0.0, 0.03, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (26, 18, 50.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (26, 14, 300.0, 0.0, 0.0);

-- RIA Build
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (35, 2, 0.0, 0.0, 0.01);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (35, 4, 0.0, 0.08, 0.03);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (35, 8, 10.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (35, 9, 0.0, 0.03, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (35, 18, 50.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (35, 14, 600.0, 0.0, 0.0);


-- Banners/Icons
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (16, 2, 100.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (16, 18, 75.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (16, 14, 600.0, 0.0, 0.0);

-- Web Design
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (17, 2, 100.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (17, 18, 75.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (17, 14, 600.0, 0.0, 0.0);

-- Wireframes
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (18, 2, 100.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (18, 18, 75.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (18, 14, 600.0, 0.0, 0.0);

-- Logo Design
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (20, 2, 150.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (20, 18, 75.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (20, 14, 600.0, 0.0, 0.0);

-- Print/Presentation
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (21, 2, 100.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (21, 18, 75.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (21, 14, 600.0, 0.0, 0.0);

-- Idea Generation
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (22, 2, 100.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (22, 18, 75.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (22, 14, 600.0, 0.0, 0.0);

-- Widget or Mobile Screen Design
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (30, 2, 100.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (30, 18, 75.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (30, 14, 600.0, 0.0, 0.0);

-- Front-End Flash
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (31, 2, 100.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (31, 18, 75.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (31, 14, 600.0, 0.0, 0.0);

-- Application Front-End Design
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (32, 2, 100.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (32, 18, 75.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (32, 14, 600.0, 0.0, 0.0);

-- Studio Other
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (34, 2, 100.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (34, 18, 75.0, 0.0, 0.0);
INSERT INTO 'informix'.default_project_payment(project_category_id,resource_role_id,fixed_amount,base_coefficient,incremental_coefficient) VALUES (34, 14, 600.0, 0.0, 0.0);