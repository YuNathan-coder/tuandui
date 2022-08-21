package com.nathan.tuandui.sys.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wtx
 * @version 1.0
 * @description：
 * @date 2021/3/4 6:57 下午
 */
@AllArgsConstructor
@Data
public class TokenException extends RuntimeException {
    private Integer status;
    private String msg;
}
