package org.spring.velocity.view;

import org.apache.velocity.tools.ToolContext;

import javax.servlet.ServletContext;

/**
 * Created by L.x on 15-1-21.
 */
public class ViewPathContext extends ToolContext {

    public static final String ROOT_PATH_NAME = "path";
    public static final String PATH_PREFIX = ROOT_PATH_NAME + "_";
    private String contextPath;

    public ViewPathContext(ServletContext servletContext) {
        contextPath = servletContext.getContextPath();
    }

    @Override
    public Object get(String key) {
        Object value = super.get(key);
        if (value == null && isPathVariable(key)) {
            String path = uri(key);
            put(key, path);
            return path;
        }
        return value;
    }

    private boolean isPathVariable(String key) {
        return ROOT_PATH_NAME.equals(key) || key.startsWith(PATH_PREFIX);
    }

    private String uri(String key) {
        if (ROOT_PATH_NAME.equals(key)) {
            return contextPath;
        }
        return contextPath + key.substring(ROOT_PATH_NAME.length()).replace('_', '/');
    }
}
