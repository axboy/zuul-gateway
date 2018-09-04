package cn.axboy.gateway.filter;

import cn.axboy.gateway.conf.MyTextWsHandler;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 作者 zcw
 * 时间 2017/8/16 23:14
 * 描述 路由过滤器
 */
@Component
public class RouteStateFilter extends ZuulFilter {

    @Autowired
    private MyTextWsHandler myTextWsHandler;

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 50;
    }


    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        myTextWsHandler.sendToAll(getRouteLog());
        return null;
    }

    private RouteLog getRouteLog() {
        RequestContext ctx = RequestContext.getCurrentContext();
        final HttpServletRequest request = ctx.getRequest();

        RouteLog log = new RouteLog();
        log.setRequestUrl(request.getRequestURL().toString());
        log.setMethod(request.getMethod());
        log.setParams(request.getParameterMap());
        log.setServerAddr(request.getLocalAddr());
        log.setServerPort(request.getServerPort());
        log.setRequestURI(request.getRequestURI());
        log.setResponse(null);
        return log;
    }

    @Data
    private static class RouteLog {
        private String requestUrl;
        private String method;
        private Map<String, String[]> params;
        private String serverAddr;
        private int serverPort;
        private String requestURI;
        private String response;
    }
}
