package cn.wazitang.gateway.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class CustomRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String apiName;
    private Boolean enabled;

    //以下都是和org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute字段一致
    private String routeId;
    private String path;
    private String serviceId;
    private String url;
    private boolean stripPrefix = true;
    private Boolean retryAble;
}
