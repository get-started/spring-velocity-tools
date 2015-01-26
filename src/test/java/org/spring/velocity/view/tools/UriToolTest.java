package org.spring.velocity.view.tools;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by L.x on 15-1-26.
 */
public class UriToolTest {

    private UriTool uriTool;
    private MockHttpServletRequest request;

    @Before
    public void setUp() throws Exception {
        uriTool = new UriTool();
        request = new MockHttpServletRequest();
        request.setServerName("localhost");
        request.setServerPort(80);
        uriTool.setRequest(request);
    }

    @Test
    public void absoluteUri() throws Exception {
        assertThat(uriTool.of("/"), equalTo("http://localhost/"));
        assertThat(uriTool.of("/app/index.html"), equalTo("http://localhost/app/index.html"));
    }

    @Test
    public void relativeUri() throws Exception {
        request.setRequestURI("/user/show");

        assertThat(uriTool.of("edit"), equalTo("http://localhost/user/edit"));
    }

    @Test
    public void relativeUriOfRoot() throws Exception {
        request.setRequestURI("");

        assertThat(uriTool.of("edit"), equalTo("http://localhost/edit"));
    }

    @Test
    public void exposeContextPath() throws Exception {
        request.setContextPath("/weixin");

        assertThat(uriTool.of("edit"), equalTo("http://localhost/weixin/edit"));
    }

    @Test
    public void hide80PortForHttpProtocol() throws Exception {
        assertThat(uriTool.of("/"), equalTo("http://localhost/"));
    }

    @Test
    public void show443PortForHttpProtocol() throws Exception {
        request.setServerPort(443);

        assertThat(uriTool.of("/"), equalTo("http://localhost:443/"));
    }

    @Test
    public void hide443PortForHttpsProtocol() throws Exception {
        request.setScheme("https");
        request.setServerPort(443);

        assertThat(uriTool.of("/"), equalTo("https://localhost/"));
    }

    @Test
    public void show80PortForHttpsProtocol() throws Exception {
        request.setScheme("https");

        assertThat(uriTool.of("/"), equalTo("https://localhost:80/"));
    }
}
