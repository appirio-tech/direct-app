/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.asset.project;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class provided static methods for handling asset file types.
 * </p>
 *
 * @author GreatKevin
 * @version 1.0 (Release Assembly - TopCoder Cockpit Asset View And Basic Upload version 1.0)
 */
public class AssetFileTypes {
    /**
     * The static map to store the mapping for file extension to file icon name. Used by the front end.
     */
    private static final Map<String, String> fileTypeIcons = new HashMap<String, String>();

    /**
     * The default file type icon name.
     */
    private static final String DEFAULT_FILE_TYPE_ICON = "other";

    /**
     * Static initializer
     */
    static {
        fileTypeIcons.put("docx", "doc");
        fileTypeIcons.put("doc", "doc");
        fileTypeIcons.put("rtf", "doc");
        fileTypeIcons.put("pdf", "pdf");
        fileTypeIcons.put("psd", "psd");
        fileTypeIcons.put("xls", "xls");
        fileTypeIcons.put("xlsx", "xls");
        fileTypeIcons.put("ai", "ai");
        fileTypeIcons.put("png", "png");
        fileTypeIcons.put("zip", "zip");
        fileTypeIcons.put("tar", "zip");
        fileTypeIcons.put("rar", "rar");
        fileTypeIcons.put("swf", "swf");
        fileTypeIcons.put("7z", "7zip");
        fileTypeIcons.put("ppt", "ppt");
        fileTypeIcons.put("pptx", "ppt");
        fileTypeIcons.put("html", "html");
        fileTypeIcons.put("css", "css");
        fileTypeIcons.put("txt", "txt");
        fileTypeIcons.put("js", "js");
        fileTypeIcons.put("gif", "gif");
        fileTypeIcons.put("xml", "xml");
        fileTypeIcons.put("jpg", "jpg");
        fileTypeIcons.put("bmp", "bmp");
    }

    /**
     * Gets the file icon name by the given file type name.
     *
     * @param fileType the file type name.
     * @return the file icon name.
     */
    public static String getFileTypeIconName(String fileType) {
        if (fileTypeIcons.containsKey(fileType.toLowerCase())) {
            return fileTypeIcons.get(fileType.toLowerCase());
        } else {
            return DEFAULT_FILE_TYPE_ICON;
        }
    }
}
