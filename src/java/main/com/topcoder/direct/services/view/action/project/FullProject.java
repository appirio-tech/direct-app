/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * This annotation can be annotated in the class or method level. It indicates that the struts
 * action will do permission related operation on the project. In direct, only the user resource
 * - has role TC staff
 * - has project permission (3L) - full permission
 *  permission_type_id	name
 *   3	                project_full
 * - has project group permission FULL
 *
 * </p>
 * <p>
 * This annotation will be consumed by <code>ProjectAccessInterceptor</code>.
 * </p>
 *
 * @version 1.0
 */
@Target({
        ElementType.METHOD,
        ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface FullProject {

}
