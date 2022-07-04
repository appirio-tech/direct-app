package com.topcoder.direct.services.view.dto.copilot;

import java.io.Serializable;

/**
 * <p>
 * The DTO for store the copilot skill rule.
 * </p>
 *
 * @author GreatKevin
 * @version 1.0 (Module Assembly - Cockpit Copilot Posting Skills Update and Submission Revamp)
 */
public class CopilotSkillDTO implements Serializable {
    /**
     * The id of the copilot skill rule.
     */
    private long ruleId;

    /**
     * The name of the copilot skill rule.
     */
    private String name;

    /**
     * The description of the copilot skill rule.
     */
    private String description;

    /**
     * Gets the id of the copilot skill rule
     *
     * @return the id of the copilot skill rule
     */
    public long getRuleId() {
        return ruleId;
    }

    /**
     * Sets the id of the copilot skill rule
     *
     * @param ruleId the id of the copilot skill rule
     */
    public void setRuleId(long ruleId) {
        this.ruleId = ruleId;
    }

    /**
     * Gets name of the copilot skill rule.
     *
     * @return name of the copilot skill rule.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of the copilot skill rule.
     *
     * @param name name of the copilot skill rule.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the copilot skill rule.
     *
     * @return the description of the copilot skill rule.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the copilot skill rule.
     *
     * @param description the description of the copilot skill rule.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
