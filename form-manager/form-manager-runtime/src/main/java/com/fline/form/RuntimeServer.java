//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.fline.form;

import java.util.Properties;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.core.io.ClassPathResource;

final class RuntimeServer {
    private final String propertyPath = "applicationContext.properties";
    private int port;
    private String contextPath;
    private String host = "127.0.0.1";
    private String warApp = "src/main/webapp";

    void start() throws Exception {
        long begin = System.nanoTime();
        Server server = new Server();
        ClassPathResource classPathResource = new ClassPathResource(this.propertyPath);
        Properties properties = new Properties();
        properties.load(classPathResource.getInputStream());
        this.port = Integer.parseInt(properties.getProperty("http.port"));
        this.contextPath = properties.getProperty("http.context.path");

        Connector connector = new SelectChannelConnector();
        connector.setPort(Integer.getInteger("jetty.port", this.port));
        server.setConnectors(new Connector[]{connector});
        WebAppContext webAppContext = new WebAppContext(this.warApp, this.contextPath);
        webAppContext.setDefaultsDescriptor("webdefault.xml");
        server.setHandler(webAppContext);
        this.host = connector.getHost() == null ? this.host : connector.getHost();

        try {
            server.start();
            String url = "http://" + this.host + ":" + this.port + this.contextPath;
            System.out.println("[Jetty Server started in " + (System.nanoTime() - begin) / 1000L / 1000L / 1000L + "s]: " + url);
            server.join();
        } catch (Exception var7) {
            var7.printStackTrace();
            System.exit(100);
        }

    }
}
