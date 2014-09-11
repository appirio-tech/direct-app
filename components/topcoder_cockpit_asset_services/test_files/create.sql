create table 'informix'.user (
    user_id DECIMAL(10,0) not null,
    first_name VARCHAR(64),
    last_name VARCHAR(64),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    modify_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION,
    handle VARCHAR(50) not null,
    last_login DATETIME YEAR TO FRACTION,
    status VARCHAR(3) not null,
    activation_code VARCHAR(32),
    middle_name VARCHAR(64),
    handle_lower VARCHAR(50),
    timezone_id DECIMAL(5,0),
    last_site_hit_date DATETIME YEAR TO FRACTION,
    name_in_another_language VARCHAR(64),
	  password VARCHAR(16),
    open_id VARCHAR(200),
    reg_source VARCHAR(20)
);


create table 'informix'.client_project (
    client_id INT not null,
    project_id INT not null,
    creation_date DATETIME YEAR TO SECOND not null,
    creation_user VARCHAR(64) not null,
    modification_date DATETIME YEAR TO SECOND not null,
    modification_user VARCHAR(64) not null
);

create table 'informix'.user_client (
    user_id DECIMAL(10,0) not null,
    client_id DECIMAL(10,0) not null,
    admin_ind DECIMAL(12,0)
);


create table 'informix'.project_manager (
    project_id INT not null,
    user_account_id INT not null,
    pay_rate DECIMAL(9,2),
    cost DECIMAL(5,2) not null,
    active SMALLINT not null,
    creation_date DATETIME YEAR TO SECOND not null,
    creation_user VARCHAR(64) not null,
    modification_date DATETIME YEAR TO SECOND not null,
    modification_user VARCHAR(64) not null
);

create table 'informix'.user_account (
    user_account_id INT not null,
    company_id INT,
    account_status_id INT not null,
    user_name VARCHAR(64) not null,
    password VARCHAR(64) not null,
    creation_date DATETIME YEAR TO SECOND not null,
    creation_user VARCHAR(64) not null,
    modification_date DATETIME YEAR TO SECOND not null,
    modification_user VARCHAR(64) not null,
    user_status_id INT,
    user_type_id INT,
    billable_type INT
);

CREATE TABLE 'informix'.copilot_project (
  copilot_project_id DECIMAL(10,0) NOT NULL,
  copilot_profile_id DECIMAL(10,0) NOT NULL,
  name VARCHAR(255),
  tc_direct_project_id DECIMAL(10,0) NOT NULL,
  copilot_type_id DECIMAL(10,0) NOT NULL,
  copilot_project_status_id DECIMAL(10,0) NOT NULL,
  customer_feedback LVARCHAR(4096),
  customer_rating DECIMAL(2,1),
  pm_feedback LVARCHAR(4096),
  pm_rating DECIMAL(2,1),
  private_project CHAR(1) NOT NULL,
  completion_date DATETIME YEAR TO FRACTION,
  create_user VARCHAR(64) NOT NULL,
  create_date DATETIME YEAR TO FRACTION NOT NULL,
  modify_user VARCHAR(64) NOT NULL,
  modify_date DATETIME YEAR TO FRACTION NOT NULL,
  PRIMARY KEY (copilot_project_id) constraint pk_copilot_project
);

CREATE TABLE 'informix'.copilot_profile (
    copilot_profile_id DECIMAL(10,0) NOT NULL,
    user_id DECIMAL(10, 0) NOT NULL,
    copilot_profile_status_id DECIMAL(10,0) NOT NULL,
    suspension_count INT NOT NULL,
    reliability DECIMAL(5,2) NOT NULL,
    activation_date DATETIME YEAR TO FRACTION,
    show_copilot_earnings BOOLEAN,
    create_user VARCHAR(64) NOT NULL,
    create_date DATETIME YEAR TO FRACTION  NOT NULL,
    update_user VARCHAR(64) NOT NULL,
    update_date DATETIME YEAR TO FRACTION  NOT NULL,
    is_software_copilot BOOLEAN DEFAULT 'f',
    is_studio_copilot BOOLEAN DEFAULT 'f'
);

-- -----------------------------------------------------
-- Table AuditRecord
-- -----------------------------------------------------
CREATE  TABLE 'informix'.AuditRecord (
  id SERIAL8 NOT NULL,
  timestamp DATETIME YEAR TO FRACTION NOT NULL ,
  user_id INT8 NOT NULL ,
  action VARCHAR(200) NOT NULL ,
  entity_type VARCHAR(200) NOT NULL ,
  entity_id INT8 NOT NULL ,
  old_value LVARCHAR(4000) ,
  new_value LVARCHAR(4000) ,
  PRIMARY KEY (id) );


-- -----------------------------------------------------
-- Table FileTypeIcon
-- -----------------------------------------------------
CREATE  TABLE 'informix'.FileTypeIcon (
  id SERIAL8 NOT NULL,
  file_type VARCHAR(50) NOT NULL ,
  file_type_category VARCHAR(50) NOT NULL ,
  icon_path VARCHAR(200) NOT NULL ,
  PRIMARY KEY (id) );


-- -----------------------------------------------------
-- Table Category
-- -----------------------------------------------------
CREATE  TABLE 'informix'.Category (
  id SERIAL8 NOT NULL,
  name VARCHAR(50) NOT NULL ,
  container_type VARCHAR(50) NOT NULL ,
  container_id INT8 NOT NULL ,
  PRIMARY KEY (id) );


-- -----------------------------------------------------
-- Table Asset
-- -----------------------------------------------------
CREATE  TABLE 'informix'.Asset (
  id SERIAL8 NOT NULL,
  name VARCHAR(100) NOT NULL ,
  current_version_id INT8 ,
  container_type VARCHAR(50) NOT NULL ,
  container_id INT8 NOT NULL ,
  public CHAR(1) NOT NULL ,
  PRIMARY KEY (id));

-- -----------------------------------------------------
-- Table AssetVersion
-- -----------------------------------------------------
CREATE  TABLE 'informix'.AssetVersion (
  id SERIAL8 NOT NULL,
  version VARCHAR(50) NOT NULL ,
  file_name VARCHAR(100) NOT NULL ,
  file_type VARCHAR(100) NOT NULL ,
  file_size_bytes INT8 NOT NULL ,
  uploader_id INT8 NOT NULL ,
  upload_time DATETIME YEAR TO FRACTION NOT NULL ,
  description LVARCHAR(1000) ,
  asset_id INT8 NOT NULL ,
  preview_image_path VARCHAR(200) ,
  file_path VARCHAR(200) NOT NULL ,
  PRIMARY KEY (id));
  
alter table 'informix'.AssetVersion add constraint foreign key 
    (asset_id)
    references 'informix'.Asset (id )
    ON DELETE CASCADE
    constraint version_asset_id_ref;
    
alter table 'informix'.Asset add constraint foreign key 
    (current_version_id)
    references 'informix'.AssetVersion (id )
    constraint asset_current_version_id_ref;


-- -----------------------------------------------------
-- Table AssetCategoryMapping
-- -----------------------------------------------------
CREATE  TABLE 'informix'.AssetCategoryMapping (
  asset_id INT8 NOT NULL,
  category_id INT8 NOT NULL ,
  PRIMARY KEY (asset_id, category_id));
  
alter table 'informix'.AssetCategoryMapping add constraint foreign key 
    (asset_id)
    references 'informix'.Asset (id )
    ON DELETE CASCADE
    constraint mapping_asset_id_ref;
    
alter table 'informix'.AssetCategoryMapping add constraint foreign key 
    (category_id)
    references 'informix'.Category (id )
    ON DELETE CASCADE
    constraint mapping_category_id_ref;
    
-- -----------------------------------------------------
-- Table AssetPermission
-- -----------------------------------------------------
CREATE  TABLE 'informix'.AssetPermission (
  id SERIAL8 NOT NULL,
  asset_id INT8 NOT NULL ,
  user_id INT8 NOT NULL ,
  PRIMARY KEY (id));
    
alter table 'informix'.AssetPermission add constraint foreign key 
    (asset_id)
    references 'informix'.Asset (id )
    ON DELETE CASCADE
    constraint permission_asset_id_ref;