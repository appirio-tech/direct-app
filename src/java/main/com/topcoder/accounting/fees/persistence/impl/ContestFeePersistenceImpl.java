/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.fees.persistence.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.topcoder.accounting.fees.entities.BillingAccount;
import com.topcoder.accounting.fees.entities.ContestFeeDetails;
import com.topcoder.accounting.fees.entities.ContestType;
import com.topcoder.accounting.fees.entities.FeeAuditRecord;
import com.topcoder.accounting.fees.persistence.ContestFeePersistence;
import com.topcoder.accounting.fees.services.ContestFeeConfigurationException;
import com.topcoder.accounting.fees.services.ContestFeePersistenceException;
import com.topcoder.direct.services.configs.ContestCostBillingLevel;
import com.topcoder.direct.services.configs.ContestFee;
import com.topcoder.direct.services.configs.StudioSubtypeContestFee;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.util.DBMS;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.CachedDataAccess;
import com.topcoder.web.common.cache.MaxAge;

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
 * @author winstips, TCSDEVELOPER
 * @version 1.1
 */
public class ContestFeePersistenceImpl implements ContestFeePersistence {
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
     * Represents the contest types map instance. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private Map<String, ContestType> contestTypes = null;

    /**
     * Default Constructor.
     */
    public ContestFeePersistenceImpl() {
        super();
    }

    /**
     * This method is responsible for creating contest fee given instance of ContestFeeDetails and project id.
     * 
     * @Param contestFeeDetails - denotes instance of ContestFeeDetails. ContestFeeDetails.fee should be positive double
     *        and contestType should be valid contest type
     * 
     * @Return Contest fee identifier
     * @throws ContestFeePersistenceException
     *             if there is any exception.
     */
    public long create(ContestFeeDetails contestFeeDetails) throws ContestFeePersistenceException {
        checkContestFeeDetails(contestFeeDetails);
        getSession().persist(contestFeeDetails);
        return contestFeeDetails.getProjectContestFeeId();
    }

    /**
     * Checks whether the given contestFeeDetails is valid.
     * 
     * @param contestFeeDetails
     *            - the given value to check.
     * @throws ContestFeePersistenceException
     *             - if there is any exception.
     * @throws IllegalArgumentException
     *             - if contestFeeDetails is null.
     */
    private void checkContestFeeDetails(ContestFeeDetails contestFeeDetails) throws ContestFeePersistenceException {
        if (contestFeeDetails == null) {
            throw new IllegalArgumentException("The given ContestFeeDetails instance should not be null.");
        }
        if (contestFeeDetails.getFee() <= 0) {
            throw new IllegalArgumentException("The given Fee of ContestFeeDetails should be positive.");
        }

        if (!getContesetTypes().keySet().contains(Long.toString(contestFeeDetails.getContestTypeId()))) {
            throw new IllegalArgumentException("The given contest type of ContestFeeDetails is invalid.");
        }
    }

    /**
     * Get all contest types in database.
     * 
     * @return all contest types.
     * @throws ContestFeePersistenceException
     *             if there is any exception.
     */
    public Map<String, ContestType> getContesetTypes() throws ContestFeePersistenceException {
        if (contestTypes == null) {
            CachedDataAccess dataAccess = new CachedDataAccess(MaxAge.QUARTER_HOUR, DBMS.TCS_OLTP_DATASOURCE_NAME);

            Request request = new Request();
            request.setContentHandle("project_categories_replatforming");
            contestTypes = new HashMap<String, ContestType>();
            try {
                ResultSetContainer container = dataAccess.getData(request)
                        .get("project_categories_replatforming");
                int recordNum = container.size();
                for (int i = 0; i < recordNum; i++) {
                    ContestType type = new ContestType();
                    type.setTypeId(container.getIntItem(i, "project_category_id"));
                    if (container.getIntItem(i, "project_type_id") == 3) {
                        type.setStudio(true);
                    } else {
                        type.setStudio(false);
                    }
                    type.setDescription(container.getStringItem(i, "name"));

                    contestTypes.put(Integer.toString(type.getTypeId()), type);
                }

            } catch (Exception e) {
                // ignore it
                contestTypes = null;
                throw new ContestFeePersistenceException(e.getMessage(), e);
            }
        }
        return contestTypes;
    }

    /**
     * This method is responsible for updating contest fee given instance of ContestFeeDetails and project id.
     * 
     * 
     * @Param contestFeeDetails - denotes instance of ContestFeeDetails. ContestFeeDetails.fee should be positive double
     *        and contestType should be valid contest type
     * @throws ContestFeePersistenceException
     *             if there is any exception.
     */
    public void update(ContestFeeDetails contestFeeDetails) throws ContestFeePersistenceException {
        // checkContestFeeDetails(contestFeeDetails); //TODO some values in database is invalid. skip this test now.

        Session session = getSession();
        session.getTransaction().begin();
        session.update(contestFeeDetails);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * This method is responsible for deleting contest fee given contest type and project identifier.
     * 
     * @Param contestFeeId - denotes contest fee id.
     */
    public void delete(long contestFeeId) {
        String hql = "delete ContestFeeDetails c where c.projectContestFeeId = :contestFeeId";
        getSession().createQuery(hql).setLong("contestFeeId", contestFeeId).executeUpdate();
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
        String sql = "SELECT unique p.project_id, p.company_id, p.name, p.description, p.active, p.start_date, c.name as clientName, "
                + " f.contest_type_id, f.contest_fee, f.project_contest_fee_id, f.is_studio, f.is_deleted " 
                + " from Project p " 
                + " left join Project_Contest_Fee f on f.project_id = p.project_id, "
                + " client_project cp , client c "
                + " where p.project_id=:projectId  "
				+ " and cp.client_id = c.client_id "
                + " and cp.project_id = p.project_id and is_studio = 0 "
				+ " order by f.contest_type_id";
        SQLQuery query = session.createSQLQuery(sql);
        query.setLong("projectId", projectId);
        List<BillingAccount> list = convertBillingAccounts(query.list());

        BillingAccount result = null;
        if (!list.isEmpty()) {
            result = list.get(0);
        }
        session.close();

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
        Session session = getSession();
        List<Integer> fetchId = null;
        if (pageSize != Integer.MAX_VALUE) {
            SQLQuery query = session.createSQLQuery("SELECT project_id from project order by " + sortColumn + " "
                    + sortOrder);
            query.setFirstResult(pageSize * (pageNumber - 1));
            query.setMaxResults(pageSize);
            fetchId = query.list();

        }
        String sql = "SELECT unique p.project_id, p.company_id, p.name, p.description, p.active, p.start_date, c.name as clientName, "
                + " f.contest_type_id, f.contest_fee, f.project_contest_fee_id, f.is_studio, f.is_deleted "
                + " from Project p left join Project_Contest_Fee f on f.project_id = p.project_id,  " 
                 + "  client_project cp , client c "
                + " where cp.client_id = c.client_id "
                + " and cp.project_id = p.project_id and is_studio = 0 ";
				
        if (fetchId != null && !fetchId.isEmpty()) {
            boolean isFirst = true;
            for (Integer i : fetchId) {
                if (isFirst) {
                    sql += " where p.project_id in (";
                    isFirst = false;
                } else {
                    sql += " , ";
                }
                sql += i;
            }
            sql += ")";
        }

        SQLQuery query = session.createSQLQuery(sql);
        List<BillingAccount> result = convertBillingAccounts(query.list());
        session.close();
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
            if (objects[7] != null) {
                ContestFeeDetails fee = new ContestFeeDetails();
                fee.setContestTypeId(Long.parseLong(objects[7].toString()));
                fee.setFee((((BigDecimal) objects[8]).doubleValue()));
                fee.setProjectContestFeeId(Long.parseLong(objects[9].toString()));
                fee.setProjectId(Long.parseLong(objects[0].toString()));
                fee.setIsStudio(Integer.parseInt(objects[10].toString()));
                fee.setIsDelete(Integer.parseInt(objects[11].toString()));
                List<ContestFeeDetails> fees = account.getContestFees();
                if (fees == null) {
                    fees = new ArrayList<ContestFeeDetails>();
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
        SQLQuery query = getSession().createSQLQuery(
                "SELECT contest_fee from Project_Contest_Fee WHERE project_id = :projectId "
                        + "AND contest_type_id=:contestTypeId");
        query.setLong("projectId", projectId).setLong("contestTypeId", contestTypeId);
        List list = query.list();
        double result = 0;
        if (list != null && !list.isEmpty()) {
            result = Double.parseDouble(list.get(0).toString());
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
        SQLQuery query = session.createSQLQuery("SELECT count(project_id) from project");

        List list = query.list();
        int result = ((BigDecimal) list.get(0)).intValue();
        session.close();
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
        getSession().save(record);
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
