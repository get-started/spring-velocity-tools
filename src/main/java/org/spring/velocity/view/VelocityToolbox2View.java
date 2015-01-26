package org.spring.velocity.view;

import org.apache.velocity.context.Context;
import org.apache.velocity.tools.*;
import org.apache.velocity.tools.view.ServletUtils;
import org.springframework.web.servlet.view.velocity.VelocityToolboxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by L.x on 15-1-19.
 */
public class VelocityToolbox2View extends VelocityToolboxView {
    @Override
    protected Context createVelocityContext(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ToolContext context = createToolManager(request, response).createContext(model);
        context.putAll(model);
        return context;
    }

    protected ToolManager createToolManager(HttpServletRequest request, HttpServletResponse response) {
        ToolManager toolManager = new PathViewToolManager(request, response);
        toolManager.setVelocityEngine(getVelocityEngine());
        if (getToolboxConfigLocation() != null) {
            toolManager.configure(ServletUtils.getConfiguration(getToolboxConfigLocation(), getServletContext()));
        }
        return toolManager;
    }

    private class PathViewToolManager extends ToolManager {
        private HttpServletRequest request;
        private HttpServletResponse response;

        public PathViewToolManager(HttpServletRequest request, HttpServletResponse response) {
            super(false);
            this.request = request;
            this.response = response;
        }

        @Override
        public ToolContext createContext(Map<String, Object> toolProps) {
            ToolContext toolContext = new PathViewToolContext(getVelocityEngine(), request, response, getServletContext());
            prepareContext(toolContext);
            return toolContext;
        }

        @Override
        protected void addToolboxes(ToolContext context) {
            if (hasRequestTools()) {
                context.addToolbox(getRequestToolbox());
            }
            if (hasSessionTools()) {
                context.addToolbox(getSessionToolbox());
            }
            if (hasApplicationTools()) {
                context.addToolbox(getApplicationToolbox());
            }
        }

        protected Toolbox getSessionToolbox() {
            return getToolboxFactory().createToolbox(Scope.SESSION);
        }

        protected boolean hasSessionTools() {
            return hasTools(Scope.SESSION);
        }
    }
}
