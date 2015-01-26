package org.spring.velocity.view;

import org.apache.velocity.context.Context;
import org.apache.velocity.tools.*;
import org.apache.velocity.tools.view.ServletUtils;
import org.apache.velocity.tools.view.ViewContext;
import org.springframework.web.servlet.view.velocity.VelocityToolboxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by L.x on 15-1-19.
 */
public class VelocityToolbox2View extends VelocityToolboxView {
    private ToolManager toolManager;

    @Override
    protected Context createVelocityContext(final Map<String, Object> model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        ToolContext context = getToolManager().createContext(createToolsProps(model, request, response));
        context.putAll(model);
        return context;
    }

    private HashMap<String, Object> createToolsProps(final Map<String, Object> model, final HttpServletRequest request, final HttpServletResponse response) {
        return new HashMap<String, Object>() {{
            putAll(model);
            put(ViewContext.REQUEST, request);
            put(ViewContext.RESPONSE, response);
        }};
    }

    protected ToolManager createToolManager() {
        ToolManager toolManager = new PathViewToolManager();
        toolManager.setVelocityEngine(getVelocityEngine());
        if (getToolboxConfigLocation() != null) {
            toolManager.configure(ServletUtils.getConfiguration(getToolboxConfigLocation(), getServletContext()));
        }
        return toolManager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        setToolManager(createToolManager());
    }

    public ToolManager getToolManager() {
        return toolManager;
    }

    private void setToolManager(ToolManager toolManager) {
        this.toolManager = toolManager;
    }

    private class PathViewToolManager extends ToolManager {

        public PathViewToolManager() {
            super(false);
        }

        @Override
        public ToolContext createContext(Map<String, Object> toolProps) {
            HttpServletRequest request = (HttpServletRequest) toolProps.get(ViewContext.REQUEST);
            HttpServletResponse response = (HttpServletResponse) toolProps.get(ViewContext.RESPONSE);

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
