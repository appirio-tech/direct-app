/*
 * Copyright (C) 2006-2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.topcoder.management.scorecard.PersistenceException;
import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.NamedScorecardStructure;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.Section;
import com.topcoder.management.scorecard.persistence.logging.LogMessage;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This class contains operations to create and update group instances into the Informix database. It is package
 * level because it is used only in InformixScorecardPersistence class to persist group information. Connection to
 * the database is passed to this class during initialization. Thread Safety: The implementation is not thread safe
 * in that two threads running the same method will use the same statement and could overwrite each other's work.
 * 
 * <p>
 * Changes in v1.0.2 (Cockpit Spec Review Backend Service Update v1.0):
 * - LogManager is used instead of LogFactory.
 * </p>
 *
 * @author tuenm, kr00tki, pulky
 * @version 1.0.2
 */
class InformixGroupPersistence {

    /** Logger instance using the class name as category */
    private static final Log logger = LogManager.getLog(InformixGroupPersistence.class.getName());
    
    /**
     * Selects the groups using the parent id.
     */
    private static final String SELECT_SCORECARD_GROUP_BY_PARENT_ID = "SELECT scorecard_group_id, "
            + "name, weight FROM scorecard_group WHERE scorecard_id = ? ORDER BY sort";

    /**
     * Selects the group be its id.
     */
    private static final String SELECT_SCORECARD_GROUP_BY_ID = "SELECT scorecard_group_id, name, "
            + "weight FROM scorecard_group WHERE scorecard_group_id = ?";

    /**
     * Deletes the set of groups.
     */
    private static final String DELETE_SCORECARD_GROUPS = "DELETE FROM scorecard_group WHERE scorecard_group_id IN ";

    /**
     * Selects the section ids for given set of groups.
     */
    private static final String SELECT_SCORECARD_SECTION_ID = "SELECT scorecard_section_id FROM scorecard_section "
        + "WHERE scorecard_group_id IN ";

    /**
     * Updates the group table.
     */
    private static final String UPDATE_SCORECARD_GROUP = "UPDATE scorecard_group SET scorecard_id = ?, "
            + "name = ?, weight = ?, sort = ?, modify_user = ?, modify_date = ? WHERE scorecard_group_id = ?";

    /**
     * Inserts the new group the database.
     */
    private static final String INSERT_SCORECARD_GROUP = "INSERT INTO scorecard_group (scorecard_group_id, "
            + "scorecard_id, name, weight, sort, create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * The connection used to connection to the database. It is initialized in the constructor from the passing
     * parameter.
     *
     */
    private final Connection connection;

    /**
     * Create a new instance of InformixGroupPersistence. The passing connection parameter will be used to connect
     * to the database. Null value is not allowed. Implementation notes: - Set the member connection variable.
     *
     *
     * @param connection The connection to the database.
     * @throws IllegalArgumentException if the input is null.
     */
    public InformixGroupPersistence(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("connection cannot be null.");
        }
        this.connection = connection;
    }

    /**
     * Creates the group in the database using the given group instance. The group instance can include sub items
     * such as sections and questions. Those sub items will be persisted as well. The operator parameter is used as
     * the creation/modification user of the group and its subitems. The creation date and modification date will
     * be the current date time when the group is created.
     *
     * @param group The group instance to be created in the database.
     * @param order the sort order of this group.
     * @param operator The creation user of this group.
     * @param parentId The id of the scorecard that contains this.
     * @throws IllegalArgumentException if any input is null or the operator is empty string.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public void createGroup(Group group, int order, String operator, long parentId) throws PersistenceException {
        if (group == null) {
            throw new IllegalArgumentException("group cannot be null.");
        }
        if (operator == null) {
            throw new IllegalArgumentException("operator cannot be null.");
        }

        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException("operator cannot be empty String.");
        }

        logger.log(Level.INFO, new LogMessage("Group", null, operator,
        		"create new Group with order:" + order + " and parentId:" + parentId));
        // get next id
        long groupId = DBUtils.nextId(IdGeneratorUtility.getGroupIdGenerator());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        // create section persistence
        InformixSectionPersistence sectionPersistence = new InformixSectionPersistence(connection);
        PreparedStatement pstmt = null;

        logger.log(Level.INFO, "insert record into scorecard_group with group_id:" + groupId);
        try {
        	// create statement
            pstmt = connection.prepareStatement(INSERT_SCORECARD_GROUP);

            // set the variables
            pstmt.setLong(1, groupId);
            pstmt.setLong(2, parentId);
            pstmt.setString(3, group.getName());
            pstmt.setFloat(4, group.getWeight());
            pstmt.setInt(5, order);

            pstmt.setString(6, operator);
            pstmt.setTimestamp(7, time);
            pstmt.setString(8, operator);
            pstmt.setTimestamp(9, time);

            // create group and create the sections
            pstmt.executeUpdate();
            sectionPersistence.createSections(group.getAllSections(), operator, groupId);
        } catch (SQLException ex) {
        	logger.log(Level.ERROR, new LogMessage("Group", new Long(groupId), operator, "Fail to create Group.", ex));
            throw new PersistenceException("Error occur while creating the scorecard group.", ex);
        } finally {
            DBUtils.close(pstmt);
        }

        // set the group id.
        group.setId(groupId);
    }

    /**
     * This method creates the groups instances in the datastore. It has the same behavior as
     * {@link #createGroup(Group, int, String, long)} except it executes insert at once.
     *
     * @param groups the groups to be created.
     * @param operator the creation operator.
     * @param parentId the id of the parent scorecard.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    void createGroups(Group[] groups, String operator, long parentId) throws PersistenceException {
    	logger.log(Level.INFO,
    			new LogMessage("Group", null, operator, "create new Groups with parentId:" + parentId));
    	
        // generate the ids
        long[] groupIds = DBUtils.generateIdsArray(groups.length, IdGeneratorUtility.getGroupIdGenerator());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        // create sections persistence
        InformixSectionPersistence sectionPersistence = new InformixSectionPersistence(connection);
        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(INSERT_SCORECARD_GROUP);
            // set the id of the parent
            pstmt.setLong(2, parentId);

            // for each group - set the variables
            for (int i = 0; i < groups.length; i++) {
                pstmt.setLong(1, groupIds[i]);

                pstmt.setString(3, groups[i].getName());
                pstmt.setFloat(4, groups[i].getWeight());
                pstmt.setInt(5, i);

                pstmt.setString(6, operator);
                pstmt.setTimestamp(7, time);
                pstmt.setString(8, operator);
                pstmt.setTimestamp(9, time);

                // execute the update and creates sections of this group
                pstmt.executeUpdate();
                
                logger.log(Level.INFO, "insert record into scorecard_group table with groupId:" + groupIds[i]);
                sectionPersistence.createSections(groups[i].getAllSections(), operator, groupIds[i]);
            }
        } catch (SQLException ex) {
        	logger.log(Level.INFO, new LogMessage("Group", null, operator,
        			"Failed to create new Groups with parentId:" + parentId, ex));
            throw new PersistenceException("Error occur while creating the scorecard group.", ex);
        } finally {
            DBUtils.close(pstmt);
        }

        // set ids to groups
        for (int i = 0; i < groups.length; i++) {
            groups[i].setId(groupIds[i]);
        }
    }

    /**
     * Update the given group instance into the database. The group instance can include sub items such as sections
     * and questions. Those sub items will be updated as well. If sub items are removed from the group, they will
     * be deleted from the persistence. Likewise, if new sub items are added, they will be created in the
     * persistence. The operator parameter is used as the modification user of the group and its subitems. The
     * modification date will be the current date time when the group is updated.
     *
     * @param group The group instance to be updated into the database.
     * @param order the position of the group.
     * @param operator The modification user of this group.
     * @param parentId The id of the scorecard that contains this.
     * @param oldScorecard The scorecard instance before update. It is used to find out remeved items.
     * @param deletedSectionIds This is an output parameter. An empty array is expected to be passed in. Deleted
     *        section ids will be saved into this list.
     * @param deletedQuestionIds This is an output parameter. An empty array is expected to be passed in. Deleted
     *        question ids will be saved into this list. Delete question ids is collected from updateSection()
     *        call.
     * @throws IllegalArgumentException if any input is null or the operator is empty string.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public void updateGroup(Group group, int order, String operator, long parentId, Scorecard oldScorecard,
        List deletedSectionIds, List deletedQuestionIds) throws PersistenceException {
        if (group == null) {
            throw new IllegalArgumentException("group cannot be null.");
        }

        if (operator == null) {
            throw new IllegalArgumentException("operator cannot be null.");
        }

        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException("operator cannot be empty String.");
        }

        if (oldScorecard == null) {
            throw new IllegalArgumentException("oldScorecard cannot be null.");
        }

        if (deletedSectionIds == null) {
            throw new IllegalArgumentException("deletedSectionIds cannot be null.");
        }

        if (deletedQuestionIds == null) {
            throw new IllegalArgumentException("deletedQuestionIds cannot be null.");
        }

        logger.log(Level.INFO, new LogMessage("Group", new Long(group.getId()), operator,
        		"create new Group with order:" + order + " ,parentId:" + parentId
        		+ ", oldScorecard:" + oldScorecard.getId()));
        
        Set oldSectionIds = getSectionsIds(group, oldScorecard);
        // mark all old section as 'to delete'
        deletedSectionIds.addAll(oldSectionIds);

        // get the section and create its persistence
        Section[] sections = group.getAllSections();
        InformixSectionPersistence sectionPersistence = new InformixSectionPersistence(connection);

        // for each new section
        for (int i = 0; i < sections.length; i++) {
            Long longId = new Long(sections[i].getId());
            // if is new - create it
            if (sections[i].getId() == NamedScorecardStructure.SENTINEL_ID) {
                sectionPersistence.createSection(sections[i], i, operator, group.getId());
            } else if (oldSectionIds.contains(longId)) {
                // if is old - update it and removed from delete list
                sectionPersistence.updateSection(sections[i], i, operator, group.getId(), oldScorecard,
                        deletedQuestionIds);
                deletedSectionIds.remove(longId);
            }
        }
        // update the group in the database
        updateGroup(connection, group, operator, parentId, order);
    }

    /**
     * Updates the group (the scorecard_group table) in the database.
     *
     * @param conn the database connection to be used.
     * @param group the groupt to update.
     * @param operator the update operator name.
     * @param parentId the parent of this group (scorecard id).
     * @param order the order of this group in the database.
     * @throws PersistenceException if any database error occurs.
     */
    private static void updateGroup(Connection conn, Group group, String operator, long parentId, int order)
        throws PersistenceException {

    	logger.log(Level.INFO, "update scorecard_group with groupId:" + group.getId());
        PreparedStatement pstmt = null;
        try {
            // prepare the statement
            pstmt = conn.prepareStatement(UPDATE_SCORECARD_GROUP);

            // set the variables
            pstmt.setLong(1, parentId);
            pstmt.setString(2, group.getName());
            pstmt.setFloat(3, group.getWeight());
            pstmt.setInt(4, order);

            // set the modification user and time
            Timestamp time = new Timestamp(System.currentTimeMillis());
            pstmt.setString(5, operator);
            pstmt.setTimestamp(6, time);
            pstmt.setLong(7, group.getId());

            if (pstmt.executeUpdate() != 1) {
            	logger.log(Level.ERROR, "No group with id = " + group.getId());
                throw new PersistenceException("No group with id = " + group.getId());
            }
        } catch (SQLException ex) {
        	logger.log(Level.ERROR, new LogMessage("Group", new Long(group.getId()), operator,
        			"Error occurs while updating the group.", ex));
            throw new PersistenceException("Error occurs while updating the group.", ex);
        } finally {
            DBUtils.close(pstmt);
        }

    }

    /**
     * Gets the ids of all sections for group from he scorecard.
     *
     * @param group the group for which the sections ids will be collected.
     * @param scorecard the source scorecard.
     * @return the set of sections ids for group.
     */
    private static Set getSectionsIds(Group group, Scorecard scorecard) {
        Set ids = new HashSet();
        // get all groups
        Group[] oldGroups = scorecard.getAllGroups();
        for (int i = 0; i < oldGroups.length; i++) {
            // find the one of given id
            if (oldGroups[i].getId() == group.getId()) {
                // get all sections and add the ids to set
                Section[] sections = oldGroups[i].getAllSections();
                for (int j = 0; j < sections.length; j++) {
                    ids.add(new Long(sections[j].getId()));
                }
                break;
            }
        }

        return ids;
    }

    /**
     * Remove the groups with the given array of ids from the persistence. Group deletion is required when user
     * udpate a scorecard and remove a group from its group list.
     *
     * @param ids The id of the group to remove.
     * @throws IllegalArgumentException if the ids is less than or equal to zero. Or the input array is null or
     *         empty.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public void deleteGroups(long[] ids) throws PersistenceException {
        DBUtils.checkIdsArray(ids, "ids");

        logger.log(Level.INFO, new LogMessage("Group", null, null,
        		"Delete Groups with ids:" + InformixPersistenceHelper.generateIdString(ids)));
        
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        try {
            // selects the sections ids for given groups
            pstmt = connection.prepareStatement(SELECT_SCORECARD_SECTION_ID
                    + DBUtils.createQuestionMarks(ids.length));
            logger.log(Level.INFO,
            		"delete record from scorecard_groups with ids:" + InformixPersistenceHelper.generateIdString(ids));
            // deletes the given groups
            pstmt2 = connection
                    .prepareStatement(DELETE_SCORECARD_GROUPS + DBUtils.createQuestionMarks(ids.length));

            // set the ids for both statements
            for (int i = 0; i < ids.length; i++) {
                pstmt.setLong(i + 1, ids[i]);
                pstmt2.setLong(i + 1, ids[i]);
            }

            // get all the sections to be removed
            rs = pstmt.executeQuery();
            List idsToDelete = new ArrayList();
            while (rs.next()) {
                idsToDelete.add(new Long(rs.getLong(1)));
            }

            // delete the sections
            if (idsToDelete.size() > 0) {
                InformixSectionPersistence sectionPersistence = new InformixSectionPersistence(connection);
                sectionPersistence.deleteSections(DBUtils.listToArray(idsToDelete));
            }

            // delete the groups
            pstmt2.executeUpdate();
        } catch (SQLException ex) {
        	logger.log(Level.ERROR, new LogMessage("Group", null, null,
            		"Failed to Delete Groups with ids:" + InformixPersistenceHelper.generateIdString(ids), ex));
            throw new PersistenceException("Error occurs while deleting the questions.", ex);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
            DBUtils.close(pstmt2);
        }
    }

    /**
     * Retrieves the group instance from the persistence given its id. The group instance is always retrieved with
     * its sub items.
     *
     * @return The group instance or <code>null</code> if group not found.
     * @param id The id of the group to be retrieved.
     * @throws IllegalArgumentException if the input id is less than or equal to zero.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Group getGroup(long id) throws PersistenceException {
        if (id <= 0) {
            throw new IllegalArgumentException("The id must be positive. Id: " + id);
        }
        logger.log(Level.INFO, new LogMessage("Group", new Long(id), null, "retrieve group"));
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // create the statement and set the id
            pstmt = connection.prepareStatement(SELECT_SCORECARD_GROUP_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            // if the group exists - create it
            if (rs.next()) {
                InformixSectionPersistence sectionPersistence = new InformixSectionPersistence(connection);
                Group group = populateGroup(rs);
                group.addSections(sectionPersistence.getSections(group.getId()));
                return group;
            }
        } catch (SQLException ex) {
        	logger.log(Level.ERROR, new LogMessage("Group", new Long(id), null, "Failed to retrieve group", ex));
            throw new PersistenceException("Error occurs while retrieving the group.", ex);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
        }
        // return null if group not found.
        return null;
    }

    /**
     * Retrieves the group instances from the persistence with the given parent id. The group instance is always
     * retrieved with its sub items.
     *
     * @param parentId the id of the paren scorecard.
     * @return the list of groups for the given parent.
     * @throws PersistenceException if database error occur.
     */
    Group[] getGroups(long parentId) throws PersistenceException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // create the statement
            pstmt = connection.prepareStatement(SELECT_SCORECARD_GROUP_BY_PARENT_ID);
            pstmt.setLong(1, parentId);

            // get all groups
            rs = pstmt.executeQuery();
            List result = new ArrayList();
            InformixSectionPersistence sectionPersistence = new InformixSectionPersistence(connection);
            while (rs.next()) {
                Group group = populateGroup(rs);
                // get the sections for the group
                group.addSections(sectionPersistence.getSections(group.getId()));
                result.add(group);
            }

            return (Group[]) result.toArray(new Group[result.size()]);
        } catch (SQLException ex) {
        	logger.log(Level.ERROR, new LogMessage("Group", null, null,
        			"Failed to retrieve group with parentId:" + parentId , ex));
            throw new PersistenceException("Error occurs while retrieving the group.", ex);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
        }
    }

    /**
     * Creates the Group instance using the data from the ResultSet.
     *
     * @param rs the source result set.
     * @return the Group instance.
     * @throws SQLException if any error with ResultSet occurs.
     */
    private Group populateGroup(ResultSet rs) throws SQLException {
        Group group = new Group();
        group.setId(rs.getLong("scorecard_group_id"));
        group.setName(rs.getString("name"));
        group.setWeight(rs.getFloat("weight"));

        return group;
    }

}
