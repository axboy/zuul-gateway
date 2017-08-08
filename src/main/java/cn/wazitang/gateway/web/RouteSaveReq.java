package cn.wazitang.gateway.web;

import lombok.Data;

@Data
public class RouteSaveReq {
    private Long id;
    private String apiName;
    private String path;
    private String url;
    private Boolean stripPrefix = true;
    private String memo;
}
