-- Here you need to insert your VM Accounts for testing:
insert into vm_account (vm_account_id, account_name, aws_access_key_id, aws_security_access_key, create_date, modify_date)
  values (1, 'VM Account 1', 'AKIAIAUCU32DF6IIQJVA', 'DND8RfLk3NBnF5o4RkhjPmUAKP0iq9yxG5iYDmaD', current, current);
insert into vm_account (vm_account_id, account_name, aws_access_key_id, aws_security_access_key, create_date, modify_date)
  values (2, 'VM Account 2', 'AKIAIQZ3QVPQNDX3XFAQ', '0eSYnG6MDUkv9Q1pHXmnKyMINuxzIV3BInW7376e', current, current);
  
insert into vm_account_user (vm_account_user_id, vm_account_id, user_id, create_date, modify_date) 
  values (1, 1, 132456, current, current);
insert into vm_account_user (vm_account_user_id, vm_account_id, user_id, create_date, modify_date) 
  values (2, 2, 124764, current, current);

insert into vm_instance_type (vm_instance_type_id, name, create_date, modify_date)
  values (1, 'm1.small', current, current);
insert into vm_instance_type (vm_instance_type_id, name, create_date, modify_date)
  values (2, 'm1.large', current, current);
insert into vm_instance_type (vm_instance_type_id, name, create_date, modify_date)
  values (3, 'm1.xlarge', current, current);
insert into vm_instance_type (vm_instance_type_id, name, create_date, modify_date)
  values (4, 'm2.xlarge', current, current);
insert into vm_instance_type (vm_instance_type_id, name, create_date, modify_date)
  values (5, 'm2.2xlarge', current, current);
insert into vm_instance_type (vm_instance_type_id, name, create_date, modify_date)
  values (6, 'm2.4xlarge', current, current);
insert into vm_instance_type (vm_instance_type_id, name, create_date, modify_date)
  values (7, 'c1.medium', current, current);
insert into vm_instance_type (vm_instance_type_id, name, create_date, modify_date)
  values (8, 'c1.xlarge', current, current);

insert into vm_security_group (vm_security_group_id, name, create_date, modify_date)
  values (1, 'default', current, current);
insert into vm_security_group (vm_security_group_id, name, create_date, modify_date)
  values (2, 'another_group', current, current);

insert into vm_availability_zone (vm_availability_zone_id, name, create_date, modify_date)
  values (1, 'us-east-1', current, current);
insert into vm_availability_zone (vm_availability_zone_id, name, create_date, modify_date)
  values (2, 'us-west-1', current, current);
insert into vm_availability_zone (vm_availability_zone_id, name, create_date, modify_date)
  values (3, 'eu-west-1', current, current);

insert into vm_key_pair (vm_key_pair_id, name, create_date, modify_date)
  values (1, 'default', current, current);



insert into vm_image (vm_image_id, vm_image_tc_name, aws_image_id, vm_security_group_id, vm_instance_type_id, vm_availability_zone_id, vm_key_pair_id, vm_account_id, create_date, modify_date)
  values (1, 'Shorty_VM_1', 'ami-3c47a355', 1, 1, 1, 1, 1, current, current);
insert into vm_image (vm_image_id, vm_image_tc_name, aws_image_id, vm_security_group_id, vm_instance_type_id, vm_availability_zone_id, vm_key_pair_id, vm_account_id, create_date, modify_date)
  values (2, 'Shorty_VM_2', 'ami-235fba4a', 1, 2, 3, 1, 2, current, current);




insert into vm_contest_type (vm_contest_type_id, name, create_date, modify_date)
  values (1, 'Software Contest', current, current);
insert into vm_contest_type (vm_contest_type_id, name, create_date, modify_date)
  values (2, 'Studio Contest', current, current);
insert into vm_contest_type (vm_contest_type_id, name, create_date, modify_date)
  values (3, 'Bug Race', current, current);

insert into security_roles (role_id, description) values (101, 'VMManager');
insert into security_groups (group_id, description) values (101, 'VMManager');
insert into group_role_xref (group_role_id, group_id, role_id, security_status_id) values (112, 101, 101, 1);
insert into user_group_xref (user_group_id, login_id, group_id, security_status_id) values (22915083, 124764, 101, 1);
insert into user_group_xref (login_id, group_id, security_status_id) values (124764, 101, 1);

insert into user_security_key (user_id, security_key, security_key_type_id) values (132458, 'ssh-rsa AAAAB3NzaC1yc2EAAAABJQAAAIBjL2f0rAtyd+Wuiz9W39zE6fLUezupdEdVdCw6Sufss7pbWcd5+dGAN9d8l8ws5VtebrNKeeFMl6zLy2YXg6t3GtUd0Beds1efVzLTQMuiI/R0DOa2zvKXNaPb6pdjpNXNDBuv53iNjutl5L+a+SQVQdj1Y1E0wMp5nqlTJs+bZw== rsa-key-20110510', 1);
