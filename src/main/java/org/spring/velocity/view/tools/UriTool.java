package org.spring.velocity.view.tools;

import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.InvalidScope;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by L.x on 15-1-26.
 */
@DefaultKey("uri")
@InvalidScope(Scope.REQUEST)
public class UriTool {
    private HttpServletRequest request;

    public String of(String path) {
        return baseUrl() + uri(path);
    }

    private String baseUrl() {
        return request.getScheme() + "://" + request.getServerName() + request.getContextPath() + serverPort();
    }

    private String uri(String path) {
        if (path.startsWith("/")) {
            return path;
        }
        String currentUri = request.getRequestURI();
        int pos = currentUri.lastIndexOf('/');
        String parent = pos != -1 ? currentUri.substring(0, pos + 1) : "/";
        return parent + path;
    }

    private String serverPort() {
        return usingDefaultPort() ? "" : ":" + request.getServerPort();
    }

    private boolean usingDefaultPort() {
        if ("http".equals(request.getScheme()) && request.getServerPort() == 80) {
            return true;
        }
        return "https".equals(request.getScheme()) && request.getServerPort() == 443;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
