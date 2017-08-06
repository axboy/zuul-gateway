package cn.wazitang.gateway.conf;

import cn.wazitang.gateway.zuul.MyRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyZuulConfig {

    @Autowired
    private ZuulProperties zuulProperties;

    @Autowired
    private ServerProperties server;

    @Bean
    public MyRouteLocator routeLocator() {
        return new MyRouteLocator(this.server.getServletPrefix(), this.zuulProperties);
    }
}
