package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.form.ProjectForm;
import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.project.ProjectData;

public class CreateProjectAndAddContestAction extends AbstractAction implements FormAction<ProjectForm>, ViewAction<ProjectBriefDTO> {
    /**
     * <p>A <code>ProjectForm</code> providing the input parameters submitted by user.</p>
     */
    private ProjectForm formData = new ProjectForm();

	public ProjectBriefDTO getViewData() {
		// TODO Auto-generated method stub
		return null;
	}

	public ProjectForm getFormData() {
		return formData;
	}

	private ProjectServiceFacade projectServiceFacade;
    
    public ProjectServiceFacade getProjectServiceFacade() {
		return projectServiceFacade;
	}

	public void setProjectServiceFacade(ProjectServiceFacade projectServiceFacade) {
		this.projectServiceFacade = projectServiceFacade;
	}
	
	@Override
	public String execute() throws Exception {
		String result = super.execute();
        if (SUCCESS.equals(result)) {
        	ProjectServiceFacade projectServiceFacade = getProjectServiceFacade();

            if (null == projectServiceFacade) {
                throw new IllegalStateException("The project service facade is not initialized");
            }

            // create the project data with the input parameters.
            ProjectData projectData = new ProjectData();
            projectData.setName(getFormData().getName());
            projectData.setDescription(getFormData().getDescription());
            
            // delegate to ProjectServiceFacade to create the project.
            projectData = projectServiceFacade.createProject(DirectStrutsActionsHelper.getTCSubjectFromSession(), projectData);
            ProjectBriefDTO dto = new ProjectBriefDTO();
            dto.setId(projectData.getProjectId());
            dto.setName(projectData.getName());
            getSessionData().setCurrentProjectContext(dto);
            return SUCCESS;
        } else {
            return result;
        }
	}
	
	

}
