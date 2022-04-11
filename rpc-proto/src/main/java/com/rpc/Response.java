package com.rpc;

import lombok.Data;

/**
 * 表示RPC的返回
 */
@Data
public class Response {
    /**
     * 服务返回编码，0-成功，非0-失败
     */
    private int code;
    /**
     * 具体的错误信息
     */
    private String message;
    /**
     * 返回的数据
     */
    private Object data;
}
