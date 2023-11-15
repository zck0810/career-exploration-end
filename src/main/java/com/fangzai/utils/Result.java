package com.fangzai.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;//响应码，1 代表成功; 0 代表失败
    private String msg;  //响应信息 描述字符串
    private Object data; //返回的数据
    private Long total; //数据记录

    //增删改 成功响应
    public static Result success() {
        return new Result(20000, "success",null,0L);
    }

    //查询 成功响应
    public static Result success(Object data) {
        return new Result(20000, "success",data,0L);
    }

    //查询带总数
    public static Result success(Object data, Long total) {
        return new Result(20000, "success", data, total);
    }

    //失败响应null
    public static Result error(String msg) {
        return new Result(0, msg, 0L, null);
    }
}
