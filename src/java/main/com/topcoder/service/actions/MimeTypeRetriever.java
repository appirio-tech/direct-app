/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import java.util.HashMap;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * This is an useful class to retrieve the MIME type from the file name and the MIME type id. It uses the
 * MimetypesFileTypeMap to retrieve the MIME type. The IDs are hardcoded.
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> The state class doesn't change and the mimetypesFileTypeMap usage (only has retrieve method)
 * is thread safe, so the class is thread safe.
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
public class MimeTypeRetriever {

    /**
     * <p>
     * Represents the map for converting file extension to MIME type.
     * </p>
     *
     * <p>
     * It's created in static initializer and it will never be null once initialized.
     * </p>
     */
    private static MimetypesFileTypeMap mimeTypesFileTypeMap;

    /**
     * <p>
     * Represents the map for converting MIME type string to its ID.
     * </p>
     *
     * <p>
     * It's created in static initializer and it will never be null once initialized.
     * </p>
     */
    private static Map<String, Long> mimeTypeIdByMimeType;

    /**
     * <p>
     * Represents the map for converting MIME type ID to its string MIME type.
     * </p>
     *
     * <p>
     * It's created in static initializer and it will never be null once initialized.
     * </p>
     */
    private static Map<Long, String> mimeTypeByMimeTypeId;

    /**
     * Default constructor, creates new instance.
     */
    public MimeTypeRetriever() {
    }

    /**
     * Static initialization block used to initialize the static members of this class. It initializes
     * all the MIME maps used by the class.
     */
    static {
        initMimeTypeIdByMimeType();
        initMimeTypesFileTypeMap();

        // initialize the map which maps MIME type ID to corresponding MIME type
        mimeTypeByMimeTypeId = new HashMap<Long, String>();
        for (String key : mimeTypeIdByMimeType.keySet()) {
            mimeTypeByMimeTypeId.put(mimeTypeIdByMimeType.get(key), key);
        }
    }

    /**
     * Initializes the <code>mimeTypeIdByMimeType</code> object.
     */
    private static void initMimeTypeIdByMimeType() {
        // initialize the map which maps MIME type to corresponding ID, starting
        // ID with 1
        mimeTypeIdByMimeType = new HashMap<String, Long>();

        Long id = new Long(1);
        mimeTypeIdByMimeType.put("application/msword", id++);
        mimeTypeIdByMimeType.put("application/rtf", id++);
        mimeTypeIdByMimeType.put("text/plain", id++);
        mimeTypeIdByMimeType.put("application/pdf", id++);
        mimeTypeIdByMimeType.put("application/postscript", id++);

        mimeTypeIdByMimeType.put("text/html", id++);
        mimeTypeIdByMimeType.put("image/jpeg", id++);
        mimeTypeIdByMimeType.put("image/gif", id++);
        mimeTypeIdByMimeType.put("image/png", id++);

        mimeTypeIdByMimeType.put("image/bmp", id++);
        mimeTypeIdByMimeType.put("application/vnd.ms-excel", id++);
        mimeTypeIdByMimeType.put("application/zip", id++);

        mimeTypeIdByMimeType.put("application/x-zip-compressed", id++);
        mimeTypeIdByMimeType.put("audio/mpeg", id++);
        mimeTypeIdByMimeType.put("audio/mp3", id++);
        mimeTypeIdByMimeType.put("application/java-archive", id++);
        mimeTypeIdByMimeType.put("application/vnd.ms-powerpoint", id++);
    }

    /**
     * Initializes the <code>mimeTypesFileTypeMap</code> object.
     */
    private static void initMimeTypesFileTypeMap() {
        // map each MIME type to its corresponding file extension(s)
        mimeTypesFileTypeMap = new MimetypesFileTypeMap();

        mimeTypesFileTypeMap.addMimeTypes("application/msword doc");
        mimeTypesFileTypeMap.addMimeTypes("application/rtf rtf");
        mimeTypesFileTypeMap.addMimeTypes("text/plain txt");
        mimeTypesFileTypeMap.addMimeTypes("application/pdf pdf");
        mimeTypesFileTypeMap.addMimeTypes("application/postscript ps");

        mimeTypesFileTypeMap.addMimeTypes("text/html html htm");
        mimeTypesFileTypeMap.addMimeTypes("image/jpeg jpg jpeg");
        mimeTypesFileTypeMap.addMimeTypes("image/gif gif");
        mimeTypesFileTypeMap.addMimeTypes("image/png png");

        mimeTypesFileTypeMap.addMimeTypes("image/bmp bmp");
        mimeTypesFileTypeMap.addMimeTypes("application/vnd.ms-excel xls");
        mimeTypesFileTypeMap.addMimeTypes("application/zip zip");

        mimeTypesFileTypeMap.addMimeTypes("application/x-zip-compressed rar");
        mimeTypesFileTypeMap.addMimeTypes("audio/mpeg mpg");
        mimeTypesFileTypeMap.addMimeTypes("audio/mp3 mp3");
        mimeTypesFileTypeMap.addMimeTypes("application/java-archive jar");
        mimeTypesFileTypeMap.addMimeTypes("application/vnd.ms-powerpoint ppt pps");
    }

    /**
     * Returns the MIME type string for the file name, or <code>application/octet-stream</code> if none found.
     *
     * @param fileName the file name used to retrieve the MIME type
     *
     * @return the MIME type string for the file name
     *
     * @throws IllegalArgumentException if the file name is null or empty
     */
    public String getMimeTypeFromFileName(String fileName) {
        ExceptionUtils.checkNullOrEmpty(fileName, null, null, "fileName cannot be null or empty");
        return mimeTypesFileTypeMap.getContentType(fileName);
    }

    /**
     * Returns the MIME type ID for the file name, or -1 if none found.
     *
     * @param fileName the file name used to retrieve the MIME type
     *
     * @return the MIME type ID for the file name
     *
     * @throws IllegalArgumentException if the file name is null or empty
     */
    public long getMimeTypeIdFromFileName(String fileName) {
        ExceptionUtils.checkNullOrEmpty(fileName, null, null, "fileName cannot be null or empty");
        String mimeType = mimeTypesFileTypeMap.getContentType(fileName);
        return !mimeTypeIdByMimeType.containsKey(mimeType) ? -1 : mimeTypeIdByMimeType.get(mimeType);
    }

    /**
     * Returns the MIME type ID for the MIME type string, or -1 if none found.
     *
     * @param mimeType the MIME type string used to retrieve the MIME type ID
     *
     * @return the MIME type ID for the MIME type string
     *
     * @throws IllegalArgumentException if the MIME type is null or empty
     */
    public long getMimeTypeIdFromMimeType(String mimeType) {
        ExceptionUtils.checkNullOrEmpty(mimeType, null, null, "mimeType cannot be null or empty");
        return !mimeTypeIdByMimeType.containsKey(mimeType) ? -1 : mimeTypeIdByMimeType.get(mimeType);
    }

    /**
     * Returns the MIME type string for the MIME type ID, or null if none found.
     *
     * @param mimeTypeId the MIME type ID used to retrieve the MIME type string
     *
     * @return the MIME type string for the MIME type ID
     *
     * @throws IllegalArgumentException if the MIME type ID is less than 1
     */
    public String getMimeTypeFromMimeTypeId(long mimeTypeId) {
        if (mimeTypeId < 1) {
            throw new IllegalArgumentException("mimeTypeId must be >= 1");
        }
        return mimeTypeByMimeTypeId.get(mimeTypeId);
    }
}
