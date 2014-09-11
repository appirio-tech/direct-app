package com.topcoder.direct.services.copilot.stresstests;

import com.topcoder.management.phase.HandlerRegistryInfo;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.phase.PhaseOperationEnum;
import com.topcoder.management.phase.PhaseValidator;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

public class MockPhaseManager implements PhaseManager{

    public boolean canCancel(Phase arg0) throws PhaseManagementException {
    
        return false;
    }

    public boolean canEnd(Phase arg0) throws PhaseManagementException {
    
        return false;
    }

    public boolean canStart(Phase arg0) throws PhaseManagementException {
    
        return false;
    }

    public void cancel(Phase arg0, String arg1) throws PhaseManagementException {
    
        
    }

    public void end(Phase arg0, String arg1) throws PhaseManagementException {
    
        
    }

    public PhaseHandler[] getAllHandlers() {
    
        return null;
    }

    public PhaseStatus[] getAllPhaseStatuses() throws PhaseManagementException {
    
        return null;
    }

    public PhaseType[] getAllPhaseTypes() throws PhaseManagementException {
    
        return null;
    }

    public HandlerRegistryInfo[] getHandlerRegistrationInfo(PhaseHandler arg0) {
    
        return null;
    }

    public PhaseValidator getPhaseValidator() {
    
        return null;
    }

    public Project getPhases(long arg0) throws PhaseManagementException {
    
        return null;
    }

    public Project[] getPhases(long[] arg0) throws PhaseManagementException {
    
        return null;
    }

    public void registerHandler(PhaseHandler arg0, PhaseType arg1, PhaseOperationEnum arg2) {
    
        
    }

    public void setPhaseValidator(PhaseValidator arg0) {
    
        
    }

    public void start(Phase arg0, String arg1) throws PhaseManagementException {
    
        
    }

    public PhaseHandler unregisterHandler(PhaseType arg0, PhaseOperationEnum arg1) {
    
        return null;
    }

    public void updatePhases(Project arg0, String arg1) throws PhaseManagementException {
    
        
    }

}
