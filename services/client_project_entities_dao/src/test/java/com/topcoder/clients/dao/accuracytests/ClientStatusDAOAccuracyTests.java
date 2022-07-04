/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.clients.dao.accuracytests;

import java.util.List;

import com.topcoder.clients.dao.ejb3.ClientStatusDAOBean;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;

/**
 * <p>
 * Tests the <code>{@link ClientStatusDAOBean}</code> for accuracy.
 * </p>
 * 
 * @author cyberjag
 * @version 1.0
 */
public class ClientStatusDAOAccuracyTests extends BaseTest<ClientStatusDAOBean, ClientStatus> {
    /**
     * Tests the <code>{@link ClientStatusDAOBean#ClientStatusDAOBean()}</code> for accuracy.
     */
    public void testClientStatusDAOBean() {
        assertNotNull("Failed to create the bean.", getTestBean());
    }

    /**
     * Tests the <code>{@link ClientStatusDAOBean#getClientsWithStatus(ClientStatus)}</code> for accuracy.
     * 
     * @throws Exception
     *             to junit
     */
    public void testGetClientsWithStatus() throws Exception {
        Client client = createClient(101);
        List<Client> list = getTestBean().getClientsWithStatus(client.getClientStatus());
        assertEquals("Failed to get clients with status", 1, list.size());
        assertEntity(list.get(0), client);
    }

    /**
     * Creates the entity specific to this test.
     */
    @Override
    protected void createEntity() {
        ClientStatus status = createClientStatus(51);
        setEntity(status);
    }

    /**
     * Creates the EJB specific to this test.
     */
    @Override
    protected void createTestBean() {
        ClientStatusDAOBean bean = new ClientStatusDAOBean();
        bean.setEntityManager(getEntityManager());
        setTestBean(bean);
    }

}
