package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DataProvider;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 27, 2010
 * Time: 11:38:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectGamePlanAction extends AbstractAction implements FormAction<ProjectIdForm>, ViewAction<ProjectContestsDTO> {

    /**
     * <p>A <code>ProjectIdForm</code> providing the ID of a requested project.</p>
     */
    private ProjectIdForm formData = new ProjectIdForm();

    /**
     * <p>A <code>ProjectContestsDTO</code> providing the view data for displaying by <code>Project Contests</code>
     * view.</p>
     */
    private ProjectContestsDTO viewData = new ProjectContestsDTO();

    /**
     * <p>Constructs new <code>ProjectContestsAction</code> instance. This implementation does nothing.</p>
     */
    public ProjectGamePlanAction() {
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>Object</code> providing the data for form submitted by user..
     */
    public ProjectIdForm getFormData() {
        return this.formData;
    }

    /**
     * <p>Gets the data to be displayed by view mapped to this action.</p>
     *
     * @return an <code>Object</code> providing the collector for data to be rendered by the view mapped to this action.
     */
    public ProjectContestsDTO getViewData() {
        return this.viewData;
    }

    /**
     * <p>Handles the incoming request. If action is executed successfully then changes the current project context to
     * project requested for this action.</p>
     *
     * @return a <code>String</code> referencing the next view or action to route request to. This implementation
     *         returns {@link #SUCCESS} always.
     * @throws Exception if an unexpected error occurs while processing the request.
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();
        if (SUCCESS.equals(result)) {
            getSessionData().setCurrentProjectContext(getViewData().getProjectStats().getProject());

            // set the current direct project id in session, the contest details codes incorrectly
            // use setCurrentProjectContext to override the current chosen direct project with current
            // chosen contest, for the safe, we put the direct project id into session separately again
            getSessionData().setCurrentSelectDirectProjectID(getSessionData().getCurrentProjectContext().getId());

            // set user projects
            List<ProjectBriefDTO> projects = DataProvider
                    .getUserProjects(getSessionData().getCurrentUserId());
            UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
            userProjectsDTO.setProjects(projects);
            viewData.setUserProjects(userProjectsDTO);
            
            return SUCCESS;
        } else {
            return result;
        }
    }
}
