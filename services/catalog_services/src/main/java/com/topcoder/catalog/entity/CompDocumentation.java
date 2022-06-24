/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>This class stores the documentation associated with the component compVersion.</p>
 * <p>Validation of parameters is not performed in this class. It's supposed to be a caller's responsibility.</p>
 * <p>
 * Changes in v1.2 (Cockpit Upload Attachment):
 * - Added annotations
 * - Added serialVersionUID
 * </p>
 * <p><strong>Thread safety: </strong></p> <p>This class is mutable and not thread safe.</p>
 *
 * @author cucu, KingStone, pulky
 * @version 1.2
 * @since 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "compDocumentation", propOrder = { "documentName", "documentType", "documentTypeId", "id", "url"})
public class CompDocumentation implements Serializable {

    /**
     * Serial version UID
     *
     * @since 1.2
     */
    private static final long serialVersionUID = -3661634653346198929L;

    /**
     * @since BUGR-1600
     */
    public static final Long JAVADOCS = 23L;

    /**
     * <p>This field represents the id of the entity.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private Long id;

    /**
     * <p>This field represents the component compVersion which document is associated with.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>CompVersion</code> value or <code>null</code>.</p>
     */
    @XmlTransient
    private CompVersion compVersion;

    /**
     * <p>This field represents the documentType's id of document.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private Long documentTypeId;

    /**
     * <p>This field represents the documentType of document.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String documentType;

    /**
     * <p>This field represents the name of document.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String documentName;

    /**
     * <p>This field represents the url of document.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String url;

    /**
     * <p>Default constructor.</p> <p><em>Does nothing.</em></p>
     */
    public CompDocumentation() {
    }

    /**
     * <p>Sets a value to the {@link #id} field.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     *
     * @param id the id of the entity.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>Retrieves the id of the entity.</p>
     *
     * @return {@link #id} property's value.
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>Sets a value to the {@link #compVersion} field.</p>
     * <p>The acceptance region: any <code>CompVersion</code> value or <code>null</code>.</p>
     * @param compVersion the component compVersion which link is associated with
     */
    public void setCompVersion(CompVersion compVersion) {
        this.compVersion = compVersion;
    }

    /**
     * <p>Retrieves the component compVersion which link is associated with.</p>
     *
     * @return {@link #compVersion} property's value.
     */
    public CompVersion getCompVersion() {
        return compVersion;
    }

    /**
     * <p>Sets a value to the {@link #documentTypeId} field.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     * @param documentTypeId document type id of the document.
     */
    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    /**
     * <p>Retrieves document type id of the document.</p>
     *
     * @return {@link #documentTypeId} property's value.
     */
    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    /**
     * <p>Sets a value to the {@link #documentType} field.</p>
     * <p>The acceptance region: any <code>String</code> value or <code>null</code>.</p>
     * @param documentType type of the document.
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    /**
     * <p>Retrieves type of the document.</p>
     *
     * @return {@link #documentType} property's value.
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * <p>Sets a value to the {@link #documentName} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     * @param documentName name of the document.
     */
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    /**
     * <p>Retrieves name of the document.</p>
     *
     * @return {@link #documentName} property's value.
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * <p>Sets a value to the {@link #url} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     * @param url url of the document.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * <p>Retrieves url of the document.</p>
     *
     * @return {@link #url} property's value.
     */
    public String getUrl() {
        return url;
    }
}
