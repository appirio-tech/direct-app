/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dao.impl;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProjectDAO;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectFeedback;
import com.topcoder.direct.services.copilot.model.CopilotProjectInfo;
import com.topcoder.direct.services.copilot.model.CopilotProjectInfoType;
import com.topcoder.direct.services.copilot.model.IdentifiableEntity;
import org.hibernate.HibernateException;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>This class is an implementation of CopilotProjectDAO that uses Hibernate session to access entities in
 * persistence. It extends GenericDAOImpl&lt;CopilotProject&gt; that provides common functionality for
 * CopilotProfileDAO, CopilotProjectDAO and CopilotProjectPlanDAO implementations provided in this component. This class
 * uses Logging Wrapper logger to log errors and debug information.</p>
 *
 * <p><strong>Configuration: </strong>This class will be used with Spring IoC and will be configured using Spring xml
 * file, sample bean definition:
 *
 * <pre>
 * &lt;bean id="copilotProjectDAO"
 *        class="com.topcoder.direct.services.copilot.dao.impl.CopilotProjectDAOImpl"&gt;
 *      &lt;property name="loggerName" value="myLogger"/&gt;
 *      &lt;property name="sessionFactory" ref="tcsCatalogSessionFactory"/&gt;
 * &lt;/bean&gt;
 * </pre>
 * </p>
 *
 * <p><strong>Sample usage:</strong>
 * <pre>
 * // Retrieves CopilotProjectDAO from the Spring application context
 * ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
 * CopilotProjectDAO copilotProjectDAO =
 * (CopilotProjectDAO) context.getBean("copilotProjectDAO");
 *
 * // Creates CopilotProjectProject instance and fill it with data
 * CopilotProject copilotProject = new CopilotProject();
 *
 * // Saves the CopilotProject in persistence
 * int id = copilotProjectDAO.create(copilotProject);
 *
 * // Updates the CopilotProject
 * copilotProjectDAO.update(copilotProject);
 *
 * // Deletes the CopilotProject by id
 * copilotProjectDAO.delete(1);
 *
 * // Retrieves CopilotProject by id
 * CopilotProject copilotProject = copilotProjectDAO.retrieve(1);
 *
 * // Retrieves all CopilotProjects
 * List&lt;CopilotProject&gt; copilotProjects = copilotProjectDAO.retrieveAll();
 * </pre>
 * </p>
 *
 * <p>
 *    Version 1.1 (Module Assembly - TopCoder Copilot Feedback Integration) updates:
 *    <ul>
 *        <li>Add method {@link #getCopilotProjectFeedback(long, long)}</li>
 *        <li>Add method {@link #addCopilotProjectFeedback(com.topcoder.direct.services.copilot.model.CopilotProjectFeedback, long)}</li>
 *        <li>Add method {@link #updateCopilotProjectFeedback(com.topcoder.direct.services.copilot.model.CopilotProjectFeedback, long)}</li>
 *    </ul>
 * </p>
 *
 * <p>
 *     Version 1.2 (Release Assembly - TopCoder Copilot Feedback Updates)
 *     <ul>
 *         <li>Refactor magic string and int into private static constants</li>
 *         <li>Add support for feedback ratings</li>
 *     </ul>
 * </p>
 *
 * <p><strong>Thread safety:</strong> This class has mutable attributes, thus it's not thread safe. But it's assumed
 * that it will be initialized via Spring IoC before calling any business method, this way it's always used in thread
 * safe manner. It uses thread safe SessionFactory, Session and Log instances.</p>
 *
 * @author saarixx, GreatKevin
 * @version 1.2
 */
public class CopilotProjectDAOImpl extends GenericDAOImpl<CopilotProject> implements CopilotProjectDAO {

    /**
     * <p>Represents class name.</p>
     */
    private static final String CLASS_NAME = "CopilotProjectDAOImpl";

    /**
     * <p>Represents a hql query used for retrieving {@link CopilotProject} by copilotProfileId.</p>
     */
    private static final String COPILOT_PROJECT_QUERY =
            "from CopilotProject p where p.copilotProfileId = :copilotProfileId";

    /**
     * The pending status of the feedback.
     *
     * @since 1.2
     */
    private static final String FEEDBACK_PENDING_STATUS = "Pending";

    /**
     * The yes answer for the copilot feedback.
     *
     * @since 1.2
     */
    private static final String FEEDBACK_ANSWER_YES = "Yes";

    /**
     * The no answer for the copilot feedback.
     *
     * @since 1.2
     */
    private static final String FEEDBACK_ANSWER_NO = "No";

    /**
     * The min value of the feedback rating.
     *
     * @since 1.2
     */
    private static final int MIN_FEEDBACK_RATING = 1;

    /**
     * The max value of the feedback rating.
     *
     * @since 1.2
     */
    private static final int MAX_FEEDBACK_RATING = 5;

    /**
     * The name of the timeline rating.
     *
     * @since 1.2
     */
    private static final String TIMELINE_RATING_NAME = "timeline";

    /**
     * The name of the quality rating.
     *
     * @since 1.2
     */
    private static final String QUALITY_RATING_NAME = "quality";

    /**
     * The name of the communication rating.
     *
     * @since 1.2
     */
    private static final String COMMUNICATION_RATING_NAME = "communication";

    /**
     * The name of the management rating.
     *
     * @since 1.2
     */
    private static final String MANAGEMENT_RATING_NAME = "management";

    /**
     * <p>Creates new instance of <code>{@link CopilotProjectDAOImpl}</code> class.</p>
     */
    public CopilotProjectDAOImpl() {
        // empty constructor
    }

    /**
     * <p>Retrieves the copilot projects for the copilot with the specified profile ID. Returns an empty list if the
     * copilot has no associated projects.</p>
     *
     * @param copilotProfileId the ID of the copilot profile
     *
     * @return the copilot projects for the specified copilot (not null, doesn't contain null)
     *
     * @throws IllegalArgumentException if copilotProfileId <= 0
     * @throws CopilotDAOException      if some other error occurred
     */
    @SuppressWarnings("unchecked")
    public List<CopilotProject> getCopilotProjects(long copilotProfileId) throws CopilotDAOException {
        final String methodName = "getCopilotProjects";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), CLASS_NAME, methodName, new String[]{"copilotProfileId"},
                new Object[]{copilotProfileId});

        Helper.checkIsPositive(getLog(), copilotProfileId, "copilotProfileId", CLASS_NAME, methodName);

        try {
            // execute query and retrieves result as list
            List<CopilotProject> result = getSession()
                    .createQuery(COPILOT_PROJECT_QUERY)
                    .setParameter("copilotProfileId", copilotProfileId)
                    .list();

            // log method exit
            Helper.logMethodExited(getLog(), CLASS_NAME, methodName, executionStart, result);

            // returns result
            return result;
        } catch (HibernateException e) {
            CopilotDAOException exc = new CopilotDAOException(
                    MessageFormat.format("Exception occurred when retrieving CopilotProject with copilotProfileId {0}.",
                            copilotProfileId), e);

            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), exc);

            throw exc;
        } catch (CopilotDAOException e) {
            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), e);

            throw e;
        }
    }

    /**
     * Gets the copilot feedback on the given copilot project id by the given user.
     *
     * <p>
     * Version 1.2 (Release Assembly - TopCoder Copilot Feedback Updates)
     * - Adds copilot feedback ratings data when getting copilot project feedback
     * </p>
     *
     * @param copilotProjectId the copilot project id.
     * @param userId the id of the user who gives the feedback.
     * @return the <code>CopilotProjectFeedback</code> instance
     * @throws CopilotDAOException if error occurs
     *
     * @since 1.1
     */
    @Transactional
    public CopilotProjectFeedback getCopilotProjectFeedback(long copilotProjectId, long userId)
            throws CopilotDAOException {
        try {
            CopilotProject cp = (CopilotProject) getSession().get(CopilotProject.class, copilotProjectId);

            final Set<CopilotProjectInfo> projectInfos = cp.getProjectInfos();

            if (projectInfos == null) return null;

            CopilotProjectFeedback feedback = new CopilotProjectFeedback();
            String userIdStr = String.valueOf(userId);

            for (CopilotProjectInfo info : projectInfos) {
                long infoType = info.getInfoType().getId();
                String infoOwner = info.getCreateUser();

                if (infoType == 1L && infoOwner.equals(userIdStr)) {
                    feedback.setText(info.getValue());
                    feedback.setSubmitDate(info.getModifyDate());
                } else if (infoType == 2L && infoOwner.equals(userIdStr)) {
                    feedback.setAnswer(info.getValue().equalsIgnoreCase(FEEDBACK_ANSWER_YES));
                } else if (infoType == 3L && infoOwner.equals(userIdStr)) {
                    feedback.setAuthorId(Long.parseLong(info.getValue()));
                } else if (infoType == 4L && infoOwner.equals(userIdStr)) {
                    feedback.setStatus(info.getValue());
                } else if (infoType == 5L && infoOwner.equals(userIdStr)) {
                    feedback.setTimelineRating(Integer.parseInt(info.getValue()));
                } else if (infoType == 6L && infoOwner.equals(userIdStr)) {
                    feedback.setQualityRating(Integer.parseInt(info.getValue()));
                } else if (infoType == 7L && infoOwner.equals(userIdStr)) {
                    feedback.setCommunicationRating(Integer.parseInt(info.getValue()));
                } else if (infoType == 8L && infoOwner.equals(userIdStr)) {
                    feedback.setManagementRating(Integer.parseInt(info.getValue()));
                }

            }

            // do not check ratings because old copilot feedback does not have ratings
            if (feedback.getText() == null && feedback.getStatus() == null && feedback.getSubmitDate() == null) {
                return null;
            } else if (feedback.getText() == null || feedback.getStatus() == null || feedback.getAuthorId() <= 0) {
                throw new CopilotDAOException("The copilot feedback does not exist or the feedback data is inconsistent");
            }

            return feedback;

        } catch (Exception ex) {
            throw new CopilotDAOException("getCopilotProjectFeedback error", ex);
        }
    }

    /**
     * Adds the copilot feedback to the given copilot project.
     *
     * <p>
     * Version 1.2 (Release Assembly - TopCoder Copilot Feedback Updates)
     * - Adds copilot feedback ratings
     * </p>
     *
     * @param feedback the feedback to add.
     * @param copilotProjectId the copilot project id.
     * @throws CopilotDAOException if error occurs.
     *
     * @since 1.1
     */
    @Transactional
    public void addCopilotProjectFeedback(CopilotProjectFeedback feedback, long copilotProjectId)
            throws CopilotDAOException {
        try {
            CopilotProject cp = (CopilotProject) getSession().get(CopilotProject.class, copilotProjectId);

            Set<CopilotProjectInfo> projectInfos = cp.getProjectInfos();
            String authorIdStr = String.valueOf(feedback.getAuthorId());

            if(projectInfos == null || projectInfos.size() == 0) {
            } else {
                for(CopilotProjectInfo info : projectInfos) {
                    if(info.getInfoType().getId() >= 1 && info.getInfoType().getId() <= 4 && info.getCreateUser().equals(authorIdStr)) {
                        throw new CopilotDAOException("The copilot feedback is already added");
                    }
                }
            }

            // validate copilot feedback ratings
            validateCopilotFeedbackRating(feedback.getTimelineRating(), TIMELINE_RATING_NAME);
            validateCopilotFeedbackRating(feedback.getQualityRating(), QUALITY_RATING_NAME);
            validateCopilotFeedbackRating(feedback.getCommunicationRating(), COMMUNICATION_RATING_NAME);
            validateCopilotFeedbackRating(feedback.getManagementRating(), MANAGEMENT_RATING_NAME);

            Date currentTime = new Date();

            projectInfos.add(getCopilotProjectInfo(1, feedback.getText(), copilotProjectId, authorIdStr, authorIdStr, currentTime, currentTime));
            projectInfos.add(getCopilotProjectInfo(2, feedback.isAnswer() ? FEEDBACK_ANSWER_YES : FEEDBACK_ANSWER_NO, copilotProjectId, authorIdStr, authorIdStr, currentTime, currentTime));
            projectInfos.add(getCopilotProjectInfo(3, String.valueOf(feedback.getAuthorId()), copilotProjectId, authorIdStr, authorIdStr, currentTime, currentTime));
            projectInfos.add(getCopilotProjectInfo(4, FEEDBACK_PENDING_STATUS, copilotProjectId, authorIdStr, authorIdStr, currentTime, currentTime));
            projectInfos.add(getCopilotProjectInfo(5, String.valueOf(feedback.getTimelineRating()), copilotProjectId, authorIdStr, authorIdStr, currentTime, currentTime));
            projectInfos.add(getCopilotProjectInfo(6, String.valueOf(feedback.getQualityRating()), copilotProjectId, authorIdStr, authorIdStr, currentTime, currentTime));
            projectInfos.add(getCopilotProjectInfo(7, String.valueOf(feedback.getCommunicationRating()), copilotProjectId, authorIdStr, authorIdStr, currentTime, currentTime));
            projectInfos.add(getCopilotProjectInfo(8, String.valueOf(feedback.getManagementRating()), copilotProjectId, authorIdStr, authorIdStr, currentTime, currentTime));

            cp.getProjectInfos().addAll(projectInfos);

            //getSession().update(cp);

        } catch (Exception ex) {
            throw new CopilotDAOException("addCopilotProjectFeedback error", ex);
        }
    }

    /**
     * Utility method to get the new <code>CopilotProjectInfo</code> instance with the passed in data.
     *
     * @param infoTypeId the copilot project info type id.
     * @param infoValue the copilot project info type value.
     * @param copilotProjectId the copilot project id.
     * @param createUserId the create user id.
     * @param updateUserId the update user id.
     * @param creationTime the creation time.
     * @param updateTime the update time.
     * @return the <code>CopilotProjectInfo</code> instance.
     * @since 1.1
     */
    private CopilotProjectInfo getCopilotProjectInfo(long infoTypeId, String infoValue, long copilotProjectId,
                                                     String createUserId, String updateUserId, Date creationTime, Date updateTime) {
        CopilotProjectInfo info = new CopilotProjectInfo();
        CopilotProjectInfoType infoType = new CopilotProjectInfoType();
        infoType.setId(infoTypeId);
        info.setInfoType(infoType);
        info.setValue(infoValue);
        info.setCreateDate(creationTime);
        info.setModifyDate(updateTime);
        info.setCreateUser(createUserId);
        info.setModifyUser(updateUserId);
        info.setCopilotProjectId(copilotProjectId);
        return info;
    }

    /**
     * Updates the copilot feedback of the given copilot project.
     *
     * <p>
     * Updates in version 1.2 (Release Assembly - TopCoder Copilot Feedback Updates)
     * - Update copilot feedback ratings if exist
     * - Add copilot feedback ratings if the updated feedback has them but old feedback does not
     * </p>
     *
     * @param feedback the feedback to add.
     * @param copilotProjectId the copilot project id.
     * @throws CopilotDAOException if error occurs.
     *
     * @since 1.1
     */
    @Transactional
    public void updateCopilotProjectFeedback(CopilotProjectFeedback feedback, long copilotProjectId)
            throws CopilotDAOException {
        try {
            CopilotProject cp = (CopilotProject) getSession().get(CopilotProject.class, copilotProjectId);

            Set<CopilotProjectInfo> projectInfos = cp.getProjectInfos();
            Set<CopilotProjectInfo> toRemove = new HashSet<CopilotProjectInfo>();
            String authorIdStr = String.valueOf(feedback.getAuthorId());
            // note that updater can be different from author because admin can edit the feedback
            String updaterIdStr = String.valueOf(feedback.getUpdaterId());

            Date updateTime = new Date();


            if(projectInfos == null || projectInfos.size() == 0) {
                throw new CopilotDAOException("There is no feedback for the copilot project from user:" + authorIdStr);
            } else {

                boolean hasFeedback = false;
                boolean hasRating = false;

                for(CopilotProjectInfo info : projectInfos) {
                    if(info.getInfoType().getId() >= 1 && info.getInfoType().getId() <= 8 && authorIdStr.equals(info.getCreateUser())) {
                        // toRemove.add(info);
                        hasFeedback = true;

                        // check if need to update
                        if(info.getInfoType().getId() == 1L) {
                            info.setValue(feedback.getText());
                        } else if(info.getInfoType().getId() == 2L) {
                            info.setValue(feedback.isAnswer() ? FEEDBACK_ANSWER_YES : FEEDBACK_ANSWER_NO);
                        } else if(info.getInfoType().getId() == 4L) {
                            info.setValue(feedback.getStatus());
                        } else if(info.getInfoType().getId() == 5L) {
                            hasRating = true;
                            validateCopilotFeedbackRating(feedback.getTimelineRating(), TIMELINE_RATING_NAME);
                            info.setValue(String.valueOf(feedback.getTimelineRating()));
                        } else if(info.getInfoType().getId() == 6L) {
                            hasRating = true;
                            validateCopilotFeedbackRating(feedback.getQualityRating(), QUALITY_RATING_NAME);
                            info.setValue(String.valueOf(feedback.getQualityRating()));
                        } else if(info.getInfoType().getId() == 7L) {
                            hasRating = true;
                            validateCopilotFeedbackRating(feedback.getCommunicationRating(), COMMUNICATION_RATING_NAME);
                            info.setValue(String.valueOf(feedback.getCommunicationRating()));
                        } else if(info.getInfoType().getId() == 8L) {
                            hasRating = true;
                            validateCopilotFeedbackRating(feedback.getManagementRating(), MANAGEMENT_RATING_NAME);
                            info.setValue(String.valueOf(feedback.getManagementRating()));
                        }

                        info.setModifyDate(updateTime);
                        info.setModifyUser(updaterIdStr);
                    }
                }

                // backward compatibility - if the feedback to edit exists but is old one without rating
                // we need to inset ratings if exists in the feedback object passed in
                Set<CopilotProjectInfo> ratingsInfo = new HashSet<CopilotProjectInfo>();
                if (hasFeedback && !hasRating) {
                    Date currentTime = new Date();
                    if (feedback.getTimelineRating() > 0) {
                        validateCopilotFeedbackRating(feedback.getTimelineRating(), TIMELINE_RATING_NAME);
                        ratingsInfo.add(getCopilotProjectInfo(5, String.valueOf(feedback.getTimelineRating()), copilotProjectId, authorIdStr, updaterIdStr, currentTime, currentTime));
                    }
                    if (feedback.getQualityRating() > 0) {
                        validateCopilotFeedbackRating(feedback.getQualityRating(), QUALITY_RATING_NAME);
                        ratingsInfo.add(getCopilotProjectInfo(6, String.valueOf(feedback.getQualityRating()), copilotProjectId, authorIdStr, updaterIdStr, currentTime, currentTime));
                    }
                    if (feedback.getCommunicationRating() > 0) {
                        validateCopilotFeedbackRating(feedback.getCommunicationRating(), COMMUNICATION_RATING_NAME);
                        ratingsInfo.add(getCopilotProjectInfo(7, String.valueOf(feedback.getCommunicationRating()), copilotProjectId, authorIdStr, updaterIdStr, currentTime, currentTime));
                    }
                    if (feedback.getManagementRating() > 0) {
                        validateCopilotFeedbackRating(feedback.getManagementRating(), MANAGEMENT_RATING_NAME);
                        ratingsInfo.add(getCopilotProjectInfo(8, String.valueOf(feedback.getManagementRating()), copilotProjectId, authorIdStr, updaterIdStr, currentTime, currentTime));
                    }

                    // add to copilot project info
                    projectInfos.addAll(ratingsInfo);
                }


                if(!hasFeedback) {
                    throw new CopilotDAOException("There is no feedback for the copilot project from user:" + authorIdStr);
                }

            }


        } catch (Exception ex) {
            throw new CopilotDAOException("addCopilotProjectFeedback error", ex);
        }
    }

    /**
     * <p>Updates the creation/modification timestamp property of the given entity with the current date/time.</p>
     *
     * @param entity the entity to be updated
     * @param create true if entity will be created in persistence, false - if entity will be updated
     *
     * @throws IllegalArgumentException if entity is null
     */
    protected void updateAuditTimestamp(IdentifiableEntity entity, boolean create) {
        // delegate to base class method
        // propagates IllegalArgumentException
        super.updateAuditTimestamp(entity, create);

        if (entity instanceof CopilotProject) {
            CopilotProject copilotProject = (CopilotProject) entity;

            // iterate over all ProjectInfo and update them
            if (copilotProject.getProjectInfos() != null) {
                for (CopilotProjectInfo copilotProjectInfo : copilotProject.getProjectInfos()) {
                    updateAuditTimestamp(copilotProjectInfo, copilotProjectInfo.getId() == 0);
                }
            }
        }
    }


    /**
     * Helper methods to validates whether the copilot feedback rating is in the valid rating.
     *
     * @param ratingValue the value of the feedback rating.
     * @param ratingName the name of the feedback rating.
     * @since 1.2
     */
    private void validateCopilotFeedbackRating(int ratingValue, String ratingName) {
        if (ratingValue < MIN_FEEDBACK_RATING || ratingValue > MAX_FEEDBACK_RATING) {
            throw new IllegalArgumentException("The copilot feedback " + ratingName + " rating is not between " + MIN_FEEDBACK_RATING + " and " + MAX_FEEDBACK_RATING);
        }
    }
}

