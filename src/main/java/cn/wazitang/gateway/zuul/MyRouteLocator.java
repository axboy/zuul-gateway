package cn.wazitang.gateway.zuul;

import cn.wazitang.gateway.repo.CustomRouteRepo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    @Autowired
    private CustomRouteRepo customRouteRepo;

    /**
     * 构造函数，必须有
     *
     * @param servletPath;
     * @param properties;
     */
    public MyRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
    }

    @Override
    public void refresh() {
        super.doRefresh();
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
        //routesMap.putAll(super.locateRoutes());
        routesMap.putAll(this.getRouteFromDb());
        return routesMap;
    }

    /**
     * 从数据库获取路由信息
     */
    private Map<String, ZuulRoute> getRouteFromDb() {
        Map<String, ZuulRoute> routes = new LinkedHashMap<>();
        customRouteRepo.findAll().forEach(res -> {
            if (StringUtils.isBlank(res.getPath()) || StringUtils.isBlank(res.getUrl())) {
                return;
            }
            ZuulRoute zuulRoute = new ZuulRoute();
            zuulRoute.setId(res.getRouteId());
            zuulRoute.setPath(res.getPath());
            zuulRoute.setServiceId(res.getServiceId());
            zuulRoute.setUrl(res.getUrl());
            zuulRoute.setStripPrefix(res.isStripPrefix());
            zuulRoute.setRetryable(res.getRetryAble());
            routes.put(zuulRoute.getPath(), zuulRoute);
        });
        return routes;
    }
}
