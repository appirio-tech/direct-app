package com.topcoder.service.review.comment.specification.ejb;

import com.topcoder.catalog.entity.Status;
import com.topcoder.service.review.comment.specification.SpecReviewComment;
import com.topcoder.service.review.comment.specification.SpecReviewCommentServiceException;
import com.topcoder.service.review.comment.specification.UserComment;

import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.web.ejb.forums.Forums;
import com.topcoder.web.ejb.forums.ForumsHome;
import com.topcoder.web.ejb.forums.ForumsUserComment;
import com.topcoder.web.ejb.forums.ForumsSpecReviewComment;
import com.topcoder.web.ejb.forums.ForumsException;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.topcoder.security.TCSubject;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import org.jboss.logging.Logger;

/**
 * <p>
 * This is the stateless session bean which manages the specification review
 * comments. To add, get and update the specification review comments this bean
 * calls the corresponding methods at forum EJB.
 * </p>
 *
 * @author Shorty
 * @version 1.0
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SpecReviewCommentServiceBean implements SpecReviewCommentServiceLocal, SpecReviewCommentServiceRemote {

    /**
     * Represents the default namespace for this stateless bean.
     */
    public static final String DEFAULT_NAMESPACE =
        "com.topcoder.service.review.comment.specification.ejb.SpecReviewCommentServiceBean";

	/**
	 * <p>
	 * A <code>ContestServiceFacade</code> object providing access to available
	 * contest service layer.
	 * </p>
	 */
	@EJB(name = "ejb/ContestServiceFacade")
	private ContestServiceFacade contestServiceFacade = null;

	/**
	 * <p>
	 * A flag indicating whether or not to create the Specification Review
	 * Comments. It's configured in config file, used in the <code>add</code>, <code>get</code>
	 * and <code>update</code> methods.
	 * </p>
	 */
	private boolean createComment = false;

	/**
	 * <p>
	 * This <code>forumBeanProviderUrl</code> is used in the JNDI context to get
	 * the forum bean in the <code>add</code>, <code>get</code> and
	 * <code>update</code> methods. It's configured in config file, non-null and non-empty after
	 * set.
	 * </p>
	 */
	private String forumBeanProviderUrl;

	/**
	 * <p>
	 * The logger instance for logging the information in
	 * <code>SpecReviewCommentServiceBean.</code>
	 * </p>
	 */
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * <p>
	 * This initializes the logger
	 * </p>
	 *
	 * @throws IllegalStateException If any problem to read configuration.
	 */
	@PostConstruct
	public void init() {

		if (logger == null) {
			logger = Logger.getLogger(this.getClass());
		}


		ConfigManager configManager = ConfigManager.getInstance();

        try {
            createComment = Boolean.parseBoolean(configManager.getString(DEFAULT_NAMESPACE, "createComment"));

            forumBeanProviderUrl = configManager.getString(DEFAULT_NAMESPACE, "forumBeanProviderUrl");
        } catch (ConfigManagerException e) {
            throw new IllegalStateException("Unable to read configuration from file.", e);
        }

		logger.debug("Initialized SpecRevieCommentServiceBean!");
	}

	/**
	 * <p>
	 * Adds specification review comment for the given <code>questionId</code>
	 * and <code>projectId</code>.
	 * </p>
	 *
	 * @param tcSubject
	 *            The <code>TCSubject</code> object.
	 * @param projectId
	 *            The ID of the project to which specification review forum the
	 *            comment should be added.
	 * @param isStudio
	 *            Indicates if the corresponding contest is a Studio contest.
	 * @param questionId
	 *            The ID of the question to which the given <code>comment</code>
	 *            refers to.
	 * @param comment
	 *            The <code>UserComment</code> object which should be added to
	 *            the specification review forum.
	 *
	 * @throws IllegalArgumentException
	 *             If the <code>tcSubject</code> or <code>comment</code> is
	 *             null.
	 * @throws SpecReviewCommentServiceException
	 *             If any other error occurs while adding the given
	 *             <code>comment</code> to the corresponding specification
	 *             review forum. Also thrown if a comment with the given comment
	 *             ID already exists.
	 */
	public long addSpecReviewComment(TCSubject tcSubject, long projectId, boolean isStudio, long questionId,
			UserComment comment) throws SpecReviewCommentServiceException {

		logger.debug("Entered addSpecReviewComment...");

		checkNull(tcSubject, "tcSubject");
		checkNull(comment, "comment");

		// only add spec review comments for non studio contests and if the
		// 'createComment' field is true
		if (!isStudio && this.createComment) {

			try {

				// retrieve Jive Category ID
				long jiveCategoryId = getJiveCategoryId(tcSubject, projectId);
				logger.debug("jiveCategoryId for projectId " + projectId + " is " + jiveCategoryId);

				Forums forums = lookupForumsEJB();

				return forums.addSpecReviewComment(jiveCategoryId, tcSubject.getUserId(), questionId,
						convertToForumsUserComment(comment));
			} catch (ContestServiceException cse) {

				logger.error("An error occured while calling ContestServiceFacade: " + cse);
				throw new SpecReviewCommentServiceException("An error occured while calling ContestServiceFacade!", cse);
			} catch (PermissionServiceException pse) {

				logger.error("A permission error occured while calling ContestServiceFacade: " + pse);
				throw new SpecReviewCommentServiceException(
						"A permission error occured while calling ContestServiceFacade!", pse);
			} catch (RemoteException re) {

				logger.error("A remote error occured while calling forums EJB: " + re);
				throw new SpecReviewCommentServiceException("A remote error occured while calling forums EJB: " + re);
			} catch (ForumsException fe) {

				logger.error("An error occured in forums EJB: " + fe);
				throw new SpecReviewCommentServiceException("An error occured in forums EJB: " + fe);
			}
		} else { // it's a Studio contest

			logger.debug("No spec review comment added since contest is a Studio contest!");
			return -1;
		}
	}

	/**
	 * <p>
	 * Get specification review comments for the given <code>projectId</code>.
	 * </p>
	 *
	 * @param tcSubject
	 *            The <code>TCSubject</code> object.
	 * @param projectId
	 *            The ID of the project from which specification review forum
	 *            the comments should be retrieved.
	 * @param isStudio
	 *            Indicates if the corresponding contest is a Studio contest.
	 * @return A list of specification review comments for the given
	 *         <code>projectId</code>. An empty list is returned in case no
	 *         message was found.
	 *
	 * @throws IllegalArgumentException
	 *             If the <code>tcSubject</code> is null.
	 * @throws SpecReviewCommentServiceException
	 *             If any other error occurs while retrieving the specification
	 *             review comments from the corresponding specification review
	 *             forum.
	 */
	public List<SpecReviewComment> getSpecReviewComments(TCSubject tcSubject, long projectId, boolean isStudio)
			throws SpecReviewCommentServiceException {

		logger.debug("Entered getSpecReviewComments...");

		checkNull(tcSubject, "tcSubject");

		// only add spec review comments for non studio contests and if the
		// 'createComment' field is true
		if (!isStudio && this.createComment) {

			try {

				// retrieve Jive Category ID
				long jiveCategoryId = getJiveCategoryId(tcSubject, projectId);
				logger.debug("jiveCategoryId for projectId " + projectId + " is " + jiveCategoryId);

				Forums forums = lookupForumsEJB();

				return convertFromForumsSpecReviewComments(forums.getSpecReviewComments(jiveCategoryId));
			} catch (ContestServiceException cse) {

				logger.error("An error occured while calling ContestServiceFacade: " + cse);
				throw new SpecReviewCommentServiceException("An error occured while calling ContestServiceFacade!", cse);
			} catch (PermissionServiceException pse) {

				logger.error("A permission error occured while calling ContestServiceFacade: " + pse);
				throw new SpecReviewCommentServiceException(
						"A permission error occured while calling ContestServiceFacade!", pse);
			} catch (RemoteException re) {

				logger.error("A remote error occured while calling forums EJB: " + re);
				throw new SpecReviewCommentServiceException("A remote error occured while calling forums EJB: " + re);
			} catch (ForumsException fe) {

				logger.error("An error occured in forums EJB: " + fe);
				throw new SpecReviewCommentServiceException("An error occured in forums EJB: " + fe);
			}
		} else { // it's a Studio contest

			logger.debug("No spec review comment retrieved since contest is a Studio contest!");
			return null;
		}
	}

	/**
	 * <p>
	 * Updates the specification review comment for the given
	 * <code>questionId</code> and <code>projectId</code>.
	 * </p>
	 *
	 * <p>
	 * <strong>Note: </strong> The ID of the new comment must match the ID of
	 * the old specification review comment which should be updated! Also the
	 * question name of the comment should be the same!
	 * </p>
	 *
	 * @param tcSubject
	 *            The <code>TCSubject</code> object.
	 * @param projectId
	 *            The ID of the project at which specification review forum the
	 *            comment should be updated.
	 * @param isStudio
	 *            Indicates if the corresponding contest is a Studio contest.
	 * @param questionId
	 *            The ID of the question to which the given <code>comment</code>
	 *            refers to.
	 * @param comment
	 *            The <code>UserComment</code> object which should be updated at
	 *            the specification review forum.
	 *
	 * @throws IllegalArgumentException
	 *             If the <code>tcSubject</code> or <code>comment</code> is
	 *             null.
	 * @throws SpecReviewCommentServiceException
	 *             If any other error occurs while updating the given
	 *             <code>comment</code> at the corresponding specification
	 *             review forum.
	 */
	public void updateSpecReviewComment(TCSubject tcSubject, long projectId, boolean isStudio, long questionId,
			UserComment comment) throws SpecReviewCommentServiceException {

		logger.debug("Entered updateSpecReviewComment...");

		checkNull(tcSubject, "tcSubject");
		checkNull(comment, "comment");

		// only add spec review comments for non studio contests and if the
		// 'createComment' field is true
		if (!isStudio && this.createComment) {

			try {

				// retrieve Jive Category ID
				long jiveCategoryId = getJiveCategoryId(tcSubject, projectId);
				logger.debug("jiveCategoryId for projectId " + projectId + " is " + jiveCategoryId);

				Forums forums = lookupForumsEJB();

				forums.updateSpecReviewComment(jiveCategoryId, tcSubject.getUserId(), questionId,
						convertToForumsUserComment(comment));
			} catch (ContestServiceException cse) {

				logger.error("An error occured while calling ContestServiceFacade: " + cse);
				throw new SpecReviewCommentServiceException("An error occured while calling ContestServiceFacade!", cse);
			} catch (PermissionServiceException pse) {

				logger.error("A permission error occured while calling ContestServiceFacade: " + pse);
				throw new SpecReviewCommentServiceException(
						"A permission error occured while calling ContestServiceFacade!", pse);
			} catch (RemoteException re) {

				logger.error("A remote error occured while calling forums EJB: " + re);
				throw new SpecReviewCommentServiceException("A remote error occured while calling forums EJB: " + re);
			} catch (ForumsException fe) {

				logger.error("An error occured in forums EJB: " + fe);
				throw new SpecReviewCommentServiceException("An error occured in forums EJB: " + fe);
			}
		} else { // it's a Studio contest

			logger.debug("No spec review comment updated since contest is a Studio contest!");
		}
	}

	/**
	 * <p>
	 * Retrieves the Jive Category for the given <code>projectId</code>. The
	 * Jive category ID represents the ID of the forum category which is stored
	 * in <em>jive</em> database for the given <code>projectId</code>.
	 * </p>
	 *
	 * @param tcSubject
	 *            The <code>TCSubject</code> object.
	 * @param projectId
	 *            The ID of the project for which the Jive category ID should be
	 *            retrieved.
	 * @return The corresponding Jive category ID for the given
	 *         <code>projectId</code>.
	 *
	 * @throws ContestServiceException
	 *             If an error occurs when interacting with the service layer.
	 * @throws PermissionServiceException
	 *             If the user with the userId stored at given
	 *             <code>tcSubject</code> does not have the proper permission to
	 *             retrieve information about the corresponding project from the
	 *             contest service layer (user needs to have read and write
	 *             permission).
	 */
	private long getJiveCategoryId(TCSubject tcSubject, long projectId) throws ContestServiceException,
			PermissionServiceException {

		// retrieve Jive Category ID from Contest Service Facade
		SoftwareCompetition swCompetition = this.contestServiceFacade.getSoftwareContestByProjectId(tcSubject,
				projectId);
		return swCompetition.getAssetDTO().getForum().getJiveCategoryId();
	}

	/**
	 * <p>
	 * Looks up the forum EJB from the given environment entry
	 * <code>forumBeanProviderUrl</code>.
	 * </p>
	 *
	 * @return The forum EJB.
	 *
	 * @throws SpecReviewCommentServiceException
	 *             If an error occurs while looking up the forum EJB.
	 */
	private Forums lookupForumsEJB() throws SpecReviewCommentServiceException {

		Forums forums = null;

		try {
			Properties p = new Properties();
			p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			p.put(Context.PROVIDER_URL, forumBeanProviderUrl);

			logger.debug("Looking for '" + ForumsHome.EJB_REF_NAME + "' bound on: " + forumBeanProviderUrl);
			Context c = new InitialContext(p);

			ForumsHome forumsHome = (ForumsHome) c.lookup(ForumsHome.EJB_REF_NAME);
			forums = forumsHome.create();
		} catch (Exception e) {
			logger.error("*** Could not lookup Forums EJB!" + e);
			throw new SpecReviewCommentServiceException("An error occured while trying to look up the forum EJB!", e);
		}

		return forums;
	}

	/**
	 * <p>
	 * Converts the given <code>comment</code> to an object of type
	 * <code>ForumsUserComment</code>.
	 * </p>
	 *
	 * @param comment
	 *            The <code>UserComment</code> to convert.
	 * @return The <code>ForumsUserComment</code>.
	 */
	private ForumsUserComment convertToForumsUserComment(UserComment comment) {

		ForumsUserComment forumsUserComment = new ForumsUserComment();

		forumsUserComment.setCommentId(comment.getCommentId());
		forumsUserComment.setCommentBy(comment.getCommentBy());
		forumsUserComment.setCommentDate(comment.getCommentDate());
		forumsUserComment.setComment(comment.getComment());
		forumsUserComment.setCommentQuestionName(comment.getCommentQuestionName());

		return forumsUserComment;
	}

	/**
	 * <p>
	 * Converts the given <code>forumsComment</code> to an object of type
	 * <code>UserComment</code>.
	 * </p>
	 *
	 * @param forumsComment
	 *            The <code>ForumsUserComment</code> to convert.
	 * @return The <code>UserComment</code>.
	 */
	private UserComment convertFromForumsUserComment(ForumsUserComment forumsComment) {

		UserComment comment = new UserComment();

		comment.setCommentId(forumsComment.getCommentId());
		comment.setCommentBy(forumsComment.getCommentBy());
		comment.setCommentDate(forumsComment.getCommentDate());
		comment.setComment(forumsComment.getComment());
		comment.setCommentQuestionName(forumsComment.getCommentQuestionName());

		return comment;
	}

	/**
	 * <p>
	 * Converts the given <code>forumsComments</code> list to a list of
	 * <code>UserComment</code> objects.
	 * </p>
	 *
	 * @param forumsComments
	 *            The List of <code>ForumsUserComment</code> objects.
	 * @return The List of <code>UserComment</code> objects.
	 */
	private List<UserComment> convertFromForumsUserComments(List<ForumsUserComment> forumsComments) {

		List<UserComment> comments = new LinkedList<UserComment>();

		for (ForumsUserComment forumsComment : forumsComments) {

			comments.add(convertFromForumsUserComment(forumsComment));
		}

		return comments;
	}

	/**
	 * <p>
	 * Converts the given <code>forumsComments</code> to a list of
	 * <code>SpecReviewComment</code> objects.
	 * </p>
	 *
	 * @param forumsComments
	 *            The list of <code>ForumsSpecReviewComment</code> objects.
	 * @return The converted list of <code>SpecReviewComment</code> objects.
	 */
	private List<SpecReviewComment> convertFromForumsSpecReviewComments(List<ForumsSpecReviewComment> forumsComments) {

		List<SpecReviewComment> comments = new LinkedList<SpecReviewComment>();
		for (ForumsSpecReviewComment forumsComment : forumsComments) {

			comments.add(convertFromForumsSpecReviewComment(forumsComment));
		}

		return comments;
	}

	/**
	 * <p>
	 * Converts the given <code>forumsComment</code> to a
	 * <code>SpecReviewComment</code> object.
	 * </p>
	 *
	 * @param forumsComment
	 *            The <code>ForumsSpecReviewComment</code> object to convert.
	 * @return The converted <code>SpecReviewComment</code> object.
	 */
	private SpecReviewComment convertFromForumsSpecReviewComment(ForumsSpecReviewComment forumsComment) {

		SpecReviewComment comment = new SpecReviewComment();
		comment.setQuestionId(forumsComment.getQuestionId());
		comment.setComments(convertFromForumsUserComments(forumsComment.getComments()));

		return comment;
	}

	/**
	 * <p>
	 * Checks if the given <code>obj</code> is null.
	 * </p>
	 *
	 * @param obj
	 *            The <code>Object</code> which to check for being null.
	 * @param objName
	 *            The name of the given <code>obj</code>.
	 *
	 * @throws IllegalArgumentException
	 *             If the given <code>obj</code> is null.
	 */
	private void checkNull(Object obj, String objName) {

		if (obj == null) {

			throw new IllegalArgumentException("The [" + objName + "] should not be null!");
		}
	}
}
