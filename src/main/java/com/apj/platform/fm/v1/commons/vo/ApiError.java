package com.apj.platform.fm.v1.commons.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApiError {
    private String errorCode;
    private String errMessage;
    private Object params;
}
