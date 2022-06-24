package com.topcoder.catalog.entity.stresstests;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.util.idgenerator.ejb.IDGenerator;
import com.topcoder.util.idgenerator.ejb.IDGeneratorBean;
import com.topcoder.util.idgenerator.ejb.IDGeneratorHome;

public class StressTestHelper {
    /**
     * <p>
     * Represents the EntityManager.
     * </p>
     */
    private static EntityManager entityManager;

    /**
     * <p>
     * Represents the EntityTransaction.
     */
    private static EntityTransaction entityTransaction;

    /**
     * <p>
     * Getter to the entityManager.
     * </p>
     *
     * @return instance of current entityManager
     */
    public static EntityManager getEntityManager() throws Exception {
        if (entityManager == null) {
            MockContextFactory.setAsInitial();
            // create the initial context that will be used for binding EJBs
            Context context = new InitialContext();
            // Create an instance of the MockContainer
            MockContainer mockContainer = new MockContainer(context);
            // deploy id generator bean to the mock context
            IDGeneratorBean generatorBean = new IDGeneratorBean();
            SessionBeanDescriptor descriptor = new SessionBeanDescriptor("IDGeneratorBean/home",
                IDGeneratorHome.class, IDGenerator.class, generatorBean);
            mockContainer.deploy(descriptor);
            entityManager = Persistence.createEntityManagerFactory("catalog_manager").createEntityManager();
        }
        return entityManager;
    }

    /**
     * <p>
     * Getter to the entityTransaction.
     * </p>
     *
     * @return instance of current entityTransaction
     */
    public static EntityTransaction getEntityTransaction() throws Exception {
        if (entityTransaction == null) {
            entityTransaction = getEntityManager().getTransaction();
        }
        return entityTransaction;
    }

    /**
     * Retrieves the entity with the given key.
     *
     * @param <T>
     *            the type of the entity to retrieve
     * @param clazz
     *            the class of the entity to retrieve
     * @param key
     *            the key used to retrieve the entity
     * @return the retrieved entity with the given key, null if it does not
     *         exist
     */
    public static <T> T retrieveEntity(Class<T> clazz, Object key) throws Exception {
        EntityTransaction et = getEntityManager().getTransaction();
        et.begin();
        T entityR = getEntityManager().find(clazz, key);
        et.commit();
        return entityR;
    }

    /**
     * <p>
     * Runs the sql statements in the given file.
     * </p>
     *
     * @param file
     *            sql file to be executed.
     * @throws Exception
     *             when it occurs deeper
     */
    public static void runSQL(String file) throws Exception {
        boolean transaction = !getEntityTransaction().isActive();
        EntityManager em = entityManager;

        StringBuffer buf = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        while (true) {
            String s = in.readLine();
            if (s == null) {
                break;
            }
            buf.append(s);
        }
        in.close();
        String content = buf.toString();

        try {
            if (transaction) {
                entityTransaction.begin();
            }

            final String[] statements = content.split(";");
            for (String statement : statements) {
                if (statement.trim().startsWith("@")) {
                    runSQL(statement.trim().substring(1));
                } else if (!statement.trim().startsWith("--") && !statement.trim().equalsIgnoreCase("exit")) {
                    em.createNativeQuery(statement).executeUpdate();
                }
            }

            if (transaction) {
                entityTransaction.commit();
            }
        } catch (PersistenceException e) {
            if (transaction) {
                try {
                    entityTransaction.rollback();
                } catch (Exception e1) {
                    // ignore
                }
            }
            throw e;
        }
    }

}
