/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * <p>This class represents a component that can be <tt>application</tt>, <tt>assembly</tt>, <tt>testing</tt> or
 * <tt>component</tt>.</p>
 * <p>A <tt>component</tt> can have many versions and the current version stored in <code>currentVersion</code> field,
 * while others are stored in <code>versions</code> list.</p>
 * <p>It's important to be aware of fact that the current version is not necessarily the most recent one - it points
 * to a completed version (or to the first one if it's is the only one version of a component).</p>
 * <p>Also notice that the version field is just an "internal" field that will start from '1' and increase by '1' each
 * time. A user will see <code>CompVersion.versionText</code> where version numbers look like "1.1", "3.0" etc.</p>
 * <p>Each component has a root category, stored in <code>rootCategory</code> field.</p>
 * <p>A component also has a collection of categories. Each category should have
 * its root ancestor the same as the component has.</p>
 * <p>There can be some users (other than admins) authorized to view and change component data.
 * The users attribute provides a list of <code>CompUser</code> entities with the ids of the authorized users.</p>
 * <p>Also, there can be some clients authorized to change component data, represented by clients
 * list.</p>
 * <p>Validation of parameters is not performed in this class. It's supposed to be a caller's responsibility.</p>
 * <p>Example of usage:</p>
 * <pre>
 *       // create component
 *       final Component component = new Component();
 *       component.setDescription("Component description");
 *       component.setFunctionalDesc("Functional description");
 *       component.setName("Component name");
 *       component.setShortDesc("Short Desc.");
 *       component.setStatus(Status.NEW_POST);
 *       // create version
 *       final CompVersion version = createComponentVersion();
 *       version.setComponent(component);
 *
 *       // set the current version
 *       component.setCurrentVersion(version);
 *
 *       // set the root category (categories are in the database
 *       component.setRootCategory(getEntityManager().find(Category.class, 2L));
 *
 *       // assign categories which this component belongs to
 *       component.setCategories(Arrays.asList(
 *           getEntityManager().find(Category.class, 2L),
 *           getEntityManager().find(Category.class, 3L)));
 *
 *       // assign component clients
 *       final Set&lt;CompClient&gt; clients = populateClients(component);
 *       component.setClients(clients);
 *
 *       // assign component users
 *       final Set&lt;CompUser&gt; users = populateUsers(component);
 *       component.setUsers(users);
 *
 *       // start the transaction
 *       entityTransaction.begin();
 *       // persist the version first
 *       entityManager.persist(version);
 *       // persist the component, this will update version as well to bind it to the component
 *       entityManager.persist(component);
 *       // commit the transaction
 *       entityTransaction.commit();
 *
 *
 *       // remove component
 *       entityTransaction.begin(); // start transaction
 *       version.setComponent(null); // clear reference to component
 *       component.setVersions(null); // remove to versions associations
 *       component.setUsers(null); // remove to users associations
 *       component.setCategories(null); // remove to categories associations
 *       component.setClients(null); // remove to clients associations
 *       entityManager.merge(version); // update version without
 *       entityManager.remove(entityManager.getReference(Component.class, component.getId())); // remove component
 *       entityManager.remove(entityManager.getReference(CompVersion.class, version.getId())); // remove version
 *       entityTransaction.commit(); // commit finally
 *
 * </pre>
 * <p><strong>Thread safety: </strong></p> <p>This class is mutable and not thread safe.</p>
 *
 * @author caru, Retunsky
 * @version 1.0
 */
public class Component implements Serializable {
    /**
     * <p>This field represents the id of the entity.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private Long id;
    /**
     * <p>This field represents the name of the component.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String name;
    /**
     * <p>This field represents the status of the component.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Status</code> instance or <tt>null</tt>.</p>
     */
    private Status status;
    /**
     * <p>This field represents the short description of the component.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String shortDesc;
    /**
     * <p>This field represents the functional description of the component.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String functionalDesc;
    /**
     * <p>This field represents the description of the component.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String description;
    /**
     * <p>This field represents the root category of the component.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Category</code> instance or <tt>null</tt>.</p>
     */
    private Category rootCategory;
    /**
     * <p>This field represents the current version of the component.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>CompVersion</code> instance or <tt>null</tt>.</p>
     */
    private long currentVersionNumber;

	/**
     * <p>This field represents the current version of the component.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>CompVersion</code> instance or <tt>null</tt>.</p>
     */
    private CompVersion currentVersion;
    /**
     * <p>This field represents the list of the categories this component belongs to.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptable region: any list including <code>null</code> and empty one, a non-empty list
     * containing <code>null</code> is legal as well.</p>
     */
    private List<Category> categories;
    /**
     * <p>This field represents the set of the users of the component.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptable region: any set including <code>null</code> and empty one, a non-empty set
     * containing <code>null</code> is legal as well.</p>
     */
    private Set<CompUser> users;
    /**
     * <p>This field represents the set of the clients of the component.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptable region: any set including <code>null</code> and empty one, a non-empty set
     * containing <code>null</code> is legal as well.</p>
     */
    private Set<CompClient> clients;
    /**
     * <p>This field represents the list of the versions of the component.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptable region: any list including <code>null</code> and empty one, a non-empty list
     * containing <code>null</code> is legal as well.</p>
     */
    private List<CompVersion> versions;

	/**
     * <p>This field represents the list of the versions of the component.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptable region: any list including <code>null</code> and empty one, a non-empty list
     * containing <code>null</code> is legal as well.</p>
     */
    private Set<CompVersion> versionsSet;


    /**
     * <p>Default constructor.</p> <p><em>Does nothing.</em></p>
     */
    public Component() {
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
     * <p>Sets a value to the {@link #name} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     *
     * @param name the name of the component.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Retrieves the name of the component.</p>
     *
     * @return {@link #name} property's value.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Sets a value to the {@link #status} field.</p>
     * <p>The acceptance region: any <code>Status</code> instance or <tt>null</tt>.</p>
     *
     * @param status the status of the component.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * <p>Retrieves the status of the component.</p>
     *
     * @return {@link #status} property's value.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * <p>Sets a value to the {@link #shortDesc} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     *
     * @param shortDesc the short description of the component.
     */
    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    /**
     * <p>Retrieves the short description of the component.</p>
     *
     * @return {@link #shortDesc} property's value.
     */
    public String getShortDesc() {
        return shortDesc;
    }

    /**
     * <p>Sets a value to the {@link #functionalDesc} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     *
     * @param functionalDesc the functional description of the component.
     */
    public void setFunctionalDesc(String functionalDesc) {
        this.functionalDesc = functionalDesc;
    }

    /**
     * <p>Retrieves the functional description of the component.</p>
     *
     * @return {@link #functionalDesc} property's value.
     */
    public String getFunctionalDesc() {
        return functionalDesc;
    }

    /**
     * <p>Sets a value to the {@link #description} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     *
     * @param description the description of the component.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>Retrieves the description of the component.</p>
     *
     * @return {@link #description} property's value.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>Sets a value to the {@link #rootCategory} field.</p>
     * <p>The acceptance region: any <code>Category</code> instance or <tt>null</tt>.</p>
     *
     * @param rootCategory the root category of the component.
     */
    public void setRootCategory(Category rootCategory) {
        this.rootCategory = rootCategory;
    }

    /**
     * <p>Retrieves the root category of the component.</p>
     *
     * @return {@link #rootCategory} property's value.
     */
    public Category getRootCategory() {
        return rootCategory;
    }

    /**
     * <p>Sets a value to the {@link #currentVersion} field.</p>
     * <p>The acceptance region: any <code>CompVersion</code> instance or <tt>null</tt>.</p>
     *
     * @param currentVersion the current version of the component.
     */
    public void setCurrentVersion(CompVersion currentVersion) {
        this.currentVersion = currentVersion;
		if (currentVersion.getVersion() != null)
		{
			this.currentVersionNumber = currentVersion.getVersion();
		}
		
    }

    /**
     * <p>Retrieves the current version of the component.</p>
     *
     * @return {@link #currentVersion} property's value.
     */
    public long getCurrentVersionNumber() {
        return currentVersionNumber;
    }


	/**
     * <p>Sets a value to the {@link #currentVersion} field.</p>
     * <p>The acceptance region: any <code>CompVersion</code> instance or <tt>null</tt>.</p>
     *
     * @param currentVersion the current version of the component.
     */
    public void setCurrentVersionNumber(long currentVersionNumber) {
        this.currentVersionNumber = currentVersionNumber;
    }

    /**
     * <p>Retrieves the current version of the component.</p>
     *
     * @return {@link #currentVersion} property's value.
     */
    public CompVersion getCurrentVersion() {
		if (currentVersion != null)
		{
			return currentVersion;
		}

		if (getVersions() == null || getVersions().size() == 0)
		{
			return null;
		}

		for (Iterator<CompVersion> iterator = getVersions().iterator(); iterator.hasNext();) 
		{
            CompVersion cv = iterator.next();
			if ((cv != null) && (cv.getVersion() != null) && cv.getVersion() == currentVersionNumber) 
			{
				currentVersion = cv;
				return currentVersion;
			}
		}

		return null;
        
    }

    /**
     * <p>Sets a value to the {@link #categories} field.</p>
     * <p>The acceptable region: any list including <code>null</code> and empty one, a non-empty list
     * containing <code>null</code> is legal as well.</p>
     *
     * @param categories the list of the categories this component belongs to.
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * <p>Retrieves the list of the categories this component belongs to.</p>
     *
     * @return {@link #categories} property's value.
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * <p>Sets a value to the {@link #users} field.</p>
     * <p>The acceptable region: any set including <code>null</code> and empty one, a non-empty set
     * containing <code>null</code> is legal as well.</p>
     *
     * @param users the set of the users of the component.
     */
    public void setUsers(Set<CompUser> users) {
        this.users = users;
    }

    /**
     * <p>Retrieves the set of the users of the component.</p>
     *
     * @return {@link #users} property's value.
     */
    public Set<CompUser> getUsers() {
        return users;
    }

    /**
     * <p>Sets a value to the {@link #clients} field.</p>
     * <p>The acceptable region: any set including <code>null</code> and empty one, a non-empty set
     * containing <code>null</code> is legal as well.</p>
     *
     * @param clients the set of the clients of the component.
     */
    public void setClients(Set<CompClient> clients) {
        this.clients = clients;
    }

    /**
     * <p>Retrieves the set of the clients of the component.</p>
     *
     * @return {@link #clients} property's value.
     */
    public Set<CompClient> getClients() {
        return clients;
    }

    /**
     * <p>Sets a value to the {@link #versions} field.</p>
     * <p>The acceptable region: any set including <code>null</code> and empty one, a non-empty list
     * containing <code>null</code> is legal as well.  However, when constructing the result list,
     * this method will only include the non-<code>null</code> elements.  Furthermore, it will only
     * include the first occurrence of elements with the same version number.</p>
     *
     * @param versions the list of the versions of the component.
     */
    public void setVersions(List<CompVersion> versions) {
		if (versions != null)
		{
			 this.versions = versions;
			 this.versionsSet = new HashSet(versions);
		}
       
    }

    /**
     * <p>Retrieves the list of the versions of the component.</p>
     *
     * @return {@link #versions} property's value.
     */
    public List<CompVersion> getVersions() {

		if (versionsSet != null)
		{
			return new ArrayList(versionsSet);
		}
        //if (versions == null) return null;

        /*List<CompVersion> v = new ArrayList<CompVersion>();
        Set<Long> seenVersions = new HashSet<Long>();
        CompVersion cv;
        for (Iterator<CompVersion> iterator = versions.iterator(); iterator.hasNext();) {
            cv = iterator.next();
            if ((cv != null) && (cv.getVersion() != null) && !seenVersions.contains(cv.getVersion())) {
                seenVersions.add(cv.getVersion());
                v.add(cv);
            }
        }
        return v; */
		return null;
    }


	/**
     * <p>Sets a value to the {@link #versions} field.</p>
     * <p>The acceptable region: any set including <code>null</code> and empty one, a non-empty list
     * containing <code>null</code> is legal as well.  However, when constructing the result list,
     * this method will only include the non-<code>null</code> elements.  Furthermore, it will only
     * include the first occurrence of elements with the same version number.</p>
     *
     * @param versions the list of the versions of the component.
     */
    public void setVersionsSet(Set<CompVersion> versionsSet) {
        this.versionsSet = versionsSet;
		this.versions =  new ArrayList(versionsSet);
    }

    /**
     * <p>Retrieves the list of the versions of the component.</p>
     *
     * @return {@link #versions} property's value.
     */
    public Set<CompVersion> getVersionsSet() {
        if (versionsSet == null) return null;

        /*List<CompVersion> v = new ArrayList<CompVersion>();
        Set<Long> seenVersions = new HashSet<Long>();
        CompVersion cv;
        for (Iterator<CompVersion> iterator = versions.iterator(); iterator.hasNext();) {
            cv = iterator.next();
            if ((cv != null) && (cv.getVersion() != null) && !seenVersions.contains(cv.getVersion())) {
                seenVersions.add(cv.getVersion());
                v.add(cv);
            }
        }
        return v; */
		return versionsSet;
    }

}

