package com.ling.pojo;

import lombok.Data;

@Data
public class Result {
    private Integer code; // 状态码
    private String msg; // 状态信息
    private Object data; // 数据

    public static Result success(){
        Result result = new Result();
        result.setCode(1);
        result.setMsg("success");
        return result;
    }

    public static Result success(Object data){
        Result result = success();
        result.setData(data);
        return result;
    }

    public static Result error(String msg){
        Result result = new Result();
        result.setCode(0);
        result.setMsg(msg);
        return result;
    }
}
