/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.configs;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * The file type class for xml handling.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestType")
public class FileType {
    @XmlAttribute(name = "id")
    private long id;

    @XmlElement(name = "fileFormat")
    private List<FileFormat> fileFormats;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<FileFormat> getFileFormats() {
        if (fileFormats == null) {
            fileFormats = new ArrayList<FileFormat>();
        }
        return fileFormats;
    }

    public void setFileFormats(List<FileFormat> fileFormats) {
        this.fileFormats = fileFormats;
    }
}
