package org.spring.velocity.view;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockServletContext;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by L.x on 15-1-21.
 */
public class ViewPathContextTest {

    private ViewPathContext context;

    @Before
    public void setUp() throws Exception {
        context = new ViewPathContext(new MockServletContext() {{
            setContextPath("/public");
        }});
    }

    private void put(String name, String value) {
        context.put(name, value);
    }

    private void assertVariable(String name, Matcher matcher) {
        assertThat(context.get(name), matcher);
    }

    @Test
    public void exposeContextPath() throws Exception {
        assertVariable("path", equalTo("/public"));
    }

    @Test
    public void exposePathRelativeToContext() throws Exception {
        assertVariable("path_assets_css", equalTo("/public/assets/css"));
        assertVariable("path_js", equalTo("/public/js"));
    }

    @Test
    public void returnNullIfVariableMissing() throws Exception {
        assertVariable("missingVariable", nullValue());
    }

    @Test
    public void overridePathVariable() throws Exception {
        put("path_assets_css", "/assets/css");

        assertVariable("path_assets_css", equalTo("/assets/css"));
    }

    @Test
    public void savingPathAfterFetching() throws Exception {
        context.get("path_assets_css");

        assertThat(context.getKeys(), equalTo(new Object[]{"path_assets_css"}));
    }
}
