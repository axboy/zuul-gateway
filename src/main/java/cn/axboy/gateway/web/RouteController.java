package cn.axboy.gateway.web;

import cn.axboy.gateway.domain.CustomRoute;
import cn.axboy.gateway.repo.CustomRouteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "route", produces = MediaType.APPLICATION_JSON_VALUE)
public class RouteController {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private RouteLocator routeLocator;

    @Autowired
    private CustomRouteRepo customRouteRepo;

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public UnifyResp<String> refresh() {
        this.realRefresh();
        return new UnifyResp<>(200, "SUCCESS");
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public UnifyResp<String> save(@RequestBody RouteSaveReq req) {
        CustomRoute route = new CustomRoute();
        if (req.getId() != null) {
            route = customRouteRepo.findOne(req.getId());
        }
        route.setApiName(req.getApiName());
        route.setPath(req.getPath());
        route.setLocation(req.getUrl());
        route.setStripPrefix(req.getStripPrefix());
        route.setMemo(req.getMemo());
        route.setEnabled(true);
        route.setRetryAble(false);
        customRouteRepo.save(route);
        this.realRefresh();
        return new UnifyResp<>(200, "SUCCESS");
    }

    @RequestMapping(value = "page", method = RequestMethod.GET)
    public UnifyResp<Page<CustomRoute>> page(Integer page, Integer size) {
        Page<CustomRoute> resp = customRouteRepo.findAll(new PageRequest(page != null ? page : 0, size != null ? size : 20,
                new Sort(new Sort.Order(Sort.Direction.DESC, "id"))));
        return new UnifyResp<>(200, resp);
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public UnifyResp<String> delete(Long id) {
        customRouteRepo.delete(id);
        return new UnifyResp<>(200, "SUCCESS");
    }

    private void realRefresh() {
        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
        publisher.publishEvent(routesRefreshedEvent);
    }
}
