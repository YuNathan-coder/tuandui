package com.nathan.tuandui.sys.common.utils;

import com.nathan.tuandui.sys.common.enums.ErrorCodeEnums;

import java.util.HashMap;
import java.util.List;

public class OptResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 123L;

    public OptResult() {
        put("status", "0");
    }

    public OptResult(Integer status, Object msg) {
        put("status", status);
        put("msg", msg);
    }

    public OptResult(Integer status, String msg) {
        put("status", status);
        put("msg", msg);
    }


    public OptResult(Integer status, String msg, List<?> list) {
        put("status", status);
        put("msg", msg);
        put("data", list);
    }

    public static OptResult ok() {
        return new OptResult(1, "success");
    }

    public static OptResult ok(Object data) {
        return new OptResult(1, data);
    }

    public static OptResult error(ErrorCodeEnums enums) {
        return OptResult.error(enums.code, enums.message);
    }

    public static OptResult error() {
        return new OptResult(ErrorCodeEnums.CODE_NO.code, ErrorCodeEnums.CODE_NO.message);
    }

    public static OptResult error(String msg) {
        return error(ErrorCodeEnums.CODE_NO.code, msg);
    }

    public static OptResult error(Integer code, String msg) {
        OptResult r = new OptResult();
        r.put("status", code);
        r.put("msg", msg);
        return r;
    }

    @Override
    public OptResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }


}
