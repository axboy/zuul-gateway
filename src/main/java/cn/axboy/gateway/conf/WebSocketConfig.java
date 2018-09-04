package cn.axboy.gateway.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * 作者 zcw
 * 时间 2017/8/16 23:07
 * 描述 webSocket配置
 */
@Configuration
@EnableWebSocket    //对应实现的WebSocketConfigurer接口
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer implements WebSocketConfigurer {

    @Autowired
    private MyTextWsHandler myTextWsHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stompEndpoint")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // super.configureMessageBroker(registry);
        registry.enableSimpleBroker("/route");
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myTextWsHandler, "socket/route/logs").setAllowedOrigins("*");
    }
}
