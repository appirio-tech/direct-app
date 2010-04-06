select 'Contest Launched' as activity_type, p.tc_direct_project_id as tc_direct_project_id,
tdp.name as tc_direct_project_name, pi.value as contest_name, p.project_id as contest_id, 0 as is_studio, 
p.create_user as user_id, u.handle as user, ph.actual_start_time as activity_time
from project p, project_phase ph, project_info pi, user u, tc_direct_project tdp
where
p.project_id = ph.project_id
and ph.project_id = pi.project_id
and p.create_user = @uid@
and project_info_type_id = 6
and ph.phase_type_id = 1
and ph.phase_status_id in (2,3)
AND date(ph.actual_start_time) > date(current) - @days@
and tdp.project_id = p.tc_direct_project_id
and u.user_id = p.create_user
and p.project_category_id != 27

union

select 'Contest Launched' as activit_type, p.tc_direct_project_id as tc_direct_project_id,
tdp.name as tc_direct_project_name, p.name as contest_name, p.contest_id as contest_id, 1 as is_studio, 
cast(p.create_user_id as VARCHAR(64)) as user_id, u.handle as user, p.start_time as activity_time
from studio_oltp:contest p, tc_direct_project tdp, user u
where p.tc_direct_project_id = tdp.project_id and p.create_user_id = u.user_id
and date(p.start_time) > date(current) - @days@
and p.contest_status_id in (2,4, 10)
and p.create_user_id = @uid@


order by activity_time desc, activity_type, tc_direct_project_name, contest_name
