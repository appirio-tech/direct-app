/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.comment.specification.ejb;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.review.comment.specification.SpecReviewCommentServiceException;
import com.topcoder.service.review.comment.specification.UserComment;
import com.topcoder.service.review.comment.specification.SpecReviewComment;

import com.topcoder.service.facade.contest.ejb.ContestServiceFacadeBean;
import com.topcoder.service.studio.submission.SubmissionManagerBean;

import com.topcoder.security.TCSubject;

import com.topcoder.shared.util.DBMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * <p>
 * The Unit Tests for the Specification Review Comment Service EJB.
 * </p>
 * 
 * @author Shorty,
 * @version 1.0
 */
public class SpecReviewCommentServiceBeanUnitTest extends TestCase {

	/**
	 * <p>
	 * Represents the ID of an existing project.
	 * </p>
	 * 
	 * <p>
	 * XXX: Please be sure to set this field to an existing project ID on your
	 * Cockpit/Direct system before testing.
	 * </p>
	 */
	private static final long PROJECT_ID = 30005650;

	/**
	 * <p>
	 * Represents the internal Amazon IP of the TC/OR VM.
	 * </p>
	 * 
	 * <p>
	 * XXX: Please be sure to set this field to your TC/OR VM IP before testing.
	 * </p>
	 */
	private static final String TC_OR_VM_AMAZON_IP = "ec2-50-16-32-118.compute-1.amazonaws.com";

	/**
	 * <p>
	 * Represents the comment body. It is used for testing.
	 * </p>
	 */
	private static final String TEST_MESSAGE = "Test message by Shorty!";

	/**
	 * <p>
	 * Represents the specification review comment service EJB.
	 * </p>
	 */
	private SpecReviewCommentServiceRemote specReviewCommentService;

	/**
	 * <p>
	 * Represents the <code>TCSubject</code> object to test with.
	 * </p>
	 */
	private TCSubject tcSubject = null;

	/**
	 * <p>
	 * The setup method for preparing the necessary resource for testing.
	 * </p>
	 * 
	 * @throws Exception
	 *             pass any unexpected exception to JUnit.
	 */
	protected void setUp() throws Exception {

		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		// assume SpecReviewCommentServiceBean is deployed on local host
		p.put(Context.PROVIDER_URL, "jnp://localhost:1399");

		Context c = new InitialContext(p);

		this.specReviewCommentService = (SpecReviewCommentServiceRemote) c
				.lookup("SpecReviewCommentServiceBean/remote");

		// set up the 'tcSubject' with the user ID of user 'heffan'
		this.tcSubject = new TCSubject(132456);
	}

	/**
	 * <p>
	 * The tear down method for tearing down the resources for testing.
	 * </p>
	 * 
	 * <p>
	 * The rows in used tables <em>comment_message_xref</em> and
	 * <em>question_thread_xref</em> in jive database are deleted.
	 * </p>
	 * 
	 * @throws Exception
	 *             pass any unexpected exception to JUnit.
	 */
	protected void tearDown() throws Exception {

		// prepare SQL Delete clause
		String sqlDelCm = "delete from comment_message_xref";
		String sqlDelQt = "delete from question_thread_xref";

		// connect to forums DB and execute the SQL delete clauses
		// Connection forumsConn = DBMS.g
		// .getConnection(DBMS.FORUMS_DATASOURCE_NAME);
		Class.forName("com.informix.jdbc.IfxDriver");
		String url = "jdbc:informix-sqli://" + TC_OR_VM_AMAZON_IP + ":2021/jive:informixserver=informixoltp_tcp";
		Connection forumsConn = DriverManager.getConnection(url, "informix", "1nf0rm1x");
		forumsConn.setAutoCommit(true);
		Statement stmt = forumsConn.createStatement();
		stmt.executeUpdate(sqlDelCm);
		stmt.executeUpdate(sqlDelQt);

		// close resources
		if (forumsConn != null) {
			forumsConn.close();
		}
		if (stmt != null) {
			stmt.close();
		}
	}

	/**
	 * <p>
	 * The method return the test suite for this class.
	 * </p>
	 * 
	 * @return The test suite for this class.
	 */
	public static TestSuite suite() {

		return new TestSuite(SpecReviewCommentServiceBeanUnitTest.class);
	}

	/**
	 * <p>
	 * Accuracy unit test for
	 * <code>{@link SpecReviewCommentServiceBean#addSpecReviewComment(TCSubject, long, boolean, long, UserComment)}</code>
	 * method.
	 * </p>
	 * 
	 * <p>
	 * Tests if a specification review comment is properly added.
	 * </p>
	 * 
	 * @throws Exception
	 *             pass any unexpected exception to JUnit.
	 */
	public void testAddSpecReviewComment_Accuracy() throws Exception {

		UserComment comment = buildUserComment("1.2.3", TEST_MESSAGE);

		this.specReviewCommentService.addSpecReviewComment(this.tcSubject, PROJECT_ID, false, 5, comment);
		
		// we should land here
	}

	/**
	 * <p>
	 * Accuracy unit test for
	 * <code>{@link SpecReviewCommentServiceBean#addSpecReviewComment(TCSubject, long, boolean, long, UserComment)}</code>
	 * method.
	 * </p>
	 * 
	 * <p>
	 * Tests if two consecutive specification review comments are properly added.
	 * </p>
	 * 
	 * @throws Exception
	 *             pass any unexpected exception to JUnit.
	 */
	public void testAddSpecReviewComment_AccuracyTwoCommentsInserted() throws Exception {

		UserComment comment = buildUserComment("1.2.3", TEST_MESSAGE);

		this.specReviewCommentService.addSpecReviewComment(this.tcSubject, PROJECT_ID, false, 5, comment);
		this.specReviewCommentService.addSpecReviewComment(this.tcSubject, PROJECT_ID, false, 5, comment);
		
		// we should land here
	}

	/**
	 * <p>
	 * Failure unit test for
	 * <code>{@link SpecReviewCommentServiceBean#addSpecReviewComment(TCSubject, long, boolean, long, UserComment)}</code>
	 * method.
	 * </p>
	 * 
	 * <p>
	 * Tests if <code>IllegalArgumentException</code> is thrown if the
	 * <code>TCSubject</code> is null.
	 * </p>
	 * 
	 * @throws Exception
	 *             pass any unexpected exception to JUnit.
	 */
	public void testAddSpecReviewComment_FailureNullTcSubject() throws Exception {

		// build user comment
		UserComment comment = buildUserComment("1.2.4", TEST_MESSAGE);

		try {

			this.specReviewCommentService.addSpecReviewComment(null, PROJECT_ID, false, 3, comment);
			fail("IllegalArgumentException is expected when 'TCSubject' is null!");
		} catch (EJBException ejbe) {

			if (!(ejbe.getCause() instanceof IllegalArgumentException)) {

				fail("IllegalArgumentException is expected!");
			}
			// we should land here
		}
	}

	/**
	 * <p>
	 * Failure unit test for
	 * <code>{@link SpecReviewCommentServiceBean#addSpecReviewComment(TCSubject, long, boolean, long, UserComment)}</code>
	 * method.
	 * </p>
	 * 
	 * <p>
	 * Tests if <code>IllegalArgumentException</code> is thrown if the
	 * <code>UserComment</code> is null.
	 * </p>
	 * 
	 * @throws Exception
	 *             pass any unexpected exception to JUnit.
	 */
	public void testAddSpecReviewComment_FailureNullUserComment() throws Exception {

		try {

			this.specReviewCommentService.addSpecReviewComment(this.tcSubject, PROJECT_ID, false, 3, null);
			fail("IllegalArgumentException is expected when 'UserComment' is null!");
		} catch (EJBException ejbe) {

			if (!(ejbe.getCause() instanceof IllegalArgumentException)) {

				fail("IllegalArgumentException is expected!");
			}
			// we should land here
		}
	}

	/**
	 * <p>
	 * Accuracy unit test for
	 * <code>{@link SpecReviewCommentServiceBean#getSpecReviewComment(TCSubject, long, boolean)}</code>
	 * method.
	 * </p>
	 * 
	 * <p>
	 * Tests if a specification review comment is properly retrieved.
	 * </p>
	 * 
	 * @throws Exception
	 *             pass any unexpected exception to JUnit.
	 */
	public void testGetSpecReviewComment_Accuracy() throws Exception {

		// build and add a UserComment with ID = 3 at thread for question 1.2.3
		// which is mapped to question ID = 5
		UserComment commentOne = buildUserComment("1.2.3", TEST_MESSAGE);
		long id_one = this.specReviewCommentService.addSpecReviewComment(this.tcSubject, PROJECT_ID, false, 5, commentOne);

		// build and add another UserComment with ID = 4 at thread for question
		// 2.3.4 which is mapped to question ID = 6
		UserComment commentTwo = buildUserComment("2.3.4", TEST_MESSAGE);
		long id_two = this.specReviewCommentService.addSpecReviewComment(this.tcSubject, PROJECT_ID, false, 6, commentTwo);

		// retrieve all spec review comments from given project
		List<SpecReviewComment> result = this.specReviewCommentService.getSpecReviewComments(this.tcSubject,
				PROJECT_ID, false);

		// two spec review comments are expected
		assertEquals(result.size(), 2);

		SpecReviewComment reviewComment = null;

		boolean foundIdFive = false;
		boolean foundIdSix = false;

		// iterate over the comments
		for (int i = 0; i < 2; i++) {

			reviewComment = result.get(i);
			if (reviewComment.getQuestionId() == 5) {

				foundIdFive = true;

				List<UserComment> comments = reviewComment.getComments();
				assertEquals(comments.size(), 1);

				UserComment comment = comments.get(0);
				assertEquals(comment.getCommentId(), id_one);
				assertEquals(comment.getCommentBy(), "heffan");
				assertEquals(comment.getComment(), TEST_MESSAGE);
				// question name = '1.2.3' is expected
				assertEquals(comment.getCommentQuestionName(), "1.2.3");
			} else if (reviewComment.getQuestionId() == 6) {

				foundIdSix = true;

				List<UserComment> comments = reviewComment.getComments();
				assertEquals(comments.size(), 1);

				UserComment comment = comments.get(0);
				assertEquals(comment.getCommentId(), id_two);
				assertEquals(comment.getCommentBy(), "heffan");
				assertEquals(comment.getComment(), TEST_MESSAGE);
				// question name = '2.3.4' is expected
				assertEquals(comment.getCommentQuestionName(), "2.3.4");
			}
		}

		// test failed if the proper spec review comments were not found
		if (!(foundIdFive && foundIdSix)) {

			fail("The specification review comments are not properly retrieved!");
		}
	}

	/**
	 * <p>
	 * Failure unit test for
	 * <code>{@link SpecReviewCommentServiceBean#getSpecReviewComment(TCSubject, long, boolean)}</code>
	 * method.
	 * </p>
	 * 
	 * <p>
	 * Tests if <code>IllegalArgumentException</code> is thrown if the
	 * <code>TCSubject</code> is null.
	 * </p>
	 * 
	 * @throws Exception
	 *             pass any unexpected exception to JUnit.
	 */
	public void testGetSpecReviewComment_FailureNullTcSubject() throws Exception {

		try {

			this.specReviewCommentService.getSpecReviewComments(null, PROJECT_ID, false);
			fail("IllegalArgumentException is expected when 'TCSubject' is null!");
		} catch (EJBException ejbe) {

			if (!(ejbe.getCause() instanceof IllegalArgumentException)) {

				fail("IllegalArgumentException is expected!");
			}
			// we should land here
		}
	}

	/**
	 * <p>
	 * Accuracy unit test for
	 * <code>{@link SpecReviewCommentServiceBean#updateSpecReviewComment(TCSubject, long, boolean, long, UserComment)}</code>
	 * method.
	 * </p>
	 * 
	 * @throws Exception
	 *             pass any unexpected exception to JUnit.
	 */
	public void testUpdateSpecReviewComment_Accuracy() throws Exception {

		// build and add a UserComment with ID = 3 at thread for question 1.2.3
		// which is mapped to question ID = 5
		UserComment commentOld = buildUserComment("1.2.3", TEST_MESSAGE);
		long id = this.specReviewCommentService.addSpecReviewComment(this.tcSubject, PROJECT_ID, false, 5, commentOld);

		String newTestMessage = "New Test Message!";

		// build and update a new UserComment with ID = 3 at thread for question
		// 1.2.3
		// which is mapped to question ID = 5
		UserComment commentNew = buildUserComment("1.2.3", newTestMessage);
		commentNew.setCommentId(id);
		this.specReviewCommentService.updateSpecReviewComment(this.tcSubject, PROJECT_ID, false, 5, commentNew);

		// retrieve all spec review comments from given project
		List<SpecReviewComment> result = this.specReviewCommentService.getSpecReviewComments(this.tcSubject,
				PROJECT_ID, false);

		// one spec review comments is expected
		assertEquals(result.size(), 1);

		SpecReviewComment reviewComment = result.get(0);

		List<UserComment> comments = reviewComment.getComments();
		assertEquals(comments.size(), 1);

		UserComment comment = comments.get(0);
		assertEquals(comment.getCommentBy(), "heffan");
		// the 'newTestMessage' is expected after updating the comment
		assertEquals(comment.getComment(), newTestMessage);
		assertEquals(comment.getCommentQuestionName(), "1.2.3");
	}

	/**
	 * <p>
	 * Failure unit test for
	 * <code>{@link SpecReviewCommentServiceBean#updateSpecReviewComment(TCSubject, long, boolean, long, UserComment)}</code>
	 * method.
	 * </p>
	 * 
	 * <p>
	 * Tests if <code>IllegalArgumentException</code> is thrown if the
	 * <code>TCSubject</code> is null.
	 * </p>
	 * 
	 * @throws Exception
	 *             pass any unexpected exception to JUnit.
	 */
	public void testUpdateSpecReviewComment_FailureNullTcSubject() throws Exception {

		// build user comment
		UserComment comment = buildUserComment("1.2.4", TEST_MESSAGE);

		try {

			this.specReviewCommentService.updateSpecReviewComment(null, PROJECT_ID, false, 3, comment);
			fail("IllegalArgumentException is expected when 'TCSubject' is null!");
		} catch (EJBException ejbe) {

			if (!(ejbe.getCause() instanceof IllegalArgumentException)) {

				fail("IllegalArgumentException is expected!");
			}
			// we should land here
		}
	}

	/**
	 * <p>
	 * Failure unit test for
	 * <code>{@link SpecReviewCommentServiceBean#updateSpecReviewComment(TCSubject, long, boolean, long, UserComment)}</code>
	 * method.
	 * </p>
	 * 
	 * <p>
	 * Tests if <code>IllegalArgumentException</code> is thrown if the
	 * <code>UserComment</code> is null.
	 * </p>
	 * 
	 * @throws Exception
	 *             pass any unexpected exception to JUnit.
	 */
	public void testUpdateSpecReviewComment_FailureNullUserComment() throws Exception {

		try {

			this.specReviewCommentService.updateSpecReviewComment(this.tcSubject, PROJECT_ID, false, 3, null);
			fail("IllegalArgumentException is expected when 'TCSubject' is null!");
		} catch (EJBException ejbe) {

			if (!(ejbe.getCause() instanceof IllegalArgumentException)) {

				fail("IllegalArgumentException is expected!");
			}
			// we should land here
		}
	}

	/**
	 * <p>
	 * Builds a <code>UserComment</code> object with given
	 * <code>commentId</code> and <code>questionName</code>.
	 * </p>
	 * 
	 * @param commentId
	 *            The ID of the comment.
	 * @param questionName
	 *            The question name.
	 * @param msg
	 *            The message body of the comment.
	 * @return The <code>UserComment</code> object with given
	 *         <code>commentId</code> and <code>questionName</code>.
	 */
	private UserComment buildUserComment(String questionName, String msg) {

		UserComment comment = new UserComment();
		comment.setCommentBy("heffan");
		comment.setComment(msg);
		comment.setCommentDate(new Date());
		comment.setCommentQuestionName(questionName);

		return comment;
	}
}