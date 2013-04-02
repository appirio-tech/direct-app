/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.accounting;

import com.topcoder.clients.dao.ClientInvoiceUploadDAO;
import com.topcoder.clients.dao.ClientInvoiceUploadPersistenceException;
import com.topcoder.clients.model.ClientInvoiceUpload;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.services.ClientService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This action handles all the front end operations of invoice upload management page.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 ((Release Assembly - TopCoder Cockpit - Billing Management)
 */
public class ClientInvoiceManageAction extends BaseDirectStrutsAction {

    /**
     * The client service, it's used to get all the clients of TopCoder.
     */
    private ClientService clientService;

    /**
     * The client invoice upload DAO.
     */
    private ClientInvoiceUploadDAO invoiceUploadPersistence;

    /**
     * The root path of the uploaded invoice file will be put into. It's injected by spring injection.
     */
    private String invoiceUploadRootPath;

    /**
     * All the clients.
     */
    private List<Client> clients;

    /**
     * A list of <code>ClientInvoiceUpload</code> of the client.
     */
    private List<ClientInvoiceUpload> clientInvoices;

    /**
     * The client id.
     */
    private long clientId;

    /**
     * The name of the uploaded invoice file.
     */
    private String fileName;

    /**
     * The description of the uploaded invoice file.
     */
    private String description;

    /**
     * The invoice date.
     */
    private String invoiceDate;

    /**
     * The id of the the invoice upload.
     */
    private long invoiceUploadId;

    /**
     * The start date used to filter client invoices.
     */
    private Date startDate;

    /**
     * The end date used to filter client invoices.
     */
    private Date endDate;

    /**
     * The uploaded invoice file.
     */
    private File uploadInvoiceFile;

    /**
     * The input stream to download uploaded invoice file.
     */
    private InputStream invoiceDownloadStream;

    /**
     * Sets the client service.
     *
     * @param clientService the client service.
     */
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Sets the invoice upload persistence.
     *
     * @param invoiceUploadPersistence the invoice upload persistence.
     */
    public void setInvoiceUploadPersistence(ClientInvoiceUploadDAO invoiceUploadPersistence) {
        this.invoiceUploadPersistence = invoiceUploadPersistence;
    }

    /**
     * Gets the list of Clients
     *
     * @return the list of Clients.
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * Sets the list of clients.
     *
     * @param clients the list of clients.
     */
    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    /**
     * Gets the client id.
     *
     * @return the client id.
     */
    public long getClientId() {
        return clientId;
    }

    /**
     * Sets the client id.
     *
     * @param clientId the client id.
     */
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets whether the client invoice upload / management feature is available to current user.
     *
     * @return whether the client invoice upload / management feature is available to current user.
     */
    public boolean isClientInvoiceUploadViewable() {
        return DirectUtils.isCockpitAdmin(DirectUtils.getTCSubjectFromSession());
    }

    /**
     * Gets whether the user is a client user
     *
     * @return whether the user is a client user
     * @throws Exception if there is any error
     */
    public boolean isClientInvoicesViewable() throws Exception {
        return DirectUtils.isClientUser(DirectUtils.getTCSubjectFromSession().getUserId());
    }

    /**
     * Gets the uploaded invoice file
     *
     * @return the uploaded invoice file
     */
    public File getUploadInvoiceFile() {
        return uploadInvoiceFile;
    }

    /**
     * Sets the uploaded invoice file.
     *
     * @param uploadInvoiceFile the uploaded invoice file.
     */
    public void setUploadInvoiceFile(File uploadInvoiceFile) {
        this.uploadInvoiceFile = uploadInvoiceFile;
    }

    /**
     * Sets the root file path to put the invoice upload to
     *
     * @param invoiceUploadRootPath the root file path to put the invoice upload to
     */
    public void setInvoiceUploadRootPath(String invoiceUploadRootPath) {
        this.invoiceUploadRootPath = invoiceUploadRootPath;
    }

    /**
     * Gets the description of the uploaded invoice file.
     *
     * @return the description of the uploaded invoice file.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the uploaded invoice file
     *
     * @param description the description of the uploaded invoice file
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the file name.
     *
     * @return the file name.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the file name.
     *
     * @param fileName the file name.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets the invoice date.
     *
     * @return the invoice date.
     */
    public String getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * Sets the invoice date.
     *
     * @param invoiceDate the invoice date.
     */
    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * Gets the invoice upload id.
     *
     * @return the invoice upload id.
     */
    public long getInvoiceUploadId() {
        return invoiceUploadId;
    }

    /**
     * Sets the invoice upload id.
     *
     * @param invoiceUploadId the invoice upload id.
     */
    public void setInvoiceUploadId(long invoiceUploadId) {
        this.invoiceUploadId = invoiceUploadId;
    }

    /**
     * Gets the client invoices.
     *
     * @return the client invoices
     */
    public List<ClientInvoiceUpload> getClientInvoices() {
        return clientInvoices;
    }

    /**
     * Sets the client invoices.
     *
     * @param clientInvoices the client invoices.
     */
    public void setClientInvoices(List<ClientInvoiceUpload> clientInvoices) {
        this.clientInvoices = clientInvoices;
    }

    /**
     * Gets the start date.
     *
     * @return the start date.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate the start date.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date.
     *
     * @return the end date.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date.
     *
     * @param endDate the end date.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the input stream to download the uploaded invoice file.
     *
     * @return the input stream to download the uploaded invoice file.
     */
    public InputStream getInvoiceDownloadStream() {
        return invoiceDownloadStream;
    }

    /**
     * The entry action for the client invoices page.
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {

        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();
        long userClientId = DirectUtils.getUserClientId(currentUser.getUserId());

        if (userClientId > 0) {
            // client user, list all the invoices of the client
            setClientInvoices(invoiceUploadPersistence.getAllByClientId(userClientId));
        }

        if (DirectUtils.isTCAccounting(currentUser)) {
            // prepare all clients options for the admin to choose
            setClients(clientService.getAllClients());
        }

    }

    /**
     * Handles the AJAX operation of filtering client invoices by client, start date and end date.
     *
     * @return the result code.
     */
    public String filterClientInvoices() {
        try {
            // check permission - if user is not cockpit admin, throws exception
            if (!isClientInvoiceUploadViewable()) {
                throw new Exception("You don't have permission to access client invoices");
            }

            // perform the filter invoice operation
            List<Map<String, String>> result = new ArrayList<Map<String, String>>();
            // parameter checking will be done inside the DAO
            List<ClientInvoiceUpload> invoices = invoiceUploadPersistence.getAllByClientId(getClientId(),
                    getStartDate(), getEndDate());

            DateFormat invoiceUploadDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            for (ClientInvoiceUpload invoice : invoices) {
                Map<String, String> invoiceData = new HashMap<String, String>();
                invoiceData.put("id", String.valueOf(invoice.getId()));
                invoiceData.put("invoiceDate", invoiceUploadDateFormat.format(invoice.getInvoiceUploadDate()));
                invoiceData.put("fileName", invoice.getFileName());
                invoiceData.put("createDate", invoiceUploadDateFormat.format(invoice.getCreateDate()));
                invoiceData.put("description", invoice.getDescription());
                result.add(invoiceData);
            }

            setResult(result);

        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }
        return SUCCESS;
    }

    /**
     * Handles the AJAX operation of deleting uploaded invoice file.
     *
     * @return the result code.
     */
    public String deleteClientInvoice() {
        try {
            // check permission - if user is not cockpit admin, throws exception
            if (!isClientInvoiceUploadViewable()) {
                throw new Exception("You don't have permission to delete invoice");
            }

            // get the invoice upload to delete first
            ClientInvoiceUpload invoiceToDelete = invoiceUploadPersistence.get(getInvoiceUploadId());
            File deleteFile = new File(getUploadInvoicePath(invoiceToDelete.getFileName(),
                    invoiceToDelete.getClientId()));

            if (!deleteFile.exists()) {
                throw new IllegalArgumentException("The file to delete does not exist");
            }

            // perform the delete operation
            Map<String, String> result = new HashMap<String, String>();
            invoiceUploadPersistence.delete(getInvoiceUploadId());
            result.put("operation", "delete");
            result.put("invoiceId", String.valueOf(getInvoiceUploadId()));
            result.put("invoiceFileName", invoiceToDelete.getFileName());

            // delete file
            FileUtils.forceDelete(deleteFile);

            setResult(result);

        } catch (Throwable e) {


            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Handles the operation of uploading client invoice file.
     *
     * @return result code.
     */
    public String uploadClientInvoice() throws Exception {

        File uploadFile = null;
        InputStream uploadFileStream = null;
        FileOutputStream saveFileStream = null;
        boolean needToDelete = false;

        try {
            // check permission - if user is not cockpit admin, throws exception
            if (!isClientInvoiceUploadViewable()) {
                throw new Exception("You don't have permission to upload invoice document");
            }

            uploadFile = new File(getUploadInvoicePath(getFileName(), getClientId()));

            if (uploadFile.exists()) {
                // if file exists, throw exception
                throw new IllegalArgumentException("The invoice file to upload already exists");
            }

            Date currentTime = new Date();
            Date invoiceDate = new SimpleDateFormat("MM/dd/yyyy").parse(getInvoiceDate());

            if (invoiceDate.after(currentTime)) {
                throw new IllegalArgumentException("The invoice date should not be a future date");
            }

            uploadFileStream = new FileInputStream(getUploadInvoiceFile());
            saveFileStream = new FileOutputStream(uploadFile);

            // save the upload file after inserting the record
            IOUtils.copy(uploadFileStream, saveFileStream);

            // build ClientInvoiceUpload
            ClientInvoiceUpload record = new ClientInvoiceUpload();
            record.setDescription(getDescription());
            record.setInvoiceUploadDate(invoiceDate);
            record.setClientId(getClientId());
            record.setFileName(getFileName());
            record.setCreateDate(currentTime);
            record.setCreateUser(String.valueOf(DirectUtils.getTCSubjectFromSession().getUserId()));

            // insert the record
            invoiceUploadPersistence.create(record);

            // build the ajax response
            Map<String, String> result = new HashMap<String, String>();
            result.put("id", String.valueOf(record.getId()));
            result.put("fileName", record.getFileName());
            result.put("operation", "upload");

            setResult(result);

        } catch (ClientInvoiceUploadPersistenceException cipe) {

            needToDelete = true;

            if (getModel() != null) {
                setResult(cipe);
            }
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        } finally {
            try {
                if (uploadFileStream != null) {
                    uploadFileStream.close();
                }
                if (saveFileStream != null) {
                    saveFileStream.close();
                }
            } catch (Throwable ex) {

            }
        }

        // failed to insert record
        if (uploadFile != null && uploadFile.exists() && needToDelete) {
            // delete the file
            FileUtils.forceDelete(uploadFile);
        }

        return SUCCESS;
    }

    /**
     * Handles the operation of downloading the uploaded invoice file.
     *
     * @return the result code.
     */
    public String downloadInvoiceUpload() {
        try {
            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();
            // get the invoice upload to delete first
            ClientInvoiceUpload invoiceToDownload = invoiceUploadPersistence.get(getInvoiceUploadId());

            // check permission - if user is not cockpit admin and the download invoice is not the client the user
            // belongs to, throws exception
            if (!isClientInvoiceUploadViewable() && !(DirectUtils.getUserClientId(currentUser.getUserId()) ==
                    invoiceToDownload.getClientId())) {
                throw new Exception("You don't have permission to download invoice");
            }

            setFileName(invoiceToDownload.getFileName());
            this.invoiceDownloadStream = new BufferedInputStream(new FileInputStream(getUploadInvoicePath
                    (invoiceToDownload.getFileName(), invoiceToDownload.getClientId())));

        } catch (Throwable e) {

            if (getModel() != null) {
                setResult(e);
            }
        }

        return "download";
    }

    /**
     * Gets the absolute path of saving client's invoice upload file. The path is consisted by root path and the
     * directory named with the client id.
     *
     * @param fileName the uploaded file name.
     * @param clientId the client id.
     * @return the built absolute path to save client's invoice upload file.
     */
    private String getUploadInvoicePath(String fileName, long clientId) {
        StringBuffer path = new StringBuffer();
        path.append(invoiceUploadRootPath).append(File.separator).append(clientId).append(File.separator);
        File clientFolder = new File(path.toString());
        if (!clientFolder.exists()) {
            clientFolder.mkdir();
        }
        path.append(fileName);
        return path.toString();
    }

    /**
     * Gets whether the user can view the billing tab.
     *
     * @return whether the user can view the billing tab.
     * @throws Exception if there is any error.
     */
    public static boolean canViewBillingTab() throws Exception {
        return DirectUtils.isClientUser(DirectUtils.getTCSubjectFromSession().getUserId())
                || DirectUtils.isCockpitAdmin(DirectUtils.getTCSubjectFromSession());
    }
}
