package com.wgl.sell.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = -7106544650760757455L;
    /*错误码*/
    private Integer code;
    /*提示信息*/
    private String msg;
    /*返回的具体内容*/
    private T data;
}
