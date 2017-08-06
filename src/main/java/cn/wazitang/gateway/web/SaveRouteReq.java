package cn.wazitang.gateway.web;

import lombok.Data;

@Data
public class SaveRouteReq {
    private Long id;
    private String apiName;
    private String routeId;
    private String path;
    private String url;
}
