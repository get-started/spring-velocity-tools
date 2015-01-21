package org.spring.velocity.view;

import org.apache.velocity.tools.ToolContext;

import javax.servlet.ServletContext;

/**
 * Created by L.x on 15-1-21.
 */
public class ViewPathContext extends ToolContext {

    public static final String PATH_PREFIX = "path_";
    private String contextPath;

    public ViewPathContext(ServletContext servletContext) {
        contextPath = servletContext.getContextPath();
    }

    @Override
    public Object get(String key) {
        Object value = super.get(key);
        if (value == null && key.startsWith(PATH_PREFIX)) {
            String path = uri(key);
            put(key, path);
            return path;
        }
        return value;
    }

    private String uri(String uri) {
        return contextPath + uri.substring(4).replace('_', '/');
    }
}
