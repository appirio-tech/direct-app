package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.copilot.dao.CopilotProfileDAO;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProfileStatus;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.copilot.CopilotBriefDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotProjectDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *     Action provides the ajax copilot data for the copilot management widget in the project overview page.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit Project Overview Update 1)
 */
public class ProjectCopilotsWidgetAction extends BaseDirectStrutsAction {

    /**
     * The id of the direct project.
     */
    private long directProjectId;

     /**
     * The copilot profile DAO. This field will be injected via spring injection.
     */
    private CopilotProfileDAO copilotProfileDAO;

    /**
     * Gets the id of the direct project.
     *
     * @return the id of the direct project.
     */
    public long getDirectProjectId() {
        return directProjectId;
    }

    /**
     * Sets the id of the direct project.
     *
     * @param directProjectId the id of the direct project.
     */
    public void setDirectProjectId(long directProjectId) {
        this.directProjectId = directProjectId;
    }

    /**
     * Retrieves the copilotProfileDAO field.
     *
     * @return the copilotProfileDAO
     */
    public CopilotProfileDAO getCopilotProfileDAO() {
        return copilotProfileDAO;
    }

    /**
     * Sets the copilotProfileDAO field.
     *
     * @param copilotProfileDAO the copilotProfileDAO to set
     */
    public void setCopilotProfileDAO(CopilotProfileDAO copilotProfileDAO) {
        this.copilotProfileDAO = copilotProfileDAO;
    }

    /**
     * Do nothing in this version.
     *
     * @throws Exception
     */
    @Override
    protected void executeAction() throws Exception {
        // do nothing.
    }

    /**
     * The method to handle the ajax request to get data for the project overview page copilots widget.
     *
     * @return the struts result message.
     */
    public String getProjectCopilotsWidgetData() {

        // get current user - TCSubject from the session
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        // Map to store the ajax result
        Map<String, Object> result = new HashMap<String, Object>();

        try {

            // Gets the copilots data of the project
            CopilotProjectDTO copilotProject = DataProvider.getCopilotProject(currentUser.getUserId(), getDirectProjectId());

            Map<String, Object> projectCopilots = new HashMap<String, Object>();

            for (CopilotBriefDTO copilot : copilotProject.getCopilots()) {

                // put the project copilots data into the ajax response
                Map<String, Object> copilotProjectInfo = new HashMap<String, Object>();
                copilotProjectInfo.put("handle", copilot.getHandle());
                copilotProjectInfo.put("userId", copilot.getUserId());
                copilotProjectInfo.put("copilotProjectId", copilot.getCopilotProjectId());

                projectCopilots.put(String.valueOf(copilot.getCopilotProfileId()), copilotProjectInfo);
            }

            result.put("projectCopilots", projectCopilots);

            // get all the other copilots not in the projects - will be displayed in the left side of the widget
            List<CopilotProfile> copilotProfiles = getCopilotProfileDAO().retrieveAll();

            Map<String, Object> otherCopilots = new HashMap<String, Object>();

            for(CopilotProfile cp : copilotProfiles) {
                if(!projectCopilots.containsKey(String.valueOf(cp.getId()))) {

                    if (cp.getStatus().getId() == 1L) {
                        // only add active copilots into the left list
                        otherCopilots.put(String.valueOf(cp.getId()), getUserService().getUserHandle(cp.getUserId()));

                    }
                }
            }

            result.put("allCopilots", otherCopilots);


            setResult(result);

        } catch (Throwable e) {

            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e.getMessage());
            }
        }

        return SUCCESS;
    }
}
