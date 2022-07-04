/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service.failuretests;

import static com.topcoder.catalog.service.failuretests.FailureTestHelper.getEntityTransaction;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.EJBException;
import javax.persistence.EntityTransaction;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.service.AssetDTO;
import com.topcoder.catalog.service.CatalogService;
import com.topcoder.catalog.service.CatalogServiceImpl;
import com.topcoder.catalog.service.EntityNotFoundException;
import com.topcoder.catalog.service.PersistenceException;
import com.topcoder.catalog.service.SearchCriteria;

/**
 * <p>
 * This class emulates behavior of the <code>CatalogService</code> component inside an EJB container.
 * </p>
 *
 * @author kaqi072821
 * @version 1.0
 */
public class CatalogServiceFailureMock implements CatalogService, Serializable {
    /**
     * <p>
     * Instance of catalog service to delegate calls to.
     * </p>
     */
    private final CatalogService catalogService;

    /**
     * <p>
     * Creates mockup by given <code>CatalogServiceImpl</code> delegate.
     * </p>
     * <p>
     * Injects entityManager to it.
     * </p>
     *
     * @param catalogService catalog service
     * @param failTest set <code>true</code> to inject <code>FailTestEntityManager</code>, <code>false</code> for
     *        accuracy testing
     * @throws RuntimeException to jUnit
     */
    public CatalogServiceFailureMock(CatalogServiceImpl catalogService, boolean failTest) {
        this.catalogService = catalogService;
        try {
            final Field field = CatalogServiceImpl.class.getDeclaredField("entityManager");
            field.setAccessible(true);
            field.set(catalogService, failTest ? new FailureTestEntityManager() : FailureTestHelper.getEntityManager());
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Invalid implementation of CatalogService: " + e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Invalid implementation of CatalogService: " + e.getMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Category> getActiveCategories() throws PersistenceException {
        try {
            return catalogService.getActiveCategories();
        } catch (RuntimeException e) {
            throw new EJBException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Technology> getActiveTechnologies() throws PersistenceException {
        try {
            return catalogService.getActiveTechnologies();
        } catch (RuntimeException e) {
            throw new EJBException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Phase> getPhases() throws PersistenceException {
        try {
            return catalogService.getPhases();
        } catch (RuntimeException e) {
            throw new EJBException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public AssetDTO getAssetById(long id, boolean currentVersion) throws EntityNotFoundException, PersistenceException {
        try {
            return catalogService.getAssetById(id, currentVersion);
        } catch (RuntimeException e) {
            throw new EJBException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public AssetDTO getAssetByVersionId(long id) throws EntityNotFoundException, PersistenceException {
        try {
            return catalogService.getAssetByVersionId(id);
        } catch (RuntimeException e) {
            throw new EJBException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public AssetDTO createAsset(AssetDTO asset) throws PersistenceException {
        final EntityTransaction tx = getEntityTransaction();
        final AssetDTO dto;
        tx.begin();
        try {
            dto = catalogService.createAsset(asset);
            tx.commit();
        } catch (PersistenceException e) {
            try {
                tx.rollback();
            } catch (Exception e1) {
                // ignore
            }
            throw e;
        } catch (RuntimeException e) {
            try {
                tx.rollback();
            } catch (Exception e1) {
                // ignore
            }
            throw new EJBException(e);
        }
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    public AssetDTO createVersion(AssetDTO asset) throws EntityNotFoundException, PersistenceException {
        final EntityTransaction tx = getEntityTransaction();
        final AssetDTO dto;
        tx.begin();
        try {
            dto = catalogService.createVersion(asset);
            tx.commit();
        } catch (PersistenceException e) {
            try {
                tx.rollback();
            } catch (Exception e1) {
                // ignore
            }
            throw e;
        } catch (EntityNotFoundException e) {
            try {
                tx.rollback();
            } catch (Exception e1) {
                // ignore
            }
            throw e;
        } catch (RuntimeException e) {
            try {
                tx.rollback();
            } catch (Exception e1) {
                // ignore
            }
            throw new EJBException(e);
        }
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    public void updateAsset(AssetDTO asset) throws EntityNotFoundException, PersistenceException {
        final EntityTransaction tx = getEntityTransaction();
        tx.begin();
        try {
            catalogService.updateAsset(asset);
            tx.commit();
        } catch (PersistenceException e) {
            try {
                tx.rollback();
            } catch (Exception e1) {
                // ignore
            }
            throw e;
        } catch (EntityNotFoundException e) {
            try {
                tx.rollback();
            } catch (Exception e1) {
                // ignore
            }
            throw e;
        } catch (RuntimeException e) {
            try {
                tx.rollback();
            } catch (Exception e1) {
                // ignore
            }
            throw new EJBException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<AssetDTO> findAssets(SearchCriteria criteria, boolean currentVersion) throws PersistenceException {
        final EntityTransaction tx = getEntityTransaction();
        tx.begin();
        try {
            final List<AssetDTO> assets = catalogService.findAssets(criteria, currentVersion);
            tx.commit();
            return assets;
        } catch (PersistenceException e) {
            try {
                tx.rollback();
            } catch (Exception e1) {
                // ignore
            }
            throw e;
        } catch (RuntimeException e) {
            try {
                tx.rollback();
            } catch (Exception e1) {
                // ignore
            }
            throw new EJBException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void assignUserToAsset(long userId, long componentId) throws EntityNotFoundException, PersistenceException {
        final EntityTransaction tx = getEntityTransaction();
        tx.begin();
        try {
            catalogService.assignUserToAsset(userId, componentId);
            tx.commit();
        } catch (PersistenceException e) {
            try {
                tx.rollback();
            } catch (Exception e1) {
                // ignore
            }
            throw e;
        } catch (EntityNotFoundException e) {
            try {
                tx.rollback();
            } catch (Exception e1) {
                // ignore
            }
            throw e;
        } catch (RuntimeException e) {
            try {
                tx.rollback();
            } catch (Exception e1) {
                // ignore
            }
            throw new EJBException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeUserFromAsset(long userId, long componentId) throws EntityNotFoundException, PersistenceException {
        final EntityTransaction tx = getEntityTransaction();
        tx.begin();
        try {
            catalogService.removeUserFromAsset(userId, componentId);
            tx.commit();
        } catch (PersistenceException e) {
            try {
                tx.rollback();
            } catch (Exception e1) {
                // ignore
            }
            throw e;
        } catch (EntityNotFoundException e) {
            try {
                tx.rollback();
            } catch (Exception e1) {
                // ignore
            }
            throw e;
        } catch (RuntimeException e) {
            try {
                tx.rollback();
            } catch (Exception e1) {
                // ignore
            }
            throw new EJBException(e);
        }
    }

	public List<AssetDTO> getAllAssetVersions(long id)
			throws EntityNotFoundException, PersistenceException {
        final EntityTransaction tx = getEntityTransaction();
        tx.begin();
        try {
            final List<AssetDTO> assets = catalogService.getAllAssetVersions(id);
            tx.commit();
            return assets;
        } catch (PersistenceException e) {
            try {
                tx.rollback();
            } catch (Exception e1) {
                // ignore
            }
            throw e;
        } catch (RuntimeException e) {
            try {
                tx.rollback();
            } catch (Exception e1) {
                // ignore
            }
            throw new EJBException(e);
        }
	}
}