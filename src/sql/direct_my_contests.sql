select  tdp.project_id as tc_direct_project_id, tdp.name as tc_direct_project_name, 
(select value from project_info where project_id = p.project_id and project_info_type_id =6) as contest_name, p.project_id as contest_id,
pcl.name as contest_type,
(select min(nvl(actual_start_time, scheduled_start_time)) from project_phase ph where ph.project_id=p.project_id) as start_date,
(select max(nvl(actual_end_time, scheduled_end_time)) from project_phase ph where ph.project_id=p.project_id) as end_date,
(select count(resource_id) from resource r where r.project_id = p.project_id and resource_role_id = 1) as number_of_registration, 
(select count (distinct resource_id) from upload u where u.project_id = p.project_id and upload_status_id = 1 and upload_type_id = 1) as number_of_submission,
(select count(messageid) from jivecategory c, jiveforum f, jivemessage m, project_info pi 
            		    where pi.project_info_type_id =4 and c.categoryid = pi.value and c.categoryid = f.categoryid and m.forumid = f.forumid 
            		      and pi.project_id =  p.project_id) as number_of_forum,
case when p.project_status_id != 1 then psl.name 
       when ptl.name is not null and p.project_status_id = 1 then ptl.name
        when sale.contest_sale_id is not null and p.project_status_id = 1 then 'Scheduled'
       else 'Draft' end as status

from tc_direct_project tdp, user_permission_grant upg, 
project_category_lu pcl, project_status_lu psl, 

project p left outer join project_phase ph 
      on (p.project_id = ph.project_id and ph.project_phase_id = 
                                     (select min(project_phase_id) from project_phase  where phase_status_id = 2 and project_id=p.project_id))
left outer join phase_type_lu ptl on (ph.phase_type_id = ptl.phase_type_id)
 left outer join contest_sale sale on (p.project_id = sale.contest_id and sale.contest_sale_id  = (select min(contest_sale_id) from contest_sale where contest_id = p.project_id))
where tdp.project_id = upg.resource_id
and upg.user_id = @uid@
and upg.permission_type_id in (1,2,3)
and p.tc_direct_project_id = @tcdirectid@
and p.project_category_id = pcl.project_category_id 
and p.project_status_id = psl.project_status_id
and p.tc_direct_project_id = tdp.project_id
and p.project_status_id != 3 and p.project_category_id != 27

union

select  tdp.project_id as tc_direct_project_id, tdp.name as tc_direct_project_name, 
p.name as contest_name, p.contest_id as contest_id,
(select contest_type_desc from studio_oltp:contest_type_lu where contest_type_id = p.contest_type_id) as contest_type,

p.start_time start_date, p.end_time as end_date,

(select count(*) from studio_oltp:contest_registration where contest_id = p.contest_id ) as number_of_registration, 
(select count(*) from studio_oltp:submission as s 
                    left outer join studio_oltp:submission_review sr 
                    	on s.submission_id = sr.submission_id 
                    	where contest_id = p.contest_id 
                     	and s.submission_status_id = 1 
                   		and rank is not null
                    		and rank <= (select NVL(property_value, 10000) 
                    					 	from studio_oltp:contest_config 
                    						where contest_id = p.contest_id and property_id = 8) 
                    		and (sr.submission_id is null or (sr.review_status_id <> 2 and sr.review_status_id <> 3))) as number_of_submission,
(select count(*) from jivemessage where forumid = p.forum_id )  as number_of_forum,
ds.name as status

from user_permission_grant upg, tc_direct_project tdp, studio_oltp:contest p,
studio_oltp:contest_detailed_status_lu ds 
 
where (p.deleted is null or p.deleted = 0) and (p.contest_detailed_status_id is null or p.contest_detailed_status_id!=3 )
and upg.resource_id = tdp.project_id
and upg.user_id = @uid@
and upg.permission_type_id in (1,2,3)
and tdp.project_id = @tcdirectid@
and p.tc_direct_project_id = tdp.project_id 
and p.contest_detailed_status_id = ds.contest_detailed_status_id 

order by start_date desc, contest_name

