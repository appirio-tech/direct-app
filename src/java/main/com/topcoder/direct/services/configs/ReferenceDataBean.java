package com.topcoder.direct.services.configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.service.facade.contest.ContestServiceFacade;

public class ReferenceDataBean implements InitializingBean {
    /**
     * <p>
     * Represents the service used to performing the creation or update of the contest.
     * </p>
     */
    private ContestServiceFacade contestServiceFacade;

    /**
     * <p>
     * The technologies.
     * </p>
     */
    private List<Technology> technologies;

    /**
     * <p>
     * The technology map.
     * </p>
     */
    private Map<Long, Technology> technologyMap;

    /**
     * <p>
     * The categories.
     * </p>
     */
    private List<Category> categories;

    /**
     * <p>
     * The category map.
     * </p>
     */
    private Map<Long, Category> categoryMap;

    /**
     * <p>
     * Not set catalog.
     * </p>
     */
    private Category notSetCatalog;

    /**
     * <p>
     * Not set category.
     * </p>
     */
    private Category notSetCategory;

    /**
     * <p>
     * Studio catalog.
     * </p>
     */
    private Category studioCatalog;

    /**
     * <p>
     * Studio category.
     * </p>
     */
    private Category studioCategory;

    /**
     * <p>
     * Application catalog.
     * </p>
     */
    private Category applicationCatalog;

    /**
     * <p>
     * Business layer application category.
     * </p>
     */
    private Category businessLayerApplicationCategory;

    /**
     * <p>
     * Catalogs.
     * </p>
     */
    private List<Category> catalogs;

    /**
     * <p>
     * Catalog to categories map.
     * </p>
     */
    private Map<Long, List<Category>> catalogToCategoriesMap;

    /**
     * <p>
     * Gets the contest service facade.
     * </p>
     *
     * @return the contest service facade
     */
    public ContestServiceFacade getContestServiceFacade() {
        return contestServiceFacade;
    }

    /**
     * <p>
     * Sets the contest service facade.
     * </p>
     *
     * @param contestServiceFacade the contest service facade to set
     * @throws IllegalArgumentException if <b>contestServiceFacade</b> is <code>null</code>
     */
    public void setContestServiceFacade(ContestServiceFacade contestServiceFacade) {
        this.contestServiceFacade = contestServiceFacade;
    }

    /**
     * <p>
     * Gets the technologies.
     * </p>
     *
     * @return the technologies
     */
    public List<Technology> getTechnologies() {
        return technologies;
    }

    /**
     * <p>
     * Gets technology map.
     * </p>
     *
     * @return technology map
     */
    public Map<Long, Technology> getTechnologyMap() {
        return technologyMap;
    }

    /**
     * <p>
     * Gets the categories.
     * </p>
     *
     * @return the categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * <p>
     * Gets category map.
     * </p>
     *
     * @return category map.
     */
    public Map<Long, Category> getCategoryMap() {
        return categoryMap;
    }

    /**
     * <p>
     * Gets not set catalog.
     * </p>
     *
     * @return not set catalog
     */
    public Category getNotSetCatalog() {
        return notSetCatalog;
    }

    /**
     * <p>
     * Gets Studio category.
     * </p>
     *
     * @return studio category
     */
    public Category getStudioCategory() {
        return studioCategory;
    }

    /**
     * <p>
     * Gets studio catalog.
     * </p>
     *
     * @return studio catalog
     */
    public Category getStudioCatalog() {
        return studioCatalog;
    }

    /**
     * <p>
     * Gets not set category.
     * </p>
     *
     * @return not set category
     */
    public Category getNotSetCategory() {
        return notSetCategory;
    }

    /**
     * <p>
     * Gets the application catalog.
     * </p>
     *
     * @return the application catalog
     */
    public Category getApplicationCatalog() {
        return applicationCatalog;
    }

    /**
     * <p>
     * Gets business layer application category.
     * </p>
     *
     * @return business layer application category
     */
    public Category getBusinessLayerApplicationCategory() {
        return businessLayerApplicationCategory;
    }

    /**
     * <p>
     * Gets catalogs.
     * </p>
     *
     * @return catalogs
     */
    public List<Category> getCatalogs() {
        return catalogs;
    }

    /**
     * <p>
     * Gets catalog to categories map.
     * </p>
     *
     * @return catalog to categories map
     */
    public Map<Long, List<Category>> getCatalogToCategoriesMap() {
        return catalogToCategoriesMap;
    }

    /**
     * <p>
     * Initialization function. It will be called by Spring context.
     * </p>
     *
     * @throws Exception if any error happens
     */
    public void afterPropertiesSet() throws Exception {
        final String APPLICATION_DESCR = "Application";
        final String BUSINESS_LAYER_DESCR = "Business Layer";
        final String NOT_SET_DESCR = "Not Set";
        final String STUDIO_DESCR = "Studio";

        // technologies
        technologies = getContestServiceFacade().getActiveTechnologies(null);

        technologyMap = new HashMap<Long, Technology>();
        for (Technology technology : technologies) {
            technologyMap.put(technology.getId(), technology);
        }

        // categories
        categories = new ArrayList<Category>();
        categoryMap = new HashMap<Long, Category>();
        catalogs = new ArrayList<Category>();
        catalogToCategoriesMap = new HashMap<Long, List<Category>>();
        for (Category category : getContestServiceFacade().getActiveCategories(null)) {
            if (category.isViewable()) {
                continue;
            }

            System.out.println("CATEGORY catalog name = " + category.getCatalogName() +" ;" +
            		" descr = " + category.getDescription() + " ; name = " + category.getName());
            categories.add(category);
            categoryMap.put(category.getId(), category);

            if (StringUtils.isNotBlank(category.getCatalogName())) {
                catalogs.add(category);

                if (category.getName().equals(APPLICATION_DESCR)) {
                    applicationCatalog = category;
                } else if (category.getName().equals(NOT_SET_DESCR)) {
                    notSetCatalog = category;
                    catalogs.remove(category);
                } else if (category.getName().equals(STUDIO_DESCR)) {
                    studioCatalog = category;
                }
            } else if (category.getParentCategory() != null) {
                long catalogId = category.getParentCategory().getId();
                if (catalogToCategoriesMap.get(catalogId) == null) {
                    catalogToCategoriesMap.put(catalogId, new ArrayList<Category>());
                }
                catalogToCategoriesMap.get(catalogId).add(category);

                if (category.getName().equals(BUSINESS_LAYER_DESCR)
                    && category.getParentCategory().getName().equals(APPLICATION_DESCR)) {
                    businessLayerApplicationCategory = category;
                } else if (category.getName().equals(NOT_SET_DESCR)
                    && category.getParentCategory().getName().equals(NOT_SET_DESCR)) {
                    notSetCategory = category;
                } else if (category.getName().equals(STUDIO_DESCR)) {
                    studioCategory = category;
                }
            }
        }
    }

}
