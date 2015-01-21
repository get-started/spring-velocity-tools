package org.spring.velocity.view;

import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.ToolboxFactory;
import org.apache.velocity.tools.view.ServletUtils;
import org.apache.velocity.tools.view.ViewToolContext;
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
        ToolContext toolContext = new ViewPathContext(getServletContext());
        toolContext.putAll(model);
        exposingTools(toolContext, request, response);
        return toolContext;
    }

    /**
     * Exposing tools into ToolContext.
     *
     * @param toolContext
     * @param request
     * @param response
     */
    protected void exposingTools(ToolContext toolContext, HttpServletRequest request, HttpServletResponse response) {
        if (getToolboxConfigLocation() != null) {
            ToolManager toolManager = createToolManager(request, response);
            for (String scope : Scope.values()) {
                ToolboxFactory toolboxFactory = toolManager.getToolboxFactory();
                if (toolboxFactory.hasTools(scope)) {
                    toolContext.addToolbox(toolboxFactory.createToolbox(scope));
                }
            }
        }
    }

    protected ToolManager createToolManager(HttpServletRequest request, HttpServletResponse response) {
        ToolManager toolManager = new ToolManager();
        toolManager.setVelocityEngine(getVelocityEngine());
        toolManager.configure(ServletUtils.getConfiguration(getToolboxConfigLocation(), getServletContext()));
        return toolManager;
    }
}
