/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collection;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompClient;
import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.entity.CompForum;
import com.topcoder.catalog.entity.CompLink;
import com.topcoder.catalog.entity.CompUploadedFile;
import com.topcoder.catalog.entity.CompUser;
import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.CompVersionDates;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Status;
import com.topcoder.catalog.entity.Technology;

/**
 * <p>This class implements the contract for the catalog services.</p>
 * <p>It provides facilities to retrieve <code>Components</code>, <code>Categories</code>, <code>Technologies</code>
 * and <code>Phases</code> from and to modify <code>Components</code> in the persistence.</p>
 * <p><strong>Thread safety: </strong></p>
 * <p>
 * version 1.1 add method <code>getAllAssetVersions(long)</code> which get all versions with same asset id.
 * </p>
 *
 * <p>
 * Changes in v1.2 (Cockpit Upload Attachment):
 * - Added entity manager to checkAssetDocumentation to be able to delete removed documents from persistence.
 * - Fixed multiple file upload logic in checkAssetDocumentation.
 * - Removed unnecessary document type inference from storeUploadedFile and convertUploadedFileToCompDocumentation.
 * - Removed getAllDocumentTypes method and calls, it's not used anymore.
 * - Added asset.documentation refresh after create/update asset.
 * </p>
 *
 * <p>this class is thread safe because all operations are transactions in the EJB environment.</p>
 *
 * @author caru, Retunsky, pulky
 * @version 1.2
 * @since 1.0
 */
@Stateless
public class CatalogServiceImpl implements CatalogServiceLocal, CatalogServiceRemote {
    /**
     * <p>CompVersionDates level ID.</p>
     */
    private static final long LEVEL_ID = 100L;
    /**
     * <p>Collaboration phase ID.</p>
     */


    /**
     * <p>This field represents the entity manager which is used to communicate with the persistence.</p>
     * <p>It's automatically injected by EJB container.</p>
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This is the root directory of uploaded files.
     * @since BUGR-1600
     */
    @Resource(name = "uploadedFilesRootDir")
    private String uploadedFilesRootDir;

    /**
     * <p>Default constructor.</p> <p><em>Does nothing.</em></p>
     */
    public CatalogServiceImpl() {
    }

    /**
     * <p>Returns a list containing all active <code>Categories</code>.</p>
     *
     * @return a list containing all active <code>Categories</code>. It can be empty if no objects found.
     * @throws PersistenceException if an error occurs when interacting with the persistence store,
     *                              including invalid named query configuration
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Category> getActiveCategories() throws PersistenceException {
        return retrieveList(Category.class, "getActiveCategories");
    }

    /**
     * <p>Returns a list containing all active <code>Technologies</code>.</p>
     *
     * @return a list containing all active <code>Categories</code>. It can be empty if no objects found.
     * @throws PersistenceException if an error occurs when interacting with the persistence store,
     *                              including invalid named query configuration
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Technology> getActiveTechnologies() throws PersistenceException {
        return retrieveList(Technology.class, "getActiveTechnologies");
    }

    /**
     * <p>Returns a list containing all <code>Phases</code>.</p>
     *
     * @return a list containing all active <code>Categories</code>. It can be empty if no objects found.
     * @throws PersistenceException if an error occurs when interacting with the persistence store,
     *                              including invalid named query configuration
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Phase> getPhases() throws PersistenceException {
        return retrieveList(Phase.class, "getAllPhases");
    }

    /**
     * <p>Returns a <code>AssetDTO</code> instance describing the asset with specified id,
     * with an option of retrieving whether the current or the latest version.</p>
     *
     * @param id             the id of the asset
     * @param currentVersion if <code>true</code>, retrieve the current version, otherwise retrieve the latest one
     * @return a <code>AssetDTO</code> instance describing the asset (cannot be <code>null</code>)
     * @throws EntityNotFoundException if the asset is not found in persistence
     * @throws PersistenceException    if an error occurs when interacting with the persistence store.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AssetDTO getAssetById(long id, boolean currentVersion)
        throws PersistenceException, EntityNotFoundException {
        try {
            // get the Component entity
            Component component = findComponentById(id);

            // get the latest and the current versions
            final CompVersion theLatestVersion = getLatestVersion(component);
            final CompVersion theCurrentVersion = component.getCurrentVersion();

            // assign to component version the current or the last, depending on the passed flag
            final CompVersion compVersion = currentVersion ? theCurrentVersion : theLatestVersion;

            // create AssetDTO from the component and appropriate version
            return createAssetDTO(component, compVersion);

        } catch (javax.persistence.PersistenceException e) {
            // any other errors, e.g. on JDBC level are wrapped to it
            throw new PersistenceException("Unexpected persistence error occurred: " + e.getMessage(), e);
        } catch (IllegalStateException e) {
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        }
    }

    /**
     * <p>Returns a <code>AssetDTO</code> instance describing the asset containing
     * the version with specified id.</p>
     *
     * @param id the id of the asset's version
     * @return a <code>AssetDTO</code> instance describing the asset (cannot be <code>null</code>)
     * @throws EntityNotFoundException if the asset is not found in persistence
     * @throws PersistenceException    if an error occurs when interacting with the persistence store
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AssetDTO getAssetByVersionId(long id)
        throws PersistenceException, EntityNotFoundException {
        try {
            // get the CompVersion entity
            CompVersion compVersion = getEntityManager().find(CompVersion.class, id);
            if (compVersion == null) {
                throw new EntityNotFoundException("CompVersion with id=" + id + " not found");
            }
            if (compVersion.getComponent() == null) {
                throw new PersistenceException("CompVersion with id=" + id + " doesn't have a Component entity.");
            }
            // build assetDTO from the version's Component and itself
            return createAssetDTO(compVersion.getComponent(), compVersion);
        } catch (javax.persistence.PersistenceException e) {
            // any other errors, e.g. on JDBC level are wrapped to it
            throw new PersistenceException("Unexpected persistence error occurred: " + e.getMessage(), e);
        } catch (IllegalStateException e) {
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        }
    }

    /**
     * <p>Creates a new <code>AssetDTO</code> in the persistence.</p>
     *
     * @param asset a <code>AssetDTO</code> instance describing the asset
     * @return created asset with populated <code>asset.id</code> and <code>asset.versionId</code> fields.
     * @throws IllegalArgumentException if any of the following conditions met:
     * <ul>
     * <li>if the parameter is <code>null</code></li>
     * <li>if the <code>asset.id</code> has non-null value</li>
     * <li>if the <code>asset.versionId</code> has non-null value</li>
     * <li>if the <code>asset.name</code> is <code>null</code> or empty</li>
     * <li>if the <code>asset.versionText</code> is <code>null</code> or empty</li>
     * <li>if the <code>asset.shortDescription</code> is <code>null</code> or empty</li>
     * <li>if the <code>asset.detailedDescription</code> is <code>null</code> or empty</li>
     * <li>if the <code>asset.rootCategory</code> is <code>null</code></li>
     * <li>if the <code>asset.functionalDescription</code> is <code>null</code> or empty</li>
     * <li>if the <code>asset.categories</code> is <code>null</code>, empty or contains <code>null</code> item</li>
     * <li>if the <code>asset.technologies</code> is <code>null</code>,
     * empty or contains <code>null</code> item</li>
     * <li>if the <code>asset.documentation</code> is <code>null</code>,
     * contains <code>null</code> item</li>
     * </ul>
     * @throws PersistenceException     if an error occurs when interacting with the persistence store.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AssetDTO createAsset(AssetDTO asset) throws PersistenceException {
        checkNotNull("asset", asset);
        if (asset.getId() != null) {
            throw new IllegalArgumentException("Component already has id=" + asset.getId()
                + " and cannot be created.");
        }
        checkNullVersionId(asset);
        // create a new asset entity
        final Component entityComponent = new Component();
        entityComponent.setStatus(Status.APPROVED);
        // update the new asset entity and create a new version
        final CompVersion compVersion = updateAssetCreateVersion(asset, entityComponent);
        // set the created version as current
        entityComponent.setCurrentVersion(compVersion);

        final EntityManager em = getEntityManager();


        // save the asset entity finally
        persistEntity(em, entityComponent);


        // save version
        persistEntity(em, compVersion);

        populateVersionDocumentation(asset, em, compVersion); // BUGR-1600.2
        for(CompDocumentation doc : compVersion.getDocumentation()) { // BUGR-1600.2
            mergeEntity(em, doc);
        }

        // Refresh documentation collection in asset.
        asset.setDocumentation(new ArrayList<CompDocumentation>(compVersion.getDocumentation()));

        // populate with ids of just stored entities
        asset.setId(entityComponent.getId());
        asset.setCompVersionId(compVersion.getId());
        asset.setVersionNumber(compVersion.getId());
        asset.setVersionText(compVersion.getVersionText());
        asset.setVersion(compVersion.getVersion());

        // and return it
        return asset;
    }

    /**
     * <p>
     * Creates a new version for the given <code>AssetDTO</code> and update it in the persistence.
     * </p>
     *
     * @param asset
     *            a <code>AssetDTO</code> instance describing the asset
     * @return updated asset with populated <code>asset.versionId</code> field.
     *
     * @throws IllegalArgumentException
     *             if any of the following conditions met:
     *             <ul>
     *             <li>if the parameter is <code>null</code></li>
     *             <li>if the <code>asset.id</code> has <code>null</code> value</li>
     *             <li>if the <code>asset.versionId</code> has non-null value</li>
     *             <li>if the <code>asset.name</code> is <code>null</code> or empty</li>
     *             <li>if the <code>asset.versionText</code> is <code>null</code> or empty</li>
     *             <li>if the <code>asset.shortDescription</code> is <code>null</code> or empty</li>
     *             <li>if the <code>asset.detailedDescription</code> is <code>null</code> or
     *             empty</li>
     *             <li>if the <code>asset.functionalDescription</code> is <code>null</code> or
     *             empty</li>
     *             <li>if the <code>asset.rootCategory</code> is <code>null</code></li>
     *             <li>if the <code>asset.categories</code> is <code>null</code>, empty or
     *             contains <code>null</code> item</li>
     *             <li>if the <code>asset.technologies</code> is <code>null</code>, empty or
     *             contains <code>null</code> item</li>
     *             <li>if the <code>asset.documentation</code> is <code>null</code>, or
     *             contains <code>null</code> item</li>
     *             </ul>
     * @throws EntityNotFoundException
     *             if the asset is not found in persistence
     * @throws PersistenceException
     *             if an error occurs when interacting with the persistence store.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AssetDTO createVersion(AssetDTO asset) throws EntityNotFoundException, PersistenceException {
        checkNotNull("asset", asset);
        checkComponentIdNotNull(asset);
        checkNullVersionId(asset);
        final EntityManager em = getEntityManager();
        // retrieve the asset entity
        final Component entityComponent = findComponentById(asset.getId());
        // update the asset entity and create a new version
        final CompVersion compVersion = updateAssetCreateVersion(asset, entityComponent);
        // persist the new version
        persistEntity(em, compVersion);
        // update the asset entity finally
        mergeEntity(em, entityComponent);

        // populate with ids of just stored entities
        asset.setId(entityComponent.getId());
        asset.setCompVersionId(compVersion.getId());
        asset.setVersionNumber(compVersion.getId());
        asset.setVersionText(compVersion.getVersionText());
        asset.setVersion(compVersion.getVersion());
        return asset;
    }

    /**
     * <p>Updates an <code>AssetDTO</code> in the persistence (without its version properties).</p>
     *
     * @param asset a <code>AssetDTO</code> instance describing the asset
     *
     * @throws IllegalArgumentException if any of the following conditions met:
     * <ul>
     *  <li>if the parameter is <code>null</code></li>
     *  <li>if the <code>asset.id</code> has <code>null</code> value</li>
     *  <li>if the <code>asset.name</code> is <code>null</code> or empty</li>
     *  <li>if the <code>asset.shortDescription</code> is <code>null</code> or empty</li>
     *  <li>if the <code>asset.detailedDescription</code> is <code>null</code> or empty</li>
     *  <li>if the <code>asset.functionalDescription</code> is <code>null</code> or empty</li>
     *  <li>if the <code>asset.rootCategory</code> is <code>null</code></li>
     *  <li>if the <code>asset.categories</code> is <code>null</code>, empty or contains <code>null</code> item</li>
     * </ul>
     * @throws EntityNotFoundException if the asset is not found in persistence, or version not found (if specified)
     * @throws PersistenceException     if an error occurs when interacting with the persistence store.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AssetDTO updateAsset(AssetDTO asset) throws PersistenceException, EntityNotFoundException {
        checkNotNull("asset", asset);
        checkComponentIdNotNull(asset);

        final EntityManager em = getEntityManager();
        // retrieve the asset entity
        final Component entityComponent = findComponentById(asset.getId());
        CompVersion versionToUpdate = null;
        if (asset.getCompVersionId() != null) { // if there is a version to update
            for (CompVersion compVersion : entityComponent.getVersions()) {
                if (compVersion.getId().equals(asset.getCompVersionId())) {
                    versionToUpdate = compVersion;
                    break;
                }
            }
            if (versionToUpdate == null) {
                throw new EntityNotFoundException("CompVersion with id=" + asset.getCompVersionId() + " not found");
            }
        }
        else
        {
            throw new EntityNotFoundException("Asset Version Id is null");
        }

        entityComponent.setCurrentVersion(versionToUpdate);

        // if phase is complted, udpate comp version and comp version dates
        if (asset.getPhase().equals("Completed"))
        {
            Phase phase = getEntityManager().find(Phase.class, Phase.COMPLETED_PHASE_ID);
            versionToUpdate.setPhase(phase);
            
            if (versionToUpdate.getVersionDates().get(Phase.COMPLETED_PHASE_ID) == null)
            {
                final Date zeroPointDate = buildDate(1976, 5, 5);
                final Date stubDate = buildDate(2000, 1, 1);
                final CompVersionDates compVersionDates = createInitialCompVersionDates(asset, zeroPointDate, stubDate);
                compVersionDates.setPhase(phase);
                compVersionDates.setCompVersion(versionToUpdate);
                compVersionDates.setProductionDate(getDate(asset.getProductionDate()));
                versionToUpdate.getVersionDates().put(Phase.COMPLETED_PHASE_ID, compVersionDates);
            }
           
        }

        // update the asset entity
        updateAsset(asset, entityComponent, versionToUpdate);

         // BUGR-1600 update the compVersion with uploaded /removed docs and persist it again.
        // Note that we need component entity to be existing for this, so persist twice here
        populateVersionDocumentation(asset, em, versionToUpdate);

        // update documentation collection
        for(CompDocumentation doc : versionToUpdate.getDocumentation()) {
            mergeEntity(em, doc);
        }

        // update the asset entity finally
        mergeEntity(em, entityComponent);

        // Refresh documentation collection in asset.
        asset.setDocumentation(new ArrayList<CompDocumentation>(versionToUpdate.getDocumentation()));

        return asset;
    }

    /**
     * By a given criteria returns a list of <code>AssetDTOs</code> containing only the following information:
     * <ul>
     *  <li>Id</li>
     *  <li>VersionId</li>
     *  <li>Name</li>
     *  <li>Version (text)</li>
     *  <li>Root Category</li>
     *  <li>Short Description</li>
     *  <li>component create after date</li>
     *  <li>component create before date</li>
     *  <li>component version create after date</li>
     *  <li>component version create before date</li>
     * </ul>
     * <p>It is indicated in the DTO that the information is not complete
     * (by property {@link AssetDTO#informationComplete}).</p>
     * <p>It is possible to choose whether to retrieve the current version or the last one.</p>
     * The search criteria is:
     * <ul>
     *  <li><tt>userId</tt>: If a <code>userId</code> is specified, the asset must meet one or of these conditions:
     *   <ol>
     *    <li>In the AssetDTO, users list must contain a user with that user id</li>
     *    <li>In the AssetDTO, clients list must contain a client id which is associated with the user id
     *        (via <code>CompUser</code> entity)</li>
     *    </ol>
     *  </li>
     * <li><tt>clientId</tt>: in the AssetDTO, clients list must contain the client id.</li>
     * <li>List of <tt>categories</tt>: given a list of category ids, the root category of the asset must be
     * one of those.</li>
     * <li>the AssetDTO <tt>name</tt>: seeks for partial case insensitive matches.</li>
     * <li><tt>descriptions</tt>: it must search in all the description fields,
     *   seeks for partial case insensitive matches</li>
     *  <li>compCreateAfterDate seeks for component create after this date(include)</li>
     *  <li>compCreateBeforeDate seeks for component version create before this date(include)</li>
     *  <li>compVersionCreateAfterDate seeks for component version create after this date(include)</li>
     *  <li>compVersionCreateBeforeDate seeks for component version create before this date(include)</li>
     * </ul>
     * For the algorithm details look at the CS 1.3.3.
     * @param criteria the search criteria
     * @param currentVersion if <code>true</code>, retrieve the current version, otherwise retrieve the latest one
     *
     * @return a list of <code>AssetDTO</code> partially describing the asset (can be empty if none is found)
     *
     * @throws IllegalArgumentException if criteria is <code>null</code>
     * @throws PersistenceException     if an error occurs when interacting with the persistence store.
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<AssetDTO> findAssets(SearchCriteria criteria, boolean currentVersion)
        throws PersistenceException {
        checkNotNull("criteria", criteria);
        // create a native query, as maximum four tables can be joined, and it's much faster
        // that query all then boot off records
        // create initial query and add 'where', as there couldn't be an empty criteria
        final StringBuilder subQueryString = new StringBuilder("SELECT DISTINCT C.component_id FROM comp_catalog C\n");
        // build query and get parameters map for it
        final Map<String, Object> parameters = buildSubQuery(criteria, subQueryString);

        final EntityManager em = getEntityManager();

        // create a single query to retrieve the data
        // JOIN approach can be by 'current_version' if the currentVersion parameter is true
        // or from versions to components by 'component_id' otherwise
        // in latter case we select only the version with maximum number
        String queryString = "SELECT A.component_id, A.component_name, A.short_desc,"
            + " A.root_category_id, B.version_text, B.comp_vers_id FROM comp_catalog A\n"
            + "JOIN comp_versions B ON "
            + (currentVersion ? "A.current_version=B.version and A.component_id = B.component_id" : "A.component_id=B.component_id") // join type
            + "\n where A.component_id IN (" + subQueryString + ")\n" // only in eligible components' ids
            + (currentVersion ? ""
            : "AND version=(SELECT MAX(version) FROM comp_versions WHERE component_id=A.component_id)"); // latest one

        try {
            em.clear(); // clear caches, as commenting this line leads to a hibernate bug
            final Query query = em.createNativeQuery(queryString, AssetDTO.class);
            for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
                query.setParameter(parameter.getKey(), parameter.getValue());
            }
            // this is read-only entities
            query.setHint("org.hibernate.readOnly", true);
            return (List<AssetDTO>) query.getResultList();
        } catch (javax.persistence.PersistenceException e) {
            // any other errors, e.g. on JDBC level are wrapped to it
            throw new PersistenceException("Unexpected error occurred on underlying persistence level:"
                + e.getMessage(), e);
        } catch (IllegalStateException e) {
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        }
    }

    /**
     * <p>Assigns a specified user to a specified <code>assetDTO</code>.</p>
     * <p>If a user already assigned to the asset, this method simply does nothing.</p>
     *
     * @param userId the id of the user
     * @param assetId the id of the assetDTO
     *
     * @throws EntityNotFoundException  if the assetDTO is not found in persistence
     * @throws PersistenceException     if an error occurs when interacting with the persistence store.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void assignUserToAsset(long userId, long assetId)
        throws PersistenceException, EntityNotFoundException {
        final EntityManager em = getEntityManager();
        // retrieve the component entity
        final Component entityComponent = findComponentById(assetId);
        // update the component entity
        Set<CompUser> compUserSet = entityComponent.getUsers();
        if (compUserSet == null) {
            compUserSet = new HashSet<CompUser>();
            entityComponent.setUsers(compUserSet);
        } else {
            for (CompUser compUser : compUserSet) {
                if (compUser.getUserId() == userId) {
                    return; // do nothing if association already exists
                }
            }
        }
        final CompUser compUser = new CompUser();
        compUser.setComponent(entityComponent);
        compUser.setUserId(userId);
        compUserSet.add(compUser);
        // update the component entity finally
        persistEntity(em, compUser);
        mergeEntity(em, entityComponent);
    }


    /**
     * <p>create comp forum</p>
     *
     * @param compVersId
     * @param jiveCategoryId
     *
     * @throws PersistenceException     if an error occurs when interacting with the persistence store.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addCompForum(long compVersId, long jiveCategoryId) throws PersistenceException
    {
        // we have to create component and version first so that we can
        // create the forum, then we need to create compForum, since
        // pk is forumId + compVersionsId, we have to use native query here

        final EntityManager em = getEntityManager();

        Query query = em.createNamedQuery("addCompForum");
        query.setParameter(1, compVersId);
        query.setParameter(2, jiveCategoryId);
        query.executeUpdate();

    }

    /**
     * <p>Removes a specified user from a specified <code>assetDTO</code>.</p>
     *
     * @param userId the id of the user
     * @param assetId the id of the asset
     *
     * @throws EntityNotFoundException
     *                  if the assetDTO is not found in persistence, or the user is not assigned to the asset
     * @throws PersistenceException
     *                 if an error occurs when interacting with the persistence store
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void removeUserFromAsset(long userId, long assetId)
        throws PersistenceException, EntityNotFoundException {
        final EntityManager em = getEntityManager();
        // retrieve the component entity
        final Component entityComponent = findComponentById(assetId);
        // update the component entity
        Set<CompUser> compUserSet = entityComponent.getUsers();
        boolean removed = false;
        if (compUserSet != null) {
            for (Iterator<CompUser> iterator = compUserSet.iterator(); iterator.hasNext();) {
                CompUser compUser = iterator.next();
                if (compUser.getUserId() == userId) {
                    iterator.remove();
                    removed = true;
                    break;
                }
            }
        }
        // if there were no association then report with exception
        if (!removed) {
            throw new EntityNotFoundException("User with id=" + userId + " was not assigned to"
                + " assetDTO with id=" + assetId);
        }
        // update the component entity finally
        mergeEntity(em, entityComponent);
    }

    /**
     * <p>retrieve all the versions of an <code>assetDTO</code> using given assetId.</p>
     *
     * @param assetId the assetId
     * @return a list where each element is a version of the asset, ordered by CompVersion.version
     * @throws EntityNotFoundException  if the assetDTO is not found in persistence
     * @throws PersistenceException     if an error occurs when interacting with the persistence store.
     * @since version 1.1
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<AssetDTO> getAllAssetVersions(long assetId) throws PersistenceException, EntityNotFoundException{
        Component component = findComponentById(assetId);
        List<CompVersion> versions = component.getVersions();
        // sort version
        Collections.sort(versions, new Comparator<CompVersion>() {
            public int compare(CompVersion o1, CompVersion o2) {
                long sub = o1.getId() - o2.getId();
                return (sub == 0) ? 0 : (sub > 0 ? 1 : -1);
            }
        });
        List<AssetDTO> resDTO =new ArrayList<AssetDTO>();
        // translate to DTO
        for (CompVersion version : versions) {
            resDTO.add(createAssetDTO(component, version));
        }
        return resDTO;
    }


    /**
     * <p>create dev component, basically insert a new row in CompVersionsDates</p>
     *
     * @param asset a <code>AssetDTO</code> instance describing the asset
     *
     * @throws EntityNotFoundException if the asset is not found in persistence, or version not found (if specified)
     * @throws PersistenceException     if an error occurs when interacting with the persistence store.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AssetDTO createDevComponent(AssetDTO asset) throws PersistenceException, EntityNotFoundException {
        checkNotNull("asset", asset);
        checkComponentIdNotNull(asset);

        final EntityManager em = getEntityManager();
        // retrieve the asset entity
        final Component entityComponent = findComponentById(asset.getId());
        CompVersion versionToUpdate = null;
        if (asset.getCompVersionId() != null) { // if there is a version to update
            for (CompVersion compVersion : entityComponent.getVersions()) {
                if (compVersion.getId().equals(asset.getCompVersionId())) {
                    versionToUpdate = compVersion;
                    break;
                }
            }
            if (versionToUpdate == null) {
                throw new EntityNotFoundException("CompVersion with id=" + asset.getCompVersionId() + " not found");
            }
        }
        else
        {
            throw new EntityNotFoundException("Asset Version Id is null");
        }

        final Date zeroPointDate = buildDate(1976, 5, 5);
        //final Phase collaborationPhase = getEntityManager().find(Phase.class, COLLABORATION_PHASE_ID);

        Phase phase =  getEntityManager().find(Phase.class, Phase.DEVELOPMENT_PHASE_ID);

        // populate with CompVersionDates
        final Date stubDate = buildDate(2000, 1, 1);

        final CompVersionDates compVersionDates = createInitialCompVersionDates(asset, zeroPointDate, stubDate);
        compVersionDates.setPhase(phase);
        compVersionDates.setCompVersion(versionToUpdate);

        versionToUpdate.getVersionDates().put(Phase.DEVELOPMENT_PHASE_ID, compVersionDates);

        persistEntity(em, versionToUpdate);

        asset.setVersionNumber(versionToUpdate.getId());
        asset.setVersionText(versionToUpdate.getVersionText());
        asset.setVersion(versionToUpdate.getVersion());


        return asset;


    }

    /**
     * <p>
     * Retrieves the entity manager injected by EJB container for persistence operations.
     * </p>
     *
     * @return the entity manager injected by EJB container for persistence operations
     * @throws PersistenceException
     *             if the entity manager is <code>null</code>
     */
    private EntityManager getEntityManager() throws PersistenceException {
        if (entityManager == null) {
            throw new PersistenceException("No EntityManager injected to the bean.");
        }
        return entityManager;
    }

    /**
     * <p>Creates <code>AssetDTO</code> instance by a given <code>Component</code> entity instance, which has
     * been retrieved from the persistence.</p>
     * <p>Marks returned object as completed and populates <code>isCurrentVersionAlsoLatestVersion</code> flag with
     * appropriate value (<code>true</code>, if the last version is current one).</p>
     *
     * @param component   the original component entity from which to create DTO representation
     * @param compVersion the version of the component entity
     * @return
     *    a <code>AssetDTO</code> corresponding the given <code>Component</code> and <code>CompVersion</code> entities
     * @throws PersistenceException if versions list is empty (which means error on persistence level, as there always
     *                              should be non-empty list of versions in a component entity).
     */
    private AssetDTO createAssetDTO(Component component, CompVersion compVersion) throws PersistenceException {
        final AssetDTO assetDTO = new AssetDTO();

        // and set current-also-latest flag with a proper value
        assetDTO.setCurrentVersionAlsoLatestVersion(
            component.getCurrentVersion().getVersion().equals(getLatestVersion(component).getVersion()));

        // force hibernate to retrieve lazy collection
        List<Category> categories = new ArrayList<Category>();
        if (component.getCategories() != null) {
            for (Category category : component.getCategories()) {
                categories.add(category);
            }
        }
        org.hibernate.Hibernate.initialize(component.getRootCategory());
        assetDTO.setCategories(categories);
        populateClientAndUsersIds(component, assetDTO);

        // fill flat properties
        assetDTO.setDetailedDescription(component.getDescription());
        assetDTO.setForum(compVersion.getForum());
        assetDTO.setFunctionalDescription(component.getFunctionalDesc());
        assetDTO.setId(component.getId());
        assetDTO.setInformationComplete(true);
        assetDTO.setLink(compVersion.getLink());
        assetDTO.setName(component.getName());
        assetDTO.setRootCategory(component.getRootCategory());
        assetDTO.setShortDescription(component.getShortDesc());

        //force hibernate to retrieve lazy collection
        List<Technology> technologies = new ArrayList<Technology>();
        if (compVersion.getTechnologies() != null) {
            // actually, getTechnologies will not be null
            // just to make code more robust
            for (Technology tech : compVersion.getTechnologies()) {
                technologies.add(tech);
            }
        }
        assetDTO.setTechnologies(technologies);

        // here is the new lines add in bug fix.
        // start here
        List<Long> dependencies = new ArrayList<Long>();
        if (compVersion.getDependencies() != null) {
            // actually, getDependencies will not be null
            // just to make code more robust
            for (CompVersion com : compVersion.getDependencies()) {
                dependencies.add(com.getId());
            }
        }
        assetDTO.setDependencies(dependencies);
        assetDTO.setCompComments(compVersion.getComments());
        assetDTO.setPhase(compVersion.getPhase().getDescription());
        // end here.

        // extract production date, if it's available
        final CompVersionDates versionDates = compVersion.getVersionDates().get(compVersion.getPhase().getId());
        if (versionDates != null) {
            assetDTO.setProductionDate(getXMLGregorianCalendar(versionDates.getProductionDate()));
        }

        // set version properties
        assetDTO.setCompVersionId(compVersion.getId());
        assetDTO.setVersionText(compVersion.getVersionText());
        assetDTO.setVersionNumber(compVersion.getId());
        // force lazy collection
        List<CompDocumentation> documentation = new ArrayList<CompDocumentation>();
        if (compVersion.getDocumentation() != null) {
            for (CompDocumentation doc : compVersion.getDocumentation()) {
                documentation.add(entityManager.find(CompDocumentation.class, doc.getId()));
            }
        }
        assetDTO.setDocumentation(documentation);
        return assetDTO;
    }

    /**
     * Populates clients' ids from <code>CompClients</code> and user's ids from <code>CompUsers</code>
     * of <code>Component</code> entity to the given <code>AssetDTO</code>.
     * @param component the component entity
     * @param assetDTO the assetDTO to populate with data
     */
    private void populateClientAndUsersIds(Component component, AssetDTO assetDTO) {
        // extract clientIds
        final List<Long> clientIds = new ArrayList<Long>();
        final Set<CompClient> compClients = component.getClients();
        if (compClients != null && !compClients.isEmpty()) {
            for (CompClient compClient : compClients) {
                clientIds.add(compClient.getClientId());
            }
            assetDTO.setClientIds(clientIds);
        }

        // extract userIds
        final Set<CompUser> compUsers = component.getUsers();
        if (compUsers != null && !compUsers.isEmpty()) {
            final List<Long> userIds = new ArrayList<Long>();
            for (CompUser compUser : compUsers) {
                userIds.add(compUser.getUserId());
            }
            assetDTO.setUserIds(userIds);
        }
    }

    /**
     * <p>Updates a <code>Component</code> entity by populating fields with given <code>AssetDTO</code> instance,
     * creates a new <code>CompVersion</code>, which fields are also populated from the given <code>AssetDTO</code>.</p>
     *
     * @param assetDTO the original assetDTO from which to create an entity
     * @param component    component entity to update with
     * @return a <code>Component</code> entity which fields populated form the given <code>AssetDTO</code>
     * @throws IllegalArgumentException if any of the following conditions met:
     * <ul>
     * <li>if the <code>assetDTO.name</code> is <code>null</code> or empty</li>
     * <li>if the <code>assetDTO.versionText</code> is <code>null</code> or empty</li>
     * <li>if the <code>assetDTO.shortDescription</code> is <code>null</code> or empty</li>
     * <li>if the <code>assetDTO.detailedDescription</code> is <code>null</code> or empty</li>
     * <li>if the <code>assetDTO.functionalDescription</code> is <code>null</code> or empty</li>
     * <li>if the <code>assetDTO.rootCategory</code> is <code>null</code></li>
     * <li>if the <code>assetDTO.categories</code> is <code>null</code>, empty or contains <code>null</code> item</li>
     * <li>if the <code>assetDTO.technologies</code> is <code>null</code>,
     * empty or contains <code>null</code> item</li>
     * <li>if the <code>assetDTO.documentation</code> is <code>null</code>,
     * or contains <code>null</code> item</li>
     * </ul>
     * @throws javax.persistence.PersistenceException
     *                                  re-thrown from underlying persistence level
     * @throws PersistenceException     if any passed entity cannot be found in the database, or the EntityManager
     *                                  cannot be obtained
     */
    private CompVersion updateAssetCreateVersion(AssetDTO assetDTO, Component component)
        throws PersistenceException {
        // check the version text and the technologies are valid
        checkString("versionText", assetDTO.getVersionText());
        // techonogies can be null for conceptualization and specification
        //checkList("technologies", assetDTO.getTechnologies());
        List<CompDocumentation> documentation = assetDTO.getDocumentation();
        checkNotNull("documentation", documentation);
        if (documentation.contains(null)) {
            throw new IllegalArgumentException("List 'documentation' cannot contain a null value");
        }
        // update component attributes
        updateAsset(assetDTO, component, null);

        final EntityManager em = getEntityManager();

        // create version
        CompVersion compVersion = new CompVersion();
        compVersion.setPhase(new Phase());
        populateVersionTechnologies(assetDTO, em, compVersion);

        // here is the new code line add in bug fix.
        // start here.
        populateVersionDependencies(assetDTO, em, compVersion);
        compVersion.setComments(assetDTO.getCompComments());
        compVersion.getPhase().setDescription(assetDTO.getPhase());
        // end

        compVersion.setVersionText(assetDTO.getVersionText());
        populateForumAndLink(assetDTO, compVersion);
        // create CompVersionDates

        // get latest version, if component was saved
        final CompVersion latestVersion = component.getId() == null ? null : getLatestVersion(component);
        // if one exists, then increase the version, otherwise set to the first
        compVersion.setVersion(latestVersion == null ? 1L : latestVersion.getVersion() + 1);

        if (latestVersion == null)
        {
            compVersion.setVersionText(compVersion.getVersion()+".0");
        }
        else
        {
            String lastVersionText = latestVersion.getVersionText();
            String[] versions =  lastVersionText.split("\\.");

            // should not happen, 
            if (versions.length < 2)
            {
                compVersion.setVersionText(compVersion.getVersion()+".0");
            }
            else
            {
                int major = Integer.parseInt(versions[0].trim());
                int minor = Integer.parseInt(versions[1].trim());

                if (assetDTO.getToCreateMinorVersion() == null || 
                    (!assetDTO.getToCreateMinorVersion().booleanValue())) 
                {
                    major++;
                    minor = 0;
                }
                else 
                {
                    minor++;
                }
                compVersion.setVersionText(major+"."+minor);
            }

        }

        compVersion.setComponent(component);
        List<CompVersion> versions = component.getVersions();
        if (versions == null) {
            versions = new ArrayList<CompVersion>();
        }
        populateCompVersionDates(assetDTO, compVersion);

        versions.add(compVersion);
        component.setVersions(versions);

        return compVersion;
    }

    /**
     *
     * @param file
     * @since BUGR-1600
     */
    private void deleteFileTree(File file) {
        if(file.isDirectory()) {
            for(File f : file.listFiles()) {
                deleteFileTree(f);
            }
        }
        file.delete();
    }

    /**
     * Updates the documentation.
     * On the front-end, user has 2 options:
     * - delete existing doc (if the documentation is associated, but is no longer in assetDTO.documentation)
     * - upload new doc (which means item in assetDTO.uploadedFiles)
     *
     * Note: the logic in this method was wrong and was not working with multiple files. It was fixed in v1.2.
     *
     * @param assetDTO
     * @param compVersion
     * @param em the entity manager used to delete removed documentation
     * @throws PersistenceException
     * @throws PersistenceException
     * @throws InterruptedException
     * @throws IOException
     * @since BUGR-1600
     */
    private void checkAssetDocumentation(AssetDTO assetDTO, EntityManager em, CompVersion compVersion)
        throws PersistenceException { // BUGR-1600.2 do not return value
        System.out.println("Enter checkAssetDocumentation");
        System.out.println("docs from asset=" + assetDTO.getCompUploadedFiles().size());
        List<CompDocumentation> docs = new ArrayList<CompDocumentation>();

        for(CompUploadedFile uf : assetDTO.getCompUploadedFiles()) {
            if(uf == null) continue;
            docs.add(convertUploadedFileToCompDocumentation(compVersion.getComponent(), uf.getUploadedFileDesc(),
                uf.getUploadedFileType()));
        }

        // we need to see which existing files we need to remove
        if (compVersion.getDocumentation() != null && compVersion.getDocumentation().size() > 0) {
            Set<CompDocumentation> removeDocs = new HashSet<CompDocumentation>();

            for(CompDocumentation existingDoc: compVersion.getDocumentation()) {
                // just make sure, as in facade we set to null
                existingDoc.setCompVersion(compVersion);

                // if an existing document is found in uploaded collection, it means it was updated (removed,
                // then added) on front-end. In this case it needs to be removed.
                boolean remove = false;
                for(CompDocumentation newDoc : docs) {
                    if(existingDoc.getDocumentName().equals(newDoc.getDocumentName()) &&
                            existingDoc.getDocumentTypeId().equals(newDoc.getDocumentTypeId())) {
                        remove = true;
                        System.out.println("found: " + existingDoc.getUrl());
                    }
                }

                if (!remove) {
                    // if an existing document is not in asset, it means it was removed on front-end.
                    // In this case it also needs to be removed.
                    boolean foundInAssetDTO = false;
                    for(int i = 0; i < assetDTO.getDocumentation().size() && !foundInAssetDTO; i++) {
                        CompDocumentation assetDoc = assetDTO.getDocumentation().get(i);

                        if(existingDoc.getDocumentName().equals(assetDoc.getDocumentName()) &&
                                existingDoc.getDocumentTypeId().equals(assetDoc.getDocumentTypeId())) {
                            foundInAssetDTO = true;
                        }
                    }

                    remove = !foundInAssetDTO;
                }

                if(remove) {
                    // remove from persistence
                    em.remove(existingDoc);
                    removeDocs.add(existingDoc);

                    // delete from file system
                    String rootDir = uploadedFilesRootDir;
                    if (!rootDir.endsWith("/")) {
                        rootDir += "/";
                    }
                    rootDir += existingDoc.getUrl();
                    System.out.println("deleting: " + rootDir);
                    deleteFileTree(new File(rootDir));
                }
            }
            compVersion.getDocumentation().removeAll(removeDocs);
        }

        // add uploaded docs
        for(int i = 0; i < docs.size(); i++) {
            System.out.println("adding: " + docs.get(i).getDocumentName());
            try {
                compVersion.getDocumentation().addAll(storeUploadedFile(compVersion,
                    assetDTO.getCompUploadedFiles().get(i)));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("Exit checkAssetDocumentation");
    }

    /**
     * <p>Populates <code>CompVersion</code>'s documentation collection from the
     * given <code>assetDTO.documentation</code>.</p>
     * @param assetDTO the original assetDTO to take data from
     * @param em the entity manager to verify documentation exist in the persistence and to take them from it
     * @param compVersion the CompVersion to populate
     * @throws PersistenceException if a document is not found in the persistence
     * @since 1.1
     */
    private void populateVersionDocumentation(AssetDTO assetDTO, EntityManager em,
            CompVersion compVersion)throws PersistenceException {
        System.out.println("Enter populateVersionDocumentation");
        //final List<CompDocumentation> documentation = new ArrayList<CompDocumentation>();

        try {
            // check if front-end added any compDocumentations, which means error
            for (CompDocumentation compDocumentation : assetDTO.getDocumentation()) {
                // try to find each one
                final CompDocumentation foundCompDocumentation = em.find(CompDocumentation.class, compDocumentation.getId());
                if (foundCompDocumentation == null) {
                    // invalid technology passed
                    throw new PersistenceException("Invalid parameter passed: there is no document with id="
                        + foundCompDocumentation.getId());
                }
                //documentation.add(foundCompDocumentation); BUGR-1600
            }
            // BUGR-1600.2
            checkAssetDocumentation(assetDTO, em, compVersion);
            System.out.println("doc count=" + compVersion.getDocumentation().size());
            System.out.println("Exit populateVersionDocumentation");

        } catch (javax.persistence.PersistenceException e) {
            // any other errors, e.g. on JDBC level are wrapped to it
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        } catch (IllegalStateException e) {
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        }
    }

    /**
     * Stores uploaded file in the file system.
     *
     * Note: unnecessary document type inference was removed. Also removed archive processing. (v1.2)
     *
     * @param file uploaded file
     * @throws IOException
     * @throws InterruptedException
     * @since BUGR-1600
     */
    private List<CompDocumentation> storeUploadedFile(CompVersion version, CompUploadedFile uf)
        throws PersistenceException, IOException, InterruptedException {

        System.out.println("Enter storeUploadedFile with filename=" + uf.getUploadedFileName());
        System.out.println("ROOTDIR = " + uploadedFilesRootDir);

        Component component = version.getComponent();
        Long lngComponent = component.getId();
        Long lngVersion = version.getId();
        List<CompDocumentation> result = new ArrayList<CompDocumentation>();

        String remoteFileName = uf.getUploadedFileName();

        String rootDir = uploadedFilesRootDir;
        if (!rootDir.endsWith("/")) {
            rootDir += "/";
        }
        String dir = lngComponent + "/" + lngVersion + "/";

        if (uf.getFileData().length > 0) {
            CompDocumentation parsedDoc = this.convertUploadedFileToCompDocumentation(component,
                uf.getUploadedFileDesc(), uf.getUploadedFileType());

            long lngType = parsedDoc.getDocumentTypeId();

            // Upload file
            String url = dir + remoteFileName;
            System.out.println("url=" + url);

            ByteArrayInputStream is = new ByteArrayInputStream(uf.getFileData());
            new File(rootDir, dir).mkdirs();
            System.out.println("path=" + new File(rootDir, dir).getAbsolutePath());
            File f = new File(rootDir, url);

            if (!f.exists()) {
                FileOutputStream fos = new FileOutputStream(f);
                int b = is.read();
                while (b != -1) {
                    fos.write(b);
                    b = is.read();
                }
                fos.close();
                is.close();

                // Extract the Javadocs
                if (lngType == CompDocumentation.JAVADOCS) {
                    File jDocDir = new File(f.getParent(), "javadoc");
                    jDocDir.mkdir();
                    System.out.println("Executing: jar -xf " + f.getAbsolutePath() + " in " + jDocDir.getAbsolutePath());
                    Runtime.getRuntime().exec("jar -xf " + f.getAbsolutePath(), new String[0], jDocDir);
                }

                // Add document to component
                parsedDoc.setUrl(url);
                result.add(parsedDoc);
            } else {
                //TEMP IGNORE
                //throw new PersistenceException("The file " + f.getName() + " already exists");
            }

        }
        System.out.println("Exit storeUploadedFile");
        return result;
    }

    /**
     * Simple conversion between
     *
     * Note: this method was simplified and document type inference was removed. (v1.2)
     *
     * @param component component for which the uploaded doc belongs
     * @param documentDesc the description of the file being converted
     * @param documentTypeId the document type id of the file being converted
     *
     * @return the CompDocumentation instance
     *
     * @since BUGR-1600
     */
    private CompDocumentation convertUploadedFileToCompDocumentation(Component component, String documentDesc,
        long documentTypeId) {

        CompDocumentation doc = new CompDocumentation();

        doc.setCompVersion(component.getCurrentVersion());
        doc.setDocumentName(documentDesc);
        doc.setDocumentTypeId(documentTypeId);

        return doc;
    }

    /**
     * <p>Populates <code>CompVersion</code>'s <code>CompVersionDates</code> map from the
     * given <code>assetDTO</code>.</p>
     * @param assetDTO the original assetDTO to take data from
     * @param compVersion the CompVersion to populate
     * @throws PersistenceException if a collaboration phase (<tt>111L</tt>) is not found in the persistence
     *
     * @throws javax.persistence.PersistenceException
     *                                  re-thrown from underlying persistence level
     */
    private void populateCompVersionDates(AssetDTO assetDTO, CompVersion compVersion) throws PersistenceException {
        // populate phase properties
        final Date zeroPointDate = buildDate(1976, 5, 5);
        //final Phase collaborationPhase = getEntityManager().find(Phase.class, COLLABORATION_PHASE_ID);

        Phase phase = getEntityManager().find(Phase.class, Phase.COLLABORATION_PHASE_ID);
        if (assetDTO.getPhase().equals("Development"))
        {
            phase = getEntityManager().find(Phase.class, Phase.DEVELOPMENT_PHASE_ID);
        }
        else if (assetDTO.getPhase().equals("Design"))
        {
            phase = getEntityManager().find(Phase.class, Phase.DESIGN_PHASE_ID);
        }

        compVersion.setPhase(phase);
        compVersion.setPhasePrice(0d);
        compVersion.setPhaseTime(zeroPointDate);
        // populate with CompVersionDates
        final Date stubDate = buildDate(2000, 1, 1);
        final Map<Long, CompVersionDates> versionDates = new HashMap<Long, CompVersionDates>();
        compVersion.setVersionDates(versionDates);

        final CompVersionDates compVersionDates = createInitialCompVersionDates(assetDTO, zeroPointDate, stubDate);
        compVersionDates.setPhase(phase);
        compVersionDates.setCompVersion(compVersion);
        if (assetDTO.getPhase().equals("Development"))
        {
            versionDates.put(Phase.DEVELOPMENT_PHASE_ID, compVersionDates);
        }
        else if (assetDTO.getPhase().equals("Design"))
        {
            versionDates.put(Phase.DESIGN_PHASE_ID, compVersionDates);
        }
        else
        {
            versionDates.put(Phase.COLLABORATION_PHASE_ID, compVersionDates);
        }
    }

    /**
     * <p>Populates <code>CompVersion</code>'s <code>CompForum</code> and <code>CompLink</code> from the
     * given <code>assetDTO</code>.</p>
     * @param assetDTO the original assetDTO to take data from
     * @param compVersion the CompVersion to populate
     */
    private void populateForumAndLink(AssetDTO assetDTO, CompVersion compVersion) {

        CompForum compForum = assetDTO.getForum();
        if (compForum != null && compVersion.getForum() == null) {
            compVersion.setForum(compForum);
            compForum.setCompVersion(compVersion);
        }
        CompLink link = assetDTO.getLink();
        if (link != null && compVersion.getLink() == null) {
            compVersion.setLink(link);
            link.setCompVersion(compVersion);
        }
    }

    /**
     * <p>Populates <code>CompVersion</code>'s technologies collection from the
     * given <code>assetDTO.technologies</code>.</p>
     * @param assetDTO the original assetDTO to take data from
     * @param em the entity manager to verify technologies exist in the persistence and to take them from it
     * @param compVersion the CompVersion to populate
     * @throws PersistenceException if a technology is not found in the persistence
     */
    private void populateVersionTechnologies(AssetDTO assetDTO, EntityManager em, CompVersion compVersion)
        throws PersistenceException {
        final List<Technology> technologies = new ArrayList<Technology>();
        try {
            for (Technology technology : assetDTO.getTechnologies()) {
                // try to find each one
                final Technology foundTechnology = em.find(Technology.class, technology.getId());
                if (foundTechnology == null) {
                    // invalid technology passed
                    throw new PersistenceException("Invalid parameter passed: there is no technology with id="
                        + technology.getId());
                }
                technologies.add(foundTechnology);
            }
            compVersion.setTechnologies(technologies);
        } catch (javax.persistence.PersistenceException e) {
            // any other errors, e.g. on JDBC level are wrapped to it
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        } catch (IllegalStateException e) {
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        }
    }

    /**
     * <p>
     * Populates <code>CompVersion</code>'s dependencies collection from the
     * given <code>assetDTO.dependencies</code>.
     * </p>
     *
     * @param assetDTO the original assetDTO to take data from
     * @param em the entity manager to verify dependencies exist in the persistence and to take them from it
     * @param compVersion the CompVersion to populate
     *
     * <p>
     *  <strong>Changes:</strong>
     *  A new field add in bug fix.
     * </p>
     *
     * @throws PersistenceException if a technology is not found in the persistence
     */
    private void populateVersionDependencies(AssetDTO assetDTO, EntityManager em, CompVersion compVersion)
        throws PersistenceException {
        final List<CompVersion> dependencies = new ArrayList<CompVersion>();
        try {
            for (Long compId : assetDTO.getDependencies()) {
                // try to find each one
                final CompVersion foundCompVersion = em.find(CompVersion.class, compId);
                if (foundCompVersion == null) {
                    // invalid compVersion passed
                    throw new PersistenceException("Invalid parameter passed: there is no compVersion with id="
                        + compId);
                }
                dependencies.add(foundCompVersion);
            }
            compVersion.setDependencies(dependencies);
        } catch (javax.persistence.PersistenceException e) {
            // any other errors, e.g. on JDBC level are wrapped to it
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        } catch (IllegalStateException e) {
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        }
    }

    /**
     * <p>Updates <code>Component</code> entity by populating fields with given <code>AssetDTO</code> instance.</p>
     *
     * @param assetDTO the original assetDTO from which take values to update the entity
     * @param component    component to update with
     * @param versionToUpdate version to update (can be null, means no update of comp version)
     * @throws IllegalArgumentException if any of the following conditions met:
     * <ul>
     * <li>if the <code>assetDTO.name</code> is <code>null</code> or empty</li>
     * <li>if the <code>assetDTO.shortDescription</code> is <code>null</code> or empty</li>
     * <li>if the <code>assetDTO.detailedDescription</code> is <code>null</code> or empty</li>
     * <li>if the <code>assetDTO.functionalDescription</code> is <code>null</code> or empty</li>
     * <li>if the <code>assetDTO.rootCategory</code> is <code>null</code></li>
     * <li>if the <code>assetDTO.categories</code> is <code>null</code>, empty or contains <code>null</code> item</li>
     * empty or contains <code>null</code> item</li>
     * <li>if the <code>versionToUpdate</code> is not <code>null</code>
     *       and <code>assetDTO.versionText</code> is <code>null</code> or empty</li>
     * <li>if the <code>versionToUpdate</code> is not <code>null</code>
     *      and the <code>assetDTO.technologies</code> is <code>null</code>,
     * </ul>
     * @throws javax.persistence.PersistenceException
     *                                  re-thrown from underlying persistence level
     * @throws PersistenceException     if any passed entity cannot be found in the database, or the EntityManager
     *                                  cannot be obtained
     */
    private void updateAsset(AssetDTO assetDTO, Component component, CompVersion versionToUpdate)
        throws PersistenceException {
        checkString("name", assetDTO.getName());
        checkString("shortDescription", assetDTO.getShortDescription());
        checkString("detailedDescription", assetDTO.getDetailedDescription());
        //checkString("functionalDescription", assetDTO.getFunctionalDescription());
        checkNotNull("rootCategory", assetDTO.getRootCategory());
        checkList("categories", assetDTO.getCategories());
        if (versionToUpdate != null) {
            checkString("versionText", assetDTO.getVersionText());
            //checkList("technologies", assetDTO.getTechnologies());
            populateVersionTechnologies(assetDTO, getEntityManager(), versionToUpdate);
            /*if (versionToUpdate.getDocumentation() != null) {
                populateVersionDocumentation(assetDTO, getEntityManager(), versionToUpdate, component); // BUGR-1600
            }*/
            versionToUpdate.setVersionText(assetDTO.getVersionText());
            populateForumAndLink(assetDTO, versionToUpdate);
        }

        // update component with categories, but get actual ones from the persistence by their id
        final List<Category> categories = new ArrayList<Category>();
        for (Category category : assetDTO.getCategories()) {
            categories.add(retrieveActualCategory(category));
        }
        component.setCategories(categories);
        // the same for the root one
        component.setRootCategory(retrieveActualCategory(assetDTO.getRootCategory()));
        // build lists of CompUser and CompClient entities
        buildClientsAndUsers(assetDTO, component);

        // populate flat fields
        component.setName(assetDTO.getName());
        component.setDescription(assetDTO.getDetailedDescription());
        component.setFunctionalDesc(assetDTO.getFunctionalDescription());
        component.setShortDesc(assetDTO.getShortDescription());

    }

    /**
     * <p>Builds lists of CompClients and CompUsers (if the corresponding ones of ids contain data).</p>
     *
     * @param assetDTO the assetDTO to get data from
     * @param component the component entity instance to provide data with
     */
    private void buildClientsAndUsers(AssetDTO assetDTO, Component component) {
        // build clients
        final List<Long> clientIds = assetDTO.getClientIds();
        if (clientIds != null && !clientIds.isEmpty()) {
            final Set<CompClient> clients = new HashSet<CompClient>();
            for (Long clientId : clientIds) {
                CompClient compClient = new CompClient();
                compClient.setClientId(clientId);
                compClient.setComponent(component);
                clients.add(compClient);
            }
            component.setClients(clients);
        }

        // extract userIds
        final List<Long> userIds = assetDTO.getUserIds();
        if (userIds != null && !userIds.isEmpty()) {
            final Set<CompUser> users = new HashSet<CompUser>();
            for (Long userId : userIds) {
                CompUser compUser = new CompUser();
                compUser.setUserId(userId);
                compUser.setComponent(component);
                users.add(compUser);
            }
            component.setUsers(users);
        }
    }

    /**
     * <p>Retrieves actual category from database by given one.</p>
     * <p>If no such a category then <code>PersistenceException</code> is thrown.</p>
     *
     * @param category passed category parameter (from client application)
     * @return entity retrieved from the persistence
     * @throws javax.persistence.PersistenceException
     *                              re-thrown from underlying persistence level
     * @throws PersistenceException if any passed entity cannot be found in the database, or the EntityManager cannot be
     *                              obtained
     */
    private Category retrieveActualCategory(Category category) throws PersistenceException {
        final Long categoryId = category.getId();
        try {
            final Category entity = getEntityManager().find(Category.class, categoryId);
            if (entity == null) {
                throw new PersistenceException("Invalid category passed with ID=" + categoryId);
            }
            return entity;
        } catch (javax.persistence.PersistenceException e) {
            // any other errors, e.g. on JDBC level are wrapped to it
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        } catch (IllegalStateException e) {
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        }
    }

    /**
     * <p>Retrieves the latest version of a given component entity.</p>
     *
     * @param component component entity whose latest version is being obtained
     * @return the latest version of the component
     * @throws PersistenceException if versions list is empty (which means error on persistence level, as there always
     *                              should be non-empty list of versions in a component).
     */
    private CompVersion getLatestVersion(Component component) throws PersistenceException {
        final List<CompVersion> versions = component.getVersions();
        if (versions == null || versions.isEmpty()) {
            throw new PersistenceException("Component doesn't have any versions");
        }
        // there is always at least one version
        CompVersion latestVersion = versions.get(0);
        for (CompVersion version : versions) {
            if (version.getVersion() > latestVersion.getVersion()) {
                latestVersion = version;
            }
        }
        return latestVersion;
    }

    /**
     * <p>Creates a date by given year, month, day of month.</p>
     *
     * @param year  the date's year
     * @param month the date's month
     * @param day   the date's day of month
     * @return Date object corresponding to given string representation
     */
    private Date buildDate(int year, int month, int day) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0L); // reset value
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * <p>Checks if an <code>Object</code> is not <code>null</code>.</p>
     *
     * @param name  a name of a parameter (used to build an error message).
     * @param value an <code>Object</code> to check.
     * @throws IllegalArgumentException if the parameter is null
     */
    private void checkNotNull(String name, Object value) {
        if (value == null) {
            throw new IllegalArgumentException("Argument '" + name + "' cannot be null");
        }
    }

    /**
     * <p>Checks if a <code>String</code> not <code>null</code> or empty after trimming.</p>
     *
     * @param name  a name of a parameter (used to build an error message).
     * @param value a <code>String</code> to check.
     * @throws IllegalArgumentException if the parameter is null or an empty String after trimming
     */
    private void checkString(String name, String value) {
        checkNotNull(name, value);
        if (value.trim().length() == 0) {
            throw new IllegalArgumentException("Argument '" + name + "' cannot be empty");
        }
    }

    /**
     * <p>Checks if a <code>List</code> is not null or empty and doesn't contain <code>null</code> values.</p>
     *
     * @param name  a name of a parameter (used to build an error message).
     * @param value a <code>Collection</code> to check.
     * @throws IllegalArgumentException if the collection contains null value, or <code>emptyIsIllegal</code> is
     *                                  <code>true</code> and the collection is <code>null</code> or empty
     */
    private void checkList(String name, List<?> value) {
        checkNotNull(name, value);
        if (value.isEmpty()) {
            throw new IllegalArgumentException("List '" + name + "' cannot be null or empty");
        }
        for (Object next : value) {
            if (next == null) {
                throw new IllegalArgumentException("List '" + name + "' cannot contain a null value");
            }
        }
    }

    /**
     * <p>Creates a new <code>CompVersionDates</code> instance with default values, and production date
     * taken from passed AssetDTO instance.</p>
     *
     * @param asset   an assetDTO instance
     * @param postingDate a value for posting date
     * @param otherDates  a value for all other dates
     * @return <code>CompVersionDates</code> instance populated with default parameters and production date taken
     *         from passed asset.
     */
    private CompVersionDates createInitialCompVersionDates(AssetDTO asset, Date postingDate, Date otherDates) {
        // populate with default data
        final CompVersionDates compVersionDates = new CompVersionDates();
        compVersionDates.setTotalSubmissions(0);
        compVersionDates.setPrice(0L);
        compVersionDates.setPostingDate(postingDate);
        compVersionDates.setAggregationCompleteDate(otherDates);
        compVersionDates.setEstimatedDevDate(otherDates);
        compVersionDates.setFinalSubmissionDate(otherDates);
        compVersionDates.setInitialSubmissionDate(otherDates);
        compVersionDates.setPhaseCompleteDate(otherDates);
        compVersionDates.setReviewCompleteDate(otherDates);
        compVersionDates.setScreeningCompleteDate(otherDates);
        compVersionDates.setWinnerAnnouncedDate(otherDates);
        compVersionDates.setLevelId(LEVEL_ID);
        compVersionDates.setStatus(Status.NEW_POST);
        // populate production date from the asset
        compVersionDates.setProductionDate(getDate(asset.getProductionDate()));
        return compVersionDates;
    }

    /**
     * <p>Returns canonized string parameter for database search (case insensitive, with doubled '\'' symbols).</p>
     * <p>This method introduced as Informix doesn't like JDBC queries like <tt>'%' || :param || '%'</tt>,
     * for unknown reason. So setting the parameter manually is a sad necessity.</p>
     *
     * @param parameter string parameter
     * @return canonized string parameter
     */
    private String canonize(String parameter) {
        return parameter.toLowerCase().replaceAll("'", "''");
    }

    /**
     * <p>Checks that <code>asset.versionId</code> is null (otherwise a version cannot be created).</p>
     * @param asset asset to check
     * @throws IllegalArgumentException if <code>asset.versionId</code> is not <code>null</code>
     */
    private void checkNullVersionId(AssetDTO asset) {
        if (asset.getCompVersionId() != null) {
            throw new IllegalArgumentException("CompVersion already has id=" + asset.getId()
                + " and cannot be created.");
        }
    }

    /**
     * <p>Checks that <code>asset.id</code> is not null (otherwise an asset cannot be updated).</p>
     * @param asset asset to check
     * @throws IllegalArgumentException if <code>asset.id</code> is <code>null</code>
     */
    private void checkComponentIdNotNull(AssetDTO asset) {
        if (asset.getId() == null) {
            throw new IllegalArgumentException("Component doesn't have id and cannot be updated.");
        }
    }

    /**
     * <p>Build joins and where clause for selecting componentIds of the Component entities which
     * satisfy the given criteria.</p>
     * @param criteria the search criteria to filter Component entities
     * @param subQueryString original query to append with joins and the where clause
     * @return map of parameters to set the query
     */
    private Map<String, Object> buildSubQuery(SearchCriteria criteria, StringBuilder subQueryString) {
        final List<String> condition = new ArrayList<String>();
        // map of named parameters for the query
        final Map<String, Object> parameters = new HashMap<String, Object>();
        // add joins and parameters with tables for user_id filtering, if the criteria is set
        appendUserId(criteria, subQueryString, condition, parameters);
        // add join and parameter with tables for client_id filtering, if the criteria is set
        appendClientId(criteria, subQueryString, condition, parameters);
        // add component_name and descriptions filtering, if the corresponding criteria is set
        appendNameAndDescription(criteria, condition);
        // add root category filtering, if the criteria is set
        appendCategories(criteria, condition, parameters);
        // add component and compVersion create date criteria
        appendCreateDateCriteria(criteria, condition, parameters);

        // build 'where' clause string
        subQueryString.append(" WHERE ");
        final int conditionsCount = condition.size();
        for (int i = 0; i < conditionsCount; i++) {
            String next = condition.get(i);
            if (i > 0) {
                subQueryString.append("\n AND ");
            }
            subQueryString.append(next);
        }
        return parameters;
    }

    /**
     * <p>Appends component and compVersion create date filtering</p>
     * @param criteria the search criteria
     * @param condition the list of conditions (an element for the 'where' clause can be added)
     * @param parameters the parameters for the query
     */
    private void appendCreateDateCriteria(SearchCriteria criteria, List<String> condition,
            Map<String, Object> parameters) {
        final Date before = criteria.getCompCreateBeforeDate();
        final Date after = criteria.getCompCreateAfterDate();
        final Date versionBefore = criteria.getCompVersionCreateBeforeDate();
        final Date versionAfter = criteria.getCompVersionCreateAfterDate();
        if (before != null) {
            condition.add("(C.create_time<='"+new Timestamp(before.getTime())+"')");
        }
        if (after != null) {
            condition.add("(C.create_time>='"+new Timestamp(after.getTime())+"')");
        }
        if (versionBefore != null) {
            condition.add("(B.create_time<='"+new Timestamp(versionBefore.getTime())+"')");
        }
        if (versionAfter != null) {
            condition.add("(B.create_time>='"+new Timestamp(versionAfter.getTime())+"')");
        }
    }

    /**
     * <p>Appends clause for <code>Component.rootCategory</code> filtering
     *  (if <code>criteria.categories</code> is not null).</p>
     * @param criteria the search criteria
     * @param condition the list of conditions (an element for the 'where' clause can be added)
     * @param parameters the parameters for the query (categoryIds are being added to it)
     */
    private void appendCategories(SearchCriteria criteria, List<String> condition, Map<String, Object> parameters) {
        final List<Category> categoryList = criteria.getCategories();
        if (categoryList != null) {
            StringBuilder categoryIds = new StringBuilder();
            final int categoriesCount = categoryList.size();
            // build clause like (:c0, :c1, :c2, ...) for the list of categories
            // and put each one to the parameters map
            for (int i = 0; i < categoriesCount; i++) {
                Category category = categoryList.get(i);
                final String paramName = "c" + i;
                if (i > 0) {
                    categoryIds.append(",");
                }
                categoryIds.append(':').append(paramName);
                parameters.put(paramName, category.getId());
            }
            condition.add("(C.root_category_id IN (" + categoryIds + "))\n");
        }
    }

    /**
     * <p>Appends clause for <code>Component.name</code> filtering
     *  (if <code>criteria.name</code> is not null).</p>
     * @param criteria the search criteria
     * @param condition the list of conditions (elements for the 'where' clause can be added)
     */
    private void appendNameAndDescription(SearchCriteria criteria, List<String> condition) {
        // add name filtering, if the criteria is set
        final String name = criteria.getName();
        if (name != null && name.trim().length() != 0) {
            condition.add("(LOWER(C.component_name) LIKE '%" + canonize(name) + "%')");
        }
        // add description filtering, if the criteria is set
        final String description = criteria.getDescription();
        if (description != null && description.trim().length() != 0) {
            String likeCondition = "LIKE '%" + canonize(description) + "%')";
            condition.add(
                "(LOWER(C.description) " + likeCondition
                    + " OR (LOWER(C.short_desc) " + likeCondition
                    + " OR (LOWER(C.function_desc) " + likeCondition
            );
        }
    }

    /**
     * <p>Appends clause for <code>Component.clientId</code> filtering
     *  (if <code>criteria.clientId</code> is not null).</p>
     * @param criteria the search criteria
     * @param subQueryString original query to append with joins
     * @param condition the list of conditions (an element for the 'where' clause can be added)
     * @param parameters the parameters for the query (clientId is being added to it, if it's not null)
     */
    private void appendClientId(SearchCriteria criteria, StringBuilder subQueryString, List<String> condition,
                                Map<String, Object> parameters) {
        final Long clientId = criteria.getClientId();
        if (clientId != null) {
            if (criteria.getUserId() == null) { // otherwise the table was already joined
                subQueryString.append("JOIN comp_client CL ON CL.component_id=C.component_id\n");
            }
            condition.add("(CL.client_id=:client_id)");
            parameters.put("client_id", clientId);
        }
    }

    /**
     * <p>Appends clause for <code>Component.userId</code> filtering
     *  (if <code>criteria.userId</code> is not null).</p>
     * @param criteria the search criteria
     * @param subQueryString original query to append with joins
     * @param condition the list of conditions (an element for the 'where' clause can be added)
     * @param parameters the parameters for the query (userId is being added to it, if it's not null)
     */
    private void appendUserId(SearchCriteria criteria, StringBuilder subQueryString, List<String> condition,
                              Map<String, Object> parameters) {
        final Long userId = criteria.getUserId();
        if (userId != null) {
            subQueryString
                .append("LEFT OUTER JOIN comp_user U ON C.component_id=U.component_id\n")
                .append("LEFT OUTER JOIN comp_client CL ON CL.component_id=C.component_id\n")
                .append("LEFT OUTER JOIN user_client UC ON UC.client_id=CL.client_id\n");
            condition.add("(U.user_id=:user_id OR UC.user_id=:user_id)\n");
            parameters.put("user_id", userId);
        }
    }

    /**
     * Get a <code>Component</code> by its ID.
     *
     * @param id the id of the Component
     * @return Component entity found
     * @throws PersistenceException on any error on the underlying persistence level
     * @throws EntityNotFoundException if not found an entity with the given ID
     */
    private Component findComponentById(long id) throws PersistenceException, EntityNotFoundException {
        try {
            Component component = getEntityManager().find(Component.class, id);
            if (component == null) {
                throw new EntityNotFoundException("Component with id=" + id + " not found");
            }
            return component;
        } catch (IllegalStateException e) {
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        } catch (javax.persistence.PersistenceException e) {
            // any other errors, e.g. on JDBC level are wrapped to it
            throw new PersistenceException("Unexpected persistence error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * <p>Retrieves list of entities by the given named query and the excpected type of entities.</p>
     *
     * @param cls expected type of entities
     * @param queryName the named query's name
     * @return list of entities returned by the query (empty list if none)
     * @throws PersistenceException if any error occurs on the underlying persistence level or query returned
     *                              list of invalid type
     */
    @SuppressWarnings("unchecked")
    private <T> List<T> retrieveList(Class<T> cls, String queryName) throws PersistenceException {
        try {
            // simply execute the named query
            Query query = getEntityManager().createNamedQuery(queryName);
            final List resultList = query.getResultList();
            if (!resultList.isEmpty()) {
                if (!cls.isInstance(resultList.get(0))) {
                    throw new PersistenceException("Returned invalid collection. Elements expected as <"
                        + cls.getName() + "> but was <" + resultList.get(0).getClass().getName() + ">");
                }
            }
            return (List<T>) resultList;
        } catch (javax.persistence.PersistenceException e) {
            // any other errors, e.g. on JDBC level are wrapped to it
            throw new PersistenceException("Unexpected persistence error occurred: " + e.getMessage(), e);
        } catch (IllegalStateException e) {
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Unexpected error: " + e.getMessage(), e);
        }
    }

    /**
     * Persist an entity, handles all possible exceptions.
     *
     * @param em the entity manger to perform operation
     * @param entity the entity to persist
     * @throws PersistenceException if any error occurs on the persistence level
     */
    private void persistEntity(EntityManager em, Object entity) throws PersistenceException {
        try {
            em.persist(entity);
        } catch (IllegalStateException e) {
            throw new PersistenceException("The entity manager has been closed.", e);
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Object of [" + entity.getClass() + "] is not an entity.", e);
        } catch (EntityExistsException e) {
            throw new PersistenceException("The entity already exists.", e);
        } catch (TransactionRequiredException e) {
            throw new PersistenceException("Not in transaction.", e);
        } catch (javax.persistence.PersistenceException e) {
            // any other errors, e.g. on JDBC level are wrapped to it
            throw new PersistenceException("Unexpected error occurred on underlying persistence level:"
                + e.getMessage(), e);
        }
    }

    /**
     * Merges an entity, handles all possible exceptions.
     *
     * @param em the entity manger to perform operation
     * @param entity the entity to persist
     * @throws PersistenceException if any error occurs on the persistence level
     */
    private void mergeEntity(EntityManager em, Object entity) throws PersistenceException {
        try {
            em.persist(entity);
        } catch (IllegalStateException e) {
            throw new PersistenceException("The entity manager has been closed.", e);
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Object of [" + entity.getClass() + "] is not an entity,"
                + " or has been removed.", e);
        } catch (TransactionRequiredException e) {
            throw new PersistenceException("Not in transaction.", e);
        } catch (javax.persistence.PersistenceException e) {
            // any other errors, e.g. on JDBC level are wrapped to it
            throw new PersistenceException("Unexpected error occurred on underlying persistence level:"
                + e.getMessage(), e);
        }
    }


    /**
     * Converts standard java Date object into XMLGregorianCalendar instance.
     * Returns null if parameter is null.
     *
     * @param date
     *            Date object to convert
     * @return converted calendar instance
     */
    private XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            // can't create calendar, return null
            return null;
        }
    }

    /**
     * Converts XMLGregorianCalendar date into standard java Date object.
     * Returns null if argument is null.
     *
     * @param calendar
     *            calendar instance to convert
     * @return converted Date instance
     */
    private Date getDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

}



