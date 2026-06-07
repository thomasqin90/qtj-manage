package com.qtj.manageserver.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private Boolean success;
    private Integer code;
    private String message;
    private T data;
    private Long timestamp;

    // 隐藏构造方法
    private Result() {
        this.timestamp = System.currentTimeMillis();
    }

    // 成功
    public static <S> Result<S> success(int code, String message, S data) {
        Result<S> res = new Result<>();
        res.success = true;
        res.code = code;
        res.message = message;
        res.data = data;
        return res;
    }

    public static <S> Result<S> success(S data) {
        return success(200, "操作成功", data);
    }

    // 失败
    public static <S> Result<S> error(int code, String message) {
        Result<S> res = new Result<>();
        res.success = false;
        res.code = code;
        res.message = message;
        res.data = null;
        return res;
    }

    // 失败
    public static <S> Result<S> error(String message) {
        return error(500, message);
    }
}
