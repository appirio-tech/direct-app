/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Technology;

import java.util.List;

/**
 * <p>This interface defines a contract for the catalog services.</p>
 * <p>It provides facilities to retrieve <code>Components</code>, <code>Categories</code>, <code>Technologies</code>
 * and <code>Phases</code> from and to modify <code>Components</code> in the persistence.</p>
 * Example of obtaining a CatalogService instance:
 * <pre>
 *      public CatalogService getCatalogService() {
 *          try {
 *              // get object from JNDI
 *              Context context = new InitialContext();
 *              // or "CatalogService/local" if CatalogService is in the same EAR file
 *              return (CatalogService) context.lookup("CatalogService/remote");
 *          } catch (NamingException e) {
 *              throw new IllegalStateException(
 *                  "Cannot lookup 'CatalogService'. Check the configuration (Jboss is running, "
 *                      + "id_generator_ejb.jar and catalog_services_ejb.jar are deployed successfully). "
 *                      + "The nested exception is: " + e.getMessage(), e);
 *          }
 *      }
 * </pre>
 * Example of creating an AssetDTO:
 * <pre>
 *      AssetDTO newAsset = new AssetDTO();
 *      newAsset.setName("Catalog Services");
 *      newAsset.setVersionText("1.0");
 *      newAsset.setShortDescription("short");
 *      newAsset.setDetailedDescription("detailed");
 *      newAsset.setFunctionalDescription("functional");
 *      // set the root category
 *      newAsset.setRootCategory(javaCategory);
 *
 *      // assign categories which this asset belongs to
 *      newAsset.setCategories(Arrays.asList(ejb3Category));
 *
 *      newAsset.setTechnologies(Arrays.asList(
 *        java15Technology,
 *        informixTechnology
 *      ));
 *      remote.createAsset(newAsset);
 * </pre>
 * Updating an asset works in a similar way, except that it doesn't create a new version,
 * but just updates the version referred by DTO.
 * <p/>
 * Creating a new version:
 * <pre>
 *      // retrieve asset and with current version
 *      AssetDTO asset = remote.getAssetById(assetId, true);
 *      asset.setName("Catalog Service"); // update asset name
 *      asset.setVersionText("1.1"); // sent new text version
 *      asset.setVersionId(null); // reset version ID
 *      asset.setProductionDate(parseDate("2008/01/10")); // set new production date
 *      // set new documentation date
 *      asset.setDocumentation(new List<CompDocumentation>(){new CompDocumentation()});
 *      remote.createVersion(asset);
 * </pre>
 * Finding assets in the persistence by a given criteria:
 * <pre>
 *      List&lt;AssetDTO&gt; assets = remote.findAssets(
 *          new SearchCriteria(null, null, null, "catalog", null, null, null, null, null), true);
 * </pre>
 * Suppose we have in catalog Catalog Service and Catalog Entities assets. The list will contain them both.
 *
 * <p>
 * version 1.1 add method <code>getAllAssetVersions(long)</code> which get all versions with same asset id.
 * </p>
 * <p><strong>Thread safety: </strong></p> <p>Implementations are required to be thread-safe.</p>
 *
 * @author caru, Retunsky, TCSDEVELOPER
 * @version 1.1
 */
public interface CatalogService {
    /**
     * <p>Returns a list containing all active <code>Categories</code>.</p>
     *
     * @return a list containing all active <code>Categories</code>. It can be empty if no objects found.
     * @throws PersistenceException if an error occurs when interacting with the persistence store.
     */
    List<Category> getActiveCategories() throws PersistenceException;

    /**
     * <p>Returns a list containing all active <code>Technologies</code>.</p>
     *
     * @return a list containing all active <code>Categories</code>. It can be empty if no objects found.
     * @throws PersistenceException if an error occurs when interacting with the persistence store.
     */
    List<Technology> getActiveTechnologies() throws PersistenceException;

    /**
     * <p>Returns a list containing all <code>Phases</code>.</p>
     *
     * @return a list containing all active <code>Categories</code>. It can be empty if no objects found.
     * @throws PersistenceException if an error occurs when interacting with the persistence store.
     */
    List<Phase> getPhases() throws PersistenceException;

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
    AssetDTO getAssetById(long id, boolean currentVersion) throws EntityNotFoundException, PersistenceException;

    /**
     * <p>Returns an <code>AssetDTO</code> instance describing the asset containing
     * the version with specified id.</p>
     *
     * @param id the id of the asset's version
     * @return a <code>AssetDTO</code> instance describing the asset (cannot be <code>null</code>)
     * @throws EntityNotFoundException if the asset is not found in persistence
     * @throws PersistenceException    if an error occurs when interacting with the persistence store.
     */
    AssetDTO getAssetByVersionId(long id) throws EntityNotFoundException, PersistenceException;

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
     * </ul>
     * @throws PersistenceException     if an error occurs when interacting with the persistence store.
     */
    AssetDTO createAsset(AssetDTO asset) throws PersistenceException;

    /**
     * <p>Creates a new version for the given <code>AssetDTO</code> and update it
     * in the persistence.</p>
     *
     * @param asset a <code>AssetDTO</code> instance describing the asset
     * @return updated asset with populated <code>asset.versionId</code> field.
     *
     * @throws IllegalArgumentException if any of the following conditions met:
     * <ul>
     *  <li>if the parameter is <code>null</code></li>
     *  <li>if the <code>asset.id</code> has <code>null</code> value</li>
     *  <li>if the <code>asset.versionId</code> has non-null value</li>
     *  <li>if the <code>asset.name</code> is <code>null</code> or empty</li>
     *  <li>if the <code>asset.versionText</code> is <code>null</code> or empty</li>
     *  <li>if the <code>asset.shortDescription</code> is <code>null</code> or empty</li>
     *  <li>if the <code>asset.detailedDescription</code> is <code>null</code> or empty</li>
     *  <li>if the <code>asset.functionalDescription</code> is <code>null</code> or empty</li>
     *  <li>if the <code>asset.rootCategory</code> is <code>null</code></li>
     *  <li>if the <code>asset.categories</code> is <code>null</code>, empty or contains <code>null</code> item</li>
     *  <li>if the <code>asset.technologies</code> is <code>null</code>,
     *                           empty or contains <code>null</code> item</li>
     * </ul>
     * @throws EntityNotFoundException if the asset is not found in persistence
     * @throws PersistenceException     if an error occurs when interacting with the persistence store.
     */
    AssetDTO createVersion(AssetDTO asset) throws EntityNotFoundException, PersistenceException;

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
     * @throws EntityNotFoundException if the asset is not found in persistence
     * @throws PersistenceException     if an error occurs when interacting with the persistence store.
     */
    AssetDTO updateAsset(AssetDTO asset) throws EntityNotFoundException, PersistenceException;

    /**
     * By a given criteria returns a list of <code>AssetDTOs</code> containing only the following information:
     * <ul>
     *  <li>Id</li>
     *  <li>VersionId</li>
     *  <li>Name</li>
     *  <li>Version (text)</li>
     *  <li>Root Category</li>
     *  <li>Short Description</li>
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
    List<AssetDTO> findAssets(SearchCriteria criteria, boolean currentVersion) throws PersistenceException;

    /**
     * <p>Assigns a specified user to a specified <code>assetDTO</code>.</p>
     * <p>If the user already assigned to the asset, this method simply does nothing.</p>
     *
     * @param userId the id of the user
     * @param assetId the id of the assetDTO
     *
     * @throws EntityNotFoundException  if the assetDTO is not found in persistence
     * @throws PersistenceException     if an error occurs when interacting with the persistence store.
     */
    void assignUserToAsset(long userId, long assetId) throws EntityNotFoundException, PersistenceException;

    /**
     * <p>Removes a specified user from a specified <code>assetDTO</code>.</p>
     *
     * @param userId the id of the user
     * @param assetId the id of the asset
     *
     * @throws EntityNotFoundException
     *                  if the assetDTO is not found in persistence, or if the user is not assigned to the asset
     * @throws PersistenceException
     *                 if an error occurs when interacting with the persistence store
     */
    void removeUserFromAsset(long userId, long assetId) throws EntityNotFoundException, PersistenceException;

    /**
     * <p>retrieve all the versions of an <code>assetDTO</code> using given assetId.</p>
     *
     * @param assetId the assetId
     * @return a list where each element is a version of the asset, ordered by CompVersion.version
     * @throws EntityNotFoundException  if the assetDTO is not found in persistence
     * @throws PersistenceException     if an error occurs when interacting with the persistence store.
     * @since version 1.1
     */
    List<AssetDTO> getAllAssetVersions(long assetId) throws PersistenceException, EntityNotFoundException;

	/**
     * <p>create comp forum</p>
     *
     * @param compVersId
	 * @param jiveCategoryId
     *
     * @throws PersistenceException     if an error occurs when interacting with the persistence store.
     */
	void addCompForum(long compVersId, long jiveCategoryId) throws PersistenceException;

    /**
     * <p>create dev component, basically insert a new row in CompVersionsDates</p>
     *
     * @param asset a <code>AssetDTO</code> instance describing the asset
     *
     * @throws EntityNotFoundException if the asset is not found in persistence, or version not found (if specified)
     * @throws PersistenceException     if an error occurs when interacting with the persistence store.
     */
    AssetDTO createDevComponent(AssetDTO asset) throws PersistenceException, EntityNotFoundException;
}



