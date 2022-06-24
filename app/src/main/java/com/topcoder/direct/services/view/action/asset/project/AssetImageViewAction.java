/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.asset.project;

import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.AssetVersion;
import com.topcoder.direct.services.view.action.ImageViewAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * <p>
 * This action handles the requests to view the asset image file and asset image preview file.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Cockpit Asset View Release 4 - Resource restriction update)
 * <ul>
 *     <li>Updated {@link #executeAction()} to check if user has download permission to access the asset image
 *     or the preview thumbnail of the asset image</li>
 * </ul>
 * </p>
 *
 * @author GreatKevin, TCSASSEMBLER
 * @version 1.1
 */
public class AssetImageViewAction extends ImageViewAction {

    /**
     * The image bytes.
     */
    private byte[] imageInByte = null;

    /**
     * The asset version id.
     */
    private long assetVersionId;

    /**
     * Whether it's a preview request.
     */
    private boolean preview;

    /**
     * The path of the image to view.
     */
    private String imagePath;

    /**
     * Gets the asset version id.
     *
     * @return the asset version id.
     */
    public long getAssetVersionId() {
        return assetVersionId;
    }

    /**
     * Sets the asset version id.
     *
     * @param assetVersionId the asset version id.
     */
    public void setAssetVersionId(long assetVersionId) {
        this.assetVersionId = assetVersionId;
    }

    /**
     * Gets whether it's preview request.
     *
     * @return whether it's preview request.
     */
    public boolean isPreview() {
        return preview;
    }

    /**
     * Sets whether it's preview request.
     *
     * @param preview whether it's preview request.
     */
    public void setPreview(boolean preview) {
        this.preview = preview;
    }

    /**
     * Gets the custom image bytes.
     *
     * @return the customer image bytes.
     */
    @Override
    public byte[] getCustomImageInBytes() {

        return imageInByte;
    }

    /**
     * Gets the custom content type.
     *
     * @return the custom content type.
     */
    @Override
    public String getCustomContentType() {
        return DirectUtils.getFileMIMEType(new File(this.imagePath));
    }

    /**
     * Gets the custom content disposition.
     *
     * @return the custom content disposition.
     */
    @Override
    public String getCustomContentDisposition() {
        return FilenameUtils.getName(this.imagePath);
    }

    /**
     * The action execution logic.
     *
     * @throws Exception if any error.
     */
    @Override
    public void executeAction() throws Exception {

        BufferedImage originalImage;


        if (getAssetVersionId() <= 0) {
            throw new IllegalArgumentException("The asset version id is not positive");
        }

        AssetVersion assetVersion = getAssetVersionService().getAssetVersion(getAssetVersionId());

        if (assetVersion == null) {
            throw new IllegalArgumentException("The specific asset version does not exist");
        }

        // now we need to check if user has access to the asset the asset version belong to
        Asset assetToCheck = getAssetService().getAsset(assetVersion.getAssetId());
        checkIfAssetDownloadAllowed(assetToCheck, DirectUtils.getTCSubjectFromSession());

        File imageFile = null;

        if (isPreview()) {
            imagePath = assetVersion.getPreviewImagePath();
        } else {
            imagePath = assetVersion.getFilePath();
        }

        imageFile = new File(imagePath);

        originalImage = ImageIO.read(imageFile);

        // convert BufferedImage to byte array

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(originalImage, FilenameUtils.getExtension(imagePath).toLowerCase(), baos);

        baos.flush();
        imageInByte = baos.toByteArray();
        baos.close();
    }
}
