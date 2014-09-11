/*
 * Copyright (C) 2007-2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * <p>This class represents a component's version.</p>
 * <p>When a new version is created, <code>version</code> field is increased by one, while
 * <code>versionText</code> field is to be entered by a user.</p>
 * <p>The version points to the current <code>phase</code>.
 * Fields <code>phaseTime</code> and <code>phasePrice</code> represent the start date and the price of
 * the <code>phase</code>.</p>
 * <p>They are now redundant, since <code>CompVersionDates</code> provides more detailed information.</p>
 * <p>For each phase of the version, there could be a CompVersionDates providing different
 * dates for the version, as well as comments and the price.</p>
 * <p>The field <code>versionDates</code> uses a map
 * whose key is the <code>phase.id</code> and the value is a <code>CompVersionDates</code> entity.</p>
 * <p>The version contains a list of <code>Technologies</code> in <code>technologies</code> attribute.</p>
 * <p>The component version can have a forum associated. This is represented in the entity by <code>forum</code>
 * attribute.</p>
 * <p>The component version can have a link (e.g. an SVN link where component files are stored),
 * represented by <code>link</code> attribute.</p>
 * <p>Validation of parameters is not performed in this class. It's supposed to be a caller's responsibility.</p>
 * <p>Example of creation:</p>
 * <pre>
 *       final CompVersion version = new CompVersion();
 *       version.setComments("Version comments");
 *       version.setPhasePrice(500);
 *       version.setPhaseTime(parseDate("2007/12/21"));
 *       version.setSuspended(false);
 *       version.setCompVersion(1L);
 *       version.setVersionText("1.0");
 *       // create forum
 *       final CompForum compForum = new CompForum();
 *       compForum.setCompVersion(version);
 *       version.setForum(compForum); // assign to the version
 *       // create link
 *       final CompLink compLink = new CompLink();
 *       compLink.setLink("some svnlink");
 *       compLink.setCompVersion(version);
 *       version.setLink(compLink); // assign to the version
 *       // create documentation
 *       // note: newly added in version 1.1
 *       final CompDocumentation compDocumentation = new CompDocumentation();
 *       compDocumentation.setDocumentName("my doc");
 *       compDocumentation.setDocumentTypeId(300L);
 *       compDocumentation.setUrl("software.topcoder.com");
 *       compDocumentation.setCompVersion(version);
 *       List&lt;CompDocumentation&gt; documentation = new ArrayList&lt;CompDocumentation&gt;();
 *       documentation.add(compDocumentation);
 *       version.setDocumentation(documentation); // assign to the version
 *
 *       // assign phase, which is already in the database
 *       version.setPhase(getEntityManager().find(Phase.class, 1L));
 *
 *       // populate version phase dates
 *       final Map&lt;Long, CompVersionDates&gt; dates = populateVersionDates(version);
 *       version.setVersionDates(dates);
 * </pre>
 * <p>
 * version 1.1 add filed <code>documentation</code> which stores documents associated to this component version.
 * </p>
 * <p><strong>Thread safety: </strong></p> <p>This class is mutable and not thread safe.</p>
 *
 * @author caru, Retunsky, KingStone
 * @version 1.1
 * @since 1.0
 */
public class CompVersion implements Serializable {
    /**
     * <p>This field represents the id of the entity.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private Long id;
    /**
     * <p>This field represents the component part of the entity.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Component</code> instance or <tt>null</tt>.</p>
     */
    private Component component;
    /**
     * <p>This field represents the version of the entity.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private Long version;
    /**
     * <p>This field represents the text of the version.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String versionText;
    /**
     * <p>This field represents  the phase of the component version.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Phase</code> instance or <tt>null</tt>.</p>
     */
    private Phase phase;
    /**
     * <p>This field represents the phase time of the version.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     */
    private Date phaseTime;
    /**
     * <p>This field represents the the phase price of the version.</p>
     * <p>The initial value is <tt>0.0d</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>double</code> value.</p>
     */
    private double phasePrice;
    /**
     * <p>This field represents the comments of the version.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String comments;
    /**
     * <p>This field indicates whether the version is suspended or not.</p>
     * <p>The initial value is <code>false</code>.</p>
     * <p>The acceptable region: <code>true</code> or <code>false</code>.</p>
     */
    private boolean suspended;
    /**
     * <p>This field represents the forum of the version.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>CompForum</code> instance or <tt>null</tt>.</p>
     */
    private CompForum forum;
    /**
     * <p>This field represents the link of the version.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>CompLink</code> instance or <tt>null</tt>.</p>
     */
    private CompLink link;
    /**
     * <p>This field represents the mapping between dates and versions.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptable region: any map including <code>null</code> and empty one, a non-empty map
     * containing <code>null</code> key or value is legal as well.</p>
     */
    private Map<Long, CompVersionDates> versionDates;

    /**
     * <p>This field represents the list of the technologies of the version.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptable region: any list including <code>null</code> and empty one, a non-empty list
     * containing <code>null</code> is legal as well.</p>
     */
    private List<Technology> technologies;

    /**
     * <p>
     * This field dependencies the list of the CompVersion.
     * </p>
     *
     * <p>
     * The initial value is <tt>null</tt>. Access is performed via its getter and setter.
     * </p>
     *
     * <p>
     * The acceptable region: any list including <code>null</code> and empty one,
     * a non-empty list containing <code>null</code> is legal as well.
     * </p>
     *
     * <p>
     *  <strong>Changes:</strong>
     *  A new field add in bug fix.
     * </p>
     */
    private List<CompVersion> dependencies;

    /**
     * <p>This field represents the list of the documents of the version.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptable region: any list including <code>null</code> and empty one, a non-empty list
     * containing <code>null</code> is legal as well.</p>
     * @since 1.1
     */
    private Set<CompDocumentation> documentation = new HashSet<CompDocumentation>(); // BUGR-1600

    /**
     * <p>Default constructor.</p> <p><em>Does nothing.</em></p>
     */
    public CompVersion() {
    }

    /**
     * <p>Sets a value to the {@link #id} field.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     *
     * @param id the id of the entity.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>Retrieves the id of the entity.</p>
     *
     * @return {@link #id} property's value.
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>Sets a value to the {@link #component} field.</p>
     * <p>The acceptance region: any <code>Component</code> instance or <tt>null</tt>.</p>
     *
     * @param component the component part of the entity.
     */
    public void setComponent(Component component) {
        this.component = component;
    }

    /**
     * <p>Retrieves the component part of the entity.</p>
     *
     * @return {@link #component} property's value.
     */
    public Component getComponent() {
        return component;
    }

    /**
     * <p>Sets a value to the {@link #version} field.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     *
     * @param version the version of the entity.
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * <p>Retrieves the version of the entity.</p>
     *
     * @return {@link #version} property's value.
     */
    public Long getVersion() {
        return version;
    }

    /**
     * <p>Sets a value to the {@link #versionText} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     *
     * @param versionText the text of the version.
     */
    public void setVersionText(String versionText) {
        this.versionText = versionText;
    }

    /**
     * <p>Retrieves the text of the version.</p>
     *
     * @return {@link #versionText} property's value.
     */
    public String getVersionText() {
        return versionText;
    }

    /**
     * <p>Sets a value to the {@link #phase} field.</p>
     * <p>The acceptance region: any <code>Phase</code> instance or <tt>null</tt>.</p>
     *
     * @param phase the phase of the component version.
     */
    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    /**
     * <p>Retrieves  the phase of the component version.</p>
     *
     * @return {@link #phase} property's value.
     */
    public Phase getPhase() {
        return phase;
    }

    /**
     * <p>Sets a value to the {@link #phaseTime} field.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     *
     * @param phaseTime the phase time of the version.
     */
    public void setPhaseTime(Date phaseTime) {
        this.phaseTime = phaseTime;
    }

    /**
     * <p>Retrieves the phase time of the version.</p>
     *
     * @return {@link #phaseTime} property's value.
     */
    public Date getPhaseTime() {
        return phaseTime;
    }

    /**
     * <p>Sets a value to the {@link #phasePrice} field.</p>
     * <p>The acceptance region: any <code>double</code> value.</p>
     *
     * @param phasePrice the the phase price of the version.
     */
    public void setPhasePrice(double phasePrice) {
        this.phasePrice = phasePrice;
    }

    /**
     * <p>Retrieves the the phase price of the version.</p>
     *
     * @return {@link #phasePrice} property's value.
     */
    public double getPhasePrice() {
        return phasePrice;
    }

    /**
     * <p>Sets a value to the {@link #comments} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     *
     * @param comments the comments of the version.
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * <p>Retrieves the comments of the version.</p>
     *
     * @return {@link #comments} property's value.
     */
    public String getComments() {
        return comments;
    }

    /**
     * <p>Sets a value to the {@link #suspended} field.</p>
     * <p>The acceptable region: <code>true</code> or <code>false</code>.</p>
     *
     * @param suspended whether the version is suspended or not.
     */
    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    /**
     * <p>Retrieves whether the version is suspended or not.</p>
     *
     * @return {@link #suspended} property's value.
     */
    public boolean isSuspended() {
        return suspended;
    }

    /**
     * <p>Sets a value to the {@link #forum} field.</p>
     * <p>The acceptance region: any <code>CompForum</code> instance or <tt>null</tt>.</p>
     *
     * @param forum the forum of the version.
     */
    public void setForum(CompForum forum) {
        this.forum = forum;
    }

    /**
     * <p>Retrieves the forum of the version.</p>
     *
     * @return {@link #forum} property's value.
     */
    public CompForum getForum() {
        return forum;
    }

    /**
     * <p>Sets a value to the {@link #link} field.</p>
     * <p>The acceptance region: any <code>CompLink</code> instance or <tt>null</tt>.</p>
     *
     * @param link the link of the version.
     */
    public void setLink(CompLink link) {
        this.link = link;
    }

    /**
     * <p>Retrieves the link of the version.</p>
     *
     * @return {@link #link} property's value.
     */
    public CompLink getLink() {
        return link;
    }

    /**
     * <p>Sets a value to the {@link #versionDates} field.</p>
     * <p>The acceptable region: any map including <code>null</code> and empty one, a non-empty map
     * containing <code>null</code> key or value is legal as well.</p>
     *
     * @param versionDates the mapping between dates and versions.
     */
    public void setVersionDates(Map<Long, CompVersionDates> versionDates) {
        this.versionDates = versionDates;
    }

    /**
     * <p>Retrieves the mapping between dates and versions.</p>
     *
     * @return {@link #versionDates} property's value.
     */
    public Map<Long, CompVersionDates> getVersionDates() {
        return versionDates;
    }

    /**
     * <p>Sets a value to the {@link #technologies} field.</p>
     * <p>The acceptable region: any list including <code>null</code> and empty one, a non-empty list
     * containing <code>null</code> is legal as well.</p>
     *
     * @param technologies the list of the technologies of the version.
     */
    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }

    /**
     * <p>Retrieves the list of the technologies of the version.</p>
     *
     * @return {@link #technologies} property's value.
     */
    public List<Technology> getTechnologies() {
        return technologies;
    }

    /**
     * <p>
     * Sets a value to the {@link #dependencies} field.
     * </p>
     *
     * @param dependencies the list of the technologies of the version.
     */
    public void setDependencies(List<CompVersion> dependencies) {
        this.dependencies = dependencies;
    }

    /**
     * <p>Retrieves the list of the dependencies of the version.</p>
     *
     * @return {@link #dependencies} property's value.
     */
    public List<CompVersion> getDependencies() {
        return dependencies;
    }

    /**
     * <p>Sets a value to the {@link #documentation} field.</p>
     * <p>The acceptable region: any list including <code>null</code> and empty one, a non-empty list
     * containing <code>null</code> is legal as well.</p>
     *
     * @param documentation the list of the documentation of the version.
     * @since 1.1
     */
    public void setDocumentation(Set<CompDocumentation> documentation) { // BUGR-1600
    	this.documentation.clear();
        this.documentation.addAll(documentation);
    }

    /**
     * <p>Retrieves the list of the documentation of the version.</p>
     *
     * @return {@link #documentation} property's value.
     * @since 1.1
     */
    public Set<CompDocumentation> getDocumentation() {
        return documentation;
    }
}

