package com.download.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> {

    /*
     * 状态码
     * */
    private Integer code;

    /**
     * 提示信息，如果有错误时，前端可以获取该字段进行提示
     */
    private String msg;

    /*
     * 数据
     * */
    private T data;

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseResult<T> ok(T data) {
        return new ResponseResult<>(200, "请求成功", data);
    }

    public static <T> ResponseResult<T> ok(Integer code, T data) {
        return new ResponseResult<>(code, "请求成功", data);
    }

    public static <T> ResponseResult<T> ok(String msg, T data) {
        return new ResponseResult<>(200, msg, data);
    }

    public static <T> ResponseResult<T> ok(Integer code, String msg, T data) {
        return new ResponseResult<>(code, msg, data);
    }

    public static ResponseResult error(String msg) {
        return new ResponseResult(500, msg);
    }

    public static ResponseResult error(Integer code, String msg) {
        return new ResponseResult(code, msg);
    }


}
