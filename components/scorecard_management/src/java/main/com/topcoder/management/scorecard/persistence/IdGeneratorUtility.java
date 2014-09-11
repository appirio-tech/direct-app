/*
 * Copyright (C) 2006-2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.persistence;

import com.topcoder.management.scorecard.ConfigurationException;
import com.topcoder.management.scorecard.persistence.logging.LogMessage;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * The main task of this class is to load the IDGenerators used by the component and share thame between
 * multiple classes.
 * This method is thread safe. The only method that changes state of this class is synchronized.
 * </p>
 *
 * <p>
 * Changes in v1.0.2 (Cockpit Spec Review Backend Service Update v1.0):
 * - LogManager is used instead of LogFactory.
 * </p>
 *
 * @author tuenm, kr00tki, pulky
 * @version 1.0.2
 */
class IdGeneratorUtility {

    /**
     * Logger instance.
     */
    private static final Log logger = LogManager.getLog(IdGeneratorUtility.class.getName());

    /**
     * The default name of the id generator for the scorecards.
     */
    private static final String DEFAULT_SCORECARD_ID_SEQUENCE_NAME = "scorecard_id_seq";

    /**
     * The default name of the id generator for the scorecard groups.
     */
    private static final String DEFAULT_SCORECARD_GROUP_ID_SEQUENCE_NAME = "scorecard_group_id_seq";

    /**
     * The default name of the id generator for the scorecard sections.
     */
    private static final String DEFAULT_SCORECARD_SECTION_ID_SEQUENCE_NAME = "scorecard_section_id_seq";

    /**
     * The default name of the id generator for the scorecard questions.
     */
    private static final String DEFAULT_SCORECARD_QUESTION_ID_SEQUENCE_NAME = "scorecard_question_id_seq";

    /**
     * The IDGenerator instance used for scorecards ids.
     */
    private static IDGenerator scorecardIdGenerator = null;

    /**
     * The IDGenerator instance used for scorecard group ids.
     */
    private static IDGenerator groupIdGenerator = null;

    /**
     * The IDGenerator instance used for scorecard sections ids.
     */
    private static IDGenerator sectionIdGenerator = null;

    /**
     * The IDGenerator instance used for scorecard questions ids.
     */
    private static IDGenerator questionIdGenerator = null;

    /**
     * Flag indicating if the IDGenerators were already initialized.
     */
    private static boolean isInitialized = false;

    /**
     * Private constructor. We do not want any instances of this class.
     *
     */
    private IdGeneratorUtility() {
        // empty
    }

    /**
     * Loads the ID Generators from the given namespace on the first call. Each next call won't do anything.
     * This method is synchronized because it may be used by multiple threads.
     *
     * @param namespace the namespace from the cofniguration will be read.
     * @throws ConfigurationException if error occurs while creating the generators.
     */
    public static synchronized void loadIdGenerators(String namespace) throws ConfigurationException {
        if (!isInitialized) {
            // create id generators
            scorecardIdGenerator = createIdGenerator(namespace, "ScorecardIdSequenceName",
                    DEFAULT_SCORECARD_ID_SEQUENCE_NAME);

            groupIdGenerator = createIdGenerator(namespace, "ScorecardGroupIdSequenceName",
                    DEFAULT_SCORECARD_GROUP_ID_SEQUENCE_NAME);
            questionIdGenerator = createIdGenerator(namespace, "ScorecardQuestionIdSequenceName",
                    DEFAULT_SCORECARD_SECTION_ID_SEQUENCE_NAME);

            sectionIdGenerator = createIdGenerator(namespace, "ScorecardSectionIdSequenceName",
                    DEFAULT_SCORECARD_QUESTION_ID_SEQUENCE_NAME);

            isInitialized = true;
        }
    }

    /**
     * <p>
     * Creates the IdGenerator using the IDGeneratorFactory. The name of the generator is retrieved
     * from the configuration or the default is used.
     * </p>
     *
     * @param namespace the configuration namespace to be used.
     * @param property the id generator name property.
     * @param defaultName the default name to be used.
     * @return the IDGenerator instance.
     * @throws ConfigurationException if the IDGenerator could not be created or configuration error occurs.
     */
    private static IDGenerator createIdGenerator(String namespace, String property, String defaultName)
        throws ConfigurationException {
        String name = null;
        try {

            name = ConfigManager.getInstance().getString(namespace, property);
            if ((name == null) || (name.trim().length() == 0)) {
                name = defaultName;
            } else {
                logger.log(Level.INFO,
                        "Read property " + property + "[" + name + "] from namespace:" + namespace);
            }
   
            IDGenerator idGenerator = IDGeneratorFactory.getIDGenerator(defaultName);
            logger.log(Level.INFO, "create IDGenerator instance with the idGenerator name:" + defaultName);
            return idGenerator;
        } catch (IDGenerationException ex) {
        	logger.log(Level.FATAL, "Fails to create id generator.\n" + LogMessage.getExceptionStackTrace(ex));
            throw new ConfigurationException("Error occur while creating id generator: " + name, ex);
        } catch (UnknownNamespaceException ex) {
        	logger.log(Level.FATAL, "Fails to create id generator.\n" + LogMessage.getExceptionStackTrace(ex));
            throw new ConfigurationException("Error occur while reading configuration.", ex);
        }
    }

    /**
     * Returns the IDGenerator for Group objects.
     *
     * @return the IDGenerator for group.
     */
    public static IDGenerator getGroupIdGenerator() {
        return groupIdGenerator;
    }

    /**
     * Returns the IDGenerator for Question objects.
     *
     * @return the IDGenerator for question.
     */
    public static IDGenerator getQuestionIdGenerator() {
        return questionIdGenerator;
    }

    /**
     * Returns the IDGenerator for Scorecard objects.
     *
     * @return the IDGenerator for scorecard.
     */
    public static IDGenerator getScorecardIdGenerator() {
        return scorecardIdGenerator;
    }

    /**
     * Returns the IDGenerator for Section objects.
     *
     * @return the IDGenerator for section.
     */
    public static IDGenerator getSectionIdGenerator() {
        return sectionIdGenerator;
    }
}
