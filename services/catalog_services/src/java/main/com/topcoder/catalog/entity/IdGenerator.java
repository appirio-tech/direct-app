/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity;

import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.ejb.IDGenerator;
import com.topcoder.util.idgenerator.ejb.IDGeneratorHome;
import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Properties;

/**
 * <p>This class generates ids for the entities when they are being stored in persistence.</p>
 * For example, to assign this id generator to an entity, you need to specify this class as
 * <code>generator</code> (with corresponding <code>sequence_name</code>) it in the hibernate O/R mapping file:
 * <pre>
 *        &lt;hibernate-mapping package="com.topcoder.catalog.entity"&gt;
 *            &lt;class name="Phase" table="phase"&gt;
 *                &lt;id name="id" column="phase_id"&gt;
 *                    &lt;generator class="com.topcoder.catalog.entity.IdGenerator"&gt;
 *                        &lt;param name="sequence_name"&gt;PHASE_SEQ&lt;/param&gt;
 *                    &lt;/generator&gt;
 *                &lt;/id&gt;
 *                &lt;property name="description" column="description" access="field"/&gt;
 *            &lt;/class&gt;
 *        &lt;/hibernate-mapping&gt;
 *    </pre>
 * This class delegates creation of ids to the <code>IDGenerator</code> component.
 * <p/>
 * <p><strong>Thread safety: </strong></p> <p>This class is not thread safe but used
 * in thread-safe manner by <tt>Hibernate</tt>.</p>
 *
 * @author caru, Retunsky
 * @version 1.0
 */
public class IdGenerator implements Configurable, IdentifierGenerator {
    /**
     * <p>Represents the sequence name tag, so that it can be retrieved from params parameter
     * of {@link #configure} method.</p>
     */
    public static final String SEQUENCE_NAME = "sequence_name";
    /**
     * <p>This field represents the sequence name.</p>
     * <p>The initial value is <tt>null</tt>. Value is supposed to be assigned in {@link #configure} method.</p>
     */
    private String seqName;
    /**
     * <p>Represents the actual IdGenerator.</p>
     * <p>It's being initialized in the constructor with non-null value and not being changed.</p>
     * <p>It's involved in the {@link #generate} method.</p>
     */
    private final IDGenerator idGenerator;

    /**
     * <p>Default constructor. Initializes <code>idGenerator</code>.</p>
     *
     * @throws HibernateException if no <code>IDGeneratorHome</code> bound, or bound invalid object,
     *                            or error encountered while creating <code>IDGenerator</code>
     *                            if any communication problem occurred
     */
    public IdGenerator() {
        try {
            Context ctx = new InitialContext();
            IDGeneratorHome home = (IDGeneratorHome) ctx.lookup("IDGeneratorBean/home");
            idGenerator = home.create();
        } catch (NamingException e) {
            throw new HibernateException("Cannot look up ID Generator Home", e);
        } catch (RemoteException e) {
            throw new HibernateException("Error occurred communicating with remote object: " + e.getMessage(), e);
        } catch (CreateException e) {
            throw new HibernateException("Error occurred creating ID Generator: " + e.getMessage(), e);
        } catch (ClassCastException e) {
            throw new HibernateException("Invalid object is bound. Expected IDGeneratorHome. Original error: "
                + e.getMessage(), e);
        }
    }

    /**
     * <p>Configures the <code>IdGenerator</code>.</p>
     *
     * @param type       ignored
     * @param properties param values
     * @param dialect    ignored
     * @throws HibernateException if properties is null, or no sequence name found in the properties,
     *                            or object of invalid type is put to SEQUENCE_NAME key,
     *                            or an empty string provided as a sequence name
     */

    public void configure(Type type, Properties properties, Dialect dialect) {
        if (properties == null) {
            throw new HibernateException("Parameter 'properties' cannot be null.");
        }
        final Object value = properties.get(SEQUENCE_NAME);
        if (!(value instanceof String) || ((String) value).trim().length() == 0) {
            throw new HibernateException("There should be non-empty String value for [" + SEQUENCE_NAME
                + "] key in the properties.");
        }
        seqName = (String) value;
    }

    /**
     * <p>Generates the next id.</p>
     *
     * @param sessionImplementor ignored
     * @param object             ignored
     * @return next id
     * @throws HibernateException if no sequence name defined or any exception occurred on
     *                            underlying IDGenerator level, or communication problem encountered
     */

    public Serializable generate(SessionImplementor sessionImplementor, Object object) {
        if (seqName == null) {
            throw new HibernateException(
                "Sequence Name is not specified in configuration (should call configure() method first).");
        }
        try {
            return idGenerator.getNextID(seqName);
        } catch (IDGenerationException e) {
            throw new HibernateException("Error occurred during generating id: " + e.getMessage(), e);
        } catch (RemoteException e) {
            throw new HibernateException("Error occurred communicating with remote object: " + e.getMessage(), e);
        }
    }
}

