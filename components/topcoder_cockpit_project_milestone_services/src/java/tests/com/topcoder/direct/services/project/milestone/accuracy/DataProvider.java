package com.topcoder.direct.services.project.milestone.accuracy;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.direct.services.view.dto.contest.ContestCopilotDTO;

public class DataProvider {
    /**
     * <p>Constructs new <code>DataProvider</code> instance. This implementation does nothing.</p>
     */
    private DataProvider() {
    }

    /**
     * Gets the copilot user ids for the given direct project.
     *
     * @param directProjectId the direct project id.
     * @return an array of copilot user ids of the given direct project.
     * @throws Exception if an unexpected error occurs while communicating to persistence data store.
     * @since 2.2.0
     */
    public static List<ContestCopilotDTO> getCopilotsForDirectProject(long directProjectId) throws Exception {
        List<ContestCopilotDTO> result = new ArrayList<ContestCopilotDTO>();
        if (directProjectId == 1) {
            ContestCopilotDTO dto = new ContestCopilotDTO();
            dto.setHandle("handle1");
            dto.setUserId(123);
            result.add(dto);
            dto = new ContestCopilotDTO();
            dto.setHandle("handle2");
            dto.setUserId(456);
            result.add(dto);
        } else if (directProjectId == 999) {
            throw new Exception("error in getCopilotsForDirectProject");
        }
//        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
//        Request request = new Request();
//        request.setContentHandle("direct_project_copilots");
//        request.setProperty("tcdirectid", String.valueOf(directProjectId));
//
//        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_project_copilots");
//        List<ContestCopilotDTO> result = new ArrayList<ContestCopilotDTO>();
//
//        final int recordNum = resultContainer.size();
//
//        for (int i = 0; i < recordNum; i++) {
//            long copilotUserId = resultContainer.getLongItem(i, "copilot_user_id");
//            long copilotProfileId = resultContainer.getLongItem(i, "copilot_profile_id");
//            long projectId = resultContainer.getLongItem(i, "project_id");
//            String projectName = resultContainer.getStringItem(i, "project_name");
//            String handle = resultContainer.getStringItem(i, "handle");
//
//            ContestCopilotDTO copilot = new ContestCopilotDTO();
//            copilot.setUserId(copilotUserId);
//            copilot.setHandle(handle);
//            copilot.setCopilotProfileId(copilotProfileId);
//            ProjectBriefDTO project = new ProjectBriefDTO();
//            project.setId(projectId);
//            project.setName(projectName);
//
//            copilot.setCopilotProject(project);
//
//            // add to result
//            result.add(copilot);
//        }

        return result;
    }
}
