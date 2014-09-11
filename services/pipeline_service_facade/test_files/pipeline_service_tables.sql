CREATE TABLE software_competition_pipeline_info (
	id  INTEGER NOT NULL,	
	review_payment DECIMAL(10,2) NULL,
	specification_review_payment DECIMAL(10,2) NULL,
	contest_fee DECIMAL(10,2) NULL,
	client_name VARCHAR(45) NULL ,
	confidence INTEGER NULL,
	client_approval DECIMAL(1,0) NULL,
	pricing_approval  DECIMAL(1,0) NULL,
	has_wiki_specification  DECIMAL(1,0) NULL,
	passed_spec_review  DECIMAL(1,0) NULL,
	has_dependant_competitions  DECIMAL(1,0) NULL,
	was_reposted  DECIMAL(1,0) NULL,
	notes VARCHAR(150) NULL ,
	component_id DECIMAL(12,0) NULL,	
	PRIMARY KEY(id) --primary key description
	FOREIGN KEY(component_id) -- foreign key to comp_catalog table
    	REFERENCES comp_catalog(component_id)
);

CREATE TABLE studio_competition_pipeline_info (
	id  INTEGER NOT NULL,	
	review_payment DECIMAL(10,2) NULL,
	specification_review_payment DECIMAL(10,2) NULL,
	contest_fee DECIMAL(10,2) NULL,
	client_name VARCHAR(45) NULL ,
	confidence INTEGER NULL,
	client_approval DECIMAL(1,0) NULL,
	pricing_approval  DECIMAL(1,0) NULL,
	has_wiki_specification  DECIMAL(1,0) NULL,
	passed_spec_review  DECIMAL(1,0) NULL,
	has_dependant_competitions  DECIMAL(1,0) NULL,
	was_reposted  DECIMAL(1,0) NULL,
	notes VARCHAR(150) NULL ,
	contest_id DECIMAL(10,0) NULL,	
	PRIMARY KEY(id) --primary key description
	FOREIGN KEY(contest_id) -- foreign key to contest table
    	REFERENCES contest(contest_id)
);

CREATE TABLE studio_competition_pipeline_resources(
	id INTEGER NOT NULL,	
	studio_competition_pipeline_info_id INTEGER NOT NULL,	
	resource_id INTEGER NOT NULL,
	PRIMARY KEY(id) --primary key description
	FOREIGN KEY(studio_competition_pipeline_info_id)
    	REFERENCES studio_competition_pipeline_info(studio_competition_pipeline_info_id)
    FOREIGN KEY(resource_id)
    	REFERENCES resource(resource_id)	
);

CREATE TABLE software_competition_change_history(
	id  INTEGER NOT NULL,	
	manager VARCHAR(45) NULL ,
	client VARCHAR(45) NULL ,
	type VARCHAR(45) NULL ,
	new_data VARCHAR(45) NULL ,
	old_data VARCHAR(45) NULL ,
	initial_data VARCHAR(45) NULL ,
	change_time DATETIME NULL,
	change_type VARCHAR(45) NULL ,
	software_competition_pipeline_info_id INTEGER NULL,
	PRIMARY KEY(id) --primary key description
	FOREIGN KEY(software_competition_pipeline_info_id)
    	REFERENCES software_competition_pipeline_info(id)
);

CREATE TABLE studio_competition_change_history(
	id  INTEGER NOT NULL,	
	manager VARCHAR(45) NULL ,
	client VARCHAR(45) NULL ,
	type VARCHAR(45) NULL ,
	new_data VARCHAR(45) NULL ,
	old_data VARCHAR(45) NULL ,
	initial_data VARCHAR(45) NULL ,
	change_time DATETIME NULL,
	change_type VARCHAR(45) NULL ,
	studio_competition_pipeline_info_id INTEGER NULL,
	PRIMARY KEY(id) --primary key description
	FOREIGN KEY(software_competition_pipeline_info_id)
    	REFERENCES studio_competition_pipeline_info(id)
);
