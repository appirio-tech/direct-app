/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import java.util.List;

import com.topcoder.asset.entities.FileTypeIcon;
import com.topcoder.asset.exceptions.EntityNotFoundException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.asset.services.FileTypeIconService;

/**
 * <p>
 * This class is an implementation of FileTypeIconService that uses injected JPA EntityManager interface for accessing
 * FileTypeIcon data in database. This class uses Logging Wrapper logger to perform logging. It's assumed that this
 * class is initialized with Spring setter dependency injection only.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;beans xmlns=&quot;http://www.springframework.org/schema/beans&quot;
 *        xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
 *        xmlns:context=&quot;http://www.springframework.org/schema/context&quot;
 *        xmlns:tx=&quot;http://www.springframework.org/schema/tx&quot;
 *        xmlns:aop=&quot;http://www.springframework.org/schema/aop&quot;
 *        xsi:schemaLocation=&quot;http://www.springframework.org/schema/beans
 *        http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
 *        http://www.springframework.org/schema/context
 *        http://www.springframework.org/schema/context/spring-context-2.5.xsd
 *        http://www.springframework.org/schema/tx
 *        http://www.springframework.org/schema/tx/spring-tx-2.5.xsd
 *        http://www.springframework.org/schema/aop
 *        http://www.springframework.org/schema/aop/spring-aop-2.5.xsd&quot;&gt;
 *
 *     &lt;context:annotation-config/&gt;
 *     &lt;bean class=&quot;org.springframework.context.annotation.CommonAnnotationBeanPostProcessor&quot;/&gt;
 *
 *     &lt;bean id=&quot;fileTypeIconService&quot;
 *         class=&quot;com.topcoder.asset.services.impl.FileTypeIconServiceImpl&quot;&gt;
 *         &lt;property name=&quot;entityManager&quot; ref=&quot;entityManager&quot;/&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *
 *     &lt;!-- Log --&gt;
 *     &lt;bean id=&quot;log&quot;
 *         class=&quot;org.springframework.beans.factory.config.MethodInvokingFactoryBean&quot;&gt;
 *         &lt;property name=&quot;staticMethod&quot;&gt;
 *             &lt;value&gt;com.topcoder.util.log.LogManager.getLog&lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name=&quot;arguments&quot;&gt;
 *             &lt;list&gt;
 *                 &lt;value&gt;com.topcoder.asset.logger&lt;/value&gt;
 *             &lt;/list&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 *
 *
 *     &lt;bean id=&quot;entityManagerFactory&quot;
 *       class=&quot;org.springframework.orm.jpa.LocalEntityManagerFactoryBean&quot; &gt;
 *       &lt;property name=&quot;persistenceUnitName&quot; value=&quot;persistenceUnit&quot; /&gt;
 *     &lt;/bean&gt;
 *     &lt;bean id=&quot;entityManager&quot;
 *         class = &quot;org.springframework.orm.jpa.support.SharedEntityManagerBean&quot;&gt;
 *             &lt;property name = &quot;entityManagerFactory&quot; ref=&quot;entityManagerFactory&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean class=&quot;org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor&quot; /&gt;
 *     &lt;context:annotation-config/&gt;
 *     &lt;context:component-scan base-package=&quot;com.topcoder.asset.entities&quot; /&gt;
 *
 *     &lt;!-- the transactional advice (what 'happens'; see the &lt;aop:advisor/&gt; bean below) --&gt;
 *     &lt;tx:advice id=&quot;txAdvice&quot; transaction-manager=&quot;txManager&quot;&gt;
 *       &lt;tx:attributes&gt;
 *         &lt;tx:method name=&quot;create*&quot;/&gt;
 *         &lt;tx:method name=&quot;remove*&quot;/&gt;
 *         &lt;tx:method name=&quot;set*&quot;/&gt;
 *         &lt;tx:method name=&quot;update*&quot;/&gt;
 *         &lt;tx:method name=&quot;delete*&quot;/&gt;
 *         &lt;tx:method name=&quot;*&quot; read-only=&quot;true&quot;/&gt;
 *       &lt;/tx:attributes&gt;
 *     &lt;/tx:advice&gt;
 *
 *     &lt;!-- ensure that the above transactional advice runs for any execution of an operation
 *         defined by the service interfaces --&gt;
 *     &lt;aop:config&gt;
 *       &lt;aop:pointcut id=&quot;serviceOperation&quot;
 *       expression=&quot;execution(* com.topcoder.asset.services.impl.*Service*.*(..))&quot;/&gt;
 *       &lt;aop:advisor advice-ref=&quot;txAdvice&quot; pointcut-ref=&quot;serviceOperation&quot;/&gt;
 *     &lt;/aop:config&gt;
 *
 *  &lt;bean id=&quot;txManager&quot; class=&quot;org.springframework.orm.jpa.JpaTransactionManager&quot;&gt;
 *      &lt;property name=&quot;entityManagerFactory&quot; ref=&quot;entityManagerFactory&quot; /&gt;
 *  &lt;/bean&gt;
 *
 * &lt;/beans&gt;
 *
 *
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Sample Code:</em>
 * <pre>
 * // Load application context
 * ApplicationContext context = new ClassPathXmlApplicationContext(&quot;beans.xml&quot;);
 *
 * // Retrieve FileTypeIconService from the Spring application context
 * FileTypeIconService fileTypeIconService = (FileTypeIconService) context.getBean(&quot;fileTypeIconService&quot;);
 *
 * // Prepare fileTypeIcon
 * FileTypeIcon fileTypeIcon = new FileTypeIcon();
 * fileTypeIcon.setFileType(&quot;fileType1&quot;);
 * fileTypeIcon.setFileTypeCategory(&quot;fileTypeCategory1&quot;);
 * fileTypeIcon.setIconPath(&quot;iconPath1&quot;);
 *
 * // Create fileTypeIcon
 * fileTypeIconService.create(fileTypeIcon);
 * // The corresponding record will be inserted into database.
 * // And the id of newly created fileTypeIcon will be set.
 *
 * fileTypeIcon.setFileType(&quot;fileType2&quot;);
 * // Update fileTypeIcon
 * fileTypeIconService.update(fileTypeIcon);
 * // The corresponding record will be updated in database
 *
 * // Retrieve fileTypeIcon
 * fileTypeIcon = fileTypeIconService.get(fileTypeIcon.getId());
 * // The corresponding record will be retrieved from database
 *
 * // Retrieve all fileTypeIcons
 * List&lt;FileTypeIcon&gt; fileTypeIcons = fileTypeIconService.getAll();
 * // All fileTypeIcons will be retrieved from database;
 *
 * // Retrieve all file type categories
 * List&lt;String&gt; fileTypeCategories = fileTypeIconService.getAllFileTypeCategories();
 * // All distinct file type categories will be retrieved from database;
 *
 * // Remove fileTypeIcon
 * fileTypeIconService.delete(fileTypeIcon.getId());
 * // The corresponding record will be removed in database
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is mutable since it provides public setters for its properties. But it
 * doesn't change its state and is thread safe when the following condition is met: this class is initialized by
 * Spring right after construction and its parameters are never changed after that. All entities passed to this class
 * are used by the caller in thread safe manner (accessed from a single thread only).
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public class FileTypeIconServiceImpl extends BaseMiscService implements FileTypeIconService {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = FileTypeIconServiceImpl.class.getName();

    /**
     * The JPQL to query the icon.
     */
    private static final String JPQL_QUERY_ICON = "SELECT e FROM FileTypeIcon e";

    /**
     * The JPQL to query the category.
     */
    private static final String JPQL_QUERY_CATEGORY = "SELECT DISTINCT e.fileTypeCategory FROM FileTypeIcon e";

    /**
     * Creates an instance of FileTypeIconServiceImpl.
     */
    public FileTypeIconServiceImpl() {
        // Empty
    }

    /**
     * This method will create the file type icon. It will also set newly assigned id to the given entity.
     *
     * @param icon
     *            the file type icon to create.
     *
     * @throws IllegalArgumentException
     *             if icon is null.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    public void create(FileTypeIcon icon) throws PersistenceException {
        String signature = CLASS_NAME + ".create(FileTypeIcon icon)";

        MiscHelper.create(getLog(), signature, getEntityManager(), icon, "icon");
    }

    /**
     * This method will update the file type icon.
     *
     * @param icon
     *            the file type icon to update.
     *
     * @throws IllegalArgumentException
     *             if icon is null, or icon.id is not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    public void update(FileTypeIcon icon) throws PersistenceException {
        String signature = CLASS_NAME + ".update(FileTypeIcon icon)";

        MiscHelper.update(getLog(), signature, getEntityManager(), icon, "icon");
    }

    /**
     * This method will remove the file type icon with the given id.
     *
     * @param iconId
     *            the id of the file type icon to remove.
     *
     * @throws IllegalArgumentException
     *             if iconId is not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    public void delete(long iconId) throws PersistenceException {
        String signature = CLASS_NAME + ".delete(long iconId)";

        MiscHelper.delete(getLog(), signature, getEntityManager(), FileTypeIcon.class, iconId, "iconId");
    }

    /**
     * This method will retrieve the file type icon with the given id.
     *
     * @param iconId
     *            the id of the file type icon to retrieve.
     *
     * @return the file type icon with the given id
     *
     * @throws IllegalArgumentException
     *             if iconId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    public FileTypeIcon get(long iconId) throws PersistenceException {
        String signature = CLASS_NAME + ".get(long iconId)";

        return MiscHelper.get(getLog(), signature, getEntityManager(), FileTypeIcon.class, iconId, "iconId");
    }

    /**
     * This method will retrieve all file type icons.
     *
     * @return all file type icons
     *
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    public List<FileTypeIcon> getAll() throws PersistenceException {
        String signature = CLASS_NAME + ".getAll()";

        return MiscHelper.getEntities(getLog(), signature, getEntityManager(), JPQL_QUERY_ICON, null);
    }

    /**
     * This method will retrieve all file type categories.
     *
     * @return all file type categories
     *
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    public List<String> getAllFileTypeCategories() throws PersistenceException {
        String signature = CLASS_NAME + ".getAllFileTypeCategories()";

        return MiscHelper.getEntities(getLog(), signature, getEntityManager(), JPQL_QUERY_CATEGORY, null);
    }
}
