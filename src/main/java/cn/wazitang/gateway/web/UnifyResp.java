package cn.wazitang.gateway.web;

import lombok.Data;

/**
 * 作者 zcw
 * 时间 2017/8/8 17:04
 * 描述 统一的响应格式
 */
@Data
class UnifyResp<T> {

    /**
     * 自定义响应码，非http请求状态码
     */
    private int code = 200;

    /**
     * 数据内容
     */
    private T data;

    /**
     * 错误消息
     */
    private String errMsg;

    UnifyResp() {
    }

    UnifyResp(int code) {
        this.code = code;
    }

    UnifyResp(int code, T data) {
        this.code = code;
        this.data = data;
    }
}
