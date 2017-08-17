package cn.wazitang.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 作者 zcw
 * 时间 2017/8/16 23:14
 * 描述 TODO
 */
@Component
public class RouteStateFilter extends ZuulFilter {
    @Override
    public String filterType() {
        System.out.println(">>>filterType");
        return "pre";
    }

    @Override
    public int filterOrder() {
        System.out.println(">>>filterOrder");
        return 50;
    }


    @Override
    public boolean shouldFilter() {
        System.out.println(">>>shouldFilter");
        return true;
    }


    @Override
    public Object run() {
        System.out.println("run");
        return routeLog("str" + new Random().nextInt());
    }


    @SendTo("/route/log")
    public String routeLog(String str) {
        return str;
    }
}
