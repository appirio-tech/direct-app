/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.AssetSearchCriteria;
import com.topcoder.asset.entities.AssetVersion;
import com.topcoder.asset.entities.Category;
import com.topcoder.asset.entities.CategorySearchCriteria;
import com.topcoder.asset.entities.FileTypeIcon;
import com.topcoder.asset.entities.PagedResult;
import com.topcoder.asset.entities.User;
import com.topcoder.asset.services.AssetCategoryService;
import com.topcoder.asset.services.AssetPermissionService;
import com.topcoder.asset.services.AssetService;
import com.topcoder.asset.services.AssetVersionService;
import com.topcoder.asset.services.FileTypeIconService;
import com.topcoder.asset.services.ManagerService;

/**
 * <p>
 * Shows usage for the component.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public class Demo extends BaseUnitTests {
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(Demo.class);
    }
    
    /**
     * <p>
     * Demo API usage of <code>AssetService</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unused")
    @Test
    public void testDemoAssetService() throws Exception {
        loadDB(false);
        
        // Load application context
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        // Retrieve AssetService from the Spring application context
        AssetService assetService = (AssetService) context.getBean("assetService");

        // Prepare the id of user who performs the action
        long userId = 12;

        // Prepare asset to create
        List<Category> categories = new ArrayList<Category>();
        Category category1 = new Category();
        category1.setId(100);
        categories.add(category1);

        Category category2 = new Category();
        category2.setId(101);
        categories.add(category2);

        Asset asset = new Asset();
        asset.setName("design document");
        asset.setCategories(categories);
        asset.setContainerType("containerType1");
        asset.setContainerId(1);

        // Create asset
        assetService.createAsset(userId, asset);
        // The asset will be persisted in database,
        // Its child entities currentVersion and categories won¡¯t be managed,
        // but relationship will be managed.

        // Prepare asset to update
        asset.setName("new design document");
        asset.getCategories().get(1).setId(102);

        // Update asset
        assetService.updateAsset(userId, asset);
        // The asset will be updated in database. Its child entities currentVersion and categories won¡¯t be managed,
        // but relationship will be managed.

        long assetId = asset.getId();

        // Get asset
        Asset result = assetService.getAsset(assetId);
        // The asset will be retrieved from database. It will also retrieve its child entity
        // currentVersion:AssetVersion and categories:List<Category>.

        // Prepare AssetSearchCriteria
        AssetSearchCriteria criteria = new AssetSearchCriteria();
        criteria.setVersion("Version 1.0");
        // set other properties of criteria similarly

        // Search assets
        PagedResult<Asset> assets = assetService.searchAssets(criteria);
        // The matched assets will be fetched.

        Asset asset2 = new Asset();
        asset2.setName("design document 2");
        asset2.setContainerType("containerType2");
        asset2.setContainerId(2);

        // Prepare assets to create
        List<Asset> assetList = new ArrayList<Asset>();
        assetList.add(asset2);

        // Batch create assets
        assetService.createAssets(userId, assetList);

        // Prepare asset ids
        List<Long> assetIds = new ArrayList<Long>();
        assetIds.add(asset2.getId());

        // Prepare assets to update
        asset2.setName("design document 3");

        // Batch update assets
        assetService.updateAssets(userId, assetList);

        // Batch delete assets
        assetService.deleteAssets(userId, assetIds);

        // Delete asset
        assetService.deleteAsset(userId, assetId);
        // The asset will be deleted in database. All version files and all versions will also be deleted.
    }

    /**
     * <p>
     * Demo API usage of <code>AssetVersionService</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testDemoAssetVersionService() throws Exception {
        loadDB(false);
        
        // Load application context
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        // Retrieve AssetVersionService from the Spring application context
        AssetVersionService assetVersionService = (AssetVersionService) context.getBean("assetVersionService");

        // Prepare user id
        Long userId = 12L;

        // Prepare asset version to create
        AssetVersion assetVersion = new AssetVersion();
        assetVersion.setVersion("Version 1.0");
        assetVersion.setAssetId(1);
        assetVersion.setFileName("erd.png");
        assetVersion.setFileType("png");
        assetVersion.setFileSizeBytes(57527);

        User uploader = new User();
        uploader.setId(1);
        assetVersion.setUploader(uploader);
        assetVersion.setUploadTime(new Date());
        assetVersion.setDescription("The description.");
        assetVersion.setFilePath("filePath");
        assetVersion.setPreviewImagePath("previewImagePath");

        // Prepare File
        File file = new File(TEST_FILES + "erd.png");

        // Create asset version
        assetVersionService.createAssetVersion(userId, assetVersion, file, true);
        // The asset version will be persisted in database. The file will be saved to configured location, and preview
        // image will also be generated.

        // Prepare asset version to update
        assetVersion.setVersion("Version 1.1");

        // Update asset version
        assetVersionService.updateAssetVersion(userId, assetVersion);
        // The asset version will be updated in database. The related file will be move to new file location, and
        // preview image will also be moved.

        long assetVersionId = assetVersion.getId();

        // Get asset version
        assetVersion = assetVersionService.getAssetVersion(assetVersionId);
        // The asset version will be retrieved from database

        // Get asset versions of asset
        List<AssetVersion> assetVersions = assetVersionService.getAssetVersionsOfAsset(1);
        // The asset versions of the specific asset will be retrieved from database

        // Prepare asset versions to update
        assetVersions = new ArrayList<AssetVersion>();

        assetVersion.setVersion("Version 1.2");
        assetVersions.add(assetVersion);

        // Batch update asset versions
        assetVersionService.updateAssetVersions(userId, assetVersions);

        // Prepare asset version ids to retrieve
        List<Long> assetVersionIds = new ArrayList<Long>();
        assetVersionIds.add(assetVersionId);

        // Prepare output stream
        OutputStream outputStream = new ByteArrayOutputStream();
        // Batch get asset version contents
        assetVersionService.batchGetAssetVersionContents(assetVersionIds, outputStream);
        // The file contents of these asset version will be zipped and written to output stream.

        // Delete asset version
        assetVersionService.deleteAssetVersion(userId, assetVersionId);
        // The asset version will be deleted in database. The related files will also be deleted.
    }

    /**
     * <p>
     * Demo API usage of <code>AssetPermissionService</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unused")
    @Test
    public void testDemoAssetPermissionService() throws Exception {
        loadDB(true);
        
        // Load application context
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        // Retrieve AssetPermissionService from the Spring application context
        AssetPermissionService assetPermissionService = (AssetPermissionService) context
            .getBean("assetPermissionService");

        // Create Permission
        assetPermissionService.createPermission(1, 1);

        // Check if asset is allowed for the specific user
        boolean isAllowed = assetPermissionService.isAllowed(1, 1);
        // isAllowed will be true.

        // Get allowed users for asset
        List<User> users = assetPermissionService.getAllowedUsersForAsset(1);
        // users will contain 1 records, with user.id = 1

        // Remove Permission
        assetPermissionService.removePermission(1, 1);
        // The corresponding record will be removed in database

        // Prepare assetIds and userIds
        List<Long> assetIds = new ArrayList<Long>();
        assetIds.add(1L);
        assetIds.add(2L);

        List<Long> userIds = new ArrayList<Long>();
        userIds.add(1L);
        userIds.add(2L);

        // Set permissions
        assetPermissionService.setPermissions(assetIds, userIds);
    }

    /**
     * <p>
     * Demo API usage of <code>AssetCategoryService</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unused")
    @Test
    public void testDemoAssetCategoryService() throws Exception {
        loadDB(true);
        
        // Load application context
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        // Retrieve AssetCategoryService from the Spring application context
        AssetCategoryService assetCategoryService = (AssetCategoryService) context.getBean("assetCategoryService");

        // Prepare category
        Category category = new Category();
        category.setName("category11");
        category.setContainerType("project");
        category.setContainerId(11);

        // Create category
        assetCategoryService.create(category);
        // The corresponding record will be inserted into database. And the id of newly created category will be set.

        category.setContainerId(12);
        // Update category
        assetCategoryService.update(category);
        // The corresponding record will be updated in database

        // Retrieve category
        category = assetCategoryService.get(category.getId());
        // The corresponding record will be retrieved from database;

        // Prepare criteria
        CategorySearchCriteria criteria = new CategorySearchCriteria();
        criteria.setPage(1);
        criteria.setPageSize(10);
        criteria.setSortingColumn("name");
        criteria.setAscending(true);
        criteria.setContainerType("project");

        // Search categories
        PagedResult<Category> result = assetCategoryService.search(criteria);
        // The corresponding matched result will be returned, sorted by name.

        // Remove category
        assetCategoryService.delete(category.getId());
        // The corresponding record will be removed in database
    }

    /**
     * <p>
     * Demo API usage of <code>FileTypeIconService</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unused")
    @Test
    public void testDemoFileTypeIconService() throws Exception {
        loadDB(true);
        
        // Load application context
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        // Retrieve FileTypeIconService from the Spring application context
        FileTypeIconService fileTypeIconService = (FileTypeIconService) context.getBean("fileTypeIconService");

        // Prepare fileTypeIcon
        FileTypeIcon fileTypeIcon = new FileTypeIcon();
        fileTypeIcon.setFileType("fileType1");
        fileTypeIcon.setFileTypeCategory("fileTypeCategory1");
        fileTypeIcon.setIconPath("iconPath1");

        // Create fileTypeIcon
        fileTypeIconService.create(fileTypeIcon);
        // The corresponding record will be inserted into database.
        // And the id of newly created fileTypeIcon will be set.

        fileTypeIcon.setFileType("fileType2");
        // Update fileTypeIcon
        fileTypeIconService.update(fileTypeIcon);
        // The corresponding record will be updated in database

        // Retrieve fileTypeIcon
        fileTypeIcon = fileTypeIconService.get(fileTypeIcon.getId());
        // The corresponding record will be retrieved from database

        // Retrieve all fileTypeIcons
        List<FileTypeIcon> fileTypeIcons = fileTypeIconService.getAll();
        // All fileTypeIcons will be retrieved from database;

        // Retrieve all file type categories
        List<String> fileTypeCategories = fileTypeIconService.getAllFileTypeCategories();
        // All distinct file type categories will be retrieved from database;

        // Remove fileTypeIcon
        fileTypeIconService.delete(fileTypeIcon.getId());
        // The corresponding record will be removed in database
    }

    /**
     * <p>
     * Demo API usage of <code>ManagerService</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unused")
    @Test
    public void testDemoManagerService() throws Exception {
        loadDB(true);
        
        // Load application context
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        // Retrieve ManagerService from the Spring application context
        ManagerService managerService = (ManagerService) context.getBean("managerService");

        // Get client managers
        List<User> clientManagers = managerService.getClientManagers(11);
        // The client managers of the project will be retrieved.

        // Get TopCoder managers
        List<User> topcoderManagers = managerService.getTopCoderManagers(11);
        // The TopCoder managers of the project will be retrieved.

        // Get copilots
        List<User> copilots = managerService.getCopilots(11);
        // The copilots of the project will be retrieved.

    }
}
