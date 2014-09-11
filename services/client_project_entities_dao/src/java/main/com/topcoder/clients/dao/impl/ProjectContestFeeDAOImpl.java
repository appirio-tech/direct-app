/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.clients.dao.ContestFeeConfigurationException;
import com.topcoder.clients.dao.ProjectContestFeeDAO;
import com.topcoder.clients.dao.ContestFeePersistenceException;
import com.topcoder.clients.model.BillingAccount;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.clients.model.FeeAuditRecord;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;


/**
 * Implements contract methods to manage contest fees for billing accounts. It defines CRUD operations on contest fee
 * for a billing and create/update default contest fees.
 * 
 * Thread safety: The class is mutable and not thread safe. But it'll not caused thread safety issue if used under
 * Spring container.
 * 
 * <p>
 * Version 1.1 (Release Assembly - Project Contest Fee Management ) Change notes:
 *   <ol>
 *     <li>{@link #search(int, int, String, String)} method is changed to load the client name.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - Project Contest Fees Management Update 1 Assembly) Change notes:
 *   <ol>
 *     <li>Added {@link #search(boolean, int, int, String, String)} method.</li>
 *     <li>Added {@link #getBillingAccountSize(boolean, boolean)} method.</li>
 *   </ol>
 * </p>
 * 
 * @author winstips, isv
 * @version 1.2
 */
public class ProjectContestFeeDAOImpl implements ProjectContestFeeDAO {
    /**
     * Instance of Logger used to perform logging operations. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Log logger;
    /**
     * Represents the session factory instance. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private SessionFactory sessionFactory;


    /**
     * Default Constructor.
     */
    public ProjectContestFeeDAOImpl() {
        super();
    }

    /**
     * This method is responsible for creating contests
     * 
     * @Param contestFees - a list of ProjectContestFee instances
     * 
     * @throws ContestFeePersistenceException
     *             if there is any exception.
     */
    public void save(List<ProjectContestFee> contestFees) throws ContestFeePersistenceException {
    	Session session = getSession();
        try {
            for (ProjectContestFee contestFeeDetails : contestFees) {
                contestFeeDetails.setId(0);	        
                session.save(contestFeeDetails);	        
            }
            session.flush();
        } finally {
            session.close();
        }
    }


    /**
     * This method is responsible for updating contest fee given instance of ProjectContestFee and project id.
     * 
     * 
     * @Param contestFeeDetails - denotes instance of ProjectContestFee. ProjectContestFee.fee should be positive double
     *        and contestType should be valid contest type
     * @throws ContestFeePersistenceException
     *             if there is any exception.
     */
    public void update(ProjectContestFee contestFeeDetails) throws ContestFeePersistenceException {
        // checkContestFeeDetails(contestFeeDetails); //TODO some values in database is invalid. skip this test now.
        Session session = getSession();
        try {
            session.save(contestFeeDetails);
        } finally {
            session.close();
        }
    }
    
    /**
     * Clean up the contest fees for the project.
     * 
     * @param projectId the project id.
     */
    public void cleanUpContestFees(long projectId) {
    	final String QUERY = "delete ProjectContestFee c where c.projectId = :projectId";
    	Session session = getSession();
        try {
            session.createQuery(QUERY).setLong("projectId", projectId).executeUpdate();
        } finally {
            session.close();
        }
    }

    /**
     * This method is responsible for deleting contest fee given contest type and project identifier.
     * 
     * @Param contestFeeId - denotes contest fee id.
     */
    public void delete(long contestFeeId) {
        String hql = "delete ProjectContestFee c where c.projectContestFeeId = :contestFeeId";
        Session session = getSession();
        try {
            session.createQuery(hql).setLong("contestFeeId", contestFeeId).executeUpdate();
        } finally {
            session.close();
        }
    }

    /**
     * This method is responsible for retrieving billing account given billing account id.
     * 
     * @Param projectId:long - denotes billing account id. should be positive
     * @Return instance of BillingAccount
     * @throws ContestFeePersistenceException
     *             if there is any exception.
     */
    public BillingAccount getBillingAccount(long projectId) {
        Session session = getSession();
        BillingAccount result;
        try {
            String sql = "SELECT unique p.project_id, p.company_id, p.name, p.description, p.active, p.start_date, " +
                         "c.name as clientName, f.contest_type_id, f.contest_fee, f.project_contest_fee_id, f.is_studio, " +
                         "f.is_deleted, cat.display_order " 
                    + " from Project p " 
                    + " left join Project_Contest_Fee f on f.project_id = p.project_id "
                    + " left join project_category_lu cat on f.contest_type_id = cat.project_category_id, "
                    + " client_project cp , client c "
                    + " where p.project_id=:projectId  "
                    + " and cp.client_id = c.client_id "
                    + " and cp.project_id = p.project_id "
                    + " order by cat.display_order";
            SQLQuery query = session.createSQLQuery(sql);
            query.setLong("projectId", projectId);
            List<BillingAccount> list = convertBillingAccounts(query.list());

            result = null;
            if (!list.isEmpty()) {
                result = list.get(0);
            }
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * This method is responsible for retrieving all billing accounts given billing account id.
     * 
     * @Param pageNumber - denotes the page number. should be positive and > 0.
     * @Param pageSize - denotes no of items to display per page.should be positive (>0)
     * @Param sortColumn - denotes the name of the one of the member of BillingAccount entity which will be used for
     *        sorting resultant billing entities
     * @Param sortingOrder- denotes the SortOrder string. It can be ASC or DSC value.
     * @return returns instance of search result<BillingAccount>
     */
    public List<BillingAccount> search(int pageSize, int pageNumber, String sortColumn, String sortOrder) {
        // fetch project id for pagination
        String sql1 = "SELECT project_id from project order by " + sortColumn + " " + sortOrder;
        String sql2 = "SELECT unique p.project_id, p.company_id, p.name, p.description, p.active, " +
                      "p.start_date, c.name as clientName, "
                    + " f.contest_type_id, f.contest_fee, f.project_contest_fee_id, f.is_studio, f.is_deleted "
                    + " from Project p left join Project_Contest_Fee f on f.project_id = p.project_id,  " 
                     + "  client_project cp , client c "
                    + " where cp.client_id = c.client_id "
                    + " and cp.project_id = p.project_id ";
        return search(sql1, sql2, pageSize, pageNumber);
    }

    /**
     * <p>Gets the list of active billing accounts either having or not having contest fees set.</p>
     * 
     * @param contestFeesSet <code>true</code> if accounts having contest fees set are to be returned only; 
     *        <code>false</code> if accounts having no contest fees set are to be returned only.
     * @param pageNumber - denotes the page number. should be positive and > 0.
     * @param pageSize - denotes no of items to display per page.should be positive (>0)
     * @param sortColumn - denotes the name of the one of the member of BillingAccount entity which will be used for
     *        sorting resultant billing entities
     * @param sortOrder - denotes the SortOrder string. It can be ASC or DSC value.
     * @return returns instance of search result<BillingAccount>
     * @since 1.2
     */
    @SuppressWarnings("unchecked")
    public List<BillingAccount> search(boolean contestFeesSet, int pageSize, int pageNumber, String sortColumn, 
                                       String sortOrder) {
        // fetch project id for pagination
        String sql1 = "SELECT p.project_id FROM project p " +
                      "WHERE p.active = 1 " +
                      "AND " + (contestFeesSet ? "" : "NOT") + 
                      "      EXISTS (SELECT 1 " +
                      "              FROM project_contest_fee f " +
                      "              WHERE f.project_id = p.project_id) " +
                      "ORDER BY " + sortColumn + " "
                        + sortOrder;
        
        String sql2;
        if (contestFeesSet) {
            sql2 = "SELECT UNIQUE p.project_id, p.company_id, p.name, p.description, p.active, p.start_date, " +
                         "              c.name as clientName,  f.contest_type_id, f.contest_fee, " +
                         "              f.project_contest_fee_id, f.is_studio, f.is_deleted " +
                         "FROM project p " +
                         "LEFT JOIN project_contest_fee f ON f.project_id = p.project_id, " +
                         "client_project cp, " +
                         "client c " +
                         "WHERE p.active = 1 " +
                         "AND EXISTS (SELECT 1 " +
                         "              FROM project_contest_fee f " +
                         "              WHERE f.project_id = p.project_id) " +
                         "AND cp.client_id = c.client_id " +
                         "AND cp.project_id = p.project_id";
        } else {
            sql2 = "SELECT UNIQUE p.project_id, p.company_id, p.name, p.description, p.active, p.start_date, " +
                         "              c.name as clientName " +
                         "FROM project p, " +
                         "client_project cp, " +
                         "client c " +
                         "WHERE p.active = 1 " +
                         "AND NOT EXISTS (SELECT 1 " +
                         "                FROM project_contest_fee f " +
                         "                WHERE f.project_id = p.project_id) " +
                         "AND cp.client_id = c.client_id " +
                         "AND cp.project_id = p.project_id";
        }
        
        return search(sql1, sql2, pageSize, pageNumber);
    }

    /**
     * <p>Searches for billing projects.</p>
     * 
     * @param projectsForPageSQL a <code>String</code> providing the SQL statement for selecting the IDs for projects
     *        matching the specified page size and number.
     * @param projectDataSQL a <code>String</code> providing the SQL statement for selecting projects data.
     * @param pageSize an <code>int</code> providing the page size.
     * @param pageNumber an <code>int</code> providing the number of page.
     * @return a list of billing accounts.
     * @since 1.2
     */
    private List<BillingAccount> search(String projectsForPageSQL, String projectDataSQL, int pageSize, int pageNumber) {
        // fetch project id for pagination
        Session session = getSession();
        List<BillingAccount> result;
        try {
            List<Integer> fetchId = null;
            if (pageSize != Integer.MAX_VALUE) {
                SQLQuery query = session.createSQLQuery(projectsForPageSQL);
                query.setFirstResult(pageSize * (pageNumber - 1));
                query.setMaxResults(pageSize);
                fetchId = query.list();
            }

            StringBuilder sql = new StringBuilder(projectDataSQL);
            if (fetchId != null && !fetchId.isEmpty()) {
                    boolean isFirst = true;
                    for (Integer i : fetchId) {
                        if (isFirst) {
                            sql.append(" WHERE p.project_id IN (");
                            isFirst = false;
                        } else {
                            sql.append(" , ");
                        }
                        sql.append(i);
                    }
                    sql.append(")");
                }

            SQLQuery query = session.createSQLQuery(sql.toString());
            result = convertBillingAccounts(query.list());
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * Converts search result to BillingAccount entities.
     * 
     * @param list
     *            - the search result
     * @return created BillingAccount entities.
     */
    private List<BillingAccount> convertBillingAccounts(List<Object[]> list) {
        List<BillingAccount> result = new ArrayList<BillingAccount>();
        Map<String, BillingAccount> existingEntities = new HashMap<String, BillingAccount>();
        for (Object[] objects : list) {
            BillingAccount account;
            if (existingEntities.keySet().contains(objects[0].toString())) {
                account = existingEntities.get(objects[0].toString());
            } else {
                account = new BillingAccount();
                account.setProjectId(Long.parseLong(objects[0].toString()));
                account.setCompanyId(Long.parseLong(objects[1].toString()));
                account.setName((String) objects[2]);
                account.setDescription((String) objects[3]);
                account.setActive(Boolean.parseBoolean(objects[4].toString()));
                account.setStartDate((Timestamp) objects[5]);
                account.setClientName(objects[6].toString());
                existingEntities.put(objects[0].toString(), account);
                result.add(account);
            }
            if (objects.length > 7 && objects[7] != null) {
                ProjectContestFee fee = new ProjectContestFee();
                fee.setContestType(Long.parseLong(objects[7].toString()));
                fee.setContestFee((((BigDecimal) objects[8]).doubleValue()));
                fee.setId(Long.parseLong(objects[9].toString()));
                fee.setProjectId(Long.parseLong(objects[0].toString()));
                fee.setStudio(Integer.parseInt(objects[10].toString()) == 0 ? false : true);
                if (objects[11] != null) {
                    fee.setDeleted(Integer.parseInt(objects[11].toString()) == 0 ? false : true);
                } else {
                    fee.setDeleted(false);
                }
                List<ProjectContestFee> fees = account.getContestFees();
                if (fees == null) {
                    fees = new ArrayList<ProjectContestFee>();
                    account.setContestFees(fees);
                }
                fees.add(fee);
            }
        }
        return result;
    }

    /**
     * responsible for retrieving contest fee given contest typeId and project id.
     * 
     * @param contestTypeId
     *            - denotes the contest type identifier
     * @param projectId
     *            - denotes the project identifier
     * @return found contest fee value.
     */
    public double get(long contestTypeId, long projectId) {
        if (contestTypeId <= 0) {
            throw new IllegalArgumentException("The given contest type id should be positive.");
        }

        if (projectId <= 0) {
            throw new IllegalArgumentException("The given project id should be positive.");
        }
        Session session = getSession();
        double result;
        try {
            SQLQuery query = session.createSQLQuery(
                    "SELECT contest_fee from Project_Contest_Fee WHERE project_id = :projectId "
                            + "AND contest_type_id=:contestTypeId");
            query.setLong("projectId", projectId).setLong("contestTypeId", contestTypeId);
            List list = query.list();
            result = 0;
            if (list != null && !list.isEmpty()) {
                result = Double.parseDouble(list.get(0).toString());
            }
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * Get total BillingAccount record number.
     * 
     * @return total BillingAccount record number.
     */
    public int getBillingAccountSize() {
        Session session = getSession();
        int result;
        try {
            SQLQuery query = session.createSQLQuery("SELECT count(project_id) from project");

            List list = query.list();
            result = ((BigDecimal) list.get(0)).intValue();
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * <p>Gets the total number of billing accounts matching the specified criteria.</p>
     * 
     * @param active <code>true</code> if active billing accounts are to be counted; <code>false</code> if inactive.
     * @param contestFeesSet <code>true</code> if billing accounts with contest fees set are to be counted;
     *        <code>false</code> otherwise.
     * @return an <code>int</code> providing the total number of billing accounts.
     * @since 1.2
     */
    public int getBillingAccountSize(boolean active, boolean contestFeesSet) {
        Session session = getSession();
        int result;
        try {
            SQLQuery query = session.createSQLQuery("SELECT COUNT(project_id) " +
                                                    "FROM project p " +
                                                    "WHERE p.active = :active " +
                                                    "AND " + (contestFeesSet ? "" : "NOT")  + 
                                                    " EXISTS (SELECT 1 " +
                                                    "         FROM project_contest_fee f " +
                                                    "         WHERE f.project_id = p.project_id)");
            query.setInteger("active", active ? 1 : 0);

            List list = query.list();
            result = ((BigDecimal) list.get(0)).intValue();
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * contract responsible for persisting audit record given instance of FeeAuditRecord.
     * 
     * @Param record - instance of FeeAuditRecord. should not be null.
     * @Return long - audit entity identifier
     */
    public long audit(FeeAuditRecord record) {
        if (record == null) {
            throw new IllegalArgumentException("The given instance should not be null.");
        }
        Session session = getSession();
        try {
            session.save(record);
        } finally {
            session.close();
        }
        return record.getId();
    }

    /**
     * gets the logger field
     * 
     * @return the logger field value.
     */
    public Log getLogger() {
        return logger;
    }

    /**
     * Sets the corresponding member field
     * 
     * 
     * @param loggerName
     *            - the given logger name to set.
     */
    public void setLoggerName(String loggerName) {
        if (loggerName == null) {
            this.logger = null;
        } else {
            this.logger = LogManager.getLog(loggerName);
        }
    }

    /**
     * Check the parameters.
     * 
     * @throw ContestFeeConfigurationException if this.logger or this.session is null.
     */
    public void postConstruct() {
        if (logger == null || sessionFactory == null) {
            throw new ContestFeeConfigurationException("This class requires logger instance and session instance.");
        }
    }

    /**
     * Returns the Session field value.
     * 
     * @return created Session field value.
     */
    private Session getSession() {
        return sessionFactory.openSession();
    }

    /**
     * Returns the sessionFactory field value.
     * 
     * @return sessionFactory field value.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Sets the given value to sessionFactory field.
     * 
     * @param sessionFactory
     *            - the given value to set.
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
