--insert into vm_image (vm_image_id, aws_image_id) values (1, 'abc');
--insert into vm_image (vm_image_id, aws_image_id) values (2, 'xyz');


CREATE TABLE vm_security_group (
  vm_security_group_id DECIMAL(10,0) NOT NULL  ,
  name VARCHAR(50) NOT NULL ,
  create_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  modify_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  PRIMARY KEY (vm_security_group_id)
);


CREATE TABLE vm_instance_type (
  vm_instance_type_id DECIMAL(10,0) NOT NULL  ,
  name VARCHAR(50) NOT NULL ,
  create_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  modify_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  PRIMARY KEY (vm_instance_type_id)
);


CREATE TABLE vm_availability_zone (
  vm_availability_zone_id DECIMAL(10,0) NOT NULL  ,
  name VARCHAR(50) NOT NULL ,
  create_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  modify_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  PRIMARY KEY (vm_availability_zone_id)
);


CREATE TABLE vm_key_pair (
  vm_key_pair_id DECIMAL(10,0) NOT NULL  ,
  name VARCHAR(50) NOT NULL ,
  create_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  modify_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  PRIMARY KEY (vm_key_pair_id)
);

CREATE TABLE vm_account (
  vm_account_id DECIMAL(10,0) NOT NULL  ,
  account_name VARCHAR(50) NOT NULL,
  aws_access_key_id VARCHAR(50) NOT NULL ,
  aws_security_access_key VARCHAR(100) NOT NULL ,
  create_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  modify_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  PRIMARY KEY (vm_account_id)
);

create table 'informix'.vm_account_user (
  vm_account_user_id DECIMAL(10,0) NOT NULL,
  vm_account_id DECIMAL(10,0) NOT NULL,
  user_id DECIMAL(10,0) NOT NULL ,
  create_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  modify_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  PRIMARY KEY (vm_account_user_id) 
)

CREATE TABLE vm_image (
  vm_image_id DECIMAL(10,0) NOT NULL  ,
  aws_image_id VARCHAR(50) NOT NULL ,
  vm_security_group_id DECIMAL(10,0)  ,
  vm_instance_type_id DECIMAL(10,0)  ,
  vm_availability_zone_id DECIMAL(10,0)  ,
  vm_key_pair_id DECIMAL(10,0)  ,
  vm_account_id DECIMAL(10,0) NOT NULL, 
  create_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  modify_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  PRIMARY KEY (vm_image_id)
);


CREATE TABLE vm_user_data (
  vm_user_data_id DECIMAL(10,0) NOT NULL  ,
  key VARCHAR(50) NOT NULL ,
  value VARCHAR(200)  ,
  create_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  modify_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  encrypted CHAR(1) NOT NULL ,
  PRIMARY KEY (vm_user_data_id)
);


CREATE TABLE vm_image_user_data (
  vm_image_id DECIMAL(10,0) NOT NULL ,
  vm_user_data_id DECIMAL(10,0) NOT NULL ,
  PRIMARY KEY (vm_image_id, vm_user_data_id)
);


CREATE TABLE vm_contest_type (
  vm_contest_type_id DECIMAL(10,0) NOT NULL  ,
  name VARCHAR(50) NOT NULL ,
  create_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  modify_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  PRIMARY KEY (vm_contest_type_id)
);


CREATE TABLE vm_instance (
  vm_instance_id DECIMAL(10,0) NOT NULL ,
  aws_instance_id VARCHAR(50) NOT NULL ,
  create_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  modify_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  vm_image_id DECIMAL(10,0) NOT NULL ,
  vm_account_user_id DECIMAL(10,0) NOT NULL ,
  tc_member_handle VARCHAR(50) NOT NULL ,
  svn_branch VARCHAR(200) NOT NULL ,
  contest_id DECIMAL(10,0) NOT NULL ,
  vm_contest_type_id DECIMAL(10,0) NOT NULL ,
  PRIMARY KEY (vm_instance_id)
);


CREATE TABLE vm_instance_audit (
  vm_instance_audit_id DECIMAL(10,0) NOT NULL  ,
  vm_instance_id DECIMAL(10,0) NOT NULL ,
  create_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  modify_date DATETIME YEAR to FRACTION(3) NOT NULL ,
  action VARCHAR(50) NOT NULL ,
  PRIMARY KEY (vm_instance_audit_id)
);



--SET SQL_MODE=@OLD_SQL_MODE;
--SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
--SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
