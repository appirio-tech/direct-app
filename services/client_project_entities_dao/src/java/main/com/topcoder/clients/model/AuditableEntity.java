/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This class represents the AuditableEntity java bean. An AuditableEntity can
 * contain a id, createUsername, createDate, modifyUsername, modifyDate, name,
 * deleted. This is a simple java bean (with a default no-arg constructor and
 * for each property, a corresponding getter/setter method). This entity
 * corresponds to a mapped super class. Any attribute in this bean is OPTIONAL
 * so NO VALIDATION IS PERFORMED here. This class is Serializable (implements
 * Serializable interface).
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> This class contains only mutable fields so
 * therefore it is not thread safe.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "auditableEntity", propOrder = { "id", "createUsername",
        "createDate", "modifyUsername", "modifyDate", "name", "deleted" })
@MappedSuperclass
public abstract class AuditableEntity implements Serializable {
    /**
     * The serial version uid of this class.
     */
    private static final long serialVersionUID = -4879212317145627222L;

    /**
     * <p>
     * This field represents the 'id' property of the AuditableEntity.
     * Represents the identifier of the entity.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Entity.id [Entity.getId()] and in table entity_name.entity_name_id.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Id
    private long id;

    /**
     * <p>
     * This field represents the 'createUsername' property of the
     * AuditableEntity. Represents the creation user of the entity.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Entity.createUsername [Entity.getCreateUsername()] and in table
     * entity_name.creation_user.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "creation_user")
    private String createUsername;

    /**
     * <p>
     * This field represents the 'createDate' property of the AuditableEntity.
     * Represents the creation date of the entity.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Entity.createDate [Entity.getCreateDate()] and in table
     * entity_name.creation_date.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "creation_date")
    private Date createDate;

    /**
     * <p>
     * This field represents the 'modifyUsername' property of the
     * AuditableEntity. Represents the modification user of the entity.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Entity.modifyUsername [Entity.getModifyUsername()] and in table
     * entity_name.modification_user.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "modification_user")
    private String modifyUsername;

    /**
     * <p>
     * This field represents the 'modifyDate' property of the AuditableEntity.
     * Represents the modification date of the entity.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Entity.modifyDate [Entity.getModifyDate()] and in table
     * entity_name.modification_date.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "modification_date")
    private Date modifyDate;

    /**
     * <p>
     * This field represents the 'name' property of the AuditableEntity.
     * Represents the name of the entity.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Entity.name [Entity.getName()] and in table entity_name.name.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "name")
    private String name;

    /**
     * <p>
     * This field represents the 'deleted' property of the AuditableEntity.
     * Represents the deleted flag of the entity.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Entity.deleted [Entity.isDeleted()] and in table entity_name.is_deleted.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "is_deleted", nullable=true)
    private Boolean deleted;

    /**
     * <p>
     * Default no-arg constructor. Constructs a new 'AuditableEntity' instance.
     * </p>
     * <p>
     * It is PUBLIC because mapped super classes should be instantiable. Java
     * classes used as web service or for reading/writing xml data (e.g. web
     * service parameter class) should be instantiable.
     * </p>
     */
    public AuditableEntity() {
    }

    /**
     * <p>
     * Getter for 'createDate' property. Please refer to the related
     * 'createDate' field for more information.
     * </p>
     *
     * @return the value of the 'createDate' property. It can be any value.
     */
    public Date getCreateDate() {
        return this.createDate;
    }

    /**
     * <p>
     * Setter for 'createDate' property. Please refer to the related
     * 'createDate' field for more information.
     * </p>
     *
     * @param createDate
     *                the new createDate to be used for 'createDate' property.
     *                It can be any value.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>
     * Getter for 'createUsername' property. Please refer to the related
     * 'createUsername' field for more information.
     * </p>
     *
     * @return the value of the 'createUsername' property. It can be any value.
     */
    public String getCreateUsername() {
        return this.createUsername;
    }

    /**
     * <p>
     * Setter for 'createUsername' property. Please refer to the related
     * 'createUsername' field for more information.
     * </p>
     *
     * @param createUsername
     *                the new createUsername to be used for 'createUsername'
     *                property. It can be any value.
     */
    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    /**
     * <p>
     * Getter for 'deleted' property. Please refer to the related 'deleted'
     * field for more information.
     * </p>
     *
     * @return the value of the 'deleted' property. It can be any value.
     */
    public Boolean isDeleted() {
        return this.deleted;
    }

    /**
     * <p>
     * Setter for 'deleted' property. Please refer to the related 'deleted'
     * field for more information.
     * </p>
     *
     * @param deleted
     *                the new deleted to be used for 'deleted' property. It can
     *                be any value.
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * <p>
     * Getter for 'id' property. Please refer to the related 'id' field for more
     * information.
     * </p>
     *
     * @return the value of the 'id' property. It can be any value.
     */
    public long getId() {
        return this.id;
    }

    /**
     * <p>
     * Setter for 'id' property. Please refer to the related 'id' field for more
     * information.
     * </p>
     *
     * @param id
     *                the new id to be used for 'id' property. It can be any
     *                value.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Getter for 'modifyDate' property. Please refer to the related
     * 'modifyDate' field for more information.
     * </p>
     *
     * @return the value of the 'modifyDate' property. It can be any value.
     */
    public Date getModifyDate() {
        return this.modifyDate;
    }

    /**
     * <p>
     * Setter for 'modifyDate' property. Please refer to the related
     * 'modifyDate' field for more information.
     * </p>
     *
     * @param modifyDate
     *                the new modifyDate to be used for 'modifyDate' property.
     *                It can be any value.
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * <p>
     * Getter for 'modifyUsername' property. Please refer to the related
     * 'modifyUsername' field for more information.
     * </p>
     *
     * @return the value of the 'modifyUsername' property. It can be any value.
     */
    public String getModifyUsername() {
        return this.modifyUsername;
    }

    /**
     * <p>
     * Setter for 'modifyUsername' property. Please refer to the related
     * 'modifyUsername' field for more information.
     * </p>
     *
     * @param modifyUsername
     *                the new modifyUsername to be used for 'modifyUsername'
     *                property. It can be any value.
     */
    public void setModifyUsername(String modifyUsername) {
        this.modifyUsername = modifyUsername;
    }

    /**
     * <p>
     * Getter for 'name' property. Please refer to the related 'name' field for
     * more information.
     * </p>
     *
     * @return the value of the 'name' property. It can be any value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>
     * Setter for 'name' property. Please refer to the related 'name' field for
     * more information.
     * </p>
     *
     * @param name
     *                the new name to be used for 'name' property. It can be any
     *                value.
     */
    public void setName(String name) {
        this.name = name;
    }
}
