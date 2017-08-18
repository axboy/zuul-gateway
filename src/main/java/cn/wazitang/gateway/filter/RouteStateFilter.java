package cn.wazitang.gateway.filter;

import cn.wazitang.gateway.conf.MyTextWsHandler;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * 作者 zcw
 * 时间 2017/8/16 23:14
 * 描述 路由过滤器
 */
@Component
public class RouteStateFilter extends ZuulFilter {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

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
        //FIXME @SendTo 不可用
        //发送给所有订阅了/route/log的用户
        messagingTemplate.convertAndSend("/route/log", getRouteLog());
        myTextWsHandler.sendToAll(getRouteLog());
        return null;
    }

    private RouteLog getRouteLog() {
        RequestContext ctx = RequestContext.getCurrentContext();
        final HttpServletRequest request = ctx.getRequest();
//        BufferedReader br = new BufferedReader(new InputStreamReader(ctx.getResponseDataStream()));
//        String resp = null;
//        try {
//            resp = br.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
