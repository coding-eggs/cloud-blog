package com.cloud.blog.data.model.base;


import com.cloud.blog.data.model.enums.EnumReturnCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName BlogObject
 * @Description 全局返回类型
 * @Author wsail
 * @Date 2019/12/25 12:16
 **/

@Data
public class BaseResponse<T> implements Serializable {

    private String code = "200";


    private String msg = "成功";


    private T data;


    public BaseResponse(){
        this.code = EnumReturnCode.SUCCESS.getCode().toString();
        this.msg = EnumReturnCode.SUCCESS.getMsg();
    }
    public BaseResponse(T data){
        this();
        this.data = data;
    }

    public BaseResponse(String code, String msg){
        this();
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(String code, String msg, T data){
        this();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
