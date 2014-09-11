package com.topcoder.direct.services.project.milestone.accuracy;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.direct.services.project.metadata.DirectProjectMetadataService;
import com.topcoder.direct.services.project.metadata.EntityNotFoundException;
import com.topcoder.direct.services.project.metadata.PersistenceException;
import com.topcoder.direct.services.project.metadata.ValidationException;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.TcDirectProject;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectMetadataDTO;

public class MockDirectProjectMetadataService implements DirectProjectMetadataService {

    public long createProjectMetadata(DirectProjectMetadata projectMetadata, long userId)
        throws ValidationException, PersistenceException {
        // TODO Auto-generated method stub
        return 0;
    }

    public void updateProjectMetadata(DirectProjectMetadata projectMetadata, long userId)
        throws EntityNotFoundException, ValidationException, PersistenceException {
        // TODO Auto-generated method stub

    }

    public long saveProjectMetadata(DirectProjectMetadata projectMetadata, long userId)
        throws ValidationException, PersistenceException {
        // TODO Auto-generated method stub
        return 0;
    }

    public void deleteProjectMetadata(long projectMetadataId, long userId) throws EntityNotFoundException,
        PersistenceException {
        // TODO Auto-generated method stub

    }

    public DirectProjectMetadata getProjectMetadata(long projectMetadataId) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<DirectProjectMetadata> getProjectMetadataByProject(long tcDirectProjectId)
        throws PersistenceException {
        List<DirectProjectMetadata> result = new ArrayList<DirectProjectMetadata>();
        if (tcDirectProjectId == 1) {
            DirectProjectMetadata data = new DirectProjectMetadata();
            data.setMetadataValue("1/handle1");
            result.add(data);
            data = new DirectProjectMetadata();
            data.setMetadataValue("2/handle2");
            result.add(data);
        } else if (tcDirectProjectId == 101) {
            DirectProjectMetadata data = new DirectProjectMetadata();
            data.setMetadataValue("123456heffan");
            result.add(data);
        } else if (tcDirectProjectId == 102) {
            DirectProjectMetadata data = new DirectProjectMetadata();
            data.setMetadataValue("wrong/heffan");
            result.add(data);
        } else if (tcDirectProjectId == 103) {
            throw new PersistenceException("error in getProjectMetadataByProject");
        }
        return result;
    }

    public void addProjectMetadata(long tcDirectProjectId, List<DirectProjectMetadataDTO> projectMetadataList,
        long userId) throws ValidationException, PersistenceException {
        // TODO Auto-generated method stub

    }

    public void addProjectMetadata(long[] tcDirectProjectIds, DirectProjectMetadataDTO projectMetadata, long userId)
        throws ValidationException, PersistenceException {
        // TODO Auto-generated method stub

    }

    public List<TcDirectProject> searchProjects(DirectProjectFilter filter) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

}
