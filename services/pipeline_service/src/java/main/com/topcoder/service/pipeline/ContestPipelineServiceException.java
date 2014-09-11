/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline;

/**
 * <p>
 * Basically it is an wrapper for the Exception from the this component.
 * </p>
 * <b>Thread Safety</b>: This class is thread safe as it has no state and the super class is thread safe.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class ContestPipelineServiceException extends Exception {

	/**
	 * serial version uid.
	 */
	private static final long serialVersionUID = -3122727730908111292L;
	/**
     * <p>
     * Constructs this fault with an error message.
     * </p>
     *
     * @param message
     *            The error message describing this fault. Possibly null/empty.
     */
	public ContestPipelineServiceException(String message) {
		super(message);
	}
	/**
     * <p>
     * Constructs this fault with an error message.
     * </p>
     *
     * @param message The error message describing this fault. Possibly null/empty.
     * @param cause the inner cause
     */
	public ContestPipelineServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
