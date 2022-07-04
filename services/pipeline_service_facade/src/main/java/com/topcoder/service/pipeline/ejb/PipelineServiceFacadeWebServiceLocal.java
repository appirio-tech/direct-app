/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.ejb;

import javax.ejb.Local;

import com.topcoder.service.pipeline.PipelineServiceFacadeWebService;

/**
 * <p>
 * This is the remote interface for EJB 3.0 bean. It is a marker interface which simply extends the
 * <code>{@link PipelineServiceFacadeWebService}</code> contract. It allows the
 * <code>{@link PipelineServiceFacadeWebService}</code> to also be accessed as a local bean.
 * </p>
 * <p>
 * <b>Thread Safety</b>: Implementations must be thread safe.
 * </p>
 *
 * @author TCASSEMBLER
 * @since Cockpit Security Facade V1.0
 * @version 1.0
 */
@Local
public interface PipelineServiceFacadeWebServiceLocal extends PipelineServiceFacadeWebService {

}
