--
-- This file provides a dummy content for tc_direct_facts query to be created in Query Tool.
--
SELECT
    (SELECT 1 FROM dual) AS active_contests_count,
    (SELECT 2 FROM dual) AS active_members_count,
    (SELECT 3 FROM dual) AS active_projects_count,
    (SELECT 4 FROM dual) AS completed_projects_count,
    (SELECT 10303 FROM dual) AS prize_purse
FROM dual;
