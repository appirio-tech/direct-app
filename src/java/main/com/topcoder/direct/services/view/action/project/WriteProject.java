/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * This annotation can be annotated in the class or method level. It indicates that the struts
 * action will modify/save the existing direct project. So the interceptor will check the write
 * permission before allowing the action to be executed.
 * </p>
 * <p>
 * This annotation will be consumed by <code>ProjectAccessInterceptor</code>.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 * @since Release Assembly - TopCoder Security Groups Release 5
 */
@Target({
    ElementType.METHOD,
    ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface WriteProject {
}
