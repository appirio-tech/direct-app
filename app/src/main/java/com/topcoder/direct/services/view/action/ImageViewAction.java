/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action;

import com.topcoder.direct.services.view.action.asset.BaseAbstractAssetAction;

/**
 * <p>
 * The abstract action for actions which need to view images file
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit Asset View Release 3)
 */
public abstract class ImageViewAction extends BaseAbstractAssetAction {


    /**
     * Gets the custom image in bytes.
     *
     * @return the custom image in bytes.
     */
    abstract public byte[] getCustomImageInBytes();

    /**
     * Gets the custom content type.
     *
     * @return the custom content type.
     */
    abstract public String getCustomContentType();

    /**
     * Gets the custom content disposition.
     *
     * @return the custom content disposition.
     */
    abstract public String getCustomContentDisposition();
}
