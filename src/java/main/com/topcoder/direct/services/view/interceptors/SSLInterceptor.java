package com.topcoder.direct.services.view.interceptors;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.topcoder.shared.util.logging.Logger;
import org.apache.struts2.StrutsStatics;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SSL interceptor for changing HTTP request to HTTPS request.
 *
 * @author hohosky
 * @version 1.0
 */
public class SSLInterceptor extends AbstractInterceptor {


    // configure the logger for this class
    private static final Logger logger = Logger.getLogger(SSLInterceptor.class);


    final static int LOCAL_HTTPS_PORT = 8543;

    final static int PROD_HTTPS_PORT = 430;

    final static String HTTP_GET = "GET";
    final static String HTTP_POST = "POST";
    final static String SCHEME_HTTP = "http";
    final static String SCHEME_HTTPS = "https";

    private String env = "production";

    public SSLInterceptor() {
        super();
        logger.info("Setup SSLInterceptor");
    }

    /**
     * Redirect to SSL or non-SSL version of page as indicated by the presence (or absence) of the
     *
     * @Secure annotation on the action class.
     */
    public String intercept(ActionInvocation invocation) throws Exception {

        // initialize request and response
        final ActionContext context = invocation.getInvocationContext();
        final HttpServletRequest request =
                (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
        final HttpServletResponse response =
                (HttpServletResponse) context.get(StrutsStatics.HTTP_RESPONSE);


        // check scheme
        String scheme = request.getScheme().toLowerCase();

        // check method
        String method = request.getMethod().toUpperCase();

        logger.info("scheme:" + scheme);
        logger.info("method:" + method);

        int httpsPort = PROD_HTTPS_PORT;

        if ((HTTP_GET.equals(method) || HTTP_POST.equals(method)) && SCHEME_HTTP.equals(scheme)) {
            if(env.equalsIgnoreCase("local")) {
                httpsPort = LOCAL_HTTPS_PORT;
            }
            URI uri = new URI(SCHEME_HTTPS, null, request.getServerName(),
                    httpsPort, response.encodeRedirectURL(request.getRequestURI()),
                    buildQueryString(request), null);

            logger.info("Using https... Redirecting to " + uri.toString());

            response.sendRedirect(uri.toString());
            return null;
        }
        return invocation.invoke();
    }

    @Inject(value = "struts.topcoder.env", required = false)
    public void setSSLEnv(String sslEnv) {
        this.env = sslEnv;
    }

    private static String buildQueryString(HttpServletRequest request) {
        String queryString = request.getQueryString();
        StringBuffer query = new StringBuffer();

        if (queryString != null && queryString.length() != 0) {
            query.append(queryString);
        } else {
            queryString = getRequestParameters(request);
            if (queryString != null && queryString.length() != 0) {
                query.append(queryString);
            }
        }
        return query.length() == 0 ? null : query.toString();
    }


    private static String getRequestParameters(HttpServletRequest aRequest) {
        return createQueryStringFromMap(aRequest.getParameterMap(), "&").toString();
    }

    private static StringBuffer createQueryStringFromMap(Map m, String ampersand) {
        StringBuffer aReturn = new StringBuffer("");
        Set aEntryS = m.entrySet();
        Iterator aEntryI = aEntryS.iterator();
        while (aEntryI.hasNext()) {
            Map.Entry aEntry = (Map.Entry) aEntryI.next();
            Object value = aEntry.getValue();
            String[] aValues = new String[1];
            if (value == null) {
                aValues[0] = "";
            } else if (value instanceof List) {
                List aList = (List) value;
                aValues = (String[]) aList.toArray(new String[aList.size()]);
            } else if (value instanceof String) {
                aValues[0] = (String) value;
            } else {
                aValues = (String[]) value;
            }
            for (int i = 0; i < aValues.length; i++) {

                append(aEntry.getKey(), aValues[i], aReturn, ampersand);
            }
        }
        return aReturn;
    }

    private static StringBuffer append(Object key, Object value, StringBuffer queryString, String ampersand) {
        if (queryString.length() > 0) {
            queryString.append(ampersand);
        }
        try {
            queryString.append(URLEncoder.encode(key.toString(), "UTF-8"));
            queryString.append("=");
            queryString.append(URLEncoder.encode(value.toString(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {

        }
        return queryString;
    }
}
