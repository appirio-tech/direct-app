/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services;

import java.util.List;

import com.topcoder.asset.entities.FileTypeIcon;
import com.topcoder.asset.exceptions.EntityNotFoundException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.asset.exceptions.ServiceException;

/**
 * <p>
 * This interface represents a file type icon service. It defines CURD methods for FileTypeIcon. It aslo define a
 * method to retrieve all file type categories.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> Implementations of this interface are required to be thread safe when entities
 * passed to them are used by the caller in thread safe manner.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public interface FileTypeIconService {
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
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void create(FileTypeIcon icon) throws ServiceException;

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
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void update(FileTypeIcon icon) throws ServiceException;

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
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void delete(long iconId) throws ServiceException;

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
     * @throws ServiceException
     *             if any other error occurs.
     */
    public FileTypeIcon get(long iconId) throws ServiceException;

    /**
     * This method will retrieve all file type icons.
     *
     * @return all file type icons
     *
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public List<FileTypeIcon> getAll() throws ServiceException;

    /**
     * This method will retrieve all file type categories.
     *
     * @return all file type categories
     *
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public List<String> getAllFileTypeCategories() throws ServiceException;
}
