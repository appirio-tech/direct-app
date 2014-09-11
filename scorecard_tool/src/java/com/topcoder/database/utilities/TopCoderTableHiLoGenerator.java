/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.database.utilities;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.engine.TransactionHelper;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;
import org.hibernate.util.PropertiesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This class is the customized id generator which is used by the tool to generate the ids based the corresponding
 * sequence name in id_sequence table.
 * </p>
 *
 * @author FireIce, TCSASSEMBER
 * @version 1.1
 */
public class TopCoderTableHiLoGenerator extends TransactionHelper implements IdentifierGenerator, Configurable {
    /**
     * Represents whether is test environment.
     */
    public static boolean TEST_ENV = false;

	/**
	 * Represents the logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(TopCoderTableHiLoGenerator.class);
	
	/**
	 * Represents the parameter name for sequence name configuration.
	 */
	private static final String SEQ_NAME = "seq_name";
	
	/** 
	 * The next_block_start field name of table id_sequences. 
	 */
    private static final String NEXT_BLOCK_START = "next_block_start";

    /** 
     * The block_size field name of table id_sequences. 
     */
    private static final String BLOCK_SIZE = "block_size";

    /** 
     * The exausted field name of table id_sequences. 
     */
    private static final String EXHAUSTED = "exhausted";

    /** 
     * The select sql sentence used for retrieving data from table. 
     */
    private static final String SELECT_NEXT_BLOCK =
        "SELECT next_block_start, block_size, exhausted FROM id_sequences WHERE name = ? FOR UPDATE";

    /** 
     * The select sql sentence used for retrieving data from table in test environment. 
     */
    private static final String SELECT_NEXT_BLOCK_TEST =
        "SELECT next_block_start, block_size, exhausted FROM id_sequences WHERE name = ?";

    /** 
     * Update the next_block_start of the table. 
     */
    private static final String UPDATE_NEXT_BLOCK_START = "UPDATE id_sequences SET next_block_start = ? WHERE name = ?";

    /** 
     * The sql sentence to set the exausted to 1. 
     */
    private static final String UPDATE_EXHAUSTED = "UPDATE id_sequences SET exhausted = 1 WHERE name = ?";
    
    /** 
     * This is the next value that will be generated for this sequence. 
     */
    private long nextID;

    /** 
     * Indicate the ids left in the current block for the generate method. 
     */
    private int idsLeft = 0;
    
    /**
     * Represents the sequence name to generate the next id.
     */
	private String sequenceName;
	
	/**
	 * Configures the id generator.
	 * 
	 * @param type the id type.
	 * @param params the configured parameters.
	 * @param d the database dialect
	 * @throws MappingException if any mapping problem occurs.
	 */
	public void configure(Type type, Properties params, Dialect d)
			throws MappingException {
		sequenceName = PropertiesHelper.getString(SEQ_NAME, params, null);
	}
	
	/**
	 * Generates next id.
	 * 
	 * @param session the session implementor.
	 * @param obj the object
	 * @throws SQLException if any error occurs.
	 */
	public Serializable generate(SessionImplementor session, Object obj)
			throws HibernateException {
		if (idsLeft <= 0) {
            // if no ids left,
            // acquire a new block
            synchronized (TopCoderTableHiLoGenerator.class) {
            	doWorkInNewTransaction(session);
            }
        }
		
		--idsLeft;

        return nextID++;
	}

	/**
	 * The does the real id generation, the logic is similar as ID Generator component.
	 * @param conn the database connection.
	 * @param sql the sql string.
	 * @throws SQLException if any error occurs.
	 */
	protected Serializable doWorkInCurrentTransaction(Connection conn,
			String sql) throws SQLException {
		// access the database
        ResultSet rs = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateExaustedStmt = null;
        PreparedStatement updateStartStmt = null;

        try {
            selectStmt = conn.prepareStatement(TEST_ENV ? SELECT_NEXT_BLOCK_TEST : SELECT_NEXT_BLOCK);
            selectStmt.setString(1, sequenceName);
            rs = selectStmt.executeQuery();

            if (!rs.next()) {
                throw new SQLException("The specified IDName does not exist in the database.");
            }

            // if the ids are exausted yet, simply throw exception
            if (rs.getBoolean(EXHAUSTED)) {
                throw new SQLException("The ids of specified IDName are exausted yet.");
            }

            // otherwise, read the new block and update this id
            long myNextID = rs.getLong(NEXT_BLOCK_START);
            int blockSize = rs.getInt(BLOCK_SIZE);

            // if the ids left are not sufficient to make a full block,
            // throw exception
            if ((myNextID - 1) > (Long.MAX_VALUE - blockSize)) {
                throw new SQLException("The ids left are not sufficient to make a block.");
            }

            // From here, we need to consider the rollback problem while error occurs
            // if the ids are exausted, set the flag
            if ((myNextID - 1) >= (Long.MAX_VALUE - blockSize)) {
                updateExaustedStmt = conn.prepareStatement(UPDATE_EXHAUSTED);
                updateExaustedStmt.setString(1, sequenceName);
                updateExaustedStmt.executeUpdate();
            }

            long myMaxBlockID = (myNextID + blockSize) - 1;

            // update the next block start
            updateStartStmt = conn.prepareStatement(UPDATE_NEXT_BLOCK_START);
            updateStartStmt.setLong(1, myMaxBlockID + 1);
            updateStartStmt.setString(2, sequenceName);
            updateStartStmt.executeUpdate();
            
            // it is safe to assign all the value now
            idsLeft = blockSize;
            nextID = myNextID;
            
            return myNextID;
        } catch (SQLException e) {
			log.error("could not get next id", e);
			
			throw e;
        } finally {
        	if (updateStartStmt != null) {
        		try {
        			updateStartStmt.close();
        		} catch (SQLException e) {
        			// ignore
        		}
        	}
        	
        	if (updateExaustedStmt != null) {
        		try {
        			updateExaustedStmt.close();
        		} catch (SQLException e) {
        			// ignore
        		}
        	}
        	
        	if (rs != null) {
        		try {
        			rs.close();
        		} catch (SQLException e) {
        			// ignore
        		}
        	}
        	
        	if (selectStmt != null) {
        		try {
        			selectStmt.close();
        		} catch (SQLException e) {
        			// ignore
        		}
        	}
        }
	}
	
}
