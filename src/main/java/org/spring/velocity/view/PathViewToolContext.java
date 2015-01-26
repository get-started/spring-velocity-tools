package org.spring.velocity.view;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.view.ViewToolContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by L.x on 15-1-21.
 */
public class PathViewToolContext extends ViewToolContext {

    public static final String ROOT_PATH_NAME = "path";
    public static final String PATH_PREFIX = ROOT_PATH_NAME + "_";
    private String contextPath;

    public PathViewToolContext(VelocityEngine velocity, HttpServletRequest request, HttpServletResponse response, ServletContext application) {
        super(velocity, request, response, application);
        contextPath = application.getContextPath();
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
