package com.ean.management.commons;

import com.ean.management.constants.ResCode;
import lombok.Data;

/**
 * @description:TODO
 * @author:Povlean
 */
@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Integer code, String msg) {
        this(code,msg,null);
    }

    public Result() {
    }

    public static <T> Result<T> success() {
        return new Result<>(ResCode.SUCCESS,"success",null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResCode.SUCCESS,"success",data);
    }

    public static <T> Result<T> success(T data,String msg) {
        return new Result<>(ResCode.SUCCESS,msg,data);
    }

    public static <T> Result<T> success(String msg) {
        return new Result<>(ResCode.SUCCESS,msg,null);
    }

    public static <T> Result<T> error() {
        return new Result<>(20001,"fail",null);
    }

    public static <T> Result<T> error(Integer code) {
        return new Result<>(code,"fail",null);
    }

    public static <T> Result<T> error(Integer code,String msg) {
        return new Result<>(20001,msg,null);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(20001,msg,null);
    }
}
