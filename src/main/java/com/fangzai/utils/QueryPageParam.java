package com.fangzai.utils;

import lombok.Data;

import java.util.HashMap;

@Data
public class QueryPageParam {

    private static int PAGE_NUM=1;
    private static int PAGE_SIZE=20;

    private int pageSize=PAGE_SIZE;
    private int pageNum =PAGE_NUM;
    //自定义传参数
    private HashMap param =new HashMap();
}
