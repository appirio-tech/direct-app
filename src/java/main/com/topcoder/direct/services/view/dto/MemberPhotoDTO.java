/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

/**
 * A class to be used for represent member photo.
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TC Direct Select From Copilot Pool Assembly)
 */
public class MemberPhotoDTO extends UserDTO {
    /**
     * The path of photo.
     */
    private String photoPath;

    /**
     * The width of photo.
     */
    private int width;

    /**
     * The height of photo.
     */
    private int height;

    /**
     * Get the photo path.
     *
     * @return the photo path.
     */
    public String getPhotoPath() {
        return photoPath;
    }

    /**
     * Set the photo path.
     *
     * @param photoPath the photo path to set.
     */
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    /**
     * Get the width.
     *
     * @return the width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set the width.
     *
     * @param width the width to set.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Get the height.
     *
     * @return the height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the height.
     *
     * @param height the height to set.
     */
    public void setHeight(int height) {
        this.height = height;
    }
}
