insert into tc_direct_project(project_id ,name ,description ,user_id ,create_date ,modify_date) values (1 , 'pservice 1', 'desc', 1, CURRENT, CURRENT);
insert into tc_direct_project(project_id ,name ,description ,user_id ,create_date ,modify_date) values (2 , 'pservice 2', 'desc', 2, CURRENT, CURRENT);
insert into tc_direct_project(project_id ,name ,description ,user_id ,create_date ,modify_date) values (3 , 'pservice 1', 'desc', 0, CURRENT, CURRENT);
insert into competition(competition_id ,project_id) values ( 1,1);
insert into competition(competition_id ,project_id) values ( 2,1);
insert into competition(competition_id ,project_id) values ( 3,2);
insert into competition(competition_id ,project_id) values ( 4,2);