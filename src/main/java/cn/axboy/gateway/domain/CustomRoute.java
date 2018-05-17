package cn.axboy.gateway.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 自定义路由
 *
 * @see org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute
 */
@Data
@Table
@Entity
public class CustomRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * 自定义的api名
     */
    private String apiName;

    /**
     * 是否可用，未使用
     */
    private Boolean enabled;

    /**
     * 备注，说明
     */
    private String memo;

    /**
     * 路由id，path去除{"/", "/*", "*"}<br/>
     * 参考：ZuulRoute.extractId(path)
     */
    private String routeId;

    /**
     * 匹配的路径<br/>
     * 例如：/api/**
     */
    private String path;

    /**
     * eureka服务id，这里未启用注册中心，无用<br/>
     * 例如：service-user
     */
    private String serviceId;

    /**
     * 映射的url，必须以http:、https:开头
     */
    private String url;

    /**
     * 删除前缀
     */
    private boolean stripPrefix = true;

    /**
     * 失败自动重试
     */
    private Boolean retryAble;

    /**
     * 设置url
     *
     * @param location
     */
    public void setLocation(String location) {
        if (location == null || !location.startsWith("http:") && !location.startsWith("https:")) {
            this.serviceId = location;
        } else {
            this.url = location;
        }
    }

    public void setPath(String path) {
        this.path = path;
        this.routeId = extractId(path);
    }

    private String extractId(String path) {
        path = path.startsWith("/") ? path.substring(1) : path;
        path = path.replace("/*", "").replace("*", "");
        return path;
    }
}
