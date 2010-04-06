select unique tdp.project_id as tc_direct_project_id, name as tc_direct_project_name

from tc_direct_project tdp, user_permission_grant upg

where tdp.project_id = upg.resource_id

and upg.user_id = @uid@

and upg.permission_type_id in (1,2,3)

order by tc_direct_project_name