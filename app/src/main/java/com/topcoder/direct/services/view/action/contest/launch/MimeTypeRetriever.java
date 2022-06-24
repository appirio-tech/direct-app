/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import java.util.HashMap;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.lang.StringUtils;

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
     * Default mime type.
     * </p>
     */
    private static final String DEFAULT_MIME_TYPE = "application/octet-stream";

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
     * Static initialization block used to initialize the static members of this class. It initializes all the MIME
     * maps used by the class.
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

        mimeTypeIdByMimeType.put("application/msword", new Long(1));
        mimeTypeIdByMimeType.put("application/rtf", new Long(2));
        mimeTypeIdByMimeType.put("text/plain", new Long(3));
        mimeTypeIdByMimeType.put("application/pdf", new Long(4));
        mimeTypeIdByMimeType.put("application/postscript", new Long(5));

        mimeTypeIdByMimeType.put("text/html", new Long(6));
        mimeTypeIdByMimeType.put("text/rtf", new Long(7));
        mimeTypeIdByMimeType.put("image/jpeg", new Long(8));
        mimeTypeIdByMimeType.put("image/gif", new Long(9));
        mimeTypeIdByMimeType.put("image/png", new Long(10));

        mimeTypeIdByMimeType.put("image/bmp", new Long(11));
        mimeTypeIdByMimeType.put("application/vnd.ms-excel", new Long(12));
        mimeTypeIdByMimeType.put("image/pjpeg", new Long(13));
        mimeTypeIdByMimeType.put("image/x-png", new Long(14));
        mimeTypeIdByMimeType.put("application/zip", new Long(15));

        mimeTypeIdByMimeType.put("application/x-zip-compressed", new Long(16));
        mimeTypeIdByMimeType.put("audio/mpeg", new Long(17));
        mimeTypeIdByMimeType.put("audio/mp3", new Long(18));
        mimeTypeIdByMimeType.put("application/x-zip", new Long(19));
        mimeTypeIdByMimeType.put("application/java-archive", new Long(20));

        mimeTypeIdByMimeType.put("application/x-java-archive", new Long(21));
        mimeTypeIdByMimeType.put("application/vnd.ms-powerpoint", new Long(22));
        mimeTypeIdByMimeType.put("image/photoshop", new Long(23));
        mimeTypeIdByMimeType.put("image/x-photoshop", new Long(24));
        mimeTypeIdByMimeType.put("image/psd", new Long(25));

        mimeTypeIdByMimeType.put("application/photoshop", new Long(26));
        mimeTypeIdByMimeType.put("application/psd", new Long(27));
        mimeTypeIdByMimeType.put("zz-application/zz-winassoc-psd", new Long(28));
        mimeTypeIdByMimeType.put("image/ico", new Long(29));
        mimeTypeIdByMimeType.put("image/x-ico", new Long(30));
        mimeTypeIdByMimeType.put("application/octet-stream", new Long(31));
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

        mimeTypesFileTypeMap.addMimeTypes("application/psd psd");
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
        return getMimeTypeIdFromMimeType(mimeType);
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
        if(StringUtils.isBlank(mimeType)) {
            mimeType = DEFAULT_MIME_TYPE;
        }
        return !mimeTypeIdByMimeType.containsKey(mimeType) ? mimeTypeIdByMimeType.get(DEFAULT_MIME_TYPE)
            : mimeTypeIdByMimeType.get(mimeType);
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
