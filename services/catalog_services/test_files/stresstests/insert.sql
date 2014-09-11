insert into phase(
     phase_id ,
     description
) values (1 ,"desc");
insert into comp_versions(
     comp_vers_id ,
     component_id ,
     version ,
     version_text ,
     create_time ,
     phase_id ,
     phase_time ,
     price ,
     comments ,
     modify_date ,
     suspended_ind
) values ( 2,null ,1 ,"1.0" ,CURRENT ,1 ,CURRENT , 2500, "c",CURRENT ,0);
insert into comp_catalog(
     component_id ,
     current_version ,
     short_desc ,
     component_name ,
     description ,
     function_desc ,
     create_time ,
     status_id ,
     root_category_id ,
     modify_date
) values (2 ,2 ,"sd" , "cn", "desc", "fd", CURRENT, 301, null,CURRENT);

insert into comp_catalog(
     component_id ,
     current_version ,
     short_desc ,
     component_name ,
     description ,
     function_desc ,
     create_time ,
     status_id ,
     root_category_id ,
     modify_date
) values (3 ,2 ,"sd" , "cn", "desc", "fd", CURRENT, 301, null,CURRENT);

insert into categories(
     category_id ,
     parent_category_id ,
     category_name ,
     description ,
     status_id ,
     viewable
) values ( 2, null,"cn" , "des", 1,1);

insert into comp_jive_category_xref(
     comp_vers_id ,
     jive_category_id
) values ( 2,2);

insert into technology_types(
     technology_type_id ,
     technology_name ,
     description ,
     status_id
) values (2 , "t", "des",1);
insert into catalog(
     catalog_id ,
     catalog_name
) values ( 2,"catalogName");
insert into category_catalog(
     catalog_id ,
     category_id
) values (2 ,2);
insert into comp_categories(
     comp_categories_id ,
     component_id ,
     category_id
) values ( 2,2 ,2);
insert into comp_user(
     component_id ,
     user_id
) values (2 ,2);
insert into comp_user(
     component_id ,
     user_id
) values (2 ,5);
insert into comp_versions(
     comp_vers_id ,
     component_id ,
     version ,
     version_text ,
     phase_id ,
     phase_time ,
     price ,
     comments ,
     modify_date ,
     suspended_ind
) values ( 3, 2,2 , "2",1 , CURRENT, 2500, "c", CURRENT,0);

insert into comp_link(
     comp_link_id ,
     comp_vers_id ,
     link
) values (2 ,2 ,"link");

insert into comp_technology(
     comp_tech_id ,
     comp_vers_id ,
     technology_type_id
) values (2 , 2,2);

insert into comp_version_dates(
     comp_version_dates_id ,
     comp_vers_id ,
     phase_id ,
     posting_date ,
     initial_submission_date ,
     winner_announced_date ,
     final_submission_date ,
     estimated_dev_date ,
     price ,
     total_submissions ,
     status_id ,
     create_time ,
     level_id ,
     screening_complete_date ,
     review_complete_date ,
     aggregation_complete_date ,
     phase_complete_date ,
     production_date 
) values (2 , 2, 1,CURRENT , CURRENT,CURRENT ,CURRENT , CURRENT, 2500, 2,301 ,CURRENT ,1 ,CURRENT ,CURRENT ,CURRENT ,CURRENT ,CURRENT);