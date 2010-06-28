/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

/**
 * <p>A form bean for requests for viewing the list of registrants for desired contest.</p>
 *
 * @author isv
 * @version 1.0 (Direct Registrants List assembly)
 */
public class ContestRegistrantsForm extends ContestIdForm {

    /**
     * <p>A <code>boolean</code> flag indicating whether the retrieved data is expected to be converted into
     * <code>Excel</code> format or not.</p>
     */
    private boolean excel;

    /**
     * <p>Constructs new <code>ContestRegistrantsForm</code> instance. This implementation does nothing.</p>
     */
    public ContestRegistrantsForm() {
    }

    /**
     * <p>Constructs new <code>ContestRegistrantsForm</code> instance to be used for notification of specified party on
     * parameters of this form.</p>
     *
     * @param aware an <code>Aware</code> referencing the object to be notified on parameters of this form.
     */
    public ContestRegistrantsForm(Aware aware) {
        super(aware);
    }

    /**
     * <p>Gets the flag indicating whether the retrieved data is expected to be converted into <code>Excel</code> format
     * or not.</p>
     *
     * @return <code>true</code> if registrants list is to be converted into <code>Excel</code> format;
     *         <code>false</code> otherwise.
     */
    public boolean isExcel() {
        return this.excel;
    }

    /**
     * <p>Sets the flag indicating whether the retrieved data is expected to be converted into <code>Excel</code> format
     * or not.</p>
     *
     * @param excel <code>true</code> if registrants list is to be converted into <code>Excel</code> format;
     *        <code>false</code> otherwise.
     */
    public void setExcel(boolean excel) {
        this.excel = excel;
    }
}
