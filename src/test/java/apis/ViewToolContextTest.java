package apis;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.view.ViewToolContext;
import org.apache.velocity.tools.view.ViewToolManager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * Created by L.x on 15-1-26.
 */
public class ViewToolContextTest {

    public static final String REQUEST_TOOL_KEY = "requestTool";
    private MockHttpServletRequest request;
    private ToolManager manager;

    @Before
    public void setUp() throws Exception {
        request = new MockHttpServletRequest();
        manager = new ToolManager(false);
        manager.configure("/tools.xml");
    }

    @Test
    public void injectToolProperties() throws Exception {
        ToolContext toolContext = manager.createContext(new HashMap<String, Object>() {{
            put("request", request);
        }});

        RequestTool requestTool = (RequestTool) toolContext.getToolbox().get(REQUEST_TOOL_KEY);
        assertThat(requestTool.request, sameInstance((Object) request));
    }

    @Test
    public void injectToolPropertiesByCustomToolContext() throws Exception {
        ViewToolContext toolContext = new ViewToolContext(new VelocityEngine(), request, new MockHttpServletResponse(), new MockServletContext());

        toolContext.addToolbox(manager.getToolboxFactory().createToolbox(Scope.REQUEST));

        RequestTool requestTool = (RequestTool) toolContext.getToolbox().get(REQUEST_TOOL_KEY);
        assertThat(requestTool.request, sameInstance((Object) request));
    }

    /**
     * Created by L.x on 15-1-26.
     */
    public static class RequestTool {
        private HttpServletRequest request;


        public void setRequest(HttpServletRequest request) {
            this.request = request;
        }
    }
}
